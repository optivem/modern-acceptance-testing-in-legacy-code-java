package com.optivem.eshop.systemtest.core.channels.library;

import org.junit.jupiter.api.extension.Extension;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.junit.jupiter.api.extension.TestTemplateInvocationContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContextProvider;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * JUnit Jupiter extension that enables running tests across multiple channels.
 * When a test method is annotated with @Channel and @TestTemplate, this extension
 * creates separate test invocations for each specified channel (e.g., UI, API).
 *
 * Also supports @ChannelArgumentsSource to combine channel types with inline test data.
 */
public class ChannelExtension implements TestTemplateInvocationContextProvider {

    @Override
    public boolean supportsTestTemplate(ExtensionContext context) {
        return context.getTestMethod()
                .map(method -> method.isAnnotationPresent(Channel.class))
                .orElse(false);
    }

    @Override
    public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(ExtensionContext context) {
        Method testMethod = context.getRequiredTestMethod();
        Channel channelAnnotation = testMethod.getAnnotation(Channel.class);
        String[] channels = channelAnnotation.value();

        List<Object[]> dataRows = new ArrayList<>();

        // Check if the method has @MethodSource annotation
        MethodSource methodSourceAnnotation = testMethod.getAnnotation(MethodSource.class);
        if (methodSourceAnnotation != null) {
            // Handle @MethodSource - invoke the provider method
            String[] methodNames = methodSourceAnnotation.value();
            if (methodNames.length == 0) {
                // Default: use test method name
                methodNames = new String[]{testMethod.getName()};
            }

            for (String methodName : methodNames) {
                try {
                    Method providerMethod = context.getRequiredTestClass().getDeclaredMethod(methodName);
                    providerMethod.setAccessible(true);
                    Object result = providerMethod.invoke(null);

                    if (result instanceof Stream) {
                        ((Stream<?>) result).forEach(arg -> {
                            if (arg instanceof org.junit.jupiter.params.provider.Arguments) {
                                Object[] arguments = ((org.junit.jupiter.params.provider.Arguments) arg).get();
                                dataRows.add(arguments);
                            }
                        });
                    }
                } catch (Exception e) {
                    throw new RuntimeException("Failed to invoke @MethodSource provider: " + methodName, e);
                }
            }
        } else {
            // Check if the method has ChannelArgumentsSource annotations
            ChannelArgumentsSource.Container containerAnnotation =
                    testMethod.getAnnotation(ChannelArgumentsSource.Container.class);
            ChannelArgumentsSource singleAnnotation =
                    testMethod.getAnnotation(ChannelArgumentsSource.class);

            if (containerAnnotation != null) {
                // Multiple @ChannelArgumentsSource annotations
                for (ChannelArgumentsSource annotation : containerAnnotation.value()) {
                    dataRows.addAll(extractArgumentsFromAnnotation(annotation, context));
                }
            } else if (singleAnnotation != null) {
                // Single @ChannelArgumentsSource annotation
                dataRows.addAll(extractArgumentsFromAnnotation(singleAnnotation, context));
            }
        }

        if (dataRows.isEmpty()) {
            // No data annotations, just run for each channel
            return Arrays.stream(channels)
                    .map(channel -> new ChannelInvocationContext(channel, null, testMethod));
        } else {
            // Combine channels with data rows
            List<TestTemplateInvocationContext> contexts = new ArrayList<>();
            for (String channel : channels) {
                for (Object[] dataRow : dataRows) {
                    contexts.add(new ChannelInvocationContext(channel, dataRow, testMethod));
                }
            }
            return contexts.stream();
        }
    }

    /**
     * Extracts arguments from a single @ChannelArgumentsSource annotation.
     * Handles both inline values and provider classes.
     */
    private List<Object[]> extractArgumentsFromAnnotation(ChannelArgumentsSource annotation, ExtensionContext context) {
        List<Object[]> results = new ArrayList<>();

        // Check if provider is specified
        Class<? extends ChannelArgumentsProvider> providerClass = annotation.provider();

        if (providerClass != null && providerClass != NullArgumentsProvider.class) {
            // Use provider class
            try {
                ChannelArgumentsProvider provider = providerClass.getDeclaredConstructor().newInstance();
                provider.provideArguments(context).forEach(results::add);
            } catch (Exception e) {
                throw new RuntimeException("Failed to instantiate provider: " + providerClass.getName(), e);
            }
        } else if (annotation.value().length > 0) {
            // Use inline values
            String[] values = annotation.value();
            Object[] row = new Object[values.length];
            for (int i = 0; i < values.length; i++) {
                row[i] = values[i];
            }
            results.add(row);
        }

        return results;
    }

    /**
     * Inner class representing a single test invocation context for a specific channel.
     */
    private static class ChannelInvocationContext implements TestTemplateInvocationContext {

        private final String channel;
        private final Object[] testData;
        private final Method testMethod;

        public ChannelInvocationContext(String channel, Object[] testData, Method testMethod) {
            this.channel = channel;
            this.testData = testData;
            this.testMethod = testMethod;
        }

        @Override
        public String getDisplayName(int invocationIndex) {
            if (testData == null || testData.length == 0) {
                return "[" + channel + "]";
            } else {
                StringBuilder sb = new StringBuilder("[" + channel);
                for (Object data : testData) {
                    sb.append(", ").append(data);
                }
                sb.append("]");
                return sb.toString();
            }
        }

        @Override
        public List<Extension> getAdditionalExtensions() {
            List<Extension> extensions = new ArrayList<>();
            extensions.add(new ChannelSetupExtension(channel));

            // Add TestDataParameterResolver if we have test data
            // (either from @ChannelArgumentsSource or extracted from @MethodSource)
            if (testData != null && testData.length > 0) {
                extensions.add(new TestDataParameterResolver(testData));
            }

            return extensions;
        }
    }

    /**
     * Extension that sets up the channel context before each test invocation.
     */
    private static class ChannelSetupExtension implements
            org.junit.jupiter.api.extension.BeforeEachCallback,
            org.junit.jupiter.api.extension.AfterEachCallback {

        private final String channel;

        public ChannelSetupExtension(String channel) {
            this.channel = channel;
        }

        @Override
        public void beforeEach(ExtensionContext context) {
            ChannelContext.set(channel);
        }

        @Override
        public void afterEach(ExtensionContext context) {
            ChannelContext.clear();
        }
    }

    /**
     * Parameter resolver that provides test data values to test method parameters.
     */
    private static class TestDataParameterResolver implements ParameterResolver {

        private final Object[] testData;

        public TestDataParameterResolver(Object[] testData) {
            this.testData = testData;
        }

        @Override
        public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) {
            // Support parameters that are not injected by other means (like @BeforeEach dependencies)
            int index = parameterContext.getIndex();
            return index < testData.length;
        }

        @Override
        public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) {
            int index = parameterContext.getIndex();
            if (index < testData.length) {
                Object value = testData[index];
                Class<?> targetType = parameterContext.getParameter().getType();

                // If value is already the correct type (from provider), return it directly
                if (value != null && targetType.isAssignableFrom(value.getClass())) {
                    return value;
                }

                // Otherwise, if it's a string, try to convert it
                if (value instanceof String) {
                    return convertParameter((String) value, targetType);
                }

                // Return as-is for other types
                return value;
            }
            throw new IllegalStateException("No test data available for parameter index " + index);
        }

        private Object convertParameter(String value, Class<?> targetType) {
            if (targetType == String.class) {
                return value;
            } else if (targetType == int.class || targetType == Integer.class) {
                return Integer.parseInt(value);
            } else if (targetType == long.class || targetType == Long.class) {
                return Long.parseLong(value);
            } else if (targetType == boolean.class || targetType == Boolean.class) {
                return Boolean.parseBoolean(value);
            } else if (targetType == double.class || targetType == Double.class) {
                return Double.parseDouble(value);
            }
            // Default: return as string
            return value;
        }
    }
}


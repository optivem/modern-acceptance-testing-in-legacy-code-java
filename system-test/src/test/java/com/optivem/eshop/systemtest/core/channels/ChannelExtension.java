package com.optivem.eshop.systemtest.core.channels;

import org.junit.jupiter.api.extension.Extension;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.junit.jupiter.api.extension.TestTemplateInvocationContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContextProvider;
import org.junit.jupiter.params.provider.Arguments;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * JUnit Jupiter extension that enables running tests across multiple channels.
 * When a test method is annotated with @Channel and @TestTemplate, this extension
 * creates separate test invocations for each specified channel (e.g., UI, API).
 *
 * Also supports @CombinatorialInlineData to combine channel types with inline test data.
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

        // Check if the method has CombinatorialInlineSource annotations
        CombinatorialInlineSource.Container containerAnnotation =
                testMethod.getAnnotation(CombinatorialInlineSource.Container.class);
        CombinatorialInlineSource singleAnnotation =
                testMethod.getAnnotation(CombinatorialInlineSource.class);

        List<String[]> dataRows = new ArrayList<>();

        if (containerAnnotation != null) {
            // Multiple @CombinatorialInlineSource annotations
            for (CombinatorialInlineSource data : containerAnnotation.value()) {
                dataRows.add(data.value());
            }
        } else if (singleAnnotation != null) {
            // Single @CombinatorialInlineSource annotation
            dataRows.add(singleAnnotation.value());
        }

        if (dataRows.isEmpty()) {
            // No data annotations, just run for each channel
            return Arrays.stream(channels)
                    .map(channel -> new ChannelInvocationContext(channel, null));
        } else {
            // Combine channels with data rows
            List<TestTemplateInvocationContext> contexts = new ArrayList<>();
            for (String channel : channels) {
                for (String[] dataRow : dataRows) {
                    contexts.add(new ChannelInvocationContext(channel, dataRow));
                }
            }
            return contexts.stream();
        }
    }

    /**
     * Inner class representing a single test invocation context for a specific channel.
     */
    private static class ChannelInvocationContext implements TestTemplateInvocationContext {

        private final String channel;
        private final String[] testData;

        public ChannelInvocationContext(String channel, String[] testData) {
            this.channel = channel;
            this.testData = testData;
        }

        @Override
        public String getDisplayName(int invocationIndex) {
            if (testData == null || testData.length == 0) {
                return "[" + channel + "]";
            } else {
                StringBuilder sb = new StringBuilder("[" + channel);
                for (String data : testData) {
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

        private final String[] testData;

        public TestDataParameterResolver(String[] testData) {
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
                return convertParameter(testData[index], parameterContext.getParameter().getType());
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


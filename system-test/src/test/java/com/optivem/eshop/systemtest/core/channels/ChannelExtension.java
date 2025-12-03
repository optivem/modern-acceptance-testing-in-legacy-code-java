package com.optivem.eshop.systemtest.core.channels;

import org.junit.jupiter.api.extension.Extension;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContextProvider;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * JUnit Jupiter extension that enables running tests across multiple channels.
 * When a test method is annotated with @Channel and @TestTemplate, this extension
 * creates separate test invocations for each specified channel (e.g., UI, API).
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
        Channel channelAnnotation = context.getRequiredTestMethod().getAnnotation(Channel.class);
        String[] channels = channelAnnotation.value();

        return Arrays.stream(channels)
                .map(channel -> new ChannelInvocationContext(channel));
    }

    /**
     * Inner class representing a single test invocation context for a specific channel.
     */
    private static class ChannelInvocationContext implements TestTemplateInvocationContext {

        private final String channel;

        public ChannelInvocationContext(String channel) {
            this.channel = channel;
        }

        @Override
        public String getDisplayName(int invocationIndex) {
            return "[" + channel + "]";
        }

        @Override
        public List<Extension> getAdditionalExtensions() {
            return List.of(new ChannelSetupExtension(channel));
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
}


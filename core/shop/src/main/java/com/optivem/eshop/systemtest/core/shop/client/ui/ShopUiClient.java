package com.optivem.eshop.systemtest.core.shop.client.ui;

import com.microsoft.playwright.*;
import com.optivem.eshop.systemtest.core.shop.client.ui.pages.HomePage;
import com.optivem.lang.Closer;
import com.optivem.playwright.PageClient;
import org.springframework.http.HttpStatus;


public class ShopUiClient implements AutoCloseable {

    private static final String CONTENT_TYPE = "content-type";
    private static final String TEXT_HTML = "text/html";
    private static final String HTML_OPENING_TAG = "<html";
    private static final String HTML_CLOSING_TAG = "</html>";

    // Headless mode configuration:
    // - Set via system property: -Dheadless=false (to see browser locally)
    // - Or via environment variable: HEADLESS=false
    // - Defaults to true (headless) for CI environments
    // - Can also detect CI environment automatically
    private static final boolean IS_HEADLESS = getHeadlessMode();
    private static final int SLOW_MO_MS = 100;

    private static boolean getHeadlessMode() {
        // Check system property first (e.g., -Dheadless=false)
        String systemProperty = System.getProperty("headless");
        if (systemProperty != null) {
            return Boolean.parseBoolean(systemProperty);
        }

        // Check environment variable (e.g., HEADLESS=false)
        String envVariable = System.getenv("HEADLESS");
        if (envVariable != null) {
            return Boolean.parseBoolean(envVariable);
        }

        // Auto-detect CI environment (CI=true, JENKINS_HOME, GITHUB_ACTIONS, etc.)
        if (isRunningInCI()) {
            return true;  // Always headless in CI
        }

        // Default to true (headless) for safety
        return true;
    }

    private static boolean isRunningInCI() {
        // Check common CI environment variables
        return System.getenv("CI") != null ||
               System.getenv("JENKINS_HOME") != null ||
               System.getenv("GITHUB_ACTIONS") != null ||
               System.getenv("GITLAB_CI") != null ||
               System.getenv("CIRCLECI") != null ||
               System.getenv("TRAVIS") != null ||
               System.getenv("TEAMCITY_VERSION") != null ||
               System.getenv("BUILDKITE") != null;
    }

    private final String baseUrl;
    private final Playwright playwright;
    private final Browser browser;
    private final BrowserContext context;
    private final Page page;
    private final HomePage homePage;

    private Response response;

    public ShopUiClient(String baseUrl) {
        this.baseUrl = baseUrl;
        this.playwright = Playwright.create();

        // Launch browser with options for parallel test isolation
        var launchOptions = new BrowserType.LaunchOptions()
                .setHeadless(IS_HEADLESS)
                .setSlowMo(SLOW_MO_MS);

        this.browser = playwright.chromium().launch(launchOptions);

        // Create isolated browser context for this test instance
        var contextOptions = new Browser.NewContextOptions()
                .setViewportSize(1920, 1080)
                // Ensure complete isolation between parallel tests
                .setStorageStatePath(null);

        this.context = browser.newContext(contextOptions);

        // Each test gets its own page
        this.page = context.newPage();

        var pageClient = PageClient.builder(page)
                .baseUrl(baseUrl)
                .build();
        this.homePage = new HomePage(pageClient);
    }

    public HomePage openHomePage() {
        response = page.navigate(baseUrl);
        return homePage;
    }

    public boolean isStatusOk() {
        return response.status() == HttpStatus.OK.value();
    }

    public boolean isPageLoaded() {
        if (response == null || response.status() != HttpStatus.OK.value()) {
            return false;
        }

        var contentType = response.headers().get(CONTENT_TYPE);
        if (contentType == null || !contentType.equals(TEXT_HTML)) {
            return false;
        }

        var pageContent = page.content();
        return pageContent != null && pageContent.contains(HTML_OPENING_TAG) && pageContent.contains(HTML_CLOSING_TAG);
    }

    @Override
    public void close() {
        Closer.close(page);
        Closer.close(context);
        Closer.close(browser);
        Closer.close(playwright);
    }
}


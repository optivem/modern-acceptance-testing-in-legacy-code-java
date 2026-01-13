package com.optivem.eshop.systemtest.core.shop.client.ui;

import com.microsoft.playwright.*;
import com.optivem.eshop.systemtest.core.shop.client.ui.pages.HomePage;
import com.optivem.commons.util.Closer;
import com.optivem.commons.playwright.BrowserLifecycleExtension;
import com.optivem.commons.playwright.PageClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;


public class ShopUiClient implements AutoCloseable {
    private static final Logger log = LoggerFactory.getLogger(ShopUiClient.class);

    private static final String CONTENT_TYPE = "content-type";
    private static final String TEXT_HTML = "text/html";
    private static final String HTML_OPENING_TAG = "<html";
    private static final String HTML_CLOSING_TAG = "</html>";

    private final String baseUrl;
    private final BrowserContext context;
    private final Page page;
    private final HomePage homePage;

    private Response response;

    public ShopUiClient(String baseUrl) {
        long totalStart = System.currentTimeMillis();
        this.baseUrl = baseUrl;

        // Get browser for current thread from BrowserLifecycleExtension
        Browser browser = BrowserLifecycleExtension.getBrowser();

        // Create isolated browser context for this test instance
        var contextOptions = new Browser.NewContextOptions()
                .setViewportSize(1920, 1080)
                // Ensure complete isolation between parallel tests
                .setStorageStatePath(null);

        long contextStart = System.currentTimeMillis();
        this.context = browser.newContext(contextOptions);
        log.info("[PERF] Browser context creation took {}ms", System.currentTimeMillis() - contextStart);

        // Each test gets its own page
        long pageStart = System.currentTimeMillis();
        this.page = context.newPage();
        log.info("[PERF] New page creation took {}ms", System.currentTimeMillis() - pageStart);

        var pageClient = PageClient.builder(page)
                .baseUrl(baseUrl)
                .build();
        this.homePage = new HomePage(pageClient);
        
        log.info("[PERF] ShopUiClient constructor total took {}ms", System.currentTimeMillis() - totalStart);
    }

    public HomePage openHomePage() {
        long start = System.currentTimeMillis();
        response = page.navigate(baseUrl);
        log.info("[PERF] page.navigate() took {}ms", System.currentTimeMillis() - start);
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
        // Don't close browser - it's shared and managed by BrowserLifecycleExtension
    }
}


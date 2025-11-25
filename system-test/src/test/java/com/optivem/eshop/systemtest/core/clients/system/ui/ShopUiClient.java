package com.optivem.eshop.systemtest.core.clients.system.ui;

import com.microsoft.playwright.*;
import com.optivem.eshop.systemtest.core.clients.commons.TestPageClient;
import com.optivem.eshop.systemtest.core.clients.system.ui.pages.HomePage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ShopUiClient implements AutoCloseable {

    private static final String CONTENT_TYPE = "content-type";
    private static final String TEXT_HTML = "text/html";
    private static final String HTML_OPENING_TAG = "<html";
    private static final String HTML_CLOSING_TAG = "</html>";

    private final String baseUrl;
    private final Playwright playwright;
    private final Browser browser;
    private final Page page;
    private final TestPageClient pageClient;
    private final HomePage homePage;

    private Response response;

    public ShopUiClient(String baseUrl) {
        this.baseUrl = baseUrl;
        this.playwright = Playwright.create();
        this.browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));
        this.page = browser.newPage();
        this.pageClient = new TestPageClient(page, baseUrl);
        this.homePage = new HomePage(pageClient);
    }

    public HomePage openHomePage() {
        response = page.navigate(baseUrl);
        return homePage;
    }

    public boolean isStatusOk() {
        return response.status() == 200;
    }

    public void assertHomePageLoaded() {
        assertEquals(200, response.status());

        var contentType = response.headers().get(CONTENT_TYPE);
        assertTrue(contentType != null && contentType.contains(TEXT_HTML),
                "Content-Type should be text/html, but was: " + contentType);

        // Check HTML structure using Playwright's content method
        var pageContent = page.content();
        assertTrue(pageContent.contains(HTML_OPENING_TAG), "Response should contain HTML opening tag");
        assertTrue(pageContent.contains(HTML_CLOSING_TAG), "Response should contain HTML closing tag");
    }

    @Override
    public void close() {
        if (page != null) {
            page.close();
        }
        if (browser != null) {
            browser.close();
        }
        if (playwright != null) {
            playwright.close();
        }
    }
}


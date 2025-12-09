package com.optivem.eshop.systemtest.core.drivers.system.shop.ui.client;

import com.microsoft.playwright.*;
import com.optivem.lang.Closer;
import com.optivem.playwright.PageGateway;
import com.optivem.eshop.systemtest.core.drivers.system.shop.ui.client.pages.HomePage;
import org.springframework.http.HttpStatus;


public class ShopUiClient implements AutoCloseable {

    private static final String CONTENT_TYPE = "content-type";
    private static final String TEXT_HTML = "text/html";
    private static final String HTML_OPENING_TAG = "<html";
    private static final String HTML_CLOSING_TAG = "</html>";

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
        this.browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));
        this.context = browser.newContext();
        this.page = browser.newPage();
        var pageClient = PageGateway.builder(page)
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
        if(response == null || response.status() != HttpStatus.OK.value()) {
            return false;
        }

        var contentType = response.headers().get(CONTENT_TYPE);
        if(contentType == null || !contentType.equals(TEXT_HTML)) {
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


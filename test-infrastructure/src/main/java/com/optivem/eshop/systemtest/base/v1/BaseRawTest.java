package com.optivem.eshop.systemtest.base.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.microsoft.playwright.*;
import com.optivem.eshop.systemtest.configuration.BaseConfigurableTest;
import com.optivem.eshop.systemtest.core.SystemConfiguration;
import com.optivem.lang.Closer;
import org.junit.jupiter.api.AfterEach;

import java.net.http.HttpClient;

public class BaseRawTest extends BaseConfigurableTest {
    protected HttpClient erpHttpClient;
    protected HttpClient taxHttpClient;
    protected HttpClient shopHttpClient;
    protected Playwright playwright;
    protected Browser browser;
    protected BrowserContext browserContext;
    protected Page page;
    protected SystemConfiguration configuration;
    protected ObjectMapper objectMapper;

    protected void setUpExternalHttpClients() {
        configuration = loadConfiguration();
        erpHttpClient = HttpClient.newHttpClient();
        taxHttpClient = HttpClient.newHttpClient();
        objectMapper = createObjectMapper();
    }

    protected void setUpShopHttpClient() {
        if (configuration == null) {
            configuration = loadConfiguration();
        }
        shopHttpClient = HttpClient.newHttpClient();
        if (objectMapper == null) {
            objectMapper = createObjectMapper();
        }
    }

    protected void setUpShopBrowser() {
        if (configuration == null) {
            configuration = loadConfiguration();
        }

        playwright = Playwright.create();

        var launchOptions = new BrowserType.LaunchOptions()
                .setHeadless(true)
                .setSlowMo(100);

        browser = playwright.chromium().launch(launchOptions);

        var contextOptions = new Browser.NewContextOptions()
                .setViewportSize(1920, 1080)
                .setStorageStatePath(null);

        browserContext = browser.newContext(contextOptions);
        page = browserContext.newPage();
    }

    protected String getErpBaseUrl() {
        return configuration.getErpBaseUrl();
    }

    protected String getTaxBaseUrl() {
        return configuration.getTaxBaseUrl();
    }

    protected String getShopApiBaseUrl() {
        return configuration.getShopApiBaseUrl();
    }

    protected String getShopUiBaseUrl() {
        return configuration.getShopUiBaseUrl();
    }

    private ObjectMapper createObjectMapper() {
        var mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }

    @AfterEach
    void tearDown() {
        Closer.close(page);
        Closer.close(browserContext);
        Closer.close(browser);
        Closer.close(playwright);
        Closer.close(erpHttpClient);
        Closer.close(taxHttpClient);
        Closer.close(shopHttpClient);
    }
}


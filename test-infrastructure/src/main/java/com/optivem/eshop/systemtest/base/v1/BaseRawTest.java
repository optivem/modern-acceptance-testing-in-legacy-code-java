package com.optivem.eshop.systemtest.base.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.microsoft.playwright.*;
import com.optivem.eshop.systemtest.configuration.BaseConfigurableTest;
import com.optivem.eshop.systemtest.core.SystemConfiguration;
import com.optivem.commons.util.Closer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.net.http.HttpClient;

public class BaseRawTest extends BaseConfigurableTest {
    protected SystemConfiguration configuration;

    protected HttpClient shopHttpClient;
    protected HttpClient erpHttpClient;
    protected HttpClient taxHttpClient;

    protected Playwright playwright;
    protected Browser browser;
    protected BrowserContext browserContext;
    protected Page page;

    protected ObjectMapper objectMapper;

    @BeforeEach
    protected void setUpConfiguration() {
        configuration = loadConfiguration();
    }

    protected void setUpShopBrowser() {
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

    protected void setUpShopHttpClient() {
        shopHttpClient = HttpClient.newHttpClient();
        if (objectMapper == null) {
            objectMapper = createObjectMapper();
        }
    }

    protected void setUpExternalHttpClients() {
        configuration = loadConfiguration();
        erpHttpClient = HttpClient.newHttpClient();
        taxHttpClient = HttpClient.newHttpClient();
        objectMapper = createObjectMapper();
    }

    protected String getShopUiBaseUrl() {
        return configuration.getShopUiBaseUrl();
    }

    protected String getShopApiBaseUrl() {
        return configuration.getShopApiBaseUrl();
    }

    protected String getErpBaseUrl() {
        return configuration.getErpBaseUrl();
    }

    protected String getTaxBaseUrl() {
        return configuration.getTaxBaseUrl();
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


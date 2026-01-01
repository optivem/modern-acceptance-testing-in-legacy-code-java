package com.optivem.playwright;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.extension.*;

/**
 * JUnit Jupiter extension that manages Playwright browser lifecycle for improved performance.
 * Creates one browser per thread (not per test), significantly reducing test execution time.
 * <p>
 * Why one browser per thread?
 * - Playwright Browser objects are NOT thread-safe for context creation
 * - Sharing a single browser across threads causes "Cannot find object to call __adopt__" errors
 * - ThreadLocal ensures each parallel thread gets its own browser instance
 * - Each browser is reused for all tests running in that thread
 * <p>
 * With 4 parallel threads, you'll see 4 browser instances (one per thread), 
 * but each is reused for ~5 tests, saving ~300ms per test after the first.
 * <p>
 * Thread-safe implementation using ThreadLocal ensures proper isolation in parallel test execution.
 * <p>
 * Usage:
 * <pre>
 * {@code
 * @ExtendWith(BrowserLifecycleExtension.class)
 * public class MyTest {
 *     // Tests automatically use thread-local shared browser
 * }
 * }
 * </pre>
 */
public class BrowserLifecycleExtension implements BeforeAllCallback, AfterAllCallback {
    
    private static final ThreadLocal<Browser> BROWSER_POOL = new ThreadLocal<>();
    private static final ThreadLocal<Playwright> PLAYWRIGHT_POOL = new ThreadLocal<>();
    
    private static final boolean IS_HEADLESS = getHeadlessMode();
    private static final int SLOW_MO_MS = 100;
    private static final boolean DEFAULT_HEADLESS = false;
    
    private static boolean getHeadlessMode() {
        String systemProperty = System.getProperty("headless");
        if (systemProperty != null) {
            return Boolean.parseBoolean(systemProperty);
        }
        
        String envVariable = System.getenv("HEADLESS");
        if (envVariable != null) {
            return Boolean.parseBoolean(envVariable);
        }
        
        if (isRunningInCI()) {
            return true;
        }
        
        return DEFAULT_HEADLESS;
    }
    
    private static boolean isRunningInCI() {
        return System.getenv("CI") != null ||
               System.getenv("JENKINS_HOME") != null ||
               System.getenv("GITHUB_ACTIONS") != null ||
               System.getenv("GITLAB_CI") != null ||
               System.getenv("CIRCLECI") != null ||
               System.getenv("TRAVIS") != null ||
               System.getenv("TEAMCITY_VERSION") != null ||
               System.getenv("BUILDKITE") != null;
    }
    
    @Override
    public void beforeAll(ExtensionContext context) {
        // Browser will be created lazily on first access per thread
    }
    
    @Override
    public void afterAll(ExtensionContext context) {
        // Clean up browser for this thread after test class completes
        Browser browser = BROWSER_POOL.get();
        Playwright playwright = PLAYWRIGHT_POOL.get();
        
        if (browser != null) {
            browser.close();
            BROWSER_POOL.remove();
        }
        
        if (playwright != null) {
            playwright.close();
            PLAYWRIGHT_POOL.remove();
        }
    }
    
    /**
     * Gets the browser instance for the current thread.
     * Creates browser lazily on first access, then reuses it for all subsequent tests in this thread.
     * Thread-safe: each thread gets its own isolated browser instance.
     *
     * @return the Browser instance for this thread
     */
    public static Browser getBrowser() {
        Browser browser = BROWSER_POOL.get();
        
        if (browser == null) {
            // Lazy initialization for this thread
            Playwright playwright = Playwright.create();
            
            var launchOptions = new BrowserType.LaunchOptions()
                    .setHeadless(IS_HEADLESS)
                    .setSlowMo(SLOW_MO_MS);
            
            browser = playwright.chromium().launch(launchOptions);
            
            PLAYWRIGHT_POOL.set(playwright);
            BROWSER_POOL.set(browser);
        }
        
        return browser;
    }
}

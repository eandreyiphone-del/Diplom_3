package ru.yandex.practicum.base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

/**
 * Базовый класс для всех тестов
 */
public abstract class BaseTest {

    protected WebDriver driver;
    protected static final String BASE_URL = "https://stellarburgers.education-services.ru/";

    @Before
    public void setUp() {
        setupDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get(BASE_URL);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    /**
     * Настройка WebDriver
     * По умолчанию используется Chrome, можно переопределить через системное свойство browser
     */
    private void setupDriver() {
        String browserName = System.getProperty("browser", "chrome");

        switch (browserName.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--disable-dev-shm-usage");
                chromeOptions.addArguments("--no-sandbox");
                driver = new ChromeDriver(chromeOptions);
                break;
            case "yandex":
                // Для Яндекс браузера используем ChromeDriver с путем к Yandex
                System.setProperty("webdriver.chrome.driver", getYandexDriverPath());
                ChromeOptions yandexOptions = new ChromeOptions();
                yandexOptions.setBinary(getYandexBinaryPath());
                yandexOptions.addArguments("--disable-dev-shm-usage");
                yandexOptions.addArguments("--no-sandbox");
                driver = new ChromeDriver(yandexOptions);
                break;
            default:
                throw new IllegalArgumentException("Браузер " + browserName + " не поддерживается");
        }
    }

    /**
     * Получить путь к драйверу Yandex браузера
     */
    private String getYandexDriverPath() {
        // Путь может отличаться в зависимости от системы
        return System.getProperty("yandex.driver.path",
                "C:\\Users\\%USERNAME%\\AppData\\Local\\Yandex\\YandexBrowser\\Application\\chromedriver.exe");
    }

    /**
     * Получить путь к исполняемому файлу Yandex браузера
     */
    private String getYandexBinaryPath() {
        return System.getProperty("yandex.binary.path",
                "C:\\Users\\%USERNAME%\\AppData\\Local\\Yandex\\YandexBrowser\\Application\\browser.exe");
    }
}

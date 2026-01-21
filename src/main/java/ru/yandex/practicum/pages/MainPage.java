package ru.yandex.practicum.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Page Object для главной страницы
 */
public class MainPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Кнопки входа
    @FindBy(xpath = "//button[contains(text(),'Войти в аккаунт')]")
    private WebElement loginButton;

    @FindBy(xpath = "//p[contains(text(),'Личный Кабинет')]")
    private WebElement personalAccountButton;

    // Разделы конструктора
    @FindBy(xpath = "//span[text()='Булки']")
    private WebElement bunsSection;

    @FindBy(xpath = "//span[text()='Соусы']")
    private WebElement saucesSection;

    @FindBy(xpath = "//span[text()='Начинки']")
    private WebElement fillingsSection;

    // Активные разделы (для проверки)
    @FindBy(xpath = "//div[contains(@class,'tab_tab_type_current')]//span[text()='Булки']")
    private WebElement activeBunsSection;

    @FindBy(xpath = "//div[contains(@class,'tab_tab_type_current')]//span[text()='Соусы']")
    private WebElement activeSaucesSection;

    @FindBy(xpath = "//div[contains(@class,'tab_tab_type_current')]//span[text()='Начинки']")
    private WebElement activeFillingsSection;

    public MainPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    @Step("Нажать кнопку 'Войти в аккаунт' на главной странице")
    public LoginPage clickLoginButton() {
        wait.until(ExpectedConditions.elementToBeClickable(loginButton));
        loginButton.click();
        return new LoginPage(driver);
    }

    @Step("Нажать кнопку 'Личный кабинет'")
    public LoginPage clickPersonalAccountButton() {
        wait.until(ExpectedConditions.elementToBeClickable(personalAccountButton));
        personalAccountButton.click();
        return new LoginPage(driver);
    }

    @Step("Нажать на раздел 'Булки'")
    public void clickBunsSection() {
        wait.until(ExpectedConditions.elementToBeClickable(bunsSection));
        bunsSection.click();
    }

    @Step("Нажать на раздел 'Соусы'")
    public void clickSaucesSection() {
        wait.until(ExpectedConditions.elementToBeClickable(saucesSection));
        saucesSection.click();
    }

    @Step("Нажать на раздел 'Начинки'")
    public void clickFillingsSection() {
        wait.until(ExpectedConditions.elementToBeClickable(fillingsSection));
        fillingsSection.click();
    }

    @Step("Проверить, что активен раздел 'Булки'")
    public boolean isBunsSectionActive() {
        try {
            wait.until(ExpectedConditions.visibilityOf(activeBunsSection));
            return activeBunsSection.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Проверить, что активен раздел 'Соусы'")
    public boolean isSaucesSectionActive() {
        try {
            wait.until(ExpectedConditions.visibilityOf(activeSaucesSection));
            return activeSaucesSection.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Проверить, что активен раздел 'Начинки'")
    public boolean isFillingsSectionActive() {
        try {
            wait.until(ExpectedConditions.visibilityOf(activeFillingsSection));
            return activeFillingsSection.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}

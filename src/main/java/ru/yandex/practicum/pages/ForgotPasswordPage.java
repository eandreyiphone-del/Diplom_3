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
 * Page Object для страницы восстановления пароля
 */
public class ForgotPasswordPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Поля ввода
    @FindBy(xpath = "//input[@name='name']")
    private WebElement emailField;

    // Кнопки
    @FindBy(xpath = "//button[contains(text(),'Восстановить')]")
    private WebElement restoreButton;

    @FindBy(xpath = "//a[contains(text(),'Войти')]")
    private WebElement loginLink;

    public ForgotPasswordPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    @Step("Ввести email для восстановления: {email}")
    public ForgotPasswordPage enterEmail(String email) {
        wait.until(ExpectedConditions.visibilityOf(emailField));
        emailField.clear();
        emailField.sendKeys(email);
        return this;
    }

    @Step("Нажать кнопку 'Восстановить'")
    public void clickRestoreButton() {
        wait.until(ExpectedConditions.elementToBeClickable(restoreButton));
        restoreButton.click();
    }

    @Step("Нажать ссылку 'Войти'")
    public LoginPage clickLoginLink() {
        wait.until(ExpectedConditions.elementToBeClickable(loginLink));
        loginLink.click();
        return new LoginPage(driver);
    }
}

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
 * Page Object для страницы входа
 */
public class LoginPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Поля ввода (более устойчивые локаторы)
    @FindBy(xpath = "//label[text()='Email']/following-sibling::input | //input[@name='email']")
    private WebElement emailField;

    @FindBy(xpath = "//label[text()='Пароль']/following-sibling::input | //input[@type='password']")
    private WebElement passwordField;

    // Кнопки
    @FindBy(xpath = "//button[contains(text(),'Войти')]")
    private WebElement loginSubmitButton;

    @FindBy(xpath = "//a[contains(text(),'Зарегистрироваться')]")
    private WebElement registerLink;

    @FindBy(xpath = "//a[contains(text(),'Восстановить пароль')]")
    private WebElement forgotPasswordLink;

    // Сообщения об ошибках
    @FindBy(xpath = "//p[contains(@class,'input__error')]")
    private WebElement errorMessage;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    @Step("Ввести email: {email}")
    public LoginPage enterEmail(String email) {
        wait.until(ExpectedConditions.visibilityOf(emailField));
        emailField.clear();
        emailField.sendKeys(email);
        return this;
    }

    @Step("Ввести пароль")
    public LoginPage enterPassword(String password) {
        wait.until(ExpectedConditions.visibilityOf(passwordField));
        passwordField.clear();
        passwordField.sendKeys(password);
        return this;
    }

    @Step("Нажать кнопку 'Войти'")
    public MainPage clickLoginButton() {
        wait.until(ExpectedConditions.elementToBeClickable(loginSubmitButton)).click();
        // Ждем ухода со страницы /login
        new WebDriverWait(driver, Duration.ofSeconds(15))
                .until(ExpectedConditions.not(ExpectedConditions.urlContains("/login")));
        return new MainPage(driver);
    }

    @Step("Нажать кнопку 'Войти' (ожидаем остаться на /login)")
    public LoginPage clickLoginButtonExpectingStay() {
        wait.until(ExpectedConditions.elementToBeClickable(loginSubmitButton)).click();
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.urlContains("/login"));
        return this;
    }

    @Step("Нажать ссылку 'Зарегистрироваться'")
    public RegisterPage clickRegisterLink() {
        wait.until(ExpectedConditions.elementToBeClickable(registerLink)).click();
        return new RegisterPage(driver);
    }

    @Step("Нажать ссылку 'Восстановить пароль'")
    public ForgotPasswordPage clickForgotPasswordLink() {
        wait.until(ExpectedConditions.elementToBeClickable(forgotPasswordLink)).click();
        return new ForgotPasswordPage(driver);
    }

    @Step("Получить текст сообщения об ошибке")
    public String getErrorMessage() {
        try {
            wait.until(ExpectedConditions.visibilityOf(errorMessage));
            return errorMessage.getText();
        } catch (Exception e) {
            return "";
        }
    }

    @Step("Проверить, что отображается сообщение об ошибке")
    public boolean isErrorMessageDisplayed() {
        try {
            return errorMessage.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Выполнить вход с учетными данными: {email}")
    public MainPage login(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        return clickLoginButton();
    }

    @Step("Форма входа отображается")
    public boolean isLoginFormVisible() {
        try {
            return wait.until(ExpectedConditions.visibilityOf(loginSubmitButton)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}

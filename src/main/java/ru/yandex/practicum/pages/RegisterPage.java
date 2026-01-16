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
 * Page Object для страницы регистрации
 */
public class RegisterPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Поля ввода (устойчивые локаторы по атрибуту name)
    @FindBy(xpath = "//input[@name='name'] | //label[normalize-space()='Имя']/following-sibling::input")
    private WebElement nameField;

    @FindBy(xpath = "//input[@name='email'] | //label[normalize-space()='Email']/following-sibling::input")
    private WebElement emailField;

    @FindBy(xpath = "//input[@name='password'] | //label[normalize-space()='Пароль']/following-sibling::input | //input[@type='password']")
    private WebElement passwordField;

    // Кнопки / ссылки
    @FindBy(xpath = "//button[contains(text(),'Зарегистрироваться')]")
    private WebElement registerButton;

    @FindBy(xpath = "//a[contains(text(),'Войти')] | //a[@href='/login']")
    private WebElement loginLink;

    // Ошибка валидации
    @FindBy(xpath = "//p[contains(@class,'input__error')]")
    private WebElement errorMessage;

    public RegisterPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    @Step("Ввести имя: {name}")
    public RegisterPage enterName(String name) {
        wait.until(ExpectedConditions.visibilityOf(nameField));
        nameField.clear();
        nameField.sendKeys(name);
        return this;
    }

    @Step("Ввести email: {email}")
    public RegisterPage enterEmail(String email) {
        wait.until(ExpectedConditions.visibilityOf(emailField));
        emailField.clear();
        emailField.sendKeys(email);
        return this;
    }

    @Step("Ввести пароль")
    public RegisterPage enterPassword(String password) {
        wait.until(ExpectedConditions.visibilityOf(passwordField));
        passwordField.clear();
        passwordField.sendKeys(password);
        return this;
    }

    @Step("Нажать кнопку 'Зарегистрироваться'")
    public LoginPage clickRegisterButton() {
        wait.until(ExpectedConditions.elementToBeClickable(registerButton)).click();
        new WebDriverWait(driver, Duration.ofSeconds(15))
                .until(ExpectedConditions.urlContains("/login"));
        return new LoginPage(driver);
    }

    @Step("Нажать ссылку 'Войти'")
    public LoginPage clickLoginLink() {
        wait.until(ExpectedConditions.elementToBeClickable(loginLink)).click();
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.urlContains("/login"));
        return new LoginPage(driver);
    }

    @Step("Получить текст сообщения об ошибке")
    public String getErrorMessage() {
        try {
            return wait.until(ExpectedConditions.visibilityOf(errorMessage)).getText();
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

    @Step("Зарегистрировать пользователя: {name}, {email}")
    public LoginPage register(String name, String email, String password) {
        enterName(name);
        enterEmail(email);
        enterPassword(password);
        return clickRegisterButton();
    }

    @Step("Попытаться зарегистрироваться с некорректными данными")
    public RegisterPage tryRegisterWithError(String name, String email, String password) {
        enterName(name);
        enterEmail(email);
        enterPassword(password);
        wait.until(ExpectedConditions.elementToBeClickable(registerButton)).click();
        return this;
    }
}

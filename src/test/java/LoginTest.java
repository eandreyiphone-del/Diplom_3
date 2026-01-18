package ru.yandex.practicum.tests;

import io.qameta.allure.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.practicum.api.UserApiClient;
import ru.yandex.practicum.base.BaseTest;
import ru.yandex.practicum.model.User;
import ru.yandex.practicum.pages.ForgotPasswordPage;
import ru.yandex.practicum.pages.LoginPage;
import ru.yandex.practicum.pages.MainPage;
import ru.yandex.practicum.pages.RegisterPage;
import ru.yandex.practicum.utils.TestDataGenerator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Тесты функциональности входа в систему
 */
@Epic("Stellar Burgers")
@Feature("Вход в систему")
public class LoginTest extends BaseTest {

    private String testEmail;
    private String testPassword;
    private String testName;
    private String userToken;

    @Before
    public void prepareTestUser() {
        testName = TestDataGenerator.generateRandomName();
        testEmail = TestDataGenerator.generateRandomEmail();
        testPassword = TestDataGenerator.generateValidPassword();

        User user = new User(testEmail, testPassword, testName);
        userToken = UserApiClient.createUser(user);

        driver.get(BASE_URL);
    }

    @Override
    @After
    public void tearDown() {
        if (userToken != null) {
            UserApiClient.deleteUser(userToken);
        }
    }

    @Test
    @Description("Вход через основную кнопку входа работает корректно")
    @Severity(SeverityLevel.CRITICAL)
    @Step("Вход через основную кнопку входа")
    public void testLoginViaMainLoginButton() {
        MainPage mainPage = new MainPage(driver);
        LoginPage loginPage = mainPage.clickLoginButton();
        loginPage.login(testEmail, testPassword);
        assertFalse("После входа не должны оставаться на /login", driver.getCurrentUrl().contains("/login"));
    }

    @Test
    @Description("Вход через кнопку 'Личный кабинет' работает корректно")
    @Severity(SeverityLevel.CRITICAL)
    @Step("Вход через кнопку 'Личный кабинет'")
    public void testLoginViaPersonalAccountButton() {
        MainPage mainPage = new MainPage(driver);
        LoginPage loginPage = mainPage.clickPersonalAccountButton();
        loginPage.login(testEmail, testPassword);
        assertFalse("После входа не должны оставаться на /login", driver.getCurrentUrl().contains("/login"));
    }

    @Test
    @Description("Вход через ссылку на форме регистрации работает корректно")
    @Severity(SeverityLevel.NORMAL)
    @Step("Вход через ссылку на форме регистрации")
    public void testLoginViaRegistrationForm() {
        MainPage mainPage = new MainPage(driver);
        RegisterPage registerPage = mainPage.clickLoginButton().clickRegisterLink();
        LoginPage loginPage = registerPage.clickLoginLink();
        loginPage.login(testEmail, testPassword);
        assertFalse("После входа не должны оставаться на /login", driver.getCurrentUrl().contains("/login"));
    }

    @Test
    @Description("Вход через ссылку на форме восстановления пароля работает корректно")
    @Severity(SeverityLevel.NORMAL)
    @Step("Вход через ссылку на форме восстановления пароля")
    public void testLoginViaForgotPasswordForm() {
        MainPage mainPage = new MainPage(driver);
        LoginPage initialLoginPage = mainPage.clickLoginButton();
        ForgotPasswordPage forgotPasswordPage = initialLoginPage.clickForgotPasswordLink();
        LoginPage loginPage = forgotPasswordPage.clickLoginLink();
        loginPage.login(testEmail, testPassword);
        assertFalse("После входа не должны оставаться на /login", driver.getCurrentUrl().contains("/login"));
    }

    @Test
    @Description("Неверные учетные данные показывают ошибку")
    @Severity(SeverityLevel.NORMAL)
    @Step("Неверные учетные данные показывают ошибку")
    public void testLoginWithInvalidCredentials() {
        MainPage mainPage = new MainPage(driver);
        LoginPage loginPage = mainPage.clickLoginButton();
        String invalidEmail = TestDataGenerator.generateRandomEmail();
        String invalidPassword = TestDataGenerator.generateValidPassword();
        loginPage.enterEmail(invalidEmail);
        loginPage.enterPassword(invalidPassword);
        loginPage.clickLoginButtonExpectingStay();
        assertTrue("При неверных данных должны остаться на странице входа", driver.getCurrentUrl().contains("login"));
    }
}

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
        // Создаем тестового пользователя через API с данными из DataFaker
        testName = TestDataGenerator.generateRandomName();
        testEmail = TestDataGenerator.generateRandomEmail();
        testPassword = TestDataGenerator.generateValidPassword();

        User user = new User(testEmail, testPassword, testName);
        userToken = UserApiClient.createUser(user);

        // Возвращаемся на главную страницу
        driver.get(BASE_URL);
    }

    @Override
    @After
    public void tearDown() {
        // Удаляем созданного пользователя через API
        if (userToken != null) {
            UserApiClient.deleteUser(userToken);
        }
    }

    @Test
    @Description("Вход через основную кнопку входа работает корректно")
    @Severity(SeverityLevel.CRITICAL)
    public void testLoginViaMainLoginButton() {
        MainPage mainPage = new MainPage(driver);
        LoginPage loginPage = mainPage.clickLoginButton();

        // Входим в систему
        loginPage.login(testEmail, testPassword);

        // Проверяем: ушли со страницы /login
        assertFalse("После входа не должны оставаться на /login", driver.getCurrentUrl().contains("/login"));
    }

    @Test
    @Description("Вход через кнопку 'Личный кабинет' работает корректно")
    @Severity(SeverityLevel.CRITICAL)
    public void testLoginViaPersonalAccountButton() {
        MainPage mainPage = new MainPage(driver);
        LoginPage loginPage = mainPage.clickPersonalAccountButton();

        // Входим в систему
        loginPage.login(testEmail, testPassword);

        assertFalse("После входа не должны оставаться на /login", driver.getCurrentUrl().contains("/login"));
    }

    @Test
    @Description("Вход через ссылку на форме регистрации работает корректно")
    @Severity(SeverityLevel.NORMAL)
    public void testLoginViaRegistrationForm() {
        MainPage mainPage = new MainPage(driver);
        RegisterPage registerPage = mainPage.clickLoginButton().clickRegisterLink();
        LoginPage loginPage = registerPage.clickLoginLink();

        // Входим в систему
        loginPage.login(testEmail, testPassword);

        assertFalse("После входа не должны оставаться на /login", driver.getCurrentUrl().contains("/login"));
    }

    @Test
    @Description("Вход через ссылку на форме восстановления пароля работает корректно")
    @Severity(SeverityLevel.NORMAL)
    public void testLoginViaForgotPasswordForm() {
        MainPage mainPage = new MainPage(driver);
        LoginPage initialLoginPage = mainPage.clickLoginButton();
        ForgotPasswordPage forgotPasswordPage = initialLoginPage.clickForgotPasswordLink();
        LoginPage loginPage = forgotPasswordPage.clickLoginLink();

        // Входим в систему
        loginPage.login(testEmail, testPassword);

        assertFalse("После входа не должны оставаться на /login", driver.getCurrentUrl().contains("/login"));
    }

    @Test
    @Description("Неверные учетные данные показывают ошибку")
    @Severity(SeverityLevel.NORMAL)
    public void testLoginWithInvalidCredentials() {
        MainPage mainPage = new MainPage(driver);
        LoginPage loginPage = mainPage.clickLoginButton();

        // Пытаемся войти с неверными данными, сгенерированными DataFaker
        String invalidEmail = TestDataGenerator.generateRandomEmail();
        String invalidPassword = TestDataGenerator.generateValidPassword();

        loginPage.enterEmail(invalidEmail);
        loginPage.enterPassword(invalidPassword);
        loginPage.clickLoginButtonExpectingStay();

        // Проверяем, что остались на странице входа (не произошел переход)
        assertTrue("При неверных данных должны остаться на странице входа", driver.getCurrentUrl().contains("login"));
    }
}

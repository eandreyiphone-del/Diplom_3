import io.qameta.allure.*;
import org.junit.After;
import org.junit.Test;
import ru.yandex.practicum.api.UserApiClient;
import ru.yandex.practicum.base.BaseTest;
import ru.yandex.practicum.pages.MainPage;
import ru.yandex.practicum.pages.RegisterPage;
import ru.yandex.practicum.utils.TestDataGenerator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Тесты функциональности регистрации
 */
@Epic("Stellar Burgers")
@Feature("Регистрация пользователей")
public class RegistrationTest extends BaseTest {

    private String createdUserToken;

    @Override
    @After
    public void tearDown() {
        if (createdUserToken != null) {
            UserApiClient.deleteUser(createdUserToken);
        }
    }

    @Test
    @Description("Успешная регистрация с валидными данными")
    @Severity(SeverityLevel.CRITICAL)
    @Step("Успешная регистрация с валидными данными")
    public void testSuccessfulRegistration() {
        String name = TestDataGenerator.generateRandomName();
        String email = TestDataGenerator.generateRandomEmail();
        String password = TestDataGenerator.generateValidPassword();

        MainPage mainPage = new MainPage(driver);
        RegisterPage registerPage = mainPage.clickLoginButton().clickRegisterLink();
        registerPage.register(name, email, password);

        // Сохраняем токен ДЛЯ УДАЛЕНИЯ ПОСЛЕ ТЕСТА
        createdUserToken = UserApiClient.loginUser(email, password);

        // Только после сохранения токена делаем утверждение
        assertTrue("После успешной регистрации должен быть переход на страницу входа",
                driver.getCurrentUrl().contains("/login"));
    }

    @Test
    @Description("Регистрация с паролем менее 6 символов показывает ошибку")
    @Severity(SeverityLevel.NORMAL)
    @Step("Регистрация с паролем менее 6 символов")
    public void testRegistrationWithInvalidPassword() {
        String name = TestDataGenerator.generateRandomName();
        String email = TestDataGenerator.generateRandomEmail();
        String invalidPassword = TestDataGenerator.generateInvalidPassword();

        MainPage mainPage = new MainPage(driver);
        RegisterPage registerPage = mainPage.clickLoginButton().clickRegisterLink();
        registerPage.tryRegisterWithError(name, email, invalidPassword);
        assertTrue("Должно отображаться сообщение об ошибке для некорректного пароля",
                registerPage.isErrorMessageDisplayed());
        String errorMessage = registerPage.getErrorMessage();
        assertFalse("Сообщение об ошибке не должно быть пустым",
                errorMessage.isEmpty());
    }

    @Test
    @Description("Навигация с регистрации на вход работает корректно")
    @Severity(SeverityLevel.MINOR)
    @Step("Навигация с регистрации на вход")
    public void testNavigationToLoginFromRegistration() {
        MainPage mainPage = new MainPage(driver);
        RegisterPage registerPage = mainPage.clickLoginButton().clickRegisterLink();
        registerPage.clickLoginLink();
        assertTrue("Должен быть переход на страницу входа",
                driver.getCurrentUrl().contains("login"));
    }
}
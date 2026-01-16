package ru.yandex.practicum.api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import ru.yandex.practicum.model.User;

import static io.restassured.RestAssured.given;

/**
 * API клиент для работы с пользователями через REST API
 */
public class UserApiClient {

    private static final String BASE_URL = "https://stellarburgers.nomoreparties.site/api";
    private static final String REGISTER_ENDPOINT = "/auth/register";
    private static final String LOGIN_ENDPOINT = "/auth/login";
    private static final String USER_ENDPOINT = "/auth/user";

    static {
        RestAssured.baseURI = BASE_URL;
    }

    /**
     * Создает пользователя через API
     */
    public static String createUser(User user) {
        Response response = given()
                .header("Content-Type", "application/json")
                .body(user)
                .when()
                .post(REGISTER_ENDPOINT);

        if (response.statusCode() == 200) {
            return response.jsonPath().getString("accessToken");
        }
        return null;
    }

    /**
     * Удаляет пользователя через API
     */
    public static boolean deleteUser(String accessToken) {
        if (accessToken == null) {
            return false;
        }

        Response response = given()
                .header("Authorization", accessToken)
                .when()
                .delete(USER_ENDPOINT);

        return response.statusCode() == 202;
    }

    /**
     * Выполняет вход пользователя через API
     */
    public static String loginUser(String email, String password) {
        User loginData = new User(email, password, null);

        Response response = given()
                .header("Content-Type", "application/json")
                .body(loginData)
                .when()
                .post(LOGIN_ENDPOINT);

        if (response.statusCode() == 200) {
            return response.jsonPath().getString("accessToken");
        }
        return null;
    }
}

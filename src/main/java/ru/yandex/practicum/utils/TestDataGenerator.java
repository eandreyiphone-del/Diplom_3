package ru.yandex.practicum.utils;


import net.datafaker.Faker;

/**
 * Утилитный класс для генерации тестовых данных с использованием DataFaker
 */
public class TestDataGenerator {

    private static final Faker faker = new Faker();

    /**
     * Генерирует случайный email
     */
    public static String generateRandomEmail() {
        return faker.internet().emailAddress();
    }

    /**
     * Генерирует случайное имя
     */
    public static String generateRandomName() {
        return faker.name().firstName();
    }

    /**
     * Генерирует случайный пароль заданной длины
     */
    public static String generateRandomPassword(int length) {
        return faker.internet().password(length, length, true, true, true);
    }

    /**
     * Генерирует валидный пароль (6+ символов)
     */
    public static String generateValidPassword() {
        return faker.internet().password(8, 20, true, true, true);
    }

    /**
     * Генерирует невалидный пароль (менее 6 символов)
     */
    public static String generateInvalidPassword() {
        return faker.internet().password(1, 5, true, true, true);
    }

    /**
     * Генерирует невалидный email для негативных тестов
     */
    public static String generateInvalidEmail() {
        return faker.lorem().word();
    }
}

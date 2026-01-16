package ru.yandex.practicum.tests;

import io.qameta.allure.*;
import org.junit.Test;
import ru.yandex.practicum.base.BaseTest;
import ru.yandex.practicum.pages.MainPage;

import static org.junit.Assert.assertTrue;

/**
 * Тесты функциональности раздела "Конструктор"
 */
@Epic("Stellar Burgers")
@Feature("Конструктор бургеров")
public class ConstructorTest extends BaseTest {

    @Test
    @Description("Переход к разделу 'Булки' работает корректно")
    @Severity(SeverityLevel.NORMAL)
    public void testNavigationToBunsSection() {
        MainPage mainPage = new MainPage(driver);

        // Сначала переходим в другой раздел, чтобы потом проверить переход к булкам
        mainPage.clickSaucesSection();

        // Теперь переходим к разделу "Булки"
        mainPage.clickBunsSection();

        // Проверяем, что раздел "Булки" активен
        assertTrue("Раздел 'Булки' должен быть активным после клика по нему",
                mainPage.isBunsSectionActive());
    }

    @Test
    @Description("Переход к разделу 'Соусы' работает корректно")
    @Severity(SeverityLevel.NORMAL)
    public void testNavigationToSaucesSection() {
        MainPage mainPage = new MainPage(driver);

        // Переходим к разделу "Соусы"
        mainPage.clickSaucesSection();

        // Проверяем, что раздел "Соусы" активен
        assertTrue("Раздел 'Соусы' должен быть активным после клика по нему",
                mainPage.isSaucesSectionActive());
    }

    @Test
    @Description("Переход к разделу 'Начинки' работает корректно")
    @Severity(SeverityLevel.NORMAL)
    public void testNavigationToFillingsSection() {
        MainPage mainPage = new MainPage(driver);

        // Переходим к разделу "Начинки"
        mainPage.clickFillingsSection();

        // Проверяем, что раздел "Начинки" активен
        assertTrue("Раздел 'Начинки' должен быть активным после клика по нему",
                mainPage.isFillingsSectionActive());
    }

    @Test
    @Description("Переключение между всеми разделами конструктора работает корректно")
    @Severity(SeverityLevel.NORMAL)
    public void testSectionSwitching() {
        MainPage mainPage = new MainPage(driver);

        // Переходим к разделу "Соусы"
        mainPage.clickSaucesSection();
        assertTrue("Раздел 'Соусы' должен быть активным",
                mainPage.isSaucesSectionActive());

        // Переходим к разделу "Начинки"
        mainPage.clickFillingsSection();
        assertTrue("Раздел 'Начинки' должен быть активным",
                mainPage.isFillingsSectionActive());

        // Возвращаемся к разделу "Булки"
        mainPage.clickBunsSection();
        assertTrue("Раздел 'Булки' должен быть активным",
                mainPage.isBunsSectionActive());
    }
}

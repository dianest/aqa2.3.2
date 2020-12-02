package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class BankTest {

    @BeforeAll
    static void setUpAll() {
        UserGenerator.registerActiveUser();
        UserGenerator.registerBlockedUser();
    }

    @Test
    public void activeUserTest() {
        testUserLogin(
                UserGenerator.getActiveUser(),
                () -> {
                    final SelenideElement account = $(By.cssSelector("h2"));
                    account.waitUntil(Condition.visible, 5000).
                            shouldHave(text("Личный кабинет"));
                });
    }

    @Test
    public void blockedUserTest() {
        testUserLogin(
                UserGenerator.getBlockedUser(),
                () -> {
                    final SelenideElement account = $("[data-test-id=error-notification]");
                    account.waitUntil(Condition.visible, 5000).
                            shouldHave(text("Ошибка! Пользователь заблокирован"));
                });
    }

    @Test
    public void activeNotExistingUserTest() {
        testUserLogin(
                UserGenerator.getActiveNotExistingUser(),
                () -> {
                    final SelenideElement account = $("[data-test-id=error-notification]");
                    account.waitUntil(Condition.visible, 5000).
                            shouldHave(text("Ошибка! Неверно указан логин или пароль"));
                });
    }

    @Test
    public void blockedNotExistingUserTest() {
        testUserLogin(
                UserGenerator.getBlockedNotExistingUser(),
                () -> {
                    final SelenideElement account = $("[data-test-id=error-notification]");
                    account.waitUntil(Condition.visible, 5000).
                            shouldHave(text("Ошибка! Неверно указан логин или пароль"));
                });
    }

    @Test
    public void activeWrongNameUserTest() {
        testUserLogin(
                UserGenerator.getActiveWrongNameUser(),
                () -> {
                    final SelenideElement account = $("[data-test-id=error-notification]");
                    account.waitUntil(Condition.visible, 5000).
                            shouldHave(text("Ошибка! Неверно указан логин или пароль"));
                });
    }

    @Test
    public void activeWrongPasswordUserTest() {
        testUserLogin(
                UserGenerator.getActiveWrongPasswordUser(),
                () -> {
                    final SelenideElement account = $("[data-test-id=error-notification]");
                    account.waitUntil(Condition.visible, 5000).
                            shouldHave(text("Ошибка! Неверно указан логин или пароль"));
                });
    }

    @Test
    public void activeWrongNameAndPasswordUserTest() {
        testUserLogin(
                UserGenerator.getActiveWrongNameAndPasswordUser(),
                () -> {
                    final SelenideElement account = $("[data-test-id=error-notification]");
                    account.waitUntil(Condition.visible, 5000).
                            shouldHave(text("Ошибка! Неверно указан логин или пароль"));
                });
    }

    @Test
    public void blockedWrongNameUserTest() {
        testUserLogin(
                UserGenerator.getBlockedWrongNameUser(),
                () -> {
                    final SelenideElement account = $("[data-test-id=error-notification]");
                    account.waitUntil(Condition.visible, 5000).
                            shouldHave(text("Ошибка! Неверно указан логин или пароль"));
                });
    }

    @Test
    public void blockedWrongPasswordUserTest() {
        testUserLogin(
                UserGenerator.getBlockedWrongPasswordUser(),
                () -> {
                    final SelenideElement account = $("[data-test-id=error-notification]");
                    account.waitUntil(Condition.visible, 5000).
                            shouldHave(text("Ошибка! Неверно указан логин или пароль"));
                });
    }

    @Test
    public void blockedWrongNameAndPasswordUserTest() {
        testUserLogin(
                UserGenerator.getBlockedWrongNameAndPasswordUser(),
                () -> {
                    final SelenideElement account = $("[data-test-id=error-notification]");
                    account.waitUntil(Condition.visible, 5000).
                            shouldHave(text("Ошибка! Неверно указан логин или пароль"));
                });

    }

    private void submitForm(User user) {
        open("http://localhost:9999/");
        SelenideElement form = $(By.tagName("FORM"));

        form.$("[data-test-id=login] input").setValue(user.getLogin());
        form.$("[data-test-id=password] input").setValue(user.getPassword());
        form.$("[data-test-id=action-login]").click();
    }

    private void testUserLogin(User user, Runnable action) {
        submitForm(user);

        action.run();
    }
}

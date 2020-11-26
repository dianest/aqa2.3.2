package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static io.restassured.RestAssured.given;

public class BankTest {
    private static final RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    @BeforeAll
    static void setUpAll() {
        final User activeUser = UserGenerator.getUser(UserGenerator.UserType.ACTIVE);
        final User blockedUser = UserGenerator.getUser(UserGenerator.UserType.BLOCKED);

        given()
                .spec(requestSpec)
                .body(activeUser)
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);

        given()
                .spec(requestSpec)
                .body(blockedUser)
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
    }

    private void submitForm(User user) {
        open("http://localhost:9999/");
        SelenideElement form = $(By.tagName("FORM"));

        form.$("[data-test-id=login] input").setValue(user.getLogin());
        form.$("[data-test-id=password] input").setValue(user.getPassword());
        form.$("[data-test-id=action-login]").click();
    }

    @Test
    public void activeUserTest() {
        final User user = UserGenerator.getUser(UserGenerator.UserType.ACTIVE);
        submitForm(user);

        final SelenideElement account = $(By.cssSelector("h2"));
        account.waitUntil(Condition.visible, 5000).
                shouldHave(text("Личный кабинет"));

    }

    @Test
    public void blockedUserTest() {
        final User user = UserGenerator.getUser(UserGenerator.UserType.BLOCKED);
        submitForm(user);

        final SelenideElement account = $("[data-test-id=error-notification]");
        account.waitUntil(Condition.visible, 5000).
                shouldHave(text("Ошибка! Пользователь заблокирован"));

    }

    @Test
    public void activeNotExistingUserTest() {
        final User user = UserGenerator.getUser(UserGenerator.UserType.ACTIVE_NOT_EXISTING);
        submitForm(user);

        final SelenideElement account = $("[data-test-id=error-notification]");
        account.waitUntil(Condition.visible, 5000).
                shouldHave(text("Ошибка! Неверно указан логин или пароль"));

    }

    @Test
    public void blockedNotExistingUserTest() {
        final User user = UserGenerator.getUser(UserGenerator.UserType.BLOCKED_NOT_EXISTING);
        submitForm(user);

        final SelenideElement account = $("[data-test-id=error-notification]");
        account.waitUntil(Condition.visible, 5000).
                shouldHave(text("Ошибка! Неверно указан логин или пароль"));

    }

    @Test
    public void activeWrongNameUserTest() {
        final User user = UserGenerator.getUser(UserGenerator.UserType.ACTIVE_WRONG_NAME);
        submitForm(user);

        final SelenideElement account = $("[data-test-id=error-notification]");
        account.waitUntil(Condition.visible, 5000).
                shouldHave(text("Ошибка! Неверно указан логин или пароль"));

    }

    @Test
    public void activeWrongPasswordUserTest() {
        final User user = UserGenerator.getUser(UserGenerator.UserType.ACTIVE_WRONG_PASSWORD);
        submitForm(user);

        final SelenideElement account = $("[data-test-id=error-notification]");
        account.waitUntil(Condition.visible, 5000).
                shouldHave(text("Ошибка! Неверно указан логин или пароль"));

    }

    @Test
    public void activeWrongNameAndPasswordUserTest() {
        final User user = UserGenerator.getUser(UserGenerator.UserType.ACTIVE_WRONG_NAME_AND_PASSWORD);
        submitForm(user);

        final SelenideElement account = $("[data-test-id=error-notification]");
        account.waitUntil(Condition.visible, 5000).
                shouldHave(text("Ошибка! Неверно указан логин или пароль"));

    }

    @Test
    public void blockedWrongNameUserTest() {
        final User user = UserGenerator.getUser(UserGenerator.UserType.BLOCKED_WRONG_NAME);
        submitForm(user);

        final SelenideElement account = $("[data-test-id=error-notification]");
        account.waitUntil(Condition.visible, 5000).
                shouldHave(text("Ошибка! Неверно указан логин или пароль"));

    }

    @Test
    public void blockedWrongPasswordUserTest() {
        final User user = UserGenerator.getUser(UserGenerator.UserType.BLOCKED_WRONG_PASSWORD);
        submitForm(user);

        final SelenideElement account = $("[data-test-id=error-notification]");
        account.waitUntil(Condition.visible, 5000).
                shouldHave(text("Ошибка! Неверно указан логин или пароль"));

    }

    @Test
    public void blockedWrongNameAndPasswordUserTest() {
        final User user = UserGenerator.getUser(UserGenerator.UserType.BLOCKED_WRONG_NAME_AND_PASSWORD);
        submitForm(user);

        final SelenideElement account = $("[data-test-id=error-notification-missing]");
        account.waitUntil(Condition.visible, 5000).
                shouldHave(text("Ошибка! Неверно указан логин или пароль"));

    }
}

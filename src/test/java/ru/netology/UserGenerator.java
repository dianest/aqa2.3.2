package ru.netology;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class UserGenerator {
    private static final RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    private static final Map<UserType, User> users = new HashMap<>();
    private static final Faker faker = new Faker();

    static {
        final User activeUser = generateUser();
        users.put(UserType.ACTIVE, activeUser);

        final User blockedUser = generateUser();
        blockedUser.setStatus("blocked");
        users.put(UserType.BLOCKED, blockedUser);

        final User activeNotExistingUser = generateUser();
        users.put(UserType.ACTIVE_NOT_EXISTING, activeNotExistingUser);

        final User blockedNotExistingUser = generateUser();
        blockedNotExistingUser.setStatus("blocked");
        users.put(UserType.BLOCKED_NOT_EXISTING, blockedNotExistingUser);

        final User activeWrongNameUser = activeUser.copy();
        activeWrongNameUser.setLogin(faker.name().username());
        users.put(UserType.ACTIVE_WRONG_NAME, activeWrongNameUser);

        final User activeWrongPasswordUser = activeUser.copy();
        activeWrongPasswordUser.setPassword(faker.internet().password());
        users.put(UserType.ACTIVE_WRONG_PASSWORD, activeWrongPasswordUser);

        final User activeWrongNameAndPasswordUser = activeUser.copy();
        activeWrongNameAndPasswordUser.setLogin(faker.name().username());
        activeWrongNameAndPasswordUser.setPassword(faker.internet().password());
        users.put(UserType.ACTIVE_WRONG_NAME_AND_PASSWORD, activeWrongNameAndPasswordUser);

        final User blockedWrongNameUser = blockedUser.copy();
        blockedWrongNameUser.setLogin(faker.name().username());
        users.put(UserType.BLOCKED_WRONG_NAME, blockedWrongNameUser);

        final User blockedWrongPasswordUser = blockedUser.copy();
        blockedWrongPasswordUser.setPassword(faker.internet().password());
        users.put(UserType.BLOCKED_WRONG_PASSWORD, blockedWrongPasswordUser);

        final User blockedWrongNameAndPasswordUser = blockedUser.copy();
        blockedWrongNameAndPasswordUser.setLogin(faker.name().username());
        blockedWrongNameAndPasswordUser.setPassword(faker.internet().password());
        users.put(UserType.BLOCKED_WRONG_NAME_AND_PASSWORD, blockedWrongNameAndPasswordUser);
    }

    private UserGenerator() {
    }

    public static void registerActiveUser() {
        registerUser(getUser(UserType.ACTIVE));
    }

    public static void registerBlockedUser() {
        registerUser(getUser(UserType.BLOCKED));
    }

    public static User getActiveUser() {
        return getUser(UserType.ACTIVE);
    }

    public static User getBlockedUser() {
        return getUser(UserType.BLOCKED);
    }

    public static User getActiveNotExistingUser() {
        return getUser(UserType.ACTIVE_NOT_EXISTING);
    }

    public static User getBlockedNotExistingUser() {
        return getUser(UserType.BLOCKED_NOT_EXISTING);
    }

    public static User getActiveWrongNameUser() {
        return getUser(UserType.ACTIVE_WRONG_NAME);
    }

    public static User getActiveWrongPasswordUser() {
        return getUser(UserType.ACTIVE_WRONG_PASSWORD);
    }

    public static User getActiveWrongNameAndPasswordUser() {
        return getUser(UserType.ACTIVE_WRONG_NAME_AND_PASSWORD);
    }

    public static User getBlockedWrongNameUser() {
        return getUser(UserType.BLOCKED_WRONG_NAME);
    }

    public static User getBlockedWrongPasswordUser() {
        return getUser(UserType.BLOCKED_WRONG_PASSWORD);
    }

    public static User getBlockedWrongNameAndPasswordUser() {
        return getUser(UserType.BLOCKED_WRONG_NAME_AND_PASSWORD);
    }

    private static User getUser(UserType userType) {
        return users.get(userType);
    }

    private static User generateUser() {
        return new User(
                faker.name().username(),
                faker.internet().password(),
                "active"
        );
    }

    private static void registerUser(User user) {
        given()
                .spec(requestSpec)
                .body(user)
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
    }

    public enum UserType {
        ACTIVE,
        BLOCKED,
        ACTIVE_NOT_EXISTING,
        BLOCKED_NOT_EXISTING,
        ACTIVE_WRONG_NAME,
        ACTIVE_WRONG_PASSWORD,
        ACTIVE_WRONG_NAME_AND_PASSWORD,
        BLOCKED_WRONG_NAME,
        BLOCKED_WRONG_PASSWORD,
        BLOCKED_WRONG_NAME_AND_PASSWORD,
    }
}

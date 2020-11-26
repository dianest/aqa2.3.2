package ru.netology;

import com.github.javafaker.Faker;

import java.util.HashMap;
import java.util.Map;

public class UserGenerator {
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

    private UserGenerator() {}

    public static User getUser(UserType userType) {
        return users.get(userType);
    }

    private static User generateUser() {
        return new User(
                faker.name().username(),
                faker.internet().password(),
                "active"
        );
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

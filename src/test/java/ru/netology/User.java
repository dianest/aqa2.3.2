package ru.netology;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class User {
    private String login;
    private String password;
    private String status;

    public User copy() {
        return new User(login, password, status);
    }
}

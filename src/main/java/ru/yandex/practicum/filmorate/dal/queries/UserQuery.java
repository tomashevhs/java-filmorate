package ru.yandex.practicum.filmorate.dal.queries;

import lombok.Getter;

@Getter
public enum UserQuery {
    FIND_ALL_QUERY("SELECT * FROM users"),
    FIND_BY_ID_QUERY("SELECT * FROM users WHERE user_id = ?"),
    INSERT_QUERY("INSERT INTO users(user_email, user_name, user_login, user_birthday)" +
            " VALUES (?, ?, ?, ?)"),
    UPDATE_USER_QUERY("UPDATE users SET user_email = ?, user_name = ?, user_login = ?," +
            " user_birthday = ?  WHERE user_id = ?");
    private final String query;

    UserQuery(String query) {
        this.query = query;
    }
}

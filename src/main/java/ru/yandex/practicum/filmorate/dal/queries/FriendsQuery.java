package ru.yandex.practicum.filmorate.dal.queries;

import lombok.Getter;

@Getter
public enum FriendsQuery {
    FIND_ALL_QUERY("SELECT * FROM friendship"),
    FIND_BY_ID_QUERY("SELECT * FROM users WHERE user_id IN (SELECT friend_id FROM " +
            "friendship WHERE user_id = ?)"),
    FIND_COMMON_ID_QUERY("SELECT * FROM users WHERE user_id IN" +
            " ((SELECT friend_id FROM friendship WHERE user_id = ?) " +
            "INTERSECT (SELECT friend_id FROM friendship WHERE user_id = ?))"),
    INSERT_FRIEND_QUERY("INSERT INTO friendship(user_id, friend_id) VALUES (?,?)"),
    DELETE_USER_QUERY(" DELETE FROM friendship WHERE user_id = ?");
    private final String query;

    FriendsQuery(String query) {
        this.query = query;
    }

}

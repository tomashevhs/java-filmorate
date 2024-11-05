package ru.yandex.practicum.filmorate.dal.queries;

import lombok.Getter;

@Getter
public enum LikesQuery {
    INSERT_QUERY_OF_FILM("INSERT INTO film_like (film_id, user_id) VALUES (?, ?)"),
    DELETE_QUERY_OF_FILM("DELETE FROM film_like WHERE film_id = ? AND user_id = ?");
    private final String query;

    LikesQuery(String query) {
        this.query = query;
    }
}

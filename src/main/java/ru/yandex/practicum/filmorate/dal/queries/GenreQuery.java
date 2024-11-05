package ru.yandex.practicum.filmorate.dal.queries;

import lombok.Getter;

@Getter
public enum GenreQuery {
    QUERY_FOR_ALL_GENRES("SELECT * FROM genre"),
    INSERT_QUERY("INSERT INTO film_genres (film_id, genre_id) VALUES (?, ?) "),
    QUERY_FOR_GENRE_BY_ID ("SELECT * FROM genre WHERE genre_id = ?"),
    DELETE_ALL_FROM_FILM_QUERY ("DELETE FROM film_genres WHERE film_id = ?");
    private final String query;

    GenreQuery(String query) {
        this.query = query;
    }

}

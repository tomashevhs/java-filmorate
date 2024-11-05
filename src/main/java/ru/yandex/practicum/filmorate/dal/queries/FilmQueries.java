package ru.yandex.practicum.filmorate.dal.queries;

import lombok.Getter;

@Getter
public enum FilmQueries {
    QUERY_FOR_ALL_FILMS("SELECT * FROM film f, " +
            "rating_MPA rm WHERE f.film_rating_MPA_id = rm.rating_MPA_id"),

    INSERT_QUERY("INSERT INTO film (film_name, film_description, film_release_date," +
            " film_duration, film_rating_MPA_id) VALUES (?, ?, ?, ?, ?)"),

    UPDATE_QUERY("UPDATE film SET film_name = ?, film_description = ?, film_release_date = ?," +
            " film_duration = ?, film_rating_MPA_id = ? WHERE film_id = ?"),

    QUERY_ALL_GENRES_FILMS("SELECT * FROM film_genres fg, genre g WHERE fg.genre_id = g.genre_id"),

    QUERY_TOP_FILMS("SELECT * FROM film f LEFT JOIN rating_MPA m " +
            "ON f.film_rating_MPA_id = m.rating_MPA_id LEFT JOIN (SELECT film_id, COUNT(film_id) AS LIKES FROM film_like " +
            "GROUP BY film_id) fl ON f.film_id = fl.film_id ORDER BY LIKES DESC LIMIT ?"),

    QUERY_FOR_FILM_BY_ID("SELECT * FROM film f, rating_MPA m " +
            "WHERE f.film_rating_MPA_id = m.rating_MPA_id AND f.film_id = ?"),

    QUERY_GENRES_BY_FILM("SELECT * FROM genre g, film_genres fg " +
            "WHERE g.genre_id = fg.genre_id AND fg.film_id = ?");
    private final String query;

    FilmQueries(String query) {
        this.query = query;
    }

}

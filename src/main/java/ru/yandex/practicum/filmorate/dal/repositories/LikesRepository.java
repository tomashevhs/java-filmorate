package ru.yandex.practicum.filmorate.dal.repositories;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dal.BaseRepository;
import ru.yandex.practicum.filmorate.model.Film;

@Repository
public class LikesRepository extends BaseRepository<Film> {
    private static final String INSERT_QUERY_OF_FILM = "INSERT INTO film_like (film_id, user_id) VALUES (?, ?)";
    private static final String DELETE_QUERY_OF_FILM = "DELETE FROM film_like WHERE film_id = ? AND user_id = ?";

    public LikesRepository(JdbcTemplate jdbc, RowMapper<Film> mapper) {
        super(jdbc, mapper);
    }

    public void addLike(Integer filmId, Integer userId) {
        update(INSERT_QUERY_OF_FILM, filmId, userId);
    }

    public void deleteLike(Integer filmId, Integer userId) {
        update(DELETE_QUERY_OF_FILM, filmId, userId);
    }
}

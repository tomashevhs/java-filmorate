package ru.yandex.practicum.filmorate.dal.repositories;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dal.BaseRepository;
import ru.yandex.practicum.filmorate.dal.queries.LikesQuery;
import ru.yandex.practicum.filmorate.model.Film;

@Repository
public class LikesRepository extends BaseRepository<Film> {
    public LikesRepository(JdbcTemplate jdbc, RowMapper<Film> mapper) {
        super(jdbc, mapper);
    }

    public void addLike(Integer filmId, Integer userId) {
        update(LikesQuery.INSERT_QUERY_OF_FILM.getQuery(), filmId, userId);
    }

    public void deleteLike(Integer filmId, Integer userId) {
        update(LikesQuery.DELETE_QUERY_OF_FILM.getQuery(), filmId, userId);
    }
}

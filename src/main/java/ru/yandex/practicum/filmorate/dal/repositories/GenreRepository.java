package ru.yandex.practicum.filmorate.dal.repositories;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dal.BaseRepository;
import ru.yandex.practicum.filmorate.dal.queries.GenreQuery;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Genres;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class GenreRepository extends BaseRepository<Genres> {
    public GenreRepository(JdbcTemplate jdbc, RowMapper<Genres> mapper) {
        super(jdbc, mapper);
    }

    public List<Genres> getAllGenres() {
        return findMany(GenreQuery.QUERY_FOR_ALL_GENRES.getQuery());
    }

    public Genres getGenreById(Integer id) {
        if (findOne(GenreQuery.QUERY_FOR_GENRE_BY_ID.getQuery(), id) != null) {
            return findOne(GenreQuery.QUERY_FOR_GENRE_BY_ID.getQuery(), id);
        } else {
            throw new NotFoundException("Такого жанра не существует");
        }
    }

    public void addGenres(Integer filmId, List<Integer> genresIds) {
        batchUpdateBase(GenreQuery.INSERT_QUERY.getQuery(), new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setInt(1, filmId);
                ps.setInt(2, genresIds.get(i));
            }

            @Override
            public int getBatchSize() {
                return genresIds.size();
            }
        });
    }

    public void deleteGenres(Integer filmId) {
        update(GenreQuery.DELETE_ALL_FROM_FILM_QUERY.getQuery(), filmId);
    }
}

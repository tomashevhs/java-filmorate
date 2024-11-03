package ru.yandex.practicum.filmorate.dal.repositories;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dal.BaseRepository;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Genres;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

@Repository
public class GenreRepository extends BaseRepository<Genres> {
    private static final String QUERY_FOR_ALL_GENRES = "SELECT * FROM genre";
    private static final String INSERT_QUERY = "INSERT INTO film_genres (film_id, genre_id) VALUES (?, ?) ";
    private static final String QUERY_FOR_GENRE_BY_ID = "SELECT * FROM genre WHERE genre_id = ?";
    private static final String DELETE_ALL_FROM_FILM_QUERY = "DELETE FROM film_genres WHERE film_id = ?";

    public GenreRepository(JdbcTemplate jdbc, RowMapper<Genres> mapper) {
        super(jdbc, mapper);
    }

    public Collection<Genres> getAllGenres() {
        return findMany(QUERY_FOR_ALL_GENRES);
    }

    public Genres getGenreById(Integer id) {
        if (findOne(QUERY_FOR_GENRE_BY_ID, id) != null) {
            return findOne(QUERY_FOR_GENRE_BY_ID, id);
        } else {
            throw new NotFoundException("Такого жанра не существует");
        }
    }

    public void addGenres(Integer filmId, List<Integer> genresIds) {
        batchUpdateBase(INSERT_QUERY, new BatchPreparedStatementSetter() {

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
        update(DELETE_ALL_FROM_FILM_QUERY, filmId);
    }
}

package ru.yandex.practicum.filmorate.dal.repositories;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dal.BaseRepository;
import ru.yandex.practicum.filmorate.dal.queries.FilmQueries;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genres;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.sql.ResultSet;
import java.util.*;

@Repository
public class FilmDbStorage extends BaseRepository<Film> implements FilmStorage {
    public FilmDbStorage(JdbcTemplate jdbc, RowMapper<Film> mapper) {
        super(jdbc, mapper);
    }

    @Override
    public List<Film> findAll() {
        List<Film> films = findMany(FilmQueries.QUERY_FOR_ALL_FILMS.getQuery());
        Map<Integer, Set<Genres>> genres = getAllGenres();
        for (Film film : films) {
            if (genres.containsKey(film.getId())) {
                film.setGenres(genres.get(film.getId()));
            }
        }
        return films;
    }

    @Override
    public Film createFilm(Film film) {
        Integer id = insert(
                FilmQueries.INSERT_QUERY.getQuery(),
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getMpa().getId()
        );
        film.setId(id);
        return film;
    }

    @Override
    public Film update(Film newFilm) {
        update(
                FilmQueries.UPDATE_QUERY.getQuery(),
                newFilm.getName(),
                newFilm.getDescription(),
                newFilm.getReleaseDate(),
                newFilm.getDuration(),
                newFilm.getMpa().getId(),
                newFilm.getId()
        );
        return newFilm;
    }

    @Override
    public Film getFilm(Integer filmId) {
        Film film = findOne(FilmQueries.QUERY_FOR_FILM_BY_ID.getQuery(), filmId);
        film.setGenres(getGenresByFilm(filmId));
        return film;
    }

    private Map<Integer, Set<Genres>> getAllGenres() {
        Map<Integer, Set<Genres>> genres = new HashMap<>();
        return jdbc.query(FilmQueries.QUERY_ALL_GENRES_FILMS.getQuery(), (ResultSet rs) -> {
            while (rs.next()) {
                Integer filmId = rs.getInt("film_id");
                Integer genreId = rs.getInt("genre_id");
                String genreName = rs.getString("genre_name");
                genres.computeIfAbsent(filmId, k -> new HashSet<>()).add(new Genres(genreId, genreName));
            }
            return genres;
        });
    }

    public List<Film> findTopOfFilms(int count) {
        List<Film> films = findMany(FilmQueries.QUERY_TOP_FILMS.getQuery(), count);
        Map<Integer, Set<Genres>> genres = getAllGenres();
        for (Film film : films) {
            if (genres.containsKey(film.getId())) {
                film.setGenres(genres.get(film.getId()));
            }
        }
        return films;
    }

    private Set<Genres> getGenresByFilm(Integer filmId) {
        return jdbc.query(FilmQueries.QUERY_GENRES_BY_FILM.getQuery(), (ResultSet rs) -> {
            Set<Genres> genres = new HashSet<>();
            while (rs.next()) {
                Integer genreId = rs.getInt("genre_id");
                String genreName = rs.getString("genre_name");
                genres.add(new Genres(genreId, genreName));
            }
            return genres;
        }, filmId);
    }
}

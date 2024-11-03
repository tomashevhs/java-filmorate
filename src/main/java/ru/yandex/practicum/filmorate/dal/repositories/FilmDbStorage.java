package ru.yandex.practicum.filmorate.dal.repositories;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dal.BaseRepository;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genres;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.sql.ResultSet;
import java.util.*;

@Repository
public class FilmDbStorage extends BaseRepository<Film> implements FilmStorage {
    private static final String QUERY_FOR_ALL_FILMS = "SELECT * FROM film f, " +
            "rating_MPA m WHERE f.film_rating_MPA_id = m.rating_MPA_id";

    private static final String INSERT_QUERY = "INSERT INTO film (film_name, film_description, film_release_date," +
            " film_duration, film_rating_MPA_id) " + "VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE_QUERY = "UPDATE film SET film_name = ?, film_description = ?, film_release_date = ?," +
            " film_duration = ?, film_rating_MPA_id = ? WHERE film_id = ?";
    private static final String QUERY_ALL_GENRES_FILMS = "SELECT * FROM film_genres fg, " +
            "genre g WHERE fg.genre_id = g.genre_id";
    private static final String QUERY_TOP_FILMS = "SELECT * FROM film f LEFT JOIN rating_MPA m " +
            "ON f.film_rating_MPA_id = m.rating_MPA_id LEFT JOIN (SELECT film_id, COUNT(film_id) AS LIKES FROM film_like " +
            "GROUP BY film_id) fl ON f.film_id = fl.film_id ORDER BY LIKES DESC LIMIT ?";
    private static final String QUERY_FOR_FILM_BY_ID = "SELECT * FROM film f, rating_MPA m " +
            "WHERE f.film_rating_MPA_id = m.rating_MPA_id AND f.film_id = ?";
    private static final String QUERY_GENRES_BY_FILM = "SELECT * FROM genre g, film_genres fg " +
            "WHERE g.genre_id = fg.genre_id AND fg.film_id = ?";

    public FilmDbStorage(JdbcTemplate jdbc, RowMapper<Film> mapper) {
        super(jdbc, mapper);
    }

    @Override
    public Collection<Film> findAll() {
        Collection<Film> films = findMany(QUERY_FOR_ALL_FILMS);
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
                INSERT_QUERY,
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
                UPDATE_QUERY,
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
        Film film = findOne(QUERY_FOR_FILM_BY_ID, filmId);
        film.setGenres(getGenresByFilm(filmId));
        return film;
    }

    private Map<Integer, Set<Genres>> getAllGenres() {
        Map<Integer, Set<Genres>> genres = new HashMap<>();
        return jdbc.query(QUERY_ALL_GENRES_FILMS, (ResultSet rs) -> {
            while (rs.next()) {
                Integer filmId = rs.getInt("FILM_ID");
                Integer genreId = rs.getInt("GENRE_ID");
                String genreName = rs.getString("GENRE_NAME");
                genres.computeIfAbsent(filmId, k -> new HashSet<>()).add(new Genres(genreId, genreName));
            }
            return genres;
        });
    }

    public Collection<Film> findTopOfFilms(int count) {
        Collection<Film> films = findMany(QUERY_TOP_FILMS, count);
        Map<Integer, Set<Genres>> genres = getAllGenres();
        for (Film film : films) {
            if (genres.containsKey(film.getId())) {
                film.setGenres(genres.get(film.getId()));
            }
        }
        return films;
    }

    private Set<Genres> getGenresByFilm(Integer filmId) {
        return jdbc.query(QUERY_GENRES_BY_FILM, (ResultSet rs) -> {
            Set<Genres> genres = new HashSet<>();
            while (rs.next()) {
                Integer genreId = rs.getInt("GENRE_ID");
                String genreName = rs.getString("GENRE_NAME");
                genres.add(new Genres(genreId, genreName));
            }
            return genres;
        }, filmId);
    }
}

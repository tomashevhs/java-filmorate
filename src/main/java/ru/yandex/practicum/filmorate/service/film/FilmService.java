package ru.yandex.practicum.filmorate.service.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.List;

public interface FilmService {
    Collection<Film> findAll();
    Film createFilm(Film film);

    Film update(Film newFilm);

    Film getFilm(Long filmId);
    void addLike(Long filmId, Long userId);

    void deleteLike(Long filmId, Long userId);

    List<Film> findTopFilms(Long count);
}

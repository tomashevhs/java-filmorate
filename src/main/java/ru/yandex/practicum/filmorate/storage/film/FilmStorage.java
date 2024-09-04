package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

public interface FilmStorage {
    Collection<Film> findAll();

    Film createFilm(Film film);

    Film update(Film newFilm);

    Film getFilm(Long filmId);
}

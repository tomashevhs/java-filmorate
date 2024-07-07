package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;

class FilmControllerTest {
    private final LocalDate movieBirthday = LocalDate.of(1895, 12, 28);
    FilmController filmController = new FilmController();

    @Test
    void create() {
        Film film = new Film();
        film.setReleaseDate(LocalDate.of(1879, 12, 1));
        assertThrows(ValidationException.class, () -> filmController.create(film), "Дата релиза не может раньше 24 декабря 1895 года");
    }
}
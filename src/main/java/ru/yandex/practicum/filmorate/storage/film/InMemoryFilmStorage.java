package ru.yandex.practicum.filmorate.storage.film;

import jakarta.validation.Valid;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final LocalDate movieBirthday = LocalDate.of(1895, 12, 28);
    private final Map<Long, Film> films = new HashMap<>();

    public Collection<Film> findAll() {
        return films.values();
    }

    public Film createFilm(@Valid @RequestBody Film film) {
        // проверяем выполнение необходимых условий

        if (film.getReleaseDate().isBefore(movieBirthday)) {
            throw new ValidationException("Дата релиза не может раньше 24 декабря 1895 года");
        }

        film.setId(getNextId());
        films.put(film.getId(), film);
        return film;
    }

    public Film update(@Valid @RequestBody Film newFilm) {
        // проверяем необходимые условия
        if (newFilm.getId() == null) {
            throw new ValidationException("Id должен быть указан");
        }
        if (films.containsKey(newFilm.getId())) {
            Film oldFilm = films.get(newFilm.getId());

            if (newFilm.getReleaseDate().isBefore(movieBirthday)) {
                throw new ValidationException("Дата релиза не может раньше 24 декабря 1895 года");
            }

            oldFilm.setName(newFilm.getName());
            oldFilm.setDuration(newFilm.getDuration());
            oldFilm.setReleaseDate(newFilm.getReleaseDate());
            oldFilm.setDescription(newFilm.getDescription());
            return oldFilm;
        }
        throw new ValidationException("Фильм с id = " + newFilm.getId() + " не найден");
    }

    private long getNextId() {
        long currentMaxId = films.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }

    @Override
    public Film getFilm(Long filmId) {
        return films.get(filmId);
    }
}

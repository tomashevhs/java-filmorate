package ru.yandex.practicum.filmorate.service.film;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.*;

@Service
@AllArgsConstructor
@Slf4j
public class InMemoryFilmService implements FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    @Override
    public Collection<Film> findAll() {
        return filmStorage.findAll();
    }

    @Override
    public Film createFilm(Film film) {
        return filmStorage.createFilm(film);
    }

    @Override
    public Film update(Film newFilm) {
        return filmStorage.update(newFilm);
    }

    @Override
    public Film getFilm(Long filmId) {
        return filmStorage.getFilm(filmId);
    }

    @Override
    public void addLike(Long filmId, Long userId) {
        if (filmStorage.getFilm(filmId) != null && userStorage.getUser(userId) != null) {
            Film film = filmStorage.getFilm(filmId);
            Set<Long> likes = film.getLikes();
            likes.add(userId);
            film.setLikes(likes);

        } else {
            throw new NotFoundException("Id не введен.");
        }
    }

    @Override
    public void deleteLike(Long filmId, Long userId) {
        if (filmStorage.getFilm(filmId) != null && userStorage.getUser(userId) != null) {
            Set<Long> likes = filmStorage.getFilm(filmId).getLikes();
            likes.remove(userId);
            filmStorage.getFilm(filmId).setLikes(likes);
        } else {
            throw new NotFoundException("Id не введен.");
        }
    }

    @Override
    public List<Film> findTopFilms(Long count) {
        if (count != null) {
            return filmStorage.findAll().stream()
                    .sorted(Comparator.comparingInt((Film film) -> Optional.ofNullable(film.getLikes()).map(Set::size)
                            .orElse(0)).reversed())
                    .limit(count)
                    .toList();
        } else {
            throw new RuntimeException("Count не введен.");
        }
    }
}

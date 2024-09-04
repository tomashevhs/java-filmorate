package ru.yandex.practicum.filmorate.service.film;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.*;

@Service
@AllArgsConstructor
@Slf4j
public class FilmServiceImpl implements FilmService {
    private final FilmStorage filmStorage;

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
    public void addLike(@Valid @RequestBody Long filmId, Long userId) {
        if (userId != null) {
            Film film = filmStorage.getFilm(filmId);
            if (film.getLikes() != null) {
                Set<Long> likes = film.getLikes();
                likes.add(userId);
                film.setLikes(likes);
            } else {
                film.setLikes(new HashSet<>());
                Set<Long> likes = film.getLikes();
                likes.add(userId);
                film.setLikes(likes);
            }
        }

    }

    @Override
    public void deleteLike(@Valid @RequestBody Long filmId, Long userId) {
        if (userId != null) {
            Set<Long> likes = filmStorage.getFilm(filmId).getLikes();
            likes.remove(userId);
            filmStorage.getFilm(filmId).setLikes(likes);
        }
    }

    @Override
    public List<Film> findTopFilms(@Valid @RequestBody Long count) {
        return filmStorage.findAll().stream()
                .sorted(Comparator.comparingInt((Film film) -> Optional.ofNullable(film.getLikes()).map(Set::size)
                        .orElse(0)).reversed())
                .limit(count)
                .toList();

    }
}

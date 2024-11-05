package ru.yandex.practicum.filmorate.service.film;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dal.repositories.FilmDbStorage;
import ru.yandex.practicum.filmorate.dal.repositories.GenreRepository;
import ru.yandex.practicum.filmorate.dal.repositories.LikesRepository;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genres;

import java.util.Collection;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class FilmService {
    private final FilmDbStorage filmDbStorage;
    private final GenreRepository genreRepository;
    private final LikesRepository likesRepository;

    public List<Film> findAll() {
        return filmDbStorage.findAll();
    }

    public Film createFilm(Film film) {
        Film createdFilm = filmDbStorage.createFilm(film);
        if (!createdFilm.getGenres().isEmpty()) {
            genreRepository.addGenres(createdFilm.getId(), createdFilm.getGenres()
                    .stream()
                    .map(Genres::getId)
                    .toList());
        }
        return createdFilm;
    }

    public Film updateFl(Film newFilm) {
        if (filmDbStorage.getFilm(newFilm.getId()) == null) {
            throw new NotFoundException("Не передан идентификатор фильма");
        }
        Film updatedFilm = filmDbStorage.update(newFilm);
        if (!updatedFilm.getGenres().isEmpty()) {
            genreRepository.deleteGenres(updatedFilm.getId());
            genreRepository.addGenres(updatedFilm.getId(), updatedFilm.getGenres()
                    .stream()
                    .map(Genres::getId)
                    .toList());
        }
        return updatedFilm;
    }

    public Film getFilmById(Integer filmId) {
        return filmDbStorage.getFilm(filmId);
    }

    public Film addLike(Integer filmId, Integer userId) {
        Film film = filmDbStorage.getFilm(filmId);
        film.getLikes().add(userId);
        likesRepository.addLike(filmId, userId);
        log.info("User {} liked film {}", userId, filmId);
        return film;
    }

    public Film deleteLike(Integer filmId, Integer userId) {
        Film film = filmDbStorage.getFilm(filmId);
        film.getLikes().remove(userId);
        likesRepository.deleteLike(filmId, userId);
        log.info("User {} unliked film {}", userId, filmId);
        return film;
    }

    public List<Film> getTopFilms(int count) {
        return filmDbStorage.findTopOfFilms(count);
    }
}

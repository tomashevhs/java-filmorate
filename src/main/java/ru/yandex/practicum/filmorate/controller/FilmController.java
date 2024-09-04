package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.film.FilmService;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/films")
@AllArgsConstructor
public class FilmController {
    private final FilmService filmService;

    @GetMapping
    public Collection<Film> findAll() {
        return filmService.findAll();
    }

    @PostMapping
    public Film createFilm(@Valid @RequestBody Film film) {
        return filmService.createFilm(film);
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film newFilm) {
        return filmService.update(newFilm);
    }

    @PutMapping("/{id}/like/{userId}")
    public void addLikes(@PathVariable("userId") Long userId, @PathVariable("friendId") Long friendId) {
        filmService.addLike(userId, friendId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void deleteLikes(@PathVariable("id") Long id, @PathVariable("friendId") Long friendId) {
        filmService.deleteLike(id, friendId);
    }

    @GetMapping("/popular?count={count}")
    public List<Film> getTopFilms(@PathVariable("count") @RequestParam(defaultValue = "10") Long count) {
        return filmService.findTopFilms(count);
    }

    @ExceptionHandler
    public Map<String, String> handleNegativeCount(final IllegalArgumentException e) {
        return Map.of("error", "Передан отрицательный параметр count.");
    }

    @ExceptionHandler
    public Map<String, String> handleNullCount(final NullPointerException e) {
        return Map.of("error", "Параметр count не указан.");
    }

    @ExceptionHandler
    public Map<String, String> handleNegativeRunTime(final RuntimeException e) {
        return Map.of("error", "Произошла ошибка!");
    }
}

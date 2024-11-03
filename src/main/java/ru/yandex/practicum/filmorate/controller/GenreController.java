package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Genres;
import ru.yandex.practicum.filmorate.service.genre.GenreService;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping("/genres")
public class GenreController {

    private final GenreService genreService;

    @GetMapping
    public Collection<Genres> getGenres() {
        return genreService.getAllGenres();
    }

    @GetMapping("/{id}")
    public Genres getGenreById(@PathVariable Integer id) {
        return genreService.getGenreById(id);
    }
}

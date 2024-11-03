package ru.yandex.practicum.filmorate.service.genre;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dal.repositories.GenreRepository;
import ru.yandex.practicum.filmorate.model.Genres;

import java.util.Collection;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class GenreService {
    private final GenreRepository genreRepository;

    public Collection<Genres> getAllGenres() {
        return genreRepository.getAllGenres();
    }

    public Genres getGenreById(Integer id) {
        return genreRepository.getGenreById(id);
    }

    public void updateGenre(Integer filmId, List<Integer> genresIds) {
        genreRepository.addGenres(filmId, genresIds);
    }

    public void deleteGenres(Integer filmId) {
        genreRepository.deleteGenres(filmId);
    }
}

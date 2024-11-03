package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.service.mpa.MpaService;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mpa")
public class MpaController {
    private final MpaService mpaService;

    @GetMapping
    public Collection<MPA> getAllMpa() {
        return mpaService.getAllMpa();
    }

    @GetMapping("/{id}")
    public MPA getMpaById(@PathVariable Integer id) {
        return mpaService.getMpaById(id);
    }
}

package ru.yandex.practicum.filmorate.service.mpa;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dal.repositories.MpaRepository;
import ru.yandex.practicum.filmorate.model.MPA;

import java.util.Collection;

@Slf4j
@Service
@RequiredArgsConstructor
public class MpaService {
    private final MpaRepository mpaRepository;

    public Collection<MPA> getAllMpa() {
        return mpaRepository.getAllMpa();
    }

    public MPA getMpaById(Integer mpaId) {
        return mpaRepository.getMpaById(mpaId);
    }
}

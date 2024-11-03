package ru.yandex.practicum.filmorate.dal.repositories;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dal.BaseRepository;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.MPA;

import java.util.Collection;

@Repository
public class MpaRepository extends BaseRepository<MPA> {
    private static final String QUERY_FOR_ALL_MPA = "SELECT * FROM rating_MPA";
    private static final String QUERY_FOR_BY_ID = "SELECT * FROM rating_MPA WHERE rating_MPA_id = ?";

    public MpaRepository(JdbcTemplate jdbc, RowMapper<MPA> mapper) {
        super(jdbc, mapper);
    }

    public Collection<MPA> getAllMpa() {
        return findMany(QUERY_FOR_ALL_MPA);
    }

    public MPA getMpaById(Integer mpaId) {
        if (findOne(QUERY_FOR_BY_ID, mpaId) != null) {
            return findOne(QUERY_FOR_BY_ID, mpaId);
        } else {
            throw new NotFoundException("Такого жанра не существует");
        }
    }
}

package ru.yandex.practicum.filmorate.dal.repositories;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dal.BaseRepository;
import ru.yandex.practicum.filmorate.dal.queries.MpaQuery;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.MPA;

import java.util.Collection;
import java.util.List;

@Repository
public class MpaRepository extends BaseRepository<MPA> {
    public MpaRepository(JdbcTemplate jdbc, RowMapper<MPA> mapper) {
        super(jdbc, mapper);
    }

    public List<MPA> getAllMpa() {
        return findMany(MpaQuery.QUERY_FOR_ALL_MPA.getQuery());
    }

    public MPA getMpaById(Integer mpaId) {
        if (findOne(MpaQuery.QUERY_FOR_BY_ID.getQuery(), mpaId) != null) {
            return findOne(MpaQuery.QUERY_FOR_BY_ID.getQuery(), mpaId);
        } else {
            throw new NotFoundException("Такого жанра не существует");
        }
    }
}

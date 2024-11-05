package ru.yandex.practicum.filmorate.dal.repositories;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dal.BaseRepository;
import ru.yandex.practicum.filmorate.dal.queries.FriendsQuery;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

@Repository
public class FriendsRepository extends BaseRepository<User> {
    public FriendsRepository(JdbcTemplate jdbc, RowMapper<User> mapper) {
        super(jdbc, mapper);
    }

    public List<User> findAll() {
        return findMany(FriendsQuery.FIND_ALL_QUERY.getQuery());
    }

    public List<User> findFriendsOfUser(Integer userId) {
        return findMany(FriendsQuery.FIND_BY_ID_QUERY.getQuery(), userId);
    }

    public void addFr(Integer userId, Integer friendId) {
        update(FriendsQuery.INSERT_FRIEND_QUERY.getQuery(), userId, friendId);
    }

    public void deleteFr(Integer userId, Integer friendId) {
        delete(FriendsQuery.DELETE_USER_QUERY.getQuery(), userId);
    }

    public List<User> findCommonFriends(Integer userId, Integer otherUserId) {
        return findMany(FriendsQuery.FIND_COMMON_ID_QUERY.getQuery(), userId, otherUserId);
    }
}

package ru.yandex.practicum.filmorate.dal.repositories;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dal.BaseRepository;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.List;

@Repository
public class FriendsRepository extends BaseRepository<User> {
    private static final String FIND_ALL_QUERY = "SELECT * FROM friendship";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM users WHERE user_id IN (SELECT friend_id FROM " +
            "friendship WHERE user_id = ?)";
    private static final String FIND_COMMON_ID_QUERY = "SELECT * FROM users WHERE user_id IN" +
            " ((SELECT friend_id FROM friendship WHERE user_id = ?) " +
            "INTERSECT (SELECT friend_id FROM friendship WHERE user_id = ?))";
    private static final String INSERT_FRIEND_QUERY = "INSERT INTO friendship(user_id, friend_id) VALUES (?,?)";
    private static final String DELETE_USER_QUERY = " DELETE FROM friendship WHERE user_id = ?";

    public FriendsRepository(JdbcTemplate jdbc, RowMapper<User> mapper) {
        super(jdbc, mapper);
    }

    public List<User> findAll() {
        return findMany(FIND_ALL_QUERY);
    }

    public List<User> findFriendsOfUser(Integer userId) {
        return findMany(FIND_BY_ID_QUERY, userId);
    }

    public void addFr(Integer userId, Integer friendId) {
        update(INSERT_FRIEND_QUERY, userId, friendId);
    }

    public void deleteFr(Integer userId, Integer friendId) {
        delete(DELETE_USER_QUERY, userId);
    }

    public Collection<User> findCommonFriends(Integer userId, Integer otherUserId) {
        return findMany(FIND_COMMON_ID_QUERY, userId, otherUserId);
    }
}

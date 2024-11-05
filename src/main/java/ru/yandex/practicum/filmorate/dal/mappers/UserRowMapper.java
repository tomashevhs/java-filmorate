package ru.yandex.practicum.filmorate.dal.mappers;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt("user_id"));
        user.setEmail(resultSet.getString("user_email"));
        user.setName(resultSet.getString("user_name"));
        user.setLogin(resultSet.getString("user_login"));
        user.setBirthday(resultSet.getDate("user_birthday").toLocalDate());
        return user;
    }
}
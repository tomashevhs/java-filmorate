package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {
    private final Map<Long, User> users = new HashMap<>();
    private final static Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @GetMapping
    public Collection<User> findAll() {
        return users.values();
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) {

        if (user.getName() == null) {
            user.setName(user.getLogin());
        }

        user.setId(getNextId());
        LOGGER.info("Для юзера {} установлен ID {}.", user.getName(), user.getId());
        users.put(user.getId(), user);
        LOGGER.info("Юзер с названием {} добавлен в коллекцию.", user.getName());
        return user;
    }

    @PutMapping
    public User update(@Valid @RequestBody User newUser) {
        if (users.containsKey(newUser.getId())) {
            User oldUser = users.get(newUser.getId());

            if (newUser.getName() == null) {
                newUser.setName(newUser.getLogin());
            }

            oldUser.setEmail(newUser.getEmail());
            oldUser.setName(newUser.getName());
            oldUser.setId(newUser.getId());
            oldUser.setLogin(newUser.getLogin());
            oldUser.setBirthday(newUser.getBirthday());
            return oldUser;
        } else {
            throw new ValidationException("Юзер с id = " + newUser.getId() + " не найден");
        }
    }

    private long getNextId() {
        long currentMaxId = users.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }
}

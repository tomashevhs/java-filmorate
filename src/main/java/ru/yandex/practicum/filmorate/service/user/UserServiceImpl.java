package ru.yandex.practicum.filmorate.service.user;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserStorage userStorage;

    @Override
    public User createUser(User user) {
        return userStorage.createUser(user);
    }

    @Override
    public User updateUser(User newUser) {
        return userStorage.updateUser(newUser);
    }

    @Override
    public Collection<User> findAll() {
        return userStorage.findAll();
    }

    @Override
    public User getUser(Long id) {
        return userStorage.getUser(id);
    }

    @Override
    public void addFriend(Long userId, Long friendId) {
        if (userStorage.getUser(userId) != null && userStorage.getUser(friendId) != null) {
            User user = userStorage.getUser(userId);
            User friend = userStorage.getUser(friendId);
            user.getFriends().add(friendId);
            friend.getFriends().add(userId);
        } else {
            throw new NotFoundException("Id не введен.");
        }
    }

    @Override
    public void deleteFriend(Long userId, Long friendId) {
        if (userStorage.getUser(userId) != null && userStorage.getUser(friendId) != null) {
            User user = userStorage.getUser(userId);
            User friend = userStorage.getUser(friendId);
            Set<Long> userFriends = user.getFriends();
            Set<Long> friendFriends = friend.getFriends();
            userFriends.remove(friendId);
            friendFriends.remove(userId);
            user.setFriends(userFriends);
            friend.setFriends(friendFriends);
        } else {
            throw new NotFoundException("Id не введен.");
        }
    }

    public Collection<User> findAllFriends(Long userId) {
        if (userStorage.getUser(userId) != null) {
            Set<Long> friends = userStorage.getUser(userId).getFriends();
            ArrayList<User> userFriends = new ArrayList<>();
            for (Long id : friends) {
                if (userStorage.getUser(id) != null) {
                    User friend = userStorage.getUser(id);
                    userFriends.add(friend);
                }
            }
            return userFriends;
        } else {
            throw new NotFoundException("Id не введен.");
        }
    }

    @Override
    public Collection<User> getCommonFriends(Long userId, Long otherId) {
        if (userId != null && otherId != null) {
            Set<Long> friends = userStorage.getUser(userId).getFriends();
            return userStorage.getUser(otherId).getFriends().stream()
                    .filter(friends::contains)
                    .map(userStorage::getUser)
                    .collect(Collectors.toList());
        } else {
            throw new ValidationException("Пользователи не являются друзьями.");
        }
    }
}

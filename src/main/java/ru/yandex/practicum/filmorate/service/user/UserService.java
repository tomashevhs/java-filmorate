package ru.yandex.practicum.filmorate.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dal.repositories.FriendsRepository;
import ru.yandex.practicum.filmorate.dal.repositories.UserDbStorage;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserDbStorage userDbStorage;
    private final FriendsRepository friendsRepository;

    public User createUser(User user) {
        return userDbStorage.createUser(user);
    }


    public User updateUser(User newUser) {
        return userDbStorage.updateUser(newUser);
    }


    public List<User> findAll() {
        return userDbStorage.findAll();
    }


    public User getUser(Integer id) {
        return userDbStorage.getUser(id);
    }


    public void addFriend(Integer userId, Integer friendId) {
        if (userDbStorage.getUser(friendId) == null || userDbStorage.getUser(userId) == null) {
            throw new NotFoundException("Пользователя не существует.");
        }
        friendsRepository.addFr(userId, friendId);
    }


    public void deleteFriend(Integer userId, Integer friendId) {
        if (userDbStorage.getUser(friendId) == null || userDbStorage.getUser(userId) == null) {
            throw new NotFoundException("Пользователя не существует.");
        }
        friendsRepository.deleteFr(userId, friendId);
    }

    public List<User> findAllFriends(Integer userId) {
        if (userDbStorage.getUser(userId) == null) {
            throw new NotFoundException("Пользователя не существует.");
        }
        return friendsRepository.findFriendsOfUser(userId);
    }


    public List<User> getCommonFriends(Integer userId, Integer otherId) {
        return friendsRepository.findCommonFriends(userId, otherId);
    }
}

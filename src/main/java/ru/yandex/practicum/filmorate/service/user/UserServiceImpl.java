package ru.yandex.practicum.filmorate.service.user;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
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
    public void addFriend(@Valid @RequestBody Long userId, Long friendId) {
        if (userStorage.getUser(userId) != null && userStorage.getUser(friendId) != null) {
            User user = userStorage.getUser(userId);
            User friend = userStorage.getUser(friendId);
            if (user.getFriends() != null && friend.getFriends() != null) {
                user.getFriends().add(friendId);
                friend.getFriends().add(userId);
            } else {
                user.setFriends(new HashSet<>());
                friend.setFriends(new HashSet<>());
                Set<Long> userFriends = user.getFriends();
                Set<Long> friendFriends = friend.getFriends();
                userFriends.add(friendId);
                friendFriends.add(userId);
                user.setFriends(userFriends);
                friend.setFriends(friendFriends);
            }
        }
    }

    @Override
    public void deleteFriend(@Valid @RequestBody Long userId, Long friendId) {
        if (userStorage.getUser(userId) != null && userStorage.getUser(friendId) != null) {
            User user = userStorage.getUser(userId);
            User friend = userStorage.getUser(friendId);
            Set<Long> userFriends = user.getFriends();
            Set<Long> friendFriends = friend.getFriends();
            userFriends.remove(friendId);
            friendFriends.remove(userId);
            user.setFriends(userFriends);
            friend.setFriends(friendFriends);
        }
    }

    public Collection<User> findAllFriends(@Valid @RequestBody Long userId) {
        Set<Long> friends = userStorage.getUser(userId).getFriends();
        ArrayList<User> userFriends = new ArrayList<>();
        for (Long id : friends) {
            if (userStorage.getUser(id) != null) {
                User friend = userStorage.getUser(userId);
                userFriends.add(friend);
            }
        }
        return userFriends;
    }

    @Override
    public Collection<User> getFriendsOfTwoUsers(Long userId, Long otherId) {
        Set<Long> friends = userStorage.getUser(userId).getFriends();
        return userStorage.getUser(otherId).getFriends().stream()
                .filter(friends::contains)
                .map(userStorage::getUser)
                .collect(Collectors.toList());

    }
}

package ru.practicum.shareit.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService {
    @Autowired
    private UserDB userDB;

    public UserDto addUser(UserDto userDto) {
        User user = userDB.addUser(UserMapper.toUser(userDto));
        log.info("adding new user: ok");
        return UserMapper.toUserDto(user);
    }

    public UserDto changeUser(int userId, UserDto userDto) {
        userDto.setId(userId);
        User user = userDB.changeUser(UserMapper.toUser(userDto));
        log.info("changing user: ok");
        return UserMapper.toUserDto(user);
    }

    public UserDto getUser(int userId) {
        User item = userDB.getUser(userId);
        log.info("getting user: ok");
        return UserMapper.toUserDto(item);
    }

    public void deleteUser(int userId) {
        userDB.deleteUser(userId);
        log.info("deleting user: ok");
    }

    public List<UserDto> getUsers() {
        List<User> users = userDB.getUsers();
        log.info("getting users: ok");
        return users.stream().map(user -> UserMapper.toUserDto(user)).collect(Collectors.toList());
    }

    public boolean checkId(int userId) {
        return userDB.checkId(userId);
    }
}

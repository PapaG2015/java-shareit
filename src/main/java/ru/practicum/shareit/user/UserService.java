package ru.practicum.shareit.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserDB userDB;

    public UserDto addUser(UserDto userDto) {
        User user = userDB.addUser(UserMapper.toUser(userDto));
        return UserMapper.toUserDto(user);
    }

    public UserDto changeUser(int userId, UserDto userDto) {
        userDto.setId(userId);
        User user = userDB.changeUser(UserMapper.toUser(userDto));
        return UserMapper.toUserDto(user);
    }

    public UserDto getUser(int userId) {
        User item = userDB.getUser(userId);
        return UserMapper.toUserDto(item);
    }

    public void deleteUser(int userId) {
        userDB.deleteUser(userId);
    }

    public List<UserDto> getUsers() {
        List<User> users = userDB.getUsers();
        return users.stream().map(user -> UserMapper.toUserDto(user)).collect(Collectors.toList());
    }

    public boolean checkId(int userId) {
        return userDB.checkId(userId);
    }
}

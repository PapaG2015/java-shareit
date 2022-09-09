package ru.practicum.shareit.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.IdException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public UserDto addUser(UserDto userDto) {
        User user = userRepository.save(UserMapper.toUser(userDto));
        log.info("adding new user: ok");
        return UserMapper.toUserDto(user);
    }

    public UserDto changeUser(int userId, UserDto userDto) {
        Optional<User> userDB = userRepository.findById(userId);

        if (userDto.getEmail() == null) userDto.setEmail(userDB.get().getEmail());
        if (userDto.getName() == null) userDto.setName(userDB.get().getName());

        userDto.setId(userId);
        User user = userRepository.save(UserMapper.toUser(userDto));
        log.info("changing user: ok");
        return UserMapper.toUserDto(user);
    }

    public UserDto getUser(int userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            log.info("getting user: ok");
            return UserMapper.toUserDto(user.get());
        }
        else {
            throw new IdException("no user with such id");
        }
    }

    public void deleteUser(int userId) {
        userRepository.deleteById(userId);
        log.info("deleting user: ok");
    }

    public List<UserDto> getUsers() {
        List<User> users = userRepository.findAll();
        log.info("getting users: ok");
        return users.stream().map(user -> UserMapper.toUserDto(user)).collect(Collectors.toList());
    }

    public boolean checkId(int userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) return true;
        else return false;
    }
}

package ru.practicum.shareit;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.user.UserService;
import ru.practicum.shareit.user.dto.UserDto;

import javax.transaction.Transactional;

@Transactional
@SpringBootTest(
        properties = "db.name=test",
        webEnvironment = SpringBootTest.WebEnvironment.NONE)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserServiceTest {

    private final UserService userService;

    @Test
    void testAddUser() {
        UserDto userDto1 = new UserDto(1, "test1", "test1@yandex.ru");
        userService.addUser(userDto1);

        UserDto userDto2 = new UserDto(2, "test2", "test2@yandex.ru");
        userService.addUser(userDto2);

        UserDto userDtoTest = userService.getUser(2);

        Assertions.assertEquals(userDtoTest.getName(), "test2");
    }

    @Test
    void testChangeUser() {
        UserDto userDto3 = new UserDto(3, "test3", "test3@yandex.ru");
        userService.addUser(userDto3);

        UserDto userDto4 = new UserDto(4, "test4", "test4@yandex.ru");

        userService.changeUser(3, userDto4);
        UserDto userDtoTest = userService.getUser(3);

        Assertions.assertEquals(userDtoTest.getName(), "test4");
    }
}

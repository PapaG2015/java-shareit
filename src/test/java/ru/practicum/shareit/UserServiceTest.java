package ru.practicum.shareit;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.user.UserService;
import ru.practicum.shareit.user.dto.UserDto;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Transactional
@SpringBootTest(
        properties = "db.name=test",
        webEnvironment = SpringBootTest.WebEnvironment.NONE)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserServiceTest {

    private final UserService userService;

    @Test //AddUser
    void test1() {
        UserDto userDto1 = new UserDto(1, "test1", "test1@yandex.ru");
        userService.addUser(userDto1);

        UserDto userDto2 = new UserDto(2, "test2", "test2@yandex.ru");
        userService.addUser(userDto2);

        UserDto userDtoTest = userService.getUser(2);

        Assertions.assertEquals(userDtoTest.getName(), "test2");
    }

    @Test //ChangeUser
    void test2() {
        UserDto userDto3 = new UserDto(3, "test3", "test3@yandex.ru");
        userService.addUser(userDto3);

        UserDto userDto4 = new UserDto(4, "test4", "test4@yandex.ru");

        userService.changeUser(3, userDto4);
        UserDto userDtoTest = userService.getUser(3);

        Assertions.assertEquals(userDtoTest.getName(), "test4");
    }

    @Test //GetUsers
    void test3() {
        UserDto userDto4 = new UserDto(4, "test5", "test5@yandex.ru");
        userService.addUser(userDto4);

        UserDto userDto5 = new UserDto(5, "test6", "test6@yandex.ru");
        userService.addUser(userDto5);

        List<UserDto> usersDto = userService.getUsers();
        List<UserDto> usersDtoTest = new ArrayList<>();
        usersDtoTest.add(userDto4);
        usersDtoTest.add(userDto5);

        Assertions.assertEquals(usersDto, usersDtoTest);
    }
}

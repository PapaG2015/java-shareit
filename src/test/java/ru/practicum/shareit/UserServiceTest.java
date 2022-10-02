package ru.practicum.shareit;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.user.UserService;
import ru.practicum.shareit.user.dto.UserDto;

@Transactional
@SpringBootTest(
        properties = "db.name=test",
        webEnvironment = SpringBootTest.WebEnvironment.NONE)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserServiceTest {

    private final UserService userService;

    @Test
    void testAddUser() {
        UserDto userDto = new UserDto(1, "test", "test@yandex.ru");
        userService.addUser(userDto);

        UserDto userDtoTest = userService.getUser(1);

        Assertions.assertEquals(userDtoTest.getName(), userDto.getName());
    }
}

package ru.practicum.shareit;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.item.ItemService;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemOwnerDto;
import ru.practicum.shareit.user.UserService;
import ru.practicum.shareit.user.dto.UserDto;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@SpringBootTest(
        properties = "db.name=testItemService",
        webEnvironment = SpringBootTest.WebEnvironment.NONE)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ItemServiceTest {

    private final ItemService itemService;
    private final UserService userService;

    @Test
    void testGetUserItems() {
        UserDto userDto = new UserDto(1, "test", "test@yandex.ru");
        userService.addUser(userDto);

        ItemDto itemDto = new ItemDto(1, "item", "item", true, 2, null, null);
        itemService.addItem(1, itemDto);

        List<ItemOwnerDto> itemDtoList = itemService.getItems(1);

        Assertions.assertEquals(true, itemDtoList.get(0).getAvailable());
    }
}

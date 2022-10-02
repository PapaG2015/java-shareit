package ru.practicum.shareit;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.item.ItemService;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.requests.ItemRequestService;
import ru.practicum.shareit.requests.ItemShort;
import ru.practicum.shareit.requests.dto.ItemRequestDto;
import ru.practicum.shareit.user.UserService;
import ru.practicum.shareit.user.dto.UserDto;

import javax.transaction.Transactional;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

@Transactional
@SpringBootTest(
        properties = "db.name=testItemRequestService",
        webEnvironment = SpringBootTest.WebEnvironment.NONE)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ItemRequestServiceTest {

    private final ItemRequestService itemRequestService;
    private final UserService userService;
    private final ItemService itemService;

    @Test
    void test() {
        ItemRequestDto itemRequestDto = new ItemRequestDto(1, "request1", 1, LocalDateTime.now());
        UserDto userDto1 = new UserDto(1, "test1", "test1@yandex.ru");
        userService.addUser(userDto1);

        ItemDto itemDto = new ItemDto(1, "item", "item", true, 2, null, null);
        itemService.addItem(1, itemDto);

        itemRequestService.addItemRequest(1, itemRequestDto);
        List<ItemRequestDto> itemRequests = itemRequestService.getAllItemRequests(1, null, null);

        Assertions.assertEquals("request1", itemRequests.get(0).getDescription());

    }
}

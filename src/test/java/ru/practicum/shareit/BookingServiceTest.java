package ru.practicum.shareit;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.booking.BookingService;
import ru.practicum.shareit.booking.Status;
import ru.practicum.shareit.booking.dto.BookingDtoRequest;
import ru.practicum.shareit.booking.dto.BookingDtoResponse;
import ru.practicum.shareit.item.ItemService;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserService;
import ru.practicum.shareit.user.dto.UserDto;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Transactional
@SpringBootTest(
        properties = "db.name=testBookingService",
        webEnvironment = SpringBootTest.WebEnvironment.NONE)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class BookingServiceTest {

    private final BookingService bookingService;
    private final UserService userService;
    private final ItemService itemService;

    void testGetAllBooking() {
        UserDto userDto1 = new UserDto(1, "test1", "test1@yandex.ru");
        userService.addUser(userDto1);

        UserDto userDto2 = new UserDto(2, "test2", "test2@yandex.ru");
        userService.addUser(userDto2);

        ItemDto itemDto = new ItemDto(1, "item", "item", true, 2, null, null);
        itemService.addItem(1, itemDto);

        LocalDateTime time1 = LocalDateTime.now();
        LocalDateTime time2 = time1.plusDays(2);
        BookingDtoRequest bookingDtoRequest = new BookingDtoRequest(1, time1, time2,
                1, 1, Status.WAITING);


        bookingService.addBooking(2, bookingDtoRequest);
        List<BookingDtoResponse> bookingDtoResponseList = bookingService.getAllBooking(1, "ALL", null, null);

        Assertions.assertEquals("WAITING", bookingDtoResponseList.get(0).getStatus());
    }
}

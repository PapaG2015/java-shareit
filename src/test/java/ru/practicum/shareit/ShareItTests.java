package ru.practicum.shareit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.item.ItemService;
import ru.practicum.shareit.user.UserMapper;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.UserService;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import java.util.Optional;

@SpringBootTest
class ShareItTests {

    @Mock
    BookingRepository mockBookingRepository;

    @Mock
    private ItemService mockItemService;

    @Mock
    private UserService mockUserService;

    @Mock
    private UserRepository mockUserRepository;

    @Test
    void testAddUser() {
        UserService userService = new UserService();
        userService.setUserRepository(mockUserRepository);

        UserDto userDto = new UserDto(1, "marat", "m@yandex.ru");
        User user = UserMapper.toUser(userDto);

        Mockito
                .when(mockUserRepository.save(Mockito.any(User.class)))
                .thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0, User.class));

        UserDto userDtoTest = userService.addUser(userDto);

        Assertions.assertEquals(userDto.getId(), userDtoTest.getId());
    }

    @Test
    void testCheckId() {
        UserService userService = new UserService();
        userService.setUserRepository(mockUserRepository);

        UserDto userDto = new UserDto(1, "marat", "m@yandex.ru");
        User user = UserMapper.toUser(userDto);

        Mockito
                .when(mockUserRepository.findById(Mockito.anyInt()))
                .thenAnswer(invocationOnMock -> {
                    int i = invocationOnMock.getArgument(0, Integer.class);
                    if (i < 0) return Optional.empty();
                    else return Optional.of(user);
                });

        Boolean b = userService.checkId(-3);
        Assertions.assertEquals(b, false);
    }

    @Test
    void testGetUser() {
        UserService userService = new UserService();
        userService.setUserRepository(mockUserRepository);

        UserDto userDto = new UserDto(1, "marat", "m@yandex.ru");
        User user = UserMapper.toUser(userDto);

        Mockito
                .when(mockUserRepository.findById(Mockito.anyInt()))
                .thenAnswer(invocationOnMock -> {
                    int i = invocationOnMock.getArgument(0, Integer.class);
                    if (i < 0) return Optional.empty();
                    else return Optional.of(user);
                });

        UserDto userDtoTest = userService.getUser(3);
        Assertions.assertEquals(userDto, userDtoTest);
    }

	/*@Test
	void testAddBooking() {
		BookingDtoRequest bookingDtoRequest = new BookingDtoRequest(1, LocalDateTime.now(),
				LocalDateTime.now(), 1, 1, Status.REJECTED);

		ItemOwnerDto itemOwnerDto = new ItemOwnerDto();
		itemOwnerDto.setOwner(1);

		BookingService bookingService = new BookingService();
		bookingService.setBookingRepository(mockBookingRepository);
		bookingService.setItemService(mockItemService);
		bookingService.setUserService(mockUserService);

		Mockito
				.when(mockItemService.getItem(Mockito.anyInt(), Mockito.anyInt()))
				.thenReturn(itemOwnerDto);

		bookingService.addBooking(1, bookingDtoRequest);
	}*/


    @Test
    void contextLoads() {
    }

}

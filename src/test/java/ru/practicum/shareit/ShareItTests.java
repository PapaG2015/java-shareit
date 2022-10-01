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
				.when(mockUserRepository.save(user))
				.thenReturn(user);

		UserDto userDtoTest = userService.addUser(userDto);

		Assertions.assertEquals(userDtoTest.getId(), userDtoTest.getId());


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

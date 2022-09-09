package ru.practicum.shareit.booking;

import ru.practicum.shareit.booking.dto.BookingDtoRequest;
import ru.practicum.shareit.booking.dto.BookingDtoResponse;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.ItemService;
import ru.practicum.shareit.user.UserService;

public class BookingMapper {

    public static BookingDtoResponse toBookingDtoResponse(Booking booking, ItemService itemService, UserService userService) {
        return new BookingDtoResponse(
                booking.getId(),
                booking.getStart(),
                booking.getEnding(),
                itemService.getItem(0, booking.getItemId()),
                userService.getUser(booking.getBookerId()),
                booking.getStatus());
    }

    public static Booking toBooking(BookingDtoRequest bookingDtoRequest) {
        return new Booking(
                bookingDtoRequest.getId(),
                bookingDtoRequest.getStart(),
                bookingDtoRequest.getEnd(),
                bookingDtoRequest.getItemId(),
                bookingDtoRequest.getBooker_id(),
                bookingDtoRequest.getStatus());
    }
}

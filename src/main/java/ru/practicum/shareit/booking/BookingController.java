package ru.practicum.shareit.booking;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDtoRequest;
import ru.practicum.shareit.booking.dto.BookingDtoResponse;
import ru.practicum.shareit.exception.BookingException;
import ru.practicum.shareit.exception.IdException;
import ru.practicum.shareit.exception.ParamException;
import ru.practicum.shareit.exception.StatusErrorException;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/bookings")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @PostMapping
    public BookingDtoResponse addBooking(@RequestHeader("X-Sharer-User-Id") int userId, @RequestBody BookingDtoRequest bookingDtoRequest) {
        bookingDtoRequest.setStatus(Status.WAITING);
        return bookingService.addBooking(userId, bookingDtoRequest);
    }

    @PatchMapping("/{bookingId}")
    public BookingDtoResponse changeBooking(@RequestHeader("X-Sharer-User-Id") int userId,
                                            @PathVariable int bookingId, @RequestParam boolean approved) {
        return bookingService.changeBooking(userId, bookingId, approved);
    }

    @GetMapping(value = {"/{bookingId}", "/"})
    public BookingDtoResponse getBooking(@RequestHeader("X-Sharer-User-Id") int userId,
                                         @PathVariable(required = false) Integer bookingId) {
        return bookingService.getBooking(userId, bookingId);
    }

    @GetMapping ///bookings?state={state}
    public List<BookingDtoResponse> getAllBooking(@RequestHeader("X-Sharer-User-Id") int userId,
                                                  @RequestParam(required = false, defaultValue = "ALL") String state,
                                                  @RequestParam(required = false) Integer from,
                                                  @RequestParam(required = false) Integer size) {
        return bookingService.getAllBooking(userId, state, from, size);
    }

    @GetMapping("/owner")
    public List<BookingDtoResponse> getAllBookingOwner(@RequestHeader("X-Sharer-User-Id") int userId,
                                                       @RequestParam(required = false, defaultValue = "ALL") String state,
                                                       @RequestParam(required = false) Integer from,
                                                       @RequestParam(required = false) Integer size) {
        return bookingService.getAllBookingOwner(userId, state, from, size);
    }


    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleStatusException(BookingException e) {
        log.error(e.getMessage());
        return e.getMessage();
    }


    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleIdException(IdException e) {
        log.error(e.getMessage());
        return e.getMessage();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleStatusErrorException(StatusErrorException e) {
        log.error(e.getMessage());
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleParamException(ParamException e) {
        log.error(e.getMessage());
        return e.getMessage();
    }
}

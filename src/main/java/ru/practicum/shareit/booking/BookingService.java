package ru.practicum.shareit.booking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dto.BookingDtoRequest;
import ru.practicum.shareit.booking.dto.BookingDtoResponse;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.exception.BookingException;
import ru.practicum.shareit.exception.IdException;
import ru.practicum.shareit.exception.StatusErrorException;
import ru.practicum.shareit.item.ItemService;
import ru.practicum.shareit.user.UserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private ItemService itemService;

    @Autowired
    private UserService userService;

    public BookingDtoResponse addBooking(int userId, BookingDtoRequest bookingDtoRequest) {
        if (userId == itemService.getItem(userId, bookingDtoRequest.getItemId()).getOwner())
            throw new IdException("it's you item");

        if (bookingDtoRequest.getEnd().isBefore(LocalDateTime.now())) throw new BookingException("end < now");

        if (bookingDtoRequest.getStart().isAfter(bookingDtoRequest.getEnd())) throw new BookingException("end < start");

        if (bookingDtoRequest.getStart().isBefore(LocalDateTime.now())) throw new BookingException("start < now");

        if (!itemService.getItem(userId, bookingDtoRequest.getItemId()).getAvailable())
            throw new BookingException("you can't book this item");

        itemService.getItem(userId, bookingDtoRequest.getItemId());
        userService.getUser(userId);

        bookingDtoRequest.setBooker_id(userId);
        Booking booking = bookingRepository.save(BookingMapper.toBooking(bookingDtoRequest));
        return BookingMapper.toBookingDtoResponse(booking, itemService, userService);
    }

    public BookingDtoResponse changeBooking(int userId, int bookingId, boolean approved) {
        if (userId != itemService.getItem(userId, bookingRepository.findById(bookingId).get().getItemId()).getOwner()) {
            throw new IdException("you can't see booking, if you are not owner or booker");
            //userId != bookingRepository.findById(bookingId).get().getBookerId() &&
        }

        if (bookingRepository.findById(bookingId).get().getStatus() == Status.APPROVED)
            throw new BookingException("already approved");

        if (approved) {
            Booking bookingBD = bookingRepository.findById(bookingId).get();
            bookingBD.setStatus(Status.APPROVED);
            bookingRepository.save(bookingBD);
            return BookingMapper.toBookingDtoResponse(bookingBD, itemService, userService);
        } else {
            Booking bookingBD = bookingRepository.findById(bookingId).get();
            bookingBD.setStatus(Status.REJECTED);
            bookingRepository.save(bookingBD);
            return BookingMapper.toBookingDtoResponse(bookingBD, itemService, userService);
        }
    }

    public BookingDtoResponse getBooking(int userId, Integer bookingId) {
        if (bookingRepository.findById(bookingId).isEmpty()) throw new IdException("no such booking id");

        if (bookingId == null) throw new IdException("id missing");

        if (userId != bookingRepository.findById(bookingId).get().getBookerId() &&
                userId != itemService.getItem(userId, bookingRepository.findById(bookingId).get().getItemId()).getOwner()) {
            throw new IdException("you can't see booking, if you are not owner or booker");
        }

        if (bookingRepository.findById(bookingId).isPresent()) {
            Booking bookingBD = bookingRepository.findById(bookingId).get();
            return BookingMapper.toBookingDtoResponse(bookingBD, itemService, userService);
        } else throw new IdException("no item with such id");
    }

    public List<BookingDtoResponse> getAllBooking(int userId, String state) {
        if (state.equals("ALL")) {
            return bookingRepository.findByBookerIdOrderByIdDesc(userId).stream()
                    .map(booking -> BookingMapper.toBookingDtoResponse(booking, itemService, userService)).collect(Collectors.toList());
        }
        if (state.equals("FUTURE")) {
            return bookingRepository.findByBookerIdAndStartIsAfterOrderByIdDesc(userId, LocalDateTime.now()).stream()
                    .map(booking -> BookingMapper.toBookingDtoResponse(booking, itemService, userService)).collect(Collectors.toList());
        }
        if (state.equals("WAITING")) {
            return bookingRepository.findByBookerIdAndStatusOrderByIdDesc(userId, Status.WAITING).stream()
                    .map(booking -> BookingMapper.toBookingDtoResponse(booking, itemService, userService)).collect(Collectors.toList());
        }
        if (state.equals("REJECTED")) {
            return bookingRepository.findByBookerIdAndStatusOrderByIdDesc(userId, Status.REJECTED).stream()
                    .map(booking -> BookingMapper.toBookingDtoResponse(booking, itemService, userService)).collect(Collectors.toList());
        }
        if (state.equals("CURRENT")) {
            LocalDateTime date = LocalDateTime.now();
            return bookingRepository.findByBookerIdAndStartBeforeAndEndingAfterOrderByIdDesc(userId, date, date).stream()
                    .map(booking -> BookingMapper.toBookingDtoResponse(booking, itemService, userService)).collect(Collectors.toList());
        }
        if (state.equals("PAST")) {
            LocalDateTime date = LocalDateTime.now();
            return bookingRepository.findByBookerIdAndEndingBeforeOrderByIdDesc(userId, date).stream()
                    .map(booking -> BookingMapper.toBookingDtoResponse(booking, itemService, userService)).collect(Collectors.toList());
        }

        throw new StatusErrorException("Unknown state: UNSUPPORTED_STATUS");
    }

    public List<BookingDtoResponse> getAllBookingOwner(int userId, String state) {
        userService.getUser(userId);

        if (state.equals("ALL")) {
            return bookingRepository.findByOrderByIdDesc().stream()
                    .filter(booking -> itemService.getItem(userId, booking.getItemId()).getOwner() == userId)
                    .map(booking -> BookingMapper.toBookingDtoResponse(booking, itemService, userService)).collect(Collectors.toList());
        }
        if (state.equals("FUTURE")) {
            return bookingRepository.findByAndStartIsAfterOrderByIdDesc(LocalDateTime.now()).stream()
                    .filter(booking -> itemService.getItem(userId, booking.getItemId()).getOwner() == userId)
                    .map(booking -> BookingMapper.toBookingDtoResponse(booking, itemService, userService)).collect(Collectors.toList());
        }
        if (state.equals("WAITING")) {
            return bookingRepository.findByOrderByIdDesc().stream()
                    .filter(booking -> itemService.getItem(userId, booking.getItemId()).getOwner() == userId &&
                            booking.getStatus() == Status.WAITING)
                    .map(booking -> BookingMapper.toBookingDtoResponse(booking, itemService, userService)).collect(Collectors.toList());
        }
        if (state.equals("REJECTED")) {
            return bookingRepository.findByOrderByIdDesc().stream()
                    .filter(booking -> itemService.getItem(userId, booking.getItemId()).getOwner() == userId &&
                            booking.getStatus() == Status.REJECTED)
                    .map(booking -> BookingMapper.toBookingDtoResponse(booking, itemService, userService)).collect(Collectors.toList());
        }
        if (state.equals("CURRENT")) {
            LocalDateTime date = LocalDateTime.now();
            return bookingRepository.findByStartBeforeAndEndingAfterOrderByIdDesc(date, date).stream()
                    .filter(booking -> itemService.getItem(userId, booking.getItemId()).getOwner() == userId)
                    .map(booking -> BookingMapper.toBookingDtoResponse(booking, itemService, userService)).collect(Collectors.toList());
        }
        if (state.equals("PAST")) {
            LocalDateTime date = LocalDateTime.now();
            return bookingRepository.findByEndingBeforeOrderByIdDesc(date).stream()
                    .filter(booking -> itemService.getItem(userId, booking.getItemId()).getOwner() == userId)
                    .map(booking -> BookingMapper.toBookingDtoResponse(booking, itemService, userService)).collect(Collectors.toList());
        }

        throw new StatusErrorException("Unknown state: UNSUPPORTED_STATUS");

    }

}

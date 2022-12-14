package ru.practicum.shareit.booking;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.booking.model.Booking;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
    List<Booking> findByBookerIdOrderByIdDesc(int userId);

    Page<Booking> findByBookerIdOrderByIdDesc(int userId, Pageable pageable);

    List<Booking> findByOrderByIdDesc();

    Page<Booking> findByOrderByIdDesc(Pageable pageable);

    List<Booking> findByAndStartIsAfterOrderByIdDesc(LocalDateTime end);

    List<Booking> findByBookerIdAndStartIsAfterOrderByIdDesc(int bookerId, LocalDateTime end);

    List<Booking> findByBookerIdAndStatusOrderByIdDesc(int userId, Status s);

    List<Booking> findTop2ByItemIdOrderByIdAsc(int itemId);

    List<Booking> findByBookerIdAndItemIdAndStatusAndEndingBefore(int userId, int itemId, Status s, LocalDateTime end);

    List<Booking> findByBookerIdAndStartBeforeAndEndingAfterOrderByIdDesc(int userId, LocalDateTime date1, LocalDateTime date2);

    List<Booking> findByStartBeforeAndEndingAfterOrderByIdDesc(LocalDateTime date1, LocalDateTime date2);

    List<Booking> findByBookerIdAndEndingBeforeOrderByIdDesc(int userId, LocalDateTime date);

    List<Booking> findByEndingBeforeOrderByIdDesc(LocalDateTime date);
}

package ru.practicum.shareit.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.booking.model.Booking;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
    List<Booking> findByBookerIdOrderByIdDesc(int userId);

    List<Booking> findByOrderByIdDesc();

    List<Booking> findByAndStartIsAfterOrderByIdDesc(LocalDateTime end);

    List<Booking> findByBookerIdAndStartIsAfterOrderByIdDesc(int bookerId, LocalDateTime end);

    /*@Query("SELECT b " +
            "FROM Booking b " +
            "WHERE b.itemId = ?1")*/
    List<Booking> findByBookerIdAndStatusOrderByIdDesc(int userId, Status s);

    List<Booking> findTop2ByItemIdOrderByIdAsc(int itemId);

    //List<Booking> findByBookerIdAndItemIdAndStatusAndEndingBefore(int userId, int itemId, Status s, LocalDateTime end);
    //List<Booking> findByBookerIdAndItemIdAndStatus(int userId, int itemId, Status s);
    List<Booking> findByBookerIdAndItemIdAndStatusAndEndingBefore(int userId, int itemId, Status s, LocalDateTime end);

    List<Booking> findByBookerIdAndStartBeforeAndEndingAfterOrderByIdDesc(int userId, LocalDateTime date1, LocalDateTime date2);

    List<Booking> findByStartBeforeAndEndingAfterOrderByIdDesc(LocalDateTime date1, LocalDateTime date2);

    List<Booking> findByBookerIdAndEndingBeforeOrderByIdDesc(int userId, LocalDateTime date);

    List<Booking> findByEndingBeforeOrderByIdDesc(LocalDateTime date);
}

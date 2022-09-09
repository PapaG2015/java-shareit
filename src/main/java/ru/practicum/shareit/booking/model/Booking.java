package ru.practicum.shareit.booking.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.shareit.booking.Status;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@Table(name = "bookings", schema = "public")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDateTime start;
    private LocalDateTime ending;
    @Column(name = "item_id")
    private int itemId;
    @Column(name = "booker_id")
    private int bookerId; //тот, кто бронирует
    @Enumerated(EnumType.STRING)
    private Status status;

    public Booking() {}
}
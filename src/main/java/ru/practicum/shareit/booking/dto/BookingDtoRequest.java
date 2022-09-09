package ru.practicum.shareit.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.shareit.booking.Status;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class BookingDtoRequest {
    private int id;
    private LocalDateTime start;
    private LocalDateTime end;
    private int itemId;
    private int booker_id; //тот, кто бронирует
    private Status status;
}

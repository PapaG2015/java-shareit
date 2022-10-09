package ru.practicum.shareit.item.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.shareit.booking.model.Booking;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@AllArgsConstructor
public class ItemOwnerDto extends ItemDto {
    private Booking lastBooking;
    private Booking nextBooking;

    public ItemOwnerDto() {
        super();
    }

    public ItemOwnerDto(int id, @NotBlank String name, @NotBlank String description, Boolean available, int owner, Integer request, List<CommentDto> comment, Booking lastBooking, Booking nextBooking) {
        super(id, name, description, available, owner, request, comment);
        this.lastBooking = lastBooking;
        this.nextBooking = nextBooking;
    }



    /*public ItemOwnerDto(int id, @NotBlank String name, @NotBlank String description, Boolean available, int owner, String request, Booking lastBooking, Booking nextBooking) {
        super(id, name, description, available, owner, request);
        this.lastBooking = lastBooking;
        this.nextBooking = nextBooking;
    }*/
}
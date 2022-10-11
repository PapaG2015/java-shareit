package ru.practicum.shareit.requests.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.shareit.requests.ItemShort;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class ItemRequestDto {
    private int id;
    @NotBlank
    private String description;
    private int requestor;
    private LocalDateTime created;
    List<ItemShort> items;

    public ItemRequestDto(int id, @NotBlank String description, int requestor, LocalDateTime created) {
        this.id = id;
        this.description = description;
        this.requestor = requestor;
        this.created = created;
    }

    public ItemRequestDto() {
    }
}

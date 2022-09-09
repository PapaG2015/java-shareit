package ru.practicum.shareit.item.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.shareit.user.dto.UserDto;


import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class CommentDto {
    private int id;
    @NotBlank
    private String text;
    private int item;
    private String authorName;
    private LocalDateTime created;

    public CommentDto() {}
}

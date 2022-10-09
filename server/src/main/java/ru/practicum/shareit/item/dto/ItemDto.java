package ru.practicum.shareit.item.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Data
public class ItemDto {
    private int id;
    private String name;
    private String description;
    private Boolean available;
    private int owner;
    private Integer requestId;
    private List<CommentDto> comments = new ArrayList<>();

    public ItemDto() {
    }


}
package ru.practicum.shareit.item.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Data
public class ItemDto  {
    private int id;
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    private Boolean available;
    private int owner;
    private String request;
    private List<CommentDto> comments = new ArrayList<>();

    public ItemDto() {}


}
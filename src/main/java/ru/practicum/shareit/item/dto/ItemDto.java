package ru.practicum.shareit.item.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

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
}
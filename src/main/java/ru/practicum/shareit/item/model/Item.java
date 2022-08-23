package ru.practicum.shareit.item.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@AllArgsConstructor
@Data
public class Item {
    private int id;
    private String name;
    private String description;
    @NonNull
    private Boolean available;
    private int owner;
    private String request;
}
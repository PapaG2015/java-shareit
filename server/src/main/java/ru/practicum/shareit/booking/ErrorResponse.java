package ru.practicum.shareit.booking;

import lombok.Data;

@Data
public class ErrorResponse {
    private String error;

    public ErrorResponse(String s) {
        error = s;
    }
}

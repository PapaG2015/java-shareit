package ru.practicum.shareit.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@AllArgsConstructor
@Data
public class UserDto {
    private int id;
    private String name;
    @NotBlank
    @Pattern(regexp = ".+[@].+[.].+")
    private String email;
}
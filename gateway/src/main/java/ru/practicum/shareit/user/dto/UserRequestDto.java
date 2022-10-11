package ru.practicum.shareit.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@AllArgsConstructor
@Data
public class UserRequestDto {
    private long id;
    private String name;
    @NotBlank
    @Pattern(regexp = ".+[@].+[.].+")
    private String email;
}
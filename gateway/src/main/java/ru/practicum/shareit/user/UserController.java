package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.exception.EmailException;
import ru.practicum.shareit.exception.IdException;
import ru.practicum.shareit.user.dto.UserRequestDto;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
public class UserController {
    //@Autowired
    private final UserClient userClient;

    @PostMapping
    public ResponseEntity<Object> addUser(@Valid @RequestBody UserRequestDto userRequestDto) {
        return userClient.addUser(userRequestDto);
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<Object> changeUser(@PathVariable long userId, @RequestBody UserRequestDto userRequestDto) {
        return userClient.changeUser(userId, userRequestDto);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Object> getUser(@PathVariable long userId) {
        return userClient.getUser(userId);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Object> deleteUser(@PathVariable int userId) {
        return userClient.deleteUser(userId);
    }

    @GetMapping
    public ResponseEntity<Object> getUsers() {
        return userClient.getUsers();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleEmailException(EmailException e) {
        log.error(e.getMessage());
        return e.getMessage();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleIdException(IdException e) {
        log.error(e.getMessage());
        return e.getMessage();
    }
}

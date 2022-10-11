package ru.practicum.shareit.requests;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.exception.IdException;
import ru.practicum.shareit.exception.ParamException;
import ru.practicum.shareit.requests.dto.ItemRequestDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Slf4j
@RestController
@RequestMapping(path = "/requests")
@Validated
public class ItemRequestController {
    @Autowired
    ItemRequestClient itemRequestClient;

    @PostMapping
    public ResponseEntity<Object> addItemRequest(@RequestHeader("X-Sharer-User-Id") long userId, @Valid @RequestBody ItemRequestDto itemRequestDto) {
        return itemRequestClient.addItemRequest(userId, itemRequestDto);
    }

    @GetMapping
    public ResponseEntity<Object>  getAllOwnerItemRequests(@RequestHeader("X-Sharer-User-Id") int userId) {
        return itemRequestClient.getAllOwnerItemRequest(userId);
    }

    @GetMapping("/all")
    public ResponseEntity<Object>  getAllItemRequests(@RequestHeader("X-Sharer-User-Id") int userId,
                                                      @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                                      @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {
                                                      //@RequestParam(required = false) Integer from,
                                                      //@RequestParam(required = false) Integer size) {
        return itemRequestClient.getAllItemRequests(userId, from, size);
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<Object>  getItemRequest(@RequestHeader("X-Sharer-User-Id") long userId, @PathVariable int requestId) {
        return itemRequestClient.getItemRequest(userId, requestId);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleIdException(IdException e) {
        log.error(e.getMessage());
        return e.getMessage();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleParamException(ParamException e) {
        log.error(e.getMessage());
        return e.getMessage();
    }
}

package ru.practicum.shareit.item;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.exception.BookingException;
import ru.practicum.shareit.exception.IdException;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;

import javax.validation.Valid;

@RestController
@RequestMapping("/items")
@Slf4j
public class ItemController {

    @Autowired
    private ItemClient itemClient;

    @PostMapping
    public ResponseEntity<Object> addItem(@RequestHeader("X-Sharer-User-Id") long userId,
                                          @Valid @RequestBody ItemDto itemRequestDto) {
        log.info("Creating item {}, userId={}", itemRequestDto, userId);
        return itemClient.addItem(userId, itemRequestDto);
    }

    @PatchMapping("/{itemId}")
    public ResponseEntity<Object> changeItem(@RequestHeader("X-Sharer-User-Id") long userId,
                                             @RequestBody ItemDto itemRequestDto, @PathVariable int itemId) {
        log.info("Changing item with id {}, new item {}, userId={}", itemId, itemRequestDto, userId);
        return itemClient.changeItem(userId, itemRequestDto, itemId);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<Object> getItem(@RequestHeader("X-Sharer-User-Id") long userId, @PathVariable long itemId) {
        log.info("Get item {}, userId={}", itemId, userId);
        return itemClient.getItem(userId, itemId);
    }

    @GetMapping
    public ResponseEntity<Object> getItems(@RequestHeader("X-Sharer-User-Id") long userId) {
        log.info("Get items of userId={}", userId);
        return itemClient.getItems(userId);
    }

    @GetMapping("/search")
    public ResponseEntity<Object> findItem(@RequestParam String text) {
        log.info("Find items with word {}", text);
        return itemClient.findItem(text);
    }

    @PostMapping("/{itemId}/comment")
    public ResponseEntity<Object> addComment(@RequestHeader("X-Sharer-User-Id") int userId, @PathVariable int itemId,  @Valid @RequestBody CommentDto commentDto) {
        log.info("Add item {} with comment {}, userId={}", itemId, commentDto, userId);
        return itemClient.addComment(userId, itemId, commentDto);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleIdException(IdException e) {
        log.error(e.getMessage());
        return e.getMessage();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleNullPointerException(NullPointerException e) {
        log.error("missing property \"available\"");
        return e.getMessage();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleBookingException(BookingException e) {
        return e.getMessage();
    }
}

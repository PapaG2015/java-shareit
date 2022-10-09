package ru.practicum.shareit.item;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.exception.BookingException;
import ru.practicum.shareit.exception.IdException;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemOwnerDto;

import java.util.List;

@RestController
@RequestMapping("/items")
@Slf4j
public class ItemController {

    @Autowired
    private ItemService itemService;

    @PostMapping
    public ItemDto addItem(@RequestHeader("X-Sharer-User-Id") int userId,
                           @RequestBody ItemDto itemDto) {
        return itemService.addItem(userId, itemDto);
    }

    @PatchMapping("/{itemId}")
    public ItemDto changeItem(@RequestHeader("X-Sharer-User-Id") int userId,
                              @RequestBody ItemDto itemDto, @PathVariable int itemId) {
        return itemService.changeItem(userId, itemDto, itemId);
    }

    @GetMapping("/{itemId}")
    public ItemOwnerDto getItem(@RequestHeader("X-Sharer-User-Id") int userId, @PathVariable int itemId) {
        return itemService.getItem(userId, itemId);
    }

    @GetMapping
    public List<ItemOwnerDto> getItems(@RequestHeader("X-Sharer-User-Id") int userId) {
        return itemService.getItems(userId);
    }

    @GetMapping("/search")
    public List<ItemDto> findItem(@RequestParam String text) {
        return itemService.findItem(text);
    }

    @PostMapping("/{itemId}/comment")
    public CommentDto addComment(@RequestHeader("X-Sharer-User-Id") int userId, @PathVariable int itemId, @RequestBody CommentDto commentDto) {
        return itemService.addComment(userId, itemId, commentDto);
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

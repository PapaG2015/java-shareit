package ru.practicum.shareit.requests;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.exception.IdException;
import ru.practicum.shareit.exception.ParamException;
import ru.practicum.shareit.requests.dto.ItemRequestDto;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/requests")
public class ItemRequestController {
    @Autowired
    ItemRequestService itemRequestService;

    @PostMapping
    public ItemRequestDto addItemRequest(@RequestHeader("X-Sharer-User-Id") int userId, @Valid @RequestBody ItemRequestDto itemRequestDto) {
        return itemRequestService.addItemRequest(userId, itemRequestDto);
    }

    @GetMapping
    public List<ItemRequestDto> getAllOwnerItemRequests(@RequestHeader("X-Sharer-User-Id") int userId) {
        return itemRequestService.getAllOwnerItemRequest(userId);
    }

    @GetMapping("/all")
    public List<ItemRequestDto> getAllItemRequests(@RequestHeader("X-Sharer-User-Id") int userId,
                                                   @RequestParam(required = false) Integer from,
                                                   @RequestParam(required = false) Integer size) {
        return itemRequestService.getAllItemRequests(userId, from, size);
    }

    @GetMapping("/{requestId}")
    public ItemRequestDto getItemRequest(@RequestHeader("X-Sharer-User-Id") int userId, @PathVariable int requestId) {
        return itemRequestService.getItemRequest(userId, requestId);
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

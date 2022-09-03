package ru.practicum.shareit.item;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.IdException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ItemService {

    @Autowired
    private ItemDB itemDB;

    @Autowired
    private UserService userService;

    public ItemDto addItem(int userId, ItemDto itemDto) {
        if (!userService.checkId(userId)) throw new IdException("no such user");

        itemDto.setOwner(userId);
        Item item = itemDB.addItem(ItemMapper.toItem(itemDto));
        log.info("adding new item: ok");
        return ItemMapper.toItemDto(item);
    }

    public ItemDto changeItem(int userId, ItemDto itemDto, int itemId) {
        if (userId != itemDB.getItem(itemId).getOwner()) throw new IdException("item update with other user");

        itemDto.setId(itemId);
        itemDto.setOwner(userId);

        Item item = itemDB.changeItem(itemDto);
        log.info("changing item: ok");
        return ItemMapper.toItemDto(item);
    }

    public ItemDto getItem(int itemId) {
        Item item = itemDB.getItem(itemId);
        log.info("getting item: ok");
        return ItemMapper.toItemDto(item);
    }

    public List<ItemDto> getItems(int userId) {
        List<Item> items = itemDB.getItems(userId);
        log.info("getting items: ok");
        return items.stream().map(item -> ItemMapper.toItemDto(item)).collect(Collectors.toList());
    }

    public List<ItemDto> findItem(String text) {
        if (text.isEmpty()) return new ArrayList<>();

        List<Item> items = itemDB.findItem(text);
        log.info("searching items: ok");
        return items.stream().map(item -> ItemMapper.toItemDto(item)).collect(Collectors.toList());
    }
}

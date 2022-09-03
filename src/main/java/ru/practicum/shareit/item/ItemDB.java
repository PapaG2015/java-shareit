package ru.practicum.shareit.item;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class ItemDB {
    private Map<Integer, Item> itemDB = new HashMap<>();
    private int counter = 0;

    public Item addItem(Item item) {
        counter = counter + 1;
        item.setId(counter);

        itemDB.put(counter, item);
        return itemDB.get(counter);
    }

    public Item changeItem(ItemDto item) {
        int id = item.getId();

        Item baseItem = itemDB.get(id);

        if (item.getName() != null) baseItem.setName(item.getName());
        if (item.getDescription() != null) baseItem.setDescription(item.getDescription());
        if (item.getAvailable() != null) baseItem.setAvailable(item.getAvailable());

        return itemDB.get(id);
    }

    public Item getItem(int itemId) {
        return itemDB.get(itemId);
    }

    public List<Item> getItems(int userId) {
        return itemDB.values().stream().filter(item -> item.getOwner() == userId).collect(Collectors.toList());
    }

    public List<Item> findItem(String text) {
        String textTLC = text.toLowerCase();

        return itemDB.values().stream().filter(item ->
                (item.getName().toLowerCase().contains(textTLC) ||
                        item.getDescription().toLowerCase().contains(textTLC))
                        && item.getAvailable() == true)
                .collect(Collectors.toList());
    }
}

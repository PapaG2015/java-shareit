package ru.practicum.shareit.item;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.booking.Status;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.exception.BookingException;
import ru.practicum.shareit.exception.IdException;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemOwnerDto;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private CommentRepository commentRepository;

    public ItemDto addItem(int userId, ItemDto itemDto) {
        if (!userService.checkId(userId)) throw new IdException("no such user");

        itemDto.setOwner(userId);
        Item item = itemRepository.save(ItemMapper.toItem(itemDto));
        log.info("adding new item: ok");
        return ItemMapper.toItemDto(item);
    }

    public ItemDto changeItem(int userId, ItemDto itemDto, int itemId) {
        if (itemRepository.findById(itemId).isEmpty())
            throw new IdException("no such user");

        if (userId != itemRepository.findById(itemId).get().getOwner())
            throw new IdException("item update with other user");

        Optional<Item> itemDB = itemRepository.findById(itemId);

        if (itemDto.getName() == null) itemDto.setName(itemDB.get().getName());
        if (itemDto.getDescription() == null) itemDto.setDescription(itemDB.get().getDescription());
        if (itemDto.getAvailable() == null) itemDto.setAvailable(itemDB.get().getAvailable());

        itemDto.setId(itemId);
        itemDto.setOwner(userId);

        Item item = itemRepository.save(ItemMapper.toItem(itemDto));
        log.info("changing item: ok");
        return ItemMapper.toItemDto(item);
    }

    public ItemOwnerDto getItem(int userId, int itemId) {
        Optional<Item> item = itemRepository.findById(itemId);
        if (item.isPresent()) {
            log.info("getting item: ok");

            List<Comment> commentBD = commentRepository.findByItemId(itemId);

            List<CommentDto> comments = commentBD.stream().map(comment -> {
                String authorName = userService.getUser(comment.getAuthorId()).getName();
                return CommentMapper.toCommentDto(comment, authorName);
            }).collect(Collectors.toList());

            if (item.get().getOwner() != userId) return ItemOwnerMapper.toItemDto(item.get(), null, null, comments);
            else {
                List<Booking> bookings = bookingRepository.findTop2ByItemIdOrderByIdAsc(itemId);

                if (bookings.isEmpty()) return ItemOwnerMapper.toItemDto(item.get(), null, null, comments);

                if (bookings.size() != 2) return ItemOwnerMapper.toItemDto(item.get(), bookings.get(0), null, comments);

                return ItemOwnerMapper.toItemDto(item.get(), bookings.get(0), bookings.get(1), comments);
            }
        } else throw new IdException("no item with such id");
    }

    public List<ItemOwnerDto> getItems(int userId) {
        List<Item> items = itemRepository.findByOwnerOrderByIdAsc(userId);
        log.info("getting items: ok");

        return items.stream().map(item -> {
            List<Booking> bookings = bookingRepository.findTop2ByItemIdOrderByIdAsc(item.getId());

            if (bookings.isEmpty()) return ItemOwnerMapper.toItemDto(item, null, null, null);

            if (bookings.size() != 2) return ItemOwnerMapper.toItemDto(item, bookings.get(0), null, null);

            return ItemOwnerMapper.toItemDto(item, bookings.get(0), bookings.get(1), null);

        }).collect(Collectors.toList());
    }

    public List<ItemDto> findItem(String text) {
        if (text.isEmpty()) return new ArrayList<>();

        List<Item> items = itemRepository.findItem(text);
        log.info("searching items: ok");
        return items.stream().map(item -> ItemMapper.toItemDto(item)).collect(Collectors.toList());
    }

    public CommentDto addComment(int userId, int itemId, CommentDto commentDto) {
        if (bookingRepository.findByBookerIdAndItemIdAndStatusAndEndingBefore(userId, itemId, Status.APPROVED, LocalDateTime.now()).isEmpty())
            throw new BookingException("you didn't book this item");
        else {
            commentDto.setItem(itemId);
            commentDto.setCreated(LocalDateTime.now());
            Comment comment = commentRepository.save(CommentMapper.toComment(commentDto, userId));
            String authorName = userService.getUser(userId).getName();
            return CommentMapper.toCommentDto(comment, authorName);
        }
    }
}

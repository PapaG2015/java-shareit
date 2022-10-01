package ru.practicum.shareit.requests;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.IdException;
import ru.practicum.shareit.exception.ParamException;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.requests.dto.ItemRequestDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ItemRequestService {

    @Autowired
    ItemRequestRepository itemRequestRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    ItemRepository userRepository;

    public ItemRequestDto addItemRequest(int userId, ItemRequestDto itemRequestDto) {
        if (itemRepository.findById(userId).isEmpty()) {
            throw new IdException("no user with such id");
        } else {
            itemRequestDto.setRequestor(userId);
            itemRequestDto.setCreated(LocalDateTime.now());
            ItemRequest itemRequest = ItemRequestMapper.toItemRequest(itemRequestDto);
            log.info("adding request: ok");
            return ItemRequestMapper.toItemRequestDto(
                    itemRequestRepository.save(itemRequest));
        }
    }

    public List<ItemRequestDto> getAllOwnerItemRequest(int userId) {
        if (itemRepository.findById(userId).isEmpty()) {
            throw new IdException("no user with such id");
        } else {
            List<ItemRequest> itemRequests = itemRequestRepository.findByRequestor(userId);
            List<ItemRequestDto> itemRequestsDto = itemRequests.stream()
                    .map(ItemRequestMapper::toItemRequestDto).collect(Collectors.toList());
            itemRequestsDto.stream()
                    .forEach(itemRequestDto -> {
                        List<ItemShort> items = itemRepository.findByRequestId(itemRequestDto.getId());
                        itemRequestDto.setItems(items);
                    });

            log.info("getting all owner requests: ok");
            return itemRequestsDto;
        }
    }

    public List<ItemRequestDto> getAllItemRequests(int userId, Integer from, Integer size) {
        if (from == null || size == null) {
            List<ItemRequest> itemRequests = itemRequestRepository.findAll();
            List<ItemRequestDto> itemRequestsDto = itemRequests.stream()
                    .map(ItemRequestMapper::toItemRequestDto).collect(Collectors.toList());
            log.info("getting all requests with no paging: ok");
            return itemRequestsDto;
        }

        if (size <= 0 || from < 0) {
            throw new ParamException("size can't be <=0 or from < 0");
        }

        Pageable pageable = PageRequest.of(from / size, size);
        Page<ItemRequest> itemRequests = itemRequestRepository
                .findByRequestorNot(userId, pageable);
        List<ItemRequestDto> itemRequestsDto = itemRequests.stream()
                .map(ItemRequestMapper::toItemRequestDto).collect(Collectors.toList());

        itemRequestsDto.stream()
                .forEach(itemRequestDto -> {
                    List<ItemShort> items = itemRepository.findByRequestId(itemRequestDto.getId());
                    itemRequestDto.setItems(items);
                });

        log.info("getting all requests with paging: ok");
        return itemRequestsDto;
    }

    public ItemRequestDto getItemRequest(int userId, int requestId) {
        if (userRepository.findById(userId).isEmpty())
            throw new IdException("no user with such id");

        if (itemRequestRepository.findById(requestId).isEmpty()) {
            throw new IdException("no item request with such id");
        } else {
            ItemRequest itemRequest = itemRequestRepository.findById(requestId).get();
            ItemRequestDto itemRequestDto = ItemRequestMapper.toItemRequestDto(itemRequest);
            List<ItemShort> items = itemRepository.findByRequestId(itemRequestDto.getId());
            itemRequestDto.setItems(items);
            log.info("getting request: ok");
            return itemRequestDto;
        }
    }
}


package ru.practicum.shareit.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.shareit.client.BaseClient;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;

import java.util.Map;

@Service
public class ItemClient extends BaseClient {
    private static final String API_PREFIX = "/items";

    @Autowired
    //shareit-server.url=http://localhost:9090
    public ItemClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    public ResponseEntity<Object> getItems(long userId) {
        return get(null, userId);
    }


    public ResponseEntity<Object> addItem(Long userId, ItemDto userRequestDto) {
        return post("", userId, null, userRequestDto);
    }

    public ResponseEntity<Object> getItem(Long userId, long itemId) {
        return get("/" + itemId, userId);
    }


    public ResponseEntity<Object> changeItem(long userId, ItemDto userRequestDto, long itemId) {
        return patch("/" + itemId, userId, userRequestDto);
    }

    public ResponseEntity<Object> deleteItem(long userId) {
        return delete("/" + userId);
    }

    public ResponseEntity<Object> findItem(String text) {
        Map<String, Object> parameters = Map.of(
                "text", text
        );
        return get("/search" + "?text={text}", null, parameters);
    }

    public ResponseEntity<Object> addComment(long userId, long itemId, CommentDto commentDto) {
        return post("/" + itemId + "/comment", userId, commentDto);
    }
}

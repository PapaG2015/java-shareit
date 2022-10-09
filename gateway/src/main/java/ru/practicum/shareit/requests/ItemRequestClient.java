package ru.practicum.shareit.requests;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.shareit.client.BaseClient;
import ru.practicum.shareit.requests.dto.ItemRequestDto;

import java.util.Map;

@Service
public class ItemRequestClient extends BaseClient {
    private static final String API_PREFIX = "/requests";

    @Autowired
    //shareit-server.url=http://localhost:9090
    public ItemRequestClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    public ResponseEntity<Object> getItemRequest(long userId) {
        /*Map<String, Object> parameters = Map.of(
                "state", state.name(),
                "from", from,
                "size", size
        );*/
        return get(null, userId);
    }


    public ResponseEntity<Object> addItemRequest(Long userId, ItemRequestDto itemRequestDto) {
        return post("", userId, null, itemRequestDto);
    }

    public ResponseEntity<Object> getItemRequest(Long userId, long requestId) {
        return get("/" + requestId, userId);
    }

    public ResponseEntity<Object> getAllOwnerItemRequest(long userId) {
        return get("", userId);
    }

    public ResponseEntity<Object> getAllItemRequests(long userId, Integer from, Integer size) {
        Map<String, Object> parameters = Map.of(
                "from", from,
                "size", size
        );
        return get("/all" + "?from={from}&size={size}", userId, parameters);
    }
}

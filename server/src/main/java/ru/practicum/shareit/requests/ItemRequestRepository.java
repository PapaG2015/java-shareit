package ru.practicum.shareit.requests;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRequestRepository extends JpaRepository<ItemRequest, Integer> {
    List<ItemRequest> findByRequestor(int requestor);

    Page<ItemRequest> findByRequestorNot(Integer requestorId, Pageable pageable);

}

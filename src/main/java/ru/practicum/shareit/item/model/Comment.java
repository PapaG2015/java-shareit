package ru.practicum.shareit.item.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.shareit.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@Table(name = "comments", schema = "public")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Column(name = "comment_id")
    private int id;
    private String text;
    @Column(name = "item_id")
    private int itemId;
    @Column(name = "author_id")
    private int authorId;
    private LocalDateTime created;

    public Comment() {}
}



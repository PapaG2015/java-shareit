package ru.practicum.shareit.item.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;

@AllArgsConstructor
@Data
@Entity
@Table(name = "items", schema = "public")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String description;
    @NonNull
    private Boolean available;
    private int owner;
    @Column(name = "request_id")
    private Integer requestId;

    //@ElementCollection
    //@OneToMany
    //@CollectionTable(name="comments", joinColumns=@JoinColumn(name="id"))
    //@Column(name="name")

    //private List<Comment> comment = new ArrayList<>();

    public Item() {
    }


}
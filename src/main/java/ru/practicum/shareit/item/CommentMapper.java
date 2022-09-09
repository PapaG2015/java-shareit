package ru.practicum.shareit.item;

import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.model.Comment;


public class CommentMapper {
    public static CommentDto toCommentDto(Comment comment, String authorName) {
        return new CommentDto(
                comment.getId(),
                comment.getText(),
                comment.getItemId(),
                authorName,
                comment.getCreated());
    }

    public static Comment toComment(CommentDto commentDto, int userId) {
        return new Comment(
                commentDto.getId(),
                commentDto.getText(),
                commentDto.getItem(),
                userId,
                commentDto.getCreated());
    }
}

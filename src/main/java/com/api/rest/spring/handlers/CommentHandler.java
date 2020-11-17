package com.api.rest.spring.handlers;

import com.api.rest.spring.Entity.Comment;
import com.api.rest.spring.Entity.Dto.CommentDto;
import com.api.rest.spring.Entity.builders.CommentBuilder;
import com.api.rest.spring.handlers.exceptions.ValidationException;
import com.api.rest.spring.repository.CommentRepository;

import javax.naming.AuthenticationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CommentHandler {
    private CommentRepository commentRepository;

    public CommentHandler(CommentRepository commentRepository) {
        this.commentRepository = Objects.requireNonNull(commentRepository);
    }



    public void addComment(Integer userId, CommentDto commentDto) throws ValidationException, AuthenticationException {

        ValidateAddComment(userId, commentDto);


        CommentBuilder commentBuilder = CommentBuilder.aCommentBuilder();

        Comment build = commentBuilder
                .withUserId(userId)
                .withTaskId(commentDto.getTaskId())
                .withCommentText(commentDto.getCommentText())
                .build();

        commentRepository.save(build);


    }

    private void ValidateAddComment(Integer userId, CommentDto commentDto) throws ValidationException, AuthenticationException{
        if (userId == null)
            throw new ValidationException("Missing user");
        if (commentDto == null)
            throw new ValidationException("Missing CommentDto");
    }

    public List<CommentDto> getCommentsLists(Integer userId, Integer taskId) throws ValidationException, AuthenticationException {


        ValidateGetCommentsLists(userId, taskId);
        List<Comment> comments = commentRepository.findAllByTaskId(taskId);

        List<CommentDto> resultObjects = new ArrayList<>();
        for (Comment comment : comments) {
            CommentDto dto = new CommentDto(comment.getId(), comment.getUserId(), comment.getTaskId(), comment.getCommentText(), comment.getCreated());
            resultObjects.add(dto);
        }

        return resultObjects;
    }

    private void ValidateGetCommentsLists(Integer userId, Integer taskId) throws ValidationException {
        if (userId == null)
            throw new ValidationException("Missing userId");
        if (taskId == null)
            throw new ValidationException("Missing taskId");
    }
}

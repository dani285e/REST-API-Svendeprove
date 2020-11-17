package com.api.rest.spring.Entity.builders;

import com.api.rest.spring.Entity.Comment;

import java.util.Date;
import java.util.Objects;

public class CommentBuilder {
    private Integer id;
    private Integer userId;
    private Integer taskId;
    private String commentText;
    private Date created;

    public static CommentBuilder aCommentBuilder() {
        return new CommentBuilder();
    }

    public CommentBuilder withId(Integer id) {
        this.id = id;
        return this;
    }


    public CommentBuilder withUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public CommentBuilder withTaskId(Integer taskId) {
        this.taskId = taskId;
        return this;
    }

    public CommentBuilder withCommentText(String commentText) {
        this.commentText = commentText;
        return this;
    }

    public CommentBuilder withCreated(Date created) {
        this.created = created;
        return this;
    }

    public Comment build() {
        Comment comment = new Comment();
        comment.setUserId(Objects.requireNonNull(userId));
        comment.setTaskId(Objects.requireNonNull(taskId));
        comment.setCommentText(Objects.requireNonNull(commentText));
        // with variables

        return comment;
    }
}

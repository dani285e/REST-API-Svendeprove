package com.api.rest.spring.Entity.Dto;

import java.util.Date;

public class CommentDto {
    private Integer id;
    private Integer userId;
    private Integer taskId;
    private String commentText;
    private Date created;

    public CommentDto(Integer id, Integer userId, Integer taskId, String commentText, Date created) {
        this.id = id;
        this.userId = userId;
        this.taskId = taskId;
        this.commentText = commentText;
        this.created = created;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}

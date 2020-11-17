package com.api.rest.spring.Entity;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
public class Comment {

    @Id
    @GeneratedValue
    private Integer id;
    @NotNull
    private Integer userId;
    @NotNull
    private Integer taskId;
    @NotNull
    private String commentText;
    @NotNull
    private Date created;

    @PrePersist
    protected void onCreate(){
        created = new Date();
    }

    public Integer getId() {
        return id;
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

    public void setCommentText(String comment) {
        this.commentText = comment;
    }

    public Date getCreated() {
        return created;
    }
}

package com.api.rest.spring.Entity.Dto;

import com.api.rest.spring.Entity.Enum.Role;
import com.api.rest.spring.Entity.Enum.TaskStatus;
import com.api.rest.spring.Entity.Task;

import java.util.Date;

public class TaskDto {
    private Integer id;
    private String taskName;
    private String taskDesc;
    private String taskOwner;
    private TaskStatus taskStatus;
    private Date created;

    public TaskDto(Integer id, String taskName, String taskDesc, String taskOwner, TaskStatus taskStatus, Date created) {
        this.id = id;
        this.taskName = taskName;
        this.taskDesc = taskDesc;
        this.taskOwner = taskOwner;
        this.taskStatus = taskStatus;
        this.created = created;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDesc() {
        return taskDesc;
    }

    public void setTaskDesc(String taskDesc) {
        this.taskDesc = taskDesc;
    }

    public String getTaskOwner() {
        return taskOwner;
    }

    public void setTaskOwner(String taskOwner) {
        this.taskOwner = taskOwner;
    }

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    public void TaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}

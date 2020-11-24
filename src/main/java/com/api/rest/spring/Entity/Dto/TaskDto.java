package com.api.rest.spring.Entity.Dto;

import com.api.rest.spring.Entity.Enum.Role;
import com.api.rest.spring.Entity.Enum.TaskStatus;
import com.api.rest.spring.Entity.Task;

import java.util.Date;

public class TaskDto {

    private Integer requestingUser;

    private Integer id;
    private String taskName;
    private String taskDesc;
    private Integer taskOwner;
    private TaskStatus taskStatus;

    public Integer getRequestingUser() {
        return requestingUser;
    }

    public void setRequestingUser(Integer requestingUser) {
        this.requestingUser = requestingUser;
    }

    public void setTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }

    public TaskDto(Integer id, String taskName, String taskDesc, Integer taskOwner, TaskStatus taskStatus, Integer requestingUser) {
        this.id = id;
        this.taskName = taskName;
        this.taskDesc = taskDesc;
        this.taskOwner = taskOwner;
        this.taskStatus = taskStatus;
        this.requestingUser = requestingUser;
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

    public Integer getTaskOwner() {
        return taskOwner;
    }

    public void setTaskOwner(Integer taskOwner) {
        this.taskOwner = taskOwner;
    }

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    public void TaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }

}

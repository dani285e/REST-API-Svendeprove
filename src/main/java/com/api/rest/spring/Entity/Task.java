package com.api.rest.spring.Entity;


import com.api.rest.spring.Entity.Enum.TaskStatus;
import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table
public class Task {

    @Id
    @GeneratedValue
    private Integer id;
    @NotNull
    private String taskName;
    @NotNull
    private String taskDesc;
    @NotNull
    private Integer taskOwner;
    @NotNull
    private TaskStatus taskStatus;
    @NotNull
    private Date created;
    @ManyToMany
    private List<User> users = new ArrayList<>();

    @PrePersist
    protected void onCreate(){
        created = new Date();
    }

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }

    public Integer getTaskOwner() {
        return taskOwner;
    }

    public void setTaskOwner(Integer taskOwner) {
        this.taskOwner = taskOwner;
    }

    public Integer getId() {
        return id;
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

    public Date getCreated() {
        return created;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id.equals(task.id) &&
                taskName.equals(task.taskName) &&
                taskDesc.equals(task.taskDesc) &&
                taskOwner.equals(task.taskOwner) &&
                taskStatus.equals(task.taskStatus) &&
                created.equals(task.created);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, taskName, taskDesc, taskOwner, taskStatus, created);
    }
}

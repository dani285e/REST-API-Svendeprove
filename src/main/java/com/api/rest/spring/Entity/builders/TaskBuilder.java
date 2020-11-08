package com.api.rest.spring.Entity.builders;

import com.api.rest.spring.Entity.Enum.TaskStatus;
import com.api.rest.spring.Entity.Task;
import com.api.rest.spring.Entity.User;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

public class TaskBuilder {

    private Integer id;
    private String taskName;
    private String taskDesc;
    private String taskOwner;
    private String taskStatus;
    private Date created;

    public static TaskBuilder aTaskBuilder() {
        return new TaskBuilder();
    }

    public TaskBuilder withId(Integer id) {
        this.id = id;
        return this;
    }

    public TaskBuilder withTaskName(String taskName) {
        this.taskName = taskName;
        return this;
    }

    public TaskBuilder withTaskDesc(String taskDesc) {
        this.taskDesc = taskDesc;
        return this;
    }

    public TaskBuilder withTaskOwner(String taskOwner) {
        this.taskOwner = taskOwner;
        return this;
    }

    public TaskBuilder withTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
        return this;
    }

    public TaskBuilder withCreated(Date created) {
        this.created = created;
        return this;
    }

    public Task build() {
        Task task = new Task();
        task.setTaskName(Objects.requireNonNull(taskName));
        task.setTaskDesc(Objects.requireNonNull(taskDesc));
        task.setTaskOwner(Objects.requireNonNull(taskOwner));
        task.setTaskStatus(Objects.requireNonNull(TaskStatus.CREATED));
        // with variables

        return task;
    }

}

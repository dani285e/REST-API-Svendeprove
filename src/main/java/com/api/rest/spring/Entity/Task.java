package com.api.rest.spring.Entity;


import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table
public class Task {

    @Id
    @GeneratedValue
    private Integer id;
    private String taskName;
    private String taskDesc;
    private Date created;

    @PrePersist
    protected void onCreate(){
        created = new Date();
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

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id.equals(task.id) &&
                taskName.equals(task.taskName) &&
                taskDesc.equals(task.taskDesc) &&
                created.equals(task.created);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, taskName, taskDesc, created);
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", taskName='" + taskName + '\'' +
                ", taskDesc='" + taskDesc + '\'' +
                ", created=" + created +
                '}';
    }
}

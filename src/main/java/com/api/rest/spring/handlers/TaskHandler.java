package com.api.rest.spring.handlers;

import com.api.rest.spring.Entity.Dto.TaskDto;
import com.api.rest.spring.Entity.Enum.TaskStatus;
import com.api.rest.spring.Entity.Task;
import com.api.rest.spring.handlers.exceptions.ValidationException;
import com.api.rest.spring.repository.TaskRepository;
import com.api.rest.spring.repository.UserRepository;

import javax.naming.AuthenticationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TaskHandler {

    private TaskRepository taskRepository;

    public TaskHandler(TaskRepository taskRepository) {
        this.taskRepository = Objects.requireNonNull(taskRepository);
    }


    public void addTask(String taskName, String taskDesc, Integer taskOwner) throws AuthenticationException, ValidationException {

    }

    public void changeStatus(Integer taskId, TaskStatus taskStatus){

    }




    public List<TaskDto> showTaskList(Integer userId, Integer requestingUserId) throws AuthenticationException, ValidationException{

        ValidateUser(userId, requestingUserId);
        List<Task> tasks = taskRepository.findAllByUser(userId);

        List<TaskDto> resultObjects = new ArrayList<>();
        for (Task task : tasks) {
             TaskDto dto = new TaskDto(task.getId(), task.getTaskName(), task.getTaskDesc(), task.getTaskOwner(), task.getTaskStatus(), task.getCreated());
             resultObjects.add(dto);
        }

        return resultObjects;
    }

    private void ValidateUser(Integer userId, Integer requestingUserId) throws ValidationException {
        if (userId == null)
            throw new ValidationException("Missing userId");
        if (requestingUserId == null)
            throw new ValidationException("Missing requester userId");
        if (userId != requestingUserId)
            throw new ValidationException("User is not the one requesting a task list");
    }
}

package com.api.rest.spring.handlers;

import com.api.rest.spring.Entity.Dto.TaskDto;
import com.api.rest.spring.Entity.Enum.Role;
import com.api.rest.spring.Entity.Enum.TaskStatus;
import com.api.rest.spring.Entity.Task;
import com.api.rest.spring.Entity.User;
import com.api.rest.spring.Entity.builders.TaskBuilder;
import com.api.rest.spring.Entity.builders.UserBuilder;
import com.api.rest.spring.WebApiHelper;
import com.api.rest.spring.handlers.exceptions.ValidationException;
import com.api.rest.spring.repository.TaskRepository;
import com.api.rest.spring.repository.UserRepository;
import com.sun.istack.NotNull;

import javax.naming.AuthenticationException;
import java.util.*;

public class TaskHandler {

    private TaskRepository taskRepository;

    private UserRepository userRepository;

    public TaskHandler(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = Objects.requireNonNull(taskRepository);
        this.userRepository = Objects.requireNonNull(userRepository);
    }


    public void addTask(String taskName, String taskDesc, Integer taskOwner) throws AuthenticationException, ValidationException {
        ValidateAddTask(taskName, taskDesc, taskOwner);

        TaskBuilder taskBuilder = TaskBuilder.aTaskBuilder();

        Task build = taskBuilder
                .withTaskName(taskName)
                .withTaskDesc(taskDesc)
                .withTaskOwner(taskOwner)
                .withTaskStatus(TaskStatus.CREATED.toString())
                .build();

        taskRepository.save(build);

    }

    private void ValidateAddTask(String taskName, String taskDesc, Integer taskOwner) throws ValidationException {
            if (taskName == null || taskName.isEmpty())
                throw new ValidationException("Missing taskName");
            if (taskDesc == null || taskDesc.isEmpty())
                throw new ValidationException("Missing taskDesc");
            if (taskOwner == null)
                throw new ValidationException("Missing taskOwner");
    }

    /**
     *
     * @param userId
     * @param requestingUserId
     * @return
     * @throws AuthenticationException
     * @throws ValidationException
     */
    public List<TaskDto> createTaskList(Integer userId, Integer requestingUserId) throws AuthenticationException, ValidationException{

        ValidateUser(userId, requestingUserId);
        Optional<User> nullableUser = userRepository.findById(userId);
        List<User> user = List.of(nullableUser.get());
        Iterable<Task> tasks = taskRepository.findAllByTaskOwnerOrUsersIn(userId, user);

        List<TaskDto> resultObjects = new ArrayList<>();
        for (Task task : tasks) {
             TaskDto dto = new TaskDto(task.getId(), task.getTaskName(), task.getTaskDesc(), task.getTaskOwner(), task.getTaskStatus(), null);
             resultObjects.add(dto);
        }

        return resultObjects;
    }




    /**
     *
     * @param requestingUserId
     * @param taskId
     * @throws ValidationException
     * @throws AuthenticationException
     */
    public void removeTask(Integer requestingUserId, Integer taskId) throws ValidationException, AuthenticationException{
        Task task = taskRepository.findTaskById(taskId);
        if (task == null) {
            throw new ValidationException("Task does not exist");
        }
        User requestingUser = userRepository.findUserById(task.getTaskOwner());
        ValidateRemoveTask(requestingUser, task);

        taskRepository.deleteById(taskId);
    }
    private void ValidateRemoveTask(User requestingUser, Task task) throws ValidationException, AuthenticationException{
        if (requestingUser == null)
            throw new ValidationException("Missing user");
        if (task == null)
            throw new ValidationException("Missing task");
        if (requestingUser.getId() != task.getTaskOwner() || !WebApiHelper.ADMIN_ROLES.contains(requestingUser.getRole()))
            throw new AuthenticationException("User does not have permission to remove this task");
    }
    /**
     *
     * @param userId
     * @param requestingUserId
     * @throws ValidationException
     */
    private void ValidateUser(Integer userId, Integer requestingUserId) throws ValidationException {
        if (userId == null)
            throw new ValidationException("Missing userId");
        if (requestingUserId == null)
            throw new ValidationException("Missing requester userId");
        if (userId != requestingUserId)
            throw new ValidationException("User is not the one requesting a task list");
    }



    public void updateTask(Integer taskId, TaskDto taskDto, Integer userId) throws ValidationException, AuthenticationException{
        User user = userRepository.findUserById(userId);
        Task task = taskRepository.findTaskById(taskId);
        ValidateUpdateTask(task, taskDto, user);

        task.setTaskName(taskDto.getTaskName());
        task.setTaskDesc(taskDto.getTaskDesc());
        task.setTaskOwner(taskDto.getTaskOwner());
        taskRepository.save(task);
    }
    private void ValidateUpdateTask(Task task, TaskDto taskDto, User user) throws ValidationException {
        if (task == null)
            throw new ValidationException("Task is missing");
        if (taskDto == null)
            throw new ValidationException("TaskDto is missing");
        if (user == null)
            throw new ValidationException("User is missing");
        //TODO Add more validations...
    }



    public void updateStatus(TaskDto taskDto) throws ValidationException, AuthenticationException {
        Integer requestingUser = taskDto.getRequestingUser();
        Integer taskId = taskDto.getId();
        ValidateUpdateStatus(taskDto, requestingUser);
        Task task = taskRepository.findTaskById(taskId);
        if (task == null) {
            throw new ValidationException(String.format("Task with id <%s> does not exist", taskId));
        }
        task.setTaskStatus(taskDto.getTaskStatus());
        System.out.println(String.format("UPDATE_STATUS: Requesting Status: <%s>. Requesting UserId for updating Status: <%s>", taskDto.getTaskStatus(), requestingUser));
        taskRepository.save(task);
        System.out.println("UPDATE_STATUS: DONE");
    }

    private void ValidateUpdateStatus(TaskDto taskDto, Integer userId) throws ValidationException, AuthenticationException {
        if (taskDto == null)
            throw new ValidationException("Missing TaskDto");
        if (userId == null)
            throw new ValidationException("Missing userId");
        if (userRepository.findUserById(userId) == null)
            throw new ValidationException("Can't find user");
        //TODO check if user is in
    }
}

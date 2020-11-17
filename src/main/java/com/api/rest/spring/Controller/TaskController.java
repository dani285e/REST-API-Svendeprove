package com.api.rest.spring.Controller;

import com.api.rest.spring.Entity.Dto.TaskDto;
import com.api.rest.spring.handlers.TaskHandler;
import com.api.rest.spring.handlers.exceptions.ValidationException;
import com.api.rest.spring.repository.TaskRepository;
import com.api.rest.spring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.naming.AuthenticationException;
import java.util.List;


@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/add")
    public HttpStatus addTask(@RequestParam String taskName, @RequestParam String taskDesc, @RequestParam Integer taskOwner) {
        try {
            TaskHandler taskHandler = new TaskHandler(taskRepository, userRepository);
            taskHandler.addTask(taskName, taskDesc, taskOwner);
            return HttpStatus.CREATED;
        } catch(ValidationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getErrorReason());
        } catch (Exception e) {
            System.out.println(String.format("Unexpected exception during call addUser: <%s>", e.getMessage()));
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/remove")
    public HttpStatus removeTask(@RequestParam Integer taskId, @RequestParam Integer userId) throws ValidationException, AuthenticationException{
        try {
            //Integer requestingUserId = userId; //TODO change to session userId... Does I need to check session userID with the userID that gets send
            TaskHandler taskHandler = new TaskHandler(taskRepository, userRepository);
            taskHandler.removeTask(userId, taskId);
            return HttpStatus.OK;
        }catch (ValidationException e){
         throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getErrorReason());
        }catch (AuthenticationException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/updateStatus") //TODO find a good name for when you update status
    public HttpStatus updateTaskStatus(@RequestParam Integer taskId, @RequestParam TaskDto taskDto, @RequestParam Integer userId) throws ValidationException, AuthenticationException{
        try {
            TaskHandler taskHandler = new TaskHandler(taskRepository, userRepository);
            taskHandler.updateStatus(taskId, taskDto, userId);
            return HttpStatus.OK;
        } catch (ValidationException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getErrorReason());
        } catch (AuthenticationException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/list")
    public List<TaskDto> taskOverview(@RequestParam Integer userId) throws AuthenticationException, ValidationException {
        try {
            TaskHandler taskHandler = new TaskHandler(taskRepository, userRepository);
            Integer requestingUserId = userId; //TODO change to session userId
            List<TaskDto> taskDtoList =  taskHandler.createTaskList(userId, requestingUserId);

            return taskDtoList;
        } catch (ValidationException e){ //TODO ask Jonas is this exception right or does it need to throw another exception
            throw new ValidationException("Failed to fetch data");
        } catch (AuthenticationException e){
            throw new AuthenticationException("You don't have permission to view this");
        }
    }
}

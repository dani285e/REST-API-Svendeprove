package com.api.rest.spring.Controller;

import com.api.rest.spring.handlers.TaskHandler;
import com.api.rest.spring.handlers.UserHandler;
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


@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @PostMapping("/add")
    public HttpStatus addTask(@RequestParam String taskName, @RequestParam String taskDesc, @RequestParam Integer taskOwner) {
        try {
            TaskHandler taskHandler = new TaskHandler(taskRepository);
            taskHandler.addTask(taskName, taskDesc, taskOwner);
            return HttpStatus.CREATED;
        } catch(ValidationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getErrorReason());
        } catch (Exception e) {
            System.out.println(String.format("Unexpected exception during call addUser: <%s>", e.getMessage()));
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

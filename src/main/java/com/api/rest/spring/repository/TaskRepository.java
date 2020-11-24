package com.api.rest.spring.repository;

import com.api.rest.spring.Entity.Dto.TaskDto;
import com.api.rest.spring.Entity.Task;
import com.api.rest.spring.Entity.User;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface TaskRepository extends CrudRepository<Task, Integer> {
    Task findTaskByTaskName(String username);

    Task findTaskById(Integer id);

    void deleteById(Integer id);

    Iterable<Task> findAllByTaskOwnerOrUsersIn(Integer taskOwner, List<User> users);

}
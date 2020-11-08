package com.api.rest.spring.repository;

import com.api.rest.spring.Entity.Dto.TaskDto;
import com.api.rest.spring.Entity.Task;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends CrudRepository<Task, Integer> {
    Task findTaskByTaskName(String username);
    Task findTaskById(Integer id);
    void deleteById(Integer id);

    @Query("select t.* from dbo.task_users tu inner join dbo.task t on tu.tasks_id = t.id where tu.users_id = :userId")
    List<Task> findAllByUser(@Param("userId") Integer userId);
}


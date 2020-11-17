package com.api.rest.spring.repository;

import com.api.rest.spring.Entity.Comment;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Integer> {
    Comment findCommentById(Integer id);

    List<Comment> findAllByTaskId(Integer id);
}

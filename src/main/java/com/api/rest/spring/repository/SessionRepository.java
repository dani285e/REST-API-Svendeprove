package com.api.rest.spring.repository;

import com.api.rest.spring.Entity.Session;
import com.api.rest.spring.Entity.User;
import org.springframework.data.repository.CrudRepository;

public interface SessionRepository extends CrudRepository<Session, Integer> {
    Session findSessionById(String id);
    Session findSessionByUserId(Integer id);
}
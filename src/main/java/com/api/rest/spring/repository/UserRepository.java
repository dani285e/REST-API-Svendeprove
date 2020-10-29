package com.api.rest.spring.repository;


import com.api.rest.spring.Entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
    User findUserById(Integer id);
}

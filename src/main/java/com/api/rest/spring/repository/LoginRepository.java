package com.api.rest.spring.repository;


import com.api.rest.spring.Entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginRepository extends CrudRepository<User, Integer> {
    User findUserById(Integer id);
}

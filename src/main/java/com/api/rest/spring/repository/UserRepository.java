package com.api.rest.spring.repository;


import com.api.rest.spring.Entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    User findUserByUsername(String username);
    User findUserById(Integer id);
    void deleteById(Integer id);
}

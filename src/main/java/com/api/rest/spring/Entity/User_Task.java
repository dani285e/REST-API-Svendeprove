package com.api.rest.spring.Entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class User_Task {

    @Id
    @GeneratedValue
    private Integer id;
    private Integer taskId;
    private Integer userId;
}

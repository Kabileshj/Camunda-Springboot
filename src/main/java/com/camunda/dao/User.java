package com.camunda.dao;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Users")
@Data

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "firstName", nullable = false)
    private String firstName;

    @Column(name = "lastName", nullable = false)
    private String lastName;

    @Column(name = "age", nullable = false)
    private Integer age;

    @Column(name = "isDeleted", nullable = false)
    private Boolean isDeleted;

    @Column(name = "createdOn")
    private Date createdOn;

    @Column(name = "updatedOn")
    private Date updatedOn;
}
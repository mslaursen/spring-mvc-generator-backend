package com.code.springmvcgenerator.entity;

import com.code.springmvcgenerator.entity.Project;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@ToString
@javax.persistence.Entity
public class User {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "user", cascade = CascadeType.MERGE)
    @ToString.Exclude
    private List<Project> projectList;

    private String email;
    private String password;
}

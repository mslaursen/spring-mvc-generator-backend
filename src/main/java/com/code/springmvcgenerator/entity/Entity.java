package com.code.springmvcgenerator.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@ToString
@javax.persistence.Entity

public class Entity {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonBackReference
    private Project project;

    @OneToMany(mappedBy = "entity", cascade = CascadeType.ALL)
    @ToString.Exclude
    @JsonManagedReference
    private List<Variable> variables;

    @OneToMany(mappedBy = "entity", cascade = CascadeType.ALL)
    @ToString.Exclude
    @JsonManagedReference
    private List<Relation> relations;

    private String name;

    private Boolean hasCreate;
    private Boolean hasReadAll;
    private Boolean hasRead;
    private Boolean hasUpdate;
    private Boolean hasDelete;

}

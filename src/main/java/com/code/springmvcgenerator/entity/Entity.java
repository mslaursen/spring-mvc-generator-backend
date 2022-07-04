package com.code.springmvcgenerator.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@ToString
@javax.persistence.Entity
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Entity {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @OneToMany(mappedBy = "entity", cascade = CascadeType.MERGE)
    @ToString.Exclude
    private List<Variable> variables;

    @OneToMany(mappedBy = "entity", cascade = CascadeType.MERGE)
    @ToString.Exclude
    private List<Relation> relations;

    private String name;

    private Boolean hasCreate;
    private Boolean hasReadAll;
    private Boolean hasRead;
    private Boolean hasUpdate;
    private Boolean hasDelete;

}

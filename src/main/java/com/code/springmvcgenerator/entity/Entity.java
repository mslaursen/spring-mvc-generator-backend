package com.code.springmvcgenerator.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
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
    @JoinColumn(name = "project_id")
    private Project project;

    @OneToMany(mappedBy = "entity", cascade = CascadeType.MERGE)
    @ToString.Exclude
    private List<Variable> variables;

    @OneToMany(mappedBy = "entity", cascade = CascadeType.MERGE)
    @ToString.Exclude
    private List<Relation> relations;

    private String name;
    private String namePlural;

    private Boolean hasCreate;
    private Boolean hasRead;
    private Boolean hasUpdate;
    private Boolean hasDelete;

}

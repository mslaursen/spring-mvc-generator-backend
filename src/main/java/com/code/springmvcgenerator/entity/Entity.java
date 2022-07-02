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

    private String name;
    private String namePlural;

    @OneToMany(mappedBy = "entity", cascade = CascadeType.MERGE)
    @ToString.Exclude
    private List<Variable> variables;

    @OneToMany(mappedBy = "entity", cascade = CascadeType.MERGE)
    @ToString.Exclude
    private List<Relation> relations;

    private Boolean hasCreate;
    private Boolean hasRead;
    private Boolean hasUpdate;
    private Boolean hasDelete;
}

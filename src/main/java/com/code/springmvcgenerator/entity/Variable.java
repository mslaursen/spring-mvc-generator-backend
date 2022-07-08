package com.code.springmvcgenerator.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@javax.persistence.Entity
public class Variable {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String dataType;
    private String columnName;

    @ManyToOne
    @JsonBackReference
    private Entity entity;
}

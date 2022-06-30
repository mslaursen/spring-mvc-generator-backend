package com.code.springmvcgenerator.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
public class Relation {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String relation;
    private String name;
    private String namePlural;

    @ManyToOne
    @JoinColumn(name = "entity_detail_id")
    @JsonBackReference(value = "relationJbr")
    private EntityDetail entityDetail;

}

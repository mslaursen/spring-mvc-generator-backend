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
public class Variable {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String val1;
    private String val2;
    private String val3;

    @ManyToOne
    @JoinColumn(name = "entity_detail_id")
    @JsonBackReference(value = "variableJbr")
    private EntityDetail entityDetail;
}

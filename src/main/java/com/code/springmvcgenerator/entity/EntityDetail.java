package com.code.springmvcgenerator.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
public class EntityDetail {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String entityName;
    private String entityNamePlural;

    @OneToMany(mappedBy = "entityDetail", cascade = CascadeType.MERGE)
    @ToString.Exclude
    private List<Vector3S> variables;

    @OneToMany(mappedBy = "entityDetail", cascade = CascadeType.MERGE)
    @ToString.Exclude
    private List<Vector3S> relations;

    private Boolean hasCreate;
    private Boolean hasRead;
    private Boolean hasUpdate;
    private Boolean hasDelete;
}

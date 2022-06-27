package com.code.springmvcgenerator.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class EntityDetail {
    private String entityName;
    private String entityNamePlural;
    private List<Vector3S> variables;
    private List<Vector3S> relations;
    private Boolean hasCreate;
    private Boolean hasRead;
    private Boolean hasUpdate;
    private Boolean hasDelete;
}

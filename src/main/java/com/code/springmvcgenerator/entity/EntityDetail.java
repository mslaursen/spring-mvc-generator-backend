package com.code.springmvcgenerator.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Locale;

@Getter
@Setter
@ToString
public class EntityDetail {
    private String entityName;
    private String entityNamePlural;
    private List<Vector3S> variables;
    private List<Vector3S> relations;
}

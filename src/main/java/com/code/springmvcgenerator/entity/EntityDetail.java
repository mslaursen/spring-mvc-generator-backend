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
    private List<Vector2S> variables;
    private List<Vector2S> relations;
}

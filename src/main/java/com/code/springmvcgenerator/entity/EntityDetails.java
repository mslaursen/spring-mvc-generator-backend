package com.code.springmvcgenerator.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

@Getter
@Setter
@ToString
public class EntityDetails {
    private String entityName;
    private String entityNamePlural;
    private Map<String, String> variables;
    private Map<String, String> relations;
}

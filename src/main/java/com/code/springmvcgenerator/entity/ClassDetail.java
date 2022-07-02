package com.code.springmvcgenerator.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@ToString

public class ClassDetail {

    private String name;
    private String content;
}

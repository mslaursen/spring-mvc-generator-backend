package com.code.springmvcgenerator.wrapper;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@ToString

public class ClassWrapper {

    private String name;
    private String content;
}

package com.code.springmvcgenerator.service;

import com.code.springmvcgenerator.constants.ClassType;
import com.code.springmvcgenerator.entity.Entity;
import com.code.springmvcgenerator.utils.Util;
import com.code.springmvcgenerator.wrapper.ClassWrapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransformService {

    private byte spacing = 4;
    private final String SPACES = " ".repeat(spacing);


   // public TransformService(byte spacing) {
   //     this.spacing = spacing;
   // }

    private List<ClassWrapper> getClasses(Entity entity) {
        List<ClassWrapper> classes = new ArrayList<>();

        classes.add(toClassByType(entity, ClassType.ENTITY));
        classes.add(toClassByType(entity, ClassType.CONTROLLER));
        classes.add(toClassByType(entity, ClassType.SERVICE));
        classes.add(toClassByType(entity, ClassType.REPOSITORY));

        return classes;
    }

    public List<ClassWrapper> transformToClasses(List<Entity> entities) {
        List<ClassWrapper> classes = new ArrayList<>();

        for (Entity ed : entities) {
            classes.addAll(getClasses(ed));
        }

        return classes;
    }

    private ClassWrapper toClassByType(Entity entity, ClassType type) {
        switch (type) {
            case ENTITY -> {
                return toEntityClass(entity);
            }
            case CONTROLLER -> {
                return toControllerClass(entity);
            }
            case SERVICE -> {
                return toServiceClass(entity);
            }
            case REPOSITORY -> {
                return toRepositoryInterface(entity);
            }

        }
        try {
            throw new Exception();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //

    private ClassWrapper toEntityClass(Entity entity) {
        StringBuilder content = new StringBuilder();

        addClassHeader(content, ClassType.ENTITY, entity.getName());



        ClassWrapper classWrapper = new ClassWrapper();
        classWrapper.setName(entity.getName());
        classWrapper.setContent(content.toString());

        return classWrapper;
    }

    private ClassWrapper toControllerClass(Entity entity) {
        return null;
    }

    private ClassWrapper toServiceClass(Entity entity) {
        return null;
    }

    private ClassWrapper toRepositoryInterface(Entity entity) {
        return null;
    }

    // String constructor methods

    private void addClassHeader(StringBuilder sb, ClassType classType, String className) {
        sb.append("public ")
                .append(Util.capitalize(classType.toString().toLowerCase()))
                .append(className)
                .append(" {\n\n");

    }

    private void newLine(String str, byte spacing) {

    }

    private void addId() {

    }

    private String addVariable(String modifier, String dataType, String name) {
        return null;
    }

    private String addMethod(String modifier, String dataType, String name) {
        return null;
    }

    private String addMethodContentLine(String variableName, String dataType, String value) {
        return null;
    }

    private String returnValue(String value) {
        return null;
    }
}

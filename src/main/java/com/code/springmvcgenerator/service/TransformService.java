package com.code.springmvcgenerator.service;

import com.code.springmvcgenerator.constants.ClassType;
import com.code.springmvcgenerator.entity.Entity;
import com.code.springmvcgenerator.entity.Relation;
import com.code.springmvcgenerator.entity.Variable;
import com.code.springmvcgenerator.utils.Util;
import com.code.springmvcgenerator.wrapper.ClassWrapper;
import org.aspectj.weaver.ast.Var;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class TransformService {

    private byte spacing = 4;

   // public TransformService(byte spacing) {
   //     this.spacing = spacing;
   // }

    private List<ClassWrapper> getClasses(Entity entity) {
        List<ClassWrapper> classes = new ArrayList<>();

        classes.add(toClassByType(entity, ClassType.ENTITY));
        //classes.add(toClassByType(entity, ClassType.CONTROLLER));
        //classes.add(toClassByType(entity, ClassType.SERVICE));
        //classes.add(toClassByType(entity, ClassType.REPOSITORY));

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

        newLine(content, "@Getter");
        newLine(content, "@Setter");
        newLine(content, "@ToString");
        newLine(content, "@Entity");
        addClassHeader(content, ClassType.ENTITY, entity.getName());

        addId(content);
        
        for (Relation relation : entity.getRelations()) {
            addRelation(content, relation, entity.getName());
            breakLine(content);
        }

        for (Variable variable : entity.getVariables()) {
            addVariable(content, variable);
        }
        
        ClassWrapper classWrapper = new ClassWrapper();
        classWrapper.setName(entity.getName());
        classWrapper.setContent(content.toString());

        System.out.println(content);
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

    private void addClassHeader(StringBuilder content, ClassType classType, String className) {
        String type = "";
        switch (classType) {
            case ENTITY, CONTROLLER, SERVICE -> type = "class";
            case REPOSITORY -> type = "interface";
        }

        content.append("public ")
                .append(type)
                .append(" ")
                .append(className)
                .append(" {");
        breakLine(content);
        breakLine(content);
    }

    private void newLine(StringBuilder content, String str) {
        content.append(str);
        breakLine(content);
    }

    private void newLineSpaced(StringBuilder content, String str, String spacing) {
        content.append(spacing);
        content.append(str);
        breakLine(content);
    }
    
    private void line(StringBuilder content, String str) {
        content.append(str);
    }

    private void lineSpaced(StringBuilder sb, String str, String spacing) {
        sb.append(spacing);
        sb.append(str);
    }

    private void breakLine(StringBuilder sb) {
        sb.append("\n");
    }

    private void addId(StringBuilder content) {

    }

    private void addVariable(StringBuilder content, Variable variable) {
        if (variable.getColumnName() != null && !variable.getColumnName().isEmpty()) {
            newLineSpaced(content, "@Column(name = \"" + variable.getColumnName() + "\")", getSpaces(spacing));
        }
        newLineSpaced(content, "private " + variable.getDataType() + " " + variable.getName() + ";", getSpaces(spacing));
    }

    private void addRelation(StringBuilder content, Relation relation, String entityName) {

        if (Objects.equals(relation.getAnnotation(), "OneToMany")) {
            newLineSpaced(content, "@" + relation.getAnnotation() + "(mappedBy = \""
                    + Util.decapitalize(entityName) + "\", cascade = CascadeType.MERGE)" , getSpaces(spacing));

            newLineSpaced(content, "@ToString.Exclude", getSpaces(spacing));

            lineSpaced(content, "private List<"
                    + relation.getRelatedTo() + "> "
                    + Util.decapitalize(relation.getRelatedTo()) + "List", getSpaces(spacing));
        } else {
            newLineSpaced(content, "@" + relation.getAnnotation(), getSpaces(spacing));

            newLineSpaced(content, "@JoinColumn(name = \""
                    + Util.decapitalize(relation.getRelatedTo()) + "_id\")", getSpaces(spacing));

            lineSpaced(content, "private " + relation.getRelatedTo()
                    + " " + Util.decapitalize(relation.getRelatedTo()), getSpaces(spacing));
        }
        breakLine(content);


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

    private String getSpaces(byte spacing) {
        return " ".repeat(spacing);
    }
}

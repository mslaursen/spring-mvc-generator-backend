package com.code.springmvcgenerator.service;

import com.code.springmvcgenerator.constants.ClassType;
import com.code.springmvcgenerator.entity.Entity;
import com.code.springmvcgenerator.entity.Relation;
import com.code.springmvcgenerator.entity.Variable;
import com.code.springmvcgenerator.utils.Util;
import com.code.springmvcgenerator.wrapper.ClassWrapper;
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

    private ClassWrapper toEntityClass(Entity entity) {
        StringBuilder content = new StringBuilder();

        newLine(content, "@Getter");
        newLine(content, "@Setter");
        newLine(content, "@ToString");
        newLine(content, "@Entity");
        addClassHeader(content, ClassType.ENTITY, entity.getName());

        addId(content);

        breakLine(content);
        
        for (Relation relation : entity.getRelations()) {
            addRelation(content, relation, entity.getName());
            breakLine(content);
        }

        for (Variable variable : entity.getVariables()) {
            addVariable(content, variable);
        }

        newLine(content, "}");
        
        ClassWrapper classWrapper = new ClassWrapper();
        classWrapper.setName(entity.getName());
        classWrapper.setContent(content.toString());

        return classWrapper;
    }

    private ClassWrapper toControllerClass(Entity entity) {
        StringBuilder content = new StringBuilder();

        newLine(content, "@RestController");
        newLine(content, "@RequestMapping(\"/api/" + Util.pluralize(entity.getName()) + "\")");
        newLine(content, "@CrossOrigin");
        addClassHeader(content, ClassType.CONTROLLER, entity.getName() + "Controller");

        newLineSpaced(content, "private final " + entity.getName()
                + "Service " + entity.getName().toLowerCase() + "Service;", getSpaces(spacing));
        breakLine(content);

        newLineSpaced(content, "@Autowired", getSpaces(spacing));
        addConstructor(content, entity.getName(), ClassType.CONTROLLER, ClassType.SERVICE);
        breakLine(content);

        addCrud(content, entity, ClassType.CONTROLLER);

        newLine(content, "}");

        ClassWrapper classWrapper = new ClassWrapper();
        classWrapper.setName(entity.getName() + "Controller");
        classWrapper.setContent(content.toString());

        return classWrapper;
    }

    private ClassWrapper toServiceClass(Entity entity) {
        StringBuilder content = new StringBuilder();

        newLine(content, "@Service");
        addClassHeader(content, ClassType.SERVICE, entity.getName() + "Service");

        newLineSpaced(content, "private final " + entity.getName()
                + "Repository " + entity.getName().toLowerCase() + "Repository;", getSpaces(spacing));
        breakLine(content);

        newLineSpaced(content, "@Autowired", getSpaces(spacing));
        addConstructor(content, entity.getName(), ClassType.SERVICE, ClassType.REPOSITORY);
        breakLine(content);
        
        addCrud(content, entity, ClassType.SERVICE);

        newLine(content, "}");

        ClassWrapper classWrapper = new ClassWrapper();
        classWrapper.setName(entity.getName() + "Service");
        classWrapper.setContent(content.toString());

        return classWrapper;
    }

    private ClassWrapper toRepositoryInterface(Entity entity) {
        StringBuilder content = new StringBuilder();

        newLine(content, "public interface " + entity.getName()
                + "Repository extends JpaRepository<" + entity.getName() + ", Long> {");
        newLine(content, "}");

        ClassWrapper classWrapper = new ClassWrapper();
        classWrapper.setName(entity.getName() + "Repository");
        classWrapper.setContent(content.toString());

        return classWrapper;
    }

    private void addCrud(StringBuilder content, Entity entity, ClassType type) {
        if (entity.getHasCreate()) {
            addCreate(content, entity.getName(), type);
            breakLine(content);
        }

        if (entity.getHasReadAll()) {
            addReadAll(content, entity.getName(), type);
            breakLine(content);
        }

        if (entity.getHasRead()) {
            addRead(content, entity.getName(), type);
            breakLine(content);
        }

        if (entity.getHasUpdate()) {
            addUpdate(content, entity, type);
            breakLine(content);
        }

        if (entity.getHasDelete()) {
            addDelete(content, entity.getName(), type);
            breakLine(content);
        }
    }

    private void addCreate(StringBuilder content, String name, ClassType type) {
        if (type == ClassType.CONTROLLER) {
            newLineSpaced(content, "@PostMapping", getSpaces(spacing));
            newLineSpaced(content, "public ResponseEntity<" + name + "> create(@RequestBody "
                    + name + " " + Util.decapitalize(name) + ") {", getSpaces(spacing));

            newLineSpaced(content, name + " saved" + name + " = " + Util.decapitalize(name)
                    + "Service.save(" + Util.decapitalize(name) + ");", getSpaces((byte) (spacing * 2)));

            returnResponseEntity(content, "saved" + name);
        }
        else if (type == ClassType.SERVICE) {
            newLineSpaced(content, "public " + name + " save("
                    + name + " " + Util.decapitalize(name) + ") {", getSpaces(spacing));

            newLineSpaced(content, "return " + Util.decapitalize(name)
                    + "Repository.save(" + Util.decapitalize(name) + ");", getSpaces((byte) (spacing * 2)));

            newLineSpaced(content, "}", getSpaces(spacing));
        }
    }

    private void addReadAll(StringBuilder content, String name, ClassType type) {
        if (type == ClassType.CONTROLLER) {
            newLineSpaced(content, "@GetMapping", getSpaces(spacing));
            newLineSpaced(content, "public ResponseEntity<List<" + name + ">> fetchAll() {", getSpaces(spacing));

            newLineSpaced(content, "List<" + name + "> found = "
                    + Util.decapitalize(name) + "Service.findAll();", getSpaces((byte) (spacing * 2)));

            returnResponseEntity(content, "found");
        }
        else if (type == ClassType.SERVICE) {
            newLineSpaced(content, "public List<" + name + "> findAll() {", getSpaces(spacing));

            newLineSpaced(content, "return " + Util.decapitalize(name) + "Repository.findAll();"
                    , getSpaces((byte) (spacing * 2)));

            newLineSpaced(content, "}", getSpaces(spacing));
        }
    }

    private void addRead(StringBuilder content, String name, ClassType type) {
        if (type == ClassType.CONTROLLER) {
            newLineSpaced(content, "@GetMapping(\"/{id}\")", getSpaces(spacing));
            newLineSpaced(content, "public ResponseEntity<" + name
                    + "> fetchById(@PathVariable Long id) {", getSpaces(spacing));

            newLineSpaced(content, name + " found = " + Util.decapitalize(name)
                    + "Service.findById(id);", getSpaces((byte) (spacing * 2)));

            returnResponseEntity(content, "found");
        }
        else if (type == ClassType.SERVICE) {
            newLineSpaced(content, "public " + name + " findById(Long id) {", getSpaces(spacing));

            newLineSpaced(content, "return " + Util.decapitalize(name)
                    + "Repository.findById(id).orElseThrow();", getSpaces((byte) (spacing * 2)));

            newLineSpaced(content, "}", getSpaces(spacing));
        }
    }

    private void addUpdate(StringBuilder content, Entity entity, ClassType type) {
        if (type == ClassType.CONTROLLER) {
            newLineSpaced(content, "@PutMapping(\"/{id}\")", getSpaces(spacing));

            newLineSpaced(content, "public ResponseEntity<" + entity.getName()
                    + "> updateById(@RequestBody " + entity.getName() + " "
                    + Util.decapitalize(entity.getName()) + ", @PathVariable Long id) {", getSpaces(spacing));

            newLineSpaced(content, entity.getName() + " toUpdate = "
                    + Util.decapitalize(entity.getName()) + "Service.findById(id);", getSpaces((byte) (spacing * 2)));

            for (Variable v : entity.getVariables()) {
                newLineSpaced(content, "toUpdate.set" + Util.capitalize(v.getName()) + "("
                        + Util.decapitalize(entity.getName()) + ".get"
                        + Util.capitalize(v.getName()) + "());", getSpaces((byte) (spacing * 2)));
            }

            breakLine(content);
            newLineSpaced(content, entity.getName() + " updated = "
                    + Util.decapitalize(entity.getName())
                    + "Service.update(toUpdate);", getSpaces((byte) (spacing * 2)));

            returnResponseEntity(content, "updated");
        }
        else if (type == ClassType.SERVICE) {
            newLineSpaced(content, "public " + entity.getName()
                    + " update(" + entity.getName() + " toUpdate) {", getSpaces(spacing));

            newLineSpaced(content, "return " + Util.decapitalize(entity.getName())
                    + "Repository.save(toUpdate);", getSpaces((byte) (spacing * 2)));

            newLineSpaced(content, "}", getSpaces(spacing));
        }
    }

    private void addDelete(StringBuilder content, String name, ClassType type) {
        if (type == ClassType.CONTROLLER) {
            newLineSpaced(content, "@DeleteMapping(\"/{id}\")", getSpaces(spacing));
            newLineSpaced(content, "public ResponseEntity<Object> deleteById(@PathVariable Long id) {",
                    getSpaces(spacing));

            newLineSpaced(content, Util.decapitalize(name) + "Service.deleteById(id);",
                    getSpaces((byte) (spacing * 2)));

            returnBodylessResponseEntity(content);
        }
        else if (type == ClassType.SERVICE) {
            newLineSpaced(content, "public void deleteById(Long id) {",
                    getSpaces(spacing));

            newLineSpaced(content, Util.decapitalize(name) + "Repository.deleteById(id);",
                    getSpaces((byte) (spacing * 2)));

            newLineSpaced(content, "}", getSpaces(spacing));
        }
    }

    private void addConstructor(StringBuilder content, String entityName, ClassType type1, ClassType type2) {
        newLineSpaced(content, "public " + entityName
                + Util.capitalize(type1.toString()) + "(" + entityName
                + Util.capitalize(type2.toString()) + " " + Util.decapitalize(entityName)
                + Util.capitalize(type2.toString()) + ") {"
                , getSpaces(spacing));

        newLineSpaced(content, "this." + Util.decapitalize(entityName) + Util.capitalize(type2.toString()) + " = "
                + Util.decapitalize(entityName) + Util.capitalize(type2.toString()) + ";"
                , getSpaces((byte) (spacing*2)));

        newLineSpaced(content, "}", getSpaces(spacing));
    }

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
        newLineSpaced(content, "@Id", getSpaces(spacing));
        newLineSpaced(content, "@Column(name = \"id\", nullable = false)", getSpaces(spacing));
        newLineSpaced(content, "@GeneratedValue(strategy = GenerationType.IDENTITY)", getSpaces(spacing));
        newLineSpaced(content, "private Long id;", getSpaces(spacing));
    }

    private void addVariable(StringBuilder content, Variable variable) {
        if (variable.getColumnName() != null && !variable.getColumnName().isEmpty()) {
            newLineSpaced(content, "@Column(name = \"" + variable.getColumnName() + "\")", getSpaces(spacing));
        }
        newLineSpaced(content, "private " + variable.getDataType()
                + " " + variable.getName() + ";", getSpaces(spacing));
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
                    + " " + Util.decapitalize(relation.getRelatedTo()) + ";", getSpaces(spacing));
        }
        breakLine(content);
    }

    private void returnResponseEntity(StringBuilder content, String entityName) {
        newLineSpaced(content, "return ResponseEntity.ok()", getSpaces((byte) (spacing*2)));
        newLineSpaced(content, ".body(" + entityName + ");", getSpaces((byte) (spacing*3)));
        newLineSpaced(content, "}", getSpaces(spacing));
    }

    private void returnBodylessResponseEntity(StringBuilder content) {
        newLineSpaced(content, "return ResponseEntity.ok()", getSpaces((byte) (spacing*2)));
        newLineSpaced(content, ".build();", getSpaces((byte) (spacing*3)));
        newLineSpaced(content, "}", getSpaces(spacing));
    }

    private String getSpaces(byte spacing) {
        return " ".repeat(spacing);
    }
}

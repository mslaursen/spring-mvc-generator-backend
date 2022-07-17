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

        addImports(content, entity, ClassType.ENTITY);

        newLine(content, "@Getter");
        newLine(content, "@Setter");
        newLine(content, "@ToString");
        newLine(content, "@javax.persistence.Entity");
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

        addImports(content, entity, ClassType.CONTROLLER);

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

        addImports(content, entity, ClassType.SERVICE);

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

        addImports(content, entity, ClassType.REPOSITORY);

        newLine(content, "public interface " + entity.getName()
                + "Repository extends JpaRepository<" + entity.getName() + ", Long> {");
        newLine(content, "}");

        ClassWrapper classWrapper = new ClassWrapper();
        classWrapper.setName(entity.getName() + "Repository");
        classWrapper.setContent(content.toString());

        return classWrapper;
    }

    private boolean checkForAnnotation(Entity entity, String annotation) {
        for (Relation r : entity.getRelations()) {
            if (r.getAnnotation().equals(annotation)) {
                return true;
            }
        }
        return false;
    }

    private void addImports(StringBuilder content, Entity entity, ClassType type) {
        boolean hasReadAll = entity.getHasReadAll();
        boolean hasOneToMany = checkForAnnotation(entity, "OneToMany");
        boolean hasManyToOne = checkForAnnotation(entity, "ManyToOne");

        switch (type) {
            case ENTITY -> {
                if (hasManyToOne) {
                    newLine(content, "import com.fasterxml.jackson.annotation.JsonBackReference;");
                }

                if (hasOneToMany) {
                    newLine(content, "import com.fasterxml.jackson.annotation.JsonManagedReference;");
                }


                newLine(content, "import lombok.Getter;");
                newLine(content, "import lombok.Setter;");
                newLine(content, "import lombok.ToString;");


                breakLine(content);
                newLine(content, "import javax.persistence.*;");
                if (hasOneToMany) {
                    newLine(content, "import java.util.List;");
                }
                breakLine(content);

            }
            case CONTROLLER -> {
                newLine(content, "import org.springframework.beans.factory.annotation.Autowired;");
                newLine(content, "import org.springframework.http.ResponseEntity;");
                newLine(content, "import org.springframework.web.bind.annotation.*;");
                breakLine(content);

                if (hasReadAll) {
                    newLine(content, "import java.util.List;");
                }

                breakLine(content);
            }
            case SERVICE -> {
                newLine(content, "import org.springframework.beans.factory.annotation.Autowired;");
                newLine(content, "import org.springframework.stereotype.Service;");
                breakLine(content);

                if (hasReadAll) {
                    newLine(content, "import java.util.List;");
                }

                breakLine(content);
            }
            case REPOSITORY -> {
                newLine(content, "import org.springframework.data.jpa.repository.JpaRepository;");
                breakLine(content);
            }
        }
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
        String nameDecapitalized = Util.decapitalize(name);


        if (type == ClassType.CONTROLLER) {
            newLineSpaced(content, "@PostMapping", getSpaces(spacing));
            newLineSpaced(content, "public ResponseEntity<" + name + "> create(@RequestBody "
                    + name + " " + nameDecapitalized + ") {", getSpaces(spacing));

            newLineSpaced(content, name + " saved" + name + " = " + nameDecapitalized
                    + "Service.save(" + nameDecapitalized + ");", getSpaces((byte) (spacing * 2)));

            returnResponseEntity(content, "saved" + name);
        }
        else if (type == ClassType.SERVICE) {
            newLineSpaced(content, "public " + name + " save("
                    + name + " " + nameDecapitalized + ") {", getSpaces(spacing));

            newLineSpaced(content, "return " + nameDecapitalized
                    + "Repository.save(" + nameDecapitalized + ");", getSpaces((byte) (spacing * 2)));

            newLineSpaced(content, "}", getSpaces(spacing));
        }
    }

    private void addReadAll(StringBuilder content, String name, ClassType type) {
        String nameDecapitalized = Util.decapitalize(name);

        if (type == ClassType.CONTROLLER) {
            newLineSpaced(content, "@GetMapping", getSpaces(spacing));
            newLineSpaced(content, "public ResponseEntity<List<" + name + ">> fetchAll() {", getSpaces(spacing));

            newLineSpaced(content, "List<" + name + "> found = "
                    + nameDecapitalized + "Service.findAll();", getSpaces((byte) (spacing * 2)));

            returnResponseEntity(content, "found");
        }
        else if (type == ClassType.SERVICE) {
            newLineSpaced(content, "public List<" + name + "> findAll() {", getSpaces(spacing));

            newLineSpaced(content, "return " + nameDecapitalized + "Repository.findAll();"
                    , getSpaces((byte) (spacing * 2)));

            newLineSpaced(content, "}", getSpaces(spacing));
        }
    }

    private void addRead(StringBuilder content, String name, ClassType type) {
        String nameDecapitalized = Util.decapitalize(name);

        if (type == ClassType.CONTROLLER) {
            newLineSpaced(content, "@GetMapping(\"/{id}\")", getSpaces(spacing));
            newLineSpaced(content, "public ResponseEntity<" + name
                    + "> fetchById(@PathVariable Long id) {", getSpaces(spacing));

            newLineSpaced(content, name + " found = " + nameDecapitalized
                    + "Service.findById(id);", getSpaces((byte) (spacing * 2)));

            returnResponseEntity(content, "found");
        }
        else if (type == ClassType.SERVICE) {
            newLineSpaced(content, "public " + name + " findById(Long id) {", getSpaces(spacing));

            newLineSpaced(content, "return " + nameDecapitalized
                    + "Repository.findById(id).orElseThrow();", getSpaces((byte) (spacing * 2)));

            newLineSpaced(content, "}", getSpaces(spacing));
        }
    }

    private void addUpdate(StringBuilder content, Entity entity, ClassType type) {
        String nameDecapitalized = Util.decapitalize(entity.getName());

        if (type == ClassType.CONTROLLER) {
            newLineSpaced(content, "@PutMapping(\"/{id}\")", getSpaces(spacing));

            newLineSpaced(content, "public ResponseEntity<" + entity.getName()
                    + "> updateById(@RequestBody " + entity.getName() + " "
                    + nameDecapitalized + ", @PathVariable Long id) {", getSpaces(spacing));

            newLineSpaced(content, entity.getName() + " toUpdate = "
                    + nameDecapitalized + "Service.findById(id);", getSpaces((byte) (spacing * 2)));

            for (Variable v : entity.getVariables()) {
                String vNameCapitalized = Util.capitalize(v.getName());

                newLineSpaced(content, "toUpdate.set" + vNameCapitalized + "("
                        + nameDecapitalized + ".get"
                        + vNameCapitalized + "());", getSpaces((byte) (spacing * 2)));
            }

            breakLine(content);
            newLineSpaced(content, entity.getName() + " updated = "
                    + nameDecapitalized
                    + "Service.update(toUpdate);", getSpaces((byte) (spacing * 2)));

            returnResponseEntity(content, "updated");
        }
        else if (type == ClassType.SERVICE) {
            newLineSpaced(content, "public " + entity.getName()
                    + " update(" + entity.getName() + " toUpdate) {", getSpaces(spacing));

            newLineSpaced(content, "return " + nameDecapitalized
                    + "Repository.save(toUpdate);", getSpaces((byte) (spacing * 2)));

            newLineSpaced(content, "}", getSpaces(spacing));
        }
    }

    private void addDelete(StringBuilder content, String name, ClassType type) {
        String nameDecapitalized = Util.decapitalize(name);

        if (type == ClassType.CONTROLLER) {
            newLineSpaced(content, "@DeleteMapping(\"/{id}\")", getSpaces(spacing));
            newLineSpaced(content, "public ResponseEntity<Object> deleteById(@PathVariable Long id) {",
                    getSpaces(spacing));

            newLineSpaced(content, nameDecapitalized + "Service.deleteById(id);",
                    getSpaces((byte) (spacing * 2)));

            returnBodylessResponseEntity(content);
        }
        else if (type == ClassType.SERVICE) {
            newLineSpaced(content, "public void deleteById(Long id) {",
                    getSpaces(spacing));

            newLineSpaced(content, nameDecapitalized + "Repository.deleteById(id);",
                    getSpaces((byte) (spacing * 2)));

            newLineSpaced(content, "}", getSpaces(spacing));
        }
    }

    private void addConstructor(StringBuilder content, String entityName, ClassType type1, ClassType type2) {
        String nameDecapitalized = Util.decapitalize(entityName);

        newLineSpaced(content, "public " + entityName
                + Util.capitalize(type1.toString()) + "(" + entityName
                + Util.capitalize(type2.toString()) + " " + nameDecapitalized
                + Util.capitalize(type2.toString()) + ") {"
                , getSpaces(spacing));

        newLineSpaced(content, "this." + nameDecapitalized + Util.capitalize(type2.toString()) + " = "
                + nameDecapitalized + Util.capitalize(type2.toString()) + ";"
                , getSpaces((byte) (spacing*2)));

        newLineSpaced(content, "}", getSpaces(spacing));
    }

    private void addClassHeader(StringBuilder content, ClassType classType, String className) {
        String type = "";
        switch (classType) {
            case ENTITY, CONTROLLER, SERVICE -> type = "class";
            case REPOSITORY -> type = "interface";
        }

        newLine(content, "public " + type + " " + className + " {");
        breakLine(content);
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
        String relationDecapitalized = Util.decapitalize(relation.getRelatedTo());

        if (Objects.equals(relation.getAnnotation(), "OneToMany")) {
            newLineSpaced(content, "@" + relation.getAnnotation() + "(mappedBy = \""
                    + Util.decapitalize(entityName) + "\", cascade = CascadeType.ALL)" , getSpaces(spacing));

            newLineSpaced(content, "@ToString.Exclude", getSpaces(spacing));
            newLineSpaced(content, "@JsonManagedReference", getSpaces(spacing));

            lineSpaced(content, "private List<"
                    + relation.getRelatedTo() + "> "
                    + relationDecapitalized + "List", getSpaces(spacing));
        }
        else if (Objects.equals(relation.getAnnotation(), "ManyToOne")) {
            newLineSpaced(content, "@" + relation.getAnnotation(), getSpaces(spacing));
            newLineSpaced(content, "@JsonBackReference", getSpaces(spacing));

            lineSpaced(content, "private " + relation.getRelatedTo()
                    + " " + relationDecapitalized + ";", getSpaces(spacing));
        } else {
            newLineSpaced(content, "@" + relation.getAnnotation(), getSpaces(spacing));

            lineSpaced(content, "private " + relation.getRelatedTo()
                    + " " + relationDecapitalized + ";", getSpaces(spacing));
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

    // StringBuilder ---------->

    private void newLine(StringBuilder content, String str) {
        content.append(str);
        breakLine(content);
    }

    private void newLineSpaced(StringBuilder content, String str, String spacing) {
        content.append(spacing);
        content.append(str);
        breakLine(content);
    }

    private void lineSpaced(StringBuilder sb, String str, String spacing) {
        sb.append(spacing);
        sb.append(str);
    }

    private void breakLine(StringBuilder sb) {
        sb.append("\n");
    }
}

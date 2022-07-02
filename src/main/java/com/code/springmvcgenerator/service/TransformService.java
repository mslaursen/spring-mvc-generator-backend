package com.code.springmvcgenerator.service;

import com.code.springmvcgenerator.constants.ClassType;
import com.code.springmvcgenerator.wrapper.ClassWrapper;
import com.code.springmvcgenerator.entity.Entity;
import com.code.springmvcgenerator.entity.Relation;
import com.code.springmvcgenerator.entity.Variable;
import com.code.springmvcgenerator.utils.Util;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransformService {

    private final byte SPACING = 4;
    private final String SPACES = " ".repeat(SPACING);

    private List<ClassWrapper> getClasses(Entity entity) {
        List<ClassWrapper> classes = new ArrayList<>();

        classes.add(toClassByClassType(entity, ClassType.ENTITY));
        classes.add(toClassByClassType(entity, ClassType.CONTROLLER));
        classes.add(toClassByClassType(entity, ClassType.SERVICE));
        classes.add(toClassByClassType(entity, ClassType.REPOSITORY));

        return classes;
    }

    public List<ClassWrapper> transformToClass(List<Entity> entities) {
        List<ClassWrapper> classes = new ArrayList<>();

        for (Entity ed : entities) {
            classes.addAll(getClasses(ed));
        }

        return classes;
    }


    private ClassWrapper toClassByClassType(Entity entity, ClassType classType) {
        String dependency = "";

        switch (classType) {
            case CONTROLLER -> dependency = "Service";
            case SERVICE -> dependency = "Repository";
            case REPOSITORY -> {
                return toRepositoryInterface(entity);
            }
            case ENTITY -> {
                return toEntityClass(entity);
            }
        }

        ClassWrapper classWrapper = new ClassWrapper();
        StringBuilder sb = new StringBuilder();

        String type = Util.capitalize(classType.toString().toLowerCase());
        String entityNameLowerDependency = Util.decapitalize(entity.getName()) + dependency;
        String entityNameLower = Util.decapitalize(entity.getName());
        String entityNamePluralLower = Util.decapitalize(entity.getNamePlural());

        sb.append("@RestController\n")
                .append("@RequestMapping(\"/api/")
                .append(entity.getNamePlural().toLowerCase())
                .append("\")\n")
                .append("@CrossOrigin\n")
                .append("public class ")
                .append(entity.getName())
                .append(type)
                .append(" {\n\n");

        sb.append(SPACES)
                .append("private final ")
                .append(entity.getName())
                .append(dependency)
                .append(" ")
                .append(entityNameLowerDependency)
                .append(";\n\n");

        sb.append(SPACES)
                .append("@Autowired\n")
                .append(SPACES)
                .append("public ")
                .append(entity.getName())
                .append("Controller(")
                .append(entity.getName())
                .append("Service ")
                .append(entityNameLowerDependency)
                .append(") {\n")
                .append(SPACES)
                .append(SPACES)
                .append("this.")
                .append(entityNameLowerDependency)
                .append(" = ")
                .append(entityNameLowerDependency)
                .append(";\n")
                .append(SPACES)
                .append("}\n\n");

        if (entity.getHasCreate()) {
            sb.append(SPACES)
                    .append("@PostMapping\n")
                    .append(SPACES)
                    .append("public ResponseEntity<")
                    .append(entity.getName())
                    .append("> ")
                    .append("create")
                    .append("(@RequestBody ")
                    .append(entity.getName())
                    .append(" ")
                    .append(entityNameLower)
                    .append(") {\n");

            sb.append(SPACES)
                    .append(SPACES)
                    .append(entity.getName())
                    .append(" saved")
                    .append(entity.getName())
                    .append(" = ")
                    .append(entityNameLowerDependency)
                    .append(".save(")
                    .append(entityNameLower)
                    .append(");\n");

            sb.append(SPACES)
                    .append(SPACES)
                    .append("return ResponseEntity.ok()\n")
                    .append(SPACES)
                    .append(SPACES)
                    .append(SPACES)
                    .append(SPACES)
                    .append(".body(")
                    .append("saved")
                    .append(entity.getName())
                    .append(");\n");

            sb.append(SPACES)
                    .append("}\n\n");
        }

        if (entity.getHasRead()) {
            sb.append(SPACES)
                    .append("@GetMapping\n")
                    .append(SPACES)
                    .append("public ResponseEntity<List<")
                    .append(entity.getName())
                    .append(">> ")
                    .append("read")
                    .append("() {\n");

            sb.append(SPACES)
                    .append(SPACES)
                    .append("List<")
                    .append(entity.getName())
                    .append("> ")
                    .append(entityNamePluralLower)
                    .append(" = ")
                    .append(entityNameLowerDependency)
                    .append(".read(")
                    .append(");\n");

            sb.append(SPACES)
                    .append(SPACES)
                    .append("return ResponseEntity.ok()\n")
                    .append(SPACES)
                    .append(SPACES)
                    .append(SPACES)
                    .append(SPACES)
                    .append(".body(")
                    .append(entityNamePluralLower)
                    .append(");\n");

            sb.append(SPACES)
                    .append("}\n\n");
        }

        if (entity.getHasUpdate()) {
            sb.append(SPACES)
                    .append("@PutMapping\n")
                    .append(SPACES)
                    .append("public ResponseEntity<")
                    .append(entity.getName())
                    .append("> ")
                    .append("update")
                    .append("(@RequestBody ")
                    .append(entity.getName())
                    .append(" ")
                    .append(entityNameLower)
                    .append(") {\n");

            sb.append(SPACES)
                    .append(SPACES)
                    .append(entity.getName())
                    .append(" updated")
                    .append(entity.getName())
                    .append(" = ")
                    .append(entityNameLowerDependency)
                    .append(".save(")
                    .append(entityNameLower)
                    .append(");\n");

            sb.append(SPACES)
                    .append(SPACES)
                    .append("return ResponseEntity.ok()\n")
                    .append(SPACES)
                    .append(SPACES)
                    .append(SPACES)
                    .append(SPACES)
                    .append(".body(")
                    .append("updated")
                    .append(entity.getName())
                    .append(");\n");

            sb.append(SPACES)
                    .append("}\n\n");
        }

        if (entity.getHasDelete()) {
            sb.append(SPACES)
                    .append("@DeleteMapping(\"/{id}\")\n")
                    .append(SPACES)
                    .append("public ResponseEntity<")
                    .append(entity.getName())
                    .append("> ")
                    .append("deleteById")
                    .append("(@PathVariable Long id) {\n");

            sb.append(SPACES)
                    .append(SPACES)
                    .append(entityNameLowerDependency)
                    .append(".deleteById(id);\n");

            sb.append(SPACES)
                    .append(SPACES)
                    .append("return ResponseEntity.ok()\n")
                    .append(SPACES)
                    .append(SPACES)
                    .append(SPACES)
                    .append(SPACES)
                    .append(".build();\n");

            sb.append(SPACES)
                    .append("}");
        }

        sb.append("\n}\n");

        classWrapper.setName(entity.getName() + type);
        classWrapper.setContent(sb.toString());

        return classWrapper;
    }

    private ClassWrapper toEntityClass(Entity entity) {
        ClassWrapper classWrapper = new ClassWrapper();

        StringBuilder sb  = new StringBuilder()
                .append("@Getter\n")
                .append("@Setter\n")
                .append("@ToString\n")
                .append("@Entity\n")
                .append("public class ")
                .append(entity.getName())
                .append(" {\n")
                .append(SPACES)
                .append("@Id\n")
                .append(SPACES)
                .append("@Column(name = \"").append(entity.getName().toLowerCase()).append("_id\")\n")
                .append(SPACES)
                .append("@GeneratedValue(strategy = GenerationType.IDENTITY)\n")
                .append(SPACES)
                .append("private Long id;\n");

        // Relationship columns
        for (Relation v : entity.getRelations()) {
            sb.append(SPACES);
            switch (v.getVal1()) {
                case "ManyToOne", "OneToOne" -> sb
                        .append("\n")
                        .append(SPACES)
                        .append("@")
                        .append(v.getVal1())
                        .append("(cascade = CascadeType.MERGE)\n")
                        .append(SPACES)
                        .append("@JoinColumn(name = \"")
                        .append(v.getVal2().toLowerCase())
                        .append("_id\")\n")
                        .append(SPACES)
                        .append("private ")
                        .append(v.getVal2())
                        .append(" ")
                        .append(v.getVal2().toLowerCase())
                        .append(";\n");
                case "OneToMany" -> sb
                        .append("\n")
                        .append(SPACES)
                        .append("@JsonBackReference(value = \"")
                        .append(v.getVal3().toLowerCase())
                        .append("\")\n")
                        .append(SPACES)
                        .append("@")
                        .append(v.getVal1())
                        .append("(mappedBy = \"")
                        .append(entity.getName().toLowerCase())
                        .append("\", cascade = CascadeType.MERGE)\n")
                        .append(SPACES)
                        .append("@ToString.Exclude\n")
                        .append(SPACES)
                        .append("private List<")
                        .append(v.getVal2())
                        .append("> ")
                        .append(v.getVal3().toLowerCase())
                        .append(";\n");
            }
        }

        // Columns
        for (Variable v : entity.getVariables()) {
            sb.append(SPACES);

            if (v.getVal3() != null) {
                sb.append("\n")
                        .append(SPACES)
                        .append("@Column(name = \"")
                        .append(v.getVal3())
                        .append("\")");
            }

            sb.append("\n")
                    .append(SPACES)
                    .append("private ")
                    .append(v.getVal1())
                    .append(" ")
                    .append(v.getVal2())
                    .append(";\n");
        }

        sb.append("}\n");


        classWrapper.setName(entity.getName());
        classWrapper.setContent(sb.toString());

        return classWrapper;
    }

    private ClassWrapper toRepositoryInterface(Entity entity) {
        ClassWrapper classWrapper = new ClassWrapper();

        StringBuilder sb = new StringBuilder();

        sb.append("public interface ")
                    .append(entity.getName())
                    .append("Repository extends JpaRepository<")
                    .append(entity.getName())
                    .append(", Long> {\n")
                    .append("}\n");

        classWrapper.setName(entity.getName() + "Repository");
        classWrapper.setContent(sb.toString());

        return classWrapper;
    }
}

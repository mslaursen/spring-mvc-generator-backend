package com.code.springmvcgenerator.service;

import com.code.springmvcgenerator.entity.ClassDetail;
import com.code.springmvcgenerator.entity.EntityDetail;
import com.code.springmvcgenerator.entity.Vector3S;
import com.code.springmvcgenerator.utils.Util;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EntityDetailService {
    private final byte SPACING = 4;
    private final String SPACES = " ".repeat(SPACING);

    private List<ClassDetail> getClasses(EntityDetail entityDetail) {
        List<ClassDetail> classes = new ArrayList<>();

        classes.add(toEntityClass(entityDetail));
        classes.add(toControllerClass(entityDetail));
        //classes.add(toServiceClass(entityDetail));
        //classes.add(toRepositoryInterface(entityDetail));

        return classes;
    }

    public List<ClassDetail> getAllClasses(List<EntityDetail> entityDetails) {
        List<ClassDetail> classes = new ArrayList<>();
        for (EntityDetail ed : entityDetails) {
            classes.addAll(getClasses(ed));
        }
        return classes;
    }

    public ClassDetail toEntityClass(EntityDetail entityDetail) {
        ClassDetail classDetail = new ClassDetail();

        StringBuilder sb  = new StringBuilder()
                .append("@Getter\n")
                .append("@Setter\n")
                .append("@ToString\n")
                .append("@Entity\n")
                .append("public class ")
                .append(entityDetail.getEntityName())
                .append(" {\n")
                .append(SPACES)
                .append("@Id\n")
                .append(SPACES)
                .append("@Column(name = \"").append(entityDetail.getEntityName().toLowerCase()).append("_id\")\n")
                .append(SPACES)
                .append("@GeneratedValue(strategy = GenerationType.IDENTITY)\n")
                .append(SPACES)
                .append("private Long id\n");


        // Relationship columns

        for (Vector3S v : entityDetail.getRelations()) {
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
                        .append(entityDetail.getEntityName().toLowerCase())
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
        for (Vector3S v : entityDetail.getVariables()) {
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
                    .append("\n");
        }

        sb.append("}");

        classDetail.setName(entityDetail.getEntityName());
        classDetail.setContent(sb.toString());

        return classDetail;
    }

    public ClassDetail toControllerClass(EntityDetail entityDetail) {
        ClassDetail classDetail = new ClassDetail();

        StringBuilder sb = new StringBuilder();
        String entityNameLowerService = Util.decapitalize(entityDetail.getEntityName()) + "Service";
        String entityNameLower = Util.decapitalize(entityDetail.getEntityName());

        sb.append("@RestController\n")
                .append("@RequestMapping(/api/")
                .append(entityDetail.getEntityNamePlural().toLowerCase())
                .append("\")\n")
                .append("@CrossOrigin\n")
                .append("public class ")
                .append(entityDetail.getEntityName())
                .append("Controller {\n\n");

        sb.append(SPACES)
                .append("private final ")
                .append(entityDetail.getEntityName())
                .append("Service ")
                .append(entityNameLowerService)
                .append(";\n\n");

        sb.append(SPACES)
                .append("@Autowired\n")
                .append(SPACES)
                .append("public ")
                .append(entityDetail.getEntityName())
                .append("Controller(")
                .append(entityDetail.getEntityName())
                .append("Service ")
                .append(entityNameLowerService)
                .append(") {\n")
                .append(SPACES)
                .append(SPACES)
                .append("this.")
                .append(entityNameLowerService)
                .append(" = ")
                .append(entityNameLowerService)
                .append(";\n")
                .append(SPACES)
                .append("}\n\n");

        if (entityDetail.getHasCreate()) {
            sb.append(SPACES)
                    .append("@PostMapping\n")
                    .append(SPACES)
                    .append("public ResponseEntity<")
                    .append(entityDetail.getEntityName())
                    .append("> ")
                    .append("create")
                    .append("(@RequestBody ")
                    .append(entityDetail.getEntityName())
                    .append(" ")
                    .append(entityNameLower)
                    .append(") {\n");

            sb.append(SPACES)
                    .append(SPACES)
                    .append(entityDetail.getEntityName())
                    .append(" saved")
                    .append(entityDetail.getEntityName())
                    .append(" = ")
                    .append(entityNameLowerService)
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
                    .append(entityDetail.getEntityName())
                    .append(");\n");

            sb.append(SPACES)
                    .append("}\n\n");


        }

        if (entityDetail.getHasRead()) {

        }

        if (entityDetail.getHasUpdate()) {

        }

        if (entityDetail.getHasDelete()) {

        }

        classDetail.setName(entityDetail.getEntityName() + "Controller");
        classDetail.setContent(sb.toString());

        return classDetail;
    }

    public String toServiceClass(EntityDetail entityDetail) {
        return null;
    }

    public String toRepositoryInterface(EntityDetail entityDetail) {
        return null;
    }
}

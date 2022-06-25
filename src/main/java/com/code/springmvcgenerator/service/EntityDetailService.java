package com.code.springmvcgenerator.service;

import com.code.springmvcgenerator.entity.EntityDetail;
import com.code.springmvcgenerator.entity.Vector3S;
import org.springframework.stereotype.Service;

@Service
public class EntityDetailService {
    private final byte SPACING = 4;

    public String toEntityClass(EntityDetail entityDetail) {
        String spaces = " ".repeat(SPACING);

        StringBuilder sb  = new StringBuilder()
                .append("@Getter\n")
                .append("@Setter\n")
                .append("@ToString\n")
                .append("@Entity\n")
                .append("public class ")
                .append(entityDetail.getEntityName())
                .append(" {\n")
                .append(spaces)
                .append("@Id\n")
                .append(spaces)
                .append("@Column(name = \"").append(entityDetail.getEntityName().toLowerCase()).append("_id\")\n")
                .append(spaces)
                .append("@GeneratedValue(strategy = GenerationType.IDENTITY)\n")
                .append(spaces)
                .append("private Long id\n");


        // Relationship columns

        for (Vector3S v : entityDetail.getRelations()) {
            sb.append(spaces);
            switch (v.getVal1()) {
                case "ManyToOne", "OneToOne" -> sb
                        .append("\n")
                        .append(spaces)
                        .append("@")
                        .append(v.getVal1())
                        .append("(cascade = CascadeType.MERGE)\n")
                        .append(spaces)
                        .append("@JoinColumn(name = \"")
                        .append(v.getVal2().toLowerCase())
                        .append("_id\")\n")
                        .append(spaces)
                        .append("private ")
                        .append(v.getVal2())
                        .append(" ")
                        .append(v.getVal2().toLowerCase())
                        .append(";\n");
                case "OneToMany" -> sb
                        .append("\n")
                        .append(spaces)
                        .append("@JsonBackReference(value = \"")
                        .append(v.getVal3().toLowerCase())
                        .append("\")\n")
                        .append(spaces)
                        .append("@")
                        .append(v.getVal1())
                        .append("(mappedBy = \"")
                        .append(entityDetail.getEntityName().toLowerCase())
                        .append("\", cascade = CascadeType.MERGE)\n")
                        .append(spaces)
                        .append("@ToString.Exclude\n")
                        .append(spaces)
                        .append("private List<")
                        .append(v.getVal2())
                        .append("> ")
                        .append(v.getVal3().toLowerCase())
                        .append(";\n");
            }
        }


        // Columns
        for (Vector3S v : entityDetail.getVariables()) {
            sb.append(spaces);

            if (v.getVal3() != null) {
                sb.append("\n")
                        .append(spaces)
                        .append("@Column(name = \"")
                        .append(v.getVal3())
                        .append("\")");
            }

            sb.append("\n")
                    .append(spaces)
                    .append("private ")
                    .append(v.getVal1())
                    .append(" ")
                    .append(v.getVal2())
                    .append("\n");
        }

        sb.append("}");

        return sb.toString();
    }

    public String toControllerClass(EntityDetail entityDetail) {
        return null;
    }

    public String toServiceClass(EntityDetail entityDetail) {
        return null;
    }

    public String toRepositoryInterface(EntityDetail entityDetail) {
        return null;
    }
}

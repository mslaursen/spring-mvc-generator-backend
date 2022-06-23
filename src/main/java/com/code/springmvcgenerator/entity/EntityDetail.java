package com.code.springmvcgenerator.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Locale;

@Getter
@Setter
@ToString
public class EntityDetail {
    private String entityName;
    private String entityNamePlural;
    private List<Vector3S> variables;
    private List<Vector3S> relations;

    public String toClassFormat() {
        int spacing = 4;
        String spaces = " ".repeat(spacing);

        StringBuilder sb  = new StringBuilder()
                .append("@Getter\n")
                .append("@Setter\n")
                .append("@ToString\n")
                .append("@Entity\n")
                .append("public class ")
                .append(entityName)
                .append(" {\n")
                .append(spaces)
                .append("@Id\n")
                .append(spaces)
                .append("@Column(name = \"").append(entityName.toLowerCase()).append("_id\")\n")
                .append(spaces)
                .append("@GeneratedValue(strategy = GenerationType.IDENTITY)\n")
                .append(spaces)
                .append("private Long id\n");

        for (Vector3S v : variables) {
            sb.append("    ");

            if (v.getVal3() != null) {
                sb.append("\n")
                        .append(spaces)
                        .append("@Column(name = \"")
                        .append(v.getVal3())
                        .append("\")")
                        .append("\n");
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
}

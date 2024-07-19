package mzaenalmstpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Table(name = "lookup_tab")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class LookupEntity {
    @Id
    @Column(name = "id", length = 36)
    private String id;

    @Column(name = "lookup_group", length = 64, nullable = false)
    private String groups;

    @Column(name = "lookup_code", length = 20, nullable = false)
    private String code;

    @Column(name = "lookup_name", length = 100, nullable = false)
    private String name;

    @Column(name = "lookup_position", nullable = false)
    private Integer position;

    @Column(name = "lookup_active", nullable = false)
    private Boolean active;

    public LookupEntity(String groups, String code, String name, Integer position) {
        this.groups = groups;
        this.code = code;
        this.name = name;
        this.position = position;
        this.id = UUID.randomUUID().toString();
        this.active = true;
    }
}

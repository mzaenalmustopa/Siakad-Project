package mzaenalmstpa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import mzaenalmstpa.model.DosenModel;
import org.springframework.beans.BeanUtils;


import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "dosen_tab")
public class DosenEntity {
    @Id
    @Column(name = "id", length = 36)
    private String id;

    @Column(name = "nip", length = 20, unique = true)
    private String nip;

    @Column(name = "nama_dosen",length = 120)
    private String name;

    @Column(name = "jk", length = 36)
    private String jk;

    @Column(name = "alamat", length = 200)
    private String alamat;

    @Column(name = "gelar", length = 20)
    private String gelar;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "created_by", length = 20)
    private String createdBy;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "updated_by", length = 20)
    private String updatedBy;

    @OneToMany(mappedBy = "dosen", fetch = FetchType.EAGER)
    private Set<KelasEntity> kelas;

    public DosenEntity() {
    }

    public DosenEntity(String id) {
        this.id = id;
    }

    public DosenEntity(DosenModel model) {
        BeanUtils.copyProperties(model, this);
        this.id = UUID.randomUUID().toString();
        this.createdAt = LocalDateTime.now();
        this.createdBy = "SYSTEM";
        this.updatedAt = LocalDateTime.now();
        this.updatedBy = "SYSTEM";
    }


}

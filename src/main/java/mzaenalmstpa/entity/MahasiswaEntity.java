package mzaenalmstpa.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import mzaenalmstpa.model.MahasiswaModel;
import org.springframework.beans.BeanUtils;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "mahasiswa_tab")
public class MahasiswaEntity {

    @Id
    @Column(name = "id", length = 36)
    private String id;

    @Column(name = "nim", length = 20, unique = true)
    private String nim;

    @Column(name = "nama_siswa", length = 120)
    private String name;

    @Column(name = "jk", length = 36)
    private String jk;

    @Column(name = "alamat", length = 200)
    private String alamat;

    @Column(name = "tmp_lahir", length = 100)
    private String tmpLahir;

    @Column(name = "tgl_lahir")
    private LocalDate tglLahir;

    @Column(name = "agama", length = 36)
    private String agama;

    @Column(name = "jurusan_id", length = 36, insertable = false, updatable = false)
    private String jurusanId;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "created_by", length = 20)
    private String createdBy;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "updated_by", length = 20)
    private String updatedBy;

    @OneToMany(mappedBy = "mahasiswa", fetch = FetchType.EAGER)
    private Set<KelasDetailEntity> kelasDetailEntities;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "jurusan_id", nullable = false)
    private JurusanEntity jurusan;

    public MahasiswaEntity() {
    }

    public MahasiswaEntity(String id) {
        this.id = id;
    }

    public MahasiswaEntity(String nim, String name, String jurusanId) {
        this.nim = nim;
        this.name = name;
        this.jurusanId = jurusanId;
        this.createdAt = LocalDateTime.now();
        this.createdBy = "SYSTEM";
    }

    public MahasiswaEntity(MahasiswaModel model) {
        BeanUtils.copyProperties(model, this);
        this.id = UUID.randomUUID().toString();

        JurusanEntity jurusanEntity = new JurusanEntity();
        jurusanEntity.setId(model.getJurusanId());
        this.jurusan = jurusanEntity;
        this.jk = model.getJk();
        this.agama = model.getAgama();

        this.createdAt = LocalDateTime.now();
        this.createdBy = "SYSTEM";
        this.updatedAt = LocalDateTime.now();
        this.updatedBy = "SYSTEM";
        this.jurusanId = model.getJurusanId();
        if (model.getJurusanId() != null){
            this.jurusan = new JurusanEntity(model.getJurusanId());
        }
    }

    @PrePersist
    public void onCreated(){
        this.id = UUID.randomUUID().toString();
    }
}

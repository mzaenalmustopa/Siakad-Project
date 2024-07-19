package mzaenalmstpa.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import mzaenalmstpa.model.KelasDetailModel;


import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "kelas_detail_tab")
public class KelasDetailEntity {
    @Id
    @Column(name = "id", length = 36, unique = true)
    private String id;

    @Column(name = "kelas_id", length = 36, insertable = false, updatable = false)
    private String kelasId;

    @Column(name = "mahasiswa_id", length = 36, insertable = false, updatable = false)
    private String mahasiswaId;

    @Column(name = "status", length = 20)
    private String status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "kelas_id", nullable = false)
    private KelasEntity kelas;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "mahasiswa_id", nullable = false)
    private MahasiswaEntity mahasiswa;

    public KelasDetailEntity() {
    }

    public KelasDetailEntity(String id) {
        this.id = id;
    }

    public KelasDetailEntity(KelasDetailModel model) {
        this.id = UUID.randomUUID().toString();
        KelasEntity kelasEntity = new KelasEntity();
        kelasEntity.setId(model.getKelas().getId());
        this.kelas = kelasEntity;

        MahasiswaEntity mahasiswaEntity = new MahasiswaEntity();
        mahasiswaEntity.setId(model.getMahasiswa().getId());
        this.mahasiswa = mahasiswaEntity;
        //this.kelasId = kelasId;
        //this.mahasiswaId = mahasiswaId;
        this.status = model.getStatus();
    }

    @PrePersist
    public void onCreated(){
        this.id = UUID.randomUUID().toString();
    }
}

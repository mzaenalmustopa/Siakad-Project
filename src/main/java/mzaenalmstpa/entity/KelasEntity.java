package mzaenalmstpa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import mzaenalmstpa.model.KelasModel;
import mzaenalmstpa.util.DateUtil;
import org.springframework.beans.BeanUtils;


import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "kelas_tab")
public class KelasEntity {
    @Id
    @Column(name = "id", length = 36)
    private String id;

    @Column(name = "kode", length = 20, unique = true)
    private String kode;

    @Column(name = "hari", length = 20)
    private String namaHari;

    @Temporal(TemporalType.TIME)
    @Column(name = "jam_mulai")
    private Date jamMulai;

    @Temporal(TemporalType.TIME)
    @Column(name = "jam_selesai")
    private Date jamSelesai;

    @Column(name = "ruang_id", length = 36, insertable = false, updatable = false)
    private String ruangId;

    @Column(name = "matakuliah_id", length = 36, insertable = false, updatable = false)
    private String matakuliahId;

    @Column(name = "dosen_id", length = 36, insertable = false, updatable = false)
    private String dosenId;

    @Column(name = "status", length = 20)
    private String status;

    @Column(name = "tahun_ajaran")
    private Integer tahunAjaran;

    @Column(name = "semester", length = 20)
    private String semester;

    @Column(name = "kuota")
    private Integer kuota;

    @Column(name = "bisa_online")
    private Boolean bisaOnline;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "created_by", length = 20)
    private String createdBy;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "updated_by", length = 20)
    private String updatedBy;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ruang_id", nullable = false)
    private RuangEntity ruang;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "matakuliah_id", nullable = false)
    private MataKuliahEntity matakuliah;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "dosen_id", nullable = false)
    private DosenEntity dosen;

    @OneToMany(mappedBy = "kelas", fetch = FetchType.EAGER)
    private Set<KelasDetailEntity> kelasDetailEntities;


    public KelasEntity() {
    }

    public KelasEntity(String id) {
        this.id = id;
    }

    public KelasEntity(KelasModel model) {
        BeanUtils.copyProperties(model, this);
        this.id = UUID.randomUUID().toString();

        RuangEntity ruangEntity = new RuangEntity();
        ruangEntity.setId(model.getRuangId());
        this.ruang = ruangEntity;

        MataKuliahEntity mataKuliahEntity = new MataKuliahEntity();
        mataKuliahEntity.setId(model.getMatakuliahId());
        this.matakuliah = mataKuliahEntity;

        DosenEntity dosenEntity = new DosenEntity();
        dosenEntity.setId(model.getDosenId());
        this.dosen = dosenEntity;

        this.createdAt = LocalDateTime.now();
        this.createdBy = "SYSTEM";
    }

    public KelasEntity(String kode, String namaHari, String jamMulai, String jamSelesai, String ruangId, String matakuliahId, String dosenId) {
        this.id = UUID.randomUUID().toString();
        this.kode = kode;
        this.namaHari = namaHari;
        this.jamMulai = DateUtil.getTime(jamMulai);
        this.jamSelesai = DateUtil.getTime(jamSelesai);
        this.ruangId = ruangId;
        this.matakuliahId = matakuliahId;
        this.dosenId = dosenId;
    }

    @PrePersist
    public void onCreated(){
        this.id = UUID.randomUUID().toString();
    }
}

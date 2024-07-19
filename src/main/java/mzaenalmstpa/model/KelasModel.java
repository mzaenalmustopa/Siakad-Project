package mzaenalmstpa.model;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import mzaenalmstpa.entity.KelasEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
public class KelasModel {
    private String id;
    @NotEmpty(message = "*Kode tidak boleh kosong")
    private String kode;

    @NotEmpty(message = "*Hari tidak boleh kosong")
    private String namaHari;

    @NotNull(message = "*Jam tidak boleh kosong")
    @DateTimeFormat(pattern = "HH:mm")
    private Date jamMulai;

    @NotNull(message = "*Jam tidak boleh kosong")
    @DateTimeFormat(pattern = "HH:mm")
    private Date jamSelesai;

    @NotEmpty(message = "*Ruang tidak boleh kosong")
    private String ruangId;

    @NotEmpty(message = "*Matkul tidak boleh kosong")
    private String matakuliahId;

    @NotEmpty(message = "*Dosen tidak boleh kosong")
    private String dosenId;

    @NotEmpty(message = "*Status harus diisi")
    private String status;

    @NotNull(message = "*TA harus diisi")
    private Integer tahunAjaran;

    @NotEmpty(message = "*Semester harus diisi")
    private String semester;

    @NotNull(message = "*Kuota tidak boleh kosong")
    private Integer kuota;

    @NotNull(message = "*Pilih salah satu tidak boleh kosong")
    private Boolean bisaOnline;
    private RuangModel ruang;
    private MataKuliahModel matakuliah;
    private DosenModel dosen;

    public KelasModel() {
    }

    public KelasModel(String id) {
        this.id = id;
    }


    public KelasModel(String id, String kode, String ruangId, String matakuliahId, String dosenId) {
        this.id = id;
        this.kode = kode;
        this.ruangId = ruangId;
        this.matakuliahId = matakuliahId;
        this.dosenId = dosenId;
    }

    public KelasModel(KelasEntity entity) {
        BeanUtils.copyProperties(entity, this);
        if (entity.getRuang() != null) {
            ruangId = entity.getRuang().getId();
            ruang = new RuangModel(entity.getRuang());
        }
        if (entity.getMatakuliah() != null) {
            matakuliahId = entity.getMatakuliah().getId();
            matakuliah = new MataKuliahModel(entity.getMatakuliah());
        }
        if (entity.getDosen() != null) {
            dosenId = entity.getDosen().getId();
            dosen = new DosenModel(entity.getDosen());

        }
    }
}

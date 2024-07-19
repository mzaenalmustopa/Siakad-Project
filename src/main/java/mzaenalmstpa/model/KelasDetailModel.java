package mzaenalmstpa.model;


import lombok.Getter;
import lombok.Setter;
import mzaenalmstpa.entity.KelasDetailEntity;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
public class KelasDetailModel {
    private String id;
    private String kelasId;
    private String mahasiswaId;
    private String status;
    private KelasModel kelas;
    private MahasiswaModel mahasiswa;

    public KelasDetailModel() {
    }

    public KelasDetailModel(String id) {
        this.id = id;
    }

    public KelasDetailModel(KelasDetailEntity entity) {
        BeanUtils.copyProperties(entity, this);
        if (entity.getKelas() != null) {
            kelasId = entity.getKelas().getId();
            kelas = new KelasModel(entity.getKelas());
        }

        if (entity.getMahasiswa() != null) {
            mahasiswaId = entity.getMahasiswa().getId();
            mahasiswa = new MahasiswaModel(entity.getMahasiswa());
        }
    }
}

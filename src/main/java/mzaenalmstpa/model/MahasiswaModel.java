package mzaenalmstpa.model;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import mzaenalmstpa.entity.MahasiswaEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.format.annotation.DateTimeFormat;


import java.time.LocalDate;

@Getter
@Setter
public class MahasiswaModel {
    private String id;

    @NotEmpty(message = "*NIM tidak boleh kosong")
    private String nim;

    @NotEmpty(message = "*Nama wajib diisi")
    private String name;

    @NotEmpty(message = "*Jenis kelamin harus diisi")
    private String jk;

    @NotEmpty(message = "*Alamat harus diisi")
    private String alamat;

    @NotEmpty(message = "*Tempat lahir harus diisi")
    private String tmpLahir;

    @NotNull(message = "*Tanggal lahir wajib diisi")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate tglLahir;

    @NotEmpty(message = "*Agama jangan kosong")
    private String agama;

    @NotEmpty(message = "*Jurusan harus diisi")
    private String jurusanId;
    private String jurusanName;


    public MahasiswaModel() {
    }

    public MahasiswaModel(String nim,String name, String jurusanId) {
        this.nim = nim;
        this.name = name;
        this.jurusanId = jurusanId;
    }

    public MahasiswaModel(MahasiswaEntity entity) {
        BeanUtils.copyProperties(entity, this);
        if (entity.getJurusan() != null){
            jurusanId = entity.getJurusan().getId();
            jurusanName = entity.getJurusan().getName();
        }
    }
}


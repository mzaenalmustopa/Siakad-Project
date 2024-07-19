package mzaenalmstpa.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import mzaenalmstpa.entity.RuangEntity;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
public class RuangModel {
    private String id;
    @NotEmpty(message = "*Kode tidak boleh kosong")
    private String code;
    @NotEmpty(message = "*Nama wajib diisi")
    private String name;
    @NotNull(message = "*Lantai-ke tidak boleh kosong")
    private Integer lantaiKe;
    @NotEmpty(message = "*Gedung harus diisi")
    private String gedungId;
    private String gedungName;

    public RuangModel() {
    }

    public RuangModel(String code, String name, String gedungId) {
        this.code = code;
        this.name = name;
        this.gedungId = gedungId;
    }

    public RuangModel(RuangEntity entity) {
        BeanUtils.copyProperties(entity, this);
        if (entity.getGedung() != null) {
            gedungId = entity.getGedung().getId();
            gedungName = entity.getGedung().getName();
        }
    }
}

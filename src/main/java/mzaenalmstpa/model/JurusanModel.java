package mzaenalmstpa.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import mzaenalmstpa.entity.JurusanEntity;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
public class JurusanModel {
    private String id;
    @NotEmpty(message = "*Kode tidak boleh kosong")
    private String code;

    @NotEmpty(message = "*Nama wajib diisi")
    private String name;

    @NotEmpty(message = "*Fakultas harus diisi")
    private String fakultasId;
    private String fakultasName;

    public JurusanModel() {
    }

    public JurusanModel(String code, String name, String fakultasId) {
        this.code = code;
        this.name = name;
        this.fakultasId = fakultasId;
    }

    public JurusanModel(JurusanEntity entity) {
        BeanUtils.copyProperties(entity, this);
        if (entity.getFakultas() != null) {
            fakultasId = entity.getFakultas().getId();
            fakultasName = entity.getFakultas().getName();
        }
    }
}

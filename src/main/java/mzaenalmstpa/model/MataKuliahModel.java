package mzaenalmstpa.model;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import mzaenalmstpa.entity.MataKuliahEntity;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
public class MataKuliahModel {
    private String id;
    @NotEmpty(message = "*Code tidak boleh kosong")
    private String code;
    @NotEmpty(message = "*Nama wajib diisi")
    private String name;
    @NotNull(message = "*Sks tidak boleh kosong")
    private Integer sks;

    public MataKuliahModel() {
    }

    public MataKuliahModel(MataKuliahEntity entity) {
        BeanUtils.copyProperties(entity, this);
    }
}

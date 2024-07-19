package mzaenalmstpa.model;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import mzaenalmstpa.entity.DosenEntity;
import org.springframework.beans.BeanUtils;


@Getter
@Setter
public class DosenModel {

    private String id;

    @NotBlank
    @NotEmpty
    private String nip;

    @NotBlank
    @NotEmpty
    private String name;

    @NotBlank
    @NotEmpty
    private String jk;

    @NotBlank
    @NotEmpty
    private String alamat;

    @NotBlank
    @NotEmpty
    private String gelar;

    public DosenModel() {
    }

    public DosenModel(DosenEntity entity) {
        BeanUtils.copyProperties(entity, this);
    }
}

package mzaenalmstpa.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import mzaenalmstpa.entity.FakultasEntity;
import mzaenalmstpa.entity.JurusanEntity;
import org.springframework.beans.BeanUtils;


import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class FakultasModel {

    private String id;

    @NotEmpty(message = "*Kode tidak boleh kosong")
    private String code;

    @NotEmpty(message = "*Nama wajib diisi")
    private String name;

    @NotEmpty(message = "*Alamat harus diisi")
    private String alamat;

    private List<JurusanModel> jurusans;

    public FakultasModel() {
    }

    public FakultasModel(String code, String name, String alamat) {
        this.code = code;
        this.name = name;
        this.alamat = alamat;
    }

    public FakultasModel(FakultasEntity entity) {
        BeanUtils.copyProperties(entity, this);
        if (entity.getJurusans() != null || !entity.getJurusans().isEmpty()){
            jurusans = new ArrayList<>();
            for (JurusanEntity jrs : entity.getJurusans()){
                jurusans.add(new JurusanModel(jrs));
            }
        }
    }
}

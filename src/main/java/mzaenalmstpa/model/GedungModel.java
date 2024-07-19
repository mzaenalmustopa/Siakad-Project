package mzaenalmstpa.model;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import mzaenalmstpa.entity.GedungEntity;
import mzaenalmstpa.entity.RuangEntity;
import org.springframework.beans.BeanUtils;


import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class GedungModel {

    private String id;

    @NotEmpty(message = "*Kode tidak boleh kosong")
    private String code;

    @NotEmpty(message = "*Nama wajib diisi")
    private String name;

    @NotNull(message = "*Jumlah lantai harus diisi")

    private Integer jmlLantai;
    private List<RuangModel> ruangs;

    public GedungModel() {
    }

    public GedungModel(String code, String name, Integer jmlLantai) {
        this.code = code;
        this.name = name;
        this.jmlLantai = jmlLantai;
    }

    public GedungModel(GedungEntity entity) {
        BeanUtils.copyProperties(entity, this);
        if (entity.getRuangs() != null || !entity.getRuangs().isEmpty()) {
            ruangs = new ArrayList<>();
            for (RuangEntity ruang : entity.getRuangs()) {
                ruangs.add(new RuangModel(ruang));
            }
        }
    }
}
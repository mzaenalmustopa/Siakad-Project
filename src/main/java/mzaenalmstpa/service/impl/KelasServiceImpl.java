package mzaenalmstpa.service.impl;

import lombok.extern.slf4j.Slf4j;
import mzaenalmstpa.entity.DosenEntity;
import mzaenalmstpa.entity.KelasEntity;
import mzaenalmstpa.entity.MataKuliahEntity;
import mzaenalmstpa.entity.RuangEntity;
import mzaenalmstpa.model.KelasModel;
import mzaenalmstpa.repository.KelasRepository;
import mzaenalmstpa.service.KelasService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class KelasServiceImpl implements KelasService {
    private KelasRepository repository;

    @Autowired
    public KelasServiceImpl(KelasRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<KelasModel> get() {
        return this.repository.findAll().stream().map(KelasModel::new).collect(Collectors.toList());
    }

    @Override
    public Boolean validKode(KelasModel request) {
        List<KelasEntity> checkKode = this.repository.findByKode(request.getKode());
        return checkKode.isEmpty();
    }

    @Override
    public Boolean validNamaHari(KelasModel request) {
        List<KelasEntity> checkNamaHari = this.repository.findByKode(request.getNamaHari());
        return checkNamaHari.isEmpty();
    }

    @Override
    public KelasModel getById(String id) {
       return this.repository.findById(id).map(KelasModel::new).orElse(new KelasModel());
    }

    @Override
    public Optional<KelasModel> save(KelasModel request) {
        if (request == null)
            return Optional.empty();

        //check case 01
        List<KelasEntity> check01 = this.repository.checkCase01(request.getNamaHari(), request.getRuangId(), request.getJamMulai(),request.getJamSelesai());
        if (!check01.isEmpty()) {
            return Optional.empty();
        }
        //check case 02
        List<KelasEntity> check02 = this.repository.checkCase02(request.getNamaHari(), request.getDosenId(), request.getJamMulai(), request.getJamSelesai());
        if (!check02.isEmpty()){
            return Optional.empty();
        }
        //check case 03
        List<KelasEntity> check03 = this.repository.checkCase03(request.getRuangId(), request.getDosenId(), request.getJamMulai(), request.getJamSelesai());
        if (!check03.isEmpty()){
            return Optional.empty();
        }

            KelasEntity result = new KelasEntity(request);
            try {
                this.repository.save(result);
                return Optional.of(new KelasModel(result));
            } catch (Exception e) {
                log.error("Save kelas entity failed, error: {}", e.getMessage());
                return Optional.empty();
            }
        }


    @Override
    public Optional<KelasModel> update(String id, KelasModel request) {
        Optional<KelasEntity> result = this.repository.findById(id);
        if (result.isEmpty()) {
            return Optional.empty();
        }
        KelasEntity data = result.get();
        BeanUtils.copyProperties(request, data);
        RuangEntity ruang = new RuangEntity(request.getRuang().getId());
        MataKuliahEntity mataKuliah = new MataKuliahEntity(request.getMatakuliah().getId());
        DosenEntity dosen = new DosenEntity(request.getDosen().getId());

        data.setId(id);
        data.setRuang(ruang);
        data.setMatakuliah(mataKuliah);
        data.setDosen(dosen);
        data.setUpdatedAt(LocalDateTime.now());

        try {
            this.repository.save(data);
            return Optional.of(new KelasModel(data));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<KelasModel> delete(String id) {
        Optional<KelasEntity> result = this.repository.findById(id);
        if (result.isEmpty())  {
            return Optional.empty();
        }
        try {
            KelasEntity data = result.get();
            this.repository.delete(data);
            return Optional.of(new KelasModel(data));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}

package mzaenalmstpa.service.impl;

import mzaenalmstpa.entity.KelasDetailEntity;
import mzaenalmstpa.entity.KelasEntity;
import mzaenalmstpa.entity.MahasiswaEntity;
import mzaenalmstpa.model.KelasDetailModel;
import mzaenalmstpa.repository.KelasDetailRepository;
import mzaenalmstpa.service.KelasDetailService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class KelasDetailServiceImpl implements KelasDetailService {
    private KelasDetailRepository repository;

    @Autowired
    public KelasDetailServiceImpl(KelasDetailRepository repository){
        this.repository = repository;
    }

    @Override
    public List<KelasDetailModel> get() {
        return this.repository.findAll().stream().map(KelasDetailModel::new).collect(Collectors.toList());
    }

    @Override
    public KelasDetailModel getById(String id) {
        return this.repository.findById(id).map(KelasDetailModel::new).orElse(new KelasDetailModel());
    }

    @Override
    public Optional<KelasDetailModel> save(KelasDetailModel request) {
        if (request == null) {
            return Optional.empty();
        }
        KelasDetailEntity result = new KelasDetailEntity(request);

        try {
            this.repository.save(result);
            return Optional.of(new KelasDetailModel(result));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<KelasDetailModel> update(String id, KelasDetailModel request) {
        Optional<KelasDetailEntity> result = this.repository.findById(id);
        if (result.isEmpty()){
            return Optional.empty();
        }
        KelasDetailEntity data = result.get();
        BeanUtils.copyProperties(request, data);
        KelasEntity kelas = new KelasEntity(request.getKelas().getId());
        data.setKelas(kelas);
        MahasiswaEntity mahasiswa = new MahasiswaEntity(request.getMahasiswa().getId());
        data.setMahasiswa(mahasiswa);

        try {
            this.repository.save(data);
            return Optional.of(new KelasDetailModel(data));
        }catch (Exception e){
            return Optional.empty();
        }
    }

    @Override
    public Optional<KelasDetailModel> delete(String id) {
        Optional<KelasDetailEntity> result = this.repository.findById(id);
        if (result.isEmpty()){
            return Optional.empty();
        }
        try {
            KelasDetailEntity data = result.get();
            this.repository.delete(data);
            return Optional.of(new KelasDetailModel(data));
        }catch (Exception e){
            return Optional.empty();
        }
    }
}

package mzaenalmstpa.service.impl;

import mzaenalmstpa.entity.JurusanEntity;
import mzaenalmstpa.entity.MahasiswaEntity;
import mzaenalmstpa.model.MahasiswaModel;
import mzaenalmstpa.repository.MahasiswaRepository;
import mzaenalmstpa.service.MahasiswaService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MahasiswaServiceImpl implements MahasiswaService {
    private MahasiswaRepository repository;

    @Autowired
    public MahasiswaServiceImpl(MahasiswaRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<MahasiswaModel> get() {
        return this.repository.findAll().stream().map(MahasiswaModel::new).collect(Collectors.toList());
    }

    @Override
    public Boolean validNim(MahasiswaModel request) {
        List<MahasiswaEntity> checkNim = this.repository.findByNim(request.getNim());
        return checkNim.isEmpty();
    }

    @Override
    public Boolean validName(MahasiswaModel request) {
        List<MahasiswaEntity> checkName = this.repository.findByNim(request.getName());
        return checkName.isEmpty();
    }

    @Override
    public MahasiswaModel getById(String id) {
        return this.repository.findById(id).map(MahasiswaModel::new).orElse(new MahasiswaModel());
    }

    @Override
    public Optional<MahasiswaModel> save(MahasiswaModel request) {
        if (request == null) {
            return Optional.empty();
        }

        List<MahasiswaEntity> checkNim = this.repository.findByNim(request.getNim());
        if (!checkNim.isEmpty()){
            return Optional.empty();
        }

        List<MahasiswaEntity> checkName = this.repository.findByNim(request.getName());
        if (!checkName.isEmpty()){
            return Optional.empty();
        }

        MahasiswaEntity result = new MahasiswaEntity(request);
        try {
            // proses simpan data => table siswa
            this.repository.save(result);
            return Optional.of(new MahasiswaModel(result));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<MahasiswaModel> update(String id, MahasiswaModel request) {
        Optional<MahasiswaEntity> result = this.repository.findById(id);
        if (result.isEmpty()) {
            return Optional.empty();
        }
        MahasiswaEntity data = result.get();
        BeanUtils.copyProperties(request, data);
        JurusanEntity jurusan = new JurusanEntity(request.getJurusanId());
        // replace data lama dengan dataBaru
        data.setId(id);
        data.setJurusan(jurusan);
        data.setUpdatedAt(LocalDateTime.now());

        try {
            this.repository.save(data);
            // jika berhasil
            return Optional.of(new MahasiswaModel(data));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<MahasiswaModel> delete(String id) {
        Optional<MahasiswaEntity> result = this.repository.findById(id);
        if (result.isEmpty()) {
            return Optional.empty();
        }

        try {
            MahasiswaEntity data = result.get();
            this.repository.delete(data);
            return Optional.of(new MahasiswaModel(data));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}

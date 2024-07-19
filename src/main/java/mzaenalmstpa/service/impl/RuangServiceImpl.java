package mzaenalmstpa.service.impl;

import mzaenalmstpa.entity.GedungEntity;
import mzaenalmstpa.entity.RuangEntity;
import mzaenalmstpa.model.RuangModel;
import mzaenalmstpa.repository.RuangRepository;
import mzaenalmstpa.service.RuangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RuangServiceImpl implements RuangService {
    private RuangRepository repository;

    @Autowired
    public RuangServiceImpl(RuangRepository repository) {
        this.repository = repository;
    }


    @Override
    public List<RuangModel> get() {
        return this.repository.findAll().stream().map(RuangModel::new).collect(Collectors.toList());
    }

    @Override
    public Boolean validCode(RuangModel request) {
        List<RuangEntity> checkCode = this.repository.findByCode(request.getCode());
        return checkCode.isEmpty();
    }

    @Override
    public Boolean validName(RuangModel request) {
        List<RuangEntity> checkName = this.repository.findByName(request.getName());
        return checkName.isEmpty();
    }

    @Override
    public RuangModel getById(String id) {
        return this.repository.findById(id).map(RuangModel::new).orElse(new RuangModel());
    }

    @Override
    public Optional<RuangModel> save(RuangModel request) {
        if (request == null) {
            return Optional.empty();
        }
        RuangEntity result = new RuangEntity(request);
        try {
            this.repository.save(result);
            return Optional.of(new RuangModel(result));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<RuangModel> update(String id, RuangModel request) {
        Optional<RuangEntity> result = this.repository.findById(id);
        if (result.isEmpty()) {
            return Optional.empty();
        }
        RuangEntity data = result.get();
        data.setCode(request.getCode());
        data.setName(request.getName());
        GedungEntity gedung = new GedungEntity(request.getGedungId());
        data.setGedung(gedung);

        try {
            this.repository.save(data);
            return Optional.of(new RuangModel(data));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<RuangModel> delete(String id) {
        Optional<RuangEntity> result = this.repository.findById(id);
        if (result.isEmpty()) {
            return Optional.empty();
        }

        try {
            RuangEntity data = result.get();
            GedungEntity gedung = data.getGedung();
            gedung.removeRuang(data);
            data.setGedung(null);
            this.repository.delete(data);
            return Optional.of(new RuangModel(data));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}

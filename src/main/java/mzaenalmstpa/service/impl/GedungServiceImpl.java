package mzaenalmstpa.service.impl;

import mzaenalmstpa.entity.GedungEntity;
import mzaenalmstpa.model.GedungModel;
import mzaenalmstpa.repository.GedungRepository;
import mzaenalmstpa.service.GedungService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GedungServiceImpl implements GedungService {
    private GedungRepository repository;

    @Autowired
    public GedungServiceImpl(GedungRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<GedungModel> get() {
        return this.repository.findAll().stream().map(GedungModel::new).collect(Collectors.toList());
    }

    @Override
    public Boolean validCode(GedungModel request) {
        List<GedungEntity> checkCode = this.repository.findByCode(request.getCode());
        return checkCode.isEmpty();
    }

    @Override
    public Boolean validName(GedungModel request) {
        List<GedungEntity> checkName = this.repository.findByName(request.getName());
        return checkName.isEmpty();
    }

    @Override
    public GedungModel getById(String id) {
        return this.repository.findById(id).map(GedungModel::new).orElse(new GedungModel());
    }

    @Override
    public Optional<GedungModel> save(GedungModel request) {
        if (request == null) {
            return Optional.empty();
        }
        GedungEntity result = new GedungEntity(request);
        try {
            this.repository.save(result);
            return Optional.of(new GedungModel(result));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<GedungModel> update(String id, GedungModel request) {
        Optional<GedungEntity> result = this.repository.findById(id);
        if (result.isEmpty()) {
            return Optional.empty();
        }
        GedungEntity data = result.get();
        BeanUtils.copyProperties(request, data);
        data.setId(id);
        try {
            this.repository.save(data);
            return Optional.of(new GedungModel(data));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<GedungModel> delete(String id) {
        Optional<GedungEntity> result = this.repository.findById(id);
        if (result.isEmpty()) {
            return Optional.empty();
        }
        GedungEntity data = result.get();
        try {
            this.repository.delete(data);
            return Optional.of(new GedungModel(data));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}

package mzaenalmstpa.service.impl;

import mzaenalmstpa.entity.FakultasEntity;
import mzaenalmstpa.model.FakultasModel;
import mzaenalmstpa.repository.FakultasRepository;
import mzaenalmstpa.service.FakultasService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FakultasServiceImpl implements FakultasService {
    private FakultasRepository repository;

    @Autowired
    public FakultasServiceImpl(FakultasRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<FakultasModel> get() {
        return this.repository.findAll().stream().map(FakultasModel::new).collect(Collectors.toList());
    }

    @Override
    public Boolean validCode(FakultasModel model) {
        //check code
        List<FakultasEntity> checkCode = this.repository.findByCode(model.getCode());
        return checkCode.isEmpty();
    }

    @Override
    public Boolean validName(FakultasModel model) {
        //check name
        List<FakultasEntity> checkName = this.repository.findByName(model.getName());
        return checkName.isEmpty();
    }

    @Override
    public FakultasModel getById(String id) {
        if (id == null || id.isEmpty()) {
            return new FakultasModel();
        }
        return this.repository.findById(id).map(FakultasModel::new).orElse(new FakultasModel());
    }

    @Override
    public Optional<FakultasModel> save(FakultasModel request) {
        if (request == null) {
            return Optional.empty();
        }
        FakultasEntity result = new FakultasEntity(request);
        try {
            this.repository.save(result);
            return Optional.of(new FakultasModel(result));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<FakultasModel> update(String id, FakultasModel request) {
        if (id == null || id.isEmpty()){
            return Optional.empty();
        }

        Optional<FakultasEntity> result = this.repository.findById(id);
        if (result.isEmpty()) {
            return Optional.empty();
        }
        FakultasEntity data = result.get();
        BeanUtils.copyProperties(request, data);
        data.setId(id);
        try {
            this.repository.save(data);
            return Optional.of(new FakultasModel(data));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<FakultasModel> delete(String id) {
        Optional<FakultasEntity> result = this.repository.findById(id);
        if (result.isEmpty()) {
            return Optional.empty();
        }
        FakultasEntity data = result.get();
        try {
            this.repository.delete(data);
            return Optional.of(new FakultasModel(data));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}



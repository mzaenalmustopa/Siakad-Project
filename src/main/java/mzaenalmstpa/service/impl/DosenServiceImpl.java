package mzaenalmstpa.service.impl;

import mzaenalmstpa.entity.DosenEntity;
import mzaenalmstpa.model.DosenModel;
import mzaenalmstpa.repository.DosenRepository;
import mzaenalmstpa.service.DosenService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DosenServiceImpl implements DosenService {
    private DosenRepository repository;

    @Autowired
    public DosenServiceImpl(DosenRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<DosenModel> get() {
        return this.repository.findAll().stream().map(DosenModel::new).collect(Collectors.toList());
    }

    @Override
    public Boolean validNip(DosenModel model) {
        List<DosenEntity> checkNip = this.repository.findByNip(model.getNip());
        return checkNip.isEmpty();
    }

    @Override
    public Boolean validName(DosenModel model) {
        List<DosenEntity> checkName = this.repository.findByNip(model.getName());
        return checkName.isEmpty();
    }

    @Override
    public DosenModel getById(String id) {
        return this.repository.findById(id).map(DosenModel::new).orElse(new DosenModel());
    }

    @Override
    public Optional<DosenModel> save(DosenModel request) {
        if (request == null) {
            return Optional.empty();
        }
        DosenEntity result = new DosenEntity(request);
        try {
            this.repository.save(result);
            return Optional.of(new DosenModel(result));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<DosenModel> update(String id, DosenModel request) {
        Optional<DosenEntity> result = this.repository.findById(id);
        if (result.isEmpty()) {
            return Optional.empty();
        }
        DosenEntity data = result.get();
        BeanUtils.copyProperties(request, data);
        data.setId(id);
        try {
            this.repository.save(data);
            return Optional.of(new DosenModel(data));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<DosenModel> delete(String id) {
        Optional<DosenEntity> result = this.repository.findById(id);
        if (result.isEmpty()) {
            return Optional.empty();
        }
        DosenEntity data = result.get();
        try {
            this.repository.delete(data);
            return Optional.of(new DosenModel(data));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}

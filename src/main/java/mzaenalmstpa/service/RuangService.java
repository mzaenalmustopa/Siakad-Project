package mzaenalmstpa.service;

import mzaenalmstpa.model.RuangModel;

import java.util.List;
import java.util.Optional;

public interface RuangService {
    public List<RuangModel> get();
    public Boolean validCode(RuangModel request);
    public Boolean validName(RuangModel request);
    public RuangModel getById(String id);
    public Optional<RuangModel> save(RuangModel request);
    public Optional<RuangModel> update(String id, RuangModel request);
    public Optional<RuangModel> delete(String id);
}

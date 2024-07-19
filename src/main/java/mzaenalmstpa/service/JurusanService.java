package mzaenalmstpa.service;

import mzaenalmstpa.model.JurusanModel;

import java.util.List;
import java.util.Optional;

public interface JurusanService {
    public List<JurusanModel> get();
    public Boolean validCode(JurusanModel request);
    public Boolean validName(JurusanModel request);
    public JurusanModel getById(String id);
    public Optional<JurusanModel> save(JurusanModel request);
    public Optional<JurusanModel> update(String id, JurusanModel request);
    public Optional<JurusanModel> delete(String id);
}

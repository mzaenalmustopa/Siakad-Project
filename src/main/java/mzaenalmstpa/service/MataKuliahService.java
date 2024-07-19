package mzaenalmstpa.service;

import mzaenalmstpa.model.MataKuliahModel;

import java.util.List;
import java.util.Optional;

public interface MataKuliahService {
    public List<MataKuliahModel> get();
    public Boolean validCode(MataKuliahModel request);
    public Boolean validName(MataKuliahModel request);
    public MataKuliahModel getById(String id);
    public Optional<MataKuliahModel> save(MataKuliahModel request);
    public Optional<MataKuliahModel> update(String id, MataKuliahModel request);
    public Optional<MataKuliahModel> delete(String id);
}

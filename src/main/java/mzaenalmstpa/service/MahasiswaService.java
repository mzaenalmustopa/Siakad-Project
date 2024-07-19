package mzaenalmstpa.service;


import mzaenalmstpa.model.MahasiswaModel;

import java.util.List;
import java.util.Optional;

public interface MahasiswaService {
    public List<MahasiswaModel> get();
    public Boolean validNim(MahasiswaModel request);
    public Boolean validName(MahasiswaModel request);

    public MahasiswaModel getById(String id);
    public Optional<MahasiswaModel> save(MahasiswaModel request);
    public Optional<MahasiswaModel> update(String id, MahasiswaModel request);
    public Optional<MahasiswaModel> delete(String id);
}

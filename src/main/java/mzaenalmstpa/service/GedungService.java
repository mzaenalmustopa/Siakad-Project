package mzaenalmstpa.service;


import mzaenalmstpa.model.GedungModel;

import java.util.List;
import java.util.Optional;

public interface GedungService {
    public List<GedungModel> get();
    public Boolean validCode(GedungModel request);
    public Boolean validName(GedungModel request);
    public GedungModel getById(String id);
    public Optional<GedungModel> save(GedungModel request);
    public Optional<GedungModel> update(String id, GedungModel request);
    public Optional<GedungModel> delete(String id);
}

package mzaenalmstpa.service;


import mzaenalmstpa.model.DosenModel;

import java.util.List;
import java.util.Optional;

public interface DosenService {
    public List<DosenModel> get();
    public Boolean validNip(DosenModel model);
    public Boolean validName(DosenModel model);

    public DosenModel getById(String id);
    public Optional<DosenModel> save(DosenModel request);
    public Optional<DosenModel> update(String id, DosenModel request);
    public Optional<DosenModel> delete(String id);

}

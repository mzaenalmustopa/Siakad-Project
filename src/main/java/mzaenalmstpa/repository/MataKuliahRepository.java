package mzaenalmstpa.repository;


import mzaenalmstpa.entity.MataKuliahEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MataKuliahRepository extends JpaRepository<MataKuliahEntity, String> {
    List<MataKuliahEntity> findByCode(String code);
    List<MataKuliahEntity> findByName(String name);
    List<MataKuliahEntity> findByCodeAndName(String code, String name);
}

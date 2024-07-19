package mzaenalmstpa.repository;

import mzaenalmstpa.entity.DosenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface DosenRepository extends JpaRepository<DosenEntity, String> {
    List<DosenEntity> findByNip(String nip);
    List<DosenEntity> findByName(String name);
    List<DosenEntity> findByNipAndName(String nip, String name);
}

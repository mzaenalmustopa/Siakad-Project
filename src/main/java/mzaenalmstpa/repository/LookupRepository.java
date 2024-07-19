package mzaenalmstpa.repository;


import mzaenalmstpa.entity.LookupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LookupRepository extends JpaRepository<LookupEntity, String> {
    List<LookupEntity> findByGroups(String groups);
    Optional<LookupEntity> findByCode(String code);
}

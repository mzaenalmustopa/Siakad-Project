package mzaenalmstpa.repository;

import mzaenalmstpa.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, String> {
    RoleEntity findByName(String name);

    @Query("select t from RoleEntity t where t.name in :names")
    List<RoleEntity> findByNameFetchMenus(@Param("names") List<String> names);

    @Query("select t from RoleEntity t where t.id=:id")
    RoleEntity findByIdFetchMenuAndPrivilage(@Param("id") String id);

}

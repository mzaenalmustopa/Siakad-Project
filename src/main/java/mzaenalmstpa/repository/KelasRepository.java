package mzaenalmstpa.repository;

import mzaenalmstpa.entity.KelasEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface KelasRepository extends JpaRepository<KelasEntity, String> {
    List<KelasEntity> findByKode(String Kode);
    List<KelasEntity> findByNamaHari(String namaHari);

    @Query("select t from KelasEntity t where t.namaHari= :namaHari and t.ruangId= :ruangId and t.jamMulai >= :jamMulai and t.jamSelesai >= :jamSelesai")
    List<KelasEntity> checkCase01(@Param("namaHari") String namaHari,
                                  @Param("ruangId") String ruangId,
                                  @Param("jamMulai") Date jamMulai,
                                  @Param("jamSelesai") Date jamSelesai);
    @Query("select t from KelasEntity t where t.namaHari= :namaHari and t.dosenId= :dosenId and t.jamMulai >= :jamMulai and t.jamSelesai >= :jamSelesai")
    List<KelasEntity> checkCase02(@Param("namaHari") String namaHari,
                                  @Param("dosenId") String dosenId,
                                  @Param("jamMulai") Date jamMulai,
                                  @Param("jamSelesai") Date jamSelesai);
    @Query("select  t from KelasEntity t where t.ruangId= :ruangId and t.dosenId= :dosenId and t.jamMulai >= :jamMulai and t.jamSelesai >= :jamSelesai")
    List<KelasEntity> checkCase03(@Param("ruangId") String ruangId,
                                  @Param("dosenId") String dosenId,
                                  @Param("jamMulai") Date jamMulai,
                                  @Param("jamSelesai") Date jamSelesai);


}

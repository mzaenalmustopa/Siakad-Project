package mzaenalmstpa.impl;

import com.aronsoft.webmvc.entity.KelasEntity;
import com.aronsoft.webmvc.repository.KelasRepository;
import com.aronsoft.webmvc.util.DateUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class KelasServiceImplTest {
    @Autowired
    @InjectMocks
    private KelasServiceImpl service;

    @Mock
    private KelasRepository repository;
    private static List<KelasEntity> kelasList;
    private static String ruangId;
    private static String dosenId;
    private static String matkulId;

    @BeforeEach
    void setUp(){
        ruangId = UUID.randomUUID().toString();
        dosenId = UUID.randomUUID().toString();
        matkulId = UUID.randomUUID().toString();
        kelasList = Arrays.asList(
                new KelasEntity("K001","SENIN","08:00:00","09:45:00",ruangId,matkulId,dosenId),
                new KelasEntity("K002","SENIN","10:00:00","11:45:00",ruangId,matkulId,dosenId),
                new KelasEntity("K003","SENIN","13:00:00","14:45:00",ruangId,matkulId,dosenId),
                new KelasEntity("K004","SENIN","15:00:00","16:45:00",ruangId,matkulId,dosenId)
        );
    }

    @Test
    void getAll() {
        LocalDateTime time = DateUtil.getLocalTime("08:00:00");
        System.out.println(time);
        assertNull(time);
    }

    @Test
    void get() {
        when(repository.findAll()).thenReturn(kelasList);

        List<KelasEntity> result = service.get();
        assertNotNull(result);
        assertEquals(4, result.size());
        assertEquals("K001", result.get(0).getKode());
        assertEquals("SENIN", result.get(0).getNamaHari());

        Date jamMulai = DateUtil.getTime("08:00:00");
        assertEquals(jamMulai, result.get(0).getJamMulai());

        Date jamSelesai = DateUtil.getTime("09:45:00");
        assertEquals(jamSelesai, result.get(0).getJamSelesai());
    }

    @Test
    void getById() {
    }

    @Test
    void save() {

    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}
package mzaenalmstpa.impl;

import com.aronsoft.webmvc.entity.FakultasEntity;
import com.aronsoft.webmvc.model.FakultasModel;
import com.aronsoft.webmvc.repository.FakultasRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FakultasServiceImplTest {
    @Autowired
    @InjectMocks
    private FakultasServiceImpl service;

    @Mock
    private FakultasRepository repository;

    private static List<FakultasEntity> fakultasEntityList;

    @BeforeEach
    void setUp() {
        fakultasEntityList = Arrays.asList(
                new FakultasEntity("FK", "Fakultas Kedokteran", "Yogya"),
                new FakultasEntity("FH", "Fakultas Hukum", "Yogya"),
                new FakultasEntity("FE", "Fakultas Ekonomi", "Yogya")
        );
    }

    @Test
    void get() {
        //jika method repo findAll dipanggil maka kembalikan fakultas list
        when(repository.findAll()).thenReturn(fakultasEntityList);

        //test method get
        List<FakultasModel> result = service.get();
        //check 1
        assertNotNull(result);
        //check 2
        assertEquals(3, result.size());
        //check data 0
        assertEquals("FK", result.get(0).getCode());
        assertEquals("Fakultas Hukum", result.get(1).getName());

        //check data salah
        assertNotEquals("FE", result.get(0).getCode());

    }

    @Test
    void getById() {
        //skenario 1
        FakultasModel result = service.getById("");

        assertNotNull(result);
        assertNull(result.getCode());

        //skenario 2
        Optional<FakultasEntity> entity = Optional.of(fakultasEntityList.get(0));
        when(repository.findById(any(String.class))).thenReturn(entity);

        result = service.getById("0123");
        assertNotNull(result);
        assertEquals("FK", result.getCode());

    }

    @Test
    void save() {
        FakultasModel request = new FakultasModel("FE", "Fakultas Ekonomi", "Yogya");

        //mocking
        when(repository.save(any(FakultasEntity.class))).thenReturn(fakultasEntityList.get(2));

        Optional<FakultasModel> result = service.save(request);
        assertNotNull(result);
        assertEquals("FE", result.get().getCode());
        assertEquals("Fakultas Ekonomi", result.get().getName());
        assertEquals("Yogya", result.get().getAlamat());
    }

    @Test
    void update() {
        Optional<FakultasModel> result = service.update("", null);
        assertNotNull(result);
        assertTrue(result.isEmpty());

        FakultasModel request = new FakultasModel("FH", "Fakultas Hukum", "Yogya");
        when(repository.save(any(FakultasEntity.class))).thenReturn(fakultasEntityList.get(1));

        Optional<FakultasEntity> entity = Optional.of(fakultasEntityList.get(1));
        when(repository.findById(any(String.class))).thenReturn(entity);

        Optional<FakultasModel> data = service.update("013",request);
        assertNotNull(data);
        assertEquals("FH", data.get().getCode());
        assertEquals("Fakultas Hukum", data.get().getName());
        assertEquals("Yogya", data.get().getAlamat());

    }

    @Test
    void delete() {
        Optional<FakultasModel> result = service.delete("");
        assertNotNull(result);
        assertTrue(result.isEmpty());

        Optional<FakultasEntity> entity = Optional.of(fakultasEntityList.get(1));
        when(repository.findById(any(String.class))).thenReturn(entity);

        Optional<FakultasModel> data = service.delete("013");
        assertNotNull(data);
        assertFalse(data.isEmpty());
        assertNotEquals(Optional.empty(),data);

    }
}
package mzaenalmstpa.impl;

import com.aronsoft.webmvc.entity.JurusanEntity;
import com.aronsoft.webmvc.model.JurusanModel;
import com.aronsoft.webmvc.repository.JurusanRepository;
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
class JurusanServiceImplTest {
    @Autowired
    @InjectMocks
    private JurusanServiceImpl service;

    @Mock
    private JurusanRepository repository;

    private static List<JurusanEntity> jurusanEntityList;

    @BeforeEach
    void setUp() {
        jurusanEntityList = Arrays.asList(
                new JurusanEntity("G-20", "Gedung LPTK"),
                new JurusanEntity("G-21", "Gedung Laboratorium"),
                new JurusanEntity("G-22", "Gedung RF")
        );
    }

    @Test
    void get() {
        //method repo dipanggil kembalikan fakultas list
        when(repository.findAll()).thenReturn(jurusanEntityList);

        //test methoda get
        List<JurusanModel> result = service.get();
        //check 1
        assertNotNull(result);
        //check 2
        assertEquals(3, result.size());
        //check data 2
        assertEquals("G-22", result.get(2).getCode());
    }

    @Test
    void getById() {
        //skenario
        JurusanModel result = service.getById("");

        assertNotNull(result);
        assertNull(result.getCode());

        //skenario 2
        Optional<JurusanEntity> entity = Optional.of(jurusanEntityList.get(1));
        when(repository.findById(any(String.class))).thenReturn(entity);

        result = service.getById("111");
        assertNotNull(result);
        assertEquals("G-21", result.getCode());

    }

    @Test
    void save() {
        JurusanModel request = new JurusanModel("G-20", "Gedung LPTK", "FT");

        //mcoking
        when(repository.save(any(JurusanEntity.class))).thenReturn(jurusanEntityList.get(0));

        Optional<JurusanModel> result = service.save(request);
        assertNotNull(result);
        assertEquals("G-20", result.get().getCode());
        assertEquals("Gedung LPTK", result.get().getName());
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}
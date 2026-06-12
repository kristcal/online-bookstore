package njt.mavenproject2.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import njt.mavenproject2.entity.impl.Knjizara;
import njt.mavenproject2.repository.impl.KnjizaraRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

class KnjizaraControllerTest {

    private KnjizaraRepository repo;
    private KnjizaraController controller;

    @BeforeEach
    void setUp() {
        repo = mock(KnjizaraRepository.class);
        controller = new KnjizaraController(repo);
    }

    @Test
    void testGetAll() {
        Knjizara k1 = new Knjizara();
        k1.setId(1L);
        k1.setNaziv("Laguna");
        k1.setLokacija("Beograd");

        Knjizara k2 = new Knjizara();
        k2.setId(2L);
        k2.setNaziv("Vulkan");
        k2.setLokacija("Novi Sad");

        when(repo.findAll()).thenReturn(List.of(k1, k2));

        ResponseEntity<List<KnjizaraController.KnjizaraDto>> response =
                controller.getAll();

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());

        assertEquals(1L, response.getBody().get(0).id());
        assertEquals("Laguna", response.getBody().get(0).naziv());
        assertEquals("Beograd", response.getBody().get(0).lokacija());

        assertEquals(2L, response.getBody().get(1).id());
        assertEquals("Vulkan", response.getBody().get(1).naziv());
        assertEquals("Novi Sad", response.getBody().get(1).lokacija());

        verify(repo).findAll();
    }

    @Test
    void testGetAllPraznaLista() {
        when(repo.findAll()).thenReturn(List.of());

        ResponseEntity<List<KnjizaraController.KnjizaraDto>> response =
                controller.getAll();

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty());

        verify(repo).findAll();
    }
}
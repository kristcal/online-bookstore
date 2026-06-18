package njt.mavenproject2.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import njt.mavenproject2.entity.impl.Knjizara;
import njt.mavenproject2.repository.impl.KnjizaraRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

/**
 * Test klasa za proveru funkcionalnosti klase {@link KnjizaraController}.
 *
 * Testira dobavljanje svih knjižara i slučaj kada lista knjižara ne sadrži
 * nijedan element.
 *
 * @author Korisnik
 */
class KnjizaraControllerTest {

    private KnjizaraRepository repo;
    private KnjizaraController controller;

    /**
     * Inicijalizuje mock repozitorijum i instancu kontrolera pre svakog testa.
     */
    @BeforeEach
    void setUp() {
        repo = mock(KnjizaraRepository.class);
        controller = new KnjizaraController(repo);
    }

    /**
     * Proverava uspešno dobavljanje liste svih knjižara.
     */
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

    /**
     * Proverava dobavljanje knjižara kada repozitorijum vraća praznu listu.
     */
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
package njt.mavenproject2.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import njt.mavenproject2.entity.impl.Autor;
import njt.mavenproject2.repository.impl.AutorRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

/**
 * Test klasa za proveru funkcionalnosti klase {@link AutorController}.
 *
 * Testira dobavljanje svih autora i slučaj kada lista autora ne sadrži
 * nijedan element.
 *
 * @author Korisnik
 */
class AutorControllerTest {

    private AutorRepository repo;
    private AutorController controller;

    /**
     * Inicijalizuje mock repozitorijum i instancu kontrolera pre svakog testa.
     */
    @BeforeEach
    void setUp() {
        repo = mock(AutorRepository.class);
        controller = new AutorController(repo);
    }

    /**
     * Proverava uspešno dobavljanje liste svih autora.
     */
    @Test
    void testGetAll() {
        Autor a1 = new Autor();
        a1.setId(1L);
        a1.setIme("Ivo");
        a1.setPrezime("Andric");

        Autor a2 = new Autor();
        a2.setId(2L);
        a2.setIme("Mesa");
        a2.setPrezime("Selimovic");

        when(repo.findAll()).thenReturn(List.of(a1, a2));

        ResponseEntity<List<AutorController.AutorDto>> response =
                controller.getAll();

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());

        assertEquals(1L, response.getBody().get(0).id());
        assertEquals("Ivo", response.getBody().get(0).ime());
        assertEquals("Andric", response.getBody().get(0).prezime());

        assertEquals(2L, response.getBody().get(1).id());
        assertEquals("Mesa", response.getBody().get(1).ime());
        assertEquals("Selimovic", response.getBody().get(1).prezime());

        verify(repo).findAll();
    }

    /**
     * Proverava dobavljanje autora kada repozitorijum vraća praznu listu.
     */
    @Test
    void testGetAllPraznaLista() {
        when(repo.findAll()).thenReturn(List.of());

        ResponseEntity<List<AutorController.AutorDto>> response =
                controller.getAll();

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty());

        verify(repo).findAll();
    }
}
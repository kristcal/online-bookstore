package njt.mavenproject2.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import njt.mavenproject2.dto.impl.KnjigaDto;
import njt.mavenproject2.servis.KnjigaServis;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

/**
 * Test klasa za proveru funkcionalnosti klase {@link KnjigaAdminController}.
 *
 * Testira kreiranje, izmenu i brisanje knjiga preko administrativnih
 * operacija kontrolera.
 *
 * @author Korisnik
 */
class KnjigaAdminControllerTest {

    private KnjigaServis servis;
    private KnjigaAdminController controller;

    /**
     * Inicijalizuje mock servis i instancu kontrolera pre svakog testa.
     */
    @BeforeEach
    void setUp() {
        servis = mock(KnjigaServis.class);
        controller = new KnjigaAdminController(servis);
    }

    /**
     * Proverava uspešno kreiranje nove knjige.
     *
     * @throws Exception ukoliko servis baci izuzetak
     */
    @Test
    void testCreate() throws Exception {
        KnjigaDto dto = new KnjigaDto();
        dto.setNaziv("1984");

        KnjigaDto saved = new KnjigaDto();
        saved.setId(1L);
        saved.setNaziv("1984");

        when(servis.create(dto)).thenReturn(saved);

        ResponseEntity<KnjigaDto> response = controller.create(dto);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(1L, response.getBody().getId());
        verify(servis).create(dto);
    }

    /**
     * Proverava uspešnu izmenu postojeće knjige.
     *
     * @throws Exception ukoliko servis baci izuzetak
     */
    @Test
    void testUpdate() throws Exception {
        KnjigaDto dto = new KnjigaDto();
        dto.setNaziv("Novo");

        KnjigaDto updated = new KnjigaDto();
        updated.setId(1L);
        updated.setNaziv("Novo");

        when(servis.update(1L, dto)).thenReturn(updated);

        ResponseEntity<KnjigaDto> response = controller.update(1L, dto);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(1L, response.getBody().getId());
        verify(servis).update(1L, dto);
    }

    /**
     * Proverava uspešno brisanje knjige.
     */
    @Test
    void testDelete() {
        ResponseEntity<Void> response = controller.delete(1L);

        assertEquals(204, response.getStatusCode().value());
        assertNull(response.getBody());
        verify(servis).deleteById(1L);
    }
}
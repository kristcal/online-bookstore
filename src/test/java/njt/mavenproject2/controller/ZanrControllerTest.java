package njt.mavenproject2.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import njt.mavenproject2.dto.impl.ZanrDto;
import njt.mavenproject2.servis.ZanrServis;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

/**
 * Test klasa za proveru funkcionalnosti klase {@link ZanrController}.
 *
 * Testira dobavljanje svih žanrova, dobavljanje žanra po identifikatoru,
 * kreiranje, izmenu, brisanje i obradu izuzetaka.
 *
 * @author Korisnik
 */
class ZanrControllerTest {

    private ZanrServis service;
    private ZanrController controller;

    /**
     * Inicijalizuje mock servis i instancu kontrolera pre svakog testa.
     */
    @BeforeEach
    void setUp() {
        service = mock(ZanrServis.class);
        controller = new ZanrController(service);
    }

    /**
     * Proverava uspešno dobavljanje liste svih žanrova.
     */
    @Test
    void testGetAll() {
        ZanrDto z1 = new ZanrDto(1L, "Roman");
        ZanrDto z2 = new ZanrDto(2L, "Drama");

        when(service.findAll()).thenReturn(List.of(z1, z2));

        ResponseEntity<List<ZanrDto>> response = controller.getAll();

        assertEquals(200, response.getStatusCode().value());
        assertEquals(2, response.getBody().size());
        verify(service).findAll();
    }

    /**
     * Proverava uspešno dobavljanje žanra prema identifikatoru.
     *
     * @throws Exception ukoliko servis baci izuzetak
     */
    @Test
    void testGetById() throws Exception {
        ZanrDto dto = new ZanrDto(1L, "Roman");

        when(service.findById(1L)).thenReturn(dto);

        ResponseEntity<ZanrDto> response = controller.getById(1L);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(1L, response.getBody().getId());
        verify(service).findById(1L);
    }

    /**
     * Proverava obradu izuzetka kada žanr sa zadatim identifikatorom ne postoji.
     *
     * @throws Exception ukoliko servis baci izuzetak
     */
    @Test
    void testGetByIdException() throws Exception {
        when(service.findById(999L))
                .thenThrow(new Exception("Žanr nije pronađen!"));

        ResponseStatusException e = assertThrows(
                ResponseStatusException.class,
                () -> controller.getById(999L));

        assertEquals(400, e.getStatusCode().value());
        assertEquals("Žanr nije pronađen!", e.getReason());
    }

    /**
     * Proverava uspešno kreiranje novog žanra.
     */
    @Test
    void testCreate() {
        ZanrDto dto = new ZanrDto(null, "Roman");
        ZanrDto saved = new ZanrDto(1L, "Roman");

        when(service.create(dto)).thenReturn(saved);

        ResponseEntity<ZanrDto> response = controller.create(dto);

        assertEquals(201, response.getStatusCode().value());
        assertEquals(1L, response.getBody().getId());
        verify(service).create(dto);
    }

    /**
     * Proverava uspešnu izmenu postojećeg žanra.
     */
    @Test
    void testUpdate() {
        ZanrDto dto = new ZanrDto(null, "Drama");
        ZanrDto updated = new ZanrDto(1L, "Drama");

        when(service.update(dto)).thenReturn(updated);

        ResponseEntity<ZanrDto> response = controller.update(1L, dto);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(1L, dto.getId());
        assertEquals("Drama", response.getBody().getNaziv());
        verify(service).update(dto);
    }

    /**
     * Proverava uspešno brisanje žanra.
     */
    @Test
    void testDelete() {
        ResponseEntity<String> response = controller.delete(1L);

        assertEquals(200, response.getStatusCode().value());
        assertEquals("Zanr deleted.", response.getBody());
        verify(service).deleteById(1L);
    }
}
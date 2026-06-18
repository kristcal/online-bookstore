package njt.mavenproject2.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import njt.mavenproject2.dto.impl.KnjigaDto;
import njt.mavenproject2.servis.KnjigaServis;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

/**
 * Test klasa za proveru funkcionalnosti klase {@link KnjigaController}.
 *
 * Testira dobavljanje svih knjiga, dobavljanje knjige po identifikatoru,
 * kreiranje, izmenu, brisanje, filtriranje po žanru, filtriranje po ceni
 * i pretragu knjiga.
 *
 * @author Korisnik
 */
class KnjigaControllerTest {

    private KnjigaServis service;
    private KnjigaController controller;

    /**
     * Inicijalizuje mock servis i instancu kontrolera pre svakog testa.
     */
    @BeforeEach
    void setUp() {
        service = mock(KnjigaServis.class);
        controller = new KnjigaController(service);
    }

    /**
     * Proverava uspešno dobavljanje liste svih knjiga.
     */
    @Test
    void testGetAll() {
        KnjigaDto k1 = new KnjigaDto();
        k1.setId(1L);

        KnjigaDto k2 = new KnjigaDto();
        k2.setId(2L);

        when(service.findAll()).thenReturn(List.of(k1, k2));

        ResponseEntity<List<KnjigaDto>> response = controller.getAll();

        assertEquals(200, response.getStatusCode().value());
        assertEquals(2, response.getBody().size());
        verify(service).findAll();
    }

    /**
     * Proverava uspešno dobavljanje knjige prema identifikatoru.
     *
     * @throws Exception ukoliko servis baci izuzetak
     */
    @Test
    void testGetById() throws Exception {
        KnjigaDto dto = new KnjigaDto();
        dto.setId(1L);

        when(service.findById(1L)).thenReturn(dto);

        ResponseEntity<KnjigaDto> response = controller.getById(1L);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(1L, response.getBody().getId());
        verify(service).findById(1L);
    }

    /**
     * Proverava obradu izuzetka kada knjiga sa zadatim identifikatorom ne postoji.
     *
     * @throws Exception ukoliko servis baci izuzetak
     */
    @Test
    void testGetByIdException() throws Exception {
        when(service.findById(999L))
                .thenThrow(new Exception("Knjiga nije pronađena!"));

        ResponseStatusException e = assertThrows(
                ResponseStatusException.class,
                () -> controller.getById(999L));

        assertEquals(400, e.getStatusCode().value());
        assertEquals("Knjiga nije pronađena!", e.getReason());
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

        when(service.create(dto)).thenReturn(saved);

        ResponseEntity<KnjigaDto> response = controller.create(dto);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(1L, response.getBody().getId());
        verify(service).create(dto);
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

        when(service.update(1L, dto)).thenReturn(updated);

        ResponseEntity<KnjigaDto> response = controller.update(1L, dto);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(1L, response.getBody().getId());
        verify(service).update(1L, dto);
    }

    /**
     * Proverava uspešno brisanje knjige.
     */
    @Test
    void testDelete() {
        ResponseEntity<Void> response = controller.delete(1L);

        assertEquals(204, response.getStatusCode().value());
        assertNull(response.getBody());
        verify(service).deleteById(1L);
    }

    /**
     * Proverava uspešno dobavljanje knjiga prema žanru.
     */
    @Test
    void testByGenre() {
        KnjigaDto dto = new KnjigaDto();
        dto.setId(1L);

        when(service.findByGenre(2L)).thenReturn(List.of(dto));

        ResponseEntity<List<KnjigaDto>> response = controller.byGenre(2L);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(1, response.getBody().size());
        verify(service).findByGenre(2L);
    }

    /**
     * Proverava uspešno dobavljanje knjiga čija je cena manja ili jednaka zadatoj.
     */
    @Test
    void testCheap() {
        KnjigaDto dto = new KnjigaDto();
        dto.setId(1L);

        when(service.findCheaperThan(1000.0)).thenReturn(List.of(dto));

        ResponseEntity<List<KnjigaDto>> response = controller.cheap(1000.0);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(1, response.getBody().size());
        verify(service).findCheaperThan(1000.0);
    }

    /**
     * Proverava uspešnu pretragu knjiga prema tekstu, žanru i maksimalnoj ceni.
     */
    @Test
    void testSearch() {
        KnjigaDto dto = new KnjigaDto();
        dto.setId(1L);

        when(service.search("1984", 2L, 1000.0)).thenReturn(List.of(dto));

        ResponseEntity<List<KnjigaDto>> response =
                controller.search("1984", 2L, 1000.0);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(1, response.getBody().size());
        verify(service).search("1984", 2L, 1000.0);
    }
}
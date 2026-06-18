package njt.mavenproject2.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import njt.mavenproject2.dto.impl.PorudzbinaDto;
import njt.mavenproject2.servis.PorudzbinaServis;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

/**
 * Test klasa za proveru funkcionalnosti klase {@link PorudzbinaController}.
 *
 * Testira dobavljanje svih porudžbina, dobavljanje porudžbine po identifikatoru,
 * kreiranje, izmenu, brisanje, dobavljanje porudžbina za korisnika i obradu
 * izuzetaka.
 *
 * @author Korisnik
 */
class PorudzbinaControllerTest {

    private PorudzbinaServis service;
    private PorudzbinaController controller;

    /**
     * Inicijalizuje mock servis i instancu kontrolera pre svakog testa.
     */
    @BeforeEach
    void setUp() {
        service = mock(PorudzbinaServis.class);
        controller = new PorudzbinaController(service);
    }

    /**
     * Proverava uspešno dobavljanje liste svih porudžbina.
     */
    @Test
    void testGetAll() {
        PorudzbinaDto p1 = new PorudzbinaDto();
        p1.setId(1L);

        PorudzbinaDto p2 = new PorudzbinaDto();
        p2.setId(2L);

        when(service.findAll()).thenReturn(List.of(p1, p2));

        ResponseEntity<List<PorudzbinaDto>> response = controller.getAll();

        assertEquals(200, response.getStatusCode().value());
        assertEquals(2, response.getBody().size());
        verify(service).findAll();
    }

    /**
     * Proverava uspešno dobavljanje porudžbine prema identifikatoru.
     *
     * @throws Exception ukoliko servis baci izuzetak
     */
    @Test
    void testGetById() throws Exception {
        PorudzbinaDto dto = new PorudzbinaDto();
        dto.setId(1L);

        when(service.findById(1L)).thenReturn(dto);

        ResponseEntity<?> response = controller.getById(1L);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(dto, response.getBody());
        verify(service).findById(1L);
    }

    /**
     * Proverava odgovor kontrolera kada porudžbina sa zadatim identifikatorom ne postoji.
     *
     * @throws Exception ukoliko servis baci izuzetak
     */
    @Test
    void testGetByIdException() throws Exception {
        when(service.findById(999L))
                .thenThrow(new Exception("Porudžbina nije pronađena!"));

        ResponseEntity<?> response = controller.getById(999L);

        assertEquals(400, response.getStatusCode().value());
        assertEquals("Porudžbina nije pronađena!", response.getBody());
    }

    /**
     * Proverava uspešno kreiranje nove porudžbine.
     *
     * @throws Exception ukoliko servis baci izuzetak
     */
    @Test
    void testCreate() throws Exception {
        PorudzbinaDto req = new PorudzbinaDto();
        PorudzbinaDto res = new PorudzbinaDto();
        res.setId(1L);

        when(service.create(req)).thenReturn(res);

        ResponseEntity<?> response = controller.create(req);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(res, response.getBody());
        verify(service).create(req);
    }

    /**
     * Proverava odgovor kontrolera kada kreiranje porudžbine nije uspešno.
     *
     * @throws Exception ukoliko servis baci izuzetak
     */
    @Test
    void testCreateException() throws Exception {
        PorudzbinaDto req = new PorudzbinaDto();

        when(service.create(req))
                .thenThrow(new Exception("korisnik_id i stavke su obavezni"));

        ResponseEntity<?> response = controller.create(req);

        assertEquals(400, response.getStatusCode().value());
        assertEquals("korisnik_id i stavke su obavezni", response.getBody());
    }

    /**
     * Proverava uspešnu izmenu postojeće porudžbine.
     *
     * @throws Exception ukoliko servis baci izuzetak
     */
    @Test
    void testUpdate() throws Exception {
        PorudzbinaDto req = new PorudzbinaDto();
        PorudzbinaDto res = new PorudzbinaDto();
        res.setId(1L);

        when(service.update(1L, req)).thenReturn(res);

        ResponseEntity<?> response = controller.update(1L, req);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(res, response.getBody());
        verify(service).update(1L, req);
    }

    /**
     * Proverava odgovor kontrolera kada izmena porudžbine nije uspešna.
     *
     * @throws Exception ukoliko servis baci izuzetak
     */
    @Test
    void testUpdateException() throws Exception {
        PorudzbinaDto req = new PorudzbinaDto();

        when(service.update(999L, req))
                .thenThrow(new Exception("Porudžbina ne postoji"));

        ResponseEntity<?> response = controller.update(999L, req);

        assertEquals(400, response.getStatusCode().value());
        assertEquals("Porudžbina ne postoji", response.getBody());
    }

    /**
     * Proverava uspešno brisanje porudžbine.
     */
    @Test
    void testDelete() {
        ResponseEntity<?> response = controller.delete(1L);

        assertEquals(200, response.getStatusCode().value());
        assertEquals("Porudžbina obrisana.", response.getBody());
        verify(service).deleteById(1L);
    }

    /**
     * Proverava uspešno dobavljanje porudžbina za zadatog korisnika.
     */
    @Test
    void testMoje() {
        PorudzbinaDto dto = new PorudzbinaDto();
        dto.setId(1L);

        when(service.findByKorisnik(5L)).thenReturn(List.of(dto));

        ResponseEntity<List<PorudzbinaDto>> response = controller.moje(5L);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(1, response.getBody().size());
        verify(service).findByKorisnik(5L);
    }
}
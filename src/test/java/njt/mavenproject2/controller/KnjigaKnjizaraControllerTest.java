package njt.mavenproject2.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Map;

import njt.mavenproject2.dto.impl.KnjigaKnjizaraDto;
import njt.mavenproject2.servis.KnjigaKnjizaraServis;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

/**
 * Test klasa za proveru funkcionalnosti klase {@link KnjigaKnjizaraController}.
 *
 * Testira dobavljanje ponude za knjigu, dodavanje knjige u knjižaru, izmenu
 * količine, obradu izuzetaka i uklanjanje veze knjiga-knjižara.
 *
 * @author Korisnik
 */
class KnjigaKnjizaraControllerTest {

    private KnjigaKnjizaraServis servis;
    private KnjigaKnjizaraController controller;

    /**
     * Inicijalizuje mock servis i instancu kontrolera pre svakog testa.
     */
    @BeforeEach
    void setUp() {
        servis = mock(KnjigaKnjizaraServis.class);
        controller = new KnjigaKnjizaraController(servis);
    }

    /**
     * Proverava uspešno dobavljanje ponude za zadatu knjigu.
     */
    @Test
    void testList() {
        KnjigaKnjizaraDto dto = new KnjigaKnjizaraDto(1L, 2L, 10);

        when(servis.listForKnjiga(10L)).thenReturn(List.of(dto));

        ResponseEntity<List<KnjigaKnjizaraDto>> response
                = controller.list(10L);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(1, response.getBody().size());
        assertEquals(1L, response.getBody().get(0).getId());

        verify(servis).listForKnjiga(10L);
    }

    /**
     * Proverava uspešno dodavanje knjige u ponudu knjižare.
     *
     * @throws Exception ukoliko servis baci izuzetak
     */
    @Test
    void testAdd() throws Exception {
        KnjigaKnjizaraDto dto = new KnjigaKnjizaraDto(null, 2L, 10);
        KnjigaKnjizaraDto saved = new KnjigaKnjizaraDto(1L, 2L, 10);

        when(servis.addOrSet(10L, dto)).thenReturn(saved);

        ResponseEntity<KnjigaKnjizaraDto> response
                = controller.add(10L, dto);

        assertEquals(201, response.getStatusCode().value());
        assertEquals(1L, response.getBody().getId());

        verify(servis).addOrSet(10L, dto);
    }

    /**
     * Proverava obradu izuzetka prilikom dodavanja knjige u ponudu knjižare.
     *
     * @throws Exception ukoliko servis baci izuzetak
     */
    @Test
    void testAddException() throws Exception {
        KnjigaKnjizaraDto dto = new KnjigaKnjizaraDto(null, 2L, 10);

        when(servis.addOrSet(10L, dto))
                .thenThrow(new Exception("Knjizara nije pronađena!"));

        ResponseStatusException e = assertThrows(
                ResponseStatusException.class,
                () -> controller.add(10L, dto));

        assertEquals(400, e.getStatusCode().value());
        assertEquals("Knjizara nije pronađena!", e.getReason());
    }

    /**
     * Proverava uspešnu izmenu količine knjige u knjižari.
     *
     * @throws Exception ukoliko servis baci izuzetak
     */
    @Test
    void testUpdateQty() throws Exception {
        KnjigaKnjizaraDto updated = new KnjigaKnjizaraDto(1L, 2L, 20);

        when(servis.updateKolicina(1L, 20)).thenReturn(updated);

        ResponseEntity<KnjigaKnjizaraDto> response
                = controller.updateQty(10L, 1L, Map.of("kolicina", 20));

        assertEquals(200, response.getStatusCode().value());
        assertEquals(20, response.getBody().getKolicina());

        verify(servis).updateKolicina(1L, 20);
    }

    /**
     * Proverava obradu izuzetka prilikom izmene količine knjige u knjižari.
     *
     * @throws Exception ukoliko servis baci izuzetak
     */
    @Test
    void testUpdateQtyException() throws Exception {
        when(servis.updateKolicina(1L, 20))
                .thenThrow(new Exception("Veza nije pronađena!"));

        Map<String, Integer> body = Map.of("kolicina", 20);

        ResponseStatusException e = assertThrows(
                ResponseStatusException.class,
                () -> controller.updateQty(10L, 1L, body));

        assertEquals(400, e.getStatusCode().value());
        assertEquals("Veza nije pronađena!", e.getReason());
    }

    /**
     * Proverava uspešno uklanjanje knjige iz ponude knjižare.
     */
    @Test
    void testRemove() {
        ResponseEntity<Void> response
                = controller.remove(10L, 1L);

        assertEquals(200, response.getStatusCode().value());
        assertNull(response.getBody());

        verify(servis).remove(1L);
    }
}

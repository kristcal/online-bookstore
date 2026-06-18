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
 * Test klasa za proveru funkcionalnosti klase {@link PorudzbinaAdminController}.
 *
 * Testira dobavljanje svih porudžbina, dobavljanje jedne porudžbine,
 * promenu statusa porudžbine i brisanje porudžbine preko administrativnog
 * kontrolera.
 *
 * @author Korisnik
 */
class PorudzbinaAdminControllerTest {

    private PorudzbinaServis servis;
    private PorudzbinaAdminController controller;

    /**
     * Inicijalizuje mock servis i instancu kontrolera pre svakog testa.
     */
    @BeforeEach
    void setUp() {
        servis = mock(PorudzbinaServis.class);
        controller = new PorudzbinaAdminController(servis);
    }

    /**
     * Proverava uspešno dobavljanje liste svih porudžbina.
     */
    @Test
    void testSve() {
        PorudzbinaDto p1 = new PorudzbinaDto();
        p1.setId(1L);

        PorudzbinaDto p2 = new PorudzbinaDto();
        p2.setId(2L);

        when(servis.findAll()).thenReturn(List.of(p1, p2));

        ResponseEntity<List<PorudzbinaDto>> response =
                controller.sve();

        assertEquals(200, response.getStatusCode().value());
        assertEquals(2, response.getBody().size());

        verify(servis).findAll();
    }

    /**
     * Proverava uspešno dobavljanje porudžbine prema identifikatoru.
     *
     * @throws Exception ukoliko servis baci izuzetak
     */
    @Test
    void testJedna() throws Exception {
        PorudzbinaDto dto = new PorudzbinaDto();
        dto.setId(1L);

        when(servis.findById(1L)).thenReturn(dto);

        ResponseEntity<PorudzbinaDto> response =
                controller.jedna(1L);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(1L, response.getBody().getId());

        verify(servis).findById(1L);
    }

    /**
     * Proverava uspešnu promenu statusa porudžbine.
     *
     * @throws Exception ukoliko servis baci izuzetak
     */
    @Test
    void testPromeniStatus() throws Exception {
        PorudzbinaDto dto = new PorudzbinaDto();
        dto.setId(1L);
        dto.setStatus("OBRADJENA");

        when(servis.promeniStatus(1L, "OBRADJENA"))
                .thenReturn(dto);

        ResponseEntity<PorudzbinaDto> response =
                controller.promeniStatus(1L, "OBRADJENA");

        assertEquals(200, response.getStatusCode().value());
        assertEquals("OBRADJENA", response.getBody().getStatus());

        verify(servis).promeniStatus(1L, "OBRADJENA");
    }

    /**
     * Proverava uspešno brisanje porudžbine.
     */
    @Test
    void testObrisi() {
        ResponseEntity<Void> response =
                controller.obrisi(1L);

        assertEquals(204, response.getStatusCode().value());
        assertNull(response.getBody());

        verify(servis).deleteById(1L);
    }
}
package njt.mavenproject2.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import njt.mavenproject2.dto.impl.StavkaPorudzbineDto;
import njt.mavenproject2.servis.StavkaPorudzbineServis;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

class PorudzbinaStavkeControllerTest {

    private StavkaPorudzbineServis servis;
    private PorudzbinaStavkeController controller;

    @BeforeEach
    void setUp() {
        servis = mock(StavkaPorudzbineServis.class);
        controller = new PorudzbinaStavkeController(servis);
    }

    @Test
    void testList() {
        StavkaPorudzbineDto dto =
                new StavkaPorudzbineDto(1L, 1, 10L, 2, 500.0);

        when(servis.listForPorudzbina(5L))
                .thenReturn(List.of(dto));

        ResponseEntity<List<StavkaPorudzbineDto>> response =
                controller.list(5L);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(1, response.getBody().size());
        assertEquals(1L, response.getBody().get(0).getId());

        verify(servis).listForPorudzbina(5L);
    }

    @Test
    void testAdd() throws Exception {
        StavkaPorudzbineDto dto =
                new StavkaPorudzbineDto(null, 1, 10L, 2, 500.0);

        StavkaPorudzbineDto saved =
                new StavkaPorudzbineDto(1L, 1, 10L, 2, 500.0);

        when(servis.add(5L, dto)).thenReturn(saved);

        ResponseEntity<StavkaPorudzbineDto> response =
                controller.add(5L, dto);

        assertEquals(201, response.getStatusCode().value());
        assertEquals(1L, response.getBody().getId());

        verify(servis).add(5L, dto);
    }

    @Test
    void testAddException() throws Exception {
        StavkaPorudzbineDto dto =
                new StavkaPorudzbineDto(null, 1, 10L, 2, 500.0);

        when(servis.add(5L, dto))
                .thenThrow(new Exception("Porudžbina nije pronađena!"));

        ResponseStatusException e = assertThrows(
                ResponseStatusException.class,
                () -> controller.add(5L, dto));

        assertEquals(400, e.getStatusCode().value());
        assertEquals("Porudžbina nije pronađena!", e.getReason());
    }

    @Test
    void testUpdate() throws Exception {
        StavkaPorudzbineDto dto =
                new StavkaPorudzbineDto(1L, 1, 10L, 3, 500.0);

        when(servis.update(1L, dto)).thenReturn(dto);

        ResponseEntity<StavkaPorudzbineDto> response =
                controller.update(5L, 1L, dto);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(3, response.getBody().getKolicina());

        verify(servis).update(1L, dto);
    }

    @Test
    void testUpdateException() throws Exception {
        StavkaPorudzbineDto dto =
                new StavkaPorudzbineDto(1L, 1, 10L, 3, 500.0);

        when(servis.update(1L, dto))
                .thenThrow(new Exception("Stavka porudžbine nije pronađena!"));

        ResponseStatusException e = assertThrows(
                ResponseStatusException.class,
                () -> controller.update(5L, 1L, dto));

        assertEquals(400, e.getStatusCode().value());
        assertEquals("Stavka porudžbine nije pronađena!", e.getReason());
    }

    @Test
    void testDelete() {
        ResponseEntity<Void> response =
                controller.delete(5L, 1L);

        assertEquals(204, response.getStatusCode().value());
        assertNull(response.getBody());

        verify(servis).remove(1L);
    }
}
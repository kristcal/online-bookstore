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

class KnjigaControllerTest {

    private KnjigaServis service;
    private KnjigaController controller;

    @BeforeEach
    void setUp() {
        service = mock(KnjigaServis.class);
        controller = new KnjigaController(service);
    }

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

    @Test
    void testDelete() {
        ResponseEntity<Void> response = controller.delete(1L);

        assertEquals(204, response.getStatusCode().value());
        assertNull(response.getBody());
        verify(service).deleteById(1L);
    }

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
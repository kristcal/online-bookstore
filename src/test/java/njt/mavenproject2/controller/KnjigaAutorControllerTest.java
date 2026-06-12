package njt.mavenproject2.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import njt.mavenproject2.dto.impl.KnjigaAutorDto;
import njt.mavenproject2.servis.KnjigaAutorServis;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

class KnjigaAutorControllerTest {

    private KnjigaAutorServis servis;
    private KnjigaAutorController controller;

    @BeforeEach
    void setUp() {
        servis = mock(KnjigaAutorServis.class);
        controller = new KnjigaAutorController(servis);
    }

    @Test
    void testList() {
        KnjigaAutorDto dto = new KnjigaAutorDto(1L, 2L, "Pisac");

        when(servis.listForKnjiga(10L)).thenReturn(List.of(dto));

        ResponseEntity<List<KnjigaAutorDto>> response =
                controller.list(10L);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(1, response.getBody().size());
        assertEquals(1L, response.getBody().get(0).getId());

        verify(servis).listForKnjiga(10L);
    }

    @Test
    void testAdd() throws Exception {
        KnjigaAutorDto dto = new KnjigaAutorDto(null, 2L, "Pisac");
        KnjigaAutorDto saved = new KnjigaAutorDto(1L, 2L, "Pisac");

        when(servis.addAutorToKnjiga(10L, dto)).thenReturn(saved);

        ResponseEntity<KnjigaAutorDto> response =
                controller.add(10L, dto);

        assertEquals(201, response.getStatusCode().value());
        assertEquals(1L, response.getBody().getId());

        verify(servis).addAutorToKnjiga(10L, dto);
    }

    @Test
    void testAddException() throws Exception {
        KnjigaAutorDto dto = new KnjigaAutorDto(null, 2L, "Pisac");

        when(servis.addAutorToKnjiga(10L, dto))
                .thenThrow(new Exception("Autor nije pronađen!"));

        ResponseStatusException e = assertThrows(
                ResponseStatusException.class,
                () -> controller.add(10L, dto));

        assertEquals(400, e.getStatusCode().value());
        assertEquals("Autor nije pronađen!", e.getReason());
    }

    @Test
    void testRemove() {
        ResponseEntity<Void> response =
                controller.remove(10L, 1L);

        assertEquals(200, response.getStatusCode().value());
        assertNull(response.getBody());

        verify(servis).remove(1L);
    }
}
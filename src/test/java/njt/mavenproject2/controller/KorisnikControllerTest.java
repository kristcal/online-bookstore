package njt.mavenproject2.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import njt.mavenproject2.dto.impl.KorisnikDto;
import njt.mavenproject2.servis.KorisnikServis;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

class KorisnikControllerTest {

    private KorisnikServis service;
    private KorisnikController controller;

    @BeforeEach
    void setUp() {
        service = mock(KorisnikServis.class);
        controller = new KorisnikController(service);
    }

    @Test
    void testGetAll() {
        KorisnikDto k1 =
                new KorisnikDto(1L, "Pera", "Peric", "p@test.com", "123");

        KorisnikDto k2 =
                new KorisnikDto(2L, "Mika", "Mikic", "m@test.com", "456");

        when(service.findAll()).thenReturn(List.of(k1, k2));

        ResponseEntity<List<KorisnikDto>> response =
                controller.getAll();

        assertEquals(200, response.getStatusCode().value());
        assertEquals(2, response.getBody().size());

        verify(service).findAll();
    }

    @Test
    void testGetById() throws Exception {
        KorisnikDto dto =
                new KorisnikDto(1L, "Pera", "Peric", "p@test.com", "123");

        when(service.findById(1L)).thenReturn(dto);

        ResponseEntity<KorisnikDto> response =
                controller.getById(1L);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(1L, response.getBody().getId());

        verify(service).findById(1L);
    }

    @Test
    void testGetByIdException() throws Exception {
        when(service.findById(999L))
                .thenThrow(new Exception("Korisnik nije pronađen!"));

        ResponseStatusException e = assertThrows(
                ResponseStatusException.class,
                () -> controller.getById(999L));

        assertEquals(400, e.getStatusCode().value());
        assertEquals("Korisnik nije pronađen!", e.getReason());
    }

    @Test
    void testCreate() {
        KorisnikDto dto =
                new KorisnikDto(null, "Pera", "Peric", "p@test.com", "123");

        KorisnikDto saved =
                new KorisnikDto(1L, "Pera", "Peric", "p@test.com", "123");

        when(service.create(dto)).thenReturn(saved);

        ResponseEntity<KorisnikDto> response =
                controller.create(dto);

        assertEquals(201, response.getStatusCode().value());
        assertEquals(1L, response.getBody().getId());

        verify(service).create(dto);
    }

    @Test
    void testUpdate() throws Exception {
        KorisnikDto dto =
                new KorisnikDto(null, "Pera", "Peric", "p@test.com", "123");

        KorisnikDto updated =
                new KorisnikDto(1L, "Pera", "Peric", "p@test.com", "123");

        when(service.update(1L, dto)).thenReturn(updated);

        ResponseEntity<KorisnikDto> response =
                controller.update(1L, dto);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(1L, response.getBody().getId());

        verify(service).update(1L, dto);
    }

    @Test
    void testUpdateException() throws Exception {
        KorisnikDto dto =
                new KorisnikDto(null, "Pera", "Peric", "p@test.com", "123");

        when(service.update(999L, dto))
                .thenThrow(new Exception("Korisnik nije pronađen!"));

        ResponseStatusException e = assertThrows(
                ResponseStatusException.class,
                () -> controller.update(999L, dto));

        assertEquals(400, e.getStatusCode().value());
        assertEquals("Korisnik nije pronađen!", e.getReason());
    }

    @Test
    void testDelete() {
        ResponseEntity<String> response =
                controller.delete(1L);

        assertEquals(200, response.getStatusCode().value());
        assertEquals("Korisnik obrisan.", response.getBody());

        verify(service).deleteById(1L);
    }
}
package njt.mavenproject2.dto.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class KnjigaAutorDtoTest {

    @Test
    void testKnjigaAutorDto() {
        KnjigaAutorDto dto = new KnjigaAutorDto();

        assertNotNull(dto);
    }

    @Test
    void testKnjigaAutorDtoLongLongString() {
        KnjigaAutorDto dto = new KnjigaAutorDto(1L, 2L, "Pisac");

        assertEquals(1L, dto.getId());
        assertEquals(2L, dto.getAutorId());
        assertEquals("Pisac", dto.getUloga());
    }

    @Test
    void testSetId() {
        KnjigaAutorDto dto = new KnjigaAutorDto();
        dto.setId(1L);

        assertEquals(1L, dto.getId());
    }

    @Test
    void testSetAutorId() {
        KnjigaAutorDto dto = new KnjigaAutorDto();
        dto.setAutorId(2L);

        assertEquals(2L, dto.getAutorId());
    }

    @Test
    void testSetUloga() {
        KnjigaAutorDto dto = new KnjigaAutorDto();
        dto.setUloga("Pisac");

        assertEquals("Pisac", dto.getUloga());
    }
}
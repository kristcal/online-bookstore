package njt.mavenproject2.dto.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class KnjigaKnjizaraDtoTest {

    @Test
    void testKnjigaKnjizaraDto() {
        KnjigaKnjizaraDto dto = new KnjigaKnjizaraDto();

        assertNotNull(dto);
    }

    @Test
    void testKnjigaKnjizaraDtoLongLongInteger() {
        KnjigaKnjizaraDto dto = new KnjigaKnjizaraDto(1L, 2L, 10);

        assertEquals(1L, dto.getId());
        assertEquals(2L, dto.getKnjizaraId());
        assertEquals(10, dto.getKolicina());
    }

    @Test
    void testSetId() {
        KnjigaKnjizaraDto dto = new KnjigaKnjizaraDto();
        dto.setId(1L);

        assertEquals(1L, dto.getId());
    }

    @Test
    void testSetKnjizaraId() {
        KnjigaKnjizaraDto dto = new KnjigaKnjizaraDto();
        dto.setKnjizaraId(2L);

        assertEquals(2L, dto.getKnjizaraId());
    }

    @Test
    void testSetKolicina() {
        KnjigaKnjizaraDto dto = new KnjigaKnjizaraDto();
        dto.setKolicina(10);

        assertEquals(10, dto.getKolicina());
    }

    @Test
    void testSetLokacija() {
        KnjigaKnjizaraDto dto = new KnjigaKnjizaraDto();
        dto.setLokacija("Beograd");

        assertEquals("Beograd", dto.getLokacija());
    }
}
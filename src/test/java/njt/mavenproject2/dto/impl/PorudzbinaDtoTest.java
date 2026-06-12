package njt.mavenproject2.dto.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;

class PorudzbinaDtoTest {

    @Test
    void testPorudzbinaDto() {
        PorudzbinaDto dto = new PorudzbinaDto();

        assertNotNull(dto);
        assertNotNull(dto.getStavke());
        assertTrue(dto.getStavke().isEmpty());
    }

    @Test
    void testSetId() {
        PorudzbinaDto dto = new PorudzbinaDto();
        dto.setId(1L);

        assertEquals(1L, dto.getId());
    }

    @Test
    void testSetDatum() {
        PorudzbinaDto dto = new PorudzbinaDto();
        LocalDateTime datum = LocalDateTime.of(2026, 6, 12, 10, 0);

        dto.setDatum(datum);

        assertEquals(datum, dto.getDatum());
    }

    @Test
    void testSetKorisnikId() {
        PorudzbinaDto dto = new PorudzbinaDto();
        dto.setKorisnikId(5L);

        assertEquals(5L, dto.getKorisnikId());
    }

    @Test
    void testSetUkupanIznos() {
        PorudzbinaDto dto = new PorudzbinaDto();
        dto.setUkupanIznos(2500.0);

        assertEquals(2500.0, dto.getUkupanIznos());
    }

    @Test
    void testSetStatus() {
        PorudzbinaDto dto = new PorudzbinaDto();
        dto.setStatus("KREIRANA");

        assertEquals("KREIRANA", dto.getStatus());
    }

    @Test
    void testSetStavke() {
        PorudzbinaDto dto = new PorudzbinaDto();

        PorudzbinaDto.Stavka stavka = new PorudzbinaDto.Stavka();
        stavka.setKnjigaId(1L);

        dto.setStavke(List.of(stavka));

        assertEquals(1, dto.getStavke().size());
        assertEquals(1L, dto.getStavke().get(0).getKnjigaId());
    }

    @Test
    void testStavkaSetKnjigaId() {
        PorudzbinaDto.Stavka stavka = new PorudzbinaDto.Stavka();
        stavka.setKnjigaId(1L);

        assertEquals(1L, stavka.getKnjigaId());
    }

    @Test
    void testStavkaSetKolicina() {
        PorudzbinaDto.Stavka stavka = new PorudzbinaDto.Stavka();
        stavka.setKolicina(3);

        assertEquals(3, stavka.getKolicina());
    }

    @Test
    void testStavkaSetCena() {
        PorudzbinaDto.Stavka stavka = new PorudzbinaDto.Stavka();
        stavka.setCena(1000.0);

        assertEquals(1000.0, stavka.getCena());
    }

    @Test
    void testStavkaSetNaziv() {
        PorudzbinaDto.Stavka stavka = new PorudzbinaDto.Stavka();
        stavka.setNaziv("1984");

        assertEquals("1984", stavka.getNaziv());
    }
}
package njt.mavenproject2.dto.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Test klasa za proveru funkcionalnosti DTO klase {@link PorudzbinaDto}.
 *
 * Testira gettere, settere i rad ugrađene klase Stavka.
 *
 * @author Korisnik
 */
class PorudzbinaDtoTest {

	/**
	 * Proverava kreiranje praznog DTO objekta.
	 */
    @Test
    void testPorudzbinaDto() {
        PorudzbinaDto dto = new PorudzbinaDto();

        assertNotNull(dto);
        assertNotNull(dto.getStavke());
        assertTrue(dto.getStavke().isEmpty());
    }

    /**
     * Proverava postavljanje identifikatora porudžbine.
     */
    @Test
    void testSetId() {
        PorudzbinaDto dto = new PorudzbinaDto();
        dto.setId(1L);

        assertEquals(1L, dto.getId());
    }

    /**
     * Proverava postavljanje datuma porudžbine.
     */
    @Test
    void testSetDatum() {
        PorudzbinaDto dto = new PorudzbinaDto();
        LocalDateTime datum = LocalDateTime.of(2026, 6, 12, 10, 0);

        dto.setDatum(datum);

        assertEquals(datum, dto.getDatum());
    }

    /**
     * Proverava postavljanje identifikatora korisnika.
     */
    @Test
    void testSetKorisnikId() {
        PorudzbinaDto dto = new PorudzbinaDto();
        dto.setKorisnikId(5L);

        assertEquals(5L, dto.getKorisnikId());
    }

    /**
     * Proverava postavljanje ukupnog iznosa porudžbine.
     */
    @Test
    void testSetUkupanIznos() {
        PorudzbinaDto dto = new PorudzbinaDto();
        dto.setUkupanIznos(2500.0);

        assertEquals(2500.0, dto.getUkupanIznos());
    }

    /**
     * Proverava postavljanje statusa porudžbine.
     */
    @Test
    void testSetStatus() {
        PorudzbinaDto dto = new PorudzbinaDto();
        dto.setStatus("KREIRANA");

        assertEquals("KREIRANA", dto.getStatus());
    }

    /**
     * Proverava postavljanje liste stavki porudžbine.
     */
    @Test
    void testSetStavke() {
        PorudzbinaDto dto = new PorudzbinaDto();

        PorudzbinaDto.Stavka stavka = new PorudzbinaDto.Stavka();
        stavka.setKnjigaId(1L);

        dto.setStavke(List.of(stavka));

        assertEquals(1, dto.getStavke().size());
        assertEquals(1L, dto.getStavke().get(0).getKnjigaId());
    }

    /**
     * Proverava postavljanje identifikatora knjige.
     */
    @Test
    void testStavkaSetKnjigaId() {
        PorudzbinaDto.Stavka stavka = new PorudzbinaDto.Stavka();
        stavka.setKnjigaId(1L);

        assertEquals(1L, stavka.getKnjigaId());
    }

    /**
     * Proverava postavljanje količine knjiga.
     */
    @Test
    void testStavkaSetKolicina() {
        PorudzbinaDto.Stavka stavka = new PorudzbinaDto.Stavka();
        stavka.setKolicina(3);

        assertEquals(3, stavka.getKolicina());
    }

    /**
     * Proverava postavljanje cene stavke.
     */
    @Test
    void testStavkaSetCena() {
        PorudzbinaDto.Stavka stavka = new PorudzbinaDto.Stavka();
        stavka.setCena(1000.0);

        assertEquals(1000.0, stavka.getCena());
    }

    /**
     * Proverava postavljanje naziva knjige.
     */
    @Test
    void testStavkaSetNaziv() {
        PorudzbinaDto.Stavka stavka = new PorudzbinaDto.Stavka();
        stavka.setNaziv("1984");

        assertEquals("1984", stavka.getNaziv());
    }
}
package njt.mavenproject2.dto.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Test klasa za proveru funkcionalnosti DTO klase {@link KnjigaKnjizaraDto}.
 *
 * Testira konstruktore, gettere i settere klase.
 *
 * @author Korisnik
 */
class KnjigaKnjizaraDtoTest {

	/**
     * Proverava kreiranje praznog DTO objekta.
     */
    @Test
    void testKnjigaKnjizaraDto() {
        KnjigaKnjizaraDto dto = new KnjigaKnjizaraDto();

        assertNotNull(dto);
    }

    /**
     * Proverava konstruktor sa svim parametrima.
     */
    @Test
    void testKnjigaKnjizaraDtoLongLongInteger() {
        KnjigaKnjizaraDto dto = new KnjigaKnjizaraDto(1L, 2L, 10);

        assertEquals(1L, dto.getId());
        assertEquals(2L, dto.getKnjizaraId());
        assertEquals(10, dto.getKolicina());
    }

    /**
     * Proverava postavljanje identifikatora DTO objekta.
     */
    @Test
    void testSetId() {
        KnjigaKnjizaraDto dto = new KnjigaKnjizaraDto();
        dto.setId(1L);

        assertEquals(1L, dto.getId());
    }

    /**
     * Proverava postavljanje identifikatora knjižare.
     */
    @Test
    void testSetKnjizaraId() {
        KnjigaKnjizaraDto dto = new KnjigaKnjizaraDto();
        dto.setKnjizaraId(2L);

        assertEquals(2L, dto.getKnjizaraId());
    }

    /**
     * Proverava postavljanje količine knjiga.
     */
    @Test
    void testSetKolicina() {
        KnjigaKnjizaraDto dto = new KnjigaKnjizaraDto();
        dto.setKolicina(10);

        assertEquals(10, dto.getKolicina());
    }

    /**
     * Proverava postavljanje lokacije knjižare.
     */
    @Test
    void testSetLokacija() {
        KnjigaKnjizaraDto dto = new KnjigaKnjizaraDto();
        dto.setLokacija("Beograd");

        assertEquals("Beograd", dto.getLokacija());
    }
}
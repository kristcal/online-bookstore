package njt.mavenproject2.dto.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Test klasa za proveru funkcionalnosti DTO klase {@link KnjigaAutorDto}.
 *
 * Testira konstruktore, gettere i settere klase.
 *
 * @author Korisnik
 */
class KnjigaAutorDtoTest {

	/**
     * Proverava kreiranje praznog DTO objekta.
     */
    @Test
    void testKnjigaAutorDto() {
        KnjigaAutorDto dto = new KnjigaAutorDto();

        assertNotNull(dto);
    }

    /**
     * Proverava konstruktor sa svim parametrima.
     */
    @Test
    void testKnjigaAutorDtoLongLongString() {
        KnjigaAutorDto dto = new KnjigaAutorDto(1L, 2L, "Pisac");

        assertEquals(1L, dto.getId());
        assertEquals(2L, dto.getAutorId());
        assertEquals("Pisac", dto.getUloga());
    }

    /**
     * Proverava postavljanje identifikatora DTO objekta.
     */
    @Test
    void testSetId() {
        KnjigaAutorDto dto = new KnjigaAutorDto();
        dto.setId(1L);

        assertEquals(1L, dto.getId());
    }

    /**
     * Proverava postavljanje identifikatora autora.
     */
    @Test
    void testSetAutorId() {
        KnjigaAutorDto dto = new KnjigaAutorDto();
        dto.setAutorId(2L);

        assertEquals(2L, dto.getAutorId());
    }

    /**
     * Proverava postavljanje uloge autora.
     */
    @Test
    void testSetUloga() {
        KnjigaAutorDto dto = new KnjigaAutorDto();
        dto.setUloga("Pisac");

        assertEquals("Pisac", dto.getUloga());
    }
}
package njt.mavenproject2.dto.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Test klasa za proveru funkcionalnosti DTO klase {@link KnjizaraDto}.
 *
 * Testira konstruktore, gettere i settere klase.
 *
 * @author Korisnik
 */
class KnjizaraDtoTest {

	/**
	 * Proverava konstruktor sa svim parametrima.
	 */
    @Test
    void testKnjizaraDtoLongStringStringString() {
        KnjizaraDto dto = new KnjizaraDto(1L, "Laguna", "Beograd", "011123456");

        assertEquals(1L, dto.getId());
        assertEquals("Laguna", dto.getNaziv());
        assertEquals("Beograd", dto.getLokacija());
        assertEquals("011123456", dto.getKontakt());
        assertNotNull(dto.getPonuda());
        assertTrue(dto.getPonuda().isEmpty());
    }

    /**
     * Proverava postavljanje identifikatora DTO objekta.
     */
    @Test
    void testSetId() {
        KnjizaraDto dto = new KnjizaraDto(null, "Laguna", "Beograd", "011123456");
        dto.setId(1L);

        assertEquals(1L, dto.getId());
    }

    /**
     * Proverava postavljanje naziva knjižare.
     */
    @Test
    void testSetNaziv() {
        KnjizaraDto dto = new KnjizaraDto(null, "Laguna", "Beograd", "011123456");
        dto.setNaziv("Vulkan");

        assertEquals("Vulkan", dto.getNaziv());
    }

    /**
     * Proverava postavljanje lokacije knjižare.
     */
    @Test
    void testSetLokacija() {
        KnjizaraDto dto = new KnjizaraDto(null, "Laguna", "Beograd", "011123456");
        dto.setLokacija("Novi Sad");

        assertEquals("Novi Sad", dto.getLokacija());
    }

    /**
     * Proverava postavljanje kontakt podataka knjižare.
     */
    @Test
    void testSetKontakt() {
        KnjizaraDto dto = new KnjizaraDto(null, "Laguna", "Beograd", "011123456");
        dto.setKontakt("064123456");

        assertEquals("064123456", dto.getKontakt());
    }

    /**
     * Proverava postavljanje ponude knjižare.
     */
    @Test
    void testSetPonuda() {
        KnjizaraDto dto = new KnjizaraDto(null, "Laguna", "Beograd", "011123456");

        KnjigaKnjizaraDto ponuda = new KnjigaKnjizaraDto(1L, 2L, 10);
        dto.setPonuda(List.of(ponuda));

        assertEquals(1, dto.getPonuda().size());
        assertEquals(1L, dto.getPonuda().get(0).getId());
    }
}
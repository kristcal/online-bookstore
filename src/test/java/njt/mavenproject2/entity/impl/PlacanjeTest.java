package njt.mavenproject2.entity.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

/**
 * Test klasa za proveru funkcionalnosti entiteta {@link Placanje}.
 *
 * Testira konstruktore, gettere, settere, metode equals, hashCode,
 * toString i validaciona ograničenja definisana nad atributima klase.
 *
 * @author Korisnik
 */
class PlacanjeTest {

    private Placanje placanje;
    private Validator validator;

    /**
     * Inicijalizuje objekat klase Placanje i validator pre svakog testa.
     */
    @BeforeEach
    void setUp() {
        placanje = new Placanje();
        ValidatorFactory factory =
                Validation.buildDefaultValidatorFactory();

        validator = factory.getValidator();
    }

    /**
     * Oslobađa referencu na objekat nakon svakog testa.
     */
    @AfterEach
    void tearDown() {
        placanje = null;
    }

    /**
     * Proverava kreiranje praznog objekta klase Placanje.
     */
    @Test
    void testPlacanje() {
        assertNotNull(placanje);
    }

    /**
     * Proverava konstruktor sa svim parametrima.
     */
    @Test
    void testPlacanjeLongDoubleStringStringLocalDateTimeString() {
        LocalDateTime datum = LocalDateTime.of(2026, 6, 11, 17, 0);

        placanje = new Placanje(1L, 2500.0, "Kartica", "PLACENO", datum, "2026-001");

        assertEquals(1L, placanje.getId());
        assertEquals(2500.0, placanje.getIznos());
        assertEquals("Kartica", placanje.getNacinPlacanja());
        assertEquals("PLACENO", placanje.getStatusPlacanja());
        assertEquals(datum, placanje.getDatumPlacanja());
        assertEquals("2026-001", placanje.getPozivNaBroj());
    }

    /**
     * Proverava postavljanje identifikatora plaćanja.
     */
    @Test
    void testSetId() {
        placanje.setId(1L);
        assertEquals(1L, placanje.getId());
    }

    /**
     * Proverava postavljanje iznosa plaćanja.
     */
    @Test
    void testSetIznos() {
        placanje.setIznos(3000.0);
        assertEquals(3000.0, placanje.getIznos());
    }

    /**
     * Proverava postavljanje načina plaćanja.
     */
    @Test
    void testSetNacinPlacanja() {
        placanje.setNacinPlacanja("Gotovina");
        assertEquals("Gotovina", placanje.getNacinPlacanja());
    }

    /**
     * Proverava postavljanje statusa plaćanja.
     */
    @Test
    void testSetStatusPlacanja() {
        placanje.setStatusPlacanja("NA_CEKANJU");
        assertEquals("NA_CEKANJU", placanje.getStatusPlacanja());
    }

    /**
     * Proverava postavljanje datuma plaćanja.
     */
    @Test
    void testSetDatumPlacanja() {
        LocalDateTime datum = LocalDateTime.now();
        placanje.setDatumPlacanja(datum);
        assertEquals(datum, placanje.getDatumPlacanja());
    }

    /**
     * Proverava postavljanje poziva na broj.
     */
    @Test
    void testSetPozivNaBroj() {
        placanje.setPozivNaBroj("2026-002");
        assertEquals("2026-002", placanje.getPozivNaBroj());
    }

    /**
     * Proverava postavljanje porudžbine povezane sa plaćanjem.
     */
    @Test
    void testSetPorudzbina() {
        Porudzbina porudzbina = new Porudzbina();
        porudzbina.setId(1L);

        placanje.setPorudzbina(porudzbina);

        assertEquals(porudzbina, placanje.getPorudzbina());
    }

    /**
     * Proverava da su dva plaćanja jednaka kada imaju isti identifikator.
     */
    @Test
    void testEqualsObject() {
        Placanje p1 = new Placanje();
        p1.setId(1L);

        Placanje p2 = new Placanje();
        p2.setId(1L);

        assertTrue(p1.equals(p2));
    }

    /**
     * Proverava da dva plaćanja nisu jednaka kada imaju različite identifikatore.
     */
    @Test
    void testEqualsObjectFalse() {
        Placanje p1 = new Placanje();
        p1.setId(1L);

        Placanje p2 = new Placanje();
        p2.setId(2L);

        assertFalse(p1.equals(p2));
    }

    /**
     * Proverava da plaćanje nije jednako null vrednosti.
     */
    @Test
    void testEqualsNull() {
        placanje.setId(1L);
        assertFalse(placanje.equals(null));
    }

    /**
     * Proverava da plaćanje nije jednako objektu druge klase.
     */
    @Test
    void testEqualsDrugaKlasa() {
        placanje.setId(1L);
        assertFalse(placanje.equals("tekst"));
    }

    /**
     * Proverava hashCode za plaćanja sa istim identifikatorom.
     */
    @Test
    void testHashCode() {
        Placanje p1 = new Placanje();
        p1.setId(1L);

        Placanje p2 = new Placanje();
        p2.setId(1L);

        assertEquals(p1.hashCode(), p2.hashCode());
    }

    /**
     * Proverava tekstualni prikaz plaćanja.
     */
    @Test
    void testToString() {
        placanje.setId(1L);
        placanje.setIznos(2500.0);
        placanje.setNacinPlacanja("Kartica");
        placanje.setStatusPlacanja("PLACENO");

        String s = placanje.toString();

        assertTrue(s.contains("1"));
        assertTrue(s.contains("2500.0"));
        assertTrue(s.contains("Kartica"));
        assertTrue(s.contains("PLACENO"));
    }

    /**
     * Proverava validaciju kada iznos plaćanja nije postavljen.
     */
    @Test
    void testIznosNotNull() {

        placanje.setIznos(null);

        Set<ConstraintViolation<Placanje>> violations =
                validator.validate(placanje);

        assertTrue(
                violations.stream()
                        .anyMatch(v -> v.getPropertyPath().toString().equals("iznos"))
        );
    }

    /**
     * Proverava validaciju kada iznos plaćanja nije pozitivan.
     */
    @Test
    void testIznosPositive() {

        placanje.setIznos(-100.0);

        Set<ConstraintViolation<Placanje>> violations =
                validator.validate(placanje);

        assertTrue(
                violations.stream()
                        .anyMatch(v -> v.getPropertyPath().toString().equals("iznos"))
        );
    }

    /**
     * Proverava validaciju kada je način plaćanja prazan.
     */
    @Test
    void testNacinPlacanjaNotBlank() {

        placanje.setNacinPlacanja("");

        Set<ConstraintViolation<Placanje>> violations =
                validator.validate(placanje);

        assertTrue(
                violations.stream()
                        .anyMatch(v -> v.getPropertyPath().toString().equals("nacinPlacanja"))
        );
    }

    /**
     * Proverava validaciju maksimalne dužine načina plaćanja.
     */
    @Test
    void testNacinPlacanjaMaxSize() {

        placanje.setNacinPlacanja("a".repeat(51));

        Set<ConstraintViolation<Placanje>> violations =
                validator.validate(placanje);

        assertTrue(
                violations.stream()
                        .anyMatch(v -> v.getPropertyPath().toString().equals("nacinPlacanja"))
        );
    }

    /**
     * Proverava validaciju kada je status plaćanja prazan.
     */
    @Test
    void testStatusPlacanjaNotBlank() {

        placanje.setStatusPlacanja("");

        Set<ConstraintViolation<Placanje>> violations =
                validator.validate(placanje);

        assertTrue(
                violations.stream()
                        .anyMatch(v -> v.getPropertyPath().toString().equals("statusPlacanja"))
        );
    }

    /**
     * Proverava validaciju maksimalne dužine statusa plaćanja.
     */
    @Test
    void testStatusPlacanjaMaxSize() {

        placanje.setStatusPlacanja("a".repeat(51));

        Set<ConstraintViolation<Placanje>> violations =
                validator.validate(placanje);

        assertTrue(
                violations.stream()
                        .anyMatch(v -> v.getPropertyPath().toString().equals("statusPlacanja"))
        );
    }

    /**
     * Proverava validaciju kada datum plaćanja nije postavljen.
     */
    @Test
    void testDatumPlacanjaNotNull() {

        placanje.setDatumPlacanja(null);

        Set<ConstraintViolation<Placanje>> violations =
                validator.validate(placanje);

        assertTrue(
                violations.stream()
                        .anyMatch(v -> v.getPropertyPath().toString().equals("datumPlacanja"))
        );
    }

    /**
     * Proverava validaciju kada je poziv na broj prazan.
     */
    @Test
    void testPozivNaBrojNotBlank() {

        placanje.setPozivNaBroj("");

        Set<ConstraintViolation<Placanje>> violations =
                validator.validate(placanje);

        assertTrue(
                violations.stream()
                        .anyMatch(v -> v.getPropertyPath().toString().equals("pozivNaBroj"))
        );
    }

    /**
     * Proverava validaciju maksimalne dužine poziva na broj.
     */
    @Test
    void testPozivNaBrojMaxSize() {

        placanje.setPozivNaBroj("a".repeat(51));

        Set<ConstraintViolation<Placanje>> violations =
                validator.validate(placanje);

        assertTrue(
                violations.stream()
                        .anyMatch(v -> v.getPropertyPath().toString().equals("pozivNaBroj"))
        );
    }
}
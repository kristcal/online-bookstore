package njt.mavenproject2.entity.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.time.Month;

/**
 * Test klasa za proveru funkcionalnosti entiteta {@link Porudzbina}.
 *
 * Testira konstruktore, gettere, settere, metode equals, hashCode,
 * toString i validaciona ograničenja definisana nad atributima klase.
 *
 * @author Korisnik
 */
class PorudzbinaTest {

    private Porudzbina porudzbina;
    private Validator validator;

    /**
     * Inicijalizuje objekat klase Porudzbina i validator pre svakog testa.
     */
    @BeforeEach
    void setUp() {
        porudzbina = new Porudzbina();
        ValidatorFactory factory =
                Validation.buildDefaultValidatorFactory();

        validator = factory.getValidator();
    }

    /**
     * Oslobađa referencu na objekat nakon svakog testa.
     */
    @AfterEach
    void tearDown() {
        porudzbina = null;
    }

    /**
     * Proverava kreiranje praznog objekta klase Porudzbina.
     */
    @Test
    void testPorudzbina() {
        assertNotNull(porudzbina);
    }

    /**
     * Proverava postavljanje identifikatora porudžbine.
     */
    @Test
    void testSetId() {
        porudzbina.setId(1L);
        assertEquals(1L, porudzbina.getId());
    }

    /**
     * Proverava postavljanje datuma porudžbine.
     */
    @Test
    void testSetDatum() {
        LocalDateTime datum = LocalDateTime.of(2026, Month.JUNE, 11, 17, 0);

        porudzbina.setDatum(datum);

        assertEquals(datum, porudzbina.getDatum());
    }

    /**
     * Proverava postavljanje ukupnog iznosa porudžbine.
     */
    @Test
    void testSetUkupanIznos() {
        porudzbina.setUkupanIznos(5000.0);

        assertEquals(5000.0, porudzbina.getUkupanIznos());
    }

    /**
     * Proverava postavljanje statusa porudžbine.
     */
    @Test
    void testSetStatus() {
        porudzbina.setStatus("PLACENA");

        assertEquals("PLACENA", porudzbina.getStatus());
    }

    /**
     * Proverava postavljanje korisnika koji je napravio porudžbinu.
     */
    @Test
    void testSetKorisnik() {
        Korisnik k = new Korisnik();

        porudzbina.setKorisnik(k);

        assertEquals(k, porudzbina.getKorisnik());
    }

    /**
     * Proverava postavljanje plaćanja povezanog sa porudžbinom.
     */
    @Test
    void testSetPlacanje() {
        Placanje p = new Placanje();

        porudzbina.setPlacanje(p);

        assertEquals(p, porudzbina.getPlacanje());
    }

    /**
     * Proverava postavljanje liste stavki porudžbine.
     */
    @Test
    void testSetStavke() {
        StavkaPorudzbine s = new StavkaPorudzbine();

        List<StavkaPorudzbine> stavke = new ArrayList<>();
        stavke.add(s);

        porudzbina.setStavke(stavke);

        assertEquals(1, porudzbina.getStavke().size());
    }

    /**
     * Proverava da su dve porudžbine jednake kada imaju isti identifikator.
     */
    @Test
    void testEqualsObject() {
        Porudzbina p1 = new Porudzbina();
        p1.setId(1L);

        Porudzbina p2 = new Porudzbina();
        p2.setId(1L);

        assertEquals(p1,p2);
    }

    /**
     * Proverava da dve porudžbine nisu jednake kada imaju različite identifikatore.
     */
    @Test
    void testEqualsObjectFalse() {
        Porudzbina p1 = new Porudzbina();
        p1.setId(1L);

        Porudzbina p2 = new Porudzbina();
        p2.setId(2L);

        assertNotEquals(p1,p2);
    }
    
    /**
     * Proverava da je porudžbina jednaka samoj sebi.
     */
    @Test
    void testEqualsSameObject() {

        Porudzbina p = new Porudzbina();

        assertEquals(p, p);
    }

    /**
     * Proverava da porudžbina nije jednaka objektu druge klase.
     */
    @Test
    void testEqualsDifferentClass() {

        porudzbina.setId(1L);

        assertFalse(porudzbina.equals("tekst"));
    }

    /**
     * Proverava da porudžbina nije jednaka null vrednosti.
     */
    @Test
    void testEqualsNull() {

        porudzbina.setId(1L);

        assertFalse(porudzbina.equals(null));
    }
    
    /**
     * Proverava da dve porudžbine sa null identifikatorom nisu jednake.
     */
    @Test
    void testEqualsBothIdsNull() {

        Porudzbina p1 = new Porudzbina();
        Porudzbina p2 = new Porudzbina();

        assertNotEquals(p1, p2);
    }

    /**
     * Proverava hashCode za porudžbine sa istim identifikatorom.
     */
    @Test
    void testHashCode() {
        Porudzbina p1 = new Porudzbina();
        p1.setId(1L);

        Porudzbina p2 = new Porudzbina();
        p2.setId(1L);

        assertEquals(p1.hashCode(), p2.hashCode());
    }

    /**
     * Proverava tekstualni prikaz porudžbine.
     */
    @Test
    void testToString() {
        porudzbina.setId(1L);
        porudzbina.setUkupanIznos(5000.0);
        porudzbina.setStatus("PLACENA");

        String s = porudzbina.toString();

        assertTrue(s.contains("1"));
        assertTrue(s.contains("5000.0"));
        assertTrue(s.contains("PLACENA"));
    }

    /**
     * Proverava validaciju kada datum porudžbine nije postavljen.
     */
    @Test
    void testDatumNotNull() {

        porudzbina.setDatum(null);

        Set<ConstraintViolation<Porudzbina>> violations =
                validator.validate(porudzbina);

        assertTrue(
                violations.stream()
                        .anyMatch(v -> v.getPropertyPath().toString().equals("datum"))
        );
    }

    /**
     * Proverava validaciju kada ukupan iznos porudžbine nije postavljen.
     */
    @Test
    void testUkupanIznosNotNull() {

        porudzbina.setUkupanIznos(null);

        Set<ConstraintViolation<Porudzbina>> violations =
                validator.validate(porudzbina);

        assertTrue(
                violations.stream()
                        .anyMatch(v -> v.getPropertyPath().toString().equals("ukupanIznos"))
        );
    }

    /**
     * Proverava validaciju kada ukupan iznos porudžbine nije pozitivan.
     */
    @Test
    void testUkupanIznosPositive() {

        porudzbina.setUkupanIznos(-100.0);

        Set<ConstraintViolation<Porudzbina>> violations =
                validator.validate(porudzbina);

        assertTrue(
                violations.stream()
                        .anyMatch(v -> v.getPropertyPath().toString().equals("ukupanIznos"))
        );
    }

    /**
     * Proverava validaciju kada je status porudžbine prazan.
     */
    @Test
    void testStatusNotBlank() {

        porudzbina.setStatus("");

        Set<ConstraintViolation<Porudzbina>> violations =
                validator.validate(porudzbina);

        assertTrue(
                violations.stream()
                        .anyMatch(v -> v.getPropertyPath().toString().equals("status"))
        );
    }

    /**
     * Proverava validaciju maksimalne dužine statusa porudžbine.
     */
    @Test
    void testStatusMaxSize() {

        porudzbina.setStatus("a".repeat(51));

        Set<ConstraintViolation<Porudzbina>> violations =
                validator.validate(porudzbina);

        assertTrue(
                violations.stream()
                        .anyMatch(v -> v.getPropertyPath().toString().equals("status"))
        );
    }

    /**
     * Proverava konstruktor sa identifikatorom, datumom, ukupnim iznosom i korisnikom.
     */
    @Test
    void testPorudzbinaLongLocalDateTimeDoubleKorisnik() {

        Korisnik k = new Korisnik();
        k.setId(1L);

        LocalDateTime datum =
                LocalDateTime.of(2026, Month.JUNE, 11, 18, 0);

        Porudzbina p =
                new Porudzbina(1L, datum, 2500.0, k);

        assertEquals(1L, p.getId());
        assertEquals(datum, p.getDatum());
        assertEquals(2500.0, p.getUkupanIznos());
        assertEquals(k, p.getKorisnik());
        assertEquals("KREIRANA", p.getStatus());
    }
}
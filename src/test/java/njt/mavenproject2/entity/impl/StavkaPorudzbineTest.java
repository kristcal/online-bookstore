package njt.mavenproject2.entity.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

/**
 * Test klasa za proveru funkcionalnosti entiteta {@link StavkaPorudzbine}.
 *
 * Testira konstruktore, gettere, settere, metode equals, hashCode,
 * toString i validaciona ograničenja definisana nad atributima klase.
 *
 * @author Korisnik
 */
class StavkaPorudzbineTest {

    private StavkaPorudzbine stavka;
    private Validator validator;

    /**
     * Inicijalizuje objekat klase StavkaPorudzbine i validator pre svakog testa.
     */
    @BeforeEach
    void setUp() {
        stavka = new StavkaPorudzbine();
        ValidatorFactory factory =
                Validation.buildDefaultValidatorFactory();

        validator = factory.getValidator();
    }

    /**
     * Oslobađa referencu na objekat nakon svakog testa.
     */
    @AfterEach
    void tearDown() {
        stavka = null;
    }

    /**
     * Proverava kreiranje praznog objekta klase StavkaPorudzbine.
     */
    @Test
    void testStavkaPorudzbine() {
        assertNotNull(stavka);
    }

    /**
     * Proverava konstruktor sa rednim brojem, količinom, cenom, porudžbinom i knjigom.
     */
    @Test
    void testKonstruktor() {
        Porudzbina p = new Porudzbina();
        Knjiga k = new Knjiga();

        stavka = new StavkaPorudzbine(
                1,
                2,
                1000.0,
                p,
                k
        );

        assertEquals(1, stavka.getRb());
        assertEquals(2, stavka.getKolicina());
        assertEquals(1000.0, stavka.getCenaK());
        assertEquals(2000.0, stavka.getUkupanIznosStavke());
    }

    /**
     * Proverava postavljanje identifikatora stavke porudžbine.
     */
    @Test
    void testSetId() {
        stavka.setId(1L);
        assertEquals(1L, stavka.getId());
    }

    /**
     * Proverava postavljanje rednog broja stavke.
     */
    @Test
    void testSetRb() {
        stavka.setRb(2);
        assertEquals(2, stavka.getRb());
    }

    /**
     * Proverava postavljanje količine.
     */
    @Test
    void testSetKolicina() {
        stavka.setKolicina(5);
        assertEquals(5, stavka.getKolicina());
    }

    /**
     * Proverava postavljanje cene knjige u okviru stavke.
     */
    @Test
    void testSetCenaK() {
        stavka.setCenaK(1200.0);
        assertEquals(1200.0, stavka.getCenaK());
    }

    /**
     * Proverava postavljanje ukupnog iznosa stavke porudžbine.
     */
    @Test
    void testSetUkupanIznosStavke() {
        stavka.setUkupanIznosStavke(6000.0);
        assertEquals(6000.0, stavka.getUkupanIznosStavke());
    }

    /**
     * Proverava postavljanje porudžbine kojoj stavka pripada.
     */
    @Test
    void testSetPorudzbina() {
        Porudzbina p = new Porudzbina();

        stavka.setPorudzbina(p);

        assertEquals(p, stavka.getPorudzbina());
    }

    /**
     * Proverava postavljanje knjige koja se nalazi u stavci.
     */
    @Test
    void testSetKnjiga() {
        Knjiga k = new Knjiga();

        stavka.setKnjiga(k);

        assertEquals(k, stavka.getKnjiga());
    }

    /**
     * Proverava da su dve stavke porudžbine jednake kada imaju isti identifikator.
     */
    @Test
    void testEqualsObject() {
        StavkaPorudzbine s1 = new StavkaPorudzbine();
        s1.setId(1L);

        StavkaPorudzbine s2 = new StavkaPorudzbine();
        s2.setId(1L);

        assertEquals(s1,s2);
    }

    /**
     * Proverava da dve stavke porudžbine nisu jednake kada imaju različite identifikatore.
     */
    @Test
    void testEqualsObjectFalse() {
        StavkaPorudzbine s1 = new StavkaPorudzbine();
        s1.setId(1L);

        StavkaPorudzbine s2 = new StavkaPorudzbine();
        s2.setId(2L);

        assertNotEquals(s1,s2);
    }

    /**
     * Proverava da je stavka porudžbine jednaka samoj sebi.
     */
    @Test
    void testEqualsSameObject() {

        StavkaPorudzbine s = new StavkaPorudzbine();

        assertEquals(s, s);
    }

    /**
     * Proverava da stavka porudžbine nije jednaka objektu druge klase.
     */
    @Test
    void testEqualsDifferentClass() {

        StavkaPorudzbine s = new StavkaPorudzbine();

        assertNotEquals("tekst", s);
    }

    /**
     * Proverava da stavka porudžbine nije jednaka null vrednosti.
     */
    @Test
    void testEqualsNull() {

        StavkaPorudzbine s = new StavkaPorudzbine();

        assertNotEquals(null, s);
    }

    /**
     * Proverava hashCode za stavke porudžbine sa istim identifikatorom.
     */
    @Test
    void testHashCode() {
        StavkaPorudzbine s1 = new StavkaPorudzbine();
        s1.setId(1L);

        StavkaPorudzbine s2 = new StavkaPorudzbine();
        s2.setId(1L);

        assertEquals(s1.hashCode(), s2.hashCode());
    }

    /**
     * Proverava tekstualni prikaz stavke porudžbine.
     */
    @Test
    void testToString() {
        stavka.setId(1L);
        stavka.setRb(1);
        stavka.setKolicina(2);
        stavka.setCenaK(1000.0);

        String s = stavka.toString();

        assertTrue(s.contains("1"));
        assertTrue(s.contains("2"));
        assertTrue(s.contains("1000.0"));
    }

    /**
     * Proverava validaciju kada redni broj stavke nije postavljen.
     */
    @Test
    void testRbNotNull() {

        stavka.setRb(null);

        Set<ConstraintViolation<StavkaPorudzbine>> violations =
                validator.validate(stavka);

        assertTrue(
                violations.stream()
                        .anyMatch(v -> v.getPropertyPath().toString().equals("rb"))
        );
    }

    /**
     * Proverava validaciju kada redni broj stavke nije pozitivan.
     */
    @Test
    void testRbPositive() {

        stavka.setRb(-1);

        Set<ConstraintViolation<StavkaPorudzbine>> violations =
                validator.validate(stavka);

        assertTrue(
                violations.stream()
                        .anyMatch(v -> v.getPropertyPath().toString().equals("rb"))
        );
    }

    /**
     * Proverava validaciju kada količina nije postavljena.
     */
    @Test
    void testKolicinaNotNull() {

        stavka.setKolicina(null);

        Set<ConstraintViolation<StavkaPorudzbine>> violations =
                validator.validate(stavka);

        assertTrue(
                violations.stream()
                        .anyMatch(v -> v.getPropertyPath().toString().equals("kolicina"))
        );
    }

    /**
     * Proverava validaciju kada količina nije pozitivna.
     */
    @Test
    void testKolicinaPositive() {

        stavka.setKolicina(-1);

        Set<ConstraintViolation<StavkaPorudzbine>> violations =
                validator.validate(stavka);

        assertTrue(
                violations.stream()
                        .anyMatch(v -> v.getPropertyPath().toString().equals("kolicina"))
        );
    }

    /**
     * Proverava validaciju kada cena knjige u stavci nije postavljena.
     */
    @Test
    void testCenaKNotNull() {

        stavka.setCenaK(null);

        Set<ConstraintViolation<StavkaPorudzbine>> violations =
                validator.validate(stavka);

        assertTrue(
                violations.stream()
                        .anyMatch(v -> v.getPropertyPath().toString().equals("cenaK"))
        );
    }

    /**
     * Proverava validaciju kada cena knjige u stavci nije pozitivna.
     */
    @Test
    void testCenaKPositive() {

        stavka.setCenaK(-100.0);

        Set<ConstraintViolation<StavkaPorudzbine>> violations =
                validator.validate(stavka);

        assertTrue(
                violations.stream()
                        .anyMatch(v -> v.getPropertyPath().toString().equals("cenaK"))
        );
    }
}
package njt.mavenproject2.entity.impl;

import static org.junit.jupiter.api.Assertions.*;

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

/**
 * Test klasa za proveru funkcionalnosti entiteta {@link Adresa}.
 *
 * Testira konstruktore, gettere, settere, metode equals, hashCode,
 * toString i validaciona ograničenja definisana nad atributima klase.
 *
 * @author Korisnik
 */
class AdresaTest {

    private Adresa adresa;
    private Validator validator;

    /**
     * Inicijalizuje objekat klase Adresa i validator pre svakog testa.
     */
    @BeforeEach
    void setUp() {
        adresa = new Adresa();

        ValidatorFactory factory =
                Validation.buildDefaultValidatorFactory();

        validator = factory.getValidator();
    }

    /**
     * Oslobađa referencu na objekat nakon svakog testa.
     */
    @AfterEach
    void tearDown() {
        adresa = null;
    }

    /**
     * Proverava kreiranje praznog objekta klase Adresa.
     */
    @Test
    void testAdresa() {
        assertNotNull(adresa);
    }

    /**
     * Proverava konstruktor sa svim parametrima.
     */
    @Test
    void testAdresaLongStringStringStringStringString() {
        adresa = new Adresa(1L, "Nemanjina", "12A", "Beograd", "11000", "Srbija");

        assertEquals(1L, adresa.getId());
        assertEquals("Nemanjina", adresa.getUlica());
        assertEquals("12A", adresa.getBroj());
        assertEquals("Beograd", adresa.getGrad());
        assertEquals("11000", adresa.getPostanskiBroj());
        assertEquals("Srbija", adresa.getDrzava());
    }

    /**
     * Proverava postavljanje identifikatora adrese.
     */
    @Test
    void testSetId() {
        adresa.setId(1L);
        assertEquals(1L, adresa.getId());
    }

    /**
     * Proverava postavljanje ulice.
     */
    @Test
    void testSetUlica() {
        adresa.setUlica("Bulevar kralja Aleksandra");
        assertEquals("Bulevar kralja Aleksandra", adresa.getUlica());
    }

    /**
     * Proverava postavljanje broja.
     */
    @Test
    void testSetBroj() {
        adresa.setBroj("73");
        assertEquals("73", adresa.getBroj());
    }

    /**
     * Proverava postavljanje grada.
     */
    @Test
    void testSetGrad() {
        adresa.setGrad("Beograd");
        assertEquals("Beograd", adresa.getGrad());
    }

    /**
     * Proverava postavljanje poštanskog broja.
     */
    @Test
    void testSetPostanskiBroj() {
        adresa.setPostanskiBroj("11000");
        assertEquals("11000", adresa.getPostanskiBroj());
    }

    /**
     * Proverava postavljanje države.
     */
    @Test
    void testSetDrzava() {
        adresa.setDrzava("Srbija");
        assertEquals("Srbija", adresa.getDrzava());
    }

    /**
     * Proverava postavljanje liste korisnika koji su povezani sa adresom.
     */
    @Test
    void testSetKorisnici() {
        Korisnik k = new Korisnik();
        k.setId(1L);

        List<Korisnik> korisnici = new ArrayList<>();
        korisnici.add(k);

        adresa.setKorisnici(korisnici);

        assertEquals(1, adresa.getKorisnici().size());
        assertTrue(adresa.getKorisnici().contains(k));
    }

    /**
     * Proverava da su dve adrese jednake kada imaju isti identifikator.
     */
    @Test
    void testEqualsObject() {
        Adresa a1 = new Adresa();
        a1.setId(1L);

        Adresa a2 = new Adresa();
        a2.setId(1L);

        assertEquals(a1, a2);
    }

    /**
     * Proverava da dve adrese nisu jednake kada imaju različite identifikatore.
     */
    @Test
    void testEqualsObjectFalse() {
        Adresa a1 = new Adresa();
        a1.setId(1L);

        Adresa a2 = new Adresa();
        a2.setId(2L);

        assertNotEquals(a1, a2);
    }

    /**
     * Proverava da adresa nije jednaka null vrednosti.
     */
    @Test
    void testEqualsNull() {
        adresa.setId(1L);
        assertNotEquals(null, adresa);
    }

    /**
     * Proverava da adresa nije jednaka objektu druge klase.
     */
    @Test
    void testEqualsDrugaKlasa() {
        adresa.setId(1L);
        assertNotEquals("tekst", adresa);
    }

    /**
     * Proverava hashCode za adrese sa istim identifikatorom.
     */
    @Test
    void testHashCode() {
        Adresa a1 = new Adresa();
        a1.setId(1L);

        Adresa a2 = new Adresa();
        a2.setId(1L);

        assertEquals(a1.hashCode(), a2.hashCode());
    }

    /**
     * Proverava tekstualni prikaz adrese.
     */
    @Test
    void testToString() {
        adresa = new Adresa(1L, "Nemanjina", "12A", "Beograd", "11000", "Srbija");

        String s = adresa.toString();

        assertTrue(s.contains("1"));
        assertTrue(s.contains("Nemanjina"));
        assertTrue(s.contains("12A"));
        assertTrue(s.contains("Beograd"));
    }

    /**
     * Proverava validaciju kada je ulica prazna.
     */
    @Test
    void testUlicaNotBlank() {
        adresa.setUlica("");

        Set<ConstraintViolation<Adresa>> violations = validator.validate(adresa);

        assertTrue(
                violations.stream()
                        .anyMatch(v -> v.getPropertyPath().toString().equals("ulica"))
        );
    }

    /**
     * Proverava validaciju maksimalne dužine ulice.
     */
    @Test
    void testUlicaMaxSize() {
        adresa.setUlica("a".repeat(101));

        Set<ConstraintViolation<Adresa>> violations = validator.validate(adresa);

        assertTrue(
                violations.stream()
                        .anyMatch(v -> v.getPropertyPath().toString().equals("ulica"))
        );
    }

    /**
     * Proverava validaciju kada je broj prazan.
     */
    @Test
    void testBrojNotBlank() {
        adresa.setBroj("");

        Set<ConstraintViolation<Adresa>> violations = validator.validate(adresa);

        assertTrue(
                violations.stream()
                        .anyMatch(v -> v.getPropertyPath().toString().equals("broj"))
        );
    }

    /**
     * Proverava validaciju maksimalne dužine broja.
     */
    @Test
    void testBrojMaxSize() {
        adresa.setBroj("a".repeat(21));

        Set<ConstraintViolation<Adresa>> violations = validator.validate(adresa);

        assertTrue(
                violations.stream()
                        .anyMatch(v -> v.getPropertyPath().toString().equals("broj"))
        );
    }

    /**
     * Proverava validaciju kada je grad prazan.
     */
    @Test
    void testGradNotBlank() {
        adresa.setGrad("");

        Set<ConstraintViolation<Adresa>> violations = validator.validate(adresa);

        assertTrue(
                violations.stream()
                        .anyMatch(v -> v.getPropertyPath().toString().equals("grad"))
        );
    }

    /**
     * Proverava validaciju maksimalne dužine grada.
     */
    @Test
    void testGradMaxSize() {
        adresa.setGrad("a".repeat(61));

        Set<ConstraintViolation<Adresa>> violations = validator.validate(adresa);

        assertTrue(
                violations.stream()
                        .anyMatch(v -> v.getPropertyPath().toString().equals("grad"))
        );
    }

    /**
     * Proverava validaciju kada je poštanski broj prazan.
     */
    @Test
    void testPostanskiBrojNotBlank() {
        adresa.setPostanskiBroj("");

        Set<ConstraintViolation<Adresa>> violations = validator.validate(adresa);

        assertTrue(
                violations.stream()
                        .anyMatch(v -> v.getPropertyPath().toString().equals("postanskiBroj"))
        );
    }

    /**
     * Proverava validaciju maksimalne dužine poštanskog broja.
     */
    @Test
    void testPostanskiBrojMaxSize() {
        adresa.setPostanskiBroj("a".repeat(21));

        Set<ConstraintViolation<Adresa>> violations = validator.validate(adresa);

        assertTrue(
                violations.stream()
                        .anyMatch(v -> v.getPropertyPath().toString().equals("postanskiBroj"))
        );
    }

    /**
     * Proverava validaciju kada je država prazna.
     */
    @Test
    void testDrzavaNotBlank() {
        adresa.setDrzava("");

        Set<ConstraintViolation<Adresa>> violations = validator.validate(adresa);

        assertTrue(
                violations.stream()
                        .anyMatch(v -> v.getPropertyPath().toString().equals("drzava"))
        );
    }

    /**
     * Proverava validaciju maksimalne dužine države.
     */
    @Test
    void testDrzavaMaxSize() {
        adresa.setDrzava("a".repeat(61));

        Set<ConstraintViolation<Adresa>> violations = validator.validate(adresa);

        assertTrue(
                violations.stream()
                        .anyMatch(v -> v.getPropertyPath().toString().equals("drzava"))
        );
    }
}
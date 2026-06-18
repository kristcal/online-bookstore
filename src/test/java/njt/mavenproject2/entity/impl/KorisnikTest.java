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
 * Test klasa za proveru funkcionalnosti entiteta {@link Korisnik}.
 *
 * Testira konstruktore, gettere, settere, metode equals, hashCode,
 * toString i validaciona ograničenja definisana nad atributima klase.
 *
 * @author Korisnik
 */
class KorisnikTest {

    private Korisnik korisnik;
    private Validator validator;

    /**
     * Inicijalizuje objekat klase Korisnik i validator pre svakog testa.
     */
    @BeforeEach
    void setUp() {
        korisnik = new Korisnik();
        ValidatorFactory factory =
                Validation.buildDefaultValidatorFactory();

        validator = factory.getValidator();
    }

    /**
     * Oslobađa referencu na objekat nakon svakog testa.
     */
    @AfterEach
    void tearDown() {
        korisnik = null;
    }

    /**
     * Proverava kreiranje praznog objekta klase Korisnik.
     */
    @Test
    void testKorisnik() {
        assertNotNull(korisnik);
    }

    /**
     * Proverava konstruktor sa svim parametrima.
     */
    @Test
    void testKorisnikLongStringStringStringString() {
        korisnik = new Korisnik(
                1L,
                "Kristina",
                "Calic",
                "kristina@gmail.com",
                "123456"
        );

        assertEquals(1L, korisnik.getId());
        assertEquals("Kristina", korisnik.getIme());
        assertEquals("Calic", korisnik.getPrezime());
        assertEquals("kristina@gmail.com", korisnik.getEmail());
        assertEquals("123456", korisnik.getLozinka());
    }

    /**
     * Proverava postavljanje identifikatora korisnika.
     */
    @Test
    void testSetId() {
        korisnik.setId(1L);
        assertEquals(1L, korisnik.getId());
    }

    /**
     * Proverava postavljanje imena korisnika.
     */
    @Test
    void testSetIme() {
        korisnik.setIme("Kristina");
        assertEquals("Kristina", korisnik.getIme());
    }

    /**
     * Proverava postavljanje prezimena korisnika.
     */
    @Test
    void testSetPrezime() {
        korisnik.setPrezime("Calic");
        assertEquals("Calic", korisnik.getPrezime());
    }

    /**
     * Proverava postavljanje email adrese korisnika.
     */
    @Test
    void testSetEmail() {
        korisnik.setEmail("test@gmail.com");
        assertEquals("test@gmail.com", korisnik.getEmail());
    }

    /**
     * Proverava postavljanje lozinke korisnika.
     */
    @Test
    void testSetLozinka() {
        korisnik.setLozinka("123456");
        assertEquals("123456", korisnik.getLozinka());
    }

    /**
     * Proverava postavljanje uloge korisnika.
     */
    @Test
    void testSetUloga() {
        korisnik.setUloga("ADMIN");
        assertEquals("ADMIN", korisnik.getUloga());
    }

    /**
     * Proverava postavljanje adrese korisnika.
     */
    @Test
    void testSetAdresa() {
        Adresa adresa = new Adresa();
        adresa.setId(1L);

        korisnik.setAdresa(adresa);

        assertEquals(adresa, korisnik.getAdresa());
    }

    /**
     * Proverava postavljanje liste porudžbina korisnika.
     */
    @Test
    void testSetPorudzbine() {
        Porudzbina p = new Porudzbina();
        p.setId(1L);

        List<Porudzbina> porudzbine = new ArrayList<>();
        porudzbine.add(p);

        korisnik.setPorudzbine(porudzbine);

        assertEquals(1, korisnik.getPorudzbine().size());
        assertTrue(korisnik.getPorudzbine().contains(p));
    }

    /**
     * Proverava da su dva korisnika jednaka kada imaju isti identifikator.
     */
    @Test
    void testEqualsObject() {
        Korisnik k1 = new Korisnik();
        k1.setId(1L);

        Korisnik k2 = new Korisnik();
        k2.setId(1L);

        assertTrue(k1.equals(k2));
    }

    /**
     * Proverava da dva korisnika nisu jednaka kada imaju različite identifikatore.
     */
    @Test
    void testEqualsObjectFalse() {
        Korisnik k1 = new Korisnik();
        k1.setId(1L);

        Korisnik k2 = new Korisnik();
        k2.setId(2L);

        assertFalse(k1.equals(k2));
    }

    /**
     * Proverava da korisnik nije jednak null vrednosti.
     */
    @Test
    void testEqualsNull() {
        korisnik.setId(1L);
        assertFalse(korisnik.equals(null));
    }

    /**
     * Proverava da korisnik nije jednak objektu druge klase.
     */
    @Test
    void testEqualsDrugaKlasa() {
        korisnik.setId(1L);
        assertFalse(korisnik.equals("tekst"));
    }

    /**
     * Proverava hashCode za korisnike sa istim identifikatorom.
     */
    @Test
    void testHashCode() {
        Korisnik k1 = new Korisnik();
        k1.setId(1L);

        Korisnik k2 = new Korisnik();
        k2.setId(1L);

        assertEquals(k1.hashCode(), k2.hashCode());
    }

    /**
     * Proverava tekstualni prikaz korisnika.
     */
    @Test
    void testToString() {
        korisnik.setId(1L);
        korisnik.setIme("Kristina");
        korisnik.setPrezime("Calic");
        korisnik.setEmail("kristina@gmail.com");
        korisnik.setUloga("ADMIN");

        String s = korisnik.toString();

        assertTrue(s.contains("1"));
        assertTrue(s.contains("Kristina"));
        assertTrue(s.contains("Calic"));
        assertTrue(s.contains("kristina@gmail.com"));
        assertTrue(s.contains("ADMIN"));
    }

    /**
     * Proverava validaciju kada je ime korisnika prazno.
     */
    @Test
    void testImeNotBlank() {

        korisnik.setIme("");

        Set<ConstraintViolation<Korisnik>> violations =
                validator.validate(korisnik);

        assertTrue(
                violations.stream()
                        .anyMatch(v -> v.getPropertyPath().toString().equals("ime"))
        );
    }

    /**
     * Proverava validaciju maksimalne dužine imena korisnika.
     */
    @Test
    void testImeMaxSize() {

        korisnik.setIme("a".repeat(61));

        Set<ConstraintViolation<Korisnik>> violations =
                validator.validate(korisnik);

        assertTrue(
                violations.stream()
                        .anyMatch(v -> v.getPropertyPath().toString().equals("ime"))
        );
    }

    /**
     * Proverava validaciju kada je prezime korisnika prazno.
     */
    @Test
    void testPrezimeNotBlank() {

        korisnik.setPrezime("");

        Set<ConstraintViolation<Korisnik>> violations =
                validator.validate(korisnik);

        assertTrue(
                violations.stream()
                        .anyMatch(v -> v.getPropertyPath().toString().equals("prezime"))
        );
    }

    /**
     * Proverava validaciju maksimalne dužine prezimena korisnika.
     */
    @Test
    void testPrezimeMaxSize() {

        korisnik.setPrezime("a".repeat(61));

        Set<ConstraintViolation<Korisnik>> violations =
                validator.validate(korisnik);

        assertTrue(
                violations.stream()
                        .anyMatch(v -> v.getPropertyPath().toString().equals("prezime"))
        );
    }

    /**
     * Proverava validaciju kada je email adresa prazna.
     */
    @Test
    void testEmailNotBlank() {

        korisnik.setEmail("");

        Set<ConstraintViolation<Korisnik>> violations =
                validator.validate(korisnik);

        assertTrue(
                violations.stream()
                        .anyMatch(v -> v.getPropertyPath().toString().equals("email"))
        );
    }

    /**
     * Proverava validaciju formata email adrese.
     */
    @Test
    void testEmailFormat() {

        korisnik.setEmail("nijeEmail");

        Set<ConstraintViolation<Korisnik>> violations =
                validator.validate(korisnik);

        assertTrue(
                violations.stream()
                        .anyMatch(v -> v.getPropertyPath().toString().equals("email"))
        );
    }

    /**
     * Proverava validaciju maksimalne dužine email adrese.
     */
    @Test
    void testEmailMaxSize() {

        korisnik.setEmail("a".repeat(121) + "@gmail.com");

        Set<ConstraintViolation<Korisnik>> violations =
                validator.validate(korisnik);

        assertTrue(
                violations.stream()
                        .anyMatch(v -> v.getPropertyPath().toString().equals("email"))
        );
    }

    /**
     * Proverava validaciju minimalne dužine lozinke.
     */
    @Test
    void testLozinkaMinSize() {

        korisnik.setLozinka("123");

        Set<ConstraintViolation<Korisnik>> violations =
                validator.validate(korisnik);

        assertTrue(
                violations.stream()
                        .anyMatch(v -> v.getPropertyPath().toString().equals("lozinka"))
        );
    }

    /**
     * Proverava validaciju maksimalne dužine lozinke.
     */
    @Test
    void testLozinkaMaxSize() {

        korisnik.setLozinka("a".repeat(101));

        Set<ConstraintViolation<Korisnik>> violations =
                validator.validate(korisnik);

        assertTrue(
                violations.stream()
                        .anyMatch(v -> v.getPropertyPath().toString().equals("lozinka"))
        );
    }

    /**
     * Proverava validaciju maksimalne dužine uloge korisnika.
     */
    @Test
    void testUlogaMaxSize() {

        korisnik.setUloga("a".repeat(41));

        Set<ConstraintViolation<Korisnik>> violations =
                validator.validate(korisnik);

        assertTrue(
                violations.stream()
                        .anyMatch(v -> v.getPropertyPath().toString().equals("uloga"))
        );
    }
}
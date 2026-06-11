package njt.mavenproject2.entity.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Set;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

class KorisnikTest {

    private Korisnik korisnik;
    private Validator validator;

    @BeforeEach
    void setUp() {
        korisnik = new Korisnik();
        ValidatorFactory factory =
                Validation.buildDefaultValidatorFactory();

        validator = factory.getValidator();
    }

    @AfterEach
    void tearDown() {
        korisnik = null;
    }

    @Test
    void testKorisnik() {
        assertNotNull(korisnik);
    }

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

    @Test
    void testSetId() {
        korisnik.setId(1L);
        assertEquals(1L, korisnik.getId());
    }

    @Test
    void testSetIme() {
        korisnik.setIme("Kristina");
        assertEquals("Kristina", korisnik.getIme());
    }

    @Test
    void testSetPrezime() {
        korisnik.setPrezime("Calic");
        assertEquals("Calic", korisnik.getPrezime());
    }

    @Test
    void testSetEmail() {
        korisnik.setEmail("test@gmail.com");
        assertEquals("test@gmail.com", korisnik.getEmail());
    }

    @Test
    void testSetLozinka() {
        korisnik.setLozinka("123456");
        assertEquals("123456", korisnik.getLozinka());
    }

    @Test
    void testSetUloga() {
        korisnik.setUloga("ADMIN");
        assertEquals("ADMIN", korisnik.getUloga());
    }

    @Test
    void testSetAdresa() {
        Adresa adresa = new Adresa();
        adresa.setId(1L);

        korisnik.setAdresa(adresa);

        assertEquals(adresa, korisnik.getAdresa());
    }

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

    @Test
    void testEqualsObject() {
        Korisnik k1 = new Korisnik();
        k1.setId(1L);

        Korisnik k2 = new Korisnik();
        k2.setId(1L);

        assertTrue(k1.equals(k2));
    }

    @Test
    void testEqualsObjectFalse() {
        Korisnik k1 = new Korisnik();
        k1.setId(1L);

        Korisnik k2 = new Korisnik();
        k2.setId(2L);

        assertFalse(k1.equals(k2));
    }

    @Test
    void testEqualsNull() {
        korisnik.setId(1L);
        assertFalse(korisnik.equals(null));
    }

    @Test
    void testEqualsDrugaKlasa() {
        korisnik.setId(1L);
        assertFalse(korisnik.equals("tekst"));
    }

    @Test
    void testHashCode() {
        Korisnik k1 = new Korisnik();
        k1.setId(1L);

        Korisnik k2 = new Korisnik();
        k2.setId(1L);

        assertEquals(k1.hashCode(), k2.hashCode());
    }

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
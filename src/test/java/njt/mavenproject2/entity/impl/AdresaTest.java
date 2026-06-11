package njt.mavenproject2.entity.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;
import java.util.Set;
import jakarta.validation.ConstraintViolation;
class AdresaTest {

    private Adresa adresa;
    private Validator validator;

    @BeforeEach
    void setUp() {
        adresa = new Adresa();

        ValidatorFactory factory =
                Validation.buildDefaultValidatorFactory();

        validator = factory.getValidator();
    }

    @AfterEach
    void tearDown() {
        adresa = null;
    }

    @Test
    void testAdresa() {
        assertNotNull(adresa);
    }

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

    @Test
    void testSetId() {
        adresa.setId(1L);
        assertEquals(1L, adresa.getId());
    }

    @Test
    void testSetUlica() {
        adresa.setUlica("Bulevar kralja Aleksandra");
        assertEquals("Bulevar kralja Aleksandra", adresa.getUlica());
    }

    @Test
    void testSetBroj() {
        adresa.setBroj("73");
        assertEquals("73", adresa.getBroj());
    }

    @Test
    void testSetGrad() {
        adresa.setGrad("Beograd");
        assertEquals("Beograd", adresa.getGrad());
    }

    @Test
    void testSetPostanskiBroj() {
        adresa.setPostanskiBroj("11000");
        assertEquals("11000", adresa.getPostanskiBroj());
    }

    @Test
    void testSetDrzava() {
        adresa.setDrzava("Srbija");
        assertEquals("Srbija", adresa.getDrzava());
    }

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

    @Test
    void testEqualsObject() {
        Adresa a1 = new Adresa();
        a1.setId(1L);

        Adresa a2 = new Adresa();
        a2.setId(1L);

        assertTrue(a1.equals(a2));
    }

    @Test
    void testEqualsObjectFalse() {
        Adresa a1 = new Adresa();
        a1.setId(1L);

        Adresa a2 = new Adresa();
        a2.setId(2L);

        assertFalse(a1.equals(a2));
    }

    @Test
    void testEqualsNull() {
        adresa.setId(1L);
        assertFalse(adresa.equals(null));
    }

    @Test
    void testEqualsDrugaKlasa() {
        adresa.setId(1L);
        assertFalse(adresa.equals("tekst"));
    }

    @Test
    void testHashCode() {
        Adresa a1 = new Adresa();
        a1.setId(1L);

        Adresa a2 = new Adresa();
        a2.setId(1L);

        assertEquals(a1.hashCode(), a2.hashCode());
    }

    @Test
    void testToString() {
        adresa = new Adresa(1L, "Nemanjina", "12A", "Beograd", "11000", "Srbija");

        String s = adresa.toString();

        assertTrue(s.contains("1"));
        assertTrue(s.contains("Nemanjina"));
        assertTrue(s.contains("12A"));
        assertTrue(s.contains("Beograd"));
    }
    
    @Test
    void testUlicaNotBlank() {
        adresa.setUlica("");

        Set<ConstraintViolation<Adresa>> violations = validator.validate(adresa);

        assertTrue(
                violations.stream()
                        .anyMatch(v -> v.getPropertyPath().toString().equals("ulica"))
        );
    }

    @Test
    void testUlicaMaxSize() {
        adresa.setUlica("a".repeat(101));

        Set<ConstraintViolation<Adresa>> violations = validator.validate(adresa);

        assertTrue(
                violations.stream()
                        .anyMatch(v -> v.getPropertyPath().toString().equals("ulica"))
        );
    }

    @Test
    void testBrojNotBlank() {
        adresa.setBroj("");

        Set<ConstraintViolation<Adresa>> violations = validator.validate(adresa);

        assertTrue(
                violations.stream()
                        .anyMatch(v -> v.getPropertyPath().toString().equals("broj"))
        );
    }

    @Test
    void testBrojMaxSize() {
        adresa.setBroj("a".repeat(21));

        Set<ConstraintViolation<Adresa>> violations = validator.validate(adresa);

        assertTrue(
                violations.stream()
                        .anyMatch(v -> v.getPropertyPath().toString().equals("broj"))
        );
    }

    @Test
    void testGradNotBlank() {
        adresa.setGrad("");

        Set<ConstraintViolation<Adresa>> violations = validator.validate(adresa);

        assertTrue(
                violations.stream()
                        .anyMatch(v -> v.getPropertyPath().toString().equals("grad"))
        );
    }

    @Test
    void testGradMaxSize() {
        adresa.setGrad("a".repeat(61));

        Set<ConstraintViolation<Adresa>> violations = validator.validate(adresa);

        assertTrue(
                violations.stream()
                        .anyMatch(v -> v.getPropertyPath().toString().equals("grad"))
        );
    }

    @Test
    void testPostanskiBrojNotBlank() {
        adresa.setPostanskiBroj("");

        Set<ConstraintViolation<Adresa>> violations = validator.validate(adresa);

        assertTrue(
                violations.stream()
                        .anyMatch(v -> v.getPropertyPath().toString().equals("postanskiBroj"))
        );
    }

    @Test
    void testPostanskiBrojMaxSize() {
        adresa.setPostanskiBroj("a".repeat(21));

        Set<ConstraintViolation<Adresa>> violations = validator.validate(adresa);

        assertTrue(
                violations.stream()
                        .anyMatch(v -> v.getPropertyPath().toString().equals("postanskiBroj"))
        );
    }

    @Test
    void testDrzavaNotBlank() {
        adresa.setDrzava("");

        Set<ConstraintViolation<Adresa>> violations = validator.validate(adresa);

        assertTrue(
                violations.stream()
                        .anyMatch(v -> v.getPropertyPath().toString().equals("drzava"))
        );
    }

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
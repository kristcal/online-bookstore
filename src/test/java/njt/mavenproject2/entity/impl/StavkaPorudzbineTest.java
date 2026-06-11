package njt.mavenproject2.entity.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import java.util.Set;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

class StavkaPorudzbineTest {

    private StavkaPorudzbine stavka;
    private Validator validator;

    @BeforeEach
    void setUp() {
        stavka = new StavkaPorudzbine();
        ValidatorFactory factory =
                Validation.buildDefaultValidatorFactory();

        validator = factory.getValidator();
    }

    @AfterEach
    void tearDown() {
        stavka = null;
    }

    @Test
    void testStavkaPorudzbine() {
        assertNotNull(stavka);
    }

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

    @Test
    void testSetId() {
        stavka.setId(1L);
        assertEquals(1L, stavka.getId());
    }

    @Test
    void testSetRb() {
        stavka.setRb(2);
        assertEquals(2, stavka.getRb());
    }

    @Test
    void testSetKolicina() {
        stavka.setKolicina(5);
        assertEquals(5, stavka.getKolicina());
    }

    @Test
    void testSetCenaK() {
        stavka.setCenaK(1200.0);
        assertEquals(1200.0, stavka.getCenaK());
    }

    @Test
    void testSetUkupanIznosStavke() {
        stavka.setUkupanIznosStavke(6000.0);
        assertEquals(6000.0, stavka.getUkupanIznosStavke());
    }

    @Test
    void testSetPorudzbina() {
        Porudzbina p = new Porudzbina();

        stavka.setPorudzbina(p);

        assertEquals(p, stavka.getPorudzbina());
    }

    @Test
    void testSetKnjiga() {
        Knjiga k = new Knjiga();

        stavka.setKnjiga(k);

        assertEquals(k, stavka.getKnjiga());
    }

    @Test
    void testEqualsObject() {
        StavkaPorudzbine s1 = new StavkaPorudzbine();
        s1.setId(1L);

        StavkaPorudzbine s2 = new StavkaPorudzbine();
        s2.setId(1L);

        assertTrue(s1.equals(s2));
    }

    @Test
    void testEqualsObjectFalse() {
        StavkaPorudzbine s1 = new StavkaPorudzbine();
        s1.setId(1L);

        StavkaPorudzbine s2 = new StavkaPorudzbine();
        s2.setId(2L);

        assertFalse(s1.equals(s2));
    }
    @Test
    void testEqualsSameObject() {

        StavkaPorudzbine s = new StavkaPorudzbine();

        assertTrue(s.equals(s));
    }
    @Test
    void testEqualsDifferentClass() {

        StavkaPorudzbine s = new StavkaPorudzbine();

        assertFalse(s.equals("tekst"));
    }
    @Test
    void testEqualsNull() {

        StavkaPorudzbine s = new StavkaPorudzbine();

        assertFalse(s.equals(null));
    }

    @Test
    void testHashCode() {
        StavkaPorudzbine s1 = new StavkaPorudzbine();
        s1.setId(1L);

        StavkaPorudzbine s2 = new StavkaPorudzbine();
        s2.setId(1L);

        assertEquals(s1.hashCode(), s2.hashCode());
    }

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
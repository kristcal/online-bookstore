package njt.mavenproject2.entity.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.*;
import java.util.Set;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

class PorudzbinaTest {

    private Porudzbina porudzbina;
    private Validator validator;

    @BeforeEach
    void setUp() {
        porudzbina = new Porudzbina();
        ValidatorFactory factory =
                Validation.buildDefaultValidatorFactory();

        validator = factory.getValidator();
    }

    @AfterEach
    void tearDown() {
        porudzbina = null;
    }

    @Test
    void testPorudzbina() {
        assertNotNull(porudzbina);
    }

    @Test
    void testSetId() {
        porudzbina.setId(1L);
        assertEquals(1L, porudzbina.getId());
    }

    @Test
    void testSetDatum() {
        LocalDateTime datum = LocalDateTime.now();

        porudzbina.setDatum(datum);

        assertEquals(datum, porudzbina.getDatum());
    }

    @Test
    void testSetUkupanIznos() {
        porudzbina.setUkupanIznos(5000.0);

        assertEquals(5000.0, porudzbina.getUkupanIznos());
    }

    @Test
    void testSetStatus() {
        porudzbina.setStatus("PLACENA");

        assertEquals("PLACENA", porudzbina.getStatus());
    }

    @Test
    void testSetKorisnik() {
        Korisnik k = new Korisnik();

        porudzbina.setKorisnik(k);

        assertEquals(k, porudzbina.getKorisnik());
    }

    @Test
    void testSetPlacanje() {
        Placanje p = new Placanje();

        porudzbina.setPlacanje(p);

        assertEquals(p, porudzbina.getPlacanje());
    }

    @Test
    void testSetStavke() {
        StavkaPorudzbine s = new StavkaPorudzbine();

        List<StavkaPorudzbine> stavke = new ArrayList<>();
        stavke.add(s);

        porudzbina.setStavke(stavke);

        assertEquals(1, porudzbina.getStavke().size());
    }

    @Test
    void testEqualsObject() {
        Porudzbina p1 = new Porudzbina();
        p1.setId(1L);

        Porudzbina p2 = new Porudzbina();
        p2.setId(1L);

        assertTrue(p1.equals(p2));
    }

    @Test
    void testEqualsObjectFalse() {
        Porudzbina p1 = new Porudzbina();
        p1.setId(1L);

        Porudzbina p2 = new Porudzbina();
        p2.setId(2L);

        assertFalse(p1.equals(p2));
    }

    @Test
    void testHashCode() {
        Porudzbina p1 = new Porudzbina();
        p1.setId(1L);

        Porudzbina p2 = new Porudzbina();
        p2.setId(1L);

        assertEquals(p1.hashCode(), p2.hashCode());
    }

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
    
    @Test
    void testPorudzbinaLongLocalDateTimeDoubleKorisnik() {

        Korisnik k = new Korisnik();
        k.setId(1L);

        LocalDateTime datum =
                LocalDateTime.of(2026, 6, 11, 18, 0);

        Porudzbina p =
                new Porudzbina(1L, datum, 2500.0, k);

        assertEquals(1L, p.getId());
        assertEquals(datum, p.getDatum());
        assertEquals(2500.0, p.getUkupanIznos());
        assertEquals(k, p.getKorisnik());
        assertEquals("KREIRANA", p.getStatus());
    }
}
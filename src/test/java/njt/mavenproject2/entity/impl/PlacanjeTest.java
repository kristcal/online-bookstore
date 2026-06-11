package njt.mavenproject2.entity.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Set;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
class PlacanjeTest {

    private Placanje placanje;
    private Validator validator;
    
    @BeforeEach
    void setUp() {
        placanje = new Placanje();
        ValidatorFactory factory =
                Validation.buildDefaultValidatorFactory();

        validator = factory.getValidator();
    }

    @AfterEach
    void tearDown() {
        placanje = null;
    }

    @Test
    void testPlacanje() {
        assertNotNull(placanje);
    }

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

    @Test
    void testSetId() {
        placanje.setId(1L);
        assertEquals(1L, placanje.getId());
    }

    @Test
    void testSetIznos() {
        placanje.setIznos(3000.0);
        assertEquals(3000.0, placanje.getIznos());
    }

    @Test
    void testSetNacinPlacanja() {
        placanje.setNacinPlacanja("Gotovina");
        assertEquals("Gotovina", placanje.getNacinPlacanja());
    }

    @Test
    void testSetStatusPlacanja() {
        placanje.setStatusPlacanja("NA_CEKANJU");
        assertEquals("NA_CEKANJU", placanje.getStatusPlacanja());
    }

    @Test
    void testSetDatumPlacanja() {
        LocalDateTime datum = LocalDateTime.now();
        placanje.setDatumPlacanja(datum);
        assertEquals(datum, placanje.getDatumPlacanja());
    }

    @Test
    void testSetPozivNaBroj() {
        placanje.setPozivNaBroj("2026-002");
        assertEquals("2026-002", placanje.getPozivNaBroj());
    }

    @Test
    void testSetPorudzbina() {
        Porudzbina porudzbina = new Porudzbina();
        porudzbina.setId(1L);

        placanje.setPorudzbina(porudzbina);

        assertEquals(porudzbina, placanje.getPorudzbina());
    }

    @Test
    void testEqualsObject() {
        Placanje p1 = new Placanje();
        p1.setId(1L);

        Placanje p2 = new Placanje();
        p2.setId(1L);

        assertTrue(p1.equals(p2));
    }

    @Test
    void testEqualsObjectFalse() {
        Placanje p1 = new Placanje();
        p1.setId(1L);

        Placanje p2 = new Placanje();
        p2.setId(2L);

        assertFalse(p1.equals(p2));
    }

    @Test
    void testEqualsNull() {
        placanje.setId(1L);
        assertFalse(placanje.equals(null));
    }

    @Test
    void testEqualsDrugaKlasa() {
        placanje.setId(1L);
        assertFalse(placanje.equals("tekst"));
    }

    @Test
    void testHashCode() {
        Placanje p1 = new Placanje();
        p1.setId(1L);

        Placanje p2 = new Placanje();
        p2.setId(1L);

        assertEquals(p1.hashCode(), p2.hashCode());
    }

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
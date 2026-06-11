package njt.mavenproject2.entity.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.*;
import java.util.Set;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

class ZanrTest {

    private Zanr zanr;
    private Validator validator;

    @BeforeEach
    void setUp() {
        zanr = new Zanr();
        ValidatorFactory factory =
                Validation.buildDefaultValidatorFactory();

        validator = factory.getValidator();
    }

    @AfterEach
    void tearDown() {
        zanr = null;
    }

    @Test
    void testZanr() {
        assertNotNull(zanr);
    }

    @Test
    void testZanrLongString() {
        zanr = new Zanr(1L, "Roman");

        assertEquals(1L, zanr.getId());
        assertEquals("Roman", zanr.getNaziv());
    }

    @Test
    void testSetId() {
        zanr.setId(1L);
        assertEquals(1L, zanr.getId());
    }

    @Test
    void testSetNaziv() {
        zanr.setNaziv("Drama");
        assertEquals("Drama", zanr.getNaziv());
    }

    @Test
    void testSetKnjige() {
        Knjiga k = new Knjiga();

        List<Knjiga> knjige = new ArrayList<>();
        knjige.add(k);

        zanr.setKnjige(knjige);

        assertEquals(1, zanr.getKnjige().size());
    }

    @Test
    void testEqualsObject() {
        Zanr z1 = new Zanr();
        z1.setId(1L);

        Zanr z2 = new Zanr();
        z2.setId(1L);

        assertTrue(z1.equals(z2));
    }

    @Test
    void testEqualsObjectFalse() {
        Zanr z1 = new Zanr();
        z1.setId(1L);

        Zanr z2 = new Zanr();
        z2.setId(2L);

        assertFalse(z1.equals(z2));
    }

    @Test
    void testHashCode() {
        Zanr z1 = new Zanr();
        z1.setId(1L);

        Zanr z2 = new Zanr();
        z2.setId(1L);

        assertEquals(z1.hashCode(), z2.hashCode());
    }

    @Test
    void testToString() {
        zanr.setId(1L);
        zanr.setNaziv("Roman");

        String s = zanr.toString();

        assertTrue(s.contains("1"));
        assertTrue(s.contains("Roman"));
    }
    
    @Test
    void testNazivNotBlank() {

        zanr.setNaziv("");

        Set<ConstraintViolation<Zanr>> violations =
                validator.validate(zanr);

        assertTrue(
                violations.stream()
                        .anyMatch(v -> v.getPropertyPath().toString().equals("naziv"))
        );
    }

    @Test
    void testNazivMaxSize() {

        zanr.setNaziv("a".repeat(51));

        Set<ConstraintViolation<Zanr>> violations =
                validator.validate(zanr);

        assertTrue(
                violations.stream()
                        .anyMatch(v -> v.getPropertyPath().toString().equals("naziv"))
        );
    }
}
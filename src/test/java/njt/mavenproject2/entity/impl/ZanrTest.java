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
 * Test klasa za proveru funkcionalnosti entiteta {@link Zanr}.
 *
 * Testira konstruktore, gettere, settere, metode equals, hashCode,
 * toString i validaciona ograničenja definisana nad atributima klase.
 *
 * @author Korisnik
 */
class ZanrTest {

    private Zanr zanr;
    private Validator validator;

    /**
     * Inicijalizuje objekat klase Zanr i validator pre svakog testa.
     */
    @BeforeEach
    void setUp() {
        zanr = new Zanr();
        ValidatorFactory factory =
                Validation.buildDefaultValidatorFactory();

        validator = factory.getValidator();
    }

    /**
     * Oslobađa referencu na objekat nakon svakog testa.
     */
    @AfterEach
    void tearDown() {
        zanr = null;
    }

    /**
     * Proverava kreiranje praznog objekta klase Zanr.
     */
    @Test
    void testZanr() {
        assertNotNull(zanr);
    }

    /**
     * Proverava konstruktor sa identifikatorom i nazivom žanra.
     */
    @Test
    void testZanrLongString() {
        zanr = new Zanr(1L, "Roman");

        assertEquals(1L, zanr.getId());
        assertEquals("Roman", zanr.getNaziv());
    }

    /**
     * Proverava postavljanje identifikatora žanra.
     */
    @Test
    void testSetId() {
        zanr.setId(1L);
        assertEquals(1L, zanr.getId());
    }

    /**
     * Proverava postavljanje naziva žanra.
     */
    @Test
    void testSetNaziv() {
        zanr.setNaziv("Drama");
        assertEquals("Drama", zanr.getNaziv());
    }

    /**
     * Proverava postavljanje liste knjiga koje pripadaju žanru.
     */
    @Test
    void testSetKnjige() {
        Knjiga k = new Knjiga();

        List<Knjiga> knjige = new ArrayList<>();
        knjige.add(k);

        zanr.setKnjige(knjige);

        assertEquals(1, zanr.getKnjige().size());
    }

    /**
     * Proverava da su dva žanra jednaka kada imaju isti identifikator.
     */
    @Test
    void testEqualsObject() {
        Zanr z1 = new Zanr();
        z1.setId(1L);

        Zanr z2 = new Zanr();
        z2.setId(1L);

        assertEquals(z1,z2);
    }

    /**
     * Proverava da dva žanra nisu jednaka kada imaju različite identifikatore.
     */
    @Test
    void testEqualsObjectFalse() {
        Zanr z1 = new Zanr();
        z1.setId(1L);

        Zanr z2 = new Zanr();
        z2.setId(2L);

        assertNotEquals(z1,z2);
    }

    /**
     * Proverava da dva zanra sa null identifikatorom nisu jednake.
     */
    @Test
    void testEqualsBothIdsNull() {

        Zanr a1 = new Zanr();
        Zanr a2 = new Zanr();

        assertNotEquals(a1, a2);
    }
    
    /**
     * Proverava da zanr nije jednak objektu druge klase.
     */
    @Test
    void testEqualsDifferentClass() {

        zanr.setId(1L);

        assertFalse(zanr.equals("tekst"));
    }

    /**
     * Proverava da zanr nije jednak null vrednosti.
     */
    @Test
    void testEqualsNull() {

        zanr.setId(1L);

        assertFalse(zanr.equals(null));
    }
    
    /**
     * Proverava hashCode za žanrove sa istim identifikatorom.
     */
    @Test
    void testHashCode() {
        Zanr z1 = new Zanr();
        z1.setId(1L);

        Zanr z2 = new Zanr();
        z2.setId(1L);

        assertEquals(z1.hashCode(), z2.hashCode());
    }

    /**
     * Proverava tekstualni prikaz žanra.
     */
    @Test
    void testToString() {
        zanr.setId(1L);
        zanr.setNaziv("Roman");

        String s = zanr.toString();

        assertTrue(s.contains("1"));
        assertTrue(s.contains("Roman"));
    }

    /**
     * Proverava validaciju kada je naziv žanra prazan.
     */
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

    /**
     * Proverava validaciju maksimalne dužine naziva žanra.
     */
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
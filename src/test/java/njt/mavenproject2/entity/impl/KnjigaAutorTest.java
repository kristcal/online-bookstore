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
 * Test klasa za proveru funkcionalnosti entiteta {@link KnjigaAutor}.
 *
 * Testira konstruktore, gettere, settere, metode equals, hashCode,
 * toString i validaciona ograničenja definisana nad atributima klase.
 *
 * @author Korisnik
 */
class KnjigaAutorTest {

    private KnjigaAutor knjigaAutor;
    private Validator validator;

    /**
     * Inicijalizuje objekat klase KnjigaAutor i validator pre svakog testa.
     */
    @BeforeEach
    void setUp() {
        knjigaAutor = new KnjigaAutor();
        ValidatorFactory factory =
                Validation.buildDefaultValidatorFactory();

        validator = factory.getValidator();
    }

    /**
     * Oslobađa referencu na objekat nakon svakog testa.
     */
    @AfterEach
    void tearDown() {
        knjigaAutor = null;
    }

    /**
     * Proverava kreiranje praznog objekta klase KnjigaAutor.
     */
    @Test
    void testKnjigaAutor() {
        assertNotNull(knjigaAutor);
    }

    /**
     * Proverava konstruktor sa knjigom, autorom i ulogom.
     */
    @Test
    void testKnjigaAutorKnjigaAutorString() {
        Knjiga knjiga = new Knjiga();
        Autor autor = new Autor();

        knjigaAutor = new KnjigaAutor(knjiga, autor, "Pisac");

        assertEquals(knjiga, knjigaAutor.getKnjiga());
        assertEquals(autor, knjigaAutor.getAutor());
        assertEquals("Pisac", knjigaAutor.getUloga());
    }

    /**
     * Proverava postavljanje knjige.
     */
    @Test
    void testSetKnjiga() {
        Knjiga knjiga = new Knjiga();

        knjigaAutor.setKnjiga(knjiga);

        assertEquals(knjiga, knjigaAutor.getKnjiga());
    }

    /**
     * Proverava postavljanje autora.
     */
    @Test
    void testSetAutor() {
        Autor autor = new Autor();

        knjigaAutor.setAutor(autor);

        assertEquals(autor, knjigaAutor.getAutor());
    }

    /**
     * Proverava postavljanje uloge autora za knjigu.
     */
    @Test
    void testSetUloga() {
        knjigaAutor.setUloga("Prevodilac");

        assertEquals("Prevodilac", knjigaAutor.getUloga());
    }

    /**
     * Proverava da su dve veze knjiga-autor jednake kada imaju isti identifikator.
     */
    @Test
    void testEqualsObject() {
        KnjigaAutor ka1 = new KnjigaAutor();
        ka1.setId(1L);

        KnjigaAutor ka2 = new KnjigaAutor();
        ka2.setId(1L);

        assertEquals(ka1,ka2);
    }

    /**
     * Proverava da dve veze knjiga-autor nisu jednake kada imaju različite identifikatore.
     */
    @Test
    void testEqualsObjectFalse() {
        KnjigaAutor ka1 = new KnjigaAutor();
        ka1.setId(1L);

        KnjigaAutor ka2 = new KnjigaAutor();
        ka2.setId(2L);

        assertNotEquals(ka1,ka2);
    }

    /**
     * Proverava da veza knjiga-autor nije jednaka null vrednosti.
     */
    @Test
    void testEqualsNull() {
        knjigaAutor.setId(1L);

        assertFalse(knjigaAutor.equals(null));
    }

    /**
     * Proverava da veza knjiga-autor nije jednaka objektu druge klase.
     */
    @Test
    void testEqualsDrugaKlasa() {
        knjigaAutor.setId(1L);

        assertFalse(knjigaAutor.equals("tekst"));
    }

    /**
     * Proverava da dva objekta knjigaAutor sa null identifikatorom nisu jednake.
     */
    @Test
    void testEqualsBothIdsNull() {

        KnjigaAutor ka1 = new KnjigaAutor();
        KnjigaAutor ka2 = new KnjigaAutor();

        assertNotEquals(ka1, ka2);
    }
    /**
     * Proverava hashCode za veze knjiga-autor sa istim identifikatorom.
     */
    @Test
    void testHashCode() {
        KnjigaAutor ka1 = new KnjigaAutor();
        ka1.setId(1L);

        KnjigaAutor ka2 = new KnjigaAutor();
        ka2.setId(1L);

        assertEquals(ka1.hashCode(), ka2.hashCode());
    }

    /**
     * Proverava tekstualni prikaz veze knjiga-autor.
     */
    @Test
    void testToString() {
        knjigaAutor.setId(1L);
        knjigaAutor.setUloga("Pisac");

        String s = knjigaAutor.toString();

        assertTrue(s.contains("1"));
        assertTrue(s.contains("Pisac"));
    }

    /**
     * Proverava validaciju kada je uloga prazna.
     */
    @Test
    void testUlogaNotBlank() {

        knjigaAutor.setUloga("");

        Set<ConstraintViolation<KnjigaAutor>> violations =
                validator.validate(knjigaAutor);

        assertTrue(
                violations.stream()
                        .anyMatch(v -> v.getPropertyPath().toString().equals("uloga"))
        );
    }

    /**
     * Proverava validaciju maksimalne dužine uloge.
     */
    @Test
    void testUlogaMaxSize() {

        knjigaAutor.setUloga("a".repeat(51));

        Set<ConstraintViolation<KnjigaAutor>> violations =
                validator.validate(knjigaAutor);

        assertTrue(
                violations.stream()
                        .anyMatch(v -> v.getPropertyPath().toString().equals("uloga"))
        );
    }
}
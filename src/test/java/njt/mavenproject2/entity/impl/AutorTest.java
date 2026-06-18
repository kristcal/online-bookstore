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
 * Test klasa za proveru funkcionalnosti entiteta {@link Autor}.
 *
 * Testira konstruktore, gettere, settere, metode equals, hashCode,
 * toString i validaciona ograničenja definisana nad atributima klase.
 *
 * @author Korisnik
 */
class AutorTest {

    private Autor autor;
    private Validator validator;

    /**
     * Inicijalizuje objekat klase Autor i validator pre svakog testa.
     */
    @BeforeEach
    void setUp() {
        autor = new Autor();
        ValidatorFactory factory =
                Validation.buildDefaultValidatorFactory();

        validator = factory.getValidator();
    }

    /**
     * Oslobađa referencu na objekat nakon svakog testa.
     */
    @AfterEach
    void tearDown() {
        autor = null;
    }

    /**
     * Proverava kreiranje praznog objekta klase Autor.
     */
    @Test
    void testAutor() {
        assertNotNull(autor);
    }

    /**
     * Proverava konstruktor sa svim parametrima.
     */
    @Test
    void testAutorLongStringString() {
        autor = new Autor(1L, "Ivo", "Andric");

        assertEquals(1L, autor.getId());
        assertEquals("Ivo", autor.getIme());
        assertEquals("Andric", autor.getPrezime());
    }

    /**
     * Proverava postavljanje identifikatora autora.
     */
    @Test
    void testSetId() {
        autor.setId(1L);
        assertEquals(1L, autor.getId());
    }

    /**
     * Proverava postavljanje imena autora.
     */
    @Test
    void testSetIme() {
        autor.setIme("Mesa");
        assertEquals("Mesa", autor.getIme());
    }

    /**
     * Proverava postavljanje prezimena autora.
     */
    @Test
    void testSetPrezime() {
        autor.setPrezime("Selimovic");
        assertEquals("Selimovic", autor.getPrezime());
    }

    /**
     * Proverava postavljanje liste knjiga povezanih sa autorom.
     */
    @Test
    void testSetKnjige() {
        KnjigaAutor ka = new KnjigaAutor();

        List<KnjigaAutor> knjige = new ArrayList<>();
        knjige.add(ka);

        autor.setKnjige(knjige);

        assertEquals(1, autor.getKnjige().size());
        assertTrue(autor.getKnjige().contains(ka));
    }

    /**
     * Proverava da su dva autora jednaka kada imaju isti identifikator.
     */
    @Test
    void testEqualsObject() {
        Autor a1 = new Autor();
        a1.setId(1L);

        Autor a2 = new Autor();
        a2.setId(1L);

        assertTrue(a1.equals(a2));
    }

    /**
     * Proverava da dva autora nisu jednaka kada imaju različite identifikatore.
     */
    @Test
    void testEqualsObjectFalse() {
        Autor a1 = new Autor();
        a1.setId(1L);

        Autor a2 = new Autor();
        a2.setId(2L);

        assertFalse(a1.equals(a2));
    }

    /**
     * Proverava da autor nije jednak null vrednosti.
     */
    @Test
    void testEqualsNull() {
        autor.setId(1L);
        assertFalse(autor.equals(null));
    }

    /**
     * Proverava da autor nije jednak objektu druge klase.
     */
    @Test
    void testEqualsDrugaKlasa() {
        autor.setId(1L);
        assertFalse(autor.equals("tekst"));
    }

    /**
     * Proverava hashCode za autore sa istim identifikatorom.
     */
    @Test
    void testHashCode() {
        Autor a1 = new Autor();
        a1.setId(1L);

        Autor a2 = new Autor();
        a2.setId(1L);

        assertEquals(a1.hashCode(), a2.hashCode());
    }

    /**
     * Proverava tekstualni prikaz autora.
     */
    @Test
    void testToString() {
        autor = new Autor(1L, "Ivo", "Andric");

        String s = autor.toString();

        assertTrue(s.contains("1"));
        assertTrue(s.contains("Ivo"));
        assertTrue(s.contains("Andric"));
    }

    /**
     * Proverava validaciju kada je ime prazno.
     */
    @Test
    void testImeNotBlank() {

        autor.setIme("");

        Set<ConstraintViolation<Autor>> violations =
                validator.validate(autor);

        assertTrue(
                violations.stream()
                        .anyMatch(v -> v.getPropertyPath().toString().equals("ime"))
        );
    }

    /**
     * Proverava validaciju maksimalne dužine imena.
     */
    @Test
    void testImeMaxSize() {

        autor.setIme("a".repeat(61));

        Set<ConstraintViolation<Autor>> violations =
                validator.validate(autor);

        assertTrue(
                violations.stream()
                        .anyMatch(v -> v.getPropertyPath().toString().equals("ime"))
        );
    }

    /**
     * Proverava validaciju kada je prezime prazno.
     */
    @Test
    void testPrezimeNotBlank() {

        autor.setPrezime("");

        Set<ConstraintViolation<Autor>> violations =
                validator.validate(autor);

        assertTrue(
                violations.stream()
                        .anyMatch(v -> v.getPropertyPath().toString().equals("prezime"))
        );
    }

    /**
     * Proverava validaciju maksimalne dužine prezimena.
     */
    @Test
    void testPrezimeMaxSize() {

        autor.setPrezime("a".repeat(61));

        Set<ConstraintViolation<Autor>> violations =
                validator.validate(autor);

        assertTrue(
                violations.stream()
                        .anyMatch(v -> v.getPropertyPath().toString().equals("prezime"))
        );
    }
}
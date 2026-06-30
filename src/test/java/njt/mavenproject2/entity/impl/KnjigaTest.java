package njt.mavenproject2.entity.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
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
import java.time.Month;

/**
 * Test klasa za proveru funkcionalnosti entiteta {@link Knjiga}.
 *
 * Testira konstruktore, gettere, settere, metode equals, hashCode,
 * toString i validaciona ograničenja definisana nad atributima klase.
 *
 * @author Korisnik
 */
class KnjigaTest {

    private Knjiga knjiga;
    private Validator validator;

    /**
     * Inicijalizuje objekat klase Knjiga i validator pre svakog testa.
     */
    @BeforeEach
    void setUp() {
        knjiga = new Knjiga();
        ValidatorFactory factory =
                Validation.buildDefaultValidatorFactory();

        validator = factory.getValidator();
    }

    /**
     * Oslobađa referencu na objekat nakon svakog testa.
     */
    @AfterEach
    void tearDown() {
        knjiga = null;
    }

    /**
     * Proverava kreiranje praznog objekta klase Knjiga.
     */
    @Test
    void testKnjiga() {
        assertNotNull(knjiga);
    }

    /**
     * Proverava konstruktor sa svim parametrima.
     */
    @Test
    void testKnjigaLongStringStringDoubleStringLocalDateStringZanr() {
        Zanr zanr = new Zanr(1L, "Roman");
        LocalDate datum = LocalDate.of(2020, Month.JANUARY, 1);

        knjiga = new Knjiga(1L, "Na Drini cuprija", "Opis knjige", 1200.0,
                "123456789", datum, "slika.jpg", zanr);

        assertEquals(1L, knjiga.getId());
        assertEquals("Na Drini cuprija", knjiga.getNaziv());
        assertEquals("Opis knjige", knjiga.getOpis());
        assertEquals(1200.0, knjiga.getCena());
        assertEquals("123456789", knjiga.getIsbn());
        assertEquals(datum, knjiga.getGodinaIzdanja());
        assertEquals("slika.jpg", knjiga.getImageUrl());
        assertEquals(zanr, knjiga.getZanr());
    }

    /**
     * Proverava postavljanje identifikatora knjige.
     */
    @Test
    void testSetId() {
        knjiga.setId(1L);
        assertEquals(1L, knjiga.getId());
    }

    /**
     * Proverava postavljanje naziva knjige.
     */
    @Test
    void testSetNaziv() {
        knjiga.setNaziv("1984");
        assertEquals("1984", knjiga.getNaziv());
    }

    /**
     * Proverava postavljanje opisa knjige.
     */
    @Test
    void testSetOpis() {
        knjiga.setOpis("Distopijski roman");
        assertEquals("Distopijski roman", knjiga.getOpis());
    }

    /**
     * Proverava postavljanje cene knjige.
     */
    @Test
    void testSetCena() {
        knjiga.setCena(999.99);
        assertEquals(999.99, knjiga.getCena());
    }

    /**
     * Proverava postavljanje ISBN broja knjige.
     */
    @Test
    void testSetIsbn() {
        knjiga.setIsbn("978-86-123456-0-1");
        assertEquals("978-86-123456-0-1", knjiga.getIsbn());
    }

    /**
     * Proverava postavljanje godine izdanja knjige.
     */
    @Test
    void testSetGodinaIzdanja() {
        LocalDate datum = LocalDate.of(2021, Month.MAY, 10);
        knjiga.setGodinaIzdanja(datum);
        assertEquals(datum, knjiga.getGodinaIzdanja());
    }

    /**
     * Proverava postavljanje URL adrese slike knjige.
     */
    @Test
    void testSetImageUrl() {
        knjiga.setImageUrl("cover.png");
        assertEquals("cover.png", knjiga.getImageUrl());
    }

    /**
     * Proverava postavljanje liste autora povezanih sa knjigom.
     */
    @Test
    void testSetAutori() {
        KnjigaAutor ka = new KnjigaAutor();

        List<KnjigaAutor> autori = new ArrayList<>();
        autori.add(ka);

        knjiga.setAutori(autori);

        assertEquals(1, knjiga.getAutori().size());
        assertTrue(knjiga.getAutori().contains(ka));
    }

    /**
     * Proverava postavljanje žanra knjige.
     */
    @Test
    void testSetZanr() {
        Zanr zanr = new Zanr(1L, "Naucna fantastika");

        knjiga.setZanr(zanr);

        assertEquals(zanr, knjiga.getZanr());
    }

    /**
     * Proverava postavljanje dostupnosti knjige u knjižarama.
     */
    @Test
    void testSetDostupnost() {
        KnjigaKnjizara kk = new KnjigaKnjizara();

        List<KnjigaKnjizara> dostupnost = new ArrayList<>();
        dostupnost.add(kk);

        knjiga.setDostupnost(dostupnost);

        assertEquals(1, knjiga.getDostupnost().size());
        assertTrue(knjiga.getDostupnost().contains(kk));
    }

    /**
     * Proverava da su dve knjige jednake kada imaju isti identifikator.
     */
    @Test
    void testEqualsObject() {
        Knjiga k1 = new Knjiga();
        k1.setId(1L);

        Knjiga k2 = new Knjiga();
        k2.setId(1L);

        assertEquals(k1,k2);
    }

    /**
     * Proverava da dve knjige nisu jednake kada imaju različite identifikatore.
     */
    @Test
    void testEqualsObjectFalse() {
        Knjiga k1 = new Knjiga();
        k1.setId(1L);

        Knjiga k2 = new Knjiga();
        k2.setId(2L);

        assertNotEquals(k1,k2);
    }

    /**
     * Proverava da knjiga nije jednaka null vrednosti.
     */
    @Test
    void testEqualsNull() {
        knjiga.setId(1L);
        assertNotEquals(null,knjiga);
    }

    /**
     * Proverava da knjiga nije jednaka objektu druge klase.
     */
    @Test
    void testEqualsDrugaKlasa() {
        knjiga.setId(1L);
        assertNotEquals("tekst",knjiga);
    }

    /**
     * Proverava hashCode za knjige sa istim identifikatorom.
     */
    @Test
    void testHashCode() {
        Knjiga k1 = new Knjiga();
        k1.setId(1L);

        Knjiga k2 = new Knjiga();
        k2.setId(1L);

        assertEquals(k1.hashCode(), k2.hashCode());
    }

    /**
     * Proverava tekstualni prikaz knjige.
     */
    @Test
    void testToString() {
        knjiga = new Knjiga();
        knjiga.setId(1L);
        knjiga.setNaziv("1984");
        knjiga.setIsbn("123456789");

        String s = knjiga.toString();

        assertTrue(s.contains("1"));
        assertTrue(s.contains("1984"));
        assertTrue(s.contains("123456789"));
    }

    /**
     * Proverava validaciju kada je naziv knjige prazan.
     */
    @Test
    void testNazivNotBlank() {

        knjiga.setNaziv("");

        Set<ConstraintViolation<Knjiga>> violations =
                validator.validate(knjiga);

        assertTrue(
                violations.stream()
                        .anyMatch(v -> v.getPropertyPath().toString().equals("naziv"))
        );
    }

    /**
     * Proverava validaciju maksimalne dužine naziva knjige.
     */
    @Test
    void testNazivMaxSize() {

        knjiga.setNaziv("a".repeat(201));

        Set<ConstraintViolation<Knjiga>> violations =
                validator.validate(knjiga);

        assertTrue(
                violations.stream()
                        .anyMatch(v -> v.getPropertyPath().toString().equals("naziv"))
        );
    }

    /**
     * Proverava validaciju maksimalne dužine opisa knjige.
     */
    @Test
    void testOpisMaxSize() {

        knjiga.setOpis("a".repeat(1001));

        Set<ConstraintViolation<Knjiga>> violations =
                validator.validate(knjiga);

        assertTrue(
                violations.stream()
                        .anyMatch(v -> v.getPropertyPath().toString().equals("opis"))
        );
    }

    /**
     * Proverava validaciju kada cena knjige nije postavljena.
     */
    @Test
    void testCenaNotNull() {

        knjiga.setCena(null);

        Set<ConstraintViolation<Knjiga>> violations =
                validator.validate(knjiga);

        assertTrue(
                violations.stream()
                        .anyMatch(v -> v.getPropertyPath().toString().equals("cena"))
        );
    }

    /**
     * Proverava validaciju kada cena knjige nije pozitivna.
     */
    @Test
    void testCenaPositive() {

        knjiga.setCena(-100.0);

        Set<ConstraintViolation<Knjiga>> violations =
                validator.validate(knjiga);

        assertTrue(
                violations.stream()
                        .anyMatch(v -> v.getPropertyPath().toString().equals("cena"))
        );
    }

    /**
     * Proverava validaciju kada je ISBN prazan.
     */
    @Test
    void testIsbnNotBlank() {

        knjiga.setIsbn("");

        Set<ConstraintViolation<Knjiga>> violations =
                validator.validate(knjiga);

        assertTrue(
                violations.stream()
                        .anyMatch(v -> v.getPropertyPath().toString().equals("isbn"))
        );
    }

    /**
     * Proverava validaciju maksimalne dužine ISBN broja.
     */
    @Test
    void testIsbnMaxSize() {

        knjiga.setIsbn("a".repeat(31));

        Set<ConstraintViolation<Knjiga>> violations =
                validator.validate(knjiga);

        assertTrue(
                violations.stream()
                        .anyMatch(v -> v.getPropertyPath().toString().equals("isbn"))
        );
    }

    /**
     * Proverava validaciju kada godina izdanja nije postavljena.
     */
    @Test
    void testGodinaIzdanjaNotNull() {

        knjiga.setGodinaIzdanja(null);

        Set<ConstraintViolation<Knjiga>> violations =
                validator.validate(knjiga);

        assertTrue(
                violations.stream()
                        .anyMatch(v -> v.getPropertyPath().toString().equals("godinaIzdanja"))
        );
    }

    /**
     * Proverava validaciju maksimalne dužine URL adrese slike.
     */
    @Test
    void testImageUrlMaxSize() {

        knjiga.setImageUrl("a".repeat(501));

        Set<ConstraintViolation<Knjiga>> violations =
                validator.validate(knjiga);

        assertTrue(
                violations.stream()
                        .anyMatch(v -> v.getPropertyPath().toString().equals("imageUrl"))
        );
    }
}
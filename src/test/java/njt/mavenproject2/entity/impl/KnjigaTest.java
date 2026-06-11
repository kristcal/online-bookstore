package njt.mavenproject2.entity.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
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

class KnjigaTest {

    private Knjiga knjiga;
    private Validator validator;

    @BeforeEach
    void setUp() {
        knjiga = new Knjiga();
        ValidatorFactory factory =
                Validation.buildDefaultValidatorFactory();

        validator = factory.getValidator();
    }

    @AfterEach
    void tearDown() {
        knjiga = null;
    }

    @Test
    void testKnjiga() {
        assertNotNull(knjiga);
    }

    @Test
    void testKnjigaLongStringStringDoubleStringLocalDateStringZanr() {
        Zanr zanr = new Zanr(1L, "Roman");
        LocalDate datum = LocalDate.of(2020, 1, 1);

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

    @Test
    void testSetId() {
        knjiga.setId(1L);
        assertEquals(1L, knjiga.getId());
    }

    @Test
    void testSetNaziv() {
        knjiga.setNaziv("1984");
        assertEquals("1984", knjiga.getNaziv());
    }

    @Test
    void testSetOpis() {
        knjiga.setOpis("Distopijski roman");
        assertEquals("Distopijski roman", knjiga.getOpis());
    }

    @Test
    void testSetCena() {
        knjiga.setCena(999.99);
        assertEquals(999.99, knjiga.getCena());
    }

    @Test
    void testSetIsbn() {
        knjiga.setIsbn("978-86-123456-0-1");
        assertEquals("978-86-123456-0-1", knjiga.getIsbn());
    }

    @Test
    void testSetGodinaIzdanja() {
        LocalDate datum = LocalDate.of(2021, 5, 10);
        knjiga.setGodinaIzdanja(datum);
        assertEquals(datum, knjiga.getGodinaIzdanja());
    }

    @Test
    void testSetImageUrl() {
        knjiga.setImageUrl("cover.png");
        assertEquals("cover.png", knjiga.getImageUrl());
    }

    @Test
    void testSetAutori() {
        KnjigaAutor ka = new KnjigaAutor();

        List<KnjigaAutor> autori = new ArrayList<>();
        autori.add(ka);

        knjiga.setAutori(autori);

        assertEquals(1, knjiga.getAutori().size());
        assertTrue(knjiga.getAutori().contains(ka));
    }

    @Test
    void testSetZanr() {
        Zanr zanr = new Zanr(1L, "Naucna fantastika");

        knjiga.setZanr(zanr);

        assertEquals(zanr, knjiga.getZanr());
    }

    @Test
    void testSetDostupnost() {
        KnjigaKnjizara kk = new KnjigaKnjizara();

        List<KnjigaKnjizara> dostupnost = new ArrayList<>();
        dostupnost.add(kk);

        knjiga.setDostupnost(dostupnost);

        assertEquals(1, knjiga.getDostupnost().size());
        assertTrue(knjiga.getDostupnost().contains(kk));
    }

    @Test
    void testEqualsObject() {
        Knjiga k1 = new Knjiga();
        k1.setId(1L);

        Knjiga k2 = new Knjiga();
        k2.setId(1L);

        assertTrue(k1.equals(k2));
    }

    @Test
    void testEqualsObjectFalse() {
        Knjiga k1 = new Knjiga();
        k1.setId(1L);

        Knjiga k2 = new Knjiga();
        k2.setId(2L);

        assertFalse(k1.equals(k2));
    }

    @Test
    void testEqualsNull() {
        knjiga.setId(1L);
        assertFalse(knjiga.equals(null));
    }

    @Test
    void testEqualsDrugaKlasa() {
        knjiga.setId(1L);
        assertFalse(knjiga.equals("tekst"));
    }

    @Test
    void testHashCode() {
        Knjiga k1 = new Knjiga();
        k1.setId(1L);

        Knjiga k2 = new Knjiga();
        k2.setId(1L);

        assertEquals(k1.hashCode(), k2.hashCode());
    }

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
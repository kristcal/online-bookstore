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
 * Test klasa za proveru funkcionalnosti entiteta {@link Knjizara}.
 *
 * Testira konstruktore, gettere, settere, metode equals, hashCode,
 * toString i validaciona ograničenja definisana nad atributima klase.
 *
 * @author Korisnik
 */
class KnjizaraTest {

    private Knjizara knjizara;
    private Validator validator;

    /**
     * Inicijalizuje objekat klase Knjizara i validator pre svakog testa.
     */
    @BeforeEach
    void setUp() {
        knjizara = new Knjizara();
        ValidatorFactory factory =
                Validation.buildDefaultValidatorFactory();

        validator = factory.getValidator();
    }

    /**
     * Oslobađa referencu na objekat nakon svakog testa.
     */
    @AfterEach
    void tearDown() {
        knjizara = null;
    }

    /**
     * Proverava kreiranje praznog objekta klase Knjizara.
     */
    @Test
    void testKnjizara() {
        assertNotNull(knjizara);
    }

    /**
     * Proverava konstruktor sa svim parametrima.
     */
    @Test
    void testKnjizaraLongStringStringString() {
        knjizara = new Knjizara(1L, "Laguna", "Beograd", "011123456");

        assertEquals(1L, knjizara.getId());
        assertEquals("Laguna", knjizara.getNaziv());
        assertEquals("Beograd", knjizara.getLokacija());
        assertEquals("011123456", knjizara.getKontakt());
    }

    /**
     * Proverava postavljanje identifikatora knjižare.
     */
    @Test
    void testSetId() {
        knjizara.setId(1L);
        assertEquals(1L, knjizara.getId());
    }

    /**
     * Proverava postavljanje naziva knjižare.
     */
    @Test
    void testSetNaziv() {
        knjizara.setNaziv("Vulkan");
        assertEquals("Vulkan", knjizara.getNaziv());
    }

    /**
     * Proverava postavljanje lokacije knjižare.
     */
    @Test
    void testSetLokacija() {
        knjizara.setLokacija("Novi Sad");
        assertEquals("Novi Sad", knjizara.getLokacija());
    }

    /**
     * Proverava postavljanje kontakta knjižare.
     */
    @Test
    void testSetKontakt() {
        knjizara.setKontakt("021123456");
        assertEquals("021123456", knjizara.getKontakt());
    }

    /**
     * Proverava postavljanje ponude knjižare.
     */
    @Test
    void testSetPonuda() {
        KnjigaKnjizara kk = new KnjigaKnjizara();

        List<KnjigaKnjizara> ponuda = new ArrayList<>();
        ponuda.add(kk);

        knjizara.setPonuda(ponuda);

        assertEquals(1, knjizara.getPonuda().size());
        assertTrue(knjizara.getPonuda().contains(kk));
    }

    /**
     * Proverava da su dve knjižare jednake kada imaju isti identifikator.
     */
    @Test
    void testEqualsObject() {
        Knjizara k1 = new Knjizara();
        k1.setId(1L);

        Knjizara k2 = new Knjizara();
        k2.setId(1L);

        assertEquals(k1,k2);
    }

    /**
     * Proverava da dve knjižare nisu jednake kada imaju različite identifikatore.
     */
    @Test
    void testEqualsObjectFalse() {
        Knjizara k1 = new Knjizara();
        k1.setId(1L);

        Knjizara k2 = new Knjizara();
        k2.setId(2L);

        assertNotEquals(k1,k2);
    }

    /**
     * Proverava da knjižara nije jednaka null vrednosti.
     */
    @Test
    void testEqualsNull() {
        knjizara.setId(1L);
        assertFalse(knjizara.equals(null));
    }

    /**
     * Proverava da knjižara nije jednaka objektu druge klase.
     */
    @Test
    void testEqualsDrugaKlasa() {
        knjizara.setId(1L);
        assertFalse(knjizara.equals("tekst"));
    }
    
    /**
     * Proverava da dve knjizare sa null identifikatorom nisu jednake.
     */
    @Test
    void testEqualsBothIdsNull() {

        Knjizara k1 = new Knjizara();
        Knjizara k2 = new Knjizara();

        assertNotEquals(k1, k2);
    }

    /**
     * Proverava hashCode za knjižare sa istim identifikatorom.
     */
    @Test
    void testHashCode() {
        Knjizara k1 = new Knjizara();
        k1.setId(1L);

        Knjizara k2 = new Knjizara();
        k2.setId(1L);

        assertEquals(k1.hashCode(), k2.hashCode());
    }

    /**
     * Proverava tekstualni prikaz knjižare.
     */
    @Test
    void testToString() {
        knjizara = new Knjizara(1L, "Laguna", "Beograd", "011123456");

        String s = knjizara.toString();

        assertTrue(s.contains("1"));
        assertTrue(s.contains("Laguna"));
        assertTrue(s.contains("Beograd"));
    }

    /**
     * Proverava validaciju kada je naziv knjižare prazan.
     */
    @Test
    void testNazivNotBlank() {

        knjizara.setNaziv("");

        Set<ConstraintViolation<Knjizara>> violations =
                validator.validate(knjizara);

        assertTrue(
                violations.stream()
                        .anyMatch(v -> v.getPropertyPath().toString().equals("naziv"))
        );
    }

    /**
     * Proverava validaciju maksimalne dužine naziva knjižare.
     */
    @Test
    void testNazivMaxSize() {

        knjizara.setNaziv("a".repeat(101));

        Set<ConstraintViolation<Knjizara>> violations =
                validator.validate(knjizara);

        assertTrue(
                violations.stream()
                        .anyMatch(v -> v.getPropertyPath().toString().equals("naziv"))
        );
    }

    /**
     * Proverava validaciju kada je lokacija knjižare prazna.
     */
    @Test
    void testLokacijaNotBlank() {

        knjizara.setLokacija("");

        Set<ConstraintViolation<Knjizara>> violations =
                validator.validate(knjizara);

        assertTrue(
                violations.stream()
                        .anyMatch(v -> v.getPropertyPath().toString().equals("lokacija"))
        );
    }

    /**
     * Proverava validaciju maksimalne dužine lokacije knjižare.
     */
    @Test
    void testLokacijaMaxSize() {

        knjizara.setLokacija("a".repeat(101));

        Set<ConstraintViolation<Knjizara>> violations =
                validator.validate(knjizara);

        assertTrue(
                violations.stream()
                        .anyMatch(v -> v.getPropertyPath().toString().equals("lokacija"))
        );
    }

    /**
     * Proverava validaciju kada je kontakt knjižare prazan.
     */
    @Test
    void testKontaktNotBlank() {

        knjizara.setKontakt("");

        Set<ConstraintViolation<Knjizara>> violations =
                validator.validate(knjizara);

        assertTrue(
                violations.stream()
                        .anyMatch(v -> v.getPropertyPath().toString().equals("kontakt"))
        );
    }

    /**
     * Proverava validaciju maksimalne dužine kontakta knjižare.
     */
    @Test
    void testKontaktMaxSize() {

        knjizara.setKontakt("a".repeat(51));

        Set<ConstraintViolation<Knjizara>> violations =
                validator.validate(knjizara);

        assertTrue(
                violations.stream()
                        .anyMatch(v -> v.getPropertyPath().toString().equals("kontakt"))
        );
    }
}
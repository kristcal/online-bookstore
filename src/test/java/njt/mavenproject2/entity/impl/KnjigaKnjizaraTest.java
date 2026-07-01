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
 * Test klasa za proveru funkcionalnosti entiteta {@link KnjigaKnjizara}.
 *
 * Testira konstruktore, gettere, settere, metode equals, hashCode,
 * toString i validaciona ograničenja definisana nad atributima klase.
 *
 * @author Korisnik
 */
class KnjigaKnjizaraTest {

    private KnjigaKnjizara knjigaKnjizara;
    private Validator validator;

    /**
     * Inicijalizuje objekat klase KnjigaKnjizara i validator pre svakog testa.
     */
    @BeforeEach
    void setUp() {
        knjigaKnjizara = new KnjigaKnjizara();
        ValidatorFactory factory =
                Validation.buildDefaultValidatorFactory();

        validator = factory.getValidator();
    }

    /**
     * Oslobađa referencu na objekat nakon svakog testa.
     */
    @AfterEach
    void tearDown() {
        knjigaKnjizara = null;
    }

    /**
     * Proverava kreiranje praznog objekta klase KnjigaKnjizara.
     */
    @Test
    void testKnjigaKnjizara() {
        assertNotNull(knjigaKnjizara);
    }

    /**
     * Proverava konstruktor sa knjigom, knjižarom i količinom.
     */
    @Test
    void testKnjigaKnjizaraKnjigaKnjizaraInteger() {
        Knjiga knjiga = new Knjiga();
        Knjizara knjizara = new Knjizara();

        knjigaKnjizara = new KnjigaKnjizara(knjiga, knjizara, 10);

        assertEquals(knjiga, knjigaKnjizara.getKnjiga());
        assertEquals(knjizara, knjigaKnjizara.getKnjizara());
        assertEquals(10, knjigaKnjizara.getKolicina());
    }

    /**
     * Proverava postavljanje identifikatora veze knjiga-knjižara.
     */
    @Test
    void testSetId() {
        knjigaKnjizara.setId(1L);
        assertEquals(1L, knjigaKnjizara.getId());
    }

    /**
     * Proverava postavljanje knjige.
     */
    @Test
    void testSetKnjiga() {
        Knjiga knjiga = new Knjiga();

        knjigaKnjizara.setKnjiga(knjiga);

        assertEquals(knjiga, knjigaKnjizara.getKnjiga());
    }

    /**
     * Proverava postavljanje knjižare.
     */
    @Test
    void testSetKnjizara() {
        Knjizara knjizara = new Knjizara();

        knjigaKnjizara.setKnjizara(knjizara);

        assertEquals(knjizara, knjigaKnjizara.getKnjizara());
    }

    /**
     * Proverava postavljanje količine knjige u knjižari.
     */
    @Test
    void testSetKolicina() {
        knjigaKnjizara.setKolicina(5);
        assertEquals(5, knjigaKnjizara.getKolicina());
    }

    /**
     * Proverava da su dve veze knjiga-knjižara jednake kada imaju isti identifikator.
     */
    @Test
    void testEqualsObject() {
        KnjigaKnjizara kk1 = new KnjigaKnjizara();
        kk1.setId(1L);

        KnjigaKnjizara kk2 = new KnjigaKnjizara();
        kk2.setId(1L);

        assertEquals(kk1, kk2);
    }

    /**
     * Proverava da dve veze knjiga-knjižara nisu jednake kada imaju različite identifikatore.
     */
    @Test
    void testEqualsObjectFalse() {
        KnjigaKnjizara kk1 = new KnjigaKnjizara();
        kk1.setId(1L);

        KnjigaKnjizara kk2 = new KnjigaKnjizara();
        kk2.setId(2L);

        assertNotEquals(kk1, kk2);
    }

    /**
     * Proverava da veza knjiga-knjižara nije jednaka null vrednosti.
     */
    @Test
    void testEqualsNull() {
        knjigaKnjizara.setId(1L);
        assertFalse(knjigaKnjizara.equals(null));
    }

    /**
     * Proverava da veza knjiga-knjižara nije jednaka objektu druge klase.
     */
    @Test
    void testEqualsDrugaKlasa() {
        knjigaKnjizara.setId(1L);
        assertFalse(knjigaKnjizara.equals("tekst"));
    }
    
    /**
     * Proverava da dva objekta knjigaKnjizara sa null identifikatorom nisu jednake.
     */
    @Test
    void testEqualsBothIdsNull() {

        KnjigaKnjizara kk1 = new KnjigaKnjizara();
        KnjigaKnjizara kk2 = new KnjigaKnjizara();

        assertNotEquals(kk1, kk2);
    }

    /**
     * Proverava hashCode za veze knjiga-knjižara sa istim identifikatorom.
     */
    @Test
    void testHashCode() {
        KnjigaKnjizara kk1 = new KnjigaKnjizara();
        kk1.setId(1L);

        KnjigaKnjizara kk2 = new KnjigaKnjizara();
        kk2.setId(1L);

        assertEquals(kk1.hashCode(), kk2.hashCode());
    }

    /**
     * Proverava tekstualni prikaz veze knjiga-knjižara.
     */
    @Test
    void testToString() {
        knjigaKnjizara.setId(1L);
        knjigaKnjizara.setKolicina(10);

        String s = knjigaKnjizara.toString();

        assertTrue(s.contains("1"));
        assertTrue(s.contains("10"));
    }

    /**
     * Proverava validaciju kada količina nije postavljena.
     */
    @Test
    void testKolicinaNotNull() {

        knjigaKnjizara.setKolicina(null);

        Set<ConstraintViolation<KnjigaKnjizara>> violations =
                validator.validate(knjigaKnjizara);

        assertTrue(
                violations.stream()
                        .anyMatch(v -> v.getPropertyPath().toString().equals("kolicina"))
        );
    }

    /**
     * Proverava validaciju kada je količina manja od nule.
     */
    @Test
    void testKolicinaPositiveOrZero() {

        knjigaKnjizara.setKolicina(-1);

        Set<ConstraintViolation<KnjigaKnjizara>> violations =
                validator.validate(knjigaKnjizara);

        assertTrue(
                violations.stream()
                        .anyMatch(v -> v.getPropertyPath().toString().equals("kolicina"))
        );
    }
}
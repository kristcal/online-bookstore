package njt.mavenproject2.entity.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Set;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

class KnjigaKnjizaraTest {

    private KnjigaKnjizara knjigaKnjizara;
    private Validator validator;

    @BeforeEach
    void setUp() {
        knjigaKnjizara = new KnjigaKnjizara();
        ValidatorFactory factory =
                Validation.buildDefaultValidatorFactory();

        validator = factory.getValidator();
    }

    @AfterEach
    void tearDown() {
        knjigaKnjizara = null;
    }

    @Test
    void testKnjigaKnjizara() {
        assertNotNull(knjigaKnjizara);
    }

    @Test
    void testKnjigaKnjizaraKnjigaKnjizaraInteger() {
        Knjiga knjiga = new Knjiga();
        Knjizara knjizara = new Knjizara();

        knjigaKnjizara = new KnjigaKnjizara(knjiga, knjizara, 10);

        assertEquals(knjiga, knjigaKnjizara.getKnjiga());
        assertEquals(knjizara, knjigaKnjizara.getKnjizara());
        assertEquals(10, knjigaKnjizara.getKolicina());
    }

    @Test
    void testSetId() {
        knjigaKnjizara.setId(1L);
        assertEquals(1L, knjigaKnjizara.getId());
    }

    @Test
    void testSetKnjiga() {
        Knjiga knjiga = new Knjiga();

        knjigaKnjizara.setKnjiga(knjiga);

        assertEquals(knjiga, knjigaKnjizara.getKnjiga());
    }

    @Test
    void testSetKnjizara() {
        Knjizara knjizara = new Knjizara();

        knjigaKnjizara.setKnjizara(knjizara);

        assertEquals(knjizara, knjigaKnjizara.getKnjizara());
    }

    @Test
    void testSetKolicina() {
        knjigaKnjizara.setKolicina(5);
        assertEquals(5, knjigaKnjizara.getKolicina());
    }

    @Test
    void testEqualsObject() {
        KnjigaKnjizara kk1 = new KnjigaKnjizara();
        kk1.setId(1L);

        KnjigaKnjizara kk2 = new KnjigaKnjizara();
        kk2.setId(1L);

        assertTrue(kk1.equals(kk2));
    }

    @Test
    void testEqualsObjectFalse() {
        KnjigaKnjizara kk1 = new KnjigaKnjizara();
        kk1.setId(1L);

        KnjigaKnjizara kk2 = new KnjigaKnjizara();
        kk2.setId(2L);

        assertFalse(kk1.equals(kk2));
    }

    @Test
    void testEqualsNull() {
        knjigaKnjizara.setId(1L);
        assertFalse(knjigaKnjizara.equals(null));
    }

    @Test
    void testEqualsDrugaKlasa() {
        knjigaKnjizara.setId(1L);
        assertFalse(knjigaKnjizara.equals("tekst"));
    }

    @Test
    void testHashCode() {
        KnjigaKnjizara kk1 = new KnjigaKnjizara();
        kk1.setId(1L);

        KnjigaKnjizara kk2 = new KnjigaKnjizara();
        kk2.setId(1L);

        assertEquals(kk1.hashCode(), kk2.hashCode());
    }

    @Test
    void testToString() {
        knjigaKnjizara.setId(1L);
        knjigaKnjizara.setKolicina(10);

        String s = knjigaKnjizara.toString();

        assertTrue(s.contains("1"));
        assertTrue(s.contains("10"));
    }
    
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
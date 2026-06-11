package njt.mavenproject2.entity.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.*;

class PorudzbinaTest {

    private Porudzbina porudzbina;

    @BeforeEach
    void setUp() {
        porudzbina = new Porudzbina();
    }

    @AfterEach
    void tearDown() {
        porudzbina = null;
    }

    @Test
    void testPorudzbina() {
        assertNotNull(porudzbina);
    }

    @Test
    void testSetId() {
        porudzbina.setId(1L);
        assertEquals(1L, porudzbina.getId());
    }

    @Test
    void testSetDatum() {
        LocalDateTime datum = LocalDateTime.now();

        porudzbina.setDatum(datum);

        assertEquals(datum, porudzbina.getDatum());
    }

    @Test
    void testSetUkupanIznos() {
        porudzbina.setUkupanIznos(5000.0);

        assertEquals(5000.0, porudzbina.getUkupanIznos());
    }

    @Test
    void testSetStatus() {
        porudzbina.setStatus("PLACENA");

        assertEquals("PLACENA", porudzbina.getStatus());
    }

    @Test
    void testSetKorisnik() {
        Korisnik k = new Korisnik();

        porudzbina.setKorisnik(k);

        assertEquals(k, porudzbina.getKorisnik());
    }

    @Test
    void testSetPlacanje() {
        Placanje p = new Placanje();

        porudzbina.setPlacanje(p);

        assertEquals(p, porudzbina.getPlacanje());
    }

    @Test
    void testSetStavke() {
        StavkaPorudzbine s = new StavkaPorudzbine();

        List<StavkaPorudzbine> stavke = new ArrayList<>();
        stavke.add(s);

        porudzbina.setStavke(stavke);

        assertEquals(1, porudzbina.getStavke().size());
    }

    @Test
    void testEqualsObject() {
        Porudzbina p1 = new Porudzbina();
        p1.setId(1L);

        Porudzbina p2 = new Porudzbina();
        p2.setId(1L);

        assertTrue(p1.equals(p2));
    }

    @Test
    void testEqualsObjectFalse() {
        Porudzbina p1 = new Porudzbina();
        p1.setId(1L);

        Porudzbina p2 = new Porudzbina();
        p2.setId(2L);

        assertFalse(p1.equals(p2));
    }

    @Test
    void testHashCode() {
        Porudzbina p1 = new Porudzbina();
        p1.setId(1L);

        Porudzbina p2 = new Porudzbina();
        p2.setId(1L);

        assertEquals(p1.hashCode(), p2.hashCode());
    }

    @Test
    void testToString() {
        porudzbina.setId(1L);
        porudzbina.setUkupanIznos(5000.0);
        porudzbina.setStatus("PLACENA");

        String s = porudzbina.toString();

        assertTrue(s.contains("1"));
        assertTrue(s.contains("5000.0"));
        assertTrue(s.contains("PLACENA"));
    }
}
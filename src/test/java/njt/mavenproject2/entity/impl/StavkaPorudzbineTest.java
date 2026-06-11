package njt.mavenproject2.entity.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

class StavkaPorudzbineTest {

    private StavkaPorudzbine stavka;

    @BeforeEach
    void setUp() {
        stavka = new StavkaPorudzbine();
    }

    @AfterEach
    void tearDown() {
        stavka = null;
    }

    @Test
    void testStavkaPorudzbine() {
        assertNotNull(stavka);
    }

    @Test
    void testKonstruktor() {
        Porudzbina p = new Porudzbina();
        Knjiga k = new Knjiga();

        stavka = new StavkaPorudzbine(
                1,
                2,
                1000.0,
                p,
                k
        );

        assertEquals(1, stavka.getRb());
        assertEquals(2, stavka.getKolicina());
        assertEquals(1000.0, stavka.getCenaK());
        assertEquals(2000.0, stavka.getUkupanIznosStavke());
    }

    @Test
    void testSetId() {
        stavka.setId(1L);
        assertEquals(1L, stavka.getId());
    }

    @Test
    void testSetRb() {
        stavka.setRb(2);
        assertEquals(2, stavka.getRb());
    }

    @Test
    void testSetKolicina() {
        stavka.setKolicina(5);
        assertEquals(5, stavka.getKolicina());
    }

    @Test
    void testSetCenaK() {
        stavka.setCenaK(1200.0);
        assertEquals(1200.0, stavka.getCenaK());
    }

    @Test
    void testSetUkupanIznosStavke() {
        stavka.setUkupanIznosStavke(6000.0);
        assertEquals(6000.0, stavka.getUkupanIznosStavke());
    }

    @Test
    void testSetPorudzbina() {
        Porudzbina p = new Porudzbina();

        stavka.setPorudzbina(p);

        assertEquals(p, stavka.getPorudzbina());
    }

    @Test
    void testSetKnjiga() {
        Knjiga k = new Knjiga();

        stavka.setKnjiga(k);

        assertEquals(k, stavka.getKnjiga());
    }

    @Test
    void testEqualsObject() {
        StavkaPorudzbine s1 = new StavkaPorudzbine();
        s1.setId(1L);

        StavkaPorudzbine s2 = new StavkaPorudzbine();
        s2.setId(1L);

        assertTrue(s1.equals(s2));
    }

    @Test
    void testEqualsObjectFalse() {
        StavkaPorudzbine s1 = new StavkaPorudzbine();
        s1.setId(1L);

        StavkaPorudzbine s2 = new StavkaPorudzbine();
        s2.setId(2L);

        assertFalse(s1.equals(s2));
    }

    @Test
    void testHashCode() {
        StavkaPorudzbine s1 = new StavkaPorudzbine();
        s1.setId(1L);

        StavkaPorudzbine s2 = new StavkaPorudzbine();
        s2.setId(1L);

        assertEquals(s1.hashCode(), s2.hashCode());
    }

    @Test
    void testToString() {
        stavka.setId(1L);
        stavka.setRb(1);
        stavka.setKolicina(2);
        stavka.setCenaK(1000.0);

        String s = stavka.toString();

        assertTrue(s.contains("1"));
        assertTrue(s.contains("2"));
        assertTrue(s.contains("1000.0"));
    }
}
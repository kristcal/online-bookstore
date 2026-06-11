package njt.mavenproject2.entity.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class KnjigaAutorTest {

    private KnjigaAutor knjigaAutor;

    @BeforeEach
    void setUp() {
        knjigaAutor = new KnjigaAutor();
    }

    @AfterEach
    void tearDown() {
        knjigaAutor = null;
    }

    @Test
    void testKnjigaAutor() {
        assertNotNull(knjigaAutor);
    }

    @Test
    void testKnjigaAutorKnjigaAutorString() {
        Knjiga knjiga = new Knjiga();
        Autor autor = new Autor();

        knjigaAutor = new KnjigaAutor(knjiga, autor, "Pisac");

        assertEquals(knjiga, knjigaAutor.getKnjiga());
        assertEquals(autor, knjigaAutor.getAutor());
        assertEquals("Pisac", knjigaAutor.getUloga());
    }

    @Test
    void testSetKnjiga() {
        Knjiga knjiga = new Knjiga();

        knjigaAutor.setKnjiga(knjiga);

        assertEquals(knjiga, knjigaAutor.getKnjiga());
    }

    @Test
    void testSetAutor() {
        Autor autor = new Autor();

        knjigaAutor.setAutor(autor);

        assertEquals(autor, knjigaAutor.getAutor());
    }

    @Test
    void testSetUloga() {
        knjigaAutor.setUloga("Prevodilac");

        assertEquals("Prevodilac", knjigaAutor.getUloga());
    }

    @Test
    void testEqualsObject() {
        KnjigaAutor ka1 = new KnjigaAutor();
        ka1.setId(1L);

        KnjigaAutor ka2 = new KnjigaAutor();
        ka2.setId(1L);

        assertTrue(ka1.equals(ka2));
    }

    @Test
    void testEqualsObjectFalse() {
        KnjigaAutor ka1 = new KnjigaAutor();
        ka1.setId(1L);

        KnjigaAutor ka2 = new KnjigaAutor();
        ka2.setId(2L);

        assertFalse(ka1.equals(ka2));
    }

    @Test
    void testEqualsNull() {
        knjigaAutor.setId(1L);

        assertFalse(knjigaAutor.equals(null));
    }

    @Test
    void testEqualsDrugaKlasa() {
        knjigaAutor.setId(1L);

        assertFalse(knjigaAutor.equals("tekst"));
    }

    @Test
    void testHashCode() {
        KnjigaAutor ka1 = new KnjigaAutor();
        ka1.setId(1L);

        KnjigaAutor ka2 = new KnjigaAutor();
        ka2.setId(1L);

        assertEquals(ka1.hashCode(), ka2.hashCode());
    }

    @Test
    void testToString() {
        knjigaAutor.setId(1L);
        knjigaAutor.setUloga("Pisac");

        String s = knjigaAutor.toString();

        assertTrue(s.contains("1"));
        assertTrue(s.contains("Pisac"));
    }
}
package njt.mavenproject2.entity.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AutorTest {

    private Autor autor;

    @BeforeEach
    void setUp() {
        autor = new Autor();
    }

    @AfterEach
    void tearDown() {
        autor = null;
    }

    @Test
    void testAutor() {
        assertNotNull(autor);
    }

    @Test
    void testAutorLongStringString() {
        autor = new Autor(1L, "Ivo", "Andric");

        assertEquals(1L, autor.getId());
        assertEquals("Ivo", autor.getIme());
        assertEquals("Andric", autor.getPrezime());
    }

    @Test
    void testSetId() {
        autor.setId(1L);
        assertEquals(1L, autor.getId());
    }

    @Test
    void testSetIme() {
        autor.setIme("Mesa");
        assertEquals("Mesa", autor.getIme());
    }

    @Test
    void testSetPrezime() {
        autor.setPrezime("Selimovic");
        assertEquals("Selimovic", autor.getPrezime());
    }

    @Test
    void testSetKnjige() {
        KnjigaAutor ka = new KnjigaAutor();

        List<KnjigaAutor> knjige = new ArrayList<>();
        knjige.add(ka);

        autor.setKnjige(knjige);

        assertEquals(1, autor.getKnjige().size());
        assertTrue(autor.getKnjige().contains(ka));
    }

    @Test
    void testEqualsObject() {
        Autor a1 = new Autor();
        a1.setId(1L);

        Autor a2 = new Autor();
        a2.setId(1L);

        assertTrue(a1.equals(a2));
    }

    @Test
    void testEqualsObjectFalse() {
        Autor a1 = new Autor();
        a1.setId(1L);

        Autor a2 = new Autor();
        a2.setId(2L);

        assertFalse(a1.equals(a2));
    }

    @Test
    void testEqualsNull() {
        autor.setId(1L);
        assertFalse(autor.equals(null));
    }

    @Test
    void testEqualsDrugaKlasa() {
        autor.setId(1L);
        assertFalse(autor.equals("tekst"));
    }

    @Test
    void testHashCode() {
        Autor a1 = new Autor();
        a1.setId(1L);

        Autor a2 = new Autor();
        a2.setId(1L);

        assertEquals(a1.hashCode(), a2.hashCode());
    }

    @Test
    void testToString() {
        autor = new Autor(1L, "Ivo", "Andric");

        String s = autor.toString();

        assertTrue(s.contains("1"));
        assertTrue(s.contains("Ivo"));
        assertTrue(s.contains("Andric"));
    }
}
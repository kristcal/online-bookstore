package njt.mavenproject2.entity.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class KnjizaraTest {

    private Knjizara knjizara;

    @BeforeEach
    void setUp() {
        knjizara = new Knjizara();
    }

    @AfterEach
    void tearDown() {
        knjizara = null;
    }

    @Test
    void testKnjizara() {
        assertNotNull(knjizara);
    }

    @Test
    void testKnjizaraLongStringStringString() {
        knjizara = new Knjizara(1L, "Laguna", "Beograd", "011123456");

        assertEquals(1L, knjizara.getId());
        assertEquals("Laguna", knjizara.getNaziv());
        assertEquals("Beograd", knjizara.getLokacija());
        assertEquals("011123456", knjizara.getKontakt());
    }

    @Test
    void testSetId() {
        knjizara.setId(1L);
        assertEquals(1L, knjizara.getId());
    }

    @Test
    void testSetNaziv() {
        knjizara.setNaziv("Vulkan");
        assertEquals("Vulkan", knjizara.getNaziv());
    }

    @Test
    void testSetLokacija() {
        knjizara.setLokacija("Novi Sad");
        assertEquals("Novi Sad", knjizara.getLokacija());
    }

    @Test
    void testSetKontakt() {
        knjizara.setKontakt("021123456");
        assertEquals("021123456", knjizara.getKontakt());
    }

    @Test
    void testSetPonuda() {
        KnjigaKnjizara kk = new KnjigaKnjizara();

        List<KnjigaKnjizara> ponuda = new ArrayList<>();
        ponuda.add(kk);

        knjizara.setPonuda(ponuda);

        assertEquals(1, knjizara.getPonuda().size());
        assertTrue(knjizara.getPonuda().contains(kk));
    }

    @Test
    void testEqualsObject() {
        Knjizara k1 = new Knjizara();
        k1.setId(1L);

        Knjizara k2 = new Knjizara();
        k2.setId(1L);

        assertTrue(k1.equals(k2));
    }

    @Test
    void testEqualsObjectFalse() {
        Knjizara k1 = new Knjizara();
        k1.setId(1L);

        Knjizara k2 = new Knjizara();
        k2.setId(2L);

        assertFalse(k1.equals(k2));
    }

    @Test
    void testEqualsNull() {
        knjizara.setId(1L);
        assertFalse(knjizara.equals(null));
    }

    @Test
    void testEqualsDrugaKlasa() {
        knjizara.setId(1L);
        assertFalse(knjizara.equals("tekst"));
    }

    @Test
    void testHashCode() {
        Knjizara k1 = new Knjizara();
        k1.setId(1L);

        Knjizara k2 = new Knjizara();
        k2.setId(1L);

        assertEquals(k1.hashCode(), k2.hashCode());
    }

    @Test
    void testToString() {
        knjizara = new Knjizara(1L, "Laguna", "Beograd", "011123456");

        String s = knjizara.toString();

        assertTrue(s.contains("1"));
        assertTrue(s.contains("Laguna"));
        assertTrue(s.contains("Beograd"));
    }
}
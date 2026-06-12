package njt.mavenproject2.mapper.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import njt.mavenproject2.dto.impl.KnjigaDto;
import njt.mavenproject2.entity.impl.Autor;
import njt.mavenproject2.entity.impl.Knjiga;
import njt.mavenproject2.entity.impl.KnjigaAutor;
import njt.mavenproject2.entity.impl.KnjigaKnjizara;
import njt.mavenproject2.entity.impl.Knjizara;
import njt.mavenproject2.entity.impl.Zanr;

class KnjigaMapperTest {

    private final KnjigaMapper mapper = new KnjigaMapper();

    @Test
    void testToDo() {
        Zanr zanr = new Zanr();
        zanr.setId(1L);
        zanr.setNaziv("Roman");

        Autor autor = new Autor();
        autor.setId(2L);
        autor.setIme("Ivo");
        autor.setPrezime("Andric");

        Knjiga knjiga = new Knjiga();
        knjiga.setId(10L);
        knjiga.setNaziv("Na Drini cuprija");
        knjiga.setOpis("Opis");
        knjiga.setCena(1200.0);
        knjiga.setIsbn("123");
        knjiga.setGodinaIzdanja(LocalDate.of(1945, 1, 1));
        knjiga.setImageUrl("slika.jpg");
        knjiga.setZanr(zanr);

        KnjigaAutor ka = new KnjigaAutor();
        ka.setAutor(autor);
        ka.setUloga("Pisac");

        Knjizara knjizara = new Knjizara();
        knjizara.setId(3L);
        knjizara.setNaziv("Laguna");
        knjizara.setLokacija("Beograd");

        KnjigaKnjizara kk = new KnjigaKnjizara();
        kk.setKnjizara(knjizara);
        kk.setKolicina(5);

        knjiga.setAutori(List.of(ka));
        knjiga.setDostupnost(List.of(kk));

        KnjigaDto dto = mapper.toDo(knjiga);

        assertNotNull(dto);
        assertEquals(10L, dto.getId());
        assertEquals("Na Drini cuprija", dto.getNaziv());
        assertEquals("Opis", dto.getOpis());
        assertEquals(1200.0, dto.getCena());
        assertEquals("123", dto.getIsbn());
        assertEquals(LocalDate.of(1945, 1, 1), dto.getGodinaIzdanja());
        assertEquals("slika.jpg", dto.getImageUrl());

        assertEquals(1L, dto.getZanrId());
        assertEquals("Roman", dto.getZanrNaziv());

        assertEquals(1, dto.getAutori().size());
        assertEquals(2L, dto.getAutori().get(0).id);
        assertEquals("Ivo", dto.getAutori().get(0).ime);
        assertEquals("Andric", dto.getAutori().get(0).prezime);
        assertEquals("Pisac", dto.getAutori().get(0).uloga);

        assertEquals(5, dto.getKolicina());
        assertEquals(1, dto.getDostupnost().size());
        assertEquals(3L, dto.getDostupnost().get(0).knjizaraId);
        assertEquals("Laguna", dto.getDostupnost().get(0).knjizaraNaziv);
        assertEquals("Beograd", dto.getDostupnost().get(0).lokacija);
        assertEquals(5, dto.getDostupnost().get(0).kolicina);
    }

    @Test
    void testToDoNull() {
        assertNull(mapper.toDo(null));
    }

    @Test
    void testToDoBezZanraAutoraIKnjizare() {
        Knjiga knjiga = new Knjiga();
        knjiga.setId(1L);

        KnjigaAutor ka = new KnjigaAutor();
        ka.setAutor(null);

        KnjigaKnjizara kk = new KnjigaKnjizara();
        kk.setKnjizara(null);
        kk.setKolicina(null);

        knjiga.setAutori(List.of(ka));
        knjiga.setDostupnost(List.of(kk));

        KnjigaDto dto = mapper.toDo(knjiga);

        assertNotNull(dto);
        assertNull(dto.getZanrId());
        assertTrue(dto.getAutori().isEmpty());
        assertEquals(0, dto.getKolicina());
        assertEquals(1, dto.getDostupnost().size());
        assertNull(dto.getDostupnost().get(0).knjizaraId);
        assertEquals(0, dto.getDostupnost().get(0).kolicina);
    }

    @Test
    void testToDoSaNullListama() {
        Knjiga knjiga = new Knjiga();
        knjiga.setId(1L);
        knjiga.setAutori(null);
        knjiga.setDostupnost(null);

        KnjigaDto dto = mapper.toDo(knjiga);

        assertNotNull(dto);
        assertTrue(dto.getAutori().isEmpty());
        assertTrue(dto.getDostupnost().isEmpty());
        assertEquals(0, dto.getKolicina());
    }

    @Test
    void testToEntity() {
        KnjigaDto dto = new KnjigaDto();
        dto.setId(1L);
        dto.setNaziv("1984");
        dto.setOpis("Opis");
        dto.setCena(1000.0);
        dto.setIsbn("123");
        dto.setGodinaIzdanja(LocalDate.of(1949, 1, 1));
        dto.setImageUrl("cover.jpg");

        Knjiga knjiga = mapper.toEntity(dto);

        assertNotNull(knjiga);
        assertEquals(1L, knjiga.getId());
        assertEquals("1984", knjiga.getNaziv());
        assertEquals("Opis", knjiga.getOpis());
        assertEquals(1000.0, knjiga.getCena());
        assertEquals("123", knjiga.getIsbn());
        assertEquals(LocalDate.of(1949, 1, 1), knjiga.getGodinaIzdanja());
        assertEquals("cover.jpg", knjiga.getImageUrl());
        assertNotNull(knjiga.getAutori());
        assertNotNull(knjiga.getDostupnost());
    }

    @Test
    void testToEntityNull() {
        assertNull(mapper.toEntity(null));
    }
}
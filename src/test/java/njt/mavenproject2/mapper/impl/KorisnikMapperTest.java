package njt.mavenproject2.mapper.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import njt.mavenproject2.dto.impl.KorisnikDto;
import njt.mavenproject2.entity.impl.Korisnik;

class KorisnikMapperTest {

    private final KorisnikMapper mapper = new KorisnikMapper();

    @Test
    void testToDo() {
        Korisnik korisnik = new Korisnik();
        korisnik.setId(1L);
        korisnik.setIme("Kristina");
        korisnik.setPrezime("Calic");
        korisnik.setEmail("test@test.com");
        korisnik.setLozinka("123456");

        KorisnikDto dto = mapper.toDo(korisnik);

        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals("Kristina", dto.getIme());
        assertEquals("Calic", dto.getPrezime());
        assertEquals("test@test.com", dto.getEmail());
        assertEquals("123456", dto.getLozinka());
    }

    @Test
    void testToDoNull() {
        assertNull(mapper.toDo(null));
    }

    @Test
    void testToEntity() {
        KorisnikDto dto = new KorisnikDto(
                1L,
                "Kristina",
                "Calic",
                "test@test.com",
                "123456"
        );

        Korisnik korisnik = mapper.toEntity(dto);

        assertNotNull(korisnik);
        assertEquals(1L, korisnik.getId());
        assertEquals("Kristina", korisnik.getIme());
        assertEquals("Calic", korisnik.getPrezime());
        assertEquals("test@test.com", korisnik.getEmail());
        assertEquals("123456", korisnik.getLozinka());
    }

    @Test
    void testToEntityNull() {
        assertNull(mapper.toEntity(null));
    }
}
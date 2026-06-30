package njt.mavenproject2.mapper.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.time.Month;

import org.junit.jupiter.api.Test;

import njt.mavenproject2.dto.impl.PorudzbinaDto;
import njt.mavenproject2.entity.impl.Korisnik;
import njt.mavenproject2.entity.impl.Porudzbina;

/**
 * Test klasa za proveru funkcionalnosti klase {@link PorudzbinaMapper}.
 *
 * Testira konverziju između entiteta Porudzbina i DTO objekta PorudzbinaDto,
 * uključujući podatke o korisniku, datumu, ukupnom iznosu i statusu
 * porudžbine.
 *
 * @author Korisnik
 */
class PorudzbinaMapperTest {

    /**
     * Mapper koji se testira.
     */
    private final PorudzbinaMapper mapper = new PorudzbinaMapper();

    /**
     * Proverava konverziju entiteta Porudzbina u DTO objekat.
     */
    @Test
    void testToDo() {
        Korisnik korisnik = new Korisnik();
        korisnik.setId(5L);

        Porudzbina p = new Porudzbina();
        p.setId(1L);
        p.setDatum(LocalDateTime.of(2026, Month.JUNE, 12, 10, 0));
        p.setUkupanIznos(2500.0);
        p.setStatus("KREIRANA");
        p.setKorisnik(korisnik);

        PorudzbinaDto dto = mapper.toDo(p);

        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals(LocalDateTime.of(2026, Month.JUNE, 12, 10, 0), dto.getDatum());
        assertEquals(2500.0, dto.getUkupanIznos());
        assertEquals("KREIRANA", dto.getStatus());
        assertEquals(5L, dto.getKorisnikId());
    }

    /**
     * Proverava konverziju porudžbine koja nema povezanog korisnika.
     */
    @Test
    void testToDoBezKorisnika() {
        Porudzbina p = new Porudzbina();
        p.setId(1L);
        p.setKorisnik(null);

        PorudzbinaDto dto = mapper.toDo(p);

        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertNull(dto.getKorisnikId());
    }

    /**
     * Proverava da konverzija null entiteta vraća null vrednost.
     */
    @Test
    void testToDoNull() {
        assertNull(mapper.toDo(null));
    }

    /**
     * Proverava konverziju DTO objekta PorudzbinaDto u entitet Porudzbina.
     */
    @Test
    void testToEntity() {
        PorudzbinaDto dto = new PorudzbinaDto();
        dto.setId(1L);
        dto.setDatum(LocalDateTime.of(2026, Month.JUNE, 12, 10, 0));
        dto.setUkupanIznos(3000.0);
        dto.setStatus("OBRADJENA");

        Porudzbina p = mapper.toEntity(dto);

        assertNotNull(p);
        assertEquals(1L, p.getId());
        assertEquals(LocalDateTime.of(2026, Month.JUNE, 12, 10, 0), p.getDatum());
        assertEquals(3000.0, p.getUkupanIznos());
        assertEquals("OBRADJENA", p.getStatus());
    }

    /**
     * Proverava da se postavlja podrazumevani status kada status nije prosleđen.
     */
    @Test
    void testToEntityDefaultStatus() {
        PorudzbinaDto dto = new PorudzbinaDto();
        dto.setStatus(null);

        Porudzbina p = mapper.toEntity(dto);

        assertNotNull(p);
        assertEquals("KREIRANA", p.getStatus());
    }

    /**
     * Proverava da konverzija null DTO objekta vraća null vrednost.
     */
    @Test
    void testToEntityNull() {
        assertNull(mapper.toEntity(null));
    }
}
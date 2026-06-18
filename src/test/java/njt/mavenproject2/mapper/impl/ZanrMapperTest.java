package njt.mavenproject2.mapper.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import njt.mavenproject2.dto.impl.ZanrDto;
import njt.mavenproject2.entity.impl.Zanr;

/**
 * Test klasa za proveru funkcionalnosti klase {@link ZanrMapper}.
 *
 * Testira konverziju između entiteta Zanr i DTO objekta ZanrDto,
 * kao i ponašanje mapper-a kada je prosleđena null vrednost.
 *
 * @author Korisnik
 */
class ZanrMapperTest {

    /**
     * Mapper koji se testira.
     */
    private final ZanrMapper mapper = new ZanrMapper();

    /**
     * Proverava konverziju entiteta Zanr u DTO objekat.
     */
    @Test
    void testToDo() {

        Zanr zanr = new Zanr();
        zanr.setId(1L);
        zanr.setNaziv("Roman");

        ZanrDto dto = mapper.toDo(zanr);

        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals("Roman", dto.getNaziv());
    }

    /**
     * Proverava da konverzija null entiteta vraća null vrednost.
     */
    @Test
    void testToDoNull() {
        assertNull(mapper.toDo(null));
    }

    /**
     * Proverava konverziju DTO objekta ZanrDto u entitet Zanr.
     */
    @Test
    void testToEntity() {

        ZanrDto dto = new ZanrDto(1L, "Roman");

        Zanr zanr = mapper.toEntity(dto);

        assertNotNull(zanr);
        assertEquals(1L, zanr.getId());
        assertEquals("Roman", zanr.getNaziv());
    }

    /**
     * Proverava da konverzija null DTO objekta vraća null vrednost.
     */
    @Test
    void testToEntityNull() {
        assertNull(mapper.toEntity(null));
    }
}
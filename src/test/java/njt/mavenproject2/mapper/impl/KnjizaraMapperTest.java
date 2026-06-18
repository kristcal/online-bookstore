package njt.mavenproject2.mapper.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import njt.mavenproject2.dto.impl.KnjizaraDto;
import njt.mavenproject2.entity.impl.Knjizara;

/**
 * Test klasa za proveru funkcionalnosti klase {@link KnjizaraMapper}.
 *
 * Testira konverziju između entiteta Knjizara i DTO objekta KnjizaraDto,
 * kao i ponašanje mapper-a kada je prosleđena null vrednost.
 *
 * @author Korisnik
 */
class KnjizaraMapperTest {

    /**
     * Mapper koji se testira.
     */
    private final KnjizaraMapper mapper = new KnjizaraMapper();

    /**
     * Proverava konverziju entiteta Knjizara u DTO objekat.
     */
    @Test
    void testToDo() {
        Knjizara knjizara = new Knjizara();
        knjizara.setId(1L);
        knjizara.setNaziv("Laguna");
        knjizara.setLokacija("Beograd");
        knjizara.setKontakt("011123456");

        KnjizaraDto dto = mapper.toDo(knjizara);

        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals("Laguna", dto.getNaziv());
        assertEquals("Beograd", dto.getLokacija());
        assertEquals("011123456", dto.getKontakt());
    }

    /**
     * Proverava da konverzija null entiteta vraća null vrednost.
     */
    @Test
    void testToDoNull() {
        assertNull(mapper.toDo(null));
    }

    /**
     * Proverava konverziju DTO objekta KnjizaraDto u entitet Knjizara.
     */
    @Test
    void testToEntity() {
        KnjizaraDto dto =
                new KnjizaraDto(1L, "Laguna", "Beograd", "011123456");

        Knjizara knjizara = mapper.toEntity(dto);

        assertNotNull(knjizara);
        assertEquals(1L, knjizara.getId());
        assertEquals("Laguna", knjizara.getNaziv());
        assertEquals("Beograd", knjizara.getLokacija());
        assertEquals("011123456", knjizara.getKontakt());
    }

    /**
     * Proverava da konverzija null DTO objekta vraća null vrednost.
     */
    @Test
    void testToEntityNull() {
        assertNull(mapper.toEntity(null));
    }
}
package njt.mavenproject2.mapper.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import njt.mavenproject2.dto.impl.KnjigaKnjizaraDto;
import njt.mavenproject2.entity.impl.KnjigaKnjizara;
import njt.mavenproject2.entity.impl.Knjizara;

/**
 * Test klasa za proveru funkcionalnosti klase {@link KnjigaKnjizaraMapper}.
 *
 * Testira konverziju između entiteta KnjigaKnjizara i DTO objekta
 * KnjigaKnjizaraDto, uključujući slučajeve kada povezana knjižara
 * nije postavljena ili kada je prosleđena null vrednost.
 *
 * @author Korisnik
 */
class KnjigaKnjizaraMapperTest {

    /**
     * Mapper koji se testira.
     */
    private final KnjigaKnjizaraMapper mapper = new KnjigaKnjizaraMapper();

    /**
     * Proverava konverziju entiteta KnjigaKnjizara u DTO objekat.
     */
    @Test
    void testToDo() {
        Knjizara knjizara = new Knjizara();
        knjizara.setId(5L);

        KnjigaKnjizara kk = new KnjigaKnjizara();
        kk.setId(1L);
        kk.setKnjizara(knjizara);
        kk.setKolicina(10);

        KnjigaKnjizaraDto dto = mapper.toDo(kk);

        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals(5L, dto.getKnjizaraId());
        assertEquals(10, dto.getKolicina());
    }

    /**
     * Proverava konverziju kada veza nema postavljenu knjižaru.
     */
    @Test
    void testToDoBezKnjizare() {
        KnjigaKnjizara kk = new KnjigaKnjizara();
        kk.setId(1L);
        kk.setKnjizara(null);
        kk.setKolicina(10);

        KnjigaKnjizaraDto dto = mapper.toDo(kk);

        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertNull(dto.getKnjizaraId());
        assertEquals(10, dto.getKolicina());
    }

    /**
     * Proverava da konverzija null entiteta vraća null vrednost.
     */
    @Test
    void testToDoNull() {
        assertNull(mapper.toDo(null));
    }

    /**
     * Proverava konverziju DTO objekta u entitet KnjigaKnjizara.
     *
     * Povezani entiteti Knjiga i Knjižara ne postavljaju se u mapper-u,
     * već u servisnom sloju.
     */
    @Test
    void testToEntity() {
        KnjigaKnjizaraDto dto = new KnjigaKnjizaraDto();
        dto.setId(1L);
        dto.setKnjizaraId(5L);
        dto.setKolicina(10);

        KnjigaKnjizara kk = mapper.toEntity(dto);

        assertNotNull(kk);
        assertEquals(1L, kk.getId());
        assertEquals(10, kk.getKolicina());
        assertNull(kk.getKnjiga());
        assertNull(kk.getKnjizara());
    }

    /**
     * Proverava da konverzija null DTO objekta vraća null vrednost.
     */
    @Test
    void testToEntityNull() {
        assertNull(mapper.toEntity(null));
    }
}
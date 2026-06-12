package njt.mavenproject2.mapper.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import njt.mavenproject2.dto.impl.KnjigaKnjizaraDto;
import njt.mavenproject2.entity.impl.KnjigaKnjizara;
import njt.mavenproject2.entity.impl.Knjizara;

class KnjigaKnjizaraMapperTest {

    private final KnjigaKnjizaraMapper mapper = new KnjigaKnjizaraMapper();

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

    @Test
    void testToDoNull() {
        assertNull(mapper.toDo(null));
    }

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
        assertNull(kk.getKnjiga()); // postavlja servis
        assertNull(kk.getKnjizara()); // postavlja servis
    }

    @Test
    void testToEntityNull() {
        assertNull(mapper.toEntity(null));
    }
}
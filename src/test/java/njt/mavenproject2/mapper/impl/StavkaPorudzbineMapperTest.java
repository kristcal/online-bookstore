package njt.mavenproject2.mapper.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import njt.mavenproject2.dto.impl.StavkaPorudzbineDto;
import njt.mavenproject2.entity.impl.Knjiga;
import njt.mavenproject2.entity.impl.StavkaPorudzbine;

class StavkaPorudzbineMapperTest {

    private final StavkaPorudzbineMapper mapper = new StavkaPorudzbineMapper();

    @Test
    void testToDo() {
        Knjiga knjiga = new Knjiga();
        knjiga.setId(10L);

        StavkaPorudzbine s = new StavkaPorudzbine();
        s.setId(1L);
        s.setRb(2);
        s.setKnjiga(knjiga);
        s.setKolicina(3);
        s.setCenaK(500.0);

        StavkaPorudzbineDto dto = mapper.toDo(s);

        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals(2, dto.getRb());
        assertEquals(10L, dto.getKnjigaId());
        assertEquals(3, dto.getKolicina());
        assertEquals(500.0, dto.getCenaK());
    }

    @Test
    void testToDoBezKnjige() {
        StavkaPorudzbine s = new StavkaPorudzbine();
        s.setId(1L);
        s.setKnjiga(null);

        StavkaPorudzbineDto dto = mapper.toDo(s);

        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertNull(dto.getKnjigaId());
    }

    @Test
    void testToDoNull() {
        assertNull(mapper.toDo(null));
    }

    @Test
    void testToEntity() {
        StavkaPorudzbineDto dto =
                new StavkaPorudzbineDto(1L, 2, 10L, 3, 500.0);

        StavkaPorudzbine s = mapper.toEntity(dto);

        assertNotNull(s);
        assertEquals(1L, s.getId());
        assertEquals(2, s.getRb());
        assertEquals(3, s.getKolicina());
        assertEquals(500.0, s.getCenaK());
        assertNull(s.getKnjiga()); // postavlja servis
        assertNull(s.getPorudzbina()); // postavlja servis
    }

    @Test
    void testToEntityNull() {
        assertNull(mapper.toEntity(null));
    }
}
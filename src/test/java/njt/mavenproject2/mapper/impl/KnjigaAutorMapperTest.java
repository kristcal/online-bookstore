package njt.mavenproject2.mapper.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import njt.mavenproject2.dto.impl.KnjigaAutorDto;
import njt.mavenproject2.entity.impl.Autor;
import njt.mavenproject2.entity.impl.KnjigaAutor;

class KnjigaAutorMapperTest {

    private final KnjigaAutorMapper mapper = new KnjigaAutorMapper();

    @Test
    void testToDo() {
        Autor autor = new Autor();
        autor.setId(5L);

        KnjigaAutor ka = new KnjigaAutor();
        ka.setId(1L);
        ka.setAutor(autor);
        ka.setUloga("Pisac");

        KnjigaAutorDto dto = mapper.toDo(ka);

        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals(5L, dto.getAutorId());
        assertEquals("Pisac", dto.getUloga());
    }

    @Test
    void testToDoBezAutora() {
        KnjigaAutor ka = new KnjigaAutor();
        ka.setId(1L);
        ka.setAutor(null);
        ka.setUloga("Pisac");

        KnjigaAutorDto dto = mapper.toDo(ka);

        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertNull(dto.getAutorId());
        assertEquals("Pisac", dto.getUloga());
    }

    @Test
    void testToDoNull() {
        assertNull(mapper.toDo(null));
    }

    @Test
    void testToEntity() {
        KnjigaAutorDto dto = new KnjigaAutorDto();
        dto.setId(1L);
        dto.setAutorId(5L);
        dto.setUloga("Pisac");

        KnjigaAutor ka = mapper.toEntity(dto);

        assertNotNull(ka);
        assertNull(ka.getId()); // mapper ne setuje ID
        assertNull(ka.getAutor()); // autor postavlja servis
        assertNull(ka.getKnjiga()); // knjigu postavlja servis
        assertEquals("Pisac", ka.getUloga());
    }

    @Test
    void testToEntityNull() {
        assertNull(mapper.toEntity(null));
    }
}
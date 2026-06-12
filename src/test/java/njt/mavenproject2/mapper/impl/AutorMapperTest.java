package njt.mavenproject2.mapper.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import njt.mavenproject2.dto.impl.AutorDto;
import njt.mavenproject2.entity.impl.Autor;

class AutorMapperTest {

    private final AutorMapper mapper = new AutorMapper();

    @Test
    void testToDo() {

        Autor autor = new Autor();
        autor.setId(1L);
        autor.setIme("Ivo");
        autor.setPrezime("Andric");

        AutorDto dto = mapper.toDo(autor);

        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals("Ivo", dto.getIme());
        assertEquals("Andric", dto.getPrezime());
    }

    @Test
    void testToDoNull() {
        assertNull(mapper.toDo(null));
    }

    @Test
    void testToEntity() {

        AutorDto dto = new AutorDto(1L, "Ivo", "Andric");

        Autor autor = mapper.toEntity(dto);

        assertNotNull(autor);
        assertEquals(1L, autor.getId());
        assertEquals("Ivo", autor.getIme());
        assertEquals("Andric", autor.getPrezime());
    }

    @Test
    void testToEntityNull() {
        assertNull(mapper.toEntity(null));
    }
}
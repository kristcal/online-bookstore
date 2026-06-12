package njt.mavenproject2.mapper.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import njt.mavenproject2.dto.impl.ZanrDto;
import njt.mavenproject2.entity.impl.Zanr;

class ZanrMapperTest {

    private final ZanrMapper mapper = new ZanrMapper();

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

    @Test
    void testToDoNull() {
        assertNull(mapper.toDo(null));
    }

    @Test
    void testToEntity() {

        ZanrDto dto = new ZanrDto(1L, "Roman");

        Zanr zanr = mapper.toEntity(dto);

        assertNotNull(zanr);
        assertEquals(1L, zanr.getId());
        assertEquals("Roman", zanr.getNaziv());
    }

    @Test
    void testToEntityNull() {
        assertNull(mapper.toEntity(null));
    }
}
package njt.mavenproject2.mapper.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import njt.mavenproject2.dto.impl.AutorDto;
import njt.mavenproject2.entity.impl.Autor;

/**
 * Test klasa za proveru funkcionalnosti klase {@link AutorMapper}.
 *
 * Testira konverziju između entiteta Autor i DTO objekta AutorDto,
 * kao i ponašanje mapper-a kada je prosleđena null vrednost.
 *
 * @author Korisnik
 */
class AutorMapperTest {

    /**
     * Mapper koji se testira.
     */
    private final AutorMapper mapper = new AutorMapper();

    /**
     * Proverava konverziju entiteta Autor u DTO objekat.
     */
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

    /**
     * Proverava da konverzija null entiteta vraća null vrednost.
     */
    @Test
    void testToDoNull() {
        assertNull(mapper.toDo(null));
    }

    /**
     * Proverava konverziju DTO objekta AutorDto u entitet Autor.
     */
    @Test
    void testToEntity() {

        AutorDto dto = new AutorDto(1L, "Ivo", "Andric");

        Autor autor = mapper.toEntity(dto);

        assertNotNull(autor);
        assertEquals(1L, autor.getId());
        assertEquals("Ivo", autor.getIme());
        assertEquals("Andric", autor.getPrezime());
    }

    /**
     * Proverava da konverzija null DTO objekta vraća null vrednost.
     */
    @Test
    void testToEntityNull() {
        assertNull(mapper.toEntity(null));
    }
}
package njt.mavenproject2.mapper.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import njt.mavenproject2.dto.impl.KnjigaAutorDto;
import njt.mavenproject2.entity.impl.Autor;
import njt.mavenproject2.entity.impl.KnjigaAutor;

/**
 * Test klasa za proveru funkcionalnosti klase {@link KnjigaAutorMapper}.
 *
 * Testira konverziju između entiteta KnjigaAutor i DTO objekta
 * KnjigaAutorDto, uključujući slučajeve kada povezani autor ne postoji
 * ili kada je prosleđena null vrednost.
 *
 * @author Korisnik
 */
class KnjigaAutorMapperTest {

    /**
     * Mapper koji se testira.
     */
    private final KnjigaAutorMapper mapper = new KnjigaAutorMapper();

    /**
     * Proverava konverziju entiteta KnjigaAutor u DTO objekat.
     */
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

    /**
     * Proverava konverziju kada veza nema postavljenog autora.
     */
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

    /**
     * Proverava da konverzija null entiteta vraća null vrednost.
     */
    @Test
    void testToDoNull() {
        assertNull(mapper.toDo(null));
    }

    /**
     * Proverava konverziju DTO objekta u entitet KnjigaAutor.
     *
     * Povezani entiteti Autor i Knjiga ne postavljaju se u mapper-u,
     * već u servisnom sloju.
     */
    @Test
    void testToEntity() {
        KnjigaAutorDto dto = new KnjigaAutorDto();
        dto.setId(1L);
        dto.setAutorId(5L);
        dto.setUloga("Pisac");

        KnjigaAutor ka = mapper.toEntity(dto);

        assertNotNull(ka);
        assertNull(ka.getId());
        assertNull(ka.getAutor());
        assertNull(ka.getKnjiga());
        assertEquals("Pisac", ka.getUloga());
    }

    /**
     * Proverava da konverzija null DTO objekta vraća null vrednost.
     */
    @Test
    void testToEntityNull() {
        assertNull(mapper.toEntity(null));
    }
}
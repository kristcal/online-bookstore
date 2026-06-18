package njt.mavenproject2.servis;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import njt.mavenproject2.dto.impl.AutorDto;
import njt.mavenproject2.entity.impl.Autor;
import njt.mavenproject2.mapper.impl.AutorMapper;
import njt.mavenproject2.repository.impl.AutorRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test klasa za proveru funkcionalnosti klase {@link AutorServis}.
 *
 * Testira pronalaženje, kreiranje, izmenu i brisanje autora.
 *
 * @author Korisnik
 */
class AutorServisTest {

    /**
     * Mock repozitorijum autora.
     */
    private AutorRepository repo;

    /**
     * Mock mapper za konverziju autora.
     */
    private AutorMapper mapper;

    /**
     * Servis koji se testira.
     */
    private AutorServis servis;

    /**
     * Inicijalizuje mock objekte pre svakog testa.
     */
    @BeforeEach
    void setUp() {
        repo = mock(AutorRepository.class);
        mapper = mock(AutorMapper.class);
        servis = new AutorServis(repo, mapper);
    }

    /**
     * Proverava uspešno pronalaženje svih autora.
     */
    @Test
    void testFindAll() {
        Autor autor = new Autor();
        AutorDto dto = new AutorDto();

        when(repo.findAll()).thenReturn(List.of(autor));
        when(mapper.toDo(autor)).thenReturn(dto);

        List<AutorDto> rezultat = servis.findAll();

        assertEquals(1, rezultat.size());
        verify(repo).findAll();
        verify(mapper).toDo(autor);
    }

    /**
     * Proverava uspešno pronalaženje autora prema identifikatoru.
     *
     * @throws Exception ukoliko autor nije pronađen
     */
    @Test
    void testFindById() throws Exception {
        Autor autor = new Autor();
        autor.setId(1L);

        AutorDto dto = new AutorDto();
        dto.setId(1L);

        when(repo.findById(1L)).thenReturn(autor);
        when(mapper.toDo(autor)).thenReturn(dto);

        AutorDto rezultat = servis.findById(1L);

        assertEquals(1L, rezultat.getId());
        verify(repo).findById(1L);
        verify(mapper).toDo(autor);
    }

    /**
     * Proverava uspešno kreiranje autora.
     */
    @Test
    void testCreate() {
        AutorDto dto = new AutorDto();
        dto.setIme("Ivo");
        dto.setPrezime("Andric");

        Autor autor = new Autor();
        autor.setId(1L);

        AutorDto rezultatDto = new AutorDto();
        rezultatDto.setId(1L);

        when(mapper.toEntity(dto)).thenReturn(autor);
        when(mapper.toDo(autor)).thenReturn(rezultatDto);

        AutorDto rezultat = servis.create(dto);

        assertEquals(1L, rezultat.getId());
        verify(mapper).toEntity(dto);
        verify(repo).save(autor);
        verify(mapper).toDo(autor);
    }

    /**
     * Proverava uspešno ažuriranje autora.
     */
    @Test
    void testUpdate() {
        AutorDto dto = new AutorDto();
        dto.setId(1L);
        dto.setIme("Mesa");

        Autor autor = new Autor();
        autor.setId(1L);

        AutorDto rezultatDto = new AutorDto();
        rezultatDto.setId(1L);
        rezultatDto.setIme("Mesa");

        when(mapper.toEntity(dto)).thenReturn(autor);
        when(mapper.toDo(autor)).thenReturn(rezultatDto);

        AutorDto rezultat = servis.update(dto);

        assertEquals(1L, rezultat.getId());
        assertEquals("Mesa", rezultat.getIme());
        verify(mapper).toEntity(dto);
        verify(repo).save(autor);
        verify(mapper).toDo(autor);
    }

    /**
     * Proverava uspešno brisanje autora prema identifikatoru.
     */
    @Test
    void testDeleteById() {
        servis.deleteById(1L);

        verify(repo).deleteById(1L);
    }
}
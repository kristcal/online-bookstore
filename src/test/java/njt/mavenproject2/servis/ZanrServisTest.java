package njt.mavenproject2.servis;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import njt.mavenproject2.dto.impl.ZanrDto;
import njt.mavenproject2.entity.impl.Zanr;
import njt.mavenproject2.mapper.impl.ZanrMapper;
import njt.mavenproject2.repository.impl.ZanrRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test klasa za proveru funkcionalnosti klase {@link ZanrServis}.
 *
 * Testira pronalaženje, kreiranje, izmenu i brisanje žanrova.
 *
 * @author Korisnik
 */
class ZanrServisTest {

    /**
     * Mock repozitorijum žanrova.
     */
    private ZanrRepository repo;

    /**
     * Mock mapper za konverziju žanrova.
     */
    private ZanrMapper mapper;

    /**
     * Servis koji se testira.
     */
    private ZanrServis servis;

    /**
     * Inicijalizuje mock objekte pre svakog testa.
     */
    @BeforeEach
    void setUp() {
        repo = mock(ZanrRepository.class);
        mapper = mock(ZanrMapper.class);
        servis = new ZanrServis(repo, mapper);
    }

    /**
     * Proverava uspešno pronalaženje svih žanrova.
     */
    @Test
    void testFindAll() {
        Zanr zanr = new Zanr();
        ZanrDto dto = new ZanrDto();

        when(repo.findAll()).thenReturn(List.of(zanr));
        when(mapper.toDo(zanr)).thenReturn(dto);

        List<ZanrDto> rezultat = servis.findAll();

        assertEquals(1, rezultat.size());
        verify(repo).findAll();
        verify(mapper).toDo(zanr);
    }

    /**
     * Proverava uspešno pronalaženje žanra prema identifikatoru.
     *
     * @throws Exception ukoliko žanr nije pronađen
     */
    @Test
    void testFindById() throws Exception {
        Zanr zanr = new Zanr();
        zanr.setId(1L);

        ZanrDto dto = new ZanrDto();
        dto.setId(1L);

        when(repo.findById(1L)).thenReturn(zanr);
        when(mapper.toDo(zanr)).thenReturn(dto);

        ZanrDto rezultat = servis.findById(1L);

        assertEquals(1L, rezultat.getId());
        verify(repo).findById(1L);
        verify(mapper).toDo(zanr);
    }

    /**
     * Proverava uspešno kreiranje žanra.
     */
    @Test
    void testCreate() {
        ZanrDto dto = new ZanrDto();
        dto.setNaziv("Roman");

        Zanr zanr = new Zanr();
        zanr.setId(1L);

        ZanrDto rezultatDto = new ZanrDto();
        rezultatDto.setId(1L);

        when(mapper.toEntity(dto)).thenReturn(zanr);
        when(mapper.toDo(zanr)).thenReturn(rezultatDto);

        ZanrDto rezultat = servis.create(dto);

        assertEquals(1L, rezultat.getId());
        verify(mapper).toEntity(dto);
        verify(repo).save(zanr);
        verify(mapper).toDo(zanr);
    }

    /**
     * Proverava uspešno ažuriranje žanra.
     */
    @Test
    void testUpdate() {
        ZanrDto dto = new ZanrDto();
        dto.setId(1L);
        dto.setNaziv("Drama");

        Zanr zanr = new Zanr();
        zanr.setId(1L);

        ZanrDto rezultatDto = new ZanrDto();
        rezultatDto.setId(1L);
        rezultatDto.setNaziv("Drama");

        when(mapper.toEntity(dto)).thenReturn(zanr);
        when(mapper.toDo(zanr)).thenReturn(rezultatDto);

        ZanrDto rezultat = servis.update(dto);

        assertEquals(1L, rezultat.getId());
        assertEquals("Drama", rezultat.getNaziv());

        verify(mapper).toEntity(dto);
        verify(repo).save(zanr);
        verify(mapper).toDo(zanr);
    }

    /**
     * Proverava uspešno brisanje žanra prema identifikatoru.
     */
    @Test
    void testDeleteById() {
        servis.deleteById(1L);

        verify(repo).deleteById(1L);
    }
}
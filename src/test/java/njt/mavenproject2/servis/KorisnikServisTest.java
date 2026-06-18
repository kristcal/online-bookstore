package njt.mavenproject2.servis;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import njt.mavenproject2.dto.impl.KorisnikDto;
import njt.mavenproject2.entity.impl.Korisnik;
import njt.mavenproject2.mapper.impl.KorisnikMapper;
import njt.mavenproject2.repository.impl.KorisnikRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test klasa za proveru funkcionalnosti klase {@link KorisnikServis}.
 *
 * Testira pronalaženje, kreiranje, izmenu i brisanje korisnika.
 *
 * @author Korisnik
 */
class KorisnikServisTest {

    /**
     * Mock repozitorijum korisnika.
     */
    private KorisnikRepository repo;

    /**
     * Mock mapper za konverziju korisnika.
     */
    private KorisnikMapper mapper;

    /**
     * Servis koji se testira.
     */
    private KorisnikServis servis;

    /**
     * Inicijalizuje mock objekte pre svakog testa.
     */
    @BeforeEach
    void setUp() {
        repo = mock(KorisnikRepository.class);
        mapper = mock(KorisnikMapper.class);
        servis = new KorisnikServis(repo, mapper);
    }

    /**
     * Proverava uspešno kreiranje korisnika.
     */
    @Test
    void testCreate() {
        KorisnikDto dto = new KorisnikDto();
        dto.setIme("Kristina");
        dto.setPrezime("Calic");
        dto.setEmail("kristina@gmail.com");
        dto.setLozinka("123456");

        Korisnik korisnik = new Korisnik();
        korisnik.setId(1L);
        korisnik.setIme("Kristina");

        KorisnikDto rezultatDto = new KorisnikDto();
        rezultatDto.setId(1L);
        rezultatDto.setIme("Kristina");

        when(mapper.toEntity(dto)).thenReturn(korisnik);
        when(mapper.toDo(korisnik)).thenReturn(rezultatDto);

        KorisnikDto rezultat = servis.create(dto);

        assertNotNull(rezultat);
        assertEquals(1L, rezultat.getId());
        assertEquals("Kristina", rezultat.getIme());

        verify(mapper).toEntity(dto);
        verify(repo).save(korisnik);
        verify(mapper).toDo(korisnik);
    }

    /**
     * Proverava uspešno pronalaženje svih korisnika.
     */
    @Test
    void testFindAll() {
        Korisnik k = new Korisnik();
        KorisnikDto dto = new KorisnikDto();

        when(repo.findAll()).thenReturn(List.of(k));
        when(mapper.toDo(k)).thenReturn(dto);

        List<KorisnikDto> rezultat = servis.findAll();

        assertEquals(1, rezultat.size());
        verify(repo).findAll();
        verify(mapper).toDo(k);
    }

    /**
     * Proverava uspešno pronalaženje korisnika prema identifikatoru.
     *
     * @throws Exception ukoliko korisnik nije pronađen
     */
    @Test
    void testFindById() throws Exception {
        Korisnik k = new Korisnik();
        k.setId(1L);

        KorisnikDto dto = new KorisnikDto();
        dto.setId(1L);

        when(repo.findById(1L)).thenReturn(k);
        when(mapper.toDo(k)).thenReturn(dto);

        KorisnikDto rezultat = servis.findById(1L);

        assertEquals(1L, rezultat.getId());
        verify(repo).findById(1L);
        verify(mapper).toDo(k);
    }

    /**
     * Proverava uspešno ažuriranje korisnika.
     *
     * @throws Exception ukoliko korisnik nije pronađen
     */
    @Test
    void testUpdate() throws Exception {
        KorisnikDto dto = new KorisnikDto();
        dto.setIme("Novo");
        dto.setPrezime("Prezime");
        dto.setEmail("novo@gmail.com");
        dto.setLozinka("abcdef");

        Korisnik existing = new Korisnik();
        existing.setId(1L);

        KorisnikDto rezultatDto = new KorisnikDto();
        rezultatDto.setId(1L);
        rezultatDto.setIme("Novo");

        when(repo.findById(1L)).thenReturn(existing);
        when(mapper.toDo(existing)).thenReturn(rezultatDto);

        KorisnikDto rezultat = servis.update(1L, dto);

        assertEquals("Novo", existing.getIme());
        assertEquals("Prezime", existing.getPrezime());
        assertEquals("novo@gmail.com", existing.getEmail());
        assertEquals("abcdef", existing.getLozinka());
        assertEquals(1L, rezultat.getId());

        verify(repo).findById(1L);
        verify(repo).save(existing);
        verify(mapper).toDo(existing);
    }

    /**
     * Proverava uspešno brisanje korisnika prema identifikatoru.
     */
    @Test
    void testDeleteById() {
        servis.deleteById(1L);

        verify(repo).deleteById(1L);
    }
}
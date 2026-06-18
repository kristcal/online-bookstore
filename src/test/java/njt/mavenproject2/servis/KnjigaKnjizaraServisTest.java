package njt.mavenproject2.servis;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import njt.mavenproject2.dto.impl.KnjigaKnjizaraDto;
import njt.mavenproject2.entity.impl.Knjiga;
import njt.mavenproject2.entity.impl.KnjigaKnjizara;
import njt.mavenproject2.entity.impl.Knjizara;
import njt.mavenproject2.mapper.impl.KnjigaKnjizaraMapper;
import njt.mavenproject2.repository.impl.KnjigaKnjizaraRepository;
import njt.mavenproject2.repository.impl.KnjigaRepository;
import njt.mavenproject2.repository.impl.KnjizaraRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test klasa za proveru funkcionalnosti klase {@link KnjigaKnjizaraServis}.
 *
 * Testira prikaz dostupnosti knjige po knjižarama, dodavanje knjige u ponudu,
 * izmenu količine, obradu grešaka i uklanjanje veze između knjige i knjižare.
 *
 * @author Korisnik
 */
class KnjigaKnjizaraServisTest {

    /**
     * Mock repozitorijum za veze između knjiga i knjižara.
     */
    private KnjigaKnjizaraRepository repo;

    /**
     * Mock repozitorijum knjiga.
     */
    private KnjigaRepository knjigaRepo;

    /**
     * Mock repozitorijum knjižara.
     */
    private KnjizaraRepository knjizaraRepo;

    /**
     * Mock mapper za konverziju veze knjiga-knjižara.
     */
    private KnjigaKnjizaraMapper mapper;

    /**
     * Servis koji se testira.
     */
    private KnjigaKnjizaraServis servis;

    /**
     * Inicijalizuje mock objekte pre svakog testa.
     */
    @BeforeEach
    void setUp() {
        repo = mock(KnjigaKnjizaraRepository.class);
        knjigaRepo = mock(KnjigaRepository.class);
        knjizaraRepo = mock(KnjizaraRepository.class);
        mapper = mock(KnjigaKnjizaraMapper.class);

        servis = new KnjigaKnjizaraServis(repo, knjigaRepo, knjizaraRepo, mapper);
    }

    /**
     * Proverava uspešno pronalaženje dostupnosti za određenu knjigu.
     */
    @Test
    void testListForKnjiga() {
        KnjigaKnjizara kk = new KnjigaKnjizara();

        KnjigaKnjizaraDto dto = new KnjigaKnjizaraDto();
        dto.setId(1L);

        when(repo.findByKnjigaId(10L)).thenReturn(List.of(kk));
        when(mapper.toDo(kk)).thenReturn(dto);

        List<KnjigaKnjizaraDto> rezultat = servis.listForKnjiga(10L);

        assertEquals(1, rezultat.size());
        assertEquals(1L, rezultat.get(0).getId());

        verify(repo).findByKnjigaId(10L);
        verify(mapper).toDo(kk);
    }

    /**
     * Proverava uspešno dodavanje knjige u ponudu knjižare.
     *
     * @throws Exception ukoliko knjiga ili knjižara nisu pronađene
     */
    @Test
    void testAddOrSet() throws Exception {
        Knjiga knjiga = new Knjiga();
        knjiga.setId(10L);

        Knjizara knjizara = new Knjizara();
        knjizara.setId(5L);

        KnjigaKnjizaraDto dto = new KnjigaKnjizaraDto();
        dto.setKnjizaraId(5L);
        dto.setKolicina(12);

        KnjigaKnjizaraDto rezultatDto = new KnjigaKnjizaraDto();
        rezultatDto.setKnjizaraId(5L);
        rezultatDto.setKolicina(12);

        when(knjigaRepo.findById(10L)).thenReturn(knjiga);
        when(knjizaraRepo.findById(5L)).thenReturn(knjizara);
        when(mapper.toDo(any(KnjigaKnjizara.class))).thenReturn(rezultatDto);

        KnjigaKnjizaraDto rezultat = servis.addOrSet(10L, dto);

        assertNotNull(rezultat);
        assertEquals(5L, rezultat.getKnjizaraId());
        assertEquals(12, rezultat.getKolicina());

        verify(knjigaRepo).findById(10L);
        verify(knjizaraRepo).findById(5L);
        verify(repo).save(any(KnjigaKnjizara.class));
        verify(mapper).toDo(any(KnjigaKnjizara.class));
    }

    /**
     * Proverava ponašanje sistema kada knjiga ne postoji.
     *
     * @throws Exception očekivani izuzetak iz repozitorijuma knjiga
     */
    @Test
    void testAddOrSetKnjigaNePostoji() throws Exception {
        KnjigaKnjizaraDto dto = new KnjigaKnjizaraDto();
        dto.setKnjizaraId(5L);

        when(knjigaRepo.findById(10L))
                .thenThrow(new Exception("Knjiga nije pronadjena!"));

        Exception e = assertThrows(Exception.class,
                () -> servis.addOrSet(10L, dto));

        assertEquals("Knjiga nije pronadjena!", e.getMessage());

        verify(knjigaRepo).findById(10L);
        verify(knjizaraRepo, never()).findById(anyLong());
        verify(repo, never()).save(any());
    }

    /**
     * Proverava ponašanje sistema kada knjižara ne postoji.
     *
     * @throws Exception očekivani izuzetak iz repozitorijuma knjižara
     */
    @Test
    void testAddOrSetKnjizaraNePostoji() throws Exception {
        Knjiga knjiga = new Knjiga();
        knjiga.setId(10L);

        KnjigaKnjizaraDto dto = new KnjigaKnjizaraDto();
        dto.setKnjizaraId(5L);

        when(knjigaRepo.findById(10L)).thenReturn(knjiga);
        when(knjizaraRepo.findById(5L))
                .thenThrow(new Exception("Knjižara nije pronađena!"));

        Exception e = assertThrows(Exception.class,
                () -> servis.addOrSet(10L, dto));

        assertEquals("Knjižara nije pronađena!", e.getMessage());

        verify(knjigaRepo).findById(10L);
        verify(knjizaraRepo).findById(5L);
        verify(repo, never()).save(any());
    }

    /**
     * Proverava uspešno ažuriranje količine knjige u knjižari.
     *
     * @throws Exception ukoliko veza između knjige i knjižare nije pronađena
     */
    @Test
    void testUpdateKolicina() throws Exception {
        KnjigaKnjizara kk = new KnjigaKnjizara();
        kk.setId(1L);
        kk.setKolicina(3);

        KnjigaKnjizaraDto rezultatDto = new KnjigaKnjizaraDto();
        rezultatDto.setId(1L);
        rezultatDto.setKolicina(20);

        when(repo.findById(1L)).thenReturn(kk);
        when(mapper.toDo(kk)).thenReturn(rezultatDto);

        KnjigaKnjizaraDto rezultat = servis.updateKolicina(1L, 20);

        assertNotNull(rezultat);
        assertEquals(20, kk.getKolicina());
        assertEquals(20, rezultat.getKolicina());

        verify(repo).findById(1L);
        verify(repo).save(kk);
        verify(mapper).toDo(kk);
    }

    /**
     * Proverava ponašanje sistema kada veza između knjige i knjižare ne postoji.
     *
     * @throws Exception očekivani izuzetak iz repozitorijuma
     */
    @Test
    void testUpdateKolicinaNePostoji() throws Exception {
        when(repo.findById(999L))
                .thenThrow(new Exception("Veza knjige i knjižare nije pronađena!"));

        Exception e = assertThrows(Exception.class,
                () -> servis.updateKolicina(999L, 20));

        assertEquals("Veza knjige i knjižare nije pronađena!", e.getMessage());

        verify(repo).findById(999L);
        verify(repo, never()).save(any());
    }

    /**
     * Proverava uspešno uklanjanje veze između knjige i knjižare.
     */
    @Test
    void testRemove() {
        servis.remove(1L);

        verify(repo).deleteById(1L);
    }
}
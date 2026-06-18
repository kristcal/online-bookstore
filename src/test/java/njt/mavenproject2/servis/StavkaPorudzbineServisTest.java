package njt.mavenproject2.servis;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import njt.mavenproject2.dto.impl.StavkaPorudzbineDto;
import njt.mavenproject2.entity.impl.Knjiga;
import njt.mavenproject2.entity.impl.Porudzbina;
import njt.mavenproject2.entity.impl.StavkaPorudzbine;
import njt.mavenproject2.mapper.impl.StavkaPorudzbineMapper;
import njt.mavenproject2.repository.impl.KnjigaRepository;
import njt.mavenproject2.repository.impl.PorudzbinaRepository;
import njt.mavenproject2.repository.impl.StavkaPorudzbineRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test klasa za proveru funkcionalnosti klase {@link StavkaPorudzbineServis}.
 *
 * Testira prikaz, dodavanje, izmenu i brisanje stavki porudžbine.
 * Posebno proverava automatsku dodelu rednog broja stavke i ponovno
 * računanje ukupnog iznosa porudžbine nakon izmene stavki.
 *
 * @author Korisnik
 */
class StavkaPorudzbineServisTest {

    /**
     * Mock repozitorijum stavki porudžbine.
     */
    private StavkaPorudzbineRepository repo;

    /**
     * Mock repozitorijum porudžbina.
     */
    private PorudzbinaRepository porRepo;

    /**
     * Mock repozitorijum knjiga.
     */
    private KnjigaRepository knjigaRepo;

    /**
     * Mock mapper za konverziju stavki porudžbine.
     */
    private StavkaPorudzbineMapper mapper;

    /**
     * Servis koji se testira.
     */
    private StavkaPorudzbineServis servis;

    /**
     * Inicijalizuje mock objekte pre svakog testa.
     */
    @BeforeEach
    void setUp() {
        repo = mock(StavkaPorudzbineRepository.class);
        porRepo = mock(PorudzbinaRepository.class);
        knjigaRepo = mock(KnjigaRepository.class);
        mapper = mock(StavkaPorudzbineMapper.class);

        servis = new StavkaPorudzbineServis(repo, porRepo, knjigaRepo, mapper);
    }

    /**
     * Proverava uspešno pronalaženje stavki određene porudžbine.
     */
    @Test
    void testListForPorudzbina() {
        StavkaPorudzbine stavka = new StavkaPorudzbine();

        StavkaPorudzbineDto dto = new StavkaPorudzbineDto();
        dto.setId(1L);

        when(repo.findByPorudzbinaId(10L)).thenReturn(List.of(stavka));
        when(mapper.toDo(stavka)).thenReturn(dto);

        List<StavkaPorudzbineDto> rezultat = servis.listForPorudzbina(10L);

        assertEquals(1, rezultat.size());
        assertEquals(1L, rezultat.get(0).getId());

        verify(repo).findByPorudzbinaId(10L);
        verify(mapper).toDo(stavka);
    }

    /**
     * Proverava uspešno dodavanje stavke u porudžbinu.
     *
     * @throws Exception ukoliko porudžbina ili knjiga nisu pronađene
     */
    @Test
    void testAdd() throws Exception {
        Porudzbina porudzbina = new Porudzbina();
        porudzbina.setId(10L);

        Knjiga knjiga = new Knjiga();
        knjiga.setId(5L);

        StavkaPorudzbineDto dto = new StavkaPorudzbineDto();
        dto.setKnjigaId(5L);
        dto.setRb(2);
        dto.setKolicina(3);
        dto.setCenaK(1000.0);

        StavkaPorudzbine stavka = new StavkaPorudzbine();
        stavka.setRb(2);
        stavka.setKolicina(3);
        stavka.setCenaK(1000.0);

        StavkaPorudzbine postojeca = new StavkaPorudzbine();
        postojeca.setKolicina(3);
        postojeca.setCenaK(1000.0);

        StavkaPorudzbineDto rezultatDto = new StavkaPorudzbineDto();
        rezultatDto.setRb(2);

        when(porRepo.findById(10L)).thenReturn(porudzbina);
        when(knjigaRepo.findById(5L)).thenReturn(knjiga);
        when(mapper.toEntity(dto)).thenReturn(stavka);
        when(repo.findByPorudzbinaId(10L)).thenReturn(List.of(postojeca));
        when(mapper.toDo(stavka)).thenReturn(rezultatDto);

        StavkaPorudzbineDto rezultat = servis.add(10L, dto);

        assertNotNull(rezultat);
        assertEquals(2, stavka.getRb());
        assertEquals(porudzbina, stavka.getPorudzbina());
        assertEquals(knjiga, stavka.getKnjiga());
        assertEquals(3000.0, porudzbina.getUkupanIznos());

        verify(repo).save(stavka);
        verify(porRepo).save(porudzbina);
    }

    /**
     * Proverava automatsku dodelu rednog broja ako nije prosleđen.
     *
     * @throws Exception ukoliko porudžbina ili knjiga nisu pronađene
     */
    @Test
    void testAddDodeljujeRbAkoNijePoslat() throws Exception {
        Porudzbina porudzbina = new Porudzbina();
        porudzbina.setId(10L);

        Knjiga knjiga = new Knjiga();
        knjiga.setId(5L);

        StavkaPorudzbineDto dto = new StavkaPorudzbineDto();
        dto.setKnjigaId(5L);

        StavkaPorudzbine stavka = new StavkaPorudzbine();
        stavka.setRb(null);
        stavka.setKolicina(1);
        stavka.setCenaK(500.0);

        when(porRepo.findById(10L)).thenReturn(porudzbina);
        when(knjigaRepo.findById(5L)).thenReturn(knjiga);
        when(mapper.toEntity(dto)).thenReturn(stavka);
        when(repo.findMaxRbForPorudzbina(10L)).thenReturn(3);
        when(repo.findByPorudzbinaId(10L)).thenReturn(List.of(stavka));
        when(mapper.toDo(stavka)).thenReturn(new StavkaPorudzbineDto());

        servis.add(10L, dto);

        assertEquals(4, stavka.getRb());
        verify(repo).findMaxRbForPorudzbina(10L);
    }

    /**
     * Proverava ponašanje sistema kada porudžbina ne postoji.
     *
     * @throws Exception očekivani izuzetak iz repozitorijuma porudžbina
     */
    @Test
    void testAddPorudzbinaNePostoji() throws Exception {
        StavkaPorudzbineDto dto = new StavkaPorudzbineDto();

        when(porRepo.findById(10L))
                .thenThrow(new Exception("Porudžbina nije pronađena!"));

        Exception e = assertThrows(Exception.class,
                () -> servis.add(10L, dto));

        assertEquals("Porudžbina nije pronađena!", e.getMessage());
        verify(repo, never()).save(any());
    }

    /**
     * Proverava ponašanje sistema kada knjiga ne postoji.
     *
     * @throws Exception očekivani izuzetak iz repozitorijuma knjiga
     */
    @Test
    void testAddKnjigaNePostoji() throws Exception {
        Porudzbina porudzbina = new Porudzbina();
        StavkaPorudzbineDto dto = new StavkaPorudzbineDto();
        dto.setKnjigaId(5L);

        when(porRepo.findById(10L)).thenReturn(porudzbina);
        when(knjigaRepo.findById(5L))
                .thenThrow(new Exception("Knjiga nije pronadjena!"));

        Exception e = assertThrows(Exception.class,
                () -> servis.add(10L, dto));

        assertEquals("Knjiga nije pronadjena!", e.getMessage());
        verify(repo, never()).save(any());
    }

    /**
     * Proverava uspešno ažuriranje stavke porudžbine.
     *
     * @throws Exception ukoliko stavka nije pronađena
     */
    @Test
    void testUpdate() throws Exception {
        Porudzbina porudzbina = new Porudzbina();
        porudzbina.setId(10L);

        StavkaPorudzbine stavka = new StavkaPorudzbine();
        stavka.setId(1L);
        stavka.setPorudzbina(porudzbina);
        stavka.setKolicina(1);
        stavka.setCenaK(500.0);

        StavkaPorudzbine postojeca = new StavkaPorudzbine();
        postojeca.setKolicina(2);
        postojeca.setCenaK(700.0);

        StavkaPorudzbineDto dto = new StavkaPorudzbineDto();
        dto.setKolicina(2);
        dto.setCenaK(700.0);

        when(repo.findById(1L)).thenReturn(stavka);
        when(porRepo.findById(10L)).thenReturn(porudzbina);
        when(repo.findByPorudzbinaId(10L)).thenReturn(List.of(postojeca));
        when(mapper.toDo(stavka)).thenReturn(new StavkaPorudzbineDto());

        StavkaPorudzbineDto rezultat = servis.update(1L, dto);

        assertNotNull(rezultat);
        assertEquals(2, stavka.getKolicina());
        assertEquals(700.0, stavka.getCenaK());
        assertEquals(1400.0, porudzbina.getUkupanIznos());

        verify(repo).save(stavka);
        verify(porRepo).save(porudzbina);
    }

    /**
     * Proverava promenu knjige na stavki porudžbine.
     *
     * @throws Exception ukoliko stavka ili knjiga nisu pronađene
     */
    @Test
    void testUpdateMenjaKnjigu() throws Exception {
        Porudzbina porudzbina = new Porudzbina();
        porudzbina.setId(10L);

        StavkaPorudzbine stavka = new StavkaPorudzbine();
        stavka.setId(1L);
        stavka.setPorudzbina(porudzbina);

        Knjiga novaKnjiga = new Knjiga();
        novaKnjiga.setId(5L);

        StavkaPorudzbineDto dto = new StavkaPorudzbineDto();
        dto.setKnjigaId(5L);

        when(repo.findById(1L)).thenReturn(stavka);
        when(knjigaRepo.findById(5L)).thenReturn(novaKnjiga);
        when(porRepo.findById(10L)).thenReturn(porudzbina);
        when(repo.findByPorudzbinaId(10L)).thenReturn(List.of());
        when(mapper.toDo(stavka)).thenReturn(new StavkaPorudzbineDto());

        servis.update(1L, dto);

        assertEquals(novaKnjiga, stavka.getKnjiga());
        verify(knjigaRepo).findById(5L);
    }

    /**
     * Proverava ponašanje sistema kada stavka ne postoji.
     *
     * @throws Exception očekivani izuzetak iz repozitorijuma stavki
     */
    @Test
    void testUpdateNePostoji() throws Exception {
        when(repo.findById(1L))
                .thenThrow(new Exception("Stavka nije pronađena!"));

        Exception e = assertThrows(Exception.class,
                () -> servis.update(1L, new StavkaPorudzbineDto()));

        assertEquals("Stavka nije pronađena!", e.getMessage());
        verify(repo).findById(1L);
    }

    /**
     * Proverava uspešno uklanjanje stavke porudžbine.
     *
     * @throws Exception ukoliko stavka ili porudžbina nisu pronađene
     */
    @Test
    void testRemove() throws Exception {
        Porudzbina porudzbina = new Porudzbina();
        porudzbina.setId(10L);

        StavkaPorudzbine stavka = new StavkaPorudzbine();
        stavka.setId(1L);
        stavka.setPorudzbina(porudzbina);

        when(repo.findById(1L)).thenReturn(stavka);
        when(porRepo.findById(10L)).thenReturn(porudzbina);
        when(repo.findByPorudzbinaId(10L)).thenReturn(List.of());

        servis.remove(1L);

        verify(repo).deleteById(1L);
        verify(porRepo).save(porudzbina);
        assertEquals(0.0, porudzbina.getUkupanIznos());
    }

    /**
     * Proverava da uklanjanje nepostojeće stavke ne baca grešku.
     *
     * @throws Exception očekivani izuzetak iz repozitorijuma koji se ignoriše
     */
    @Test
    void testRemoveNePostojiNeBacaGresku() throws Exception {
        when(repo.findById(1L))
                .thenThrow(new Exception("Stavka nije pronađena!"));

        assertDoesNotThrow(() -> servis.remove(1L));

        verify(repo, never()).deleteById(anyLong());
    }
}
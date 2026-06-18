package njt.mavenproject2.servis;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import njt.mavenproject2.dto.impl.PorudzbinaDto;
import njt.mavenproject2.entity.impl.Knjiga;
import njt.mavenproject2.entity.impl.KnjigaKnjizara;
import njt.mavenproject2.entity.impl.Korisnik;
import njt.mavenproject2.entity.impl.Porudzbina;
import njt.mavenproject2.entity.impl.StavkaPorudzbine;
import njt.mavenproject2.mapper.impl.PorudzbinaMapper;
import njt.mavenproject2.repository.impl.KnjigaKnjizaraRepository;
import njt.mavenproject2.repository.impl.KnjigaRepository;
import njt.mavenproject2.repository.impl.KorisnikRepository;
import njt.mavenproject2.repository.impl.PorudzbinaRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test klasa za proveru funkcionalnosti klase {@link PorudzbinaServis}.
 *
 * Testira pronalaženje, kreiranje, izmenu, brisanje i promenu statusa
 * porudžbina. Posebno proverava obračun ukupnog iznosa porudžbine,
 * rad sa stavkama, proveru dostupnosti knjiga i skidanje zaliha prilikom
 * obrade porudžbine.
 *
 * @author Korisnik
 */
class PorudzbinaServisTest {

    /**
     * Mock repozitorijum porudžbina.
     */
    private PorudzbinaRepository repo;

    /**
     * Mock repozitorijum korisnika.
     */
    private KorisnikRepository korisnikRepo;

    /**
     * Mock repozitorijum knjiga.
     */
    private KnjigaRepository knjigaRepo;

    /**
     * Mock mapper za konverziju porudžbina.
     */
    private PorudzbinaMapper mapper;

    /**
     * Mock repozitorijum dostupnosti knjiga u knjižarama.
     */
    private KnjigaKnjizaraRepository kkRepo;

    /**
     * Servis koji se testira.
     */
    private PorudzbinaServis servis;

    /**
     * Inicijalizuje mock objekte pre svakog testa.
     */
    @BeforeEach
    void setUp() {
        repo = mock(PorudzbinaRepository.class);
        korisnikRepo = mock(KorisnikRepository.class);
        knjigaRepo = mock(KnjigaRepository.class);
        mapper = mock(PorudzbinaMapper.class);
        kkRepo = mock(KnjigaKnjizaraRepository.class);

        servis = new PorudzbinaServis(repo, korisnikRepo, knjigaRepo, mapper, kkRepo);
    }

    /**
     * Proverava uspešno pronalaženje svih porudžbina.
     */
    @Test
    void testFindAll() {
        Porudzbina p1 = new Porudzbina();
        p1.setId(1L);

        Porudzbina p2 = new Porudzbina();
        p2.setId(2L);

        PorudzbinaDto dto1 = new PorudzbinaDto();
        dto1.setId(1L);

        PorudzbinaDto dto2 = new PorudzbinaDto();
        dto2.setId(2L);

        when(repo.findAll()).thenReturn(List.of(p1, p2));
        when(mapper.toDo(p1)).thenReturn(dto1);
        when(mapper.toDo(p2)).thenReturn(dto2);

        List<PorudzbinaDto> rezultat = servis.findAll();

        assertEquals(2, rezultat.size());
        assertTrue(rezultat.contains(dto1));
        assertTrue(rezultat.contains(dto2));

        verify(repo).findAll();
        verify(mapper).toDo(p1);
        verify(mapper).toDo(p2);
    }

    /**
     * Proverava uspešno pronalaženje porudžbine prema identifikatoru.
     *
     * @throws Exception ukoliko porudžbina nije pronađena
     */
    @Test
    void testFindById() throws Exception {
        Porudzbina p = new Porudzbina();
        p.setId(1L);

        PorudzbinaDto dto = new PorudzbinaDto();
        dto.setId(1L);

        when(repo.findById(1L)).thenReturn(p);
        when(mapper.toDo(p)).thenReturn(dto);

        PorudzbinaDto rezultat = servis.findById(1L);

        assertNotNull(rezultat);
        assertEquals(1L, rezultat.getId());

        verify(repo).findById(1L);
        verify(mapper).toDo(p);
    }

    /**
     * Proverava ponašanje sistema kada porudžbina ne postoji.
     *
     * @throws Exception očekivani izuzetak iz repozitorijuma
     */
    @Test
    void testFindByIdNePostoji() throws Exception {
        when(repo.findById(999L)).thenThrow(new Exception("Porudžbina nije pronađena!"));

        Exception e = assertThrows(Exception.class,
                () -> servis.findById(999L));

        assertEquals("Porudžbina nije pronađena!", e.getMessage());

        verify(repo).findById(999L);
        verify(mapper, never()).toDo(any());
    }

    /**
     * Proverava pronalaženje porudžbina određenog korisnika.
     */
    @Test
    void testFindByKorisnik() {
        Porudzbina p = new Porudzbina();
        p.setId(1L);

        PorudzbinaDto dto = new PorudzbinaDto();
        dto.setId(1L);

        when(repo.findByKorisnikId(10L)).thenReturn(List.of(p));
        when(mapper.toDo(p)).thenReturn(dto);

        List<PorudzbinaDto> rezultat = servis.findByKorisnik(10L);

        assertEquals(1, rezultat.size());
        assertEquals(1L, rezultat.get(0).getId());

        verify(repo).findByKorisnikId(10L);
        verify(mapper).toDo(p);
    }

    /**
     * Proverava uspešno brisanje porudžbine.
     */
    @Test
    void testDeleteById() {
        servis.deleteById(1L);

        verify(repo).deleteById(1L);
    }

    /**
     * Proverava uspešno kreiranje porudžbine.
     *
     * @throws Exception ukoliko korisnik ili knjiga nisu pronađeni
     */
    @Test
    void testCreate() throws Exception {
        Korisnik korisnik = new Korisnik();
        korisnik.setId(1L);

        Knjiga knjiga = new Knjiga();
        knjiga.setId(10L);
        knjiga.setCena(1000.0);

        PorudzbinaDto.Stavka stavka = new PorudzbinaDto.Stavka();
        stavka.setKnjigaId(10L);
        stavka.setKolicina(2);
        stavka.setCena(1000.0);

        PorudzbinaDto dto = new PorudzbinaDto();
        dto.setKorisnikId(1L);
        dto.setStavke(List.of(stavka));

        PorudzbinaDto rezultatDto = new PorudzbinaDto();
        rezultatDto.setKorisnikId(1L);
        rezultatDto.setUkupanIznos(2000.0);

        when(korisnikRepo.findById(1L)).thenReturn(korisnik);
        when(knjigaRepo.findById(10L)).thenReturn(knjiga);
        when(mapper.toDo(any(Porudzbina.class))).thenReturn(rezultatDto);

        PorudzbinaDto rezultat = servis.create(dto);

        assertNotNull(rezultat);
        assertEquals(1L, rezultat.getKorisnikId());
        assertEquals(2000.0, rezultat.getUkupanIznos());

        verify(korisnikRepo).findById(1L);
        verify(knjigaRepo).findById(10L);
        verify(repo).save(any(Porudzbina.class));
        verify(mapper).toDo(any(Porudzbina.class));
    }

    /**
     * Proverava ponašanje sistema kada se kreira porudžbina bez korisnika
     * ili bez stavki.
     */
    @Test
    void testCreateBezKorisnika() {
        PorudzbinaDto dto = new PorudzbinaDto();

        IllegalArgumentException e = assertThrows(
                IllegalArgumentException.class,
                () -> servis.create(dto));

        assertEquals("korisnik_id i stavke su obavezni", e.getMessage());

        verify(repo, never()).save(any());
    }

    /**
     * Proverava uspešnu promenu statusa porudžbine.
     *
     * @throws Exception ukoliko promena statusa nije moguća
     */
    @Test
    void testPromeniStatus() throws Exception {
        Porudzbina p = new Porudzbina();
        p.setId(1L);
        p.setStatus("KREIRANA");

        PorudzbinaDto dto = new PorudzbinaDto();
        dto.setStatus("OBRADJENA");

        when(repo.findById(1L)).thenReturn(p);
        when(mapper.toDo(p)).thenReturn(dto);

        PorudzbinaDto rezultat = servis.promeniStatus(1L, "OBRADJENA");

        assertNotNull(rezultat);
        assertEquals("OBRADJENA", p.getStatus());

        verify(repo).findById(1L);
        verify(repo).save(p);
        verify(mapper).toDo(p);
    }

    /**
     * Proverava da se već obrađena porudžbina ne obrađuje ponovo.
     *
     * @throws Exception ukoliko dođe do greške pri promeni statusa
     */
    @Test
    void testPromeniStatusVecObradjena() throws Exception {
        Porudzbina p = new Porudzbina();
        p.setId(1L);
        p.setStatus("OBRADJENA");

        PorudzbinaDto dto = new PorudzbinaDto();
        dto.setStatus("OBRADJENA");

        when(repo.findById(1L)).thenReturn(p);
        when(mapper.toDo(p)).thenReturn(dto);

        PorudzbinaDto rezultat = servis.promeniStatus(1L, "OBRADJENA");

        assertNotNull(rezultat);

        verify(repo).findById(1L);
        verify(repo, never()).save(any());
        verify(mapper).toDo(p);
    }

    /**
     * Proverava uspešno ažuriranje porudžbine.
     *
     * @throws Exception ukoliko porudžbina, korisnik ili knjiga nisu pronađeni
     */
    @Test
    void testUpdate() throws Exception {
        Korisnik korisnik = new Korisnik();
        korisnik.setId(2L);

        Knjiga knjiga = new Knjiga();
        knjiga.setId(10L);
        knjiga.setCena(500.0);

        Porudzbina postojeca = new Porudzbina();
        postojeca.setId(1L);

        PorudzbinaDto.Stavka stavka = new PorudzbinaDto.Stavka();
        stavka.setKnjigaId(10L);
        stavka.setKolicina(2);
        stavka.setCena(500.0);

        PorudzbinaDto dto = new PorudzbinaDto();
        dto.setKorisnikId(2L);
        dto.setStavke(List.of(stavka));

        PorudzbinaDto rezultatDto = new PorudzbinaDto();
        rezultatDto.setKorisnikId(2L);

        when(repo.findById(1L)).thenReturn(postojeca);
        when(korisnikRepo.findById(2L)).thenReturn(korisnik);
        when(knjigaRepo.findById(10L)).thenReturn(knjiga);
        when(mapper.toDo(postojeca)).thenReturn(rezultatDto);

        PorudzbinaDto rezultat = servis.update(1L, dto);

        assertNotNull(rezultat);
        assertEquals(2L, rezultat.getKorisnikId());

        verify(repo).findById(1L);
        verify(repo).save(postojeca);
        verify(mapper).toDo(postojeca);
    }

    /**
     * Proverava ponašanje sistema kada porudžbina za ažuriranje ne postoji.
     *
     * @throws Exception očekivani izuzetak iz repozitorijuma
     */
    @Test
    void testUpdateNePostoji() throws Exception {
        when(repo.findById(999L))
                .thenThrow(new Exception("Porudžbina ne postoji"));

        Exception e = assertThrows(
                Exception.class,
                () -> servis.update(999L, new PorudzbinaDto()));

        assertEquals("Porudžbina ne postoji", e.getMessage());

        verify(repo).findById(999L);
        verify(repo, never()).save(any());
    }

    /**
     * Proverava ponašanje sistema kada korisnik pri kreiranju ne postoji.
     *
     * @throws Exception ukoliko korisnik nije pronađen
     */
    @Test
    void testCreateKorisnikNePostoji() throws Exception {
        PorudzbinaDto.Stavka stavka = new PorudzbinaDto.Stavka();
        stavka.setKnjigaId(10L);
        stavka.setKolicina(1);
        stavka.setCena(1000.0);

        PorudzbinaDto dto = new PorudzbinaDto();
        dto.setKorisnikId(1L);
        dto.setStavke(List.of(stavka));

        when(korisnikRepo.findById(1L)).thenReturn(null);

        Exception e = assertThrows(Exception.class,
                () -> servis.create(dto));

        assertEquals("Korisnik ne postoji", e.getMessage());

        verify(repo, never()).save(any());
    }

    /**
     * Proverava ponašanje sistema kada knjiga pri kreiranju ne postoji.
     *
     * @throws Exception ukoliko knjiga nije pronađena
     */
    @Test
    void testCreateKnjigaNePostoji() throws Exception {
        Korisnik korisnik = new Korisnik();
        korisnik.setId(1L);

        PorudzbinaDto.Stavka stavka = new PorudzbinaDto.Stavka();
        stavka.setKnjigaId(10L);
        stavka.setKolicina(1);
        stavka.setCena(1000.0);

        PorudzbinaDto dto = new PorudzbinaDto();
        dto.setKorisnikId(1L);
        dto.setStavke(List.of(stavka));

        when(korisnikRepo.findById(1L)).thenReturn(korisnik);
        when(knjigaRepo.findById(10L)).thenReturn(null);

        Exception e = assertThrows(Exception.class,
                () -> servis.create(dto));

        assertEquals("Knjiga ne postoji: id=10", e.getMessage());

        verify(repo, never()).save(any());
    }

    /**
     * Proverava ponašanje sistema kada korisnik pri ažuriranju ne postoji.
     *
     * @throws Exception ukoliko korisnik nije pronađen
     */
    @Test
    void testUpdateKorisnikNePostoji() throws Exception {
        Porudzbina p = new Porudzbina();
        p.setId(1L);

        PorudzbinaDto dto = new PorudzbinaDto();
        dto.setKorisnikId(2L);

        when(repo.findById(1L)).thenReturn(p);
        when(korisnikRepo.findById(2L)).thenReturn(null);

        Exception e = assertThrows(Exception.class,
                () -> servis.update(1L, dto));

        assertEquals("Korisnik ne postoji", e.getMessage());

        verify(repo, never()).save(any());
    }

    /**
     * Proverava ponašanje sistema kada knjiga pri ažuriranju ne postoji.
     *
     * @throws Exception ukoliko knjiga nije pronađena
     */
    @Test
    void testUpdateKnjigaNePostoji() throws Exception {
        Porudzbina p = new Porudzbina();
        p.setId(1L);

        PorudzbinaDto.Stavka stavka = new PorudzbinaDto.Stavka();
        stavka.setKnjigaId(10L);
        stavka.setKolicina(1);
        stavka.setCena(1000.0);

        PorudzbinaDto dto = new PorudzbinaDto();
        dto.setStavke(List.of(stavka));

        when(repo.findById(1L)).thenReturn(p);
        when(knjigaRepo.findById(10L)).thenReturn(null);

        Exception e = assertThrows(Exception.class,
                () -> servis.update(1L, dto));

        assertEquals("Knjiga ne postoji: id=10", e.getMessage());

        verify(repo, never()).save(any());
    }

    /**
     * Proverava skidanje zaliha kada se status porudžbine promeni u OBRADJENA.
     *
     * @throws Exception ukoliko nema dovoljno zaliha
     */
    @Test
    void testPromeniStatusSkidaZalihe() throws Exception {
        Knjiga knjiga = new Knjiga();
        knjiga.setId(10L);
        knjiga.setNaziv("1984");

        StavkaPorudzbine stavka = new StavkaPorudzbine();
        stavka.setKnjiga(knjiga);
        stavka.setKolicina(2);

        Porudzbina p = new Porudzbina();
        p.setId(1L);
        p.setStatus("KREIRANA");
        p.setStavke(List.of(stavka));

        KnjigaKnjizara kk = new KnjigaKnjizara();
        kk.setKolicina(5);

        PorudzbinaDto dto = new PorudzbinaDto();
        dto.setStatus("OBRADJENA");

        when(repo.findById(1L)).thenReturn(p);
        when(kkRepo.findByKnjigaIdForUpdate(10L)).thenReturn(List.of(kk));
        when(mapper.toDo(p)).thenReturn(dto);

        PorudzbinaDto rezultat = servis.promeniStatus(1L, "OBRADJENA");

        assertNotNull(rezultat);
        assertEquals("OBRADJENA", p.getStatus());
        assertEquals(3, kk.getKolicina());

        verify(kkRepo).save(kk);
        verify(repo).save(p);
    }

    /**
     * Proverava ponašanje sistema kada nema dovoljno knjiga na stanju.
     *
     * @throws Exception očekivani izuzetak zbog nedovoljnih zaliha
     */
    @Test
    void testPromeniStatusNemaDovoljnoNaStanju() throws Exception {
        Knjiga knjiga = new Knjiga();
        knjiga.setId(10L);
        knjiga.setNaziv("1984");

        StavkaPorudzbine stavka = new StavkaPorudzbine();
        stavka.setKnjiga(knjiga);
        stavka.setKolicina(5);

        Porudzbina p = new Porudzbina();
        p.setId(1L);
        p.setStatus("KREIRANA");
        p.setStavke(List.of(stavka));

        KnjigaKnjizara kk = new KnjigaKnjizara();
        kk.setKolicina(2);

        when(repo.findById(1L)).thenReturn(p);
        when(kkRepo.findByKnjigaIdForUpdate(10L)).thenReturn(List.of(kk));

        Exception e = assertThrows(Exception.class,
                () -> servis.promeniStatus(1L, "OBRADJENA"));

        assertEquals("Nema dovoljno na stanju za: 1984", e.getMessage());

        verify(repo, never()).save(any());
    }

    /**
     * Proverava da se pri kreiranju koriste podrazumevane vrednosti za
     * količinu i cenu ako nisu prosleđene.
     *
     * @throws Exception ukoliko dođe do greške pri kreiranju
     */
    @Test
    void testCreateDefaultKolicinaICena() throws Exception {
        Korisnik korisnik = new Korisnik();
        korisnik.setId(1L);

        Knjiga knjiga = new Knjiga();
        knjiga.setId(10L);
        knjiga.setCena(800.0);

        PorudzbinaDto.Stavka stavka = new PorudzbinaDto.Stavka();
        stavka.setKnjigaId(10L);
        stavka.setKolicina(null);
        stavka.setCena(null);

        PorudzbinaDto dto = new PorudzbinaDto();
        dto.setKorisnikId(1L);
        dto.setStavke(List.of(stavka));

        when(korisnikRepo.findById(1L)).thenReturn(korisnik);
        when(knjigaRepo.findById(10L)).thenReturn(knjiga);
        when(mapper.toDo(any(Porudzbina.class))).thenReturn(new PorudzbinaDto());

        servis.create(dto);

        verify(repo).save(argThat(p ->
                p.getStavke().get(0).getKolicina().equals(1)
                && p.getStavke().get(0).getCenaK().equals(800.0)
                && p.getUkupanIznos().equals(800.0)
        ));
    }

    /**
     * Proverava ponašanje sistema kada repozitorijum vrati null za porudžbinu.
     *
     * @throws Exception očekivani izuzetak zbog nepostojeće porudžbine
     */
    @Test
    void testUpdateRepoVratiNull() throws Exception {
        when(repo.findById(1L)).thenReturn(null);

        Exception e = assertThrows(Exception.class,
                () -> servis.update(1L, new PorudzbinaDto()));

        assertEquals("Porudžbina ne postoji", e.getMessage());
        verify(repo, never()).save(any());
    }

    /**
     * Proverava da se pri ažuriranju koriste podrazumevane vrednosti za
     * količinu i cenu ako nisu prosleđene.
     *
     * @throws Exception ukoliko dođe do greške pri ažuriranju
     */
    @Test
    void testUpdateDefaultKolicinaICena() throws Exception {
        Porudzbina p = new Porudzbina();
        p.setId(1L);

        Knjiga knjiga = new Knjiga();
        knjiga.setId(10L);
        knjiga.setCena(700.0);

        PorudzbinaDto.Stavka stavka = new PorudzbinaDto.Stavka();
        stavka.setKnjigaId(10L);
        stavka.setKolicina(null);
        stavka.setCena(null);

        PorudzbinaDto dto = new PorudzbinaDto();
        dto.setStavke(List.of(stavka));

        when(repo.findById(1L)).thenReturn(p);
        when(knjigaRepo.findById(10L)).thenReturn(knjiga);
        when(mapper.toDo(p)).thenReturn(new PorudzbinaDto());

        servis.update(1L, dto);

        assertEquals(1, p.getStavke().get(0).getKolicina());
        assertEquals(700.0, p.getStavke().get(0).getCenaK());
        assertEquals(700.0, p.getUkupanIznos());
    }

    /**
     * Proverava da se zalihe ne skidaju kada status nije OBRADJENA.
     *
     * @throws Exception ukoliko dođe do greške pri promeni statusa
     */
    @Test
    void testPromeniStatusNaOtkazanaNeSkidaZalihe() throws Exception {
        Porudzbina p = new Porudzbina();
        p.setId(1L);
        p.setStatus("KREIRANA");

        when(repo.findById(1L)).thenReturn(p);
        when(mapper.toDo(p)).thenReturn(new PorudzbinaDto());

        servis.promeniStatus(1L, "OTKAZANA");

        assertEquals("OTKAZANA", p.getStatus());
        verify(kkRepo, never()).findByKnjigaIdForUpdate(anyLong());
        verify(repo).save(p);
    }

    /**
     * Proverava da se pri skidanju zaliha preskače stavka bez knjige.
     *
     * @throws Exception ukoliko dođe do greške pri promeni statusa
     */
    @Test
    void testPromeniStatusPreskaceStavkuBezKnjige() throws Exception {
        StavkaPorudzbine stavka = new StavkaPorudzbine();
        stavka.setKnjiga(null);
        stavka.setKolicina(2);

        Porudzbina p = new Porudzbina();
        p.setId(1L);
        p.setStatus("KREIRANA");
        p.setStavke(List.of(stavka));

        when(repo.findById(1L)).thenReturn(p);
        when(mapper.toDo(p)).thenReturn(new PorudzbinaDto());

        servis.promeniStatus(1L, "OBRADJENA");

        verify(kkRepo, never()).findByKnjigaIdForUpdate(anyLong());
        verify(repo).save(p);
    }

    /**
     * Proverava da se pri skidanju zaliha preskače stavka sa nultom količinom.
     *
     * @throws Exception ukoliko dođe do greške pri promeni statusa
     */
    @Test
    void testPromeniStatusPreskaceStavkuSaNultomKolicinom() throws Exception {
        Knjiga knjiga = new Knjiga();
        knjiga.setId(10L);

        StavkaPorudzbine stavka = new StavkaPorudzbine();
        stavka.setKnjiga(knjiga);
        stavka.setKolicina(0);

        Porudzbina p = new Porudzbina();
        p.setId(1L);
        p.setStatus("KREIRANA");
        p.setStavke(List.of(stavka));

        when(repo.findById(1L)).thenReturn(p);
        when(mapper.toDo(p)).thenReturn(new PorudzbinaDto());

        servis.promeniStatus(1L, "OBRADJENA");

        verify(kkRepo, never()).findByKnjigaIdForUpdate(anyLong());
        verify(repo).save(p);
    }

    /**
     * Proverava da se zaliha sa nula knjiga na stanju preskače
     * i da se baca greška ako nema dovoljno zaliha.
     *
     * @throws Exception očekivani izuzetak zbog nedovoljnih zaliha
     */
    @Test
    void testPromeniStatusPreskaceZalihuSaNulaNaStanju() throws Exception {
        Knjiga knjiga = new Knjiga();
        knjiga.setId(10L);
        knjiga.setNaziv("1984");

        StavkaPorudzbine stavka = new StavkaPorudzbine();
        stavka.setKnjiga(knjiga);
        stavka.setKolicina(1);

        Porudzbina p = new Porudzbina();
        p.setId(1L);
        p.setStatus("KREIRANA");
        p.setStavke(List.of(stavka));

        KnjigaKnjizara kk = new KnjigaKnjizara();
        kk.setKolicina(0);

        when(repo.findById(1L)).thenReturn(p);
        when(kkRepo.findByKnjigaIdForUpdate(10L)).thenReturn(List.of(kk));

        Exception e = assertThrows(Exception.class,
                () -> servis.promeniStatus(1L, "OBRADJENA"));

        assertEquals("Nema dovoljno na stanju za: 1984", e.getMessage());
        verify(kkRepo, never()).save(any());
    }
}
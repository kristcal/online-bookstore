package njt.mavenproject2.servis;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import njt.mavenproject2.dto.impl.PorudzbinaDto;
import njt.mavenproject2.entity.impl.Knjiga;
import njt.mavenproject2.entity.impl.Korisnik;
import njt.mavenproject2.entity.impl.Porudzbina;
import njt.mavenproject2.mapper.impl.PorudzbinaMapper;
import njt.mavenproject2.repository.impl.KnjigaKnjizaraRepository;
import njt.mavenproject2.repository.impl.KnjigaRepository;
import njt.mavenproject2.repository.impl.KorisnikRepository;
import njt.mavenproject2.repository.impl.PorudzbinaRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PorudzbinaServisTest {

    private PorudzbinaRepository repo;
    private KorisnikRepository korisnikRepo;
    private KnjigaRepository knjigaRepo;
    private PorudzbinaMapper mapper;
    private KnjigaKnjizaraRepository kkRepo;
    private PorudzbinaServis servis;

    @BeforeEach
    void setUp() {
        repo = mock(PorudzbinaRepository.class);
        korisnikRepo = mock(KorisnikRepository.class);
        knjigaRepo = mock(KnjigaRepository.class);
        mapper = mock(PorudzbinaMapper.class);
        kkRepo = mock(KnjigaKnjizaraRepository.class);

        servis = new PorudzbinaServis(repo, korisnikRepo, knjigaRepo, mapper, kkRepo);
    }

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

    @Test
    void testFindByIdNePostoji() throws Exception {
        when(repo.findById(999L)).thenThrow(new Exception("Porudžbina nije pronađena!"));

        Exception e = assertThrows(Exception.class,
                () -> servis.findById(999L));

        assertEquals("Porudžbina nije pronađena!", e.getMessage());

        verify(repo).findById(999L);
        verify(mapper, never()).toDo(any());
    }

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

    @Test
    void testDeleteById() {
        servis.deleteById(1L);

        verify(repo).deleteById(1L);
    }
    
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
    
    @Test
    void testCreateBezKorisnika() {

        PorudzbinaDto dto = new PorudzbinaDto();

        IllegalArgumentException e = assertThrows(
                IllegalArgumentException.class,
                () -> servis.create(dto));

        assertEquals("korisnik_id i stavke su obavezni", e.getMessage());

        verify(repo, never()).save(any());
    }
    
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

        // ne sme da radi save jer je već obrađena
        verify(repo, never()).save(any());

        verify(mapper).toDo(p);
    }
    
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
}
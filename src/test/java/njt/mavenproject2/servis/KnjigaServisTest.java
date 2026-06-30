package njt.mavenproject2.servis;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import njt.mavenproject2.dto.impl.KnjigaDto;
import njt.mavenproject2.entity.impl.Autor;
import njt.mavenproject2.entity.impl.Knjiga;
import njt.mavenproject2.entity.impl.Knjizara;
import njt.mavenproject2.entity.impl.Zanr;
import njt.mavenproject2.mapper.impl.KnjigaMapper;
import njt.mavenproject2.repository.impl.AutorRepository;
import njt.mavenproject2.repository.impl.KnjigaRepository;
import njt.mavenproject2.repository.impl.KnjizaraRepository;
import njt.mavenproject2.repository.impl.ZanrRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test klasa za proveru funkcionalnosti klase {@link KnjigaServis}.
 *
 * Testira pronalaženje, kreiranje, izmenu, brisanje i pretragu knjiga.
 * Posebno proverava rad sa povezanim entitetima kao što su žanr,
 * autori i dostupnost knjige u knjižarama.
 *
 * @author Korisnik
 */
class KnjigaServisTest {

    /**
     * Mock repozitorijum knjiga.
     */
    private KnjigaRepository repo;

    /**
     * Mock repozitorijum žanrova.
     */
    private ZanrRepository zanrRepo;

    /**
     * Mock repozitorijum autora.
     */
    private AutorRepository autorRepo;

    /**
     * Mock repozitorijum knjižara.
     */
    private KnjizaraRepository knjizaraRepo;

    /**
     * Mock mapper za konverziju knjiga.
     */
    private KnjigaMapper mapper;

    /**
     * Servis koji se testira.
     */
    private KnjigaServis servis;

    /**
     * Inicijalizuje mock objekte pre svakog testa.
     */
    @BeforeEach
    void setUp() {
        repo = mock(KnjigaRepository.class);
        zanrRepo = mock(ZanrRepository.class);
        autorRepo = mock(AutorRepository.class);
        knjizaraRepo = mock(KnjizaraRepository.class);
        mapper = mock(KnjigaMapper.class);

        servis = new KnjigaServis(repo, zanrRepo, mapper, autorRepo, knjizaraRepo);
    }

    /**
     * Proverava uspešno pronalaženje svih knjiga.
     */
    @Test
    void testFindAll() {
        Knjiga k1 = new Knjiga();
        k1.setId(1L);
        k1.setNaziv("1984");

        Knjiga k2 = new Knjiga();
        k2.setId(2L);
        k2.setNaziv("Na Drini cuprija");

        KnjigaDto dto1 = new KnjigaDto();
        dto1.setId(1L);
        dto1.setNaziv("1984");

        KnjigaDto dto2 = new KnjigaDto();
        dto2.setId(2L);
        dto2.setNaziv("Na Drini cuprija");

        when(repo.findAll()).thenReturn(List.of(k1, k2));
        when(mapper.toDo(k1)).thenReturn(dto1);
        when(mapper.toDo(k2)).thenReturn(dto2);

        List<KnjigaDto> rezultat = servis.findAll();

        assertEquals(2, rezultat.size());
        assertTrue(rezultat.contains(dto1));
        assertTrue(rezultat.contains(dto2));

        verify(repo).findAll();
        verify(mapper).toDo(k1);
        verify(mapper).toDo(k2);
    }

    /**
     * Proverava uspešno pronalaženje knjige prema identifikatoru.
     *
     * @throws Exception ukoliko knjiga nije pronađena
     */
    @Test
    void testFindById() throws Exception {
        Knjiga k = new Knjiga();
        k.setId(1L);
        k.setNaziv("1984");

        KnjigaDto dto = new KnjigaDto();
        dto.setId(1L);
        dto.setNaziv("1984");

        when(repo.findOneFull(1L)).thenReturn(k);
        when(mapper.toDo(k)).thenReturn(dto);

        KnjigaDto rezultat = servis.findById(1L);

        assertNotNull(rezultat);
        assertEquals(1L, rezultat.getId());
        assertEquals("1984", rezultat.getNaziv());

        verify(repo).findOneFull(1L);
        verify(mapper).toDo(k);
    }

    /**
     * Proverava ponašanje sistema kada knjiga ne postoji.
     *
     * @throws Exception očekivani izuzetak iz repozitorijuma
     */
    @Test
    void testFindByIdNePostoji() throws Exception {
        when(repo.findOneFull(999L)).thenThrow(new Exception("Knjiga nije pronađena!"));

        Exception e = assertThrows(Exception.class,
                () -> servis.findById(999L));

        assertEquals("Knjiga nije pronađena!", e.getMessage());

        verify(repo).findOneFull(999L);
        verify(mapper, never()).toDo(any());
    }

    /**
     * Proverava uspešno kreiranje knjige bez povezanih entiteta.
     *
     * @throws Exception ukoliko dođe do greške pri kreiranju
     */
    @Test
    void testCreate() throws Exception {

        KnjigaDto dto = new KnjigaDto();
        dto.setNaziv("1984");

        Knjiga entity = new Knjiga();
        entity.setId(1L);
        entity.setNaziv("1984");

        Knjiga saved = new Knjiga();
        saved.setId(1L);
        saved.setNaziv("1984");

        KnjigaDto rezultatDto = new KnjigaDto();
        rezultatDto.setId(1L);
        rezultatDto.setNaziv("1984");

        when(mapper.toEntity(dto)).thenReturn(entity);
        when(repo.findOneFull(1L)).thenReturn(saved);
        when(mapper.toDo(saved)).thenReturn(rezultatDto);

        KnjigaDto rezultat = servis.create(dto);

        assertNotNull(rezultat);
        assertEquals(1L, rezultat.getId());
        assertEquals("1984", rezultat.getNaziv());

        verify(mapper).toEntity(dto);
        verify(repo).save(entity);
        verify(repo).findOneFull(1L);
        verify(mapper).toDo(saved);
    }

    /**
     * Proverava kreiranje knjige sa povezanim žanrom.
     *
     * @throws Exception ukoliko žanr ili knjiga nisu pronađeni
     */
    @Test
    void testCreateSaZanrom() throws Exception {

        KnjigaDto dto = new KnjigaDto();
        dto.setNaziv("1984");
        dto.setZanrId(5L);

        Zanr zanr = new Zanr();
        zanr.setId(5L);
        zanr.setNaziv("Roman");

        Knjiga entity = new Knjiga();
        entity.setId(1L);

        Knjiga saved = new Knjiga();
        saved.setId(1L);
        saved.setZanr(zanr);

        KnjigaDto rezultatDto = new KnjigaDto();
        rezultatDto.setId(1L);
        rezultatDto.setZanrId(5L);

        when(mapper.toEntity(dto)).thenReturn(entity);
        when(zanrRepo.findById(5L)).thenReturn(zanr);
        when(repo.findOneFull(1L)).thenReturn(saved);
        when(mapper.toDo(saved)).thenReturn(rezultatDto);

        KnjigaDto rezultat = servis.create(dto);

        assertNotNull(rezultat);
        assertEquals(5L, rezultat.getZanrId());

        verify(zanrRepo).findById(5L);
        verify(repo).save(entity);
    }

    /**
     * Proverava uspešno ažuriranje osnovnih podataka o knjizi.
     *
     * @throws Exception ukoliko knjiga nije pronađena
     */
    @Test
    void testUpdate() throws Exception {

        KnjigaDto dto = new KnjigaDto();
        dto.setNaziv("Novo ime");
        dto.setOpis("Novi opis");
        dto.setCena(1500.0);
        dto.setIsbn("123");
        dto.setDostupnost(new ArrayList<>());

        Knjiga existing = new Knjiga();
        existing.setId(1L);
        existing.setAutori(new ArrayList<>());
        existing.setDostupnost(new ArrayList<>());

        Knjiga updated = new Knjiga();
        updated.setId(1L);
        updated.setNaziv("Novo ime");

        KnjigaDto rezultatDto = new KnjigaDto();
        rezultatDto.setId(1L);
        rezultatDto.setNaziv("Novo ime");

        when(repo.findById(1L)).thenReturn(existing);
        when(repo.findOneFull(1L)).thenReturn(updated);
        when(mapper.toDo(updated)).thenReturn(rezultatDto);

        KnjigaDto rezultat = servis.update(1L, dto);

        assertNotNull(rezultat);
        assertEquals("Novo ime", rezultat.getNaziv());

        verify(repo).findById(1L);
        verify(repo).save(existing);
        verify(repo).findOneFull(1L);
    }

    /**
     * Proverava uspešno brisanje knjige prema identifikatoru.
     */
    @Test
    void testDeleteById() {

        servis.deleteById(1L);

        verify(repo).deleteById(1L);
    }

    /**
     * Proverava pronalaženje knjiga prema žanru.
     */
    @Test
    void testFindByGenre() {
        Knjiga k = new Knjiga();
        KnjigaDto dto = new KnjigaDto();

        when(repo.findByGenre(1L)).thenReturn(List.of(k));
        when(mapper.toDo(k)).thenReturn(dto);

        List<KnjigaDto> rezultat = servis.findByGenre(1L);

        assertEquals(1, rezultat.size());
        verify(repo).findByGenre(1L);
    }

    /**
     * Proverava pronalaženje knjiga čija je cena manja od zadate vrednosti.
     */
    @Test
    void testFindCheaperThan() {
        Knjiga k = new Knjiga();
        KnjigaDto dto = new KnjigaDto();

        when(repo.findCheaperThan(1000.0)).thenReturn(List.of(k));
        when(mapper.toDo(k)).thenReturn(dto);

        List<KnjigaDto> rezultat = servis.findCheaperThan(1000.0);

        assertEquals(1, rezultat.size());
        verify(repo).findCheaperThan(1000.0);
    }

    /**
     * Proverava pretragu knjiga prema tekstu, žanru i maksimalnoj ceni.
     */
    @Test
    void testSearch() {
        Knjiga k = new Knjiga();
        KnjigaDto dto = new KnjigaDto();

        when(repo.search("1984", 1L, 1000.0)).thenReturn(List.of(k));
        when(mapper.toDo(k)).thenReturn(dto);

        List<KnjigaDto> rezultat = servis.search("1984", 1L, 1000.0);

        assertEquals(1, rezultat.size());
        verify(repo).search("1984", 1L, 1000.0);
    }

    /**
     * Proverava kreiranje knjige sa autorima i dostupnošću u knjižari.
     *
     * @throws Exception ukoliko autor, knjižara ili knjiga nisu pronađeni
     */
    @Test
    void testCreateSaAutorimaIDostupnoscu() throws Exception {

        KnjigaDto dto = new KnjigaDto();
        dto.setNaziv("1984");

        KnjigaDto.AutorView autorView = new KnjigaDto.AutorView();
        autorView.id = 1L;
        autorView.uloga = "Pisac";

        KnjigaDto.DostupnostView dostupnostView = new KnjigaDto.DostupnostView();
        dostupnostView.knjizaraId = 2L;
        dostupnostView.kolicina = 5;

        dto.setAutori(List.of(autorView));
        dto.setDostupnost(List.of(dostupnostView));

        Knjiga entity = new Knjiga();
        entity.setId(10L);
        entity.setAutori(new ArrayList<>());
        entity.setDostupnost(new ArrayList<>());

        Autor autor = new Autor();
        autor.setId(1L);

        Knjizara knjizara = new Knjizara();
        knjizara.setId(2L);

        Knjiga saved = new Knjiga();
        saved.setId(10L);

        KnjigaDto rezultatDto = new KnjigaDto();
        rezultatDto.setId(10L);

        when(mapper.toEntity(dto)).thenReturn(entity);
        when(autorRepo.findById(1L)).thenReturn(autor);
        when(knjizaraRepo.findById(2L)).thenReturn(knjizara);
        when(repo.findOneFull(10L)).thenReturn(saved);
        when(mapper.toDo(saved)).thenReturn(rezultatDto);

        KnjigaDto rezultat = servis.create(dto);

        assertNotNull(rezultat);
        assertEquals(10L, rezultat.getId());

        assertEquals(1, entity.getAutori().size());
        assertEquals(autor, entity.getAutori().get(0).getAutor());
        assertEquals("Pisac", entity.getAutori().get(0).getUloga());

        assertEquals(1, entity.getDostupnost().size());
        assertEquals(knjizara, entity.getDostupnost().get(0).getKnjizara());
        assertEquals(5, entity.getDostupnost().get(0).getKolicina());

        verify(autorRepo).findById(1L);
        verify(knjizaraRepo).findById(2L);
        verify(repo).save(entity);
    }

    /**
     * Proverava ažuriranje knjige sa autorima i dostupnošću.
     *
     * @throws Exception ukoliko autor, knjižara ili knjiga nisu pronađeni
     */
    @Test
    void testUpdateSaAutorimaIDostupnoscu() throws Exception {

        KnjigaDto dto = new KnjigaDto();
        dto.setNaziv("Novo ime");
        dto.setOpis("Novi opis");
        dto.setCena(1500.0);
        dto.setIsbn("123");

        KnjigaDto.AutorView autorView = new KnjigaDto.AutorView();
        autorView.id = 1L;
        autorView.uloga = "Pisac";

        KnjigaDto.DostupnostView dostupnostView = new KnjigaDto.DostupnostView();
        dostupnostView.knjizaraId = 2L;
        dostupnostView.kolicina = 7;

        dto.setAutori(List.of(autorView));
        dto.setDostupnost(List.of(dostupnostView));

        Knjiga existing = new Knjiga();
        existing.setId(10L);
        existing.setAutori(new ArrayList<>());
        existing.setDostupnost(new ArrayList<>());

        Autor autor = new Autor();
        autor.setId(1L);

        Knjizara knjizara = new Knjizara();
        knjizara.setId(2L);

        Knjiga updated = new Knjiga();
        updated.setId(10L);

        KnjigaDto rezultatDto = new KnjigaDto();
        rezultatDto.setId(10L);

        when(repo.findById(10L)).thenReturn(existing);
        when(autorRepo.findById(1L)).thenReturn(autor);
        when(knjizaraRepo.findById(2L)).thenReturn(knjizara);
        when(repo.findOneFull(10L)).thenReturn(updated);
        when(mapper.toDo(updated)).thenReturn(rezultatDto);

        KnjigaDto rezultat = servis.update(10L, dto);

        assertNotNull(rezultat);
        assertEquals(10L, rezultat.getId());

        assertEquals(1, existing.getAutori().size());
        assertEquals(autor, existing.getAutori().get(0).getAutor());

        assertEquals(1, existing.getDostupnost().size());
        assertEquals(knjizara, existing.getDostupnost().get(0).getKnjizara());
        assertEquals(7, existing.getDostupnost().get(0).getKolicina());

        verify(repo).findById(10L);
        verify(autorRepo).findById(1L);
        verify(knjizaraRepo).findById(2L);
        verify(repo).save(existing);
    }

    /**
     * Proverava da se pri kreiranju preskaču autori i dostupnosti
     * kod kojih nije prosleđen identifikator.
     *
     * @throws Exception ukoliko dođe do greške pri kreiranju
     */
    @Test
    void testCreatePreskaceAutoreIDostupnostBezId() throws Exception {

        KnjigaDto dto = new KnjigaDto();

        KnjigaDto.AutorView autorView = new KnjigaDto.AutorView();
        autorView.id = null;

        KnjigaDto.DostupnostView dostupnostView = new KnjigaDto.DostupnostView();
        dostupnostView.knjizaraId = null;

        dto.setAutori(List.of(autorView));
        dto.setDostupnost(List.of(dostupnostView));

        Knjiga entity = new Knjiga();
        entity.setId(1L);
        entity.setAutori(new ArrayList<>());
        entity.setDostupnost(new ArrayList<>());

        Knjiga saved = new Knjiga();
        saved.setId(1L);

        KnjigaDto rezultatDto = new KnjigaDto();
        rezultatDto.setId(1L);

        when(mapper.toEntity(dto)).thenReturn(entity);
        when(repo.findOneFull(1L)).thenReturn(saved);
        when(mapper.toDo(saved)).thenReturn(rezultatDto);

        KnjigaDto rezultat = servis.create(dto);

        assertNotNull(rezultat);
        assertEquals(0, entity.getAutori().size());
        assertEquals(0, entity.getDostupnost().size());

        verify(autorRepo, never()).findById(anyLong());
        verify(knjizaraRepo, never()).findById(anyLong());
        verify(repo).save(entity);
    }

    /**
     * Proverava da se pri ažuriranju preskaču autori i dostupnosti
     * kod kojih nije prosleđen identifikator.
     *
     * @throws Exception ukoliko dođe do greške pri ažuriranju
     */
    @Test
    void testUpdatePreskaceAutoreIDostupnostBezId() throws Exception {

        KnjigaDto dto = new KnjigaDto();

        KnjigaDto.AutorView autorView = new KnjigaDto.AutorView();
        autorView.id = null;

        KnjigaDto.DostupnostView dostupnostView = new KnjigaDto.DostupnostView();
        dostupnostView.knjizaraId = null;

        dto.setAutori(List.of(autorView));
        dto.setDostupnost(List.of(dostupnostView));

        Knjiga existing = new Knjiga();
        existing.setId(1L);
        existing.setAutori(new ArrayList<>());
        existing.setDostupnost(new ArrayList<>());

        Knjiga updated = new Knjiga();
        updated.setId(1L);

        KnjigaDto rezultatDto = new KnjigaDto();
        rezultatDto.setId(1L);

        when(repo.findById(1L)).thenReturn(existing);
        when(repo.findOneFull(1L)).thenReturn(updated);
        when(mapper.toDo(updated)).thenReturn(rezultatDto);

        KnjigaDto rezultat = servis.update(1L, dto);

        assertNotNull(rezultat);
        assertEquals(0, existing.getAutori().size());
        assertEquals(0, existing.getDostupnost().size());

        verify(autorRepo, never()).findById(anyLong());
        verify(knjizaraRepo, never()).findById(anyLong());
        verify(repo).save(existing);
    }
}
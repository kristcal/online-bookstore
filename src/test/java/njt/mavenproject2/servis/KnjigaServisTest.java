package njt.mavenproject2.servis;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import njt.mavenproject2.dto.impl.KnjigaDto;
import njt.mavenproject2.entity.impl.Knjiga;
import njt.mavenproject2.entity.impl.Zanr;
import njt.mavenproject2.mapper.impl.KnjigaMapper;
import njt.mavenproject2.repository.impl.AutorRepository;
import njt.mavenproject2.repository.impl.KnjigaRepository;
import njt.mavenproject2.repository.impl.KnjizaraRepository;
import njt.mavenproject2.repository.impl.ZanrRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class KnjigaServisTest {

    private KnjigaRepository repo;
    private ZanrRepository zanrRepo;
    private AutorRepository autorRepo;
    private KnjizaraRepository knjizaraRepo;
    private KnjigaMapper mapper;
    private KnjigaServis servis;

    @BeforeEach
    void setUp() {
        repo = mock(KnjigaRepository.class);
        zanrRepo = mock(ZanrRepository.class);
        autorRepo = mock(AutorRepository.class);
        knjizaraRepo = mock(KnjizaraRepository.class);
        mapper = mock(KnjigaMapper.class);

        servis = new KnjigaServis(repo, zanrRepo, mapper, autorRepo, knjizaraRepo);
    }

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

    @Test
    void testFindByIdNePostoji() throws Exception {
        when(repo.findOneFull(999L)).thenThrow(new Exception("Knjiga nije pronađena!"));

        Exception e = assertThrows(Exception.class,
                () -> servis.findById(999L));

        assertEquals("Knjiga nije pronađena!", e.getMessage());

        verify(repo).findOneFull(999L);
        verify(mapper, never()).toDo(any());
    }
    
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
    
    @Test
    void testDeleteById() {

        servis.deleteById(1L);

        verify(repo).deleteById(1L);
    }
    
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
    
}
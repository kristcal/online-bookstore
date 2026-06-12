package njt.mavenproject2.servis;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import njt.mavenproject2.dto.impl.KnjigaAutorDto;
import njt.mavenproject2.entity.impl.Autor;
import njt.mavenproject2.entity.impl.Knjiga;
import njt.mavenproject2.entity.impl.KnjigaAutor;
import njt.mavenproject2.mapper.impl.KnjigaAutorMapper;
import njt.mavenproject2.repository.impl.AutorRepository;
import njt.mavenproject2.repository.impl.KnjigaAutorRepository;
import njt.mavenproject2.repository.impl.KnjigaRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class KnjigaAutorServisTest {

    private KnjigaAutorRepository repo;
    private KnjigaRepository knjigaRepo;
    private AutorRepository autorRepo;
    private KnjigaAutorMapper mapper;
    private KnjigaAutorServis servis;

    @BeforeEach
    void setUp() {
        repo = mock(KnjigaAutorRepository.class);
        knjigaRepo = mock(KnjigaRepository.class);
        autorRepo = mock(AutorRepository.class);
        mapper = mock(KnjigaAutorMapper.class);

        servis = new KnjigaAutorServis(repo, knjigaRepo, autorRepo, mapper);
    }

    @Test
    void testListForKnjiga() {
        KnjigaAutor ka = new KnjigaAutor();

        KnjigaAutorDto dto = new KnjigaAutorDto();
        dto.setId(1L);

        when(repo.findByKnjigaId(10L)).thenReturn(List.of(ka));
        when(mapper.toDo(ka)).thenReturn(dto);

        List<KnjigaAutorDto> rezultat = servis.listForKnjiga(10L);

        assertEquals(1, rezultat.size());
        assertEquals(1L, rezultat.get(0).getId());

        verify(repo).findByKnjigaId(10L);
        verify(mapper).toDo(ka);
    }

    @Test
    void testAddAutorToKnjiga() throws Exception {
        Knjiga knjiga = new Knjiga();
        knjiga.setId(10L);

        Autor autor = new Autor();
        autor.setId(5L);

        KnjigaAutorDto dto = new KnjigaAutorDto();
        dto.setAutorId(5L);
        dto.setUloga("Pisac");

        KnjigaAutorDto rezultatDto = new KnjigaAutorDto();
        rezultatDto.setAutorId(5L);
        rezultatDto.setUloga("Pisac");

        when(knjigaRepo.findById(10L)).thenReturn(knjiga);
        when(autorRepo.findById(5L)).thenReturn(autor);
        when(mapper.toDo(any(KnjigaAutor.class))).thenReturn(rezultatDto);

        KnjigaAutorDto rezultat = servis.addAutorToKnjiga(10L, dto);

        assertNotNull(rezultat);
        assertEquals(5L, rezultat.getAutorId());
        assertEquals("Pisac", rezultat.getUloga());

        verify(knjigaRepo).findById(10L);
        verify(autorRepo).findById(5L);
        verify(repo).save(any(KnjigaAutor.class));
        verify(mapper).toDo(any(KnjigaAutor.class));
    }

    @Test
    void testAddAutorToKnjigaKnjigaNePostoji() throws Exception {
        KnjigaAutorDto dto = new KnjigaAutorDto();
        dto.setAutorId(5L);

        when(knjigaRepo.findById(10L))
                .thenThrow(new Exception("Knjiga nije pronadjena!"));

        Exception e = assertThrows(Exception.class,
                () -> servis.addAutorToKnjiga(10L, dto));

        assertEquals("Knjiga nije pronadjena!", e.getMessage());

        verify(knjigaRepo).findById(10L);
        verify(autorRepo, never()).findById(anyLong());
        verify(repo, never()).save(any());
    }

    @Test
    void testAddAutorToKnjigaAutorNePostoji() throws Exception {
        Knjiga knjiga = new Knjiga();
        knjiga.setId(10L);

        KnjigaAutorDto dto = new KnjigaAutorDto();
        dto.setAutorId(5L);

        when(knjigaRepo.findById(10L)).thenReturn(knjiga);
        when(autorRepo.findById(5L))
                .thenThrow(new Exception("Autor nije pronađen!"));

        Exception e = assertThrows(Exception.class,
                () -> servis.addAutorToKnjiga(10L, dto));

        assertEquals("Autor nije pronađen!", e.getMessage());

        verify(knjigaRepo).findById(10L);
        verify(autorRepo).findById(5L);
        verify(repo, never()).save(any());
    }

    @Test
    void testRemove() {
        servis.remove(1L);

        verify(repo).deleteById(1L);
    }
}
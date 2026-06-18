package njt.mavenproject2.servis;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import njt.mavenproject2.dto.impl.KnjizaraDto;
import njt.mavenproject2.entity.impl.Knjizara;
import njt.mavenproject2.mapper.impl.KnjizaraMapper;
import njt.mavenproject2.repository.impl.KnjizaraRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test klasa za proveru funkcionalnosti klase {@link KnjizaraServis}.
 *
 * Testira pronalaženje, kreiranje, izmenu i brisanje knjižara.
 *
 * @author Korisnik
 */
class KnjizaraServisTest {

    /**
     * Mock repozitorijum knjižara.
     */
    private KnjizaraRepository repo;

    /**
     * Mock mapper za konverziju knjižara.
     */
    private KnjizaraMapper mapper;

    /**
     * Servis koji se testira.
     */
    private KnjizaraServis servis;

    /**
     * Inicijalizuje mock objekte pre svakog testa.
     */
    @BeforeEach
    void setUp() {
        repo = mock(KnjizaraRepository.class);
        mapper = mock(KnjizaraMapper.class);

        servis = new KnjizaraServis(repo, mapper);
    }

    /**
     * Proverava uspešno pronalaženje svih knjižara.
     */
    @Test
    void testFindAll() {
        Knjizara knjizara = new Knjizara();
        knjizara.setId(1L);

        KnjizaraDto dto =
                new KnjizaraDto(1L, "Laguna", "Beograd", "011123456");
        dto.setId(1L);

        when(repo.findAll()).thenReturn(List.of(knjizara));
        when(mapper.toDo(knjizara)).thenReturn(dto);

        List<KnjizaraDto> rezultat = servis.findAll();

        assertEquals(1, rezultat.size());
        assertEquals(1L, rezultat.get(0).getId());

        verify(repo).findAll();
        verify(mapper).toDo(knjizara);
    }

    /**
     * Proverava uspešno pronalaženje knjižare prema identifikatoru.
     *
     * @throws Exception ukoliko knjižara nije pronađena
     */
    @Test
    void testFindById() throws Exception {
        Knjizara knjizara = new Knjizara();
        knjizara.setId(1L);

        KnjizaraDto dto =
                new KnjizaraDto(1L, "Laguna", "Beograd", "011123456");
        dto.setId(1L);

        when(repo.findById(1L)).thenReturn(knjizara);
        when(mapper.toDo(knjizara)).thenReturn(dto);

        KnjizaraDto rezultat = servis.findById(1L);

        assertEquals(1L, rezultat.getId());

        verify(repo).findById(1L);
        verify(mapper).toDo(knjizara);
    }

    /**
     * Proverava ponašanje sistema kada knjižara ne postoji.
     *
     * @throws Exception očekivani izuzetak iz repozitorijuma
     */
    @Test
    void testFindByIdNePostoji() throws Exception {
        when(repo.findById(999L))
                .thenThrow(new Exception("Knjižara nije pronađena!"));

        Exception e = assertThrows(Exception.class,
                () -> servis.findById(999L));

        assertEquals("Knjižara nije pronađena!", e.getMessage());

        verify(repo).findById(999L);
        verify(mapper, never()).toDo(any());
    }

    /**
     * Proverava uspešno kreiranje knjižare.
     */
    @Test
    void testCreate() {
        KnjizaraDto dto =
                new KnjizaraDto(1L, "Laguna", "Beograd", "011123456");
        dto.setNaziv("Laguna");

        Knjizara knjizara = new Knjizara();
        knjizara.setId(1L);
        knjizara.setNaziv("Laguna");

        KnjizaraDto rezultatDto =
                new KnjizaraDto(1L, "Laguna", "Beograd", "011123456");
        rezultatDto.setId(1L);
        rezultatDto.setNaziv("Laguna");

        when(mapper.toEntity(dto)).thenReturn(knjizara);
        when(mapper.toDo(knjizara)).thenReturn(rezultatDto);

        KnjizaraDto rezultat = servis.create(dto);

        assertNotNull(rezultat);
        assertEquals(1L, rezultat.getId());
        assertEquals("Laguna", rezultat.getNaziv());

        verify(mapper).toEntity(dto);
        verify(repo).save(knjizara);
        verify(mapper).toDo(knjizara);
    }

    /**
     * Proverava uspešno ažuriranje knjižare.
     */
    @Test
    void testUpdate() {
        KnjizaraDto dto =
                new KnjizaraDto(1L, "Laguna", "Beograd", "011123456");
        dto.setId(1L);
        dto.setNaziv("Vulkan");

        Knjizara knjizara = new Knjizara();
        knjizara.setId(1L);
        knjizara.setNaziv("Vulkan");

        KnjizaraDto rezultatDto =
                new KnjizaraDto(1L, "Laguna", "Beograd", "011123456");
        rezultatDto.setId(1L);
        rezultatDto.setNaziv("Vulkan");

        when(mapper.toEntity(dto)).thenReturn(knjizara);
        when(mapper.toDo(knjizara)).thenReturn(rezultatDto);

        KnjizaraDto rezultat = servis.update(dto);

        assertNotNull(rezultat);
        assertEquals(1L, rezultat.getId());
        assertEquals("Vulkan", rezultat.getNaziv());

        verify(mapper).toEntity(dto);
        verify(repo).save(knjizara);
        verify(mapper).toDo(knjizara);
    }

    /**
     * Proverava uspešno brisanje knjižare prema identifikatoru.
     */
    @Test
    void testDeleteById() {
        servis.deleteById(1L);

        verify(repo).deleteById(1L);
    }
}
package njt.mavenproject2.repository.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.Field;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import njt.mavenproject2.entity.impl.Knjiga;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test klasa za proveru funkcionalnosti klase {@link KnjigaRepository}.
 *
 * Testira osnovne CRUD operacije nad knjigama, pretragu knjiga po žanru,
 * ceni i tekstualnom kriterijumu, kao i učitavanje knjige sa povezanim
 * entitetima.
 *
 * @author Korisnik
 */
class KnjigaRepositoryTest {

    /**
     * Repozitorijum koji se testira.
     */
    private KnjigaRepository repo;

    /**
     * Mock EntityManager koji se koristi za testiranje.
     */
    private EntityManager entityManager;

    /**
     * Inicijalizuje repozitorijum i mock EntityManager pre svakog testa.
     *
     * @throws Exception ukoliko nije moguće podesiti EntityManager pomoću refleksije
     */
    @BeforeEach
    void setUp() throws Exception {
        repo = new KnjigaRepository();
        entityManager = mock(EntityManager.class);

        Field field = KnjigaRepository.class.getDeclaredField("entityManager");
        field.setAccessible(true);
        field.set(repo, entityManager);
    }

    /**
     * Proverava uspešno pronalaženje svih knjiga.
     */
    @Test
    void testFindAll() {
        TypedQuery<Knjiga> query = mock(TypedQuery.class);

        Knjiga k1 = new Knjiga();
        k1.setId(1L);

        Knjiga k2 = new Knjiga();
        k2.setId(2L);

        when(entityManager.createQuery("SELECT k FROM Knjiga k", Knjiga.class))
                .thenReturn(query);
        when(query.getResultList()).thenReturn(List.of(k1, k2));

        List<Knjiga> rezultat = repo.findAll();

        assertEquals(2, rezultat.size());
        verify(entityManager).createQuery("SELECT k FROM Knjiga k", Knjiga.class);
        verify(query).getResultList();
    }

    /**
     * Proverava uspešno pronalaženje knjige prema identifikatoru.
     *
     * @throws Exception ukoliko knjiga nije pronađena
     */
    @Test
    void testFindById() throws Exception {
        Knjiga knjiga = new Knjiga();
        knjiga.setId(1L);

        when(entityManager.find(Knjiga.class, 1L)).thenReturn(knjiga);

        Knjiga rezultat = repo.findById(1L);

        assertEquals(1L, rezultat.getId());
        verify(entityManager).find(Knjiga.class, 1L);
    }

    /**
     * Proverava ponašanje sistema kada knjiga nije pronađena.
     */
    @Test
    void testFindByIdNePostoji() {
        when(entityManager.find(Knjiga.class, 999L)).thenReturn(null);

        Exception e = assertThrows(Exception.class,
                () -> repo.findById(999L));

        assertEquals("Knjiga nije pronadjena!", e.getMessage());
    }

    /**
     * Proverava čuvanje nove knjige pomoću metode persist.
     */
    @Test
    void testSavePersist() {
        Knjiga knjiga = new Knjiga();
        knjiga.setId(null);

        repo.save(knjiga);

        verify(entityManager).persist(knjiga);
        verify(entityManager, never()).merge(any());
    }

    /**
     * Proverava ažuriranje postojeće knjige pomoću metode merge.
     */
    @Test
    void testSaveMerge() {
        Knjiga knjiga = new Knjiga();
        knjiga.setId(1L);

        repo.save(knjiga);

        verify(entityManager).merge(knjiga);
        verify(entityManager, never()).persist(any());
    }

    /**
     * Proverava uspešno brisanje knjige prema identifikatoru.
     */
    @Test
    void testDeleteById() {
        Knjiga knjiga = new Knjiga();
        knjiga.setId(1L);

        when(entityManager.find(Knjiga.class, 1L)).thenReturn(knjiga);

        repo.deleteById(1L);

        verify(entityManager).remove(knjiga);
    }

    /**
     * Proverava da se ne briše knjiga koja ne postoji.
     */
    @Test
    void testDeleteByIdNePostoji() {
        when(entityManager.find(Knjiga.class, 999L)).thenReturn(null);

        repo.deleteById(999L);

        verify(entityManager, never()).remove(any());
    }

    /**
     * Proverava pronalaženje knjiga prema žanru.
     */
    @Test
    void testFindByGenre() {
        TypedQuery<Knjiga> query = mock(TypedQuery.class);
        Knjiga knjiga = new Knjiga();

        when(entityManager.createQuery(
                "SELECT k FROM Knjiga k WHERE k.zanr.id = :z ORDER BY k.id DESC",
                Knjiga.class))
                .thenReturn(query);
        when(query.setParameter("z", 1L)).thenReturn(query);
        when(query.getResultList()).thenReturn(List.of(knjiga));

        List<Knjiga> rezultat = repo.findByGenre(1L);

        assertEquals(1, rezultat.size());
        verify(query).setParameter("z", 1L);
    }

    /**
     * Proverava pronalaženje knjiga koje su jeftinije od zadate cene.
     */
    @Test
    void testFindCheaperThan() {
        TypedQuery<Knjiga> query = mock(TypedQuery.class);
        Knjiga knjiga = new Knjiga();

        when(entityManager.createQuery(
                "SELECT k FROM Knjiga k WHERE k.cena <= :m ORDER BY k.cena ASC",
                Knjiga.class))
                .thenReturn(query);
        when(query.setParameter("m", 1000.0)).thenReturn(query);
        when(query.getResultList()).thenReturn(List.of(knjiga));

        List<Knjiga> rezultat = repo.findCheaperThan(1000.0);

        assertEquals(1, rezultat.size());
        verify(query).setParameter("m", 1000.0);
    }

    /**
     * Proverava pretragu knjiga bez dodatnih filtera.
     */
    @Test
    void testSearchBezFiltera() {
        TypedQuery<Knjiga> query = mock(TypedQuery.class);

        when(entityManager.createQuery(anyString(), eq(Knjiga.class)))
                .thenReturn(query);
        when(query.setParameter("q", "1984")).thenReturn(query);
        when(query.getResultList()).thenReturn(List.of(new Knjiga()));

        List<Knjiga> rezultat = repo.search("1984", null, null);

        assertEquals(1, rezultat.size());
        verify(query).setParameter("q", "1984");
        verify(query, never()).setParameter(eq("z"), any());
        verify(query, never()).setParameter(eq("m"), any());
    }

    /**
     * Proverava pretragu knjiga sa filterima za žanr i maksimalnu cenu.
     */
    @Test
    void testSearchSaZanromICenom() {
        TypedQuery<Knjiga> query = mock(TypedQuery.class);

        when(entityManager.createQuery(anyString(), eq(Knjiga.class)))
                .thenReturn(query);
        when(query.setParameter("q", "1984")).thenReturn(query);
        when(query.setParameter("z", 1L)).thenReturn(query);
        when(query.setParameter("m", 1000.0)).thenReturn(query);
        when(query.getResultList()).thenReturn(List.of(new Knjiga()));

        List<Knjiga> rezultat = repo.search("1984", 1L, 1000.0);

        assertEquals(1, rezultat.size());
        verify(query).setParameter("q", "1984");
        verify(query).setParameter("z", 1L);
        verify(query).setParameter("m", 1000.0);
    }

    /**
     * Proverava učitavanje knjige zajedno sa povezanim entitetima.
     *
     * @throws Exception ukoliko knjiga nije pronađena
     */
    @Test
    void testFindOneFull() throws Exception {
        TypedQuery<Knjiga> query = mock(TypedQuery.class);

        Knjiga knjiga = new Knjiga();
        knjiga.setId(1L);

        when(entityManager.createQuery(anyString(), eq(Knjiga.class)))
                .thenReturn(query);
        when(query.setParameter("id", 1L)).thenReturn(query);
        when(query.getResultList()).thenReturn(List.of(knjiga));

        Knjiga rezultat = repo.findOneFull(1L);

        assertEquals(1L, rezultat.getId());
        verify(query).setParameter("id", 1L);
    }

    /**
     * Proverava ponašanje sistema kada knjiga sa povezanim entitetima
     * nije pronađena.
     */
    @Test
    void testFindOneFullNePostoji() {
        TypedQuery<Knjiga> query = mock(TypedQuery.class);

        when(entityManager.createQuery(anyString(), eq(Knjiga.class)))
                .thenReturn(query);
        when(query.setParameter("id", 999L)).thenReturn(query);
        when(query.getResultList()).thenReturn(List.of());

        Exception e = assertThrows(Exception.class,
                () -> repo.findOneFull(999L));

        assertEquals("Knjiga nije pronađena!", e.getMessage());
    }
}
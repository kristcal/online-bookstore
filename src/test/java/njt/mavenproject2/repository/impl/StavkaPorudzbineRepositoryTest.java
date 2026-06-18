package njt.mavenproject2.repository.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.Field;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import njt.mavenproject2.entity.impl.StavkaPorudzbine;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test klasa za proveru funkcionalnosti klase {@link StavkaPorudzbineRepository}.
 *
 * Testira osnovne CRUD operacije nad stavkama porudžbine, pronalaženje
 * stavki za određenu porudžbinu i pronalaženje najvećeg rednog broja stavke.
 *
 * @author Korisnik
 */
class StavkaPorudzbineRepositoryTest {

    /**
     * Repozitorijum koji se testira.
     */
    private StavkaPorudzbineRepository repo;

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
        repo = new StavkaPorudzbineRepository();
        entityManager = mock(EntityManager.class);

        Field field = StavkaPorudzbineRepository.class.getDeclaredField("entityManager");
        field.setAccessible(true);
        field.set(repo, entityManager);
    }

    /**
     * Proverava uspešno pronalaženje svih stavki porudžbine.
     */
    @Test
    void testFindAll() {
        TypedQuery<StavkaPorudzbine> query = mock(TypedQuery.class);

        when(entityManager.createQuery(
                "SELECT s FROM StavkaPorudzbine s",
                StavkaPorudzbine.class))
                .thenReturn(query);
        when(query.getResultList()).thenReturn(List.of(new StavkaPorudzbine()));

        List<StavkaPorudzbine> rezultat = repo.findAll();

        assertEquals(1, rezultat.size());
    }

    /**
     * Proverava uspešno pronalaženje stavke porudžbine prema identifikatoru.
     *
     * @throws Exception ukoliko stavka nije pronađena
     */
    @Test
    void testFindById() throws Exception {
        StavkaPorudzbine s = new StavkaPorudzbine();
        s.setId(1L);

        when(entityManager.find(StavkaPorudzbine.class, 1L)).thenReturn(s);

        StavkaPorudzbine rezultat = repo.findById(1L);

        assertEquals(1L, rezultat.getId());
    }

    /**
     * Proverava ponašanje sistema kada stavka porudžbine nije pronađena.
     */
    @Test
    void testFindByIdNePostoji() {
        when(entityManager.find(StavkaPorudzbine.class, 999L)).thenReturn(null);

        Exception e = assertThrows(Exception.class,
                () -> repo.findById(999L));

        assertEquals("Stavka porudžbine nije pronađena!", e.getMessage());
    }

    /**
     * Proverava čuvanje nove stavke pomoću metode persist.
     */
    @Test
    void testSavePersist() {
        StavkaPorudzbine s = new StavkaPorudzbine();

        repo.save(s);

        verify(entityManager).persist(s);
        verify(entityManager, never()).merge(any());
    }

    /**
     * Proverava ažuriranje postojeće stavke pomoću metode merge.
     */
    @Test
    void testSaveMerge() {
        StavkaPorudzbine s = new StavkaPorudzbine();
        s.setId(1L);

        repo.save(s);

        verify(entityManager).merge(s);
        verify(entityManager, never()).persist(any());
    }

    /**
     * Proverava uspešno brisanje stavke porudžbine.
     */
    @Test
    void testDeleteById() {
        StavkaPorudzbine s = new StavkaPorudzbine();
        s.setId(1L);

        when(entityManager.find(StavkaPorudzbine.class, 1L)).thenReturn(s);

        repo.deleteById(1L);

        verify(entityManager).remove(s);
    }

    /**
     * Proverava da se ne briše stavka koja ne postoji.
     */
    @Test
    void testDeleteByIdNePostoji() {
        when(entityManager.find(StavkaPorudzbine.class, 999L)).thenReturn(null);

        repo.deleteById(999L);

        verify(entityManager, never()).remove(any());
    }

    /**
     * Proverava pronalaženje svih stavki određene porudžbine.
     */
    @Test
    void testFindByPorudzbinaId() {
        TypedQuery<StavkaPorudzbine> query = mock(TypedQuery.class);

        when(entityManager.createQuery(
                "SELECT s FROM StavkaPorudzbine s WHERE s.porudzbina.id=:pid",
                StavkaPorudzbine.class))
                .thenReturn(query);
        when(query.setParameter("pid", 10L)).thenReturn(query);
        when(query.getResultList()).thenReturn(List.of(new StavkaPorudzbine()));

        List<StavkaPorudzbine> rezultat = repo.findByPorudzbinaId(10L);

        assertEquals(1, rezultat.size());
        verify(query).setParameter("pid", 10L);
    }

    /**
     * Proverava pronalaženje najvećeg rednog broja stavke za određenu porudžbinu.
     */
    @Test
    void testFindMaxRbForPorudzbina() {
        TypedQuery<Integer> query = mock(TypedQuery.class);

        when(entityManager.createQuery(
                "SELECT COALESCE(MAX(s.rb), 0) FROM StavkaPorudzbine s WHERE s.porudzbina.id = :pid",
                Integer.class))
                .thenReturn(query);
        when(query.setParameter("pid", 10L)).thenReturn(query);
        when(query.getSingleResult()).thenReturn(3);

        Integer rezultat = repo.findMaxRbForPorudzbina(10L);

        assertEquals(3, rezultat);
        verify(query).setParameter("pid", 10L);
    }
}
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

class StavkaPorudzbineRepositoryTest {

    private StavkaPorudzbineRepository repo;
    private EntityManager entityManager;

    @BeforeEach
    void setUp() throws Exception {
        repo = new StavkaPorudzbineRepository();
        entityManager = mock(EntityManager.class);

        Field field = StavkaPorudzbineRepository.class.getDeclaredField("entityManager");
        field.setAccessible(true);
        field.set(repo, entityManager);
    }

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

    @Test
    void testFindById() throws Exception {
        StavkaPorudzbine s = new StavkaPorudzbine();
        s.setId(1L);

        when(entityManager.find(StavkaPorudzbine.class, 1L)).thenReturn(s);

        StavkaPorudzbine rezultat = repo.findById(1L);

        assertEquals(1L, rezultat.getId());
    }

    @Test
    void testFindByIdNePostoji() {
        when(entityManager.find(StavkaPorudzbine.class, 999L)).thenReturn(null);

        Exception e = assertThrows(Exception.class,
                () -> repo.findById(999L));

        assertEquals("Stavka porudžbine nije pronađena!", e.getMessage());
    }

    @Test
    void testSavePersist() {
        StavkaPorudzbine s = new StavkaPorudzbine();

        repo.save(s);

        verify(entityManager).persist(s);
        verify(entityManager, never()).merge(any());
    }

    @Test
    void testSaveMerge() {
        StavkaPorudzbine s = new StavkaPorudzbine();
        s.setId(1L);

        repo.save(s);

        verify(entityManager).merge(s);
        verify(entityManager, never()).persist(any());
    }

    @Test
    void testDeleteById() {
        StavkaPorudzbine s = new StavkaPorudzbine();
        s.setId(1L);

        when(entityManager.find(StavkaPorudzbine.class, 1L)).thenReturn(s);

        repo.deleteById(1L);

        verify(entityManager).remove(s);
    }

    @Test
    void testDeleteByIdNePostoji() {
        when(entityManager.find(StavkaPorudzbine.class, 999L)).thenReturn(null);

        repo.deleteById(999L);

        verify(entityManager, never()).remove(any());
    }

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
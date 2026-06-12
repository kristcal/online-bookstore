package njt.mavenproject2.repository.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.Field;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import njt.mavenproject2.entity.impl.Zanr;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ZanrRepositoryTest {

    private ZanrRepository repo;
    private EntityManager entityManager;

    @BeforeEach
    void setUp() throws Exception {
        repo = new ZanrRepository();
        entityManager = mock(EntityManager.class);

        Field field = ZanrRepository.class.getDeclaredField("entityManager");
        field.setAccessible(true);
        field.set(repo, entityManager);
    }

    @Test
    void testFindAll() {
        TypedQuery<Zanr> query = mock(TypedQuery.class);

        Zanr z1 = new Zanr();
        z1.setId(1L);

        Zanr z2 = new Zanr();
        z2.setId(2L);

        when(entityManager.createQuery("SELECT z FROM Zanr z", Zanr.class))
                .thenReturn(query);
        when(query.getResultList()).thenReturn(List.of(z1, z2));

        List<Zanr> rezultat = repo.findAll();

        assertEquals(2, rezultat.size());
        assertTrue(rezultat.contains(z1));
        assertTrue(rezultat.contains(z2));

        verify(entityManager).createQuery("SELECT z FROM Zanr z", Zanr.class);
        verify(query).getResultList();
    }

    @Test
    void testFindById() throws Exception {
        Zanr zanr = new Zanr();
        zanr.setId(1L);

        when(entityManager.find(Zanr.class, 1L)).thenReturn(zanr);

        Zanr rezultat = repo.findById(1L);

        assertEquals(1L, rezultat.getId());
        verify(entityManager).find(Zanr.class, 1L);
    }

    @Test
    void testFindByIdNePostoji() {
        when(entityManager.find(Zanr.class, 999L)).thenReturn(null);

        Exception e = assertThrows(Exception.class,
                () -> repo.findById(999L));

        assertEquals("Žanr nije pronađen!", e.getMessage());
        verify(entityManager).find(Zanr.class, 999L);
    }

    @Test
    void testSavePersist() {
        Zanr zanr = new Zanr();
        zanr.setId(null);

        repo.save(zanr);

        verify(entityManager).persist(zanr);
        verify(entityManager, never()).merge(any());
    }

    @Test
    void testSaveMerge() {
        Zanr zanr = new Zanr();
        zanr.setId(1L);

        repo.save(zanr);

        verify(entityManager).merge(zanr);
        verify(entityManager, never()).persist(any());
    }

    @Test
    void testDeleteById() {
        Zanr zanr = new Zanr();
        zanr.setId(1L);

        when(entityManager.find(Zanr.class, 1L)).thenReturn(zanr);

        repo.deleteById(1L);

        verify(entityManager).find(Zanr.class, 1L);
        verify(entityManager).remove(zanr);
    }

    @Test
    void testDeleteByIdNePostoji() {
        when(entityManager.find(Zanr.class, 999L)).thenReturn(null);

        repo.deleteById(999L);

        verify(entityManager).find(Zanr.class, 999L);
        verify(entityManager, never()).remove(any());
    }
}
package njt.mavenproject2.repository.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.Field;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import njt.mavenproject2.entity.impl.Knjizara;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class KnjizaraRepositoryTest {

    private KnjizaraRepository repo;
    private EntityManager entityManager;

    @BeforeEach
    void setUp() throws Exception {
        repo = new KnjizaraRepository();
        entityManager = mock(EntityManager.class);

        Field field = KnjizaraRepository.class.getDeclaredField("entityManager");
        field.setAccessible(true);
        field.set(repo, entityManager);
    }

    @Test
    void testFindAll() {
        TypedQuery<Knjizara> query = mock(TypedQuery.class);

        Knjizara k1 = new Knjizara();
        k1.setId(1L);

        Knjizara k2 = new Knjizara();
        k2.setId(2L);

        when(entityManager.createQuery("SELECT k FROM Knjizara k", Knjizara.class))
                .thenReturn(query);
        when(query.getResultList()).thenReturn(List.of(k1, k2));

        List<Knjizara> rezultat = repo.findAll();

        assertEquals(2, rezultat.size());
        assertTrue(rezultat.contains(k1));
        assertTrue(rezultat.contains(k2));

        verify(entityManager).createQuery("SELECT k FROM Knjizara k", Knjizara.class);
        verify(query).getResultList();
    }

    @Test
    void testFindById() throws Exception {
        Knjizara knjizara = new Knjizara();
        knjizara.setId(1L);

        when(entityManager.find(Knjizara.class, 1L)).thenReturn(knjizara);

        Knjizara rezultat = repo.findById(1L);

        assertEquals(1L, rezultat.getId());
        verify(entityManager).find(Knjizara.class, 1L);
    }

    @Test
    void testFindByIdNePostoji() {
        when(entityManager.find(Knjizara.class, 999L)).thenReturn(null);

        Exception e = assertThrows(Exception.class,
                () -> repo.findById(999L));

        assertEquals("Knjizara nije pronađena!", e.getMessage());
        verify(entityManager).find(Knjizara.class, 999L);
    }

    @Test
    void testSavePersist() {
        Knjizara knjizara = new Knjizara();
        knjizara.setId(null);

        repo.save(knjizara);

        verify(entityManager).persist(knjizara);
        verify(entityManager, never()).merge(any());
    }

    @Test
    void testSaveMerge() {
        Knjizara knjizara = new Knjizara();
        knjizara.setId(1L);

        repo.save(knjizara);

        verify(entityManager).merge(knjizara);
        verify(entityManager, never()).persist(any());
    }

    @Test
    void testDeleteById() {
        Knjizara knjizara = new Knjizara();
        knjizara.setId(1L);

        when(entityManager.find(Knjizara.class, 1L)).thenReturn(knjizara);

        repo.deleteById(1L);

        verify(entityManager).find(Knjizara.class, 1L);
        verify(entityManager).remove(knjizara);
    }

    @Test
    void testDeleteByIdNePostoji() {
        when(entityManager.find(Knjizara.class, 999L)).thenReturn(null);

        repo.deleteById(999L);

        verify(entityManager).find(Knjizara.class, 999L);
        verify(entityManager, never()).remove(any());
    }
}
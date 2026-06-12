package njt.mavenproject2.repository.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.Field;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import njt.mavenproject2.entity.impl.KnjigaAutor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class KnjigaAutorRepositoryTest {

    private KnjigaAutorRepository repo;
    private EntityManager entityManager;

    @BeforeEach
    void setUp() throws Exception {
        repo = new KnjigaAutorRepository();
        entityManager = mock(EntityManager.class);

        Field field = KnjigaAutorRepository.class.getDeclaredField("entityManager");
        field.setAccessible(true);
        field.set(repo, entityManager);
    }

    @Test
    void testFindAll() {
        TypedQuery<KnjigaAutor> query = mock(TypedQuery.class);

        when(entityManager.createQuery("SELECT ka FROM KnjigaAutor ka", KnjigaAutor.class))
                .thenReturn(query);
        when(query.getResultList()).thenReturn(List.of(new KnjigaAutor()));

        List<KnjigaAutor> rezultat = repo.findAll();

        assertEquals(1, rezultat.size());
        verify(query).getResultList();
    }

    @Test
    void testFindById() throws Exception {
        KnjigaAutor ka = new KnjigaAutor();
        ka.setId(1L);

        when(entityManager.find(KnjigaAutor.class, 1L)).thenReturn(ka);

        KnjigaAutor rezultat = repo.findById(1L);

        assertEquals(1L, rezultat.getId());
    }

    @Test
    void testFindByIdNePostoji() {
        when(entityManager.find(KnjigaAutor.class, 999L)).thenReturn(null);

        Exception e = assertThrows(Exception.class,
                () -> repo.findById(999L));

        assertEquals("Veza knjiga–autor nije pronađena!", e.getMessage());
    }

    @Test
    void testSavePersist() {
        KnjigaAutor ka = new KnjigaAutor();

        repo.save(ka);

        verify(entityManager).persist(ka);
        verify(entityManager, never()).merge(any());
    }

    @Test
    void testSaveMerge() {
        KnjigaAutor ka = new KnjigaAutor();
        ka.setId(1L);

        repo.save(ka);

        verify(entityManager).merge(ka);
        verify(entityManager, never()).persist(any());
    }

    @Test
    void testDeleteById() {
        KnjigaAutor ka = new KnjigaAutor();
        ka.setId(1L);

        when(entityManager.find(KnjigaAutor.class, 1L)).thenReturn(ka);

        repo.deleteById(1L);

        verify(entityManager).remove(ka);
    }

    @Test
    void testDeleteByIdNePostoji() {
        when(entityManager.find(KnjigaAutor.class, 999L)).thenReturn(null);

        repo.deleteById(999L);

        verify(entityManager, never()).remove(any());
    }

    @Test
    void testFindByKnjigaId() {
        TypedQuery<KnjigaAutor> query = mock(TypedQuery.class);

        when(entityManager.createQuery(
                "SELECT ka FROM KnjigaAutor ka WHERE ka.knjiga.id = :kid",
                KnjigaAutor.class))
                .thenReturn(query);
        when(query.setParameter("kid", 10L)).thenReturn(query);
        when(query.getResultList()).thenReturn(List.of(new KnjigaAutor()));

        List<KnjigaAutor> rezultat = repo.findByKnjigaId(10L);

        assertEquals(1, rezultat.size());
        verify(query).setParameter("kid", 10L);
    }
}
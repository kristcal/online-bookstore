package njt.mavenproject2.repository.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.Field;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.TypedQuery;
import njt.mavenproject2.entity.impl.KnjigaKnjizara;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class KnjigaKnjizaraRepositoryTest {

    private KnjigaKnjizaraRepository repo;
    private EntityManager entityManager;

    @BeforeEach
    void setUp() throws Exception {
        repo = new KnjigaKnjizaraRepository();
        entityManager = mock(EntityManager.class);

        Field field = KnjigaKnjizaraRepository.class.getDeclaredField("entityManager");
        field.setAccessible(true);
        field.set(repo, entityManager);
    }

    @Test
    void testFindAll() {
        TypedQuery<KnjigaKnjizara> query = mock(TypedQuery.class);

        when(entityManager.createQuery("SELECT kk FROM KnjigaKnjizara kk", KnjigaKnjizara.class))
                .thenReturn(query);
        when(query.getResultList()).thenReturn(List.of(new KnjigaKnjizara()));

        List<KnjigaKnjizara> rezultat = repo.findAll();

        assertEquals(1, rezultat.size());
    }

    @Test
    void testFindById() throws Exception {
        KnjigaKnjizara kk = new KnjigaKnjizara();
        kk.setId(1L);

        when(entityManager.find(KnjigaKnjizara.class, 1L)).thenReturn(kk);

        KnjigaKnjizara rezultat = repo.findById(1L);

        assertEquals(1L, rezultat.getId());
    }

    @Test
    void testFindByIdNePostoji() {
        when(entityManager.find(KnjigaKnjizara.class, 999L)).thenReturn(null);

        Exception e = assertThrows(Exception.class,
                () -> repo.findById(999L));

        assertEquals("Veza knjiga–knjižara nije pronađena!", e.getMessage());
    }

    @Test
    void testSavePersist() {
        KnjigaKnjizara kk = new KnjigaKnjizara();

        repo.save(kk);

        verify(entityManager).persist(kk);
        verify(entityManager, never()).merge(any());
    }

    @Test
    void testSaveMerge() {
        KnjigaKnjizara kk = new KnjigaKnjizara();
        kk.setId(1L);

        repo.save(kk);

        verify(entityManager).merge(kk);
        verify(entityManager, never()).persist(any());
    }

    @Test
    void testDeleteById() {
        KnjigaKnjizara kk = new KnjigaKnjizara();
        kk.setId(1L);

        when(entityManager.find(KnjigaKnjizara.class, 1L)).thenReturn(kk);

        repo.deleteById(1L);

        verify(entityManager).remove(kk);
    }

    @Test
    void testDeleteByIdNePostoji() {
        when(entityManager.find(KnjigaKnjizara.class, 999L)).thenReturn(null);

        repo.deleteById(999L);

        verify(entityManager, never()).remove(any());
    }

    @Test
    void testFindByKnjigaId() {
        TypedQuery<KnjigaKnjizara> query = mock(TypedQuery.class);

        when(entityManager.createQuery(
                "SELECT kk FROM KnjigaKnjizara kk WHERE kk.knjiga.id = :kid",
                KnjigaKnjizara.class))
                .thenReturn(query);
        when(query.setParameter("kid", 10L)).thenReturn(query);
        when(query.getResultList()).thenReturn(List.of(new KnjigaKnjizara()));

        List<KnjigaKnjizara> rezultat = repo.findByKnjigaId(10L);

        assertEquals(1, rezultat.size());
        verify(query).setParameter("kid", 10L);
    }

    @Test
    void testFindByKnjigaIdForUpdate() {
        TypedQuery<KnjigaKnjizara> query = mock(TypedQuery.class);

        when(entityManager.createQuery(anyString(), eq(KnjigaKnjizara.class)))
                .thenReturn(query);
        when(query.setParameter("kid", 10L)).thenReturn(query);
        when(query.setLockMode(LockModeType.PESSIMISTIC_WRITE)).thenReturn(query);
        when(query.getResultList()).thenReturn(List.of(new KnjigaKnjizara()));

        List<KnjigaKnjizara> rezultat = repo.findByKnjigaIdForUpdate(10L);

        assertEquals(1, rezultat.size());
        verify(query).setParameter("kid", 10L);
        verify(query).setLockMode(LockModeType.PESSIMISTIC_WRITE);
    }
}
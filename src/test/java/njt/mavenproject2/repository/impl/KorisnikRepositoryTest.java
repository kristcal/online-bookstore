package njt.mavenproject2.repository.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.Field;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import njt.mavenproject2.entity.impl.Korisnik;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class KorisnikRepositoryTest {

    private KorisnikRepository repo;
    private EntityManager entityManager;

    @BeforeEach
    void setUp() throws Exception {
        repo = new KorisnikRepository();
        entityManager = mock(EntityManager.class);

        Field field = KorisnikRepository.class.getDeclaredField("entityManager");
        field.setAccessible(true);
        field.set(repo, entityManager);
    }

    @Test
    void testFindAll() {
        TypedQuery<Korisnik> query = mock(TypedQuery.class);

        Korisnik k1 = new Korisnik();
        k1.setId(1L);

        Korisnik k2 = new Korisnik();
        k2.setId(2L);

        when(entityManager.createQuery("SELECT k FROM Korisnik k", Korisnik.class))
                .thenReturn(query);
        when(query.getResultList()).thenReturn(List.of(k1, k2));

        List<Korisnik> rezultat = repo.findAll();

        assertEquals(2, rezultat.size());
        assertTrue(rezultat.contains(k1));
        assertTrue(rezultat.contains(k2));

        verify(entityManager).createQuery("SELECT k FROM Korisnik k", Korisnik.class);
        verify(query).getResultList();
    }

    @Test
    void testFindById() throws Exception {
        Korisnik korisnik = new Korisnik();
        korisnik.setId(1L);

        when(entityManager.find(Korisnik.class, 1L)).thenReturn(korisnik);

        Korisnik rezultat = repo.findById(1L);

        assertEquals(1L, rezultat.getId());
        verify(entityManager).find(Korisnik.class, 1L);
    }

    @Test
    void testFindByIdNePostoji() {
        when(entityManager.find(Korisnik.class, 999L)).thenReturn(null);

        Exception e = assertThrows(Exception.class,
                () -> repo.findById(999L));

        assertEquals("Korisnik nije pronađen!", e.getMessage());
        verify(entityManager).find(Korisnik.class, 999L);
    }

    @Test
    void testSavePersist() {
        Korisnik korisnik = new Korisnik();
        korisnik.setId(null);

        repo.save(korisnik);

        verify(entityManager).persist(korisnik);
        verify(entityManager, never()).merge(any());
    }

    @Test
    void testSaveMerge() {
        Korisnik korisnik = new Korisnik();
        korisnik.setId(1L);

        repo.save(korisnik);

        verify(entityManager).merge(korisnik);
        verify(entityManager, never()).persist(any());
    }

    @Test
    void testDeleteById() {
        Korisnik korisnik = new Korisnik();
        korisnik.setId(1L);

        when(entityManager.find(Korisnik.class, 1L)).thenReturn(korisnik);

        repo.deleteById(1L);

        verify(entityManager).find(Korisnik.class, 1L);
        verify(entityManager).remove(korisnik);
    }

    @Test
    void testDeleteByIdNePostoji() {
        when(entityManager.find(Korisnik.class, 999L)).thenReturn(null);

        repo.deleteById(999L);

        verify(entityManager).find(Korisnik.class, 999L);
        verify(entityManager, never()).remove(any());
    }

    @Test
    void testFindByEmail() {
        TypedQuery<Korisnik> query = mock(TypedQuery.class);

        Korisnik korisnik = new Korisnik();
        korisnik.setEmail("test@test.com");

        when(entityManager.createQuery(
                "SELECT k FROM Korisnik k WHERE LOWER(k.email) = LOWER(:email)",
                Korisnik.class))
                .thenReturn(query);
        when(query.setParameter("email", "test@test.com")).thenReturn(query);
        when(query.getSingleResult()).thenReturn(korisnik);

        Korisnik rezultat = repo.findByEmail("test@test.com");

        assertNotNull(rezultat);
        assertEquals("test@test.com", rezultat.getEmail());

        verify(query).setParameter("email", "test@test.com");
        verify(query).getSingleResult();
    }

    @Test
    void testFindByEmailNePostoji() {
        TypedQuery<Korisnik> query = mock(TypedQuery.class);

        when(entityManager.createQuery(
                "SELECT k FROM Korisnik k WHERE LOWER(k.email) = LOWER(:email)",
                Korisnik.class))
                .thenReturn(query);
        when(query.setParameter("email", "nepostoji@test.com")).thenReturn(query);
        when(query.getSingleResult()).thenThrow(new NoResultException());

        Korisnik rezultat = repo.findByEmail("nepostoji@test.com");

        assertNull(rezultat);

        verify(query).setParameter("email", "nepostoji@test.com");
        verify(query).getSingleResult();
    }
}
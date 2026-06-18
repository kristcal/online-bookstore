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

/**
 * Test klasa za proveru funkcionalnosti klase {@link ZanrRepository}.
 *
 * Testira osnovne CRUD operacije nad žanrovima korišćenjem
 * mock objekta klase EntityManager.
 *
 * @author Korisnik
 */
class ZanrRepositoryTest {

    /**
     * Repozitorijum koji se testira.
     */
    private ZanrRepository repo;

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
        repo = new ZanrRepository();
        entityManager = mock(EntityManager.class);

        Field field = ZanrRepository.class.getDeclaredField("entityManager");
        field.setAccessible(true);
        field.set(repo, entityManager);
    }

    /**
     * Proverava uspešno pronalaženje svih žanrova.
     */
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

    /**
     * Proverava uspešno pronalaženje žanra prema identifikatoru.
     *
     * @throws Exception ukoliko žanr nije pronađen
     */
    @Test
    void testFindById() throws Exception {
        Zanr zanr = new Zanr();
        zanr.setId(1L);

        when(entityManager.find(Zanr.class, 1L)).thenReturn(zanr);

        Zanr rezultat = repo.findById(1L);

        assertEquals(1L, rezultat.getId());
        verify(entityManager).find(Zanr.class, 1L);
    }

    /**
     * Proverava ponašanje sistema kada žanr nije pronađen.
     */
    @Test
    void testFindByIdNePostoji() {
        when(entityManager.find(Zanr.class, 999L)).thenReturn(null);

        Exception e = assertThrows(Exception.class,
                () -> repo.findById(999L));

        assertEquals("Žanr nije pronađen!", e.getMessage());
        verify(entityManager).find(Zanr.class, 999L);
    }

    /**
     * Proverava čuvanje novog žanra pomoću metode persist.
     */
    @Test
    void testSavePersist() {
        Zanr zanr = new Zanr();
        zanr.setId(null);

        repo.save(zanr);

        verify(entityManager).persist(zanr);
        verify(entityManager, never()).merge(any());
    }

    /**
     * Proverava ažuriranje postojećeg žanra pomoću metode merge.
     */
    @Test
    void testSaveMerge() {
        Zanr zanr = new Zanr();
        zanr.setId(1L);

        repo.save(zanr);

        verify(entityManager).merge(zanr);
        verify(entityManager, never()).persist(any());
    }

    /**
     * Proverava uspešno brisanje žanra prema identifikatoru.
     */
    @Test
    void testDeleteById() {
        Zanr zanr = new Zanr();
        zanr.setId(1L);

        when(entityManager.find(Zanr.class, 1L)).thenReturn(zanr);

        repo.deleteById(1L);

        verify(entityManager).find(Zanr.class, 1L);
        verify(entityManager).remove(zanr);
    }

    /**
     * Proverava da se ne briše žanr koji ne postoji.
     */
    @Test
    void testDeleteByIdNePostoji() {
        when(entityManager.find(Zanr.class, 999L)).thenReturn(null);

        repo.deleteById(999L);

        verify(entityManager).find(Zanr.class, 999L);
        verify(entityManager, never()).remove(any());
    }
}
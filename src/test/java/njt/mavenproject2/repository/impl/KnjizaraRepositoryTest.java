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

/**
 * Test klasa za proveru funkcionalnosti klase {@link KnjizaraRepository}.
 *
 * Testira osnovne CRUD operacije nad knjižarama korišćenjem
 * mock objekta klase EntityManager.
 *
 * @author Korisnik
 */
class KnjizaraRepositoryTest {

    /**
     * Repozitorijum koji se testira.
     */
    private KnjizaraRepository repo;

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
        repo = new KnjizaraRepository();
        entityManager = mock(EntityManager.class);

        Field field = KnjizaraRepository.class.getDeclaredField("entityManager");
        field.setAccessible(true);
        field.set(repo, entityManager);
    }

    /**
     * Proverava uspešno pronalaženje svih knjižara.
     */
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

    /**
     * Proverava uspešno pronalaženje knjižare prema identifikatoru.
     *
     * @throws Exception ukoliko knjižara nije pronađena
     */
    @Test
    void testFindById() throws Exception {
        Knjizara knjizara = new Knjizara();
        knjizara.setId(1L);

        when(entityManager.find(Knjizara.class, 1L)).thenReturn(knjizara);

        Knjizara rezultat = repo.findById(1L);

        assertEquals(1L, rezultat.getId());
        verify(entityManager).find(Knjizara.class, 1L);
    }

    /**
     * Proverava ponašanje sistema kada knjižara nije pronađena.
     */
    @Test
    void testFindByIdNePostoji() {
        when(entityManager.find(Knjizara.class, 999L)).thenReturn(null);

        Exception e = assertThrows(Exception.class,
                () -> repo.findById(999L));

        assertEquals("Knjizara nije pronađena!", e.getMessage());
        verify(entityManager).find(Knjizara.class, 999L);
    }

    /**
     * Proverava čuvanje nove knjižare pomoću metode persist.
     */
    @Test
    void testSavePersist() {
        Knjizara knjizara = new Knjizara();
        knjizara.setId(null);

        repo.save(knjizara);

        verify(entityManager).persist(knjizara);
        verify(entityManager, never()).merge(any());
    }

    /**
     * Proverava ažuriranje postojeće knjižare pomoću metode merge.
     */
    @Test
    void testSaveMerge() {
        Knjizara knjizara = new Knjizara();
        knjizara.setId(1L);

        repo.save(knjizara);

        verify(entityManager).merge(knjizara);
        verify(entityManager, never()).persist(any());
    }

    /**
     * Proverava uspešno brisanje knjižare prema identifikatoru.
     */
    @Test
    void testDeleteById() {
        Knjizara knjizara = new Knjizara();
        knjizara.setId(1L);

        when(entityManager.find(Knjizara.class, 1L)).thenReturn(knjizara);

        repo.deleteById(1L);

        verify(entityManager).find(Knjizara.class, 1L);
        verify(entityManager).remove(knjizara);
    }

    /**
     * Proverava da se ne briše knjižara koja ne postoji.
     */
    @Test
    void testDeleteByIdNePostoji() {
        when(entityManager.find(Knjizara.class, 999L)).thenReturn(null);

        repo.deleteById(999L);

        verify(entityManager).find(Knjizara.class, 999L);
        verify(entityManager, never()).remove(any());
    }
}
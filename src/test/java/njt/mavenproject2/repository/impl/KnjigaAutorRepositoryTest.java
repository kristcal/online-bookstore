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

/**
 * Test klasa za proveru funkcionalnosti klase {@link KnjigaAutorRepository}.
 *
 * Testira osnovne CRUD operacije nad vezom između knjige i autora,
 * kao i pronalaženje autora za određenu knjigu.
 *
 * @author Korisnik
 */
class KnjigaAutorRepositoryTest {

    /**
     * Repozitorijum koji se testira.
     */
    private KnjigaAutorRepository repo;

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
        repo = new KnjigaAutorRepository();
        entityManager = mock(EntityManager.class);

        Field field = KnjigaAutorRepository.class.getDeclaredField("entityManager");
        field.setAccessible(true);
        field.set(repo, entityManager);
    }

    /**
     * Proverava uspešno pronalaženje svih veza između knjiga i autora.
     */
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

    /**
     * Proverava uspešno pronalaženje veze između knjige i autora prema identifikatoru.
     *
     * @throws Exception ukoliko veza nije pronađena
     */
    @Test
    void testFindById() throws Exception {
        KnjigaAutor ka = new KnjigaAutor();
        ka.setId(1L);

        when(entityManager.find(KnjigaAutor.class, 1L)).thenReturn(ka);

        KnjigaAutor rezultat = repo.findById(1L);

        assertEquals(1L, rezultat.getId());
    }

    /**
     * Proverava ponašanje sistema kada veza između knjige i autora ne postoji.
     */
    @Test
    void testFindByIdNePostoji() {
        when(entityManager.find(KnjigaAutor.class, 999L)).thenReturn(null);

        Exception e = assertThrows(Exception.class,
                () -> repo.findById(999L));

        assertEquals("Veza knjiga–autor nije pronađena!", e.getMessage());
    }

    /**
     * Proverava čuvanje nove veze između knjige i autora pomoću metode persist.
     */
    @Test
    void testSavePersist() {
        KnjigaAutor ka = new KnjigaAutor();

        repo.save(ka);

        verify(entityManager).persist(ka);
        verify(entityManager, never()).merge(any());
    }

    /**
     * Proverava ažuriranje postojeće veze između knjige i autora pomoću metode merge.
     */
    @Test
    void testSaveMerge() {
        KnjigaAutor ka = new KnjigaAutor();
        ka.setId(1L);

        repo.save(ka);

        verify(entityManager).merge(ka);
        verify(entityManager, never()).persist(any());
    }

    /**
     * Proverava uspešno brisanje veze između knjige i autora.
     */
    @Test
    void testDeleteById() {
        KnjigaAutor ka = new KnjigaAutor();
        ka.setId(1L);

        when(entityManager.find(KnjigaAutor.class, 1L)).thenReturn(ka);

        repo.deleteById(1L);

        verify(entityManager).remove(ka);
    }

    /**
     * Proverava da se ne briše veza koja ne postoji.
     */
    @Test
    void testDeleteByIdNePostoji() {
        when(entityManager.find(KnjigaAutor.class, 999L)).thenReturn(null);

        repo.deleteById(999L);

        verify(entityManager, never()).remove(any());
    }

    /**
     * Proverava pronalaženje svih autora povezanih sa određenom knjigom.
     */
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
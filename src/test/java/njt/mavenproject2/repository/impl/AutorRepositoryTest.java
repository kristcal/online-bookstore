package njt.mavenproject2.repository.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.Field;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import njt.mavenproject2.entity.impl.Autor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test klasa za proveru funkcionalnosti klase {@link AutorRepository}.
 *
 * Testira pronalaženje, čuvanje i brisanje autora korišćenjem
 * mock objekta klase EntityManager.
 *
 * @author Korisnik
 */
class AutorRepositoryTest {

    /**
     * Repozitorijum koji se testira.
     */
    private AutorRepository repo;

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
        repo = new AutorRepository();
        entityManager = mock(EntityManager.class);

        Field field = AutorRepository.class.getDeclaredField("entityManager");
        field.setAccessible(true);
        field.set(repo, entityManager);
    }

    /**
     * Proverava uspešno pronalaženje svih autora.
     */
    @Test
    void testFindAll() {
        TypedQuery<Autor> query = mock(TypedQuery.class);

        Autor a1 = new Autor();
        a1.setId(1L);

        Autor a2 = new Autor();
        a2.setId(2L);

        when(entityManager.createQuery("SELECT a FROM Autor a", Autor.class))
                .thenReturn(query);
        when(query.getResultList()).thenReturn(List.of(a1, a2));

        List<Autor> rezultat = repo.findAll();

        assertEquals(2, rezultat.size());
        assertTrue(rezultat.contains(a1));
        assertTrue(rezultat.contains(a2));

        verify(entityManager).createQuery("SELECT a FROM Autor a", Autor.class);
        verify(query).getResultList();
    }

    /**
     * Proverava uspešno pronalaženje autora prema identifikatoru.
     *
     * @throws Exception ukoliko autor nije pronađen
     */
    @Test
    void testFindById() throws Exception {
        Autor autor = new Autor();
        autor.setId(1L);

        when(entityManager.find(Autor.class, 1L)).thenReturn(autor);

        Autor rezultat = repo.findById(1L);

        assertEquals(1L, rezultat.getId());
        verify(entityManager).find(Autor.class, 1L);
    }

    /**
     * Proverava ponašanje sistema kada autor nije pronađen.
     */
    @Test
    void testFindByIdNePostoji() {
        when(entityManager.find(Autor.class, 999L)).thenReturn(null);

        Exception e = assertThrows(Exception.class,
                () -> repo.findById(999L));

        assertEquals("Autor nije pronađen!", e.getMessage());
        verify(entityManager).find(Autor.class, 999L);
    }

    /**
     * Proverava čuvanje novog autora pomoću metode persist.
     */
    @Test
    void testSavePersist() {
        Autor autor = new Autor();
        autor.setId(null);

        repo.save(autor);

        verify(entityManager).persist(autor);
        verify(entityManager, never()).merge(any());
    }

    /**
     * Proverava ažuriranje postojećeg autora pomoću metode merge.
     */
    @Test
    void testSaveMerge() {
        Autor autor = new Autor();
        autor.setId(1L);

        repo.save(autor);

        verify(entityManager).merge(autor);
        verify(entityManager, never()).persist(any());
    }

    /**
     * Proverava uspešno brisanje autora prema identifikatoru.
     */
    @Test
    void testDeleteById() {
        Autor autor = new Autor();
        autor.setId(1L);

        when(entityManager.find(Autor.class, 1L)).thenReturn(autor);

        repo.deleteById(1L);

        verify(entityManager).find(Autor.class, 1L);
        verify(entityManager).remove(autor);
    }

    /**
     * Proverava da se ne briše autor koji ne postoji.
     */
    @Test
    void testDeleteByIdNePostoji() {
        when(entityManager.find(Autor.class, 999L)).thenReturn(null);

        repo.deleteById(999L);

        verify(entityManager).find(Autor.class, 999L);
        verify(entityManager, never()).remove(any());
    }
}
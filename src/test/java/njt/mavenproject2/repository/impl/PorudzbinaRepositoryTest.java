package njt.mavenproject2.repository.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.Field;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import njt.mavenproject2.entity.impl.Porudzbina;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test klasa za proveru funkcionalnosti klase {@link PorudzbinaRepository}.
 *
 * Testira osnovne CRUD operacije nad porudžbinama, pronalaženje
 * porudžbina određenog korisnika i učitavanje porudžbine sa povezanim
 * stavkama.
 *
 * @author Korisnik
 */
class PorudzbinaRepositoryTest {

    /**
     * Repozitorijum koji se testira.
     */
    private PorudzbinaRepository repo;

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
        repo = new PorudzbinaRepository();
        entityManager = mock(EntityManager.class);

        Field field = PorudzbinaRepository.class.getDeclaredField("entityManager");
        field.setAccessible(true);
        field.set(repo, entityManager);
    }

    /**
     * Proverava uspešno pronalaženje svih porudžbina.
     */
    @Test
    void testFindAll() {
        TypedQuery<Porudzbina> query = mock(TypedQuery.class);

        when(entityManager.createQuery(
                "SELECT p FROM Porudzbina p ORDER BY p.datum DESC",
                Porudzbina.class))
                .thenReturn(query);
        when(query.getResultList()).thenReturn(List.of(new Porudzbina()));

        List<Porudzbina> rezultat = repo.findAll();

        assertEquals(1, rezultat.size());
    }

    /**
     * Proverava uspešno pronalaženje porudžbine prema identifikatoru.
     *
     * @throws Exception ukoliko porudžbina nije pronađena
     */
    @Test
    void testFindById() throws Exception {
        Porudzbina p = new Porudzbina();
        p.setId(1L);

        when(entityManager.find(Porudzbina.class, 1L)).thenReturn(p);

        Porudzbina rezultat = repo.findById(1L);

        assertEquals(1L, rezultat.getId());
    }

    /**
     * Proverava ponašanje sistema kada porudžbina nije pronađena.
     */
    @Test
    void testFindByIdNePostoji() {
        when(entityManager.find(Porudzbina.class, 999L)).thenReturn(null);

        Exception e = assertThrows(Exception.class,
                () -> repo.findById(999L));

        assertEquals("Porudžbina nije pronađena!", e.getMessage());
    }

    /**
     * Proverava čuvanje nove porudžbine pomoću metode persist.
     */
    @Test
    void testSavePersist() {
        Porudzbina p = new Porudzbina();

        repo.save(p);

        verify(entityManager).persist(p);
        verify(entityManager, never()).merge(any());
    }

    /**
     * Proverava ažuriranje postojeće porudžbine pomoću metode merge.
     */
    @Test
    void testSaveMerge() {
        Porudzbina p = new Porudzbina();
        p.setId(1L);

        repo.save(p);

        verify(entityManager).merge(p);
        verify(entityManager, never()).persist(any());
    }

    /**
     * Proverava uspešno brisanje porudžbine prema identifikatoru.
     */
    @Test
    void testDeleteById() {
        Porudzbina p = new Porudzbina();
        p.setId(1L);

        when(entityManager.find(Porudzbina.class, 1L)).thenReturn(p);

        repo.deleteById(1L);

        verify(entityManager).remove(p);
    }

    /**
     * Proverava da se ne briše porudžbina koja ne postoji.
     */
    @Test
    void testDeleteByIdNePostoji() {
        when(entityManager.find(Porudzbina.class, 999L)).thenReturn(null);

        repo.deleteById(999L);

        verify(entityManager, never()).remove(any());
    }

    /**
     * Proverava pronalaženje porudžbina određenog korisnika.
     */
    @Test
    void testFindByKorisnikId() {
        TypedQuery<Porudzbina> query = mock(TypedQuery.class);

        when(entityManager.createQuery(
                "SELECT p FROM Porudzbina p WHERE p.korisnik.id = :kid ORDER BY p.datum DESC",
                Porudzbina.class))
                .thenReturn(query);
        when(query.setParameter("kid", 10L)).thenReturn(query);
        when(query.getResultList()).thenReturn(List.of(new Porudzbina()));

        List<Porudzbina> rezultat = repo.findByKorisnikId(10L);

        assertEquals(1, rezultat.size());
        verify(query).setParameter("kid", 10L);
    }

    /**
     * Proverava učitavanje porudžbine zajedno sa stavkama i knjigama.
     *
     * @throws Exception ukoliko porudžbina nije pronađena
     */
    @Test
    void testFindOneFull() throws Exception {
        TypedQuery<Porudzbina> query = mock(TypedQuery.class);

        Porudzbina p = new Porudzbina();
        p.setId(1L);

        when(entityManager.createQuery(anyString(), eq(Porudzbina.class)))
                .thenReturn(query);
        when(query.setParameter("id", 1L)).thenReturn(query);
        when(query.getResultList()).thenReturn(List.of(p));

        Porudzbina rezultat = repo.findOneFull(1L);

        assertEquals(1L, rezultat.getId());
        verify(query).setParameter("id", 1L);
    }

    /**
     * Proverava ponašanje sistema kada porudžbina sa povezanim stavkama
     * nije pronađena.
     */
    @Test
    void testFindOneFullNePostoji() {
        TypedQuery<Porudzbina> query = mock(TypedQuery.class);

        when(entityManager.createQuery(anyString(), eq(Porudzbina.class)))
                .thenReturn(query);
        when(query.setParameter("id", 999L)).thenReturn(query);
        when(query.getResultList()).thenReturn(List.of());

        Exception e = assertThrows(Exception.class,
                () -> repo.findOneFull(999L));

        assertEquals("Porudžbina ne postoji", e.getMessage());
    }
}
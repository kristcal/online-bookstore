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

/**
 * Test klasa za proveru funkcionalnosti klase {@link KnjigaKnjizaraRepository}.
 *
 * Testira osnovne CRUD operacije nad vezom između knjige i knjižare,
 * pronalaženje dostupnosti za određenu knjigu i zaključavanje redova
 * prilikom izmene zaliha.
 *
 * @author Korisnik
 */
class KnjigaKnjizaraRepositoryTest {

    /**
     * Repozitorijum koji se testira.
     */
    private KnjigaKnjizaraRepository repo;

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
        repo = new KnjigaKnjizaraRepository();
        entityManager = mock(EntityManager.class);

        Field field = KnjigaKnjizaraRepository.class.getDeclaredField("entityManager");
        field.setAccessible(true);
        field.set(repo, entityManager);
    }

    /**
     * Proverava uspešno pronalaženje svih veza između knjiga i knjižara.
     */
    @Test
    void testFindAll() {
        TypedQuery<KnjigaKnjizara> query = mock(TypedQuery.class);

        when(entityManager.createQuery("SELECT kk FROM KnjigaKnjizara kk", KnjigaKnjizara.class))
                .thenReturn(query);
        when(query.getResultList()).thenReturn(List.of(new KnjigaKnjizara()));

        List<KnjigaKnjizara> rezultat = repo.findAll();

        assertEquals(1, rezultat.size());
    }

    /**
     * Proverava uspešno pronalaženje veze između knjige i knjižare prema identifikatoru.
     *
     * @throws Exception ukoliko veza nije pronađena
     */
    @Test
    void testFindById() throws Exception {
        KnjigaKnjizara kk = new KnjigaKnjizara();
        kk.setId(1L);

        when(entityManager.find(KnjigaKnjizara.class, 1L)).thenReturn(kk);

        KnjigaKnjizara rezultat = repo.findById(1L);

        assertEquals(1L, rezultat.getId());
    }

    /**
     * Proverava ponašanje sistema kada veza između knjige i knjižare ne postoji.
     */
    @Test
    void testFindByIdNePostoji() {
        when(entityManager.find(KnjigaKnjizara.class, 999L)).thenReturn(null);

        Exception e = assertThrows(Exception.class,
                () -> repo.findById(999L));

        assertEquals("Veza knjiga–knjižara nije pronađena!", e.getMessage());
    }

    /**
     * Proverava čuvanje nove veze između knjige i knjižare pomoću metode persist.
     */
    @Test
    void testSavePersist() {
        KnjigaKnjizara kk = new KnjigaKnjizara();

        repo.save(kk);

        verify(entityManager).persist(kk);
        verify(entityManager, never()).merge(any());
    }

    /**
     * Proverava ažuriranje postojeće veze između knjige i knjižare pomoću metode merge.
     */
    @Test
    void testSaveMerge() {
        KnjigaKnjizara kk = new KnjigaKnjizara();
        kk.setId(1L);

        repo.save(kk);

        verify(entityManager).merge(kk);
        verify(entityManager, never()).persist(any());
    }

    /**
     * Proverava uspešno brisanje veze između knjige i knjižare.
     */
    @Test
    void testDeleteById() {
        KnjigaKnjizara kk = new KnjigaKnjizara();
        kk.setId(1L);

        when(entityManager.find(KnjigaKnjizara.class, 1L)).thenReturn(kk);

        repo.deleteById(1L);

        verify(entityManager).remove(kk);
    }

    /**
     * Proverava da se ne briše veza koja ne postoji.
     */
    @Test
    void testDeleteByIdNePostoji() {
        when(entityManager.find(KnjigaKnjizara.class, 999L)).thenReturn(null);

        repo.deleteById(999L);

        verify(entityManager, never()).remove(any());
    }

    /**
     * Proverava pronalaženje dostupnosti za određenu knjigu.
     */
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

    /**
     * Proverava pronalaženje dostupnosti knjige uz zaključavanje redova za izmenu.
     */
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
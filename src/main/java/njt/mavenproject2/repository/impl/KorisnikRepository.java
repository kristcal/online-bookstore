package njt.mavenproject2.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import njt.mavenproject2.entity.impl.Korisnik;
import njt.mavenproject2.repository.MyAppRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Repozitorijum za rad sa entitetom Korisnik.
 *
 * Omogućava osnovne CRUD operacije nad korisnicima, kao i pronalaženje
 * korisnika prema email adresi.
 *
 * @author Korisnik
 */
@Repository
public class KorisnikRepository implements MyAppRepository<Korisnik, Long> {

    /**
     * EntityManager za komunikaciju sa bazom podataka.
     */
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Vraća listu svih korisnika.
     *
     * @return lista svih korisnika
     */
    @Override
    public List<Korisnik> findAll() {
        return entityManager
                .createQuery("SELECT k FROM Korisnik k", Korisnik.class)
                .getResultList();
    }

    /**
     * Pronalazi korisnika prema identifikatoru.
     *
     * @param id identifikator korisnika
     * @return pronađeni korisnik
     * @throws Exception ukoliko korisnik nije pronađen
     */
    @Override
    public Korisnik findById(Long id) throws Exception {
        Korisnik korisnik = entityManager.find(Korisnik.class, id);

        if (korisnik == null) {
            throw new Exception("Korisnik nije pronađen!");
        }

        return korisnik;
    }

    /**
     * Čuva novog korisnika ili ažurira postojećeg.
     *
     * @param entity korisnik koji se čuva
     */
    @Transactional
    @Override
    public void save(Korisnik entity) {
        if (entity.getId() == null) {
            entityManager.persist(entity);
        } else {
            entityManager.merge(entity);
        }
    }

    /**
     * Briše korisnika prema identifikatoru.
     *
     * @param id identifikator korisnika
     */
    @Transactional
    @Override
    public void deleteById(Long id) {
        Korisnik korisnik = entityManager.find(Korisnik.class, id);

        if (korisnik != null) {
            entityManager.remove(korisnik);
        }
    }

    /**
     * Pronalazi korisnika prema email adresi.
     *
     * Metoda se koristi pri prijavljivanju korisnika u sistem.
     * Pretraga nije osetljiva na velika i mala slova.
     *
     * @param email email adresa korisnika
     * @return korisnik sa zadatom email adresom ili {@code null} ako ne postoji
     */
    public Korisnik findByEmail(String email) {
        try {
            return entityManager.createQuery(
                    "SELECT k FROM Korisnik k WHERE LOWER(k.email) = LOWER(:email)",
                    Korisnik.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
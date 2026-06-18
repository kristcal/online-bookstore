package njt.mavenproject2.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import njt.mavenproject2.entity.impl.Zanr;
import njt.mavenproject2.repository.MyAppRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Repozitorijum za rad sa entitetom Zanr.
 *
 * Omogućava osnovne CRUD operacije nad žanrovima korišćenjem
 * JPA EntityManager-a.
 *
 * @author Korisnik
 */
@Repository
public class ZanrRepository implements MyAppRepository<Zanr, Long> {

    /**
     * EntityManager za komunikaciju sa bazom podataka.
     */
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Vraća listu svih žanrova.
     *
     * @return lista svih žanrova
     */
    @Override
    public List<Zanr> findAll() {
        return entityManager
                .createQuery("SELECT z FROM Zanr z", Zanr.class)
                .getResultList();
    }

    /**
     * Pronalazi žanr prema identifikatoru.
     *
     * @param id identifikator žanra
     * @return pronađeni žanr
     * @throws Exception ukoliko žanr nije pronađen
     */
    @Override
    public Zanr findById(Long id) throws Exception {
        Zanr zanr = entityManager.find(Zanr.class, id);

        if (zanr == null) {
            throw new Exception("Žanr nije pronađen!");
        }

        return zanr;
    }

    /**
     * Čuva novi žanr ili ažurira postojeći.
     *
     * @param entity žanr koji se čuva
     */
    @Override
    @Transactional
    public void save(Zanr entity) {
        if (entity.getId() == null) {
            entityManager.persist(entity);
        } else {
            entityManager.merge(entity);
        }
    }

    /**
     * Briše žanr prema identifikatoru.
     *
     * @param id identifikator žanra
     */
    @Override
    @Transactional
    public void deleteById(Long id) {
        Zanr zanr = entityManager.find(Zanr.class, id);

        if (zanr != null) {
            entityManager.remove(zanr);
        }
    }
}
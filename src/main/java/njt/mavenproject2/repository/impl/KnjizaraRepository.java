package njt.mavenproject2.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import njt.mavenproject2.entity.impl.Knjizara;
import njt.mavenproject2.repository.MyAppRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Repozitorijum za rad sa entitetom Knjizara.
 *
 * Omogućava osnovne CRUD operacije nad knjižarama korišćenjem
 * JPA EntityManager-a.
 *
 * @author Korisnik
 */
@Repository
public class KnjizaraRepository implements MyAppRepository<Knjizara, Long> {

    /**
     * EntityManager za komunikaciju sa bazom podataka.
     */
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Vraća listu svih knjižara.
     *
     * @return lista svih knjižara
     */
    @Override
    public List<Knjizara> findAll() {
        return entityManager
                .createQuery("SELECT k FROM Knjizara k", Knjizara.class)
                .getResultList();
    }

    /**
     * Pronalazi knjižaru prema identifikatoru.
     *
     * @param id identifikator knjižare
     * @return pronađena knjižara
     * @throws Exception ukoliko knjižara nije pronađena
     */
    @Override
    public Knjizara findById(Long id) throws Exception {
        Knjizara knjizara = entityManager.find(Knjizara.class, id);

        if (knjizara == null) {
            throw new Exception("Knjizara nije pronađena!");
        }

        return knjizara;
    }

    /**
     * Čuva novu knjižaru ili ažurira postojeću.
     *
     * @param entity knjižara koja se čuva
     */
    @Override
    @Transactional
    public void save(Knjizara entity) {
        if (entity.getId() == null) {
            entityManager.persist(entity);
        } else {
            entityManager.merge(entity);
        }
    }

    /**
     * Briše knjižaru prema identifikatoru.
     *
     * @param id identifikator knjižare
     */
    @Override
    @Transactional
    public void deleteById(Long id) {
        Knjizara knjizara = entityManager.find(Knjizara.class, id);

        if (knjizara != null) {
            entityManager.remove(knjizara);
        }
    }
}
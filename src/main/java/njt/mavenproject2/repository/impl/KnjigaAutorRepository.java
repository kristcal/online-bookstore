package njt.mavenproject2.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import njt.mavenproject2.entity.impl.KnjigaAutor;
import njt.mavenproject2.repository.MyAppRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Repozitorijum za rad sa vezama između knjiga i autora.
 *
 * Omogućava osnovne CRUD operacije nad entitetom KnjigaAutor,
 * kao i pronalaženje autora za određenu knjigu.
 *
 * @author Korisnik
 */
@Repository
public class KnjigaAutorRepository implements MyAppRepository<KnjigaAutor, Long> {

    /**
     * EntityManager za komunikaciju sa bazom podataka.
     */
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Vraća listu svih veza između knjiga i autora.
     *
     * @return lista svih veza
     */
    @Override
    public List<KnjigaAutor> findAll() {
        return entityManager
                .createQuery("SELECT ka FROM KnjigaAutor ka", KnjigaAutor.class)
                .getResultList();
    }

    /**
     * Pronalazi vezu između knjige i autora prema identifikatoru.
     *
     * @param id identifikator veze
     * @return pronađena veza
     * @throws Exception ukoliko veza nije pronađena
     */
    @Override
    public KnjigaAutor findById(Long id) throws Exception {
        KnjigaAutor ka = entityManager.find(KnjigaAutor.class, id);

        if (ka == null) {
            throw new Exception("Veza knjiga–autor nije pronađena!");
        }

        return ka;
    }

    /**
     * Čuva novu vezu između knjige i autora ili ažurira postojeću.
     *
     * @param entity veza koja se čuva
     */
    @Override
    @Transactional
    public void save(KnjigaAutor entity) {
        if (entity.getId() == null) {
            entityManager.persist(entity);
        } else {
            entityManager.merge(entity);
        }
    }

    /**
     * Briše vezu između knjige i autora prema identifikatoru.
     *
     * @param id identifikator veze
     */
    @Override
    @Transactional
    public void deleteById(Long id) {
        KnjigaAutor ka = entityManager.find(KnjigaAutor.class, id);

        if (ka != null) {
            entityManager.remove(ka);
        }
    }

    /**
     * Pronalazi sve autore povezane sa određenom knjigom.
     *
     * @param knjigaId identifikator knjige
     * @return lista veza između knjige i autora
     */
    public List<KnjigaAutor> findByKnjigaId(Long knjigaId) {
        return entityManager.createQuery(
                "SELECT ka FROM KnjigaAutor ka WHERE ka.knjiga.id = :kid",
                KnjigaAutor.class)
                .setParameter("kid", knjigaId)
                .getResultList();
    }
}
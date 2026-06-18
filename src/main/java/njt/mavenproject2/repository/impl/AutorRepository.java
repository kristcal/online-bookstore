package njt.mavenproject2.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import njt.mavenproject2.entity.impl.Autor;
import njt.mavenproject2.repository.MyAppRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Repozitorijum za rad sa entitetom Autor.
 *
 * Omogućava osnovne CRUD operacije nad autorima korišćenjem
 * JPA EntityManager-a.
 *
 * @author Korisnik
 */
@Repository
public class AutorRepository implements MyAppRepository<Autor, Long> {

    /**
     * EntityManager za komunikaciju sa bazom podataka.
     */
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Vraća listu svih autora.
     *
     * @return lista svih autora
     */
    @Override
    public List<Autor> findAll() {
        return entityManager
                .createQuery("SELECT a FROM Autor a", Autor.class)
                .getResultList();
    }

    /**
     * Pronalazi autora prema identifikatoru.
     *
     * @param id identifikator autora
     * @return pronađeni autor
     * @throws Exception ukoliko autor nije pronađen
     */
    @Override
    public Autor findById(Long id) throws Exception {
        Autor autor = entityManager.find(Autor.class, id);

        if (autor == null) {
            throw new Exception("Autor nije pronađen!");
        }

        return autor;
    }

    /**
     * Čuva novog autora ili ažurira postojećeg.
     *
     * @param entity autor koji se čuva
     */
    @Override
    @Transactional
    public void save(Autor entity) {
        if (entity.getId() == null) {
            entityManager.persist(entity);
        } else {
            entityManager.merge(entity);
        }
    }

    /**
     * Briše autora prema identifikatoru.
     *
     * @param id identifikator autora
     */
    @Override
    @Transactional
    public void deleteById(Long id) {
        Autor autor = entityManager.find(Autor.class, id);

        if (autor != null) {
            entityManager.remove(autor);
        }
    }
}
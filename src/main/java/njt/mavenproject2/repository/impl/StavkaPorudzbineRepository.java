package njt.mavenproject2.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import njt.mavenproject2.entity.impl.StavkaPorudzbine;
import njt.mavenproject2.repository.MyAppRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Repozitorijum za rad sa entitetom StavkaPorudzbine.
 *
 * Omogućava osnovne CRUD operacije nad stavkama porudžbine, pronalaženje
 * stavki za određenu porudžbinu i određivanje najvećeg rednog broja stavke.
 *
 * @author Korisnik
 */
@Repository
public class StavkaPorudzbineRepository implements MyAppRepository<StavkaPorudzbine, Long> {

    /**
     * EntityManager za komunikaciju sa bazom podataka.
     */
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Vraća listu svih stavki porudžbine.
     *
     * @return lista svih stavki porudžbine
     */
    @Override
    public List<StavkaPorudzbine> findAll() {
        return entityManager
                .createQuery("SELECT s FROM StavkaPorudzbine s", StavkaPorudzbine.class)
                .getResultList();
    }

    /**
     * Pronalazi stavku porudžbine prema identifikatoru.
     *
     * @param id identifikator stavke porudžbine
     * @return pronađena stavka porudžbine
     * @throws Exception ukoliko stavka porudžbine nije pronađena
     */
    @Override
    public StavkaPorudzbine findById(Long id) throws Exception {
        StavkaPorudzbine s = entityManager.find(StavkaPorudzbine.class, id);

        if (s == null) {
            throw new Exception("Stavka porudžbine nije pronađena!");
        }

        return s;
    }

    /**
     * Čuva novu stavku porudžbine ili ažurira postojeću.
     *
     * @param entity stavka porudžbine koja se čuva
     */
    @Override
    @Transactional
    public void save(StavkaPorudzbine entity) {
        if (entity.getId() == null) {
            entityManager.persist(entity);
        } else {
            entityManager.merge(entity);
        }
    }

    /**
     * Briše stavku porudžbine prema identifikatoru.
     *
     * @param id identifikator stavke porudžbine
     */
    @Override
    @Transactional
    public void deleteById(Long id) {
        StavkaPorudzbine s = entityManager.find(StavkaPorudzbine.class, id);

        if (s != null) {
            entityManager.remove(s);
        }
    }

    /**
     * Pronalazi sve stavke koje pripadaju određenoj porudžbini.
     *
     * @param porudzbinaId identifikator porudžbine
     * @return lista stavki zadate porudžbine
     */
    public List<StavkaPorudzbine> findByPorudzbinaId(Long porudzbinaId) {
        return entityManager.createQuery(
                "SELECT s FROM StavkaPorudzbine s WHERE s.porudzbina.id = :pid",
                StavkaPorudzbine.class)
                .setParameter("pid", porudzbinaId)
                .getResultList();
    }

    /**
     * Vraća najveći redni broj stavke u okviru određene porudžbine.
     *
     * Ako porudžbina nema stavke, vraća se vrednost 0.
     *
     * @param porudzbinaId identifikator porudžbine
     * @return najveći redni broj stavke
     */
    public Integer findMaxRbForPorudzbina(Long porudzbinaId) {
        return entityManager.createQuery(
                "SELECT COALESCE(MAX(s.rb), 0) FROM StavkaPorudzbine s "
                + "WHERE s.porudzbina.id = :pid",
                Integer.class)
                .setParameter("pid", porudzbinaId)
                .getSingleResult();
    }
}
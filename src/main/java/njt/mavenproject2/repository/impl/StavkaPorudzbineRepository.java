package njt.mavenproject2.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import njt.mavenproject2.entity.impl.StavkaPorudzbine;
import njt.mavenproject2.repository.MyAppRepository;
import org.springframework.stereotype.Repository;

/**
 * Repozitorijum za rad sa stavkama porudžbine.
 *
 * Omogućava osnovne CRUD operacije nad entitetom {@link StavkaPorudzbine},
 * kao i dodatne operacije za pronalaženje stavki određene porudžbine i
 * određivanje najvećeg rednog broja stavke u okviru porudžbine.
 *
 * Klasa koristi {@link EntityManager} za komunikaciju sa bazom podataka.
 *
 * @author Korisnik
 */
@Repository
public class StavkaPorudzbineRepository implements MyAppRepository<StavkaPorudzbine, Long> {

    /**
     * EntityManager koji se koristi za izvršavanje JPA operacija nad bazom.
     */
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Vraća sve stavke porudžbine iz baze.
     *
     * @return lista svih stavki porudžbine
     */
    @Override
    public List<StavkaPorudzbine> findAll() {
        return entityManager.createQuery("SELECT s FROM StavkaPorudzbine s", StavkaPorudzbine.class).getResultList();
    }

    /**
     * Pronalazi stavku porudžbine prema identifikatoru.
     *
     * @param id identifikator stavke porudžbine
     * @return pronađena stavka porudžbine
     * @throws Exception ukoliko stavka porudžbine sa zadatim identifikatorom ne postoji
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
     * Ako stavka nema identifikator, poziva se {@code persist}. Ako stavka
     * već ima identifikator, poziva se {@code merge}.
     *
     * @param entity stavka porudžbine koja se čuva ili ažurira
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
     * Ako stavka sa zadatim identifikatorom ne postoji, metoda ne baca izuzetak.
     *
     * @param id identifikator stavke porudžbine koja se briše
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
     * Vraća sve stavke koje pripadaju određenoj porudžbini.
     *
     * @param porudzbinaId identifikator porudžbine
     * @return lista stavki koje pripadaju zadatoj porudžbini
     */
    public List<StavkaPorudzbine> findByPorudzbinaId(Long porudzbinaId) {
        return entityManager.createQuery(
                "SELECT s FROM StavkaPorudzbine s WHERE s.porudzbina.id=:pid",
                StavkaPorudzbine.class
        )
                .setParameter("pid", porudzbinaId)
                .getResultList();
    }

    /**
     * Pronalazi najveći redni broj stavke u okviru određene porudžbine.
     *
     * Koristi se prilikom dodavanja nove stavke, kako bi se automatski
     * odredio sledeći redni broj. Ako porudžbina nema nijednu stavku,
     * vraća se vrednost 0.
     *
     * @param porudzbinaId identifikator porudžbine
     * @return najveći redni broj stavke za zadatu porudžbinu
     */
    public Integer findMaxRbForPorudzbina(Long porudzbinaId) {
        Integer max = entityManager.createQuery(
                "SELECT COALESCE(MAX(s.rb), 0) FROM StavkaPorudzbine s WHERE s.porudzbina.id = :pid",
                Integer.class
        )
                .setParameter("pid", porudzbinaId)
                .getSingleResult();
        return max;
    }
}
package njt.mavenproject2.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import njt.mavenproject2.entity.impl.Porudzbina;
import njt.mavenproject2.repository.MyAppRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Repozitorijum za rad sa entitetom Porudzbina.
 *
 * Omogućava osnovne CRUD operacije nad porudžbinama, pronalaženje
 * porudžbina određenog korisnika i učitavanje detalja porudžbine sa stavkama.
 *
 * @author Korisnik
 */
@Repository
public class PorudzbinaRepository implements MyAppRepository<Porudzbina, Long> {

    /**
     * EntityManager za komunikaciju sa bazom podataka.
     */
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Vraća listu svih porudžbina sortiranih po datumu opadajuće.
     *
     * @return lista svih porudžbina
     */
    @Override
    public List<Porudzbina> findAll() {
        return entityManager
                .createQuery("SELECT p FROM Porudzbina p ORDER BY p.datum DESC", Porudzbina.class)
                .getResultList();
    }

    /**
     * Pronalazi porudžbinu prema identifikatoru.
     *
     * @param id identifikator porudžbine
     * @return pronađena porudžbina
     * @throws Exception ukoliko porudžbina nije pronađena
     */
    @Override
    public Porudzbina findById(Long id) throws Exception {
        Porudzbina p = entityManager.find(Porudzbina.class, id);

        if (p == null) {
            throw new Exception("Porudžbina nije pronađena!");
        }

        return p;
    }

    /**
     * Čuva novu porudžbinu ili ažurira postojeću.
     *
     * @param entity porudžbina koja se čuva
     */
    @Override
    @Transactional
    public void save(Porudzbina entity) {
        if (entity.getId() == null) {
            entityManager.persist(entity);
        } else {
            entityManager.merge(entity);
        }
    }

    /**
     * Briše porudžbinu prema identifikatoru.
     *
     * @param id identifikator porudžbine
     */
    @Override
    @Transactional
    public void deleteById(Long id) {
        Porudzbina p = entityManager.find(Porudzbina.class, id);

        if (p != null) {
            entityManager.remove(p);
        }
    }

    /**
     * Pronalazi sve porudžbine određenog korisnika.
     *
     * @param korisnikId identifikator korisnika
     * @return lista porudžbina korisnika
     */
    public List<Porudzbina> findByKorisnikId(Long korisnikId) {
        return entityManager.createQuery(
                "SELECT p FROM Porudzbina p WHERE p.korisnik.id = :kid ORDER BY p.datum DESC",
                Porudzbina.class)
                .setParameter("kid", korisnikId)
                .getResultList();
    }

    /**
     * Pronalazi porudžbinu zajedno sa stavkama i knjigama.
     *
     * Metoda učitava povezane stavke i knjige kako bi se izbegli dodatni
     * pojedinačni upiti za svaku stavku.
     *
     * @param id identifikator porudžbine
     * @return porudžbina sa učitanim stavkama i knjigama
     * @throws Exception ukoliko porudžbina nije pronađena
     */
    @Transactional(readOnly = true)
    public Porudzbina findOneFull(Long id) throws Exception {
        List<Porudzbina> list = entityManager.createQuery("""
            select distinct p from Porudzbina p
            left join fetch p.stavke s
            left join fetch s.knjiga k
            where p.id = :id
        """, Porudzbina.class)
                .setParameter("id", id)
                .getResultList();

        if (list.isEmpty()) {
            throw new Exception("Porudžbina ne postoji");
        }

        return list.get(0);
    }
}
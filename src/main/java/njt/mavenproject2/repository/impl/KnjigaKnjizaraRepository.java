package njt.mavenproject2.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import njt.mavenproject2.entity.impl.KnjigaKnjizara;
import njt.mavenproject2.repository.MyAppRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Repozitorijum za rad sa dostupnošću knjiga u knjižarama.
 *
 * Omogućava osnovne CRUD operacije nad entitetom KnjigaKnjizara,
 * pronalaženje dostupnosti za određenu knjigu i zaključavanje redova
 * prilikom izmene zaliha.
 *
 * @author Korisnik
 */
@Repository
public class KnjigaKnjizaraRepository implements MyAppRepository<KnjigaKnjizara, Long> {

    /**
     * EntityManager za komunikaciju sa bazom podataka.
     */
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Vraća listu svih veza između knjiga i knjižara.
     *
     * @return lista svih veza između knjiga i knjižara
     */
    @Override
    public List<KnjigaKnjizara> findAll() {
        return entityManager
                .createQuery("SELECT kk FROM KnjigaKnjizara kk", KnjigaKnjizara.class)
                .getResultList();
    }

    /**
     * Pronalazi vezu između knjige i knjižare prema identifikatoru.
     *
     * @param id identifikator veze
     * @return pronađena veza između knjige i knjižare
     * @throws Exception ukoliko veza nije pronađena
     */
    @Override
    public KnjigaKnjizara findById(Long id) throws Exception {
        KnjigaKnjizara kk = entityManager.find(KnjigaKnjizara.class, id);

        if (kk == null) {
            throw new Exception("Veza knjiga–knjižara nije pronađena!");
        }

        return kk;
    }

    /**
     * Čuva novu vezu između knjige i knjižare ili ažurira postojeću.
     *
     * @param entity veza između knjige i knjižare koja se čuva
     */
    @Override
    @Transactional
    public void save(KnjigaKnjizara entity) {
        if (entity.getId() == null) {
            entityManager.persist(entity);
        } else {
            entityManager.merge(entity);
        }
    }

    /**
     * Briše vezu između knjige i knjižare prema identifikatoru.
     *
     * @param id identifikator veze koja se briše
     */
    @Override
    @Transactional
    public void deleteById(Long id) {
        KnjigaKnjizara kk = entityManager.find(KnjigaKnjizara.class, id);

        if (kk != null) {
            entityManager.remove(kk);
        }
    }

    /**
     * Pronalazi sve knjižare u kojima je određena knjiga dostupna.
     *
     * @param knjigaId identifikator knjige
     * @return lista dostupnosti knjige u knjižarama
     */
    public List<KnjigaKnjizara> findByKnjigaId(Long knjigaId) {
        return entityManager.createQuery(
                "SELECT kk FROM KnjigaKnjizara kk WHERE kk.knjiga.id = :kid",
                KnjigaKnjizara.class)
                .setParameter("kid", knjigaId)
                .getResultList();
    }

    /**
     * Pronalazi dostupnost knjige i zaključava pronađene redove za izmenu.
     *
     * Metoda se koristi prilikom skidanja zaliha, kako bi se sprečilo da više
     * transakcija istovremeno menja iste količine.
     *
     * @param knjigaId identifikator knjige
     * @return lista zaključanih redova dostupnosti knjige
     */
    @Transactional
    public List<KnjigaKnjizara> findByKnjigaIdForUpdate(Long knjigaId) {
        var q = entityManager.createQuery("""
            select kk from KnjigaKnjizara kk
            join fetch kk.knjiga k
            left join fetch kk.knjizara j
            where k.id = :kid
            order by kk.kolicina desc
        """, KnjigaKnjizara.class);

        q.setParameter("kid", knjigaId);
        q.setLockMode(LockModeType.PESSIMISTIC_WRITE);

        return q.getResultList();
    }
}
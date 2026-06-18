package njt.mavenproject2.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import njt.mavenproject2.entity.impl.Knjiga;
import njt.mavenproject2.repository.MyAppRepository;
import org.springframework.stereotype.Repository;

/**
 * Repozitorijum za rad sa entitetom Knjiga.
 *
 * Omogućava osnovne CRUD operacije nad knjigama, kao i pretragu
 * po žanru, ceni, tekstualnim kriterijumima i učitavanje kompletnih
 * podataka o knjizi sa povezanim entitetima.
 *
 * @author Korisnik
 */
@Repository
public class KnjigaRepository implements MyAppRepository<Knjiga, Long> {

    /**
     * EntityManager za komunikaciju sa bazom podataka.
     */
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Vraća listu svih knjiga.
     *
     * @return lista svih knjiga
     */
    @Override
    public List<Knjiga> findAll() {
        return entityManager
                .createQuery("SELECT k FROM Knjiga k", Knjiga.class)
                .getResultList();
    }

    /**
     * Pronalazi knjigu prema identifikatoru.
     *
     * @param id identifikator knjige
     * @return pronađena knjiga
     * @throws Exception ukoliko knjiga nije pronađena
     */
    @Override
    public Knjiga findById(Long id) throws Exception {
        Knjiga knjiga = entityManager.find(Knjiga.class, id);

        if (knjiga == null) {
            throw new Exception("Knjiga nije pronadjena!");
        }

        return knjiga;
    }

    /**
     * Čuva novu knjigu ili ažurira postojeću.
     *
     * @param entity knjiga koja se čuva
     */
    @Override
    public void save(Knjiga entity) {
        if (entity.getId() == null) {
            entityManager.persist(entity);
        } else {
            entityManager.merge(entity);
        }
    }

    /**
     * Briše knjigu prema identifikatoru.
     *
     * @param id identifikator knjige
     */
    @Override
    public void deleteById(Long id) {
        Knjiga knjiga = entityManager.find(Knjiga.class, id);

        if (knjiga != null) {
            entityManager.remove(knjiga);
        }
    }

    /**
     * Pronalazi sve knjige određenog žanra.
     *
     * @param zanrId identifikator žanra
     * @return lista knjiga zadatog žanra
     */
    public List<Knjiga> findByGenre(Long zanrId) {
        return entityManager.createQuery(
                "SELECT k FROM Knjiga k WHERE k.zanr.id = :z ORDER BY k.id DESC",
                Knjiga.class)
                .setParameter("z", zanrId)
                .getResultList();
    }

    /**
     * Pronalazi sve knjige čija je cena manja ili jednaka zadatoj vrednosti.
     *
     * @param max maksimalna cena knjige
     * @return lista pronađenih knjiga
     */
    public List<Knjiga> findCheaperThan(Double max) {
        return entityManager.createQuery(
                "SELECT k FROM Knjiga k WHERE k.cena <= :m ORDER BY k.cena ASC",
                Knjiga.class)
                .setParameter("m", max)
                .getResultList();
    }

    /**
     * Vrši pretragu knjiga po nazivu ili ISBN-u.
     *
     * Dodatno je moguće filtriranje po žanru i maksimalnoj ceni.
     *
     * @param q tekst za pretragu
     * @param zanrId identifikator žanra
     * @param max maksimalna cena knjige
     * @return lista pronađenih knjiga
     */
    public List<Knjiga> search(String q, Long zanrId, Double max) {

        String base = """
            SELECT k FROM Knjiga k
            WHERE (LOWER(k.naziv) LIKE LOWER(CONCAT('%', :q, '%'))
                   OR LOWER(k.isbn) LIKE LOWER(CONCAT('%', :q, '%')))
        """;

        String andGenre = (zanrId != null)
                ? " AND k.zanr.id = :z "
                : "";

        String andMax = (max != null)
                ? " AND k.cena <= :m "
                : "";

        var query = entityManager.createQuery(
                base + andGenre + andMax + " ORDER BY k.id DESC",
                Knjiga.class)
                .setParameter("q", q);

        if (zanrId != null) {
            query.setParameter("z", zanrId);
        }

        if (max != null) {
            query.setParameter("m", max);
        }

        return query.getResultList();
    }

    /**
     * Pronalazi knjigu zajedno sa svim povezanim podacima.
     *
     * Učitavaju se žanr, autori i njihove veze kako bi se izbegli problemi
     * sa lenjim učitavanjem (lazy loading).
     *
     * @param id identifikator knjige
     * @return knjiga sa učitanim povezanim entitetima
     * @throws Exception ukoliko knjiga nije pronađena
     */
    public Knjiga findOneFull(Long id) throws Exception {

        String jpql = """
            select distinct k
            from Knjiga k
            left join fetch k.zanr z
            left join fetch k.autori ka
            left join fetch ka.autor a
            where k.id = :id
        """;

        List<Knjiga> list = entityManager
                .createQuery(jpql, Knjiga.class)
                .setParameter("id", id)
                .getResultList();

        if (list.isEmpty()) {
            throw new Exception("Knjiga nije pronađena!");
        }

        return list.get(0);
    }
}
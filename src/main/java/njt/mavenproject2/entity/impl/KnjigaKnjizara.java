package njt.mavenproject2.entity.impl;

import jakarta.persistence.*;
import njt.mavenproject2.entity.MyEntity;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

/**
 * Predstavlja dostupnost određene knjige u određenoj knjižari.
 *
 * Klasa povezuje knjigu i knjižaru i čuva podatak o količini knjige koja je
 * dostupna u toj knjižari. Koristi se kao međuklasa u vezi više-prema-više
 * između knjiga i knjižara, jer veza ima dodatni atribut kolicina.
 *
 * Klasa je JPA entitet i mapira se na tabelu {@code knjiga_knjizara}.
 *
 * @author Korisnik
 */
@Entity
@Table(name = "knjiga_knjizara",
       uniqueConstraints = @UniqueConstraint(name = "uk_knjiga_knjizara",
                                             columnNames = {"knjiga_id", "knjizara_id"}))
public class KnjigaKnjizara implements MyEntity {

    /**
     * Jedinstveni identifikator dostupnosti knjige u knjižari.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Knjiga čija se dostupnost prati.
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "knjiga_id")
    private Knjiga knjiga;

    /**
     * Knjižara u kojoj se knjiga nalazi.
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "knjizara_id")
    private Knjizara knjizara;

    /**
     * Količina knjige dostupna u knjižari.
     */
    @NotNull
    @PositiveOrZero
    @Column(nullable = false)
    private Integer kolicina = 0;

    /**
     * Kreira prazan objekat klase KnjigaKnjizara.
     */
    public KnjigaKnjizara() {
    }

    /**
     * Kreira dostupnost knjige u knjižari sa zadatom količinom.
     *
     * @param knjiga knjiga čija se dostupnost evidentira
     * @param knjizara knjižara u kojoj se knjiga nalazi
     * @param kolicina količina knjige u knjižari, ne sme biti negativna
     */
    public KnjigaKnjizara(Knjiga knjiga, Knjizara knjizara, Integer kolicina) {
        this.knjiga = knjiga;
        this.knjizara = knjizara;
        this.kolicina = kolicina;
    }

    /**
     * Vraca identifikator dostupnosti knjige u knjižari.
     *
     * @return identifikator dostupnosti
     */
    public Long getId() {
        return id;
    }

    /**
     * Postavlja identifikator dostupnosti knjige u knjižari.
     *
     * @param id identifikator dostupnosti
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Vraca knjigu čija se dostupnost prati.
     *
     * @return knjiga povezana sa knjižarom
     */
    public Knjiga getKnjiga() {
        return knjiga;
    }

    /**
     * Postavlja knjigu čija se dostupnost prati.
     *
     * @param knjiga knjiga povezana sa knjižarom
     */
    public void setKnjiga(Knjiga knjiga) {
        this.knjiga = knjiga;
    }

    /**
     * Vraca knjižaru u kojoj se knjiga nalazi.
     *
     * @return knjižara povezana sa knjigom
     */
    public Knjizara getKnjizara() {
        return knjizara;
    }

    /**
     * Postavlja knjižaru u kojoj se knjiga nalazi.
     *
     * @param knjizara knjižara povezana sa knjigom
     */
    public void setKnjizara(Knjizara knjizara) {
        this.knjizara = knjizara;
    }

    /**
     * Vraca količinu knjige dostupnu u knjižari.
     *
     * @return količina knjige
     */
    public Integer getKolicina() {
        return kolicina;
    }

    /**
     * Postavlja količinu knjige dostupnu u knjižari.
     *
     * @param kolicina količina knjige, ne sme biti negativna
     */
    public void setKolicina(Integer kolicina) {
        this.kolicina = kolicina;
    }

    /**
     * Poredi dve dostupnosti knjige u knjižari prema identifikatoru.
     *
     * @param o objekat sa kojim se poredi trenutna dostupnost
     * @return {@code true} ako su objekti isti ili imaju isti identifikator,
     * a {@code false} u suprotnom
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof KnjigaKnjizara that)) {
            return false;
        }
        return id != null && id.equals(that.id);
    }

    /**
     * Vraca hash code vrednost dostupnosti knjige u knjižari na osnovu identifikatora.
     *
     * @return hash code vrednost dostupnosti
     */
    @Override
    public int hashCode() {
        return java.util.Objects.hashCode(id);
    }

    /**
     * Vraca tekstualni prikaz dostupnosti knjige u knjižari.
     *
     * @return tekstualni prikaz dostupnosti sa identifikatorom i količinom
     */
    @Override
    public String toString() {
        return "KnjigaKnjizara{id=%d, kolicina=%s}".formatted(id, kolicina);
    }
}
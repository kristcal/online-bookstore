package njt.mavenproject2.entity.impl;

import jakarta.persistence.*;
import njt.mavenproject2.entity.MyEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Predstavlja vezu između knjige i autora.
 *
 * Klasa se koristi za modelovanje veze više-prema-više između knjiga i autora,
 * uz dodatni atribut uloga. Na primer, autor može imati ulogu pisca, prevodioca
 * ili urednika za određenu knjigu.
 *
 * Klasa je JPA entitet i mapira se na tabelu {@code knjiga_autor}.
 *
 * @author Korisnik
 */
@Entity
@Table(name = "knjiga_autor")
public class KnjigaAutor implements MyEntity {

    /**
     * Jedinstveni identifikator veze između knjige i autora.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Knjiga koja učestvuje u vezi.
     */
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "knjiga_id", nullable = false)
    private Knjiga knjiga;

    /**
     * Autor koji učestvuje u vezi.
     */
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "autor_id", nullable = false)
    private Autor autor;

    /**
     * Uloga autora za konkretnu knjigu.
     */
    @NotBlank
    @Size(max = 50)
    private String uloga;

    /**
     * Kreira prazan objekat klase KnjigaAutor.
     */
    public KnjigaAutor() {
    }

    /**
     * Kreira vezu između zadate knjige i autora.
     *
     * @param knjiga knjiga koja učestvuje u vezi
     * @param autor autor koji učestvuje u vezi
     * @param uloga uloga autora za konkretnu knjigu, ne sme biti prazna i može imati najviše 50 karaktera
     */
    public KnjigaAutor(Knjiga knjiga, Autor autor, String uloga) {
        this.knjiga = knjiga;
        this.autor = autor;
        this.uloga = uloga;
    }

    /**
     * Vraca identifikator veze između knjige i autora.
     *
     * @return identifikator veze
     */
    public Long getId() {
        return id;
    }

    /**
     * Postavlja identifikator veze između knjige i autora.
     *
     * @param id identifikator veze
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Vraca knjigu koja učestvuje u vezi.
     *
     * @return knjiga povezana sa autorom
     */
    public Knjiga getKnjiga() {
        return knjiga;
    }

    /**
     * Postavlja knjigu koja učestvuje u vezi.
     *
     * @param knjiga knjiga povezana sa autorom
     */
    public void setKnjiga(Knjiga knjiga) {
        this.knjiga = knjiga;
    }

    /**
     * Vraca autora koji učestvuje u vezi.
     *
     * @return autor povezan sa knjigom
     */
    public Autor getAutor() {
        return autor;
    }

    /**
     * Postavlja autora koji učestvuje u vezi.
     *
     * @param autor autor povezan sa knjigom
     */
    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    /**
     * Vraca ulogu autora za konkretnu knjigu.
     *
     * @return uloga autora
     */
    public String getUloga() {
        return uloga;
    }

    /**
     * Postavlja ulogu autora za konkretnu knjigu.
     *
     * @param uloga uloga autora, ne sme biti prazna i može imati najviše 50 karaktera
     */
    public void setUloga(String uloga) {
        this.uloga = uloga;
    }

    /**
     * Poredi dve veze knjiga-autor prema identifikatoru.
     *
     * @param o objekat sa kojim se poredi trenutna veza
     * @return {@code true} ako su objekti isti ili imaju isti identifikator,
     * a {@code false} u suprotnom
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof KnjigaAutor that)) {
            return false;
        }
        return id != null && id.equals(that.id);
    }

    /**
     * Vraca hash code vrednost veze knjiga-autor na osnovu identifikatora.
     *
     * @return hash code vrednost veze
     */
    @Override
    public int hashCode() {
        return java.util.Objects.hashCode(id);
    }

    /**
     * Vraca tekstualni prikaz veze između knjige i autora.
     *
     * @return tekstualni prikaz veze sa identifikatorom i ulogom autora
     */
    @Override
    public String toString() {
        return "KnjigaAutor{id=%d, uloga=%s}".formatted(id, uloga);
    }
}
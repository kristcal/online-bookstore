package njt.mavenproject2.entity.impl;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import njt.mavenproject2.entity.MyEntity;

/**
 * Predstavlja žanr knjige u sistemu online knjižare.
 *
 * Žanr sadrži naziv i listu knjiga koje pripadaju tom žanru.
 * Jedan žanr može biti povezan sa više knjiga.
 *
 * Klasa je JPA entitet i mapira se na tabelu {@code zanr}.
 *
 * @author Korisnik
 */
@Entity
@Table(name = "zanr")
public class Zanr implements MyEntity {

    /**
     * Jedinstveni identifikator žanra.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Naziv žanra.
     */
    @NotBlank
    @Size(max = 50)
    @Column(nullable = false, length = 50)
    private String naziv;

    /**
     * Lista knjiga koje pripadaju žanru.
     */
    @OneToMany(mappedBy = "zanr",
            cascade = CascadeType.ALL,
            orphanRemoval = false)
    private List<Knjiga> knjige = new ArrayList<>();

    /**
     * Kreira prazan objekat klase Zanr.
     */
    public Zanr() {
    }

    /**
     * Kreira žanr sa zadatim podacima.
     *
     * @param id jedinstveni identifikator žanra
     * @param naziv naziv žanra
     */
    public Zanr(Long id, String naziv) {
        this.id = id;
        this.naziv = naziv;
    }

    /**
     * Vraća identifikator žanra.
     *
     * @return identifikator žanra
     */
    public Long getId() {
        return id;
    }

    /**
     * Postavlja identifikator žanra.
     *
     * @param id identifikator žanra
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Vraća naziv žanra.
     *
     * @return naziv žanra
     */
    public String getNaziv() {
        return naziv;
    }

    /**
     * Postavlja naziv žanra.
     *
     * @param naziv naziv žanra
     */
    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    /**
     * Vraća listu knjiga koje pripadaju žanru.
     *
     * @return lista knjiga
     */
    public List<Knjiga> getKnjige() {
        return knjige;
    }

    /**
     * Postavlja listu knjiga koje pripadaju žanru.
     *
     * @param knjige lista knjiga
     */
    public void setKnjige(List<Knjiga> knjige) {
        this.knjige = knjige;
    }

    /**
     * Poredi dva žanra prema identifikatoru.
     *
     * @param o objekat sa kojim se poredi trenutni žanr
     * @return {@code true} ako su objekti isti ili imaju isti identifikator,
     * a {@code false} u suprotnom
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Zanr that)) {
            return false;
        }
        return id != null && id.equals(that.id);
    }

    /**
     * Vraća hash code vrednost žanra na osnovu identifikatora.
     *
     * @return hash code vrednost žanra
     */
    @Override
    public int hashCode() {
        return java.util.Objects.hashCode(id);
    }

    /**
     * Vraća tekstualni prikaz žanra.
     *
     * @return tekstualni prikaz žanra sa osnovnim podacima
     */
    @Override
    public String toString() {
        return "Zanr{id=%d, naziv=%s}".formatted(id, naziv);
    }
}
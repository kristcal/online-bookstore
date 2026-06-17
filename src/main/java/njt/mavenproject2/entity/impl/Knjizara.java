package njt.mavenproject2.entity.impl;

import java.util.List;
import jakarta.persistence.*;
import java.util.ArrayList;
import njt.mavenproject2.entity.MyEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Predstavlja knjižaru u sistemu online knjižare.
 *
 * Knjižara ima naziv, lokaciju i kontakt podatke. U okviru knjižare prati se
 * ponuda knjiga, odnosno lista dostupnih knjiga i njihovih količina preko klase
 * {@link KnjigaKnjizara}.
 *
 * Klasa je JPA entitet i mapira se na tabelu {@code knjizara}.
 *
 * @author Korisnik
 */
@Entity
@Table(name = "knjizara")
public class Knjizara implements MyEntity {

    /**
     * Jedinstveni identifikator knjižare.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Naziv knjižare.
     */
    @NotBlank
    @Size(max = 100)
    private String naziv;

    /**
     * Lokacija knjižare.
     */
    @NotBlank
    @Size(max = 100)
    private String lokacija;

    /**
     * Kontakt podaci knjižare.
     */
    @NotBlank
    @Size(max = 50)
    private String kontakt;

    /**
     * Lista dostupnih knjiga u knjižari.
     */
    @OneToMany(mappedBy = "knjizara", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<KnjigaKnjizara> ponuda = new ArrayList<>();

    /**
     * Kreira prazan objekat klase Knjizara.
     */
    public Knjizara() {
    }

    /**
     * Kreira knjižaru sa zadatim podacima.
     *
     * @param id jedinstveni identifikator knjižare
     * @param naziv naziv knjižare, ne sme biti prazan i može imati najviše 100 karaktera
     * @param lokacija lokacija knjižare, ne sme biti prazna i može imati najviše 100 karaktera
     * @param kontakt kontakt podaci knjižare, ne smeju biti prazni i mogu imati najviše 50 karaktera
     */
    public Knjizara(Long id, String naziv, String lokacija, String kontakt) {
        this.id = id;
        this.naziv = naziv;
        this.lokacija = lokacija;
        this.kontakt = kontakt;
    }

    /**
     * Vraca identifikator knjižare.
     *
     * @return identifikator knjižare
     */
    public Long getId() {
        return id;
    }

    /**
     * Postavlja identifikator knjižare.
     *
     * @param id identifikator knjižare
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Vraca naziv knjižare.
     *
     * @return naziv knjižare
     */
    public String getNaziv() {
        return naziv;
    }

    /**
     * Postavlja naziv knjižare.
     *
     * @param naziv naziv knjižare, ne sme biti prazan i može imati najviše 100 karaktera
     */
    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    /**
     * Vraca lokaciju knjižare.
     *
     * @return lokacija knjižare
     */
    public String getLokacija() {
        return lokacija;
    }

    /**
     * Postavlja lokaciju knjižare.
     *
     * @param lokacija lokacija knjižare, ne sme biti prazna i može imati najviše 100 karaktera
     */
    public void setLokacija(String lokacija) {
        this.lokacija = lokacija;
    }

    /**
     * Vraca kontakt podatke knjižare.
     *
     * @return kontakt podaci knjižare
     */
    public String getKontakt() {
        return kontakt;
    }

    /**
     * Postavlja kontakt podatke knjižare.
     *
     * @param kontakt kontakt podaci knjižare, ne smeju biti prazni i mogu imati najviše 50 karaktera
     */
    public void setKontakt(String kontakt) {
        this.kontakt = kontakt;
    }

    /**
     * Vraca ponudu knjižare.
     *
     * @return lista dostupnih knjiga i količina u knjižari
     */
    public List<KnjigaKnjizara> getPonuda() {
        return ponuda;
    }

    /**
     * Postavlja ponudu knjižare.
     *
     * @param ponuda lista dostupnih knjiga i količina u knjižari
     */
    public void setPonuda(List<KnjigaKnjizara> ponuda) {
        this.ponuda = ponuda;
    }

    /**
     * Poredi dve knjižare prema identifikatoru.
     *
     * @param o objekat sa kojim se poredi trenutna knjižara
     * @return {@code true} ako su objekti isti ili imaju isti identifikator,
     * a {@code false} u suprotnom
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Knjizara that)) {
            return false;
        }
        return id != null && id.equals(that.id);
    }

    /**
     * Vraca hash code vrednost knjižare na osnovu identifikatora.
     *
     * @return hash code vrednost knjižare
     */
    @Override
    public int hashCode() {
        return java.util.Objects.hashCode(id);
    }

    /**
     * Vraca tekstualni prikaz knjižare.
     *
     * @return tekstualni prikaz knjižare sa identifikatorom, nazivom i lokacijom
     */
    @Override
    public String toString() {
        return "Knjizara{id=%d, naziv=%s, lokacija=%s}".formatted(id, naziv, lokacija);
    }
}
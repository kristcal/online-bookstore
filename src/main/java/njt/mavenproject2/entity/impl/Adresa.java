package njt.mavenproject2.entity.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import njt.mavenproject2.entity.MyEntity;

/**
 * Predstavlja adresu korisnika u sistemu online knjižare.
 *
 * Adresa sadrži ulicu, broj, grad, poštanski broj i državu. Jedna adresa može
 * biti povezana sa više korisnika, na primer kada više članova porodice koristi
 * istu adresu stanovanja ili isporuke.
 *
 * Klasa je JPA entitet i mapira se na tabelu {@code adresa}.
 *
 * @author Korisnik
 */
@Entity
@Table(name = "adresa")
public class Adresa implements MyEntity {

	/**
     * Jedinstveni identifikator adrese.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Naziv ulice.
     */
    @NotBlank
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String ulica;

    /**
     * Broj ulice, odnosno kućni broj ili broj stana.
     */
    @NotBlank
    @Size(max = 20)
    @Column(nullable = false, length = 20)
    private String broj;

    /**
     * Grad u kome se adresa nalazi.
     */
    @NotBlank
    @Size(max = 60)
    @Column(nullable = false, length = 60)
    private String grad;

    /**
     * Poštanski broj adrese.
     */
    @NotBlank
    @Size(max = 20)
    @Column(nullable = false, length = 20)
    private String postanskiBroj;

    /**
     * Država u kojoj se adresa nalazi.
     */
    @NotBlank
    @Size(max = 60)
    @Column(nullable = false, length = 60)
    private String drzava;

    /**
     * Lista korisnika koji su povezani sa ovom adresom.
     */
    @JsonIgnore
    @OneToMany(mappedBy = "adresa")
    private List<Korisnik> korisnici = new ArrayList<>();

    /**
     * Kreira prazan objekat klase Adresa.
     */
    public Adresa() {
    }

    /**
     * Kreira adresu sa zadatim podacima.
     *
     * @param id jedinstveni identifikator adrese
     * @param ulica naziv ulice, ne sme biti prazan i može imati najviše 100 karaktera
     * @param broj broj ulice, ne sme biti prazan i može imati najviše 20 karaktera
     * @param grad naziv grada, ne sme biti prazan i može imati najviše 60 karaktera
     * @param postanskiBroj poštanski broj, ne sme biti prazan i može imati najviše 20 karaktera
     * @param drzava naziv države, ne sme biti prazan i može imati najviše 60 karaktera
     */
    public Adresa(Long id, String ulica, String broj, String grad, String postanskiBroj, String drzava) {
        this.id = id;
        this.ulica = ulica;
        this.broj = broj;
        this.grad = grad;
        this.postanskiBroj = postanskiBroj;
        this.drzava = drzava;
    }

    /**
     * Vraca identifikator adrese.
     *
     * @return identifikator adrese
     */
    public Long getId() {
        return id;
    }

    /**
     * Vraca naziv ulice.
     *
     * @return naziv ulice
     */
    public String getUlica() {
        return ulica;
    }

    /**
     * Vraca broj ulice.
     *
     * @return broj ulice
     */
    public String getBroj() {
        return broj;
    }

    /**
     * Vraca naziv grada.
     *
     * @return naziv grada
     */
    public String getGrad() {
        return grad;
    }

    /**
     * Vraca poštanski broj.
     *
     * @return poštanski broj
     */
    public String getPostanskiBroj() {
        return postanskiBroj;
    }

    /**
     * Vraca naziv države.
     *
     * @return naziv države
     */
    public String getDrzava() {
        return drzava;
    }

    /**
     * Vraca listu korisnika koji koriste ovu adresu.
     *
     * @return lista korisnika povezanih sa adresom
     */
    public List<Korisnik> getKorisnici() {
        return korisnici;
    }

    /**
     * Postavlja identifikator adrese.
     *
     * @param id identifikator adrese
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Postavlja naziv ulice.
     *
     * @param ulica naziv ulice, ne sme biti prazan i može imati najviše 100 karaktera
     */
    public void setUlica(String ulica) {
        this.ulica = ulica;
    }

    /**
     * Postavlja broj ulice.
     *
     * @param broj broj ulice, ne sme biti prazan i može imati najviše 20 karaktera
     */
    public void setBroj(String broj) {
        this.broj = broj;
    }

    /**
     * Postavlja naziv grada.
     *
     * @param grad naziv grada, ne sme biti prazan i može imati najviše 60 karaktera
     */
    public void setGrad(String grad) {
        this.grad = grad;
    }

    /**
     * Postavlja poštanski broj.
     *
     * @param postanskiBroj poštanski broj, ne sme biti prazan i može imati najviše 20 karaktera
     */
    public void setPostanskiBroj(String postanskiBroj) {
        this.postanskiBroj = postanskiBroj;
    }

    /**
     * Postavlja naziv države.
     *
     * @param drzava naziv države, ne sme biti prazan i može imati najviše 60 karaktera
     */
    public void setDrzava(String drzava) {
        this.drzava = drzava;
    }

    /**
     * Postavlja listu korisnika povezanih sa adresom.
     *
     * @param korisnici lista korisnika koji koriste ovu adresu
     */
    public void setKorisnici(List<Korisnik> korisnici) {
        this.korisnici = korisnici;
    }
    
    /**
     * Poredi dve adrese prema identifikatoru.
     *
     * @param o objekat sa kojim se poredi trenutna adresa
     * @return {@code true} ako su objekti isti ili imaju isti identifikator,
     * a {@code false} u suprotnom
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Adresa that)) {
            return false;
        }
        return id != null && id.equals(that.id);
    }

    /**
     * Vraca hash code vrednost adrese na osnovu identifikatora.
     *
     * @return hash code vrednost adrese
     */
    @Override
    public int hashCode() {
        return java.util.Objects.hashCode(id);
    }

    /**
     * Vraca tekstualni prikaz adrese.
     *
     * @return tekstualni prikaz adrese sa osnovnim podacima
     */
    @Override
    public String toString() {
        return "Adresa{id=%d, ulica=%s, broj=%s, grad=%s}".formatted(id, ulica, broj, grad);
    }
}
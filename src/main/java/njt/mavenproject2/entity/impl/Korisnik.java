package njt.mavenproject2.entity.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import njt.mavenproject2.entity.MyEntity;

/**
 * Predstavlja korisnika sistema online knjižare.
 *
 * Korisnik ima osnovne lične podatke, email adresu, lozinku, ulogu, adresu i
 * listu porudžbina. Email korisnika je jedinstven u sistemu.
 *
 * Klasa je JPA entitet i mapira se na tabelu {@code korisnik}.
 *
 * @author Korisnik
 */
@Entity
@Table(
        name = "korisnik",
        uniqueConstraints = {
            @UniqueConstraint(name = "uk_korisnik_email", columnNames = "email")
        },
        indexes = {
            @Index(name = "idx_korisnik_email", columnList = "email")
        }
)
public class Korisnik implements MyEntity {

    /**
     * Jedinstveni identifikator korisnika.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Ime korisnika.
     */
    @NotBlank
    @Size(max = 60)
    private String ime;

    /**
     * Prezime korisnika.
     */
    @NotBlank
    @Size(max = 60)
    private String prezime;

    /**
     * Email adresa korisnika, jedinstvena u sistemu.
     */
    @NotBlank
    @Email
    @Size(max = 120)
    private String email;

    /**
     * Lozinka korisnika.
     *
     * U realnoj aplikaciji lozinka treba da se čuva kao hash vrednost.
     */
    @Column(nullable = false)
    @Size(min = 4, max = 100, message = "Lozinka mora imati između 4 i 100 karaktera.")
    private String lozinka;

    /**
     * Uloga korisnika u sistemu.
     *
     * Podrazumevana vrednost je USER.
     */
    @Size(max = 40)
    private String uloga = "USER";

    /**
     * Adresa korisnika.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "adresa_id")
    private Adresa adresa;

    /**
     * Lista porudžbina koje je korisnik kreirao.
     */
    @JsonIgnore
    @OneToMany(mappedBy = "korisnik", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Porudzbina> porudzbine = new ArrayList<>();

    /**
     * Kreira prazan objekat klase Korisnik.
     */
    public Korisnik() {
    }

    /**
     * Kreira korisnika sa zadatim podacima.
     *
     * @param id jedinstveni identifikator korisnika
     * @param ime ime korisnika, ne sme biti prazno i može imati najviše 60 karaktera
     * @param prezime prezime korisnika, ne sme biti prazno i može imati najviše 60 karaktera
     * @param email email adresa korisnika, mora biti u ispravnom formatu
     * @param lozinka lozinka korisnika, mora imati između 4 i 100 karaktera
     */
    public Korisnik(Long id, String ime, String prezime, String email, String lozinka) {
        this.id = id;
        this.ime = ime;
        this.prezime = prezime;
        this.email = email;
        this.lozinka = lozinka;
    }

    /**
     * Vraća identifikator korisnika.
     *
     * @return identifikator korisnika
     */
    public Long getId() {
        return id;
    }

    /**
     * Postavlja identifikator korisnika.
     *
     * @param id identifikator korisnika
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Vraća ime korisnika.
     *
     * @return ime korisnika
     */
    public String getIme() {
        return ime;
    }

    /**
     * Postavlja ime korisnika.
     *
     * @param ime ime korisnika, ne sme biti prazno i može imati najviše 60 karaktera
     */
    public void setIme(String ime) {
        this.ime = ime;
    }

    /**
     * Vraća prezime korisnika.
     *
     * @return prezime korisnika
     */
    public String getPrezime() {
        return prezime;
    }

    /**
     * Postavlja prezime korisnika.
     *
     * @param prezime prezime korisnika, ne sme biti prazno i može imati najviše 60 karaktera
     */
    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    /**
     * Vraća email adresu korisnika.
     *
     * @return email adresa korisnika
     */
    public String getEmail() {
        return email;
    }

    /**
     * Postavlja email adresu korisnika.
     *
     * @param email email adresa korisnika, mora biti u ispravnom formatu
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Vraća lozinku korisnika.
     *
     * @return lozinka korisnika
     */
    public String getLozinka() {
        return lozinka;
    }

    /**
     * Postavlja lozinku korisnika.
     *
     * @param lozinka lozinka korisnika, mora imati između 4 i 100 karaktera
     */
    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }

    /**
     * Vraća ulogu korisnika.
     *
     * @return uloga korisnika
     */
    public String getUloga() {
        return uloga;
    }

    /**
     * Postavlja ulogu korisnika.
     *
     * @param uloga uloga korisnika, na primer USER ili ADMIN
     */
    public void setUloga(String uloga) {
        this.uloga = uloga;
    }

    /**
     * Vraća listu porudžbina korisnika.
     *
     * @return lista porudžbina koje je korisnik kreirao
     */
    public List<Porudzbina> getPorudzbine() {
        return porudzbine;
    }

    /**
     * Postavlja listu porudžbina korisnika.
     *
     * @param porudzbine lista porudžbina korisnika
     */
    public void setPorudzbine(List<Porudzbina> porudzbine) {
        this.porudzbine = porudzbine;
    }

    /**
     * Vraća adresu korisnika.
     *
     * @return adresa korisnika
     */
    public Adresa getAdresa() {
        return adresa;
    }

    /**
     * Postavlja adresu korisnika.
     *
     * @param adresa adresa korisnika
     */
    public void setAdresa(Adresa adresa) {
        this.adresa = adresa;
    }

    /**
     * Poredi dva korisnika prema identifikatoru.
     *
     * @param o objekat sa kojim se poredi trenutni korisnik
     * @return {@code true} ako su objekti isti ili imaju isti identifikator,
     * a {@code false} u suprotnom
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Korisnik that)) {
            return false;
        }
        return id != null && id.equals(that.id);
    }

    /**
     * Vraća hash code vrednost korisnika na osnovu identifikatora.
     *
     * @return hash code vrednost korisnika
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    /**
     * Vraća tekstualni prikaz korisnika.
     *
     * @return tekstualni prikaz korisnika sa osnovnim podacima
     */
    @Override
    public String toString() {
        return "Korisnik{id=%d, ime=%s, prezime=%s, email=%s, uloga=%s}"
                .formatted(id, ime, prezime, email, uloga);
    }
}
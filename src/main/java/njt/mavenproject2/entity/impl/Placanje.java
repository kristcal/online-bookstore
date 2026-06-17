package njt.mavenproject2.entity.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import njt.mavenproject2.entity.MyEntity;

/**
 * Predstavlja plaćanje porudžbine u sistemu online knjižare.
 *
 * Plaćanje sadrži iznos, način plaćanja, status plaćanja, datum plaćanja i
 * poziv na broj. Jedno plaćanje može biti povezano sa jednom porudžbinom.
 *
 * Klasa je JPA entitet i mapira se na tabelu {@code placanje}.
 *
 * @author Korisnik
 */
@Entity
@Table(name = "placanje")
public class Placanje implements MyEntity {

    /**
     * Jedinstveni identifikator plaćanja.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Iznos plaćanja.
     */
    @NotNull
    @Positive
    @Column(nullable = false)
    private Double iznos;

    /**
     * Način plaćanja, na primer kartica, pouzeće ili uplata.
     */
    @NotBlank
    @Size(max = 50)
    @Column(nullable = false, length = 50)
    private String nacinPlacanja;

    /**
     * Status plaćanja, na primer KREIRANO, PLAĆENO ili ODBIJENO.
     */
    @NotBlank
    @Size(max = 50)
    @Column(nullable = false, length = 50)
    private String statusPlacanja;

    /**
     * Datum i vreme plaćanja.
     */
    @NotNull
    @Column(nullable = false)
    private LocalDateTime datumPlacanja;

    /**
     * Poziv na broj koji se koristi pri plaćanju.
     */
    @NotBlank
    @Size(max = 50)
    @Column(nullable = false, length = 50)
    private String pozivNaBroj;

    /**
     * Porudžbina na koju se plaćanje odnosi.
     */
    @JsonIgnore
    @OneToOne(mappedBy = "placanje")
    private Porudzbina porudzbina;

    /**
     * Kreira prazan objekat klase Placanje.
     */
    public Placanje() {
    }

    /**
     * Kreira plaćanje sa zadatim podacima.
     *
     * @param id jedinstveni identifikator plaćanja
     * @param iznos iznos plaćanja, mora biti pozitivan
     * @param nacinPlacanja način plaćanja, ne sme biti prazan i može imati najviše 50 karaktera
     * @param statusPlacanja status plaćanja, ne sme biti prazan i može imati najviše 50 karaktera
     * @param datumPlacanja datum i vreme plaćanja
     * @param pozivNaBroj poziv na broj, ne sme biti prazan i može imati najviše 50 karaktera
     */
    public Placanje(Long id, Double iznos, String nacinPlacanja, String statusPlacanja,
            LocalDateTime datumPlacanja, String pozivNaBroj) {
        this.id = id;
        this.iznos = iznos;
        this.nacinPlacanja = nacinPlacanja;
        this.statusPlacanja = statusPlacanja;
        this.datumPlacanja = datumPlacanja;
        this.pozivNaBroj = pozivNaBroj;
    }

    /**
     * Vraća identifikator plaćanja.
     *
     * @return identifikator plaćanja
     */
    public Long getId() {
        return id;
    }

    /**
     * Vraća iznos plaćanja.
     *
     * @return iznos plaćanja
     */
    public Double getIznos() {
        return iznos;
    }

    /**
     * Vraća način plaćanja.
     *
     * @return način plaćanja
     */
    public String getNacinPlacanja() {
        return nacinPlacanja;
    }

    /**
     * Vraća status plaćanja.
     *
     * @return status plaćanja
     */
    public String getStatusPlacanja() {
        return statusPlacanja;
    }

    /**
     * Vraća datum i vreme plaćanja.
     *
     * @return datum i vreme plaćanja
     */
    public LocalDateTime getDatumPlacanja() {
        return datumPlacanja;
    }

    /**
     * Vraća poziv na broj.
     *
     * @return poziv na broj
     */
    public String getPozivNaBroj() {
        return pozivNaBroj;
    }

    /**
     * Vraća porudžbinu na koju se plaćanje odnosi.
     *
     * @return porudžbina povezana sa plaćanjem
     */
    public Porudzbina getPorudzbina() {
        return porudzbina;
    }

    /**
     * Postavlja identifikator plaćanja.
     *
     * @param id identifikator plaćanja
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Postavlja iznos plaćanja.
     *
     * @param iznos iznos plaćanja, mora biti pozitivan
     */
    public void setIznos(Double iznos) {
        this.iznos = iznos;
    }

    /**
     * Postavlja način plaćanja.
     *
     * @param nacinPlacanja način plaćanja, ne sme biti prazan i može imati najviše 50 karaktera
     */
    public void setNacinPlacanja(String nacinPlacanja) {
        this.nacinPlacanja = nacinPlacanja;
    }

    /**
     * Postavlja status plaćanja.
     *
     * @param statusPlacanja status plaćanja, ne sme biti prazan i može imati najviše 50 karaktera
     */
    public void setStatusPlacanja(String statusPlacanja) {
        this.statusPlacanja = statusPlacanja;
    }

    /**
     * Postavlja datum i vreme plaćanja.
     *
     * @param datumPlacanja datum i vreme plaćanja
     */
    public void setDatumPlacanja(LocalDateTime datumPlacanja) {
        this.datumPlacanja = datumPlacanja;
    }

    /**
     * Postavlja poziv na broj.
     *
     * @param pozivNaBroj poziv na broj, ne sme biti prazan i može imati najviše 50 karaktera
     */
    public void setPozivNaBroj(String pozivNaBroj) {
        this.pozivNaBroj = pozivNaBroj;
    }

    /**
     * Postavlja porudžbinu na koju se plaćanje odnosi.
     *
     * @param porudzbina porudžbina povezana sa plaćanjem
     */
    public void setPorudzbina(Porudzbina porudzbina) {
        this.porudzbina = porudzbina;
    }

    /**
     * Poredi dva plaćanja prema identifikatoru.
     *
     * @param o objekat sa kojim se poredi trenutno plaćanje
     * @return {@code true} ako su objekti isti ili imaju isti identifikator,
     * a {@code false} u suprotnom
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Placanje that)) {
            return false;
        }
        return id != null && id.equals(that.id);
    }

    /**
     * Vraća hash code vrednost plaćanja na osnovu identifikatora.
     *
     * @return hash code vrednost plaćanja
     */
    @Override
    public int hashCode() {
        return java.util.Objects.hashCode(id);
    }

    /**
     * Vraća tekstualni prikaz plaćanja.
     *
     * @return tekstualni prikaz plaćanja sa osnovnim podacima
     */
    @Override
    public String toString() {
        return "Placanje{id=%d, iznos=%s, nacinPlacanja=%s, statusPlacanja=%s}"
                .formatted(id, iznos, nacinPlacanja, statusPlacanja);
    }
}
package njt.mavenproject2.entity.impl;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import njt.mavenproject2.entity.MyEntity;

/**
 * Predstavlja porudžbinu u sistemu online knjižare.
 *
 * Porudžbina sadrži datum kreiranja, ukupan iznos, status porudžbine,
 * korisnika koji je kreirao porudžbinu, podatke o plaćanju i listu stavki
 * porudžbine.
 *
 * Klasa je JPA entitet i mapira se na tabelu {@code porudzbina}.
 *
 * @author Korisnik
 */
@Entity
@Table(name = "porudzbina")
public class Porudzbina implements MyEntity {

    /**
     * Jedinstveni identifikator porudžbine.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Datum i vreme kreiranja porudžbine.
     */
    @NotNull
    @Column(nullable = false)
    private LocalDateTime datum;

    /**
     * Ukupan iznos porudžbine.
     */
    @NotNull
    @Positive
    @Column(nullable = false)
    private Double ukupanIznos;

    /**
     * Status porudžbine.
     */
    @NotBlank
    @Size(max = 50)
    @Column(nullable = false, length = 50)
    private String status = "KREIRANA";

    /**
     * Korisnik koji je kreirao porudžbinu.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "korisnik_id", nullable = false)
    private Korisnik korisnik;

    /**
     * Plaćanje povezano sa porudžbinom.
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "placanje_id")
    private Placanje placanje;

    /**
     * Lista stavki porudžbine.
     */
    @OneToMany(mappedBy = "porudzbina",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<StavkaPorudzbine> stavke = new ArrayList<>();

    /**
     * Kreira praznu porudžbinu.
     */
    public Porudzbina() {
    }

    /**
     * Kreira porudžbinu sa zadatim podacima.
     *
     * @param id identifikator porudžbine
     * @param datum datum i vreme kreiranja porudžbine
     * @param ukupanIznos ukupan iznos porudžbine
     * @param korisnik korisnik koji je kreirao porudžbinu
     */
    public Porudzbina(Long id, LocalDateTime datum,
            Double ukupanIznos, Korisnik korisnik) {
        this.id = id;
        this.datum = datum;
        this.ukupanIznos = ukupanIznos;
        this.korisnik = korisnik;
        this.status = "KREIRANA";
    }

    /**
     * Vraća identifikator porudžbine.
     *
     * @return identifikator porudžbine
     */
    public Long getId() {
        return id;
    }

    /**
     * Postavlja identifikator porudžbine.
     *
     * @param id identifikator porudžbine
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Vraća datum kreiranja porudžbine.
     *
     * @return datum kreiranja porudžbine
     */
    public LocalDateTime getDatum() {
        return datum;
    }

    /**
     * Postavlja datum kreiranja porudžbine.
     *
     * @param datum datum kreiranja porudžbine
     */
    public void setDatum(LocalDateTime datum) {
        this.datum = datum;
    }

    /**
     * Vraća ukupan iznos porudžbine.
     *
     * @return ukupan iznos porudžbine
     */
    public Double getUkupanIznos() {
        return ukupanIznos;
    }

    /**
     * Postavlja ukupan iznos porudžbine.
     *
     * @param ukupanIznos ukupan iznos porudžbine
     */
    public void setUkupanIznos(Double ukupanIznos) {
        this.ukupanIznos = ukupanIznos;
    }

    /**
     * Vraća korisnika koji je kreirao porudžbinu.
     *
     * @return korisnik porudžbine
     */
    public Korisnik getKorisnik() {
        return korisnik;
    }

    /**
     * Postavlja korisnika porudžbine.
     *
     * @param korisnik korisnik porudžbine
     */
    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
    }

    /**
     * Vraća listu stavki porudžbine.
     *
     * @return lista stavki porudžbine
     */
    public List<StavkaPorudzbine> getStavke() {
        return stavke;
    }

    /**
     * Postavlja listu stavki porudžbine.
     *
     * @param stavke lista stavki porudžbine
     */
    public void setStavke(List<StavkaPorudzbine> stavke) {
        this.stavke = stavke;
    }

    /**
     * Vraća status porudžbine.
     *
     * @return status porudžbine
     */
    public String getStatus() {
        return status;
    }

    /**
     * Postavlja status porudžbine.
     *
     * @param status status porudžbine
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Vraća podatke o plaćanju.
     *
     * @return plaćanje povezano sa porudžbinom
     */
    public Placanje getPlacanje() {
        return placanje;
    }

    /**
     * Postavlja podatke o plaćanju.
     *
     * @param placanje plaćanje povezano sa porudžbinom
     */
    public void setPlacanje(Placanje placanje) {
        this.placanje = placanje;
    }

    /**
     * Poredi dve porudžbine prema identifikatoru.
     *
     * @param o objekat sa kojim se poredi porudžbina
     * @return {@code true} ako imaju isti identifikator,
     * a {@code false} u suprotnom
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Porudzbina that)) {
            return false;
        }
        return id != null && id.equals(that.id);
    }

    /**
     * Vraća hash code vrednost porudžbine.
     *
     * @return hash code vrednost
     */
    @Override
    public int hashCode() {
        return java.util.Objects.hashCode(id);
    }

    /**
     * Vraća tekstualni prikaz porudžbine.
     *
     * @return tekstualni prikaz porudžbine
     */
    @Override
    public String toString() {
        return "Porudzbina{id=%d, datum=%s, ukupanIznos=%s, status=%s}"
                .formatted(id, datum, ukupanIznos, status);
    }
}
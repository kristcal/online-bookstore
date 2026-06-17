package njt.mavenproject2.entity.impl;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import njt.mavenproject2.entity.MyEntity;

/**
 * Predstavlja stavku porudžbine u sistemu online knjižare.
 *
 * Stavka porudžbine sadrži redni broj stavke, količinu naručene knjige,
 * cenu knjige u trenutku poručivanja i ukupan iznos stavke.
 * Svaka stavka pripada jednoj porudžbini i odnosi se na jednu knjigu.
 *
 * Klasa je JPA entitet i mapira se na tabelu {@code stavka_porudzbine}.
 *
 * @author Korisnik
 */
@Entity
@Table(
        name = "stavka_porudzbine",
        uniqueConstraints = @UniqueConstraint(columnNames = {"porudzbina_id", "rb"})
)
public class StavkaPorudzbine implements MyEntity {

    /**
     * Jedinstveni identifikator stavke porudžbine.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Redni broj stavke u okviru porudžbine.
     */
    @NotNull
    @Positive
    private Integer rb;

    /**
     * Količina naručene knjige.
     */
    @NotNull
    @Positive
    private Integer kolicina;

    /**
     * Cena knjige u trenutku kreiranja porudžbine.
     */
    @NotNull
    @Positive
    private Double cenaK;

    /**
     * Ukupan iznos stavke.
     */
    private Double ukupanIznosStavke;

    /**
     * Porudžbina kojoj stavka pripada.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "porudzbina_id", nullable = false)
    private Porudzbina porudzbina;

    /**
     * Knjiga na koju se stavka odnosi.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "knjiga_id", nullable = false)
    private Knjiga knjiga;

    /**
     * Kreira praznu stavku porudžbine.
     */
    public StavkaPorudzbine() {
    }

    /**
     * Kreira stavku porudžbine sa zadatim podacima.
     *
     * @param rb redni broj stavke
     * @param kolicina količina knjiga
     * @param cenaK cena knjige
     * @param porudzbina porudžbina kojoj stavka pripada
     * @param knjiga knjiga koja se poručuje
     */
    public StavkaPorudzbine(Integer rb, Integer kolicina, Double cenaK,
            Porudzbina porudzbina, Knjiga knjiga) {
        this.rb = rb;
        this.kolicina = kolicina;
        this.cenaK = cenaK;
        this.ukupanIznosStavke =
                (cenaK != null && kolicina != null) ? cenaK * kolicina : 0d;
        this.porudzbina = porudzbina;
        this.knjiga = knjiga;
    }

    /**
     * Izračunava ukupan iznos stavke pre čuvanja ili ažuriranja.
     */
    @PrePersist
    @PreUpdate
    private void izracunajUkupanIznos() {
        this.ukupanIznosStavke =
                (cenaK != null && kolicina != null) ? cenaK * kolicina : 0d;
    }

    /**
     * Vraća identifikator stavke.
     *
     * @return identifikator stavke
     */
    public Long getId() {
        return id;
    }

    /**
     * Postavlja identifikator stavke.
     *
     * @param id identifikator stavke
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Vraća redni broj stavke.
     *
     * @return redni broj stavke
     */
    public Integer getRb() {
        return rb;
    }

    /**
     * Postavlja redni broj stavke.
     *
     * @param rb redni broj stavke
     */
    public void setRb(Integer rb) {
        this.rb = rb;
    }

    /**
     * Vraća količinu knjiga.
     *
     * @return količina knjiga
     */
    public Integer getKolicina() {
        return kolicina;
    }

    /**
     * Postavlja količinu knjiga.
     *
     * @param kolicina količina knjiga
     */
    public void setKolicina(Integer kolicina) {
        this.kolicina = kolicina;
    }

    /**
     * Vraća cenu knjige.
     *
     * @return cena knjige
     */
    public Double getCenaK() {
        return cenaK;
    }

    /**
     * Postavlja cenu knjige.
     *
     * @param cenaK cena knjige
     */
    public void setCenaK(Double cenaK) {
        this.cenaK = cenaK;
    }

    /**
     * Vraća ukupan iznos stavke.
     *
     * @return ukupan iznos stavke
     */
    public Double getUkupanIznosStavke() {
        return ukupanIznosStavke;
    }

    /**
     * Postavlja ukupan iznos stavke.
     *
     * @param ukupanIznosStavke ukupan iznos stavke
     */
    public void setUkupanIznosStavke(Double ukupanIznosStavke) {
        this.ukupanIznosStavke = ukupanIznosStavke;
    }

    /**
     * Vraća porudžbinu kojoj stavka pripada.
     *
     * @return porudžbina
     */
    public Porudzbina getPorudzbina() {
        return porudzbina;
    }

    /**
     * Postavlja porudžbinu kojoj stavka pripada.
     *
     * @param porudzbina porudžbina
     */
    public void setPorudzbina(Porudzbina porudzbina) {
        this.porudzbina = porudzbina;
    }

    /**
     * Vraća knjigu na koju se stavka odnosi.
     *
     * @return knjiga
     */
    public Knjiga getKnjiga() {
        return knjiga;
    }

    /**
     * Postavlja knjigu na koju se stavka odnosi.
     *
     * @param knjiga knjiga
     */
    public void setKnjiga(Knjiga knjiga) {
        this.knjiga = knjiga;
    }

    /**
     * Poredi dve stavke porudžbine prema identifikatoru.
     *
     * @param o objekat sa kojim se poredi stavka
     * @return {@code true} ako imaju isti identifikator,
     * a {@code false} u suprotnom
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StavkaPorudzbine that)) {
            return false;
        }
        return id != null && id.equals(that.id);
    }

    /**
     * Vraća hash code vrednost stavke.
     *
     * @return hash code vrednost
     */
    @Override
    public int hashCode() {
        return java.util.Objects.hashCode(id);
    }

    /**
     * Vraća tekstualni prikaz stavke porudžbine.
     *
     * @return tekstualni prikaz stavke
     */
    @Override
    public String toString() {
        return "StavkaPorudzbine{id=%d, rb=%s, kolicina=%s, cenaK=%s}"
                .formatted(id, rb, kolicina, cenaK);
    }
}
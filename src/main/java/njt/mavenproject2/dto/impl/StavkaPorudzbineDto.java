package njt.mavenproject2.dto.impl;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import njt.mavenproject2.dto.Dto;

/**
 * DTO koji predstavlja stavku porudžbine.
 *
 * Koristi se za prenos podataka o pojedinačnoj stavki porudžbine
 * između klijentskog i serverskog dela aplikacije.
 *
 * @author Korisnik
 */
public class StavkaPorudzbineDto implements Dto {

    /**
     * Jedinstveni identifikator stavke porudžbine.
     */
    private Long id;

    /**
     * Redni broj stavke u okviru porudžbine.
     */
    private Integer rb;

    /**
     * Identifikator knjige.
     */
    @NotNull(message = "knjigaId je obavezan")
    private Long knjigaId;

    /**
     * Količina knjiga.
     */
    @NotNull(message = "kolicina je obavezna")
    @Positive(message = "kolicina mora biti > 0")
    private Integer kolicina;

    /**
     * Cena knjige u trenutku kreiranja porudžbine.
     */
    @NotNull(message = "cenaK je obavezna")
    @Positive(message = "cenaK mora biti > 0")
    private Double cenaK;

    /**
     * Kreira prazan DTO objekat stavke porudžbine.
     */
    public StavkaPorudzbineDto() {
    }

    /**
     * Kreira DTO objekat stavke porudžbine sa zadatim podacima.
     *
     * @param id identifikator stavke
     * @param rb redni broj stavke
     * @param knjigaId identifikator knjige
     * @param kolicina količina knjiga
     * @param cenaK cena knjige
     */
    public StavkaPorudzbineDto(Long id, Integer rb, Long knjigaId, Integer kolicina, Double cenaK) {
        this.id = id;
        this.rb = rb;
        this.knjigaId = knjigaId;
        this.kolicina = kolicina;
        this.cenaK = cenaK;
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
     * Vraća identifikator knjige.
     *
     * @return identifikator knjige
     */
    public Long getKnjigaId() {
        return knjigaId;
    }

    /**
     * Postavlja identifikator knjige.
     *
     * @param knjigaId identifikator knjige
     */
    public void setKnjigaId(Long knjigaId) {
        this.knjigaId = knjigaId;
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
}
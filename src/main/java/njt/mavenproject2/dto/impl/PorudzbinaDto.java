package njt.mavenproject2.dto.impl;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import njt.mavenproject2.dto.Dto;

/**
 * DTO koji predstavlja porudžbinu u sistemu online knjižare.
 *
 * Koristi se za razmenu podataka o porudžbinama između klijentskog i
 * serverskog dela aplikacije. Sadrži podatke o korisniku, stavkama
 * porudžbine, ukupnom iznosu, statusu i datumu kreiranja porudžbine.
 *
 * @author Korisnik
 */
public class PorudzbinaDto implements Dto {

    /**
     * Jedinstveni identifikator porudžbine.
     */
    @JsonProperty(access = Access.READ_ONLY)
    private Long id;

    /**
     * Datum i vreme kreiranja porudžbine.
     */
    @JsonProperty(access = Access.READ_ONLY)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime datum;

    /**
     * Identifikator korisnika koji kreira porudžbinu.
     */
    @JsonProperty(value = "korisnik_id", access = Access.WRITE_ONLY)
    @NotNull(message = "korisnik_id je obavezan")
    private Long korisnikId;

    /**
     * Ukupan iznos porudžbine.
     */
    @JsonProperty(value = "ukupan_iznos", access = Access.READ_ONLY)
    private Double ukupanIznos;

    /**
     * Status porudžbine.
     */
    private String status;

    /**
     * Lista stavki porudžbine.
     */
    @NotEmpty(message = "Stavke su obavezne")
    private List<Stavka> stavke = new ArrayList<>();

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
     * Vraća identifikator korisnika.
     *
     * @return identifikator korisnika
     */
    public Long getKorisnikId() {
        return korisnikId;
    }

    /**
     * Postavlja identifikator korisnika.
     *
     * @param korisnikId identifikator korisnika
     */
    public void setKorisnikId(Long korisnikId) {
        this.korisnikId = korisnikId;
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
     * Vraća listu stavki porudžbine.
     *
     * @return lista stavki porudžbine
     */
    public List<Stavka> getStavke() {
        return stavke;
    }

    /**
     * Postavlja listu stavki porudžbine.
     *
     * @param stavke lista stavki porudžbine
     */
    public void setStavke(List<Stavka> stavke) {
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
     * Predstavlja jednu stavku porudžbine.
     *
     * Sadrži podatke o knjizi, količini, ceni i nazivu knjige.
     */
    public static class Stavka {

        /**
         * Identifikator knjige.
         */
        @JsonProperty(value = "knjiga_id", access = Access.WRITE_ONLY)
        @NotNull(message = "knjiga_id je obavezno")
        private Long knjigaId;

        /**
         * Količina naručenih primeraka knjige.
         */
        @NotNull(message = "kolicina je obavezna")
        private Integer kolicina;

        /**
         * Cena jednog primerka knjige.
         */
        @NotNull(message = "cena je obavezna")
        private Double cena;

        /**
         * Naziv knjige.
         */
        @JsonProperty(access = Access.READ_ONLY)
        private String naziv;

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
        public Double getCena() {
            return cena;
        }

        /**
         * Postavlja cenu knjige.
         *
         * @param cena cena knjige
         */
        public void setCena(Double cena) {
            this.cena = cena;
        }

        /**
         * Vraća naziv knjige.
         *
         * @return naziv knjige
         */
        public String getNaziv() {
            return naziv;
        }

        /**
         * Postavlja naziv knjige.
         *
         * @param naziv naziv knjige
         */
        public void setNaziv(String naziv) {
            this.naziv = naziv;
        }
    }
}
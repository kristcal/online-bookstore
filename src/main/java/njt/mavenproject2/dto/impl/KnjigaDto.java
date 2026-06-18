package njt.mavenproject2.dto.impl;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import njt.mavenproject2.dto.Dto;

/**
 * DTO koji predstavlja knjigu u sistemu online knjižare.
 *
 * Koristi se za prenos podataka o knjizi između klijentskog i serverskog dela
 * aplikacije. Sadrži osnovne podatke o knjizi, podatke o žanru, autorima,
 * ukupnoj količini i dostupnosti po knjižarama.
 *
 * @author Korisnik
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class KnjigaDto implements Dto {

    /**
     * Jedinstveni identifikator knjige.
     */
    private Long id;

    /**
     * Naziv knjige.
     */
    private String naziv;

    /**
     * Opis knjige.
     */
    private String opis;

    /**
     * Cena knjige.
     */
    private Double cena;

    /**
     * ISBN broj knjige.
     */
    private String isbn;

    /**
     * Godina, odnosno datum izdanja knjige.
     */
    @JsonAlias({"godina_izdanja"})
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate godinaIzdanja;

    /**
     * URL adresa slike knjige.
     */
    @JsonAlias({"image_url"})
    private String imageUrl;

    /**
     * Identifikator žanra kojem knjiga pripada.
     */
    @JsonAlias({"zanr_id"})
    private Long zanrId;

    /**
     * Naziv žanra kojem knjiga pripada.
     */
    private String zanrNaziv;

    /**
     * Lista autora knjige.
     */
    private List<AutorView> autori = new ArrayList<>();

    /**
     * Ukupna količina knjige dostupna u svim knjižarama.
     */
    private Integer kolicina;

    /**
     * Lista dostupnosti knjige po knjižarama.
     */
    private List<DostupnostView> dostupnost = new ArrayList<>();

    /**
     * DTO prikaz autora knjige.
     *
     * Sadrži osnovne podatke o autoru i njegovu ulogu u nastanku knjige.
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class AutorView {

        /**
         * Identifikator autora.
         */
        public Long id;

        /**
         * Ime autora.
         */
        public String ime;

        /**
         * Prezime autora.
         */
        public String prezime;

        /**
         * Uloga autora.
         */
        public String uloga;
    }

    /**
     * DTO prikaz dostupnosti knjige u jednoj knjižari.
     *
     * Sadrži podatke o knjižari i količini knjige dostupnoj u toj knjižari.
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DostupnostView {

        /**
         * Identifikator knjižare.
         */
        @JsonAlias({"knjizara_id"})
        public Long knjizaraId;

        /**
         * Naziv knjižare.
         */
        @JsonAlias({"knjizara_naziv"})
        public String knjizaraNaziv;

        /**
         * Lokacija knjižare.
         */
        @JsonAlias({"knjizara_lokacija"})
        public String lokacija;

        /**
         * Količina knjige dostupna u knjižari.
         */
        public Integer kolicina;
    }

    /**
     * Vraća identifikator knjige.
     *
     * @return identifikator knjige
     */
    public Long getId() {
        return id;
    }

    /**
     * Postavlja identifikator knjige.
     *
     * @param id identifikator knjige
     */
    public void setId(Long id) {
        this.id = id;
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

    /**
     * Vraća opis knjige.
     *
     * @return opis knjige
     */
    public String getOpis() {
        return opis;
    }

    /**
     * Postavlja opis knjige.
     *
     * @param opis opis knjige
     */
    public void setOpis(String opis) {
        this.opis = opis;
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
     * Vraća ISBN broj knjige.
     *
     * @return ISBN broj knjige
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * Postavlja ISBN broj knjige.
     *
     * @param isbn ISBN broj knjige
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    /**
     * Vraća datum izdanja knjige.
     *
     * @return datum izdanja knjige
     */
    public LocalDate getGodinaIzdanja() {
        return godinaIzdanja;
    }

    /**
     * Postavlja datum izdanja knjige.
     *
     * @param godinaIzdanja datum izdanja knjige
     */
    public void setGodinaIzdanja(LocalDate godinaIzdanja) {
        this.godinaIzdanja = godinaIzdanja;
    }

    /**
     * Vraća URL adresu slike knjige.
     *
     * @return URL adresa slike knjige
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * Postavlja URL adresu slike knjige.
     *
     * @param imageUrl URL adresa slike knjige
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    /**
     * Vraća identifikator žanra.
     *
     * @return identifikator žanra
     */
    public Long getZanrId() {
        return zanrId;
    }

    /**
     * Postavlja identifikator žanra.
     *
     * @param zanrId identifikator žanra
     */
    public void setZanrId(Long zanrId) {
        this.zanrId = zanrId;
    }

    /**
     * Vraća naziv žanra.
     *
     * @return naziv žanra
     */
    public String getZanrNaziv() {
        return zanrNaziv;
    }

    /**
     * Postavlja naziv žanra.
     *
     * @param zanrNaziv naziv žanra
     */
    public void setZanrNaziv(String zanrNaziv) {
        this.zanrNaziv = zanrNaziv;
    }

    /**
     * Vraća listu autora knjige.
     *
     * @return lista autora knjige
     */
    public List<AutorView> getAutori() {
        return autori;
    }

    /**
     * Postavlja listu autora knjige.
     *
     * @param autori lista autora knjige
     */
    public void setAutori(List<AutorView> autori) {
        this.autori = autori;
    }

    /**
     * Vraća ukupnu količinu knjige.
     *
     * @return ukupna količina knjige
     */
    public Integer getKolicina() {
        return kolicina;
    }

    /**
     * Postavlja ukupnu količinu knjige.
     *
     * @param kolicina ukupna količina knjige
     */
    public void setKolicina(Integer kolicina) {
        this.kolicina = kolicina;
    }

    /**
     * Vraća dostupnost knjige po knjižarama.
     *
     * @return lista dostupnosti knjige
     */
    public List<DostupnostView> getDostupnost() {
        return dostupnost;
    }

    /**
     * Postavlja dostupnost knjige po knjižarama.
     *
     * @param dostupnost lista dostupnosti knjige
     */
    public void setDostupnost(List<DostupnostView> dostupnost) {
        this.dostupnost = dostupnost;
    }
}
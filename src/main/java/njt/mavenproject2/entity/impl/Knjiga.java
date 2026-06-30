/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package njt.mavenproject2.entity.impl;

import jakarta.persistence.CascadeType;
import jakarta.persistence.*;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import njt.mavenproject2.entity.MyEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

/**
 * Predstavlja knjigu u sistemu online knjižare.
 *
 * Knjiga sadrži osnovne bibliografske podatke kao što su naziv, opis, cena,
 * ISBN broj, godina izdanja i slika korice. Knjiga pripada jednom žanru, može
 * imati više autora i može biti dostupna u jednoj ili više knjižara.
 *
 * Klasa je JPA entitet i mapira se na tabelu {@code knjiga}.
 *
 * @author Korisnik
 */
@Entity
@Table(name = "knjiga")
public class Knjiga implements MyEntity {

	/**
     * Jedinstveni identifikator knjige.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Naziv knjige.
     */
    @NotBlank
    @Size(max = 200)
    private String naziv;

    /**
     * Opis knjige.
     */
    @Size(max = 1000)
    private String opis;

    /**
     * Cena knjige.
     */
    @NotNull
    @Positive
    private Double cena;

    /**
     * ISBN broj knjige.
     */
    @NotBlank
    @Size(max = 30)
    private String isbn;

    /**
     * Datum koji predstavlja godinu izdanja knjige.
     */
    @NotNull
    private LocalDate godinaIzdanja;

    /**
     * URL slike korice knjige.
     */
    @Size(max = 500)
    private String imageUrl;

    /**
     * Lista veza između knjige i njenih autora.
     */
    @OneToMany(mappedBy = "knjiga",
           cascade = CascadeType.ALL,
           orphanRemoval = true,
           fetch = FetchType.LAZY)
    private List<KnjigaAutor> autori = new ArrayList<>();

    /**
     * Žanr kojem knjiga pripada.
     */
    @ManyToOne
    @JoinColumn(name = "zanr_id")
    private Zanr zanr;

    /**
     * Lista podataka o dostupnosti knjige u knjižarama.
     */
    @OneToMany(mappedBy = "knjiga",
           cascade = CascadeType.ALL,
           orphanRemoval = true,
           fetch = FetchType.LAZY)
    private List<KnjigaKnjizara> dostupnost = new ArrayList<>();

    /**
     * Kreira prazan objekat klase Knjiga.
     */
    public Knjiga() {
    }

    /**
     * Kreira knjigu sa zadatim podacima.
     *
     * @param id jedinstveni identifikator knjige
     * @param naziv naziv knjige, ne sme biti prazan i može imati najviše 200 karaktera
     * @param opis opis knjige, može imati najviše 1000 karaktera
     * @param cena cena knjige, mora biti pozitivna vrednost
     * @param isbn ISBN broj knjige, ne sme biti prazan i može imati najviše 30 karaktera
     * @param godinaIzdanja datum koji predstavlja godinu izdanja knjige
     * @param imageUrl URL slike korice knjige, može imati najviše 500 karaktera
     * @param zanr žanr kojem knjiga pripada
     */
    public Knjiga(Long id, String naziv, String opis, Double cena, String isbn,
            LocalDate godinaIzdanja, String imageUrl, Zanr zanr) {
        this.id = id;
        this.naziv = naziv;
        this.opis = opis;
        this.cena = cena;
        this.isbn = isbn;
        this.godinaIzdanja = godinaIzdanja;
        this.imageUrl = imageUrl;
        this.zanr = zanr;
    }

    /**
     * Vraca identifikator knjige.
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
     * Vraca naziv knjige.
     *
     * @return naziv knjige
     */
    public String getNaziv() {
        return naziv;
    }

    /**
     * Postavlja naziv knjige.
     *
     * @param naziv naziv knjige, ne sme biti prazan i može imati najviše 200 karaktera
     */
    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    /**
     * Vraca opis knjige.
     *
     * @return opis knjige
     */
    public String getOpis() {
        return opis;
    }

    /**
     * Postavlja opis knjige.
     *
     * @param opis opis knjige, može imati najviše 1000 karaktera
     */
    public void setOpis(String opis) {
        this.opis = opis;
    }

    /**
     * Vraca cenu knjige.
     *
     * @return cena knjige
     */
    public Double getCena() {
        return cena;
    }

    /**
     * Postavlja cenu knjige.
     *
     * @param cena cena knjige, mora biti pozitivna vrednost
     */
    public void setCena(Double cena) {
        this.cena = cena;
    }

    /**
     * Vraca ISBN broj knjige.
     *
     * @return ISBN broj knjige
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * Postavlja ISBN broj knjige.
     *
     * @param isbn ISBN broj knjige, ne sme biti prazan i može imati najviše 30 karaktera
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    /**
     * Vraca datum koji predstavlja godinu izdanja knjige.
     *
     * @return datum izdanja knjige
     */
    public LocalDate getGodinaIzdanja() {
        return godinaIzdanja;
    }

    /**
     * Postavlja datum koji predstavlja godinu izdanja knjige.
     *
     * @param godinaIzdanja datum izdanja knjige
     */
    public void setGodinaIzdanja(LocalDate godinaIzdanja) {
        this.godinaIzdanja = godinaIzdanja;
    }

    /**
     * Vraca URL slike korice knjige.
     *
     * @return URL slike korice
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * Postavlja URL slike korice knjige.
     *
     * @param imageUrl URL slike korice, može imati najviše 500 karaktera
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    /**
     * Vraca listu autora knjige preko veza knjiga-autor.
     *
     * @return lista veza između knjige i autora
     */
    public List<KnjigaAutor> getAutori() {
        return autori;
    }

    /**
     * Postavlja listu autora knjige preko veza knjiga-autor.
     *
     * @param autori lista veza između knjige i autora
     */
    public void setAutori(List<KnjigaAutor> autori) {
        this.autori = autori;
    }

    /**
     * Vraca žanr kojem knjiga pripada.
     *
     * @return žanr knjige
     */
    public Zanr getZanr() {
        return zanr;
    }

    /**
     * Postavlja žanr kojem knjiga pripada.
     *
     * @param zanr žanr knjige
     */
    public void setZanr(Zanr zanr) {
        this.zanr = zanr;
    }

    /**
     * Vraca listu dostupnosti knjige u knjižarama.
     *
     * @return lista podataka o dostupnosti knjige
     */
    public List<KnjigaKnjizara> getDostupnost() {
        return dostupnost;
    }

    /**
     * Postavlja listu dostupnosti knjige u knjižarama.
     *
     * @param dostupnost lista podataka o dostupnosti knjige
     */
    public void setDostupnost(List<KnjigaKnjizara> dostupnost) {
        this.dostupnost = dostupnost;
    }
    
    /**
     * Poredi dve knjige prema identifikatoru.
     *
     * @param o objekat sa kojim se poredi trenutna knjiga
     * @return {@code true} ako su objekti isti ili imaju isti identifikator,
     * a {@code false} u suprotnom
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Knjiga that)) {
            return false;
        }
        return id != null && id.equals(that.id);
    }

    /**
     * Vraca hash code vrednost knjige na osnovu identifikatora.
     *
     * @return hash code vrednost knjige
     */
    @Override
    public int hashCode() {
        return java.util.Objects.hashCode(id);
    }

    /**
     * Vraca tekstualni prikaz knjige.
     *
     * @return tekstualni prikaz knjige sa identifikatorom, nazivom i ISBN brojem
     */
    @Override
    public String toString() {
        return "Knjiga{id=%d, naziv=%s, isbn=%s}".formatted(id, naziv, isbn);
    }
}

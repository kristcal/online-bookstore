/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package njt.mavenproject2.dto.impl;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import njt.mavenproject2.dto.Dto;
import njt.mavenproject2.entity.impl.KnjigaKnjizara;

/**
 * DTO koji predstavlja knjižaru u sistemu online knjižare.
 *
 * Koristi se za prenos podataka o knjižarama između klijentskog i
 * serverskog dela aplikacije.
 *
 * Sadrži osnovne podatke o knjižari i njenu ponudu knjiga.
 *
 * @author Korisnik
 */
public class KnjizaraDto implements Dto {

    /**
     * Jedinstveni identifikator knjižare.
     */
    private Long id;

    /**
     * Naziv knjižare.
     */
    @NotEmpty(message = "potreban je naziv")
    private String naziv;

    /**
     * Lokacija knjižare.
     */
    @NotBlank(message = "potrebna je lokacija")
    @Size(max = 200, message = "Lokacija ne moze biti duza od 200 karaktera")
    private String lokacija;

    /**
     * Kontakt podaci knjižare.
     */
    @NotEmpty(message = "potreban je kontakt")
    private String kontakt;

    /**
     * Lista knjiga koje su dostupne u knjižari.
     */
    private List<KnjigaKnjizaraDto> ponuda = new ArrayList<>();

    /**
     * Kreira DTO objekat knjižare sa zadatim podacima.
     *
     * @param id identifikator knjižare
     * @param naziv naziv knjižare
     * @param lokacija lokacija knjižare
     * @param kontakt kontakt podaci knjižare
     */
    public KnjizaraDto(Long id, String naziv, String lokacija, String kontakt) {
        this.id = id;
        this.naziv = naziv;
        this.lokacija = lokacija;
        this.kontakt = kontakt;
    }

    /**
     * Vraća identifikator knjižare.
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
     * Vraća naziv knjižare.
     *
     * @return naziv knjižare
     */
    public String getNaziv() {
        return naziv;
    }

    /**
     * Postavlja naziv knjižare.
     *
     * @param naziv naziv knjižare
     */
    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    /**
     * Vraća lokaciju knjižare.
     *
     * @return lokacija knjižare
     */
    public String getLokacija() {
        return lokacija;
    }

    /**
     * Postavlja lokaciju knjižare.
     *
     * @param lokacija lokacija knjižare
     */
    public void setLokacija(String lokacija) {
        this.lokacija = lokacija;
    }

    /**
     * Vraća kontakt podatke knjižare.
     *
     * @return kontakt podaci knjižare
     */
    public String getKontakt() {
        return kontakt;
    }

    /**
     * Postavlja kontakt podatke knjižare.
     *
     * @param kontakt kontakt podaci knjižare
     */
    public void setKontakt(String kontakt) {
        this.kontakt = kontakt;
    }

    /**
     * Vraća ponudu knjižare.
     *
     * @return lista dostupnih knjiga
     */
    public List<KnjigaKnjizaraDto> getPonuda() {
        return ponuda;
    }

    /**
     * Postavlja ponudu knjižare.
     *
     * @param ponuda lista dostupnih knjiga
     */
    public void setPonuda(List<KnjigaKnjizaraDto> ponuda) {
        this.ponuda = ponuda;
    }
}
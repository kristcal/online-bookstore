/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package njt.mavenproject2.dto.impl;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import njt.mavenproject2.dto.Dto;

/**
 * DTO koji predstavlja vezu između knjige i knjižare.
 *
 * Koristi se za prenos podataka o količini knjiga dostupnih
 * u određenoj knjižari.
 *
 * @author Korisnik
 */
public class KnjigaKnjizaraDto implements Dto {

    /**
     * Jedinstveni identifikator veze knjiga-knjižara.
     */
    private Long id;

    /**
     * Identifikator knjižare.
     */
    @NotNull(message = "knjizaraId je obavezan")
    private Long knjizaraId;

    /**
     * Količina knjiga dostupnih u knjižari.
     */
    @NotNull(message = "kolicina je obavezna")
    @PositiveOrZero(message = "kolicina mora biti >= 0")
    private Integer kolicina;

    /**
     * Lokacija knjižare.
     */
    private String lokacija;

    /**
     * Kreira prazan DTO objekat.
     */
    public KnjigaKnjizaraDto() {
    }

    /**
     * Kreira DTO objekat sa zadatim podacima.
     *
     * @param id identifikator veze
     * @param knjizaraId identifikator knjižare
     * @param kolicina količina knjiga
     */
    public KnjigaKnjizaraDto(Long id, Long knjizaraId, Integer kolicina) {
        this.id = id;
        this.knjizaraId = knjizaraId;
        this.kolicina = kolicina;
    }

    /**
     * Vraća identifikator veze.
     *
     * @return identifikator veze
     */
    public Long getId() {
        return id;
    }

    /**
     * Postavlja identifikator veze.
     *
     * @param id identifikator veze
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Vraća identifikator knjižare.
     *
     * @return identifikator knjižare
     */
    public Long getKnjizaraId() {
        return knjizaraId;
    }

    /**
     * Postavlja identifikator knjižare.
     *
     * @param knjizaraId identifikator knjižare
     */
    public void setKnjizaraId(Long knjizaraId) {
        this.knjizaraId = knjizaraId;
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
}
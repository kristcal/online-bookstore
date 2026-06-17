/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package njt.mavenproject2.entity.impl;

import jakarta.persistence.*;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import njt.mavenproject2.entity.MyEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Predstavlja autora knjige u sistemu online knjižare.
 *
 * Autor ima ime i prezime, kao i listu veza sa knjigama u kojima učestvuje.
 * Veza sa knjigom se realizuje preko klase {@link KnjigaAutor}, jer jedan
 * autor može biti povezan sa više knjiga, a jedna knjiga može imati više autora.
 *
 * Klasa je JPA entitet i mapira se na tabelu {@code autor}.
 *
 * @author Korisnik
 */
@Entity
@Table(name = "autor")
public class Autor implements MyEntity {

	/**
     * Jedinstveni identifikator autora.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Ime autora.
     */
    @NotBlank
    @Size(max = 60)
    private String ime;
    
    /**
     * Prezime autora.
     */
    @NotBlank
    @Size(max = 60)
    private String prezime;

    /**
     * Lista veza između autora i knjiga.
     */
    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<KnjigaAutor> knjige = new ArrayList<>();

    /**
     * Kreira prazan objekat klase Autor.
     */
    public Autor() {
    }

    /**
     * Kreira autora sa zadatim podacima.
     *
     * @param id jedinstveni identifikator autora
     * @param ime ime autora, ne sme biti prazno i može imati najviše 60 karaktera
     * @param prezime prezime autora, ne sme biti prazno i može imati najviše 60 karaktera
     */
    public Autor(Long id, String ime, String prezime) {
        this.id = id;
        this.ime = ime;
        this.prezime = prezime;
    }

    /**
     * Vraca identifikator autora.
     *
     * @return identifikator autora
     */
    public Long getId() {
        return id;
    }

    /**
     * Postavlja identifikator autora.
     *
     * @param id identifikator autora
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Vraca ime autora.
     *
     * @return ime autora
     */
    public String getIme() {
        return ime;
    }

    /**
     * Postavlja ime autora.
     *
     * @param ime ime autora, ne sme biti prazno i može imati najviše 60 karaktera
     */
    public void setIme(String ime) {
        this.ime = ime;
    }

    /**
     * Vraca prezime autora.
     *
     * @return prezime autora
     */
    public String getPrezime() {
        return prezime;
    }

    /**
     * Postavlja prezime autora.
     *
     * @param prezime prezime autora, ne sme biti prazno i može imati najviše 60 karaktera
     */
    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    /**
     * Vraca listu veza između autora i knjiga.
     *
     * @return lista objekata klase {@link KnjigaAutor}
     */
    public List<KnjigaAutor> getKnjige() {
        return knjige;
    }

    /**
     * Postavlja listu veza između autora i knjiga.
     *
     * @param knjige lista veza između autora i knjiga
     */
    public void setKnjige(List<KnjigaAutor> knjige) {
        this.knjige = knjige;
    }
    
    /**
     * Poredi dva autora prema identifikatoru.
     *
     * @param o objekat sa kojim se poredi trenutni autor
     * @return {@code true} ako su objekti isti ili imaju isti identifikator,
     * a {@code false} u suprotnom
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Autor that)) {
            return false;
        }
        return id != null && id.equals(that.id);
    }

    /**
     * Vraca hash code vrednost autora na osnovu identifikatora.
     *
     * @return hash code vrednost autora
     */
    @Override
    public int hashCode() {
        return java.util.Objects.hashCode(id);
    }

    /**
     * Vraca tekstualni prikaz autora.
     *
     * @return tekstualni prikaz autora sa identifikatorom, imenom i prezimenom
     */
    @Override
    public String toString() {
        return "Autor{id=%d, ime=%s, prezime=%s}".formatted(id, ime, prezime);
    }

}

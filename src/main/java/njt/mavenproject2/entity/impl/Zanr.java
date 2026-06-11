/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package njt.mavenproject2.entity.impl;

import jakarta.persistence.*;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import njt.mavenproject2.entity.MyEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
/**
 *
 * @author Korisnik
 */
@Entity
@Table(name="zanr")
public class Zanr implements MyEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 50)
    private String naziv;

    @OneToMany(mappedBy = "zanr", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Knjiga> knjige = new ArrayList<>();

    public Zanr() {}

    public Zanr(Long id, String naziv) {
        this.id = id;
        this.naziv = naziv;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNaziv() { return naziv; }
    public void setNaziv(String naziv) { this.naziv = naziv; }
    public List<Knjiga> getKnjige() { return knjige; }
    public void setKnjige(List<Knjiga> knjige) { this.knjige = knjige; }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Zanr that)) {
            return false;
        }
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Zanr{id=%d, naziv=%s}".formatted(id, naziv);
    }
    
}

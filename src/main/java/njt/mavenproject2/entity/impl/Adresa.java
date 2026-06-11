package njt.mavenproject2.entity.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import njt.mavenproject2.entity.MyEntity;

@Entity
@Table(name = "adresa")
public class Adresa implements MyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String ulica;

    @NotBlank
    @Size(max = 20)
    @Column(nullable = false, length = 20)
    private String broj;

    @NotBlank
    @Size(max = 60)
    @Column(nullable = false, length = 60)
    private String grad;

    @NotBlank
    @Size(max = 20)
    @Column(nullable = false, length = 20)
    private String postanskiBroj;

    @NotBlank
    @Size(max = 60)
    @Column(nullable = false, length = 60)
    private String drzava;

    @JsonIgnore
    @OneToMany(mappedBy = "adresa")
    private List<Korisnik> korisnici = new ArrayList<>();

    public Adresa() {
    }

    public Adresa(Long id, String ulica, String broj, String grad, String postanskiBroj, String drzava) {
        this.id = id;
        this.ulica = ulica;
        this.broj = broj;
        this.grad = grad;
        this.postanskiBroj = postanskiBroj;
        this.drzava = drzava;
    }

    public Long getId() {
        return id;
    }

    public String getUlica() {
        return ulica;
    }

    public String getBroj() {
        return broj;
    }

    public String getGrad() {
        return grad;
    }

    public String getPostanskiBroj() {
        return postanskiBroj;
    }

    public String getDrzava() {
        return drzava;
    }

    public List<Korisnik> getKorisnici() {
        return korisnici;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUlica(String ulica) {
        this.ulica = ulica;
    }

    public void setBroj(String broj) {
        this.broj = broj;
    }

    public void setGrad(String grad) {
        this.grad = grad;
    }

    public void setPostanskiBroj(String postanskiBroj) {
        this.postanskiBroj = postanskiBroj;
    }

    public void setDrzava(String drzava) {
        this.drzava = drzava;
    }

    public void setKorisnici(List<Korisnik> korisnici) {
        this.korisnici = korisnici;
    }
}
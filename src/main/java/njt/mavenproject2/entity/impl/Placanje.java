package njt.mavenproject2.entity.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import njt.mavenproject2.entity.MyEntity;

@Entity
@Table(name = "placanje")
public class Placanje implements MyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Positive
    @Column(nullable = false)
    private Double iznos;

    @NotBlank
    @Size(max = 50)
    @Column(nullable = false, length = 50)
    private String nacinPlacanja;

    @NotBlank
    @Size(max = 50)
    @Column(nullable = false, length = 50)
    private String statusPlacanja;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime datumPlacanja;

    @NotBlank
    @Size(max = 50)
    @Column(nullable = false, length = 50)
    private String pozivNaBroj;

    @JsonIgnore
    @OneToOne(mappedBy = "placanje")
    private Porudzbina porudzbina;

    public Placanje() {
    }

    public Placanje(Long id, Double iznos, String nacinPlacanja, String statusPlacanja,
            LocalDateTime datumPlacanja, String pozivNaBroj) {
        this.id = id;
        this.iznos = iznos;
        this.nacinPlacanja = nacinPlacanja;
        this.statusPlacanja = statusPlacanja;
        this.datumPlacanja = datumPlacanja;
        this.pozivNaBroj = pozivNaBroj;
    }

    public Long getId() {
        return id;
    }

    public Double getIznos() {
        return iznos;
    }

    public String getNacinPlacanja() {
        return nacinPlacanja;
    }

    public String getStatusPlacanja() {
        return statusPlacanja;
    }

    public LocalDateTime getDatumPlacanja() {
        return datumPlacanja;
    }

    public String getPozivNaBroj() {
        return pozivNaBroj;
    }

    public Porudzbina getPorudzbina() {
        return porudzbina;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setIznos(Double iznos) {
        this.iznos = iznos;
    }

    public void setNacinPlacanja(String nacinPlacanja) {
        this.nacinPlacanja = nacinPlacanja;
    }

    public void setStatusPlacanja(String statusPlacanja) {
        this.statusPlacanja = statusPlacanja;
    }

    public void setDatumPlacanja(LocalDateTime datumPlacanja) {
        this.datumPlacanja = datumPlacanja;
    }

    public void setPozivNaBroj(String pozivNaBroj) {
        this.pozivNaBroj = pozivNaBroj;
    }

    public void setPorudzbina(Porudzbina porudzbina) {
        this.porudzbina = porudzbina;
    }
}
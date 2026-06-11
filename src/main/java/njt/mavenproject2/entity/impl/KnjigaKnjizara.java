/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package njt.mavenproject2.entity.impl;

import jakarta.persistence.*;
import njt.mavenproject2.entity.MyEntity;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
/**
 *
 * @author Korisnik
 */
@Entity
@Table(name = "knjiga_knjizara",
       uniqueConstraints = @UniqueConstraint(name = "uk_knjiga_knjizara",
                                             columnNames = {"knjiga_id", "knjizara_id"}))
public class KnjigaKnjizara implements MyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "knjiga_id")
    private Knjiga knjiga;

    @ManyToOne(optional = false)
    @JoinColumn(name = "knjizara_id")
    private Knjizara knjizara;

    @NotNull
    @PositiveOrZero
    @Column(nullable = false)
    private Integer kolicina =0;

    public KnjigaKnjizara() {
    }

    public KnjigaKnjizara(Knjiga knjiga, Knjizara knjizara, Integer kolicina) {
        this.knjiga = knjiga;
        this.knjizara = knjizara;
        this.kolicina = kolicina;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    

    public Knjiga getKnjiga() {
        return knjiga;
    }

    public void setKnjiga(Knjiga knjiga) {
        this.knjiga = knjiga;
    }

    public Knjizara getKnjizara() {
        return knjizara;
    }

    public void setKnjizara(Knjizara knjizara) {
        this.knjizara = knjizara;
    }

    public Integer getKolicina() {
        return kolicina;
    }

    public void setKolicina(Integer kolicina) {
        this.kolicina = kolicina;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof KnjigaKnjizara that)) {
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
        return "KnjigaKnjizara{id=%d, kolicina=%s}".formatted(id, kolicina);
    }


}

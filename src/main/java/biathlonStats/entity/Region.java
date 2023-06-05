package biathlonStats.entity;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;

@Entity
@Table(name = "region", schema = "biathlonstats1")
public class Region {

    @Id
    @GenericGenerator(name = "increment", strategy = "increment")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int idregion;
    private String name;

    public Region() {
    }

    public Region(int idregion, String name) {
        this.idregion = idregion;
        this.name = name;
    }

    public int getIdregion() {
        return idregion;
    }

    public void setIdregion(int idregion) {
        this.idregion = idregion;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

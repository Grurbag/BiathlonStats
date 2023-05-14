package biathlonStats.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "region", schema = "biathlonstats")
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

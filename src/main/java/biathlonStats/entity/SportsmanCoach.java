package biathlonStats.entity;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "sportsmancoach", schema = "biathlonstats")
public class SportsmanCoach implements Serializable {

    @Id
    @GenericGenerator(name = "increment", strategy = "increment")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private String idsportsman;
    private int idcoach;


    public SportsmanCoach() {
    }

    public SportsmanCoach(String idsportsman, int idcoach) {
        this.idcoach = idcoach;
        this.idsportsman = idsportsman;
    }

    public int getIdCoach() {
        return idcoach;
    }

    public void setIdCoach(int idcoach) {
        this.idcoach = idcoach;
    }

    public String getIdsportsman() {
        return idsportsman;
    }

    public void setIdsportsman(String idsportsman) {
        this.idsportsman = idsportsman;
    }

    // equals() and hashCode()
}
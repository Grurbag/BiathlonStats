package biathlonStats.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "racesportsman", schema = "biathlonstats")
public class SportsmanRace {

    @Id
    @GenericGenerator(name = "increment", strategy = "increment")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long idrace;
    private String idsportsman;
    private String place;
    private String datetime;
    private int standingaccuracy;
    private int layingaccuracy;

    public SportsmanRace() {
    }

    public SportsmanRace(long idrace, String idsportsman, String place, String datetime, int stading_accuracy, int laying_accuracy) {
        this.idrace = idrace;
        this.idsportsman = idsportsman;
        this.place = place;
        this.datetime = datetime;
        this.standingaccuracy = stading_accuracy;
        this.layingaccuracy = laying_accuracy;
    }

    public long getIdrace() {
        return idrace;
    }

    public String getIdsportsman() {
        return idsportsman;
    }

    public String getPlace() {
        return place;
    }

    public int getStandingaccuracy() {
        return standingaccuracy;
    }

    public int getLayingaccuracy() {
        return layingaccuracy;
    }

    public void setIdrace(long idrace) {
        this.idrace = idrace;
    }

    public void setIdsportsman(String idsportsman) {
        this.idsportsman = idsportsman;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public void setStandingaccuracy(int standingaccuracy) {
        this.standingaccuracy = standingaccuracy;
    }

    public void setLayingaccuracy(int layingaccuracy) {
        this.layingaccuracy = layingaccuracy;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }
}
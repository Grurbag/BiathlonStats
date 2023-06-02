package biathlonStats.entity;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;

@Entity
@IdClass(SportsmanRaceId.class)
@Table(name = "racesportsman", schema = "biathlonstats")
public class SportsmanRace {

    @Id
    @GenericGenerator(name = "increment", strategy = "increment")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int idrace;
    @Id
    private String idsportsman;
    private String place;
    private String datetime;
    private int standingaccuracy;
    private int layingaccuracy;
    private int placesportsman;

    public SportsmanRace() {
    }

    public SportsmanRace(int idrace, String idsportsman, String place, String datetime, int stadingaccuracy, int layingaccuracy, int placesportsman) {
        this.idrace = idrace;
        this.idsportsman = idsportsman;
        this.place = place;
        this.datetime = datetime;
        this.standingaccuracy = stadingaccuracy;
        this.layingaccuracy = layingaccuracy;
        this.placesportsman = placesportsman;
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

    public void setIdrace(int idrace) {
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

    public int getPlacesportsman() {
        return placesportsman;
    }

    public void setPlacesportsman(int placesportsman) {
        this.placesportsman = placesportsman;
    }
}
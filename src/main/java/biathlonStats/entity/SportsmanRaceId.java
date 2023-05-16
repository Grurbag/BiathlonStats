package biathlonStats.entity;

import java.io.Serializable;

public class SportsmanRaceId implements Serializable {

    private int idrace;
    private String idsportsman;

    public SportsmanRaceId() {
    }

    public SportsmanRaceId(int idrace, String idsportsman) {
        this.idrace = idrace;
        this.idsportsman = idsportsman;
    }

    public int getIdrace() {
        return idrace;
    }

    public void setIdrace(int idrace) {
        this.idrace = idrace;
    }

    public String getIdsportsman() {
        return idsportsman;
    }

    public void setIdsportsman(String idsportsman) {
        this.idsportsman = idsportsman;
    }

    // equals() and hashCode()
}
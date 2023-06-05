package biathlonStats.entity;

import java.io.Serializable;

public class SportsmanRaceId implements Serializable {

    private int idrace;
    private long idsportsman;

    public SportsmanRaceId() {
    }

    public SportsmanRaceId(int idrace, long idsportsman) {
        this.idrace = idrace;
        this.idsportsman = idsportsman;
    }

    public int getIdrace() {
        return idrace;
    }

    public void setIdrace(int idrace) {
        this.idrace = idrace;
    }

    public long getIdsportsman() {
        return idsportsman;
    }

    public void setIdsportsman(long idsportsman) {
        this.idsportsman = idsportsman;
    }

    // equals() and hashCode()
}
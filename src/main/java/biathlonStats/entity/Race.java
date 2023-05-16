package biathlonStats.entity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "race", schema = "biathlonstats")
public class Race {

    @Id
    @GenericGenerator(name = "increment", strategy = "increment")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int idrace;
    private String name;
    private String region;
    private String date;
    private String time;

    public Race() {
    }

    public Race(int idrace, String name, String region, String date, String time) {
        this.idrace = idrace;
        this.name = name;
        this.region = region;
        this.date = date;
        this.time = time;
    }

    public int getIdrace() {
        return idrace;
    }

    public void setIdrace(int idrace) {
        this.idrace = idrace;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

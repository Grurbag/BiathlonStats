package biathlonStats.entity;
import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;

@Entity
@Table(name = "race", schema = "biathlonstats1")
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

    public Race(String string) {
        String[] parameters = string.split(";");
        parameters[0] = parameters[0].replace("[","");
        parameters[4] = parameters[4].replace("]","");
        this.idrace = Integer.parseInt(parameters[0]);
        this.name = parameters[1];
        this.region = parameters[2];
        this.date = parameters[3];
        this.time = parameters[4];
    }

    public Race(int idrace, String name, String region, String date, String time, int place) {
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

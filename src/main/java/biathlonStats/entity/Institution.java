package biathlonStats.entity;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;

@Entity
@Table(name = "institution", schema = "biathlonstats1")
public class Institution {

  @Id
  @GenericGenerator(name = "increment", strategy = "increment")
  @GeneratedValue(strategy=GenerationType.AUTO)
  private long idinstitution;
  private String name;
  private String region;

  public Institution() {
  }

  public Institution(String string) {
    String[] parameters = string.split(";");
    parameters[0] = parameters[0].replace("[","");
    parameters[2] = parameters[2].replace("]","");
    this.idinstitution = Integer.parseInt(parameters[0]);
    this.name = parameters[1];
    this.region = parameters[2];
  }

  public Institution(long idinstitution, String name, String region) {
    this.idinstitution = idinstitution;
    this.name = name;
    this.region = region;
  }


  public long getIdinstitution() {
    return idinstitution;
  }

  public String getName() {
    return name;
  }

  public String getRegion() {
    return region;
  }

  public void setIdinstitution(long idinstitution) {
    this.idinstitution = idinstitution;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setRegion(String region) {
    this.region = region;
  }
}
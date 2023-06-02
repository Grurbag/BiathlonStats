package biathlonStats.entity;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;

@Entity
@Table(name = "institution", schema = "biathlonstats")
public class Institution {

  @Id
  @GenericGenerator(name = "increment", strategy = "increment")
  @GeneratedValue(strategy=GenerationType.AUTO)
  private long idinstitution;
  private String name;
  private String region;

  public Institution() {
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
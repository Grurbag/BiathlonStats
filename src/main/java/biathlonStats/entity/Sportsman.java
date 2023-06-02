package biathlonStats.entity;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;

@Entity
@Table(name = "sportsman", schema = "biathlonstats")
public class Sportsman {

  @Id
  @GenericGenerator(name = "increment", strategy = "increment")
  @GeneratedValue(strategy=GenerationType.AUTO)
  private String idsportsman;
  private String name;
  private String surname;
  private String sex;
  private String region;
  private String institution;
  private String rank;
  private String birthdate;

  public Sportsman() {
  }

  public Sportsman(String idSportsman, String name, String surname, String sex, String region, String institution, String rank, String birthDate) {
    this.idsportsman = idSportsman;
    this.name = name;
    this.surname = surname;
    this.sex = sex;
    this.region = region;
    this.institution = institution;
    this.rank = rank;
    this.birthdate = birthDate;
  }

  public String getId_sportsman() {
    return idsportsman;
  }

  public String getName() {
    return name;
  }

  public String getSurname() {
    return surname;
  }

  public String getSex() {
    return sex;
  }

  public String getRegion() {
    return region;
  }

  public String getInstitution() {
    return institution;
  }

  public String getRank() {
    return rank;
  }

  public String getBirthdate() {
    return birthdate;
  }

  public void setId_sportsman(String id_sportsman) {
    this.idsportsman = id_sportsman;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setSurname(String surname) {
    this.surname = surname;
  }

  public void setSex(String sex) {
    this.sex = sex;
  }

  public void setRegion(String region) {
    this.region = region;
  }

  public void setInstitution(String institution) {
    this.institution = institution;
  }

  public void setRank(String rank) {
    this.rank = rank;
  }

  public void setBirthdate(String birthdate) {
    this.birthdate = birthdate;
  }

}
package biathlonStats;

import biathlonStats.entity.Institution;
import biathlonStats.entity.Sportsman;
import biathlonStats.entity.SportsmanRace;
import biathlonStats.repo.InstitutionStatRepo;
import biathlonStats.repo.SportsmanRaceStatRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import biathlonStats.repo.SportsmanStatRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
public class MainController {

  public class stat {
    private int raceNumber;
    private int accuracy;
    private int standingAccuracy;
    private int layingAccuracy;

    public stat(int raceNumber, int accuracy, int standingAccuracy, int layingAccuracy) {
      this.raceNumber = raceNumber;
      this.accuracy = accuracy;
      this.standingAccuracy = standingAccuracy;
      this.layingAccuracy = layingAccuracy;
    }

    public int getRaceNumber() {
      return raceNumber;
    }

    public void setRaceNumber(int raceNumber) {
      this.raceNumber = raceNumber;
    }

    public int getAccuracy() {
      return accuracy;
    }

    public void setAccuracy(int accuracy) {
      this.accuracy = accuracy;
    }

    public int getStandingAccuracy() {
      return standingAccuracy;
    }

    public void setStandingAccuracy(int standingAccuracy) {
      this.standingAccuracy = standingAccuracy;
    }

    public int getLayingAccuracy() {
      return layingAccuracy;
    }

    public void setLayingAccuracy(int layingAccuracy) {
      this.layingAccuracy = layingAccuracy;
    }
  }

  int start = 0;
  int end = 20;

  int startInstitution = 0;
  int endInstitution = 20;

  @Autowired
  private SportsmanStatRepo sportsmanStats;

  @Autowired
  private InstitutionStatRepo institutionStats;

  @Autowired
  private SportsmanRaceStatRepo sportsmanRaceStats;

  @GetMapping("/registration")
  public String registration() {
    return "registration";
  }

  @GetMapping("/sighIn")
  public String sighIn() {
    return "sighIn";
  }

  @GetMapping("/greeting")
  public String greeting(
          @RequestParam(name="name", required=false, defaultValue="World") String name,
          Map<String, Object> model
  ) {
    model.put("name", name);
    return "greeting";
  }

  @GetMapping("/institutions")
  public String institutions(Map<String, Object> model) {
    List<Institution> institutionStat = institutionStats.findAll();
    ArrayList<Institution> showingList = new ArrayList<>();
    for (int i = startInstitution; i < endInstitution; i ++){
      showingList.add(institutionStat.get(i));
    }
    model.put("institution", showingList);
    return "institutions";
  }

  @GetMapping("/coaches")
  public String coaches() {
    return "coaches";
  }

  @GetMapping("/races")
  public String races() {
    return "races";
  }

  @GetMapping("/sportsmans")
  public String sportsmans(Map < String, Object > model) {

    List<Sportsman> sportsmanStat = sportsmanStats.findAll();
    ArrayList<Sportsman> showingList = new ArrayList<>();
    for (int i = start; i < end; i ++){
      showingList.add(sportsmanStat.get(i));
    }
    model.put("sportsman", showingList);
    return "sportsmans";
  }

  private String idsportsman = "110604200300";

  @GetMapping("/sportsman")
  public String sportsman(Map < String, Object > model) {

    List<Sportsman> sportsmanStat = sportsmanStats.findAll();
    List<SportsmanRace> sportsmanRaceStat = sportsmanRaceStats.findAll();
    ArrayList<SportsmanRace> sportsmanInRaces = new ArrayList<>();
    int accuracy = 1;
    int layingAccuracy = 1;
    int standingAccuracy = 1;
    int raceNumber = 1;
    for (Sportsman stat : sportsmanStat) {
      if (stat.getId_sportsman().equals(this.idsportsman)) {
        for (SportsmanRace statRace : sportsmanRaceStat) {
          if (Objects.equals(statRace.getIdsportsman(), this.idsportsman)) {
            sportsmanInRaces.add(statRace);
            layingAccuracy += statRace.getLayingaccuracy();
            standingAccuracy += statRace.getStandingaccuracy();
            raceNumber++;
          }
        }
        standingAccuracy = standingAccuracy/raceNumber;
        layingAccuracy = layingAccuracy/raceNumber;
        accuracy = (standingAccuracy + layingAccuracy) / 2;
        stat stat1 = new stat(raceNumber, accuracy, standingAccuracy, layingAccuracy);
        model.put("sportsman", stat);
        model.put("sportsmanStat", stat1);
        break;
      }
    }

    return "sportsman";
  }

  @GetMapping("/main")
  public String main(Map<String, Object> model) {
    List<Institution> institutionStat = institutionStats.findAll();
    ArrayList<Institution> showingList = new ArrayList<>();
    for (int i = start; i < end; i ++){
      showingList.add(institutionStat.get(i));
    }
    model.put("institution", showingList);
    return "main";
  }

  @GetMapping(value = "/institutionsRedirect")
  public String institutionsRedirect() {
    return "redirect:/institutions";
  }

  @GetMapping(value = "/mainRedirect")
  public String mainRedirect() {
    return "redirect:/main";
  }

  @GetMapping(value = "/coachesRedirect")
  public String coachesRedirect() {
    return "redirect:/coaches";
  }

  @GetMapping(value = "/racesRedirect")
  public String racesRedirect() {
    return "redirect:/races";
  }

  @GetMapping(value = "/sportsmansRedirect")
  public String sportsmansRedirect() {
    return "redirect:/sportsmans";
  }

  @GetMapping(value = "/registrationRedirect")
  public String registrationRedirect() {
    return "redirect:/registration";
  }

  @GetMapping(value = "/sighInRedirect")
  public String sighInRedirect() {
    return "redirect:/sighIn";
  }

  @GetMapping(value = "/prevPage")
  public String prevPage() {
    if (start !=0) {
      start -= 20;
      end -=20;
    }
    return "redirect:/sportsmans";
  }

  @GetMapping(value = "/nextPage")
  public String nextPage() {
    if (end < 1940) {
      start += 20;
      end +=20;
    }
    return "redirect:/sportsmans";
  }

  @GetMapping(value = "/prevPageInstitution")
  public String prevPageInstitution() {
    if (startInstitution !=0) {
      startInstitution -= 20;
      endInstitution -=20;
    }
    return "redirect:/institutions";
  }

  @GetMapping(value = "/nextPageInstitution")
  public String nextPageInstitution() {
    startInstitution += 20;
    endInstitution += 20;
    if (endInstitution >= 60) {
      endInstitution = 77;
    }
    return "redirect:/institutions";
  }

  @GetMapping(value = "/sportsmanRedirect")
  public String sportsmanRedirect() {
    return "redirect:/sportsman";
  }
  @PostMapping
  public String goToSportsman(@RequestParam String idsportsman, Map<String, Object> model) {
    this.idsportsman = idsportsman;
    return "redirect:/sportsman";
  }
}
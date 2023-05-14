package biathlonStats;

import biathlonStats.entity.Institution;
import biathlonStats.entity.Region;
import biathlonStats.entity.Sportsman;
import biathlonStats.entity.SportsmanRace;
import biathlonStats.repo.InstitutionStatRepo;
import biathlonStats.repo.RegionStatRepo;
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

  private String idsportsman;
  private int idregion;
  private int idinstitution;

  int start = 0;
  int end = 20;

  int startInstitution = 0;
  int endInstitution = 20;

  int startRegion = 0;
  int endRegion = 20;

  @Autowired
  private SportsmanStatRepo sportsmanStats;

  @Autowired
  private InstitutionStatRepo institutionStats;

  @Autowired
  private SportsmanRaceStatRepo sportsmanRaceStats;

  @Autowired
  private RegionStatRepo regionStats;

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

  @GetMapping("/region")
  public String region(Map < String, Object > model) {
    List<Region> regionStat = regionStats.findAll();
    ArrayList<Sportsman> showinglist = new ArrayList<>();
    for (Region region: regionStat) {
      if (region.getIdregion() == this.idregion) {
        model.put("region", region);
        List<Sportsman> sportsmansList = sportsmanStats.findAll();
        for (Sportsman sportsman: sportsmansList) {
          if (sportsman.getRegion().equals(region.getName())){
            showinglist.add(sportsman);
          }
        }
        model.put("sportsmans", showinglist);
        break;
      }

    }
    return "region";
  }

  @GetMapping("/institution")
  public String institution(Map < String, Object > model) {
    List<Institution> institutionStat = institutionStats.findAll();
    ArrayList<Sportsman> showinglist = new ArrayList<>();
    for (Institution institution: institutionStat) {
      if (institution.getIdinstitution() == this.idinstitution) {
        model.put("institution", institution);
        List<Sportsman> sportsmansList = sportsmanStats.findAll();
        for (Sportsman sportsman: sportsmansList) {
          if (sportsman.getInstitution().equals(institution.getName())){
            showinglist.add(sportsman);
          }
        }
        model.put("sportsmans", showinglist);
        break;
      }

    }
    return "institution";
  }

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
        model.put("race", sportsmanInRaces);
        model.put("sportsman", stat);
        model.put("sportsmanStat", stat1);
        break;
      }
    }

    return "sportsman";
  }

 @GetMapping("/main")
  public String main(Map<String, Object> model) {
    List<Region> regionStat = regionStats.findAll();
    ArrayList<Region> showingList = new ArrayList<>();
    for (int i = startRegion; i < endRegion; i ++){
      showingList.add(regionStat.get(i));
    }
    model.put("region", showingList);
    return "main";
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

  @GetMapping("/regions")
  public String regions(Map<String, Object> model) {
    List<Region> regionStat = regionStats.findAll();
    ArrayList<Region> showingList = new ArrayList<>();
    for (int i = startRegion; i < endRegion; i ++){
      showingList.add(regionStat.get(i));
    }
    model.put("region", showingList);
    return "regions";
  }

  @GetMapping(value = "/institutionsRedirect")
  public String institutionsRedirect() {
    return "redirect:/institutions";
  }

  @GetMapping(value = "/mainRedirect")
  public String mainRedirect() {
    return "redirect:/main";
  }

  @GetMapping(value = "/regionsRedirect")
  public String regionsRedirect() {
    return "redirect:/regions";
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
      startInstitution = 60;
      endInstitution = 78;
    }
    return "redirect:/institutions";
  }

  @GetMapping(value = "/prevPageRegion")
  public String prevPageRegion() {
    if (startRegion !=0) {
      startRegion -= 20;
      endRegion -=20;
    }
    return "redirect:/regions";
  }

  @GetMapping(value = "/nextPageRegion")
  public String nextPageRegion() {
    startRegion += 20;
    endRegion += 20;
    if (endRegion >= 40) {
      endRegion = 55;
    }
    return "redirect:/regions";
  }

  @GetMapping(value = "/sportsmanRedirect")
  public String sportsmanRedirect() {
    return "redirect:/sportsman";
  }

  @GetMapping(value = "/regionRedirect")
  public String regionRedirect() {
    return "redirect:/region";
  }

  @GetMapping(value = "/institutionRedirect")
  public String institutionRedirect() {
    return "redirect:/institution";
  }

  @PostMapping
  public String goToStuff(@RequestParam(name = "idsportsman", required = false) String idsportsman,
                          @RequestParam(name = "idregion", required = false) Integer idregion,
                          @RequestParam(name = "idinstitution", required = false) Integer idinstitution) {
    String goTo = "";
    if (idsportsman != null) {
      this.idsportsman = idsportsman;
      goTo = "redirect:/sportsman";
    } else if (idregion != null) {
      this.idregion = idregion;
      goTo = "redirect:/region";
    } else if (idinstitution != null) {
      this.idinstitution = idinstitution;
      goTo = "redirect:/institution";
    }
    return goTo;
  }
}
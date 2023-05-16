package biathlonStats;

import biathlonStats.entity.*;
import biathlonStats.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
public class MainController {

  public static class stat {
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
  private String idFirstSportsman;
  private String idSecondSportsman;

  private int idregion;
  private int idinstitution;
  private int idcoach;
  private int idrace;

  int start = 0;
  int end = 20;

  int startInstitution = 0;
  int endInstitution = 20;

  int startRegion = 0;
  int endRegion = 20;

  int startCoach = 0;
  int endCoach = 20;

  int startRace = 0;
  int endRace = 11;

  @Autowired
  private SportsmanStatRepo sportsmanStats;

  @Autowired
  private InstitutionStatRepo institutionStats;

  @Autowired
  private SportsmanRaceStatRepo sportsmanRaceStats;

  @Autowired
  private CoachStatRepo coachStats;

  @Autowired
  private RegionStatRepo regionStats;

  @Autowired
  private RaceStatRepo raceStats;

  @GetMapping("/registration")
  public String registration() {
    return "registration";
  }

  @GetMapping("/sighIn")
  public String sighIn() {
    return "sighIn";
  }

    @GetMapping("/comparison")
    public String comparison(Map<String, Object> model) {
        List<Sportsman> sportsmanStat = sportsmanStats.findAll();
        model.put("sportsman", sportsmanStat);
        List<SportsmanRace> sportsmanRaceStat = sportsmanRaceStats.findAll();
        int accuracyFirst = 1;
        int layingAccuracyFirst = 1;
        int standingAccuracyFirst = 1;
        int raceNumberFirst = 0;
        int accuracySecond = 1;
        int layingAccuracySecond = 1;
        int standingAccuracySecond = 1;
        int raceNumberSecond = 0;
        this.idFirstSportsman = "110604200300";
        this.idSecondSportsman = "120912200400";
        for (Sportsman stat : sportsmanStat) {
            if (stat.getId_sportsman().equals(this.idFirstSportsman)) {
                for (SportsmanRace statRace : sportsmanRaceStat) {
                    if (Objects.equals(statRace.getIdsportsman(), this.idFirstSportsman)) {
                        layingAccuracyFirst += statRace.getLayingaccuracy();
                        standingAccuracyFirst += statRace.getStandingaccuracy();
                        raceNumberFirst++;
                    }
                }
                standingAccuracyFirst = standingAccuracyFirst/raceNumberFirst;
                layingAccuracyFirst = layingAccuracyFirst/raceNumberFirst;
                accuracyFirst = (standingAccuracyFirst + layingAccuracyFirst) / 2;
                stat stat1 = new stat(raceNumberFirst, accuracyFirst, standingAccuracyFirst, layingAccuracyFirst);
                model.put("sportsman1", stat);
                model.put("sportsmanStat1", stat1);
            }
            if (stat.getId_sportsman().equals(this.idSecondSportsman)) {
                for (SportsmanRace statRace : sportsmanRaceStat) {
                    if (Objects.equals(statRace.getIdsportsman(), this.idSecondSportsman)) {
                        layingAccuracySecond += statRace.getLayingaccuracy();
                        standingAccuracySecond += statRace.getStandingaccuracy();
                        raceNumberSecond++;
                    }
                }
                standingAccuracySecond = standingAccuracySecond/raceNumberSecond;
                layingAccuracySecond = layingAccuracySecond/raceNumberSecond;
                accuracySecond = (standingAccuracySecond + layingAccuracySecond) / 2;
                stat stat1 = new stat(raceNumberSecond, accuracySecond, standingAccuracySecond, layingAccuracySecond);
                model.put("sportsman2", stat);
                model.put("sportsmanStat2", stat1);
            }
        }
        stat comparison = new stat(1, 1, 1, 1);
        if (accuracyFirst > accuracySecond) {
            comparison.accuracy = accuracyFirst - accuracySecond;
        } else if (accuracyFirst < accuracySecond) {
            comparison.accuracy =  accuracySecond - accuracyFirst;
        } else {
            comparison.accuracy = 0;
        }

        if (layingAccuracyFirst > layingAccuracySecond) {
            comparison.layingAccuracy = layingAccuracyFirst - layingAccuracySecond;
        } else if (layingAccuracyFirst < layingAccuracySecond) {
            comparison.layingAccuracy =  layingAccuracySecond - layingAccuracyFirst;
        } else {
            comparison.layingAccuracy = 0;
        }

        if (standingAccuracyFirst > standingAccuracySecond) {
            comparison.standingAccuracy = standingAccuracyFirst - standingAccuracySecond;
        } else if (layingAccuracyFirst < standingAccuracySecond) {
            comparison.standingAccuracy =  standingAccuracySecond - standingAccuracyFirst;
        } else {
            comparison.standingAccuracy = 0;
        }

        if (raceNumberFirst > raceNumberSecond) {
            comparison.raceNumber = raceNumberFirst - raceNumberSecond;
        } else if (layingAccuracyFirst < raceNumberSecond) {
            comparison.raceNumber =  raceNumberSecond - raceNumberFirst;
        } else {
            comparison.raceNumber = 0;
        }
        model.put("comparison", comparison);
        return "comparison";
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
  public String coaches(Map<String, Object> model) {
      List<Coach> coachStat = coachStats.findAll();
      ArrayList<Coach> showingList = new ArrayList<>();
      for (int i = startCoach; i < endCoach; i ++){
          showingList.add(coachStat.get(i));
      }
      model.put("coach", showingList);
    return "coaches";
  }

    @GetMapping("/races")
    public String races(Map<String, Object> model) {
        List<Race> raceStat = raceStats.findAll();
        ArrayList<Race> showingList = new ArrayList<>();
        for (int i = startRace; i < endRace; i ++){
            showingList.add(raceStat.get(i));
        }
        model.put("race", showingList);
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

    @GetMapping("/coach")
    public String coach(Map < String, Object > model) {
        List<Coach> coachStat = coachStats.findAll();
        ArrayList<Coach> showinglist = new ArrayList<>();
        for (Coach coach: coachStat) {
            if (coach.getIdcoach() == this.idcoach) {
                model.put("coach", coach);
                /*List<Sportsman> sportsmansList = sportsmanStats.findAll();
                for (Sportsman sportsman: sportsmansList) {
                    if (sportsman.getRegion().equals(region.getName())){
                        showinglist.add(sportsman);
                    }
                }
                model.put("sportsmans", showinglist);*/
                break;
            }
        }
        return "coach";
    }

    @GetMapping("/race")
    public String race(Map < String, Object > model) {
        List<Race> races = raceStats.findAll();
        for (Race race: races) {
            if (race.getIdrace() == this.idrace) {
                model.put("race", race);
                break;
            }
        }

        List<SportsmanRace> sportsmanRaceStat = sportsmanRaceStats.findAll();
        ArrayList<SportsmanRace> showinglist = new ArrayList<>();

        for (SportsmanRace sportsmanRace : sportsmanRaceStat) {
            if (sportsmanRace.getIdrace() == this.idrace) {
                showinglist.add(sportsmanRace);
            }
        }
        model.put("sportsmanrace", showinglist);
        return "race";
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

    @GetMapping(value = "/comparisonRedirect")
    public String comparisonRedirect() {
        return "redirect:/comparison";
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

  @GetMapping(value = "/prevPageCoach")
  public String prevPageCoach() {
      if (startCoach !=0) {
          startCoach -= 20;
          endCoach -=20;
      }
      return "redirect:/coaches";
  }

  @GetMapping(value = "/nextPageCoach")
  public String nextPageCoach() {
      startCoach += 20;
      endCoach += 20;
      if (endCoach >= 100) {
          endCoach = 100;
      }
      return "redirect:/coaches";
  }

  @GetMapping(value = "/prevPageRace")
  public String prevPageRace() {
      if (startRace !=0) {
          startRace -= 20;
          endRace -=20;
      }
      return "redirect:/races";
  }

  @GetMapping(value = "/nextPageRace")
  public String nextPageRace() {
      startRace += 20;
      endRace += 20;
      if (endRace >= raceStats.count()) {
          endRace = (int) raceStats.count();
      }
      return "redirect:/races";
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
                          @RequestParam(name = "idsportsman1", required = false) String idsportsman1,
                          @RequestParam(name = "idsportsman2", required = false) String idsportsman2,
                          @RequestParam(name = "idregion", required = false) Integer idregion,
                          @RequestParam(name = "idinstitution", required = false) Integer idinstitution,
                          @RequestParam(name = "idcoach", required = false) Integer idcoach,
                          @RequestParam(name = "idrace", required = false) Integer idrace) {
    String goTo = "";
    if (idsportsman != null) {
      this.idsportsman = idsportsman;
      goTo = "redirect:/sportsman";
    } else if (idsportsman1 != null) {
      this.idFirstSportsman = idsportsman1;
      goTo = "redirect:/comparison";
    } else if (idsportsman2 != null) {
      this.idSecondSportsman = idsportsman2;
      goTo = "redirect:/comparison";
    } else if (idregion != null) {
      this.idregion = idregion;
      goTo = "redirect:/region";
    } else if (idinstitution != null) {
      this.idinstitution = idinstitution;
      goTo = "redirect:/institution";
    } else if (idcoach != null) {
      this.idcoach = idcoach;
      goTo = "redirect:/coach";
    } else if (idrace != null) {
      this.idrace = idrace;
      goTo = "redirect:/race";
    }
    return goTo;
  }
}
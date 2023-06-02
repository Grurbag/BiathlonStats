package biathlonStats;

import biathlonStats.entity.*;
import biathlonStats.repo.*;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class MainController {

    public class Stat {
        private String idsportsman;
        private int raceNumber;
        private int accuracy;
        private int standingAccuracy;
        private int layingAccuracy;

        public Stat(String idsportsman, int raceNumber, int accuracy, int standingAccuracy, int layingAccuracy) {
            this.idsportsman = idsportsman;
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

        public String getIdsportsman() {
            return idsportsman;
        }

        public void setIdsportsman(String idsportsman) {
            this.idsportsman = idsportsman;
        }
    }

    public class ComparisonStat {
        String accuracy;
        String standingAccuracy;
        String layingAccuracy;
        String raceNumber;

        public ComparisonStat(String accuracy, String standingAccuracy,
                              String layingAccuracy, String raceNumber) {
            this.accuracy = accuracy;
            this.standingAccuracy = standingAccuracy;
            this.layingAccuracy = layingAccuracy;
            this.raceNumber = raceNumber;
        }

        public String getAccuracy() {
            return accuracy;
        }

        public void setAccuracy(String accuracy) {
            this.accuracy = accuracy;
        }

        public String getStandingAccuracy() {
            return standingAccuracy;
        }

        public void setStandingAccuracy(String standingAccuracy) {
            this.standingAccuracy = standingAccuracy;
        }

        public String getLayingAccuracy() {
            return layingAccuracy;
        }

        public void setLayingAccuracy(String layingAccuracy) {
            this.layingAccuracy = layingAccuracy;
        }

        public String getRaceNumber() {
            return raceNumber;
        }

        public void setRaceNumber(String raceNumber) {
            this.raceNumber = raceNumber;
        }
    }

    /*private static void order(List<Stat> stats) {
        Comparator<Stat> comparator = Comparator.comparing(Stat::getAccuracy).thenComparingInt(Stat::getLayingAccuracy).thenComparingInt(Stat::getStandingAccuracy).thenComparingInt(Stat::getRaceNumber);
        Collections.sort(stats, comparator);
    }*/

    private ArrayList<Sportsman> addedSportsmans = new ArrayList<>();
    private String idsportsman;
    private String idFirstSportsman;
    private String idSecondSportsman;
    private String idAddedSportsman;
    private String idRemovedSportsman;

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

    @Autowired private SportsmanStatRepo sportsmanStats;

    @Autowired private InstitutionStatRepo institutionStats;

    @Autowired private SportsmanRaceStatRepo sportsmanRaceStats;

    @Autowired private SportsmanCoachStatRepo sportsmanCoachStats;

    @Autowired private CoachStatRepo coachStats;

    @Autowired private RegionStatRepo regionStats;

    @Autowired private RaceStatRepo raceStats;

   @GetMapping("/")
   public ModelAndView greeting() {
       Map<String, Object> model = new HashMap<>();
       return new ModelAndView("redirect:/main", model);
   }

    @GetMapping("/predictResult")
    public ModelAndView predictResult() {
        Map<String, Object> model = new HashMap<>();
        List<SportsmanRace> sportsmanRaceStat = sportsmanRaceStats.findAll();
        ArrayList<SportsmanRace> sportsmanRaceIds = new ArrayList<>();
        for (SportsmanRace sportsmanRace : sportsmanRaceStat) {
            for (Sportsman sportsman : addedSportsmans) {
                if (sportsman.getId_sportsman().equals(sportsmanRace.getIdsportsman())) {
                    sportsmanRaceIds.add(sportsmanRace);
                }
            }
        }
        ArrayList<Stat> addedSportsmansStats = new ArrayList<>();
        for (Sportsman stat : addedSportsmans) {
            int accuracy = 1;
            int layingAccuracy = 1;
            int standingAccuracy = 1;
            int raceNumber = 1;
            for (SportsmanRace statRace : sportsmanRaceIds) {
                if (stat.getId_sportsman().equals(statRace.getIdsportsman())) {
                    layingAccuracy += statRace.getLayingaccuracy();
                    standingAccuracy += statRace.getStandingaccuracy();
                    raceNumber++;
                }
            }
            standingAccuracy = standingAccuracy / raceNumber;
            layingAccuracy = layingAccuracy / raceNumber;
            accuracy = (standingAccuracy + layingAccuracy) / 2;
            raceNumber--;
            Stat stat1 = new Stat(stat.getId_sportsman(), raceNumber, accuracy, standingAccuracy, layingAccuracy);
            addedSportsmansStats.add(stat1);
        }
        boolean sorted = false;
        Stat temp;
        while(!sorted) {
            sorted = true;
            for (int i = 0; i < addedSportsmansStats.size() - 1; i++) {
                if (addedSportsmansStats.get(i).getAccuracy() < addedSportsmansStats.get(i+1).getAccuracy()) {
                    temp = addedSportsmansStats.get(i + 1);
                    addedSportsmansStats.set(i+1,addedSportsmansStats.get(i));
                    addedSportsmansStats.set(i, temp);
                    sorted = false;
                }
            }
        }
        //order(addedSportsmansStats);
        ArrayList<Sportsman> sortedSportsmans = new ArrayList<>();
        for (Stat addedSportsmansStat : addedSportsmansStats) {
            for (Sportsman addedSportsman : this.addedSportsmans) {
                if (addedSportsmansStat.getIdsportsman().equals(addedSportsman.getId_sportsman())) {
                    sortedSportsmans.add(addedSportsman);
                }
            }
        }
        this.addedSportsmans = sortedSportsmans;
        model.put("sportsman", this.addedSportsmans);
        List<Sportsman> sportsmanStat = sportsmanStats.findAll();
        model.put("sportsmanadd", sportsmanStat);
        return new ModelAndView("resultRace", model);
    }

    @GetMapping("/resultRace")
    public ModelAndView resultRace() {
        Map<String, Object> model = new HashMap<>();
        List<Sportsman> sportsmanStat = sportsmanStats.findAll();
        model.put("sportsmanadd", sportsmanStat);
        model.put("sportsman", this.addedSportsmans);
        return new ModelAndView("resultRace", model);
    }

    @GetMapping("/comparison")
    public ModelAndView comparison() {
        Map<String, Object> model = new HashMap<>();
        List<Sportsman> sportsmanStat = sportsmanStats.findAll();
        model.put("sportsman", sportsmanStat);
        List<SportsmanRace> sportsmanRaceStat = sportsmanRaceStats.findAll();
        int accuracyFirst = 1;
        int layingAccuracyFirst = 1;
        int standingAccuracyFirst = 1;
        int raceNumberFirst = 1;
        int accuracySecond = 1;
        int layingAccuracySecond = 1;
        int standingAccuracySecond = 1;
        int raceNumberSecond = 1;

        for (Sportsman stat : sportsmanStat) {
            if (stat.getId_sportsman().equals(this.idFirstSportsman)) {
                for (SportsmanRace statRace : sportsmanRaceStat) {
                    if (Objects.equals(
                            statRace.getIdsportsman(), this.idFirstSportsman)) {
                        layingAccuracyFirst += statRace.getLayingaccuracy();
                        standingAccuracyFirst += statRace.getStandingaccuracy();
                        raceNumberFirst++;
                    }
                }
                standingAccuracyFirst = standingAccuracyFirst / raceNumberFirst;
                layingAccuracyFirst = layingAccuracyFirst / raceNumberFirst;
                accuracyFirst = (standingAccuracyFirst + layingAccuracyFirst) / 2;
                raceNumberFirst--;
                Stat stat1 = new Stat(stat.getId_sportsman(),raceNumberFirst, accuracyFirst, standingAccuracyFirst, layingAccuracyFirst);
                model.put("sportsman1", stat);
                model.put("sportsmanStat1", stat1);
            }
            if (stat.getId_sportsman().equals(this.idSecondSportsman)) {
                for (SportsmanRace statRace : sportsmanRaceStat) {
                    if (Objects.equals(statRace.getIdsportsman(),
                            this.idSecondSportsman)) {
                        layingAccuracySecond += statRace.getLayingaccuracy();
                        standingAccuracySecond +=
                                statRace.getStandingaccuracy();
                        raceNumberSecond++;
                    }
                }
                standingAccuracySecond = standingAccuracySecond / raceNumberSecond;
                layingAccuracySecond = layingAccuracySecond / raceNumberSecond;
                accuracySecond = (standingAccuracySecond + layingAccuracySecond) / 2;
                raceNumberSecond--;
                Stat stat1 = new Stat(stat.getId_sportsman(),raceNumberSecond, accuracySecond, standingAccuracySecond, layingAccuracySecond);
                model.put("sportsman2", stat);
                model.put("sportsmanStat2", stat1);
            }
        }

        ComparisonStat comparisonStat = new ComparisonStat("", "", "", "");
        Stat comparison = new Stat("100000",1, 1, 1, 1);
        if (accuracyFirst > accuracySecond) {
            comparisonStat.accuracy =
                    "<" + (accuracyFirst - accuracySecond) + "%";
        } else if (accuracyFirst < accuracySecond) {
            comparisonStat.accuracy =
                     (accuracySecond - accuracyFirst) + "%" + ">";
        } else {
            comparisonStat.accuracy = "=";
        }

        if (layingAccuracyFirst > layingAccuracySecond) {
            comparisonStat.layingAccuracy =
                    "<" + (layingAccuracyFirst - layingAccuracySecond) + "%";
        } else if (layingAccuracyFirst < layingAccuracySecond) {
            comparisonStat.layingAccuracy =
                    (layingAccuracySecond - layingAccuracyFirst)+ "%" + ">";
        } else {
            comparisonStat.layingAccuracy = "=";
        }

        if (standingAccuracyFirst > standingAccuracySecond) {
            comparisonStat.standingAccuracy =
                    "<" + (standingAccuracyFirst - standingAccuracySecond) + "%";
        } else if (layingAccuracyFirst < standingAccuracySecond) {
            comparisonStat.standingAccuracy =
                     (standingAccuracySecond - standingAccuracyFirst) + "%" + ">";
        } else {
            comparisonStat.standingAccuracy = "=";
        }

        if (raceNumberFirst > raceNumberSecond) {
            comparisonStat.raceNumber =
                    "<" + (raceNumberFirst - raceNumberSecond);
        } else if (raceNumberFirst < raceNumberSecond) {
            comparisonStat.raceNumber =
                    (raceNumberSecond - raceNumberFirst) + ">";
        } else {
            comparisonStat.raceNumber = "=";
        }
        model.put("comparison", comparisonStat);
        return new ModelAndView("comparison", model);
    }

    @GetMapping("/greeting")
    public ModelAndView greeting(@RequestParam(name = "name", required = false,
            defaultValue = "World") String name) {
        Map<String, Object> model = new HashMap<>();
        model.put("name", name);
        return new ModelAndView("greeting", model);
    }

    @GetMapping("/institutions")
    public ModelAndView institutions() {
        Map<String, Object> model = new HashMap<>();
        List<Institution> institutionStat = institutionStats.findAll();
        ArrayList<Institution> showingList = new ArrayList<>();
        for (int i = startInstitution; i < endInstitution; i++) {
            showingList.add(institutionStat.get(i));
        }
        model.put("institution", showingList);
        return new ModelAndView("institutions", model);
    }

    @GetMapping("/coaches")
    public ModelAndView coaches() {
        Map<String, Object> model = new HashMap<>();
        List<Coach> coachStat = coachStats.findAll();
        ArrayList<Coach> showingList = new ArrayList<>();
        for (int i = startCoach; i < endCoach; i++) {
            showingList.add(coachStat.get(i));
        }
        model.put("coach", showingList);
        return new ModelAndView("coaches", model);
    }

    @GetMapping("/races")
    public ModelAndView races() {
        List<Race> raceStat = raceStats.findAll();
        Map<String, Object> model = new HashMap<>();
        ArrayList<Race> showingList = new ArrayList<>();
        for (int i = startRace; i < endRace; i++) {
            showingList.add(raceStat.get(i));
        }
        model.put("race", showingList);
        return new ModelAndView("races", model);
    }

    @PostMapping("/goToRegion")
    public ModelAndView region(@RequestParam(name = "idregion") Integer idregion) {
        Map<String, Object> model = new HashMap<>();
        List<Region> regionStat = regionStats.findAll();
        ArrayList<Sportsman> showinglist = new ArrayList<>();
        for (Region region : regionStat) {
            if (region.getIdregion() == idregion) {
                model.put("region", region);
                List<Sportsman> sportsmansList = sportsmanStats.findAll();
                for (Sportsman sportsman : sportsmansList) {
                    if (sportsman.getRegion().equals(region.getName())) {
                        showinglist.add(sportsman);
                    }
                }
                model.put("sportsmans", showinglist);
                break;
            }
        }
        return new ModelAndView("region", model);
    }

    @PostMapping("/goToCoach")
    public ModelAndView coach(@RequestParam(name = "idcoach") Integer idcoach) {
        Map<String, Object> model = new HashMap<>();
        List<Coach> coaches = coachStats.findAll();
        ArrayList<Coach> showinglist = new ArrayList<>();
        for (Coach coach : coaches) {
            if (coach.getIdcoach() == idcoach) {
                model.put("coach", coach);
                List<Sportsman> sportsmansList = sportsmanStats.findAll();
                model.put("sportsmans", showinglist);
                break;
            }
        }
        List<Sportsman> sportsmen = sportsmanStats.findAll();
        List<SportsmanCoach> coachStat = sportsmanCoachStats.findAll();
        for(SportsmanCoach sportsmanCoach: coachStat) {
            if (idcoach == sportsmanCoach.getIdCoach()) {
                for (Sportsman sportsman: sportsmen) {
                    if (sportsmanCoach.getIdsportsman().equals(sportsman.getId_sportsman())) {
                        model.put("sportsman", sportsman);
                    }
                }
            }
        }
        return new ModelAndView("coach", model);
    }

    @PostMapping("/goToRace")
    public ModelAndView race(@RequestParam(name = "idrace") Integer idrace) {
        Map<String, Object> model = new HashMap<>();
        List<Race> races = raceStats.findAll();
        for (Race race : races) {
            if (race.getIdrace() == idrace) {
                model.put("race", race);
                break;
            }
        }

        List<SportsmanRace> sportsmanRaceStat = sportsmanRaceStats.findAll();
        ArrayList<SportsmanRace> showinglist = new ArrayList<>();

        for (SportsmanRace sportsmanRace : sportsmanRaceStat) {
            if (sportsmanRace.getIdrace() == idrace) {
                showinglist.add(sportsmanRace);
            }
        }
        model.put("sportsmanrace", showinglist);
        return new ModelAndView("race", model);
    }

    @PostMapping("/goToInstitution")
    public ModelAndView institution(@RequestParam(name = "idinstitution") Integer idinstitution) {
        Map<String, Object> model = new HashMap<>();
        List<Institution> institutionStat = institutionStats.findAll();
        ArrayList<Sportsman> showinglist = new ArrayList<>();
        for (Institution institution : institutionStat) {
            if (institution.getIdinstitution() == idinstitution) {
                model.put("institution", institution);
                List<Sportsman> sportsmansList = sportsmanStats.findAll();
                for (Sportsman sportsman : sportsmansList) {
                    if (sportsman.getInstitution().equals(
                            institution.getName())) {
                        showinglist.add(sportsman);
                    }
                }
                model.put("sportsmans", showinglist);
                break;
            }
        }
        return new ModelAndView("institution", model);
    }

    @PostMapping("/goToSportsman")
    public ModelAndView sportsman(@RequestParam(name = "idsportsman") String idSportsman) {
        Map<String, Object> model = new HashMap<>();
        List<SportsmanCoach> coachStat = sportsmanCoachStats.findAll();
        List<Coach> coaches = coachStats.findAll();
        for(SportsmanCoach sportsmanCoach: coachStat) {
            if (idSportsman.equals(sportsmanCoach.getIdsportsman())) {
                for (Coach coach: coaches) {
                    if (sportsmanCoach.getIdCoach() == coach.getIdcoach()) {
                        model.put("coach", coach);
                    }
                }
            }
        }
        List<Sportsman> sportsmanStat = sportsmanStats.findAll();
        List<SportsmanRace> sportsmanRaceStat = sportsmanRaceStats.findAll();
        ArrayList<SportsmanRace> sportsmanInRaces = new ArrayList<>();
        int accuracy = 1;
        int layingAccuracy = 1;
        int standingAccuracy = 1;
        int raceNumber = 1;
        for (Sportsman stat : sportsmanStat) {
            if (stat.getId_sportsman().equals(idSportsman)) {
                for (SportsmanRace statRace : sportsmanRaceStat) {
                    if (Objects.equals(statRace.getIdsportsman(), idSportsman)) {
                        sportsmanInRaces.add(statRace);
                        layingAccuracy += statRace.getLayingaccuracy();
                        standingAccuracy += statRace.getStandingaccuracy();
                        raceNumber++;
                    }
                }
                standingAccuracy = standingAccuracy / raceNumber;
                layingAccuracy = layingAccuracy / raceNumber;
                accuracy = (standingAccuracy + layingAccuracy) / 2;
                raceNumber--;
                Stat stat1 = new Stat(stat.getId_sportsman(), raceNumber, accuracy, standingAccuracy, layingAccuracy);
                model.put("race", sportsmanInRaces);
                model.put("sportsman", stat);
                model.put("sportsmanStat", stat1);
                break;
            }
        }
        return new ModelAndView("sportsman", model);
    }

    @GetMapping("/main")
    public ModelAndView main() {
        Map<String, Object> model = new HashMap<>();
        List<Region> regionStat = regionStats.findAll();
        ArrayList<Region> showingList = new ArrayList<>();
        for (int i = startRegion; i < endRegion; i++) {
            showingList.add(regionStat.get(i));
        }
        model.put("region", showingList);
        return new ModelAndView("main", model);
    }

    @GetMapping("/sportsmans")
    public ModelAndView sportsmans(@RequestParam(required = false) Integer start, @RequestParam(required = false) Integer end) {
       if (start == null) {
           start = 0;
       }
        if (end == null) {
            end = 20;
        }
        Map<String, Object> model = new HashMap<>();
        List<Sportsman> sportsmanStat = sportsmanStats.findAll();
        ArrayList<Sportsman> showingList = new ArrayList<>();
        for (int i = start; i < end; i++) {
            showingList.add(sportsmanStat.get(i));
        }
        model.put("sportsman", showingList);
        return new ModelAndView( "sportsmans", model);
    }

    @GetMapping("/regions")
    public ModelAndView regions() {
        Map<String, Object> model = new HashMap<>();
        List<Region> regionStat = regionStats.findAll();
        ArrayList<Region> showingList = new ArrayList<>();
        for (int i = startRegion; i < endRegion; i++) {
            showingList.add(regionStat.get(i));
        }
        model.put("region", showingList);
        return new ModelAndView("regions", model);
    }

    @GetMapping(value = "/institutionsRedirect")
    public ModelAndView institutionsRedirect() {
        Map<String, Object> model = new HashMap<>();
        return new ModelAndView("redirect:/institutions", model);
    }

    @GetMapping(value = "/mainRedirect")
    public ModelAndView mainRedirect() {
        Map<String, Object> model = new HashMap<>();
        return new ModelAndView("redirect:/main", model);
    }

    @GetMapping(value = "/regionsRedirect")
    public ModelAndView regionsRedirect() {
        Map<String, Object> model = new HashMap<>();
        return new ModelAndView("redirect:/regions", model);
    }
    @GetMapping(value = "/coachesRedirect")
    public ModelAndView coachesRedirect() {
        Map<String, Object> model = new HashMap<>();
        return new ModelAndView("redirect:/coaches", model);
    }

    @GetMapping(value = "/racesRedirect")
    public ModelAndView racesRedirect() {
        Map<String, Object> model = new HashMap<>();
        return new ModelAndView ("redirect:/races", model);
    }

    @GetMapping(value = "/sportsmansRedirect")
    public ModelAndView sportsmansRedirect() {
        Map<String, Object> model = new HashMap<>();
        return new ModelAndView ("redirect:/sportsmans", model);
    }

    @GetMapping(value = "/comparisonRedirect")
    public ModelAndView comparisonRedirect() {
        Map<String, Object> model = new HashMap<>();
        return new ModelAndView ("redirect:/comparison", model);
    }

    @PostMapping(value = "/prevPage")
    public ModelAndView prevPage() {
        Map<String, Object> model = new HashMap<>();
        int start = 0;
        int end = 20;
        if (start != 0) {
            start -= 20;
            end -= 20;
        }
        return new ModelAndView ("redirect:/sportsmans", model);
    }

    @GetMapping(value = "/nextPage")
    public String nextPage() {
        if (end < 1940) {
            start += 20;
            end += 20;
        }
        return "redirect:/sportsmans";
    }

    @GetMapping(value = "/prevPageInstitution")
    public String prevPageInstitution() {
        if (startInstitution != 0) {
            startInstitution -= 20;
            endInstitution -= 20;
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
        if (startRegion != 0) {
            startRegion -= 20;
            endRegion -= 20;
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
        if (startCoach != 0) {
            startCoach -= 20;
            endCoach -= 20;
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
        if (startRace != 0) {
            startRace -= 20;
            endRace -= 20;
        }
        return "redirect:/races";
    }

    @GetMapping(value = "/nextPageRace")
    public String nextPageRace() {
        startRace += 20;
        endRace += 20;
        return "redirect:/races";
    }

    @GetMapping(value = "/sportsmanRedirect")
    public ModelAndView sportsmanRedirect() {
        Map<String, Object> model = new HashMap<>();
        return new ModelAndView ("redirect:/sportsman", model);
    }

    @GetMapping(value = "/regionRedirect")
    public ModelAndView regionRedirect() {
        Map<String, Object> model = new HashMap<>();
        return new ModelAndView ("redirect:/region", model);
    }

    @GetMapping(value = "/institutionRedirect")
    public ModelAndView institutionRedirect() {
        Map<String, Object> model = new HashMap<>();
        return new ModelAndView ("redirect:/institution", model);
    }

    @GetMapping(value = "/resultRaceRedirect")
    public ModelAndView resultRaceRedirect() {
        Map<String, Object> model = new HashMap<>();
        return new ModelAndView ("redirect:/resultRace", model);
    }

    @PostMapping("/sort")
    public ModelAndView ModelAndView(@RequestParam(name = "sort") String sort) {
        Map<String, Object> model = new HashMap<>();
        if (sort.equals("Я-А")){
            List<Sportsman> sportsmanStat = sportsmanStats.findAll(Sort.by(Sort.Direction.DESC, "name"));
            model.put("sportsmans", sportsmanStat);
        } else {
            List<Sportsman> sportsmanStat = sportsmanStats.findAll();
            model.put("sportsmans", sportsmanStat);
        }
        return new ModelAndView ("redirect:/sportmans", model);
    }

    @PostMapping("/addSportsman")
    public ModelAndView addSportsman(@RequestParam(name = "idaddedsportsman") String idAddedSportsman) {
        Map<String, Object> model = new HashMap<>();
        this.idAddedSportsman = idAddedSportsman;
        List<Sportsman> sportsmanStat = sportsmanStats.findAll();
        for (Sportsman sportsman : sportsmanStat) {
            if (sportsman.getId_sportsman().equals(this.idAddedSportsman)) {
                this.addedSportsmans.add(sportsman);
            }
        }
        return new ModelAndView ("redirect:/resultRace", model);
    }

    @PostMapping("/deleteSportsman")
    public String deleteSportsman(@RequestParam(name = "idremovedsportsman") String idRemovedSportsman, Map<String, Object> model) {
        int indexRemoved = 0;
        for (int i = 0; i < this.addedSportsmans.size(); i++) {
            if (this.addedSportsmans.get(i).getId_sportsman().equals(idRemovedSportsman)) {
                indexRemoved = i;
                break;
            }
        }
        this.addedSportsmans.remove(indexRemoved);
        model.put("sportsman", addedSportsmans);
        return "redirect:/resultRace";
    }
}
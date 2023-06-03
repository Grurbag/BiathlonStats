package biathlonStats.controller;

import biathlonStats.entity.*;
import biathlonStats.repo.*;

import java.io.UnsupportedEncodingException;
import java.util.*;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class MainController {

    public static class Stat {
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

    private ArrayList<Sportsman> addedSportsmans = new ArrayList<>();

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

    @PostMapping("/compare")
    public ModelAndView comparison(@RequestParam(name = "idfirstsportsman", required = false) String idfirstsportsman,
                                   @RequestParam(name = "idsecondsportsman", required = false) String idsecondsportsman) {
        Map<String, Object> model = new HashMap<>();
        List<Sportsman> sportsmanStat = sportsmanStats.findAll();

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
            if (stat.getId_sportsman().equals(idfirstsportsman)) {
                for (SportsmanRace statRace : sportsmanRaceStat) {
                    if (Objects.equals(
                            statRace.getIdsportsman(),idfirstsportsman)) {
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
            if (stat.getId_sportsman().equals(idsecondsportsman)) {
                for (SportsmanRace statRace : sportsmanRaceStat) {
                    if (Objects.equals(statRace.getIdsportsman(),
                            idsecondsportsman)) {
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
        model.put("sportsman", sportsmanStat);
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


    @GetMapping("/sportsmans")
    public ModelAndView sportsmans(@RequestParam(name = "start", required = false) Integer start, @RequestParam(name = "end", required = false)  Integer end) {
       if (start == null) {
           start = 0;
       }
        if (end == null) {
            end = 15;
        }
        Map<String, Object> model = new HashMap<>();
        List<Sportsman> sportsmanStat = sportsmanStats.findAll();
        ArrayList<Sportsman> showingList = new ArrayList<>();
        for (int i = start; i < end; i++) {
            showingList.add(sportsmanStat.get(i));
        }
        model.put("start", start);
        model.put("end", end);

        model.put("sportsman", showingList);
        return new ModelAndView( "sportsmans", model);
    }

    @GetMapping(value = "/mainRedirect")
    public ModelAndView mainRedirect() {
        Map<String, Object> model = new HashMap<>();
        return new ModelAndView("redirect:/main", model);
    }

    @GetMapping(value = "/sportsmansRedirect")
    public ModelAndView sportsmansRedirect() {
        Map<String, Object> model = new HashMap<>();
        return new ModelAndView ("redirect:/sportsmans", model);
    }

    @GetMapping(value = "/comparisonRedirect")
    public ModelAndView comparisonRedirect() {
        Map<String, Object> model = new HashMap<>();
        List<Sportsman> sportsmen = sportsmanStats.findAll();
        model.put("sportsman", sportsmen);
        return new ModelAndView ("comparison", model);
    }

    @PostMapping(value = "/prevPage")
    public ModelAndView prevPage(HttpServletRequest request) {
        Map<String, Object> model = new HashMap<>();
        int start = Integer.parseInt(request.getParameter("start"));
        int end = Integer.parseInt(request.getParameter("end"));
        if (request.getParameter("start") != null) {
            start -= 20;
        }
        if (request.getParameter("start") != null) {
            end -= 20;
        }
        return this.sportsmans(start, end);
    }

    @PostMapping(value = "/nextPage")
    public ModelAndView nextPage(HttpServletRequest request) {
        Map<String, Object> model = new HashMap<>();
        int start = Integer.parseInt(request.getParameter("start"));
        int end = Integer.parseInt(request.getParameter("end"));
        if (request.getParameter("start") != null) {
            start += 15;
        }
        if (start <= 0){
            start = 0;
        }
        if (request.getParameter("start") != null) {
            end += 15;
        }
        return this.sportsmans(start, end);
    }

    @GetMapping(value = "/sportsmanRedirect")
    public ModelAndView sportsmanRedirect() {
        Map<String, Object> model = new HashMap<>();
        return new ModelAndView ("redirect:/sportsman", model);
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
    public ModelAndView addSportsman(@RequestParam(name = "idaddedsportsman") String idAddedSportsman, @RequestParam(name = "sportsman", required = false) String sportsmen) throws UnsupportedEncodingException {
       Map<String, Object> model = new HashMap<>();
       String encodedWithISO88591 = sportsmen;
       sportsmen = new String(encodedWithISO88591.getBytes("ISO-8859-1"), "UTF-8");
       ArrayList<Sportsman> addedSportsmans = new ArrayList<>();
       if (!sportsmen.equals("[]")) {
           String[] sportsmans = sportsmen.split(", ");
           for (String sportsman: sportsmans) {
               addedSportsmans.add(new Sportsman(sportsman));
           }
       }

       List<Sportsman> sportsmanStat = sportsmanStats.findAll();
       for (Sportsman sportsman : sportsmanStat) {
           if (sportsman.getId_sportsman().equals(idAddedSportsman)) {
               addedSportsmans.add(sportsman);
               break;
           }
       }
       model.put("sportsmanadd", sportsmanStat);
       model.put("sportsman", addedSportsmans);
       return new ModelAndView ("resultRace", model);
    }

    @PostMapping("/deleteSportsman")
    public ModelAndView deleteSportsman(@RequestParam(name = "idremovedsportsman") String idRemovedSportsman,@RequestParam(name = "sportsman", required = false) String sportsmen) throws UnsupportedEncodingException {
        Map<String, Object> model = new HashMap<>();
        String encodedWithISO88591 = sportsmen;
        sportsmen = new String(encodedWithISO88591.getBytes("ISO-8859-1"), "UTF-8");
        ArrayList<Sportsman> addedSportsmans = new ArrayList<>();
        if (!sportsmen.equals("[]")) {
            String[] sportsmans = sportsmen.split(", ");
            for (String sportsman: sportsmans) {
                addedSportsmans.add(new Sportsman(sportsman));
            }
        }
        int indexRemoved = 0;
        for (int i = 0; i < addedSportsmans.size(); i++) {
            if (addedSportsmans.get(i).getId_sportsman().equals(idRemovedSportsman)) {
                indexRemoved = i;
                break;
            }
        }
        List<Sportsman> sportsmanStat = sportsmanStats.findAll();
        addedSportsmans.remove(indexRemoved);
        model.put("sportsmanadd", sportsmanStat);
        model.put("sportsman", addedSportsmans);
        return new ModelAndView ("resultRace", model);
    }
}
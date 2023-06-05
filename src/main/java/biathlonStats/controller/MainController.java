package biathlonStats.controller;

import biathlonStats.entity.*;
import biathlonStats.repo.*;

import java.io.UnsupportedEncodingException;
import java.util.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class MainController {

    public static class Stat implements Comparable<Stat> {
        private long idsportsman;
        private int raceNumber;
        private int accuracy;
        private int standingAccuracy;
        private int layingAccuracy;
        private int placeNumber;
        private int firstPlaceNumber;
        private int secondPlaceNumber;
        private int thirdPlaceNumber;

        public Stat(long idsportsman, int raceNumber, int accuracy, int standingAccuracy, int layingAccuracy, int placeNumber, int firstPlaceNumber, int secondPlaceNumber, int thirdPlaceNumber) {
            this.idsportsman = idsportsman;
            this.raceNumber = raceNumber;
            this.accuracy = accuracy;
            this.standingAccuracy = standingAccuracy;
            this.layingAccuracy = layingAccuracy;
            this.placeNumber = placeNumber;
            this.firstPlaceNumber = firstPlaceNumber;
            this.secondPlaceNumber = secondPlaceNumber;
            this.thirdPlaceNumber = thirdPlaceNumber;
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

        public long getIdsportsman() {
            return idsportsman;
        }

        public void setIdsportsman(long idsportsman) {
            this.idsportsman = idsportsman;
        }

        public int getPlaceNumber() {
            return placeNumber;
        }

        public void setPlaceNumber(int placeNumber) {
            this.placeNumber = placeNumber;
        }

        public int getFirstPlaceNumber() {
            return firstPlaceNumber;
        }

        public void setFirstPlaceNumber(int firstPlaceNumber) {
            this.firstPlaceNumber = firstPlaceNumber;
        }

        public int getSecondPlaceNumber() {
            return secondPlaceNumber;
        }

        public void setSecondPlaceNumber(int secondPlaceNumber) {
            this.secondPlaceNumber = secondPlaceNumber;
        }

        public int getThirdPlaceNumber() {
            return thirdPlaceNumber;
        }

        public void setThirdPlaceNumber(int thirdPlaceNumber) {
            this.thirdPlaceNumber = thirdPlaceNumber;
        }

        @Override
        public int compareTo(Stat o) {
            int cmp = Integer.compare(this.getAccuracy(), (o.getAccuracy()));
            if (cmp != 0) {
                return cmp;
            }

            cmp = Integer.compare(this.getFirstPlaceNumber(), (o.getFirstPlaceNumber()));
            if (cmp != 0) {
                return cmp;
            }

            cmp = Integer.compare(this.getLayingAccuracy(), (o.getLayingAccuracy()));
            if (cmp != 0) {
                return cmp;
            }

            cmp = Integer.compare(this.getStandingAccuracy(), (o.getStandingAccuracy()));
            if (cmp != 0) {
                return cmp;
            }
            return Integer.compare(this.getLayingAccuracy(), o.getLayingAccuracy());
        }
    }

    public class ComparisonStat {
        String accuracy;
        String standingAccuracy;
        String layingAccuracy;
        String raceNumber;
        String placeNumber;
        String firstPlaceNumber;
        String secondPlaceNumber;
        String thirdPlaceNumber;

        public ComparisonStat(String accuracy, String standingAccuracy,
                              String layingAccuracy, String raceNumber,
                              String placeNumber, String firstPlaceNumber,
                              String secondPlaceNumber,String thirdPlaceNumber ) {
            this.accuracy = accuracy;
            this.standingAccuracy = standingAccuracy;
            this.layingAccuracy = layingAccuracy;
            this.raceNumber = raceNumber;
            this.placeNumber = placeNumber;
            this.firstPlaceNumber = firstPlaceNumber;
            this.secondPlaceNumber = secondPlaceNumber;
            this.thirdPlaceNumber = thirdPlaceNumber;
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

        public String getPlaceNumber() {
            return placeNumber;
        }

        public void setPlaceNumber(String placeNumber) {
            this.placeNumber = placeNumber;
        }

        public String getFirstPlaceNumber() {
            return firstPlaceNumber;
        }

        public void setFirstPlaceNumber(String firstPlaceNumber) {
            this.firstPlaceNumber = firstPlaceNumber;
        }

        public String getSecondPlaceNumber() {
            return secondPlaceNumber;
        }

        public void setSecondPlaceNumber(String secondPlaceNumber) {
            this.secondPlaceNumber = secondPlaceNumber;
        }

        public String getThirdPlaceNumber() {
            return thirdPlaceNumber;
        }

        public void setThirdPlaceNumber(String thirdPlaceNumber) {
            this.thirdPlaceNumber = thirdPlaceNumber;
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

    @GetMapping("/main")
    public ModelAndView main() {
        Map<String, Object> model = new HashMap<>();
        return new ModelAndView("main", model);
    }

   @GetMapping("/")
   public ModelAndView greeting() {
       Map<String, Object> model = new HashMap<>();
       return new ModelAndView("redirect:/main", model);
   }

    @PostMapping("/compare")
    public ModelAndView comparison(@RequestParam(name = "idfirstsportsman", required = false) long idfirstsportsman,
                                   @RequestParam(name = "idsecondsportsman", required = false) long idsecondsportsman) {
        Map<String, Object> model = new HashMap<>();
        List<Sportsman> sportsmanStat = sportsmanStats.findAll();

        List<SportsmanRace> sportsmanRaceStat = sportsmanRaceStats.findAll();
        int accuracyFirst = 1;
        int layingAccuracyFirst = 1;
        int standingAccuracyFirst = 1;
        int raceNumberFirst = 1;
        int placeNumberFirst = 0;
        int firstPlaceNumberFirst = 0;
        int secondPlaceNumberFirst = 0;
        int thirdPlaceNumberFirst = 0;
        int accuracySecond = 1;
        int layingAccuracySecond = 1;
        int standingAccuracySecond = 1;
        int raceNumberSecond = 1;
        int placeNumberSecond = 0;
        int firstPlaceNumberSecond = 0;
        int secondPlaceNumberSecond = 0;
        int thirdPlaceNumberSecond = 0;

        for (Sportsman stat : sportsmanStat) {
            if (stat.getId_sportsman() == (idfirstsportsman)) {
                for (SportsmanRace statRace : sportsmanRaceStat) {
                    if (Objects.equals(
                            statRace.getIdsportsman(),idfirstsportsman)) {
                        switch (statRace.getPlacesportsman()) {
                            case 1 -> {
                                placeNumberFirst++;
                                firstPlaceNumberFirst++;
                            }
                            case 2 -> {
                                placeNumberFirst++;
                                secondPlaceNumberFirst++;
                            }
                            case 3 -> {
                                placeNumberFirst++;
                                thirdPlaceNumberFirst++;
                            }
                        }
                        layingAccuracyFirst += statRace.getLayingaccuracy();
                        standingAccuracyFirst += statRace.getStandingaccuracy();
                        raceNumberFirst++;
                    }
                }
                standingAccuracyFirst = standingAccuracyFirst / raceNumberFirst;
                layingAccuracyFirst = layingAccuracyFirst / raceNumberFirst;
                accuracyFirst = (standingAccuracyFirst + layingAccuracyFirst) / 2;
                raceNumberFirst--;
                Stat stat1 = new Stat(stat.getId_sportsman(),raceNumberFirst, accuracyFirst, standingAccuracyFirst, layingAccuracyFirst, placeNumberFirst, firstPlaceNumberFirst, secondPlaceNumberFirst, thirdPlaceNumberFirst);
                model.put("sportsman1", stat);
                model.put("sportsmanStat1", stat1);
            }
            if (stat.getId_sportsman() == (idsecondsportsman)) {
                for (SportsmanRace statRace : sportsmanRaceStat) {
                    if (Objects.equals(statRace.getIdsportsman(),
                            idsecondsportsman)) {
                        switch (statRace.getPlacesportsman()) {
                            case 1 -> {
                                placeNumberSecond++;
                                firstPlaceNumberSecond++;
                            }
                            case 2 -> {
                                placeNumberSecond++;
                                secondPlaceNumberSecond++;
                            }
                            case 3 -> {
                                placeNumberSecond++;
                                thirdPlaceNumberSecond++;
                            }
                        }
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
                Stat stat1 = new Stat(stat.getId_sportsman(),raceNumberSecond, accuracySecond, standingAccuracySecond, layingAccuracySecond, placeNumberSecond, firstPlaceNumberSecond, secondPlaceNumberSecond, thirdPlaceNumberSecond);
                model.put("sportsman2", stat);
                model.put("sportsmanStat2", stat1);
            }
        }

        ComparisonStat comparisonStat = new ComparisonStat("", "", "", "","","","","");
        Stat comparison = new Stat(100000,1, 1, 1, 1, 1, 1, 1, 1);
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

        if (placeNumberFirst > placeNumberSecond) {
            comparisonStat.placeNumber =
                    "<" + (placeNumberFirst - placeNumberSecond);
        } else if (placeNumberFirst < placeNumberSecond) {
            comparisonStat.placeNumber =
                    (placeNumberSecond - placeNumberFirst) + ">";
        } else {
            comparisonStat.placeNumber = "=";
        }

        if (firstPlaceNumberFirst > firstPlaceNumberSecond) {
            comparisonStat.firstPlaceNumber =
                    "<" + (firstPlaceNumberFirst - firstPlaceNumberSecond);
        } else if (firstPlaceNumberFirst < firstPlaceNumberSecond) {
            comparisonStat.firstPlaceNumber =
                    (firstPlaceNumberSecond - firstPlaceNumberFirst) + ">";
        } else {
            comparisonStat.firstPlaceNumber = "=";
        }

        if (secondPlaceNumberFirst > secondPlaceNumberSecond) {
            comparisonStat.secondPlaceNumber =
                    "<" + (secondPlaceNumberFirst - secondPlaceNumberSecond);
        } else if (secondPlaceNumberFirst < secondPlaceNumberSecond) {
            comparisonStat.secondPlaceNumber =
                    (secondPlaceNumberSecond - secondPlaceNumberFirst) + ">";
        } else {
            comparisonStat.secondPlaceNumber = "=";
        }

        if (thirdPlaceNumberFirst > thirdPlaceNumberSecond) {
            comparisonStat.thirdPlaceNumber =
                    "<" + (thirdPlaceNumberFirst - thirdPlaceNumberSecond);
        } else if (thirdPlaceNumberFirst < thirdPlaceNumberSecond) {
            comparisonStat.thirdPlaceNumber =
                    (thirdPlaceNumberSecond - thirdPlaceNumberFirst) + ">";
        } else {
            comparisonStat.thirdPlaceNumber = "=";
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
    public ModelAndView sportsman(@RequestParam(name = "idsportsman") long idSportsman) {
        Map<String, Object> model = new HashMap<>();
        List<SportsmanCoach> coachStat = sportsmanCoachStats.findAll();
        List<Coach> coaches = coachStats.findAll();
        for(SportsmanCoach sportsmanCoach: coachStat) {
            if (idSportsman==(sportsmanCoach.getIdsportsman())) {
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
        int placeNumber = 0;
        int firstPlaceNumber = 0;
        int secondPlaceNumber = 0;
        int thirdPlaceNumber = 0;
        for (Sportsman stat : sportsmanStat) {
            if (stat.getId_sportsman()==(idSportsman)) {
                for (SportsmanRace statRace : sportsmanRaceStat) {
                    if (Objects.equals(statRace.getIdsportsman(), idSportsman)) {
                        sportsmanInRaces.add(statRace);
                        switch (statRace.getPlace()) {
                            case "1" -> {
                                placeNumber++;
                                firstPlaceNumber++;
                            }
                            case "2" -> {
                                placeNumber++;
                                secondPlaceNumber++;
                            }
                            case "3" -> {
                                placeNumber++;
                                thirdPlaceNumber++;
                            }
                        }
                        layingAccuracy += statRace.getLayingaccuracy();
                        standingAccuracy += statRace.getStandingaccuracy();
                        raceNumber++;
                    }
                }
                standingAccuracy = standingAccuracy / raceNumber;
                layingAccuracy = layingAccuracy / raceNumber;
                accuracy = (standingAccuracy + layingAccuracy) / 2;
                raceNumber--;
                Stat stat1 = new Stat(stat.getId_sportsman(), raceNumber, accuracy, standingAccuracy, layingAccuracy, placeNumber, firstPlaceNumber, secondPlaceNumber, thirdPlaceNumber);
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

    @GetMapping(value = "/sportsmansAdminRedirect")
    public ModelAndView sportsmansAdminRedirect() {
        Map<String, Object> model = new HashMap<>();
        return new ModelAndView ("redirect:/sportsmansAdmin", model);
    }

    @GetMapping(value = "/sportsmansAdmin")
    public ModelAndView sportsmansAdmin(@RequestParam(name = "start", required = false) Integer start, @RequestParam(name = "end", required = false)  Integer end) {
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
        return new ModelAndView ("sportsmansAdmin", model);
    }

    @GetMapping(value = "/comparison")
    public ModelAndView comparison() {
        Map<String, Object> model = new HashMap<>();
        List<Sportsman> sportsmen = sportsmanStats.findAll();
        model.put("sportsman", sportsmen);
        return new ModelAndView ("comparison", model);
    }

    @GetMapping(value = "/comparisonRedirect")
    public ModelAndView comparisonRedirect() {
        Map<String, Object> model = new HashMap<>();
        return new ModelAndView ("redirect:/comparison", model);
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
        if (start <= 0 ) {
            start = 0;
        }
        if (end <= 15) {
            end = 15;
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
        if (request.getParameter("start") != null) {
            end += 15;
        } if (end >= Math.toIntExact(sportsmanStats.count())) {
            end = Math.toIntExact(sportsmanStats.count());
            start = Math.toIntExact(sportsmanStats.count()) - 20;
        }
        if (start <= 0) {
            start = 0;
        }
        if (end <= 0) {
            end = 20;
        }
        return this.sportsmans(start, end);
    }

    @PostMapping(value = "/prevPageAdmin")
    public ModelAndView prevPageAdmin(HttpServletRequest request) {
        Map<String, Object> model = new HashMap<>();
        int start = Integer.parseInt(request.getParameter("start"));
        int end = Integer.parseInt(request.getParameter("end"));
        if (request.getParameter("start") != null) {
            start -= 20;
        }
        if (request.getParameter("start") != null) {
            end -= 20;
        }
        if (start <= 0 ) {
            start = 0;
        }
        if (end <= 15) {
            end = 15;
        }
        return this.sportsmansAdmin(start, end);
    }

    @PostMapping(value = "/nextPageAdmin")
    public ModelAndView nextPageAdmin(HttpServletRequest request) {
        Map<String, Object> model = new HashMap<>();
        int start = Integer.parseInt(request.getParameter("start"));
        int end = Integer.parseInt(request.getParameter("end"));
        if (request.getParameter("start") != null) {
            start += 15;
        }
        if (request.getParameter("start") != null) {
            end += 15;
        } if (end >= Math.toIntExact(sportsmanStats.count())) {
            end = Math.toIntExact(sportsmanStats.count());
            start = Math.toIntExact(sportsmanStats.count()) - 20;
        }
        if (start <= 0) {
            start = 0;
        }
        if (end <= 0) {
            end = 20;
        }
        return this.sportsmansAdmin(start, end);
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
    public ModelAndView addSportsman(@RequestParam(name = "idaddedsportsman") long idAddedSportsman, @RequestParam(name = "sportsman", required = false) String sportsmen) throws UnsupportedEncodingException {
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
           if (sportsman.getId_sportsman()==(idAddedSportsman)) {
               addedSportsmans.add(sportsman);
               break;
           }
       }
       model.put("sportsmanadd", sportsmanStat);
       model.put("sportsman", addedSportsmans);
       return new ModelAndView ("resultRace", model);
    }

    @PostMapping("/deleteSportsman")
    public ModelAndView deleteSportsman(@RequestParam(name = "idremovedsportsman") long idRemovedSportsman,@RequestParam(name = "sportsman", required = false) String sportsmen) throws UnsupportedEncodingException {
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
            if (addedSportsmans.get(i).getId_sportsman()==(idRemovedSportsman)) {
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

    @Transactional
    @PostMapping("/changeSportsman")
    public ModelAndView changeSportsman(HttpServletRequest request, @RequestParam String stats) throws UnsupportedEncodingException {
        String encodedWithISO88591 = stats;
        stats = new String(encodedWithISO88591.getBytes("ISO-8859-1"), "UTF-8");
        try {
            stats = stats.replace(",",";");
            Sportsman stat = new Sportsman(stats);
            sportsmanStats.save(stat);
        } catch (Exception e) {
            e.printStackTrace();
        }

        int start = 0;
        int end = 15;

        if (!(request.getParameter("start") == null)) {
            start = Integer.parseInt(request.getParameter("start"));
        }

        if (!(request.getParameter("end") == null)) {
            end = Integer.parseInt(request.getParameter("end"));
        }
        return sportsmansAdmin(start, end);
    }

    @Transactional
    @PostMapping("/deleteSportsmanFromDatabase")
    public ModelAndView deleteSportsmanFromDatabase(HttpServletRequest request, @RequestParam(name = "idremovedsportsman") String idRemovedSportsman, @RequestParam(name = "sportsman", required = false) String sportsmen) throws UnsupportedEncodingException {
        //System.out.println(statId);

        sportsmanStats.deleteById(Long.valueOf(idRemovedSportsman));
        int start = 0;
        int end = 15;
        if (!(request.getParameter("start") == null)) {
            start = Integer.parseInt(request.getParameter("start"));
        }

        if (!(request.getParameter("end") == null)) {
            end = Integer.parseInt(request.getParameter("end"));
        }

        return sportsmansAdmin(start, end);
    }
}
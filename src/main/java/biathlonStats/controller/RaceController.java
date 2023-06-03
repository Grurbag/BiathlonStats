package biathlonStats.controller;

import biathlonStats.entity.*;
import biathlonStats.repo.*;

import java.io.UnsupportedEncodingException;
import java.util.*;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class RaceController {
    @Autowired private SportsmanRaceStatRepo sportsmanRaceStats;

    @Autowired private RaceStatRepo raceStats;

    @Autowired private SportsmanStatRepo sportsmanStats;

    private ArrayList<Sportsman> addedSportsmans = new ArrayList<>();

    @GetMapping("/races")
    public ModelAndView races(@RequestParam(name = "start", required = false) Integer start, @RequestParam(name = "end", required = false) Integer end) {
        if (start == null) {
            start = 0;
        }
        if (end == null) {
            end = 11;
        }
        List<Race> raceStat = raceStats.findAll();
        Map<String, Object> model = new HashMap<>();
        ArrayList<Race> showingList = new ArrayList<>();
        for (int i = start; i < end; i++) {
            showingList.add(raceStat.get(i));
        }
        model.put("start", start);
        model.put("end", end);
        model.put("race", showingList);
        return new ModelAndView("races", model);
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

    @PostMapping("/predictResult")
    public ModelAndView predictResult( @RequestParam(name = "sportsman", required = false) String sportsmen) throws UnsupportedEncodingException  {
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
        List<SportsmanRace> sportsmanRaceStat = sportsmanRaceStats.findAll();
        ArrayList<SportsmanRace> sportsmanRaceIds = new ArrayList<>();
        for (SportsmanRace sportsmanRace : sportsmanRaceStat) {
            for (Sportsman sportsman : addedSportsmans) {
                if (sportsman.getId_sportsman().equals(sportsmanRace.getIdsportsman())) {
                    sportsmanRaceIds.add(sportsmanRace);
                }
            }
        }
        ArrayList<MainController.Stat> addedSportsmansStats = new ArrayList<>();
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
            MainController.Stat stat1 = new MainController.Stat(stat.getId_sportsman(), raceNumber, accuracy, standingAccuracy, layingAccuracy);
            addedSportsmansStats.add(stat1);
        }
        boolean sorted = false;
        MainController.Stat temp;
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
        for (MainController.Stat addedSportsmansStat : addedSportsmansStats) {
            for (Sportsman addedSportsman : addedSportsmans) {
                if (addedSportsmansStat.getIdsportsman().equals(addedSportsman.getId_sportsman())) {
                    sortedSportsmans.add(addedSportsman);
                }
            }
        }
        addedSportsmans = sortedSportsmans;
        model.put("sportsman", addedSportsmans);
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

    @GetMapping(value = "/racesRedirect")
    public ModelAndView racesRedirect() {
        Map<String, Object> model = new HashMap<>();
        return new ModelAndView ("redirect:/races", model);
    }

    @GetMapping(value = "/resultRaceRedirect")
    public ModelAndView resultRaceRedirect() {
        Map<String, Object> model = new HashMap<>();
        return new ModelAndView ("redirect:/resultRace", model);
    }

    @PostMapping(value = "/prevPageRace")
    public ModelAndView prevPageRace(HttpServletRequest request) {
        Map<String, Object> model = new HashMap<>();
        int start = Integer.parseInt(request.getParameter("start"));
        int end = Integer.parseInt(request.getParameter("end"));
        if (request.getParameter("start") != null) {
            start -= 20;
        } if (start <= 0) {
            start = 0;
        }
        if (request.getParameter("start") != null) {
            end -= 20;
        } if (end <= 20) {
            end = 20;
        }
        return this.races(start, end);
    }

    @PostMapping(value = "/nextPageRace")
    public ModelAndView nextPageRace(HttpServletRequest request) {
        Map<String, Object> model = new HashMap<>();
        int start = Integer.parseInt(request.getParameter("start"));
        int end = Integer.parseInt(request.getParameter("end"));
        if (request.getParameter("start") != null) {
            start += 20;
        } if (start <= 0) {
            start = 0;
        }
        if (request.getParameter("start") != null) {
            end += 20;
        } if (end >= 11) {
            end = 11;
        }
        return this.races(start, end);
    }
}

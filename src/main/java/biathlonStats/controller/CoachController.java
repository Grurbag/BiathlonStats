package biathlonStats.controller;

import biathlonStats.entity.*;
import biathlonStats.repo.*;

import java.io.UnsupportedEncodingException;
import java.util.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class CoachController {
    @Autowired private SportsmanStatRepo sportsmanStats;

    @Autowired private CoachStatRepo coachStats;

    @Autowired private RegionStatRepo regionStats;

    @Autowired private SportsmanCoachStatRepo sportsmanCoachStats;

    @GetMapping("/coaches")
    public ModelAndView coaches(@RequestParam(name = "start", required = false) Integer start, @RequestParam(name = "end", required = false) Integer end) {
        if (start == null) {
            start = 0;
        }
        if (end == null) {
            end = 20;
        }
        Map<String, Object> model = new HashMap<>();
        List<Coach> coachStat = coachStats.findAll();
        ArrayList<Coach> showingList = new ArrayList<>();
        for (int i = start; i < end; i++) {
            showingList.add(coachStat.get(i));
        }
        model.put("start", start);
        model.put("end", end);
        model.put("coach", showingList);
        return new ModelAndView("coaches", model);
    }

    @GetMapping("/coachesAdmin")
    public ModelAndView coachesAdmin(@RequestParam(name = "start", required = false) Integer start, @RequestParam(name = "end", required = false) Integer end) {
        if (start == null) {
            start = 0;
        }
        if (end == null) {
            end = 20;
        }
        Map<String, Object> model = new HashMap<>();
        List<Coach> coachStat = coachStats.findAll();
        ArrayList<Coach> showingList = new ArrayList<>();
        for (int i = start; i < end; i++) {
            showingList.add(coachStat.get(i));
        }
        model.put("start", start);
        model.put("end", end);
        model.put("coach", showingList);
        return new ModelAndView("coachesAdmin", model);
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
                    if (sportsmanCoach.getIdsportsman()==(sportsman.getId_sportsman())) {
                        model.put("sportsman", sportsman);
                    }
                }
            }
        }
        return new ModelAndView("coach", model);
    }

    @PostMapping(value = "/prevPageCoach")
    public ModelAndView prevPageCoach(HttpServletRequest request) {
        Map<String, Object> model = new HashMap<>();
        int start = Integer.parseInt(request.getParameter("start"));
        int end = Integer.parseInt(request.getParameter("end"));
        if (request.getParameter("start") != null) {
            start -= 20;
        }

        if (request.getParameter("start") != null) {
            end -= 20;
        }
        if (start <= 0) {
            start = 0;
        }
        if (end <= 0) {
            end = 20;
        }
        return this.coaches(start, end);
    }

    @PostMapping(value = "/nextPageCoach")
    public ModelAndView nextPageCoach(HttpServletRequest request) {
        Map<String, Object> model = new HashMap<>();
        int start = Integer.parseInt(request.getParameter("start"));
        int end = Integer.parseInt(request.getParameter("end"));
        if (request.getParameter("start") != null) {
            start += 20;
        }

        if (request.getParameter("start") != null) {
            end += 20;
        } if (end >= Math.toIntExact(coachStats.count())) {
            end = Math.toIntExact(coachStats.count());
            start = Math.toIntExact(coachStats.count()) - 20;
        }
        if (start <= 0) {
            start = 0;
        }
        if (end <= 0) {
            end = 20;
        }
        return this.coaches(start, end);
    }

    @PostMapping(value = "/prevPageCoachAdmin")
    public ModelAndView prevPageCoachAdmin(HttpServletRequest request) {
        Map<String, Object> model = new HashMap<>();
        int start = Integer.parseInt(request.getParameter("start"));
        int end = Integer.parseInt(request.getParameter("end"));
        if (request.getParameter("start") != null) {
            start -= 20;
        }

        if (request.getParameter("start") != null) {
            end -= 20;
        }
        if (start <= 0) {
            start = 0;
        }
        if (end <= 0) {
            end = 20;
        }
        return this.coachesAdmin(start, end);
    }

    @PostMapping(value = "/nextPageCoachAdmin")
    public ModelAndView nextPageCoachAdmin(HttpServletRequest request) {
        Map<String, Object> model = new HashMap<>();
        int start = Integer.parseInt(request.getParameter("start"));
        int end = Integer.parseInt(request.getParameter("end"));
        if (request.getParameter("start") != null) {
            start += 20;
        }

        if (request.getParameter("start") != null) {
            end += 20;
        } if (end >= Math.toIntExact(coachStats.count())) {
            end = Math.toIntExact(coachStats.count());
            start = Math.toIntExact(coachStats.count()) - 20;
        }
        if (start <= 0) {
            start = 0;
        }
        if (end <= 0) {
            end = 20;
        }
        return this.coachesAdmin(start, end);
    }

    @GetMapping(value = "/coachesRedirect")
    public ModelAndView coachesRedirect() {
        Map<String, Object> model = new HashMap<>();
        return new ModelAndView("redirect:/coaches", model);
    }

    @GetMapping(value = "/coachesAdminRedirect")
    public ModelAndView coachesAdminRedirect() {
        Map<String, Object> model = new HashMap<>();
        return new ModelAndView("redirect:/coachesAdmin", model);
    }

    @Transactional
    @PostMapping("/deleteCoachFromDatabase")
    public ModelAndView deleteCoachFromDatabase(HttpServletRequest request, @RequestParam(name = "idremovedcoach") int idRemovedCoach) throws UnsupportedEncodingException {
        //System.out.println(statId);

        coachStats.deleteById(String.valueOf(idRemovedCoach));
        int start = 0;
        int end = 15;
        if (!(request.getParameter("start") == null)) {
            start = Integer.parseInt(request.getParameter("start"));
        }

        if (!(request.getParameter("end") == null)) {
            end = Integer.parseInt(request.getParameter("end"));
        }

        return coachesAdmin(start, end);
    }

    @Transactional
    @PostMapping("/changeCoach")
    public ModelAndView changeCoach(HttpServletRequest request, @RequestParam String stats) throws UnsupportedEncodingException {
        String encodedWithISO88591 = stats;
        stats = new String(encodedWithISO88591.getBytes("ISO-8859-1"), "UTF-8");
        try {
            stats = stats.replace(",",";");
            Coach stat = new Coach(stats);
            coachStats.save(stat);
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
        return coachesAdmin(start, end);
    }
}

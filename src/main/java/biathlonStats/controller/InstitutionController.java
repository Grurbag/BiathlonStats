package biathlonStats.controller;

import biathlonStats.entity.*;
import biathlonStats.repo.*;
import java.util.*;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class InstitutionController {

    @Autowired private InstitutionStatRepo institutionStats;

    @Autowired private SportsmanStatRepo sportsmanStats;


    @GetMapping("/institutions")
    public ModelAndView institutions(@RequestParam(name = "start", required = false) Integer start, @RequestParam(name = "end", required = false) Integer end) {
        if (start == null) {
            start = 0;
        }
        if (end == null) {
            end = 20;
        }
        Map<String, Object> model = new HashMap<>();
        List<Institution> institutionStat = institutionStats.findAll();
        ArrayList<Institution> showingList = new ArrayList<>();
        for (int i = start; i < end; i++) {
            showingList.add(institutionStat.get(i));
        }
        model.put("start", start);
        model.put("end", end);
        model.put("institution", showingList);
        return new ModelAndView("institutions", model);
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

    @GetMapping(value = "/institutionsRedirect")
    public ModelAndView institutionsRedirect() {
        Map<String, Object> model = new HashMap<>();
        return new ModelAndView("redirect:/institutions", model);
    }

    @PostMapping(value = "/prevPageInstitution")
    public ModelAndView prevPageInstitution(HttpServletRequest request) {
        Map<String, Object> model = new HashMap<>();
        int start = Integer.parseInt(request.getParameter("start"));
        int end = Integer.parseInt(request.getParameter("end"));
        if (request.getParameter("start") != null) {
            start -= 20;
        } else if (start == 0) {
            start = 0;
        }
        if (request.getParameter("start") != null) {
            end -= 20;
        } else if (end == 20) {
            end = 20;
        }
        return this.institutions(start, end);
    }

    @PostMapping(value = "/nextPageInstitution")
    public ModelAndView nextPageInstitution(HttpServletRequest request) {
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
        } if (end >= 60) {
            end = 78;
        }
        return this.institutions(start, end);
    }

    @GetMapping(value = "/institutionRedirect")
    public ModelAndView institutionRedirect() {
        Map<String, Object> model = new HashMap<>();
        return new ModelAndView ("redirect:/institution", model);
    }
}

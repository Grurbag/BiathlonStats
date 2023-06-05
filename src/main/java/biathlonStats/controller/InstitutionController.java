package biathlonStats.controller;

import biathlonStats.entity.*;
import biathlonStats.repo.*;

import java.io.UnsupportedEncodingException;
import java.util.*;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
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

    @GetMapping("/institutionsAdmin")
    public ModelAndView institutionsAdmin(@RequestParam(name = "start", required = false) Integer start, @RequestParam(name = "end", required = false) Integer end) {
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
        return new ModelAndView("institutionsAdmin", model);
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

    @GetMapping(value = "/institutionsAdminRedirect")
    public ModelAndView institutionsAdminRedirect() {
        Map<String, Object> model = new HashMap<>();
        return new ModelAndView("redirect:/institutionsAdmin", model);
    }

    @PostMapping(value = "/prevPageInstitution")
    public ModelAndView prevPageInstitution(HttpServletRequest request) {
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
        return this.institutions(start, end);
    }

    @PostMapping(value = "/nextPageInstitution")
    public ModelAndView nextPageInstitution(HttpServletRequest request) {
        Map<String, Object> model = new HashMap<>();
        int start = Integer.parseInt(request.getParameter("start"));
        int end = Integer.parseInt(request.getParameter("end"));
        if (request.getParameter("start") != null) {
            start += 20;
        }

        if (request.getParameter("start") != null) {
            end += 20;
        } if (end >= Math.toIntExact(institutionStats.count())) {
            end = Math.toIntExact(institutionStats.count());
            start = Math.toIntExact(institutionStats.count()) - 20;
        }
        if (start <= 0) {
            start = 0;
        }
        if (end <= 0) {
            end = 20;
        }
        return this.institutions(start, end);
    }

    @PostMapping(value = "/prevPageInstitutionAdmin")
    public ModelAndView prevPageInstitutionAdmin(HttpServletRequest request) {
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
        return this.institutionsAdmin(start, end);
    }

    @PostMapping(value = "/nextPageInstitutionAdmin")
    public ModelAndView nextPageInstitutionAdmin(HttpServletRequest request) {
        Map<String, Object> model = new HashMap<>();
        int start = Integer.parseInt(request.getParameter("start"));
        int end = Integer.parseInt(request.getParameter("end"));
        if (request.getParameter("start") != null) {
            start += 20;
        }

        if (request.getParameter("start") != null) {
            end += 20;
        } if (end >= Math.toIntExact(institutionStats.count())) {
            end = Math.toIntExact(institutionStats.count());
            start = Math.toIntExact(institutionStats.count()) - 20;
        }
        if (start <= 0) {
            start = 0;
        }
        if (end <= 0) {
            end = 20;
        }
        return this.institutionsAdmin(start, end);
    }

    @GetMapping(value = "/institutionRedirect")
    public ModelAndView institutionRedirect() {
        Map<String, Object> model = new HashMap<>();
        return new ModelAndView ("redirect:/institution", model);
    }

    @Transactional
    @PostMapping("/deleteInstitutionFromDatabase")
    public ModelAndView deleteInstitutionFromDatabase(HttpServletRequest request, @RequestParam(name = "idremovedinstitution") int idRemoveInstitution) throws UnsupportedEncodingException {
        //System.out.println(statId);

        institutionStats.deleteById(String.valueOf(idRemoveInstitution));
        int start = 0;
        int end = 15;
        if (!(request.getParameter("start") == null)) {
            start = Integer.parseInt(request.getParameter("start"));
        }

        if (!(request.getParameter("end") == null)) {
            end = Integer.parseInt(request.getParameter("end"));
        }

        return institutionsAdmin(start, end);
    }

    @Transactional
    @PostMapping("/changeInstitution")
    public ModelAndView changeInstitution(HttpServletRequest request, @RequestParam String stats) throws UnsupportedEncodingException {
        String encodedWithISO88591 = stats;
        stats = new String(encodedWithISO88591.getBytes("ISO-8859-1"), "UTF-8");
        try {
            stats = stats.replace(",",";");
            Institution stat = new Institution(stats);
            institutionStats.save(stat);
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
        return institutionsAdmin(start, end);
    }
}

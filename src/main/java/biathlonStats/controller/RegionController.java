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
public class RegionController {

    @Autowired private SportsmanStatRepo sportsmanStats;

    @Autowired private RegionStatRepo regionStats;

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

    @GetMapping("/regions")
    public ModelAndView regions(@RequestParam(name = "start", required = false) Integer start, @RequestParam(name = "end", required = false) Integer end) {
        if (start == null) {
            start = 0;
        }
        if (end == null) {
            end = 11;
        }
        Map<String, Object> model = new HashMap<>();
        List<Region> regionStat = regionStats.findAll();
        ArrayList<Region> showingList = new ArrayList<>();
        for (int i = start; i < end; i++) {
            showingList.add(regionStat.get(i));
        }
        model.put("start", start);
        model.put("end", end);
        model.put("region", showingList);
        return new ModelAndView("regions", model);
    }

    @GetMapping("/regionsAdmin")
    public ModelAndView regionsAdmin(@RequestParam(name = "start", required = false) Integer start, @RequestParam(name = "end", required = false) Integer end) {
        if (start == null) {
            start = 0;
        }
        if (end == null) {
            end = 11;
        }
        Map<String, Object> model = new HashMap<>();
        List<Region> regionStat = regionStats.findAll();
        ArrayList<Region> showingList = new ArrayList<>();
        for (int i = start; i < end; i++) {
            showingList.add(regionStat.get(i));
        }
        model.put("start", start);
        model.put("end", end);
        model.put("region", showingList);
        return new ModelAndView("regionsAdmin", model);
    }

    @GetMapping(value = "/regionsRedirect")
    public ModelAndView regionsRedirect() {
        Map<String, Object> model = new HashMap<>();
        return new ModelAndView("redirect:/regions", model);
    }

    @PostMapping(value = "/prevPageRegion")
    public ModelAndView prevPageRegion(HttpServletRequest request) {
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
        if (end <= 20) {
            end = 20;
        }
        return this.regions(start, end);
    }

    @PostMapping(value = "/nextPageRegion")
    public ModelAndView nextPageRegion(HttpServletRequest request) {
        Map<String, Object> model = new HashMap<>();
        int start = Integer.parseInt(request.getParameter("start"));
        int end = Integer.parseInt(request.getParameter("end"));
        if (request.getParameter("start") != null) {
            start += 20;
        } if (start <= 0) {
            start = 0;
        } if (start >= 40) {
            start = 40;
        }
        if (request.getParameter("start") != null) {
            end += 20;
        } if (end >= 40) {
            end = 55;
        }
        return this.regions(start, end);
    }

    @PostMapping(value = "/prevPageRegionAdmin")
    public ModelAndView prevPageRegionAdmin(HttpServletRequest request) {
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
        if (end <= 20) {
            end = 20;
        }
        return this.regionsAdmin(start, end);
    }

    @PostMapping(value = "/nextPageRegionAdmin")
    public ModelAndView nextPageRegionAdmin(HttpServletRequest request) {
        Map<String, Object> model = new HashMap<>();
        int start = Integer.parseInt(request.getParameter("start"));
        int end = Integer.parseInt(request.getParameter("end"));
        if (request.getParameter("start") != null) {
            start += 20;
        } if (start <= 0) {
            start = 0;
        } if (start >= 40) {
            start = 40;
        }
        if (request.getParameter("start") != null) {
            end += 20;
        } if (end >= 40) {
            end = 55;
        }
        return this.regionsAdmin(start, end);
    }

    @GetMapping(value = "/regionRedirect")
    public ModelAndView regionRedirect() {
        Map<String, Object> model = new HashMap<>();
        return new ModelAndView ("redirect:/region", model);
    }

    @GetMapping(value = "/regionsAdminRedirect")
    public ModelAndView regionsAdminRedirect() {
        Map<String, Object> model = new HashMap<>();
        return new ModelAndView ("redirect:/regionsAdmin", model);
    }

    @Transactional
    @PostMapping("/deleteRegionFromDatabase")
    public ModelAndView deleteRegionFromDatabase(HttpServletRequest request, @RequestParam(name = "idremovedregion") int idRemovedRegion, @RequestParam(name = "region", required = false) String regions) throws UnsupportedEncodingException {
        //System.out.println(statId);

        regionStats.deleteById((long) idRemovedRegion);
        int start = 0;
        int end = 15;
        if (!(request.getParameter("start") == null)) {
            start = Integer.parseInt(request.getParameter("start"));
        }

        if (!(request.getParameter("end") == null)) {
            end = Integer.parseInt(request.getParameter("end"));
        }

        return regionsAdmin(start, end);
    }

    @Transactional
    @PostMapping("/changeRegion")
    public ModelAndView changeRegion(HttpServletRequest request, @RequestParam String stats) throws UnsupportedEncodingException {
        String encodedWithISO88591 = stats;
        stats = new String(encodedWithISO88591.getBytes("ISO-8859-1"), "UTF-8");
        try {
            stats = stats.replace(",",";");
            Region stat = new Region(stats);
            regionStats.save(stat);
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
        return regionsAdmin(start, end);
    }

}

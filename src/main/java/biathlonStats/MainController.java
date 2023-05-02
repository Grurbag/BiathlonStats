package biathlonStats;

import biathlonStats.entity.Institution;
import biathlonStats.repo.InstitutionStatRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import biathlonStats.repo.SportsmanStatRepo;

import java.util.Map;

@Controller
public class MainController {

  @Autowired
  private SportsmanStatRepo sportsmanStats;

  @Autowired
  private InstitutionStatRepo institutionStats;

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
    Iterable<Institution> institutionStat = institutionStats.findAll();
    /*for (Sportsman stat : sportsmanStat) {
      System.out.println(stat.getName());
    }*/
    model.put("institutions", institutionStat);
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

  @GetMapping("/sportsmans")
  public String sportsmans() {
    return "sportsmans";
  }

  @GetMapping
  public String main(Map<String, Object> model) {
   /* Iterable<Sportsman> sportsmanStat = biathlonstats.findAll();
    for (Sportsman stat : sportsmanStat) {
      System.out.println(stat.getName());
    }
    model.put("biathlonStats", sportsmanStat);*/
    return "main";
  }

  @GetMapping(value = "/institutionsRedirect")
  public String institutionsRedirect() {
    return "redirect:/institutions";
  }

  @GetMapping(value = "/mainRedirect")
  public String mainRedirect() {
    return "redirect:/main";
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
}
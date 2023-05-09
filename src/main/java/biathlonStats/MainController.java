package biathlonStats;

import biathlonStats.entity.Institution;
import biathlonStats.entity.Sportsman;
import biathlonStats.repo.InstitutionStatRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import biathlonStats.repo.SportsmanStatRepo;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MainController {
  int start = 0;
  int end = 20;
  @Autowired
  private SportsmanStatRepo sportsmanStats;

  @Autowired
  private InstitutionStatRepo institutionStats;

  @GetMapping("/registration")
  public String registration() {
    return "registration";
  }

  @GetMapping("/sighIn")
  public String sighIn() {
    return "sighIn";
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
  public String sportsmans(Map < String, Object > model) {

    List<Sportsman> sportsmanStat = sportsmanStats.findAll();
    ArrayList<Sportsman> showingList = new ArrayList<>();
    for (int i = start; i < end; i ++){
      showingList.add(sportsmanStat.get(i));
    }
    model.put("sportsman", showingList);
    return "sportsmans";
  }

  @GetMapping
  public String main(Map<String, Object> model) {

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

  @GetMapping(value = "/registrationRedirect")
  public String registrationRedirect() {
    return "redirect:/registration";
  }

  @GetMapping(value = "/sighInRedirect")
  public String sighInRedirect() {
    return "redirect:/sighIn";
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
}
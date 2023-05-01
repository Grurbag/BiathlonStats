package biathlonStats;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import biathlonStats.entity.Sportsman;
import biathlonStats.repo.BiathlonStatsRepo;

import java.util.Map;

@Controller
public class MainController {

  @Autowired
  private BiathlonStatsRepo biathlonstats;

  @GetMapping("/greeting")
  public String greeting(
          @RequestParam(name="name", required=false, defaultValue="World") String name,
          Map<String, Object> model
  ) {
    model.put("name", name);
    return "greeting";
  }

  @GetMapping("/institution")
  public String institution() {
    return "institution";
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

  @GetMapping(value = "/institutionRedirect")
  public String institutionRedirect() {
    return "redirect:/institution";
  }

  @GetMapping(value = "/mainRedirect")
  public String mainRedirect() {
    return "redirect:/main";
  }
}
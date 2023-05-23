package biathlonStats;

import biathlonStats.entity.Role;
import biathlonStats.entity.User;
import biathlonStats.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;
import java.util.Map;

@Controller
public class RegistrationController {
    @Autowired
    private UserRepo userRepo;

    @GetMapping("/redirectPersonalArea")
    public String redirectPersonalArea() {
        return "redirect:/personalArea";
    }

    @GetMapping("/personalArea")
    public String personalArea(Map<String, Object> model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        model.put("user", currentPrincipalName);
        return "personalArea";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/logIn")
    public String logIn(User user, Map<String, Object> model) {
        User userFromDb = userRepo.findByUsername(user.getUsername());

        if (userFromDb != null) {
            model.put("message", "Неправильно введенные данные, попробуйте еще раз");
            return "login";
        }
        return "personalArea";
    }

    @PostMapping("/logOut")
    public String logOut() {
        SecurityContextHolder.clearContext();
        return "/login";
    }

    @PostMapping("/registration")
    public String addUser(User user, Map<String, Object> model) {
            User userFromDb = userRepo.findByUsername(user.getUsername());

            if (userFromDb != null) {
                model.put("message", "Пользователь существует!");
                return "registration";
            }

            user.setActive(true);
            user.setRoles(Collections.singleton(Role.ROLE_USER));
            userRepo.save(user);

            return "redirect:/login";
    }

    @GetMapping(value = "/registrationRedirect")
    public String registrationRedirect() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return "redirect:/personalArea";
        } else {
            return "redirect:/registration";
        }
    }

    @GetMapping(value = "/sighInRedirect")
    public String sighInRedirect() {
        return "redirect:/sighIn";
    }

    @GetMapping(value = "/personalAreaRedirect")
    public String personalAreaRedirect() {
        return "redirect:/personalArea";
    }
}
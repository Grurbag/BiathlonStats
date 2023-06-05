package biathlonStats.controller;

import biathlonStats.entity.Role;
import biathlonStats.entity.User;
import biathlonStats.repo.UserRepo;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
public class RegistrationController {

    @Autowired
    private UserRepo userRepo;

    @GetMapping("/redirectPersonalArea")
    public ModelAndView redirectPersonalArea() {
        Map<String, Object> model = new HashMap<>();
        return new ModelAndView("redirect:/personalArea", model);
    }

    @GetMapping("/personalArea")
    public ModelAndView personalArea() {
        Map<String, Object> model = new HashMap<>();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        String role = String.valueOf(authentication.getAuthorities());
        if (role.equals("[ROLE_ADMIN]")) {
            role = "Администратор";
        } else {
            role = "Пользователь";
        }
        model.put("user", currentPrincipalName);
        model.put("role", role);
        return new ModelAndView ("personalArea", model);
    }

    @GetMapping("/login")
    public ModelAndView login() {
        Map<String, Object> model = new HashMap<>();
        return new ModelAndView("login", model);
    }

    @GetMapping("/loginRedirect")
    public ModelAndView loginRedirect() {
        Map<String, Object> model = new HashMap<>();
        return new ModelAndView("redirect:/login", model);
    }

    @GetMapping("/registration")
    public ModelAndView registration() {
        Map<String, Object> model = new HashMap<>();
        return new ModelAndView("registration", model);
    }

    @PostMapping("/logIn")
    public ModelAndView logIn(User user) {
        Map<String, Object> model = new HashMap<>();
        User userFromDb = userRepo.findByUsername(user.getUsername());

        if (userFromDb == null) {
            model.put("message", "Неправильно введенные данные, попробуйте еще раз");
            return new ModelAndView("login", model);
        } else {
            return new ModelAndView("redirect:/personalArea", model);
        }
    }

    @PostMapping("/logOut")
    public ModelAndView logOut(HttpServletRequest request) throws ServletException {
        Map<String, Object> model = new HashMap<>();
        request.logout();
        return new ModelAndView("login", model);
    }

    @Transactional
    @PostMapping("/registration")
    public ModelAndView addUser(User user) {
        Map<String, Object> model = new HashMap<>();
        User userFromDb = userRepo.findByUsername(user.getUsername());

        if (userFromDb != null) {
            model.put("message", "Пользователь существует!");
            return new ModelAndView("registration", model);
        }

        if (user.getUsername() == null) {
            model.put("message", "Ошибка ввода данных");
            return new ModelAndView("registration", model);
        }

        user.setActive(true);
        user.setRoles(Collections.singleton(Role.ROLE_USER));
        userRepo.save(user);
        return new ModelAndView("login", model);
    }

    @GetMapping(value = "/registrationRedirect")
    public ModelAndView registrationRedirect() {
        Map<String, Object> model = new HashMap<>();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return new ModelAndView("redirect:/personalArea", model);
        } else {
            return new ModelAndView("redirect:/registration", model);
        }
    }

    @GetMapping(value = "/personalAreaRedirect")
    public ModelAndView personalAreaRedirect() {
        Map<String, Object> model = new HashMap<>();
        return new ModelAndView("redirect:/personalArea", model);
    }
}
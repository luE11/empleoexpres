package pra.lue11.empleoexpres.controller;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pra.lue11.empleoexpres.model.User;
import pra.lue11.empleoexpres.service.PersonService;
import pra.lue11.empleoexpres.service.UserService;

/**
 * @author luE11 on 18/07/23
 */
@Controller
@AllArgsConstructor
public class HomeController {

    private final String INDEX_TEMPLATE = "index";
    private final String HOME_TEMPLATE = "user/home";

    private UserService userService;
    private PersonService personService;

    @GetMapping(value = "/home")
    public String showHomeScreen(Authentication authentication, Model model) {
        model.addAttribute("user", getUserFromAuth(authentication));
        return HOME_TEMPLATE;
    }

    /**
     * TODO: Logout method
     */

    private User getUserFromAuth(Authentication authentication){
        return userService.findUserByEmail(authentication.getName()).orElseThrow(() -> new EntityNotFoundException("Logged user not found"));
    }

}

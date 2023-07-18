package pra.lue11.empleoexpres.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pra.lue11.empleoexpres.model.User;
import pra.lue11.empleoexpres.service.AuthService;

import java.util.Optional;

/**
 * @author luE11 on 18/07/23
 */
@Controller
@AllArgsConstructor
public class AuthController {

    private final String LOGIN_TEMPLATE = "auth/login";
    private final String SIGNIN_TEMPLATE = "auth/signin";

    private AuthService authService;

    @GetMapping(value = "/login")
    public String login(Model model, @RequestParam() Optional<String> error,
                        @RequestParam() Optional<String> logout) {
        if (error.isPresent())
            model.addAttribute("error", "Your email and/or password are invalid.");

        if (logout.isPresent())
            model.addAttribute("message", "You have been logged out successfully.");

        return LOGIN_TEMPLATE;
    }

    /**
     * Change user to candidate/company
     * @param model
     * @return
     */
    @GetMapping(value = "/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return SIGNIN_TEMPLATE;
    }

}

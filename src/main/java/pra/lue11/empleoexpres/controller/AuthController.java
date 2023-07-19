package pra.lue11.empleoexpres.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pra.lue11.empleoexpres.dto.CandidateDTO;
import pra.lue11.empleoexpres.dto.CompanyDTO;
import pra.lue11.empleoexpres.service.UserService;

import java.util.Optional;

/**
 * @author luE11 on 18/07/23
 */
@Controller
@AllArgsConstructor
public class AuthController {

    private final String LOGIN_TEMPLATE = "auth/login";
    private final String SIGNIN_TEMPLATE = "auth/signin";

    private UserService userService;

    @GetMapping(value = "/login")
    public String login(Model model, @RequestParam("error") Optional<String> error,
                        @RequestParam("logout") Optional<String> logout) {
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
    @GetMapping(value = "/signin")
    public String showRegisterForm(@RequestParam("role") Optional<String> userRole, Model model) {
        if(userRole.isPresent() &&
                (userRole.get().compareTo("candidate")==0 || userRole.get().compareTo("ccompany")==0)){
            model.addAttribute("user",
                    (userRole.get().compareTo("candidate") == 0 ? new CandidateDTO() : new CompanyDTO()));
            model.addAttribute("userRole", userRole);
        }else {
            model.addAttribute("userRole", Optional.empty());
        }
        return SIGNIN_TEMPLATE;
    }

    @PostMapping(value = "/signin-candidate")
    public String signInCandidate(@Valid CandidateDTO candidateDTO){
        return "";
    }

    @PostMapping(value = "/signin-company")
    public String signInCompany(@Valid CompanyDTO companyDTO){
        return "";
    }

}

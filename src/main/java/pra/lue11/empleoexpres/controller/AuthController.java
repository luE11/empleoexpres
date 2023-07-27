package pra.lue11.empleoexpres.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import pra.lue11.empleoexpres.dto.CandidateDTO;
import pra.lue11.empleoexpres.dto.PublisherDTO;
import pra.lue11.empleoexpres.service.PersonService;
import pra.lue11.empleoexpres.service.PlaceService;
import pra.lue11.empleoexpres.service.PublisherService;
import pra.lue11.empleoexpres.service.UserService;

import java.io.IOException;
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
    private PersonService personService;
    private PlaceService placeService;
    private PublisherService publisherService;

    @GetMapping(value = "/login")
    public String login(Model model, @RequestParam("error") Optional<String> error,
                        @RequestParam("logout") Optional<String> logout) {
        if (error.isPresent())
            model.addAttribute("error", "Correo y/o contraseña inválidos.");

        if (logout.isPresent())
            model.addAttribute("message", "Sesión cerrada exitosamente");

        return LOGIN_TEMPLATE;
    }

    /**
     * Change user to candidate/company
     * @param model template model
     * @return name of register form template
     */
    @GetMapping(value = "/signin")
    public String showRegisterForm(@RequestParam("role") Optional<String> userRole, Model model) {
        if(userRole.isPresent() &&
                (userRole.get().compareTo("candidate")==0 || userRole.get().compareTo("publisher")==0)){
            model.addAttribute("user",
                    (userRole.get().compareTo("candidate") == 0 ? new CandidateDTO() : new PublisherDTO()));
            model.addAttribute("userRole", userRole);
            if(userRole.get().compareTo("candidate")==0){
                model.addAttribute("jobModalities", personService.getPreferredJobModalities());
                model.addAttribute("places", placeService.getAllPlaces());
            }
        }else {
            model.addAttribute("userRole", Optional.empty());
        }
        return SIGNIN_TEMPLATE;
    }

    @PostMapping(value = "/signin-candidate")
    public String signInCandidate(@Valid @ModelAttribute(name = "user") CandidateDTO user, BindingResult result, Model model) throws IOException {
        boolean error = result.hasErrors();
        if(checkEmailInUse(user.getEmail())){
            result.rejectValue("email", "", "El correo ingresado ya se encuentra en uso");
            error = true;
        }
        if(error){
            model.addAttribute("userRole", Optional.of("candidate"));
            model.addAttribute("user", user);
            model.addAttribute("jobModalities", personService.getPreferredJobModalities());
            model.addAttribute("places", placeService.getAllPlaces());
            return SIGNIN_TEMPLATE;
        }
        personService.insert(user);
        model.addAttribute("message", "Usuario registrado exitosamente");
        return LOGIN_TEMPLATE;
    }

    @PostMapping(value = "/signin-publisher")
    public String signInPublisher(@Valid @ModelAttribute(name = "user") PublisherDTO user, BindingResult result, Model model) throws IOException {
        boolean error = result.hasErrors();
        if(checkEmailInUse(user.getEmail())){
            result.rejectValue("email", "", "El correo ingresado ya se encuentra en uso");
            error = true;
        }
        if(error){
            model.addAttribute("userRole", Optional.of("publisher"));
            model.addAttribute("user", user);
            return SIGNIN_TEMPLATE;
        }
        publisherService.insert(user);
        model.addAttribute("message", "Usuario registrado exitosamente");
        return LOGIN_TEMPLATE;
    }

    private boolean checkEmailInUse(String email){
        return userService.userExistsByEmail(email);
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

}

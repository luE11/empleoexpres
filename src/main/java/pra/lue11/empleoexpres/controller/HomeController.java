package pra.lue11.empleoexpres.controller;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pra.lue11.empleoexpres.dto.CandidateStudyDTO;
import pra.lue11.empleoexpres.dto.JobHistoryDTO;
import pra.lue11.empleoexpres.model.User;
import pra.lue11.empleoexpres.model.enums.UserRole;
import pra.lue11.empleoexpres.service.PersonService;
import pra.lue11.empleoexpres.service.StudyService;
import pra.lue11.empleoexpres.service.UserService;

/**
 * @author luE11 on 18/07/23
 */
@Controller
@AllArgsConstructor
public class HomeController {

    private final String PROFILE_TEMPLATE = "user/my-profile";
    private final String HOME_TEMPLATE = "user/home";

    private UserService userService;
    private StudyService studyService;

    @GetMapping(value = "/home")
    public String showHomeScreen(Authentication authentication, Model model) {
        model.addAttribute("user", getUserFromAuth(authentication));
        return HOME_TEMPLATE;
    }

    @GetMapping(value = "/profile")
    public String showSelfProfile(Authentication authentication, Model model) {
        User self = getUserFromAuth(authentication);
        model.addAttribute("user", self);
        if(self.isPublisher())
            model.addAttribute("publisher", self.getPublisher());
        else{
            model.addAttribute("candidate", self.getPerson());
            model.addAttribute("newJhistory", new JobHistoryDTO());
            model.addAttribute("candidateStudy", new CandidateStudyDTO());
            model.addAttribute("studies", studyService.getAllStudies());
        }
        return PROFILE_TEMPLATE;
    }

    private User getUserFromAuth(Authentication authentication){
        return userService.findUserByEmail(authentication.getName()).orElseThrow(() -> new EntityNotFoundException("Logged user not found"));
    }

}

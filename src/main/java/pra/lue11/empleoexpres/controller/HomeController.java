package pra.lue11.empleoexpres.controller;

import jakarta.annotation.Nullable;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pra.lue11.empleoexpres.dto.CandidateStudyDTO;
import pra.lue11.empleoexpres.dto.JobHistoryDTO;
import pra.lue11.empleoexpres.model.Publisher;
import pra.lue11.empleoexpres.model.User;
import pra.lue11.empleoexpres.model.enums.UserRole;
import pra.lue11.empleoexpres.model.specifications.JobSpecification;
import pra.lue11.empleoexpres.service.PersonService;
import pra.lue11.empleoexpres.service.StudyService;
import pra.lue11.empleoexpres.service.UserService;

/**
 * @author luE11 on 18/07/23
 */
@Controller
@AllArgsConstructor
public class HomeController {

    private final String PROFILE_TEMPLATE = "user/profile";
    private final String HOME_TEMPLATE = "user/home";

    private UserService userService;
    private StudyService studyService;

    @GetMapping(value = "/home")
    public String showHomeScreen(Authentication authentication, Model model) {
        model.addAttribute("user", getUserFromAuth(authentication));
        return HOME_TEMPLATE;
    }

    @GetMapping(value = "/profile")
    public String showProfile(@RequestParam("id") @Nullable Integer userId,
            Authentication authentication, Model model) {
        User user = userId==null ? getUserFromAuth(authentication) : userService.findById(userId);
        model.addAttribute("user", user);
        boolean isSelfProfile = authentication.getName().compareTo(user.getEmail())==0;
        model.addAttribute("isSelfProfile", isSelfProfile);
        if(user.isPublisher()) {
            model.addAttribute("publisher", user.getPublisher());
            if (!isSelfProfile) {
                JobSpecification spec = new JobSpecification();
                Publisher p = user.getPublisher();
                spec.setPublisherId(p.getId());
                model.addAttribute("publisherJobFilter", spec);
            }
        }else{
            model.addAttribute("candidate", user.getPerson());
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

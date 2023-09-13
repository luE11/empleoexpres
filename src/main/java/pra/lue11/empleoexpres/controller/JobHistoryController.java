package pra.lue11.empleoexpres.controller;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pra.lue11.empleoexpres.dto.CandidateStudyDTO;
import pra.lue11.empleoexpres.dto.JobHistoryDTO;
import pra.lue11.empleoexpres.model.User;
import pra.lue11.empleoexpres.service.JobService;
import pra.lue11.empleoexpres.service.StudyService;
import pra.lue11.empleoexpres.service.UserService;

/**
 * @author luE11 on 11/09/23
 */
@AllArgsConstructor
@Controller
public class JobHistoryController {

    private final String PROFILE_TEMPLATE = "user/my-profile";

    private UserService userService;
    private JobService jobService;
    private StudyService studyService;

    @PreAuthorize("hasAnyAuthority('CANDIDATE', 'ADMIN')")
    @PostMapping(value = "/job-history")
    public String insertJobHistory(@Valid @ModelAttribute(name = "newJhistory") JobHistoryDTO newJhistory, BindingResult result, Model model, Authentication authentication, RedirectAttributes redirectAttributes) {
        User self = getUserFromAuth(authentication);
        model.addAttribute("user", self);
        model.addAttribute("candidate", self.getPerson());
        model.addAttribute("candidateStudy", new CandidateStudyDTO());
        if(result.hasErrors()){
            model.addAttribute("jobOffcanvas", "");
            model.addAttribute("newJhistory", newJhistory);
            return PROFILE_TEMPLATE;
        }else{
            jobService.insertJobHistory(newJhistory, self.getPerson());
            model.addAttribute("newJhistory", new JobHistoryDTO());
            redirectAttributes.addFlashAttribute("successMessage", "Empleo insertado exitosamente");
        }
        return "redirect:/profile";
    }

    @PreAuthorize("hasAnyAuthority('CANDIDATE', 'ADMIN')")
    @DeleteMapping(value = "/job-history/{jobHistoryId}")
    public String deleteJobHistory(@PathVariable(name = "jobHistoryId") Integer jobHistoryId, Model model, Authentication authentication, RedirectAttributes redirectAttributes) {
        User self = getUserFromAuth(authentication);
        model.addAttribute("user", self);
        model.addAttribute("candidate", self.getPerson());
        jobService.deleteJobHistory(jobHistoryId, self.getPerson());
        model.addAttribute("newJhistory", new JobHistoryDTO());
        model.addAttribute("candidateStudy", new CandidateStudyDTO());
        model.addAttribute("studies", studyService.getAllStudies());
        redirectAttributes.addFlashAttribute("successMessage", "Empleo eliminado exitosamente");
        return "redirect:/profile";
    }

    private User getUserFromAuth(Authentication authentication) {
        return userService.findUserByEmail(authentication.getName()).orElseThrow(() -> new EntityNotFoundException("Logged user not found"));
    }
}

package pra.lue11.empleoexpres.controller;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pra.lue11.empleoexpres.dto.CandidateStudyDTO;
import pra.lue11.empleoexpres.dto.JobHistoryDTO;
import pra.lue11.empleoexpres.model.User;
import pra.lue11.empleoexpres.service.StudyService;
import pra.lue11.empleoexpres.service.UserService;

/**
 * @author luE11 on 14/08/23
 */
@Controller
@RequestMapping(value = "/study")
@AllArgsConstructor
public class StudyController {

    private final String PROFILE_TEMPLATE = "user/my-profile";

    private UserService userService;
    private StudyService studyService;

    @PostMapping
    public String insert(@Valid @ModelAttribute(name = "candidateStudy") CandidateStudyDTO candidateStudy,
            BindingResult result, Model model, Authentication authentication, RedirectAttributes redirectAttributes){
        User self = getUserFromAuth(authentication);
        model.addAttribute("user", self);
        model.addAttribute("candidate", self.getPerson());
        model.addAttribute("newJhistory", new JobHistoryDTO());
        model.addAttribute("studies", studyService.getAllStudies());
        if(result.hasErrors()){
            model.addAttribute("studyOffcanvas", "");
            model.addAttribute("candidateStudy", candidateStudy);
            return PROFILE_TEMPLATE;
        }else{
            studyService.insert(candidateStudy, self.getPerson());
            model.addAttribute("candidateStudy", new CandidateStudyDTO());
            redirectAttributes.addFlashAttribute("successMessage", "Estudio insertado exitosamente");
        }
        return "redirect:/profile";
    }

    @PreAuthorize("hasAnyAuthority('CANDIDATE', 'ADMIN')")
    @DeleteMapping(value = "/{candidateStudyId}")
    public String deleteCandidateStudy(@PathVariable(name = "candidateStudyId") Integer candidateStudyId, Model model, Authentication authentication, RedirectAttributes redirectAttributes) {
        User self = getUserFromAuth(authentication);
        model.addAttribute("user", self);
        model.addAttribute("candidate", self.getPerson());
        studyService.deleteCandidateStudy(candidateStudyId, self.getPerson());
        model.addAttribute("newJhistory", new JobHistoryDTO());
        model.addAttribute("candidateStudy", new CandidateStudyDTO());
        model.addAttribute("studies", studyService.getAllStudies());
        redirectAttributes.addFlashAttribute("successMessage", "Estudio eliminado exitosamente");
        return "redirect:/profile";
    }

    private User getUserFromAuth(Authentication authentication){
        return userService.findUserByEmail(authentication.getName()).orElseThrow(() -> new EntityNotFoundException("Logged user not found"));
    }

}

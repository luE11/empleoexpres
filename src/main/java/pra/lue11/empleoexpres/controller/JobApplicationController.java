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
import pra.lue11.empleoexpres.dto.CandidateInsertApplicationDTO;
import pra.lue11.empleoexpres.dto.PublisherUpdateApplicationDTO;
import pra.lue11.empleoexpres.model.Job;
import pra.lue11.empleoexpres.model.User;
import pra.lue11.empleoexpres.service.*;

/**
 * @author luE11 on 11/09/23
 */

@AllArgsConstructor
@Controller
public class JobApplicationController {

    private final String JOB_CANDIDATES_PAGE = "jobs/applications/job-candidates";
    private final String APPLICATION_DETAILS_PAGE = "jobs/applications/details";
    private final String APPLICATION_APPLY_PAGE = "jobs/applications/apply";

    private UserService userService;
    private JobService jobService;

    @PreAuthorize("hasAuthority('CANDIDATE')")
    @GetMapping("/job/apply/{jid}")
    public String getCandidateApplicationPage(@PathVariable(name = "jid") Integer jobId,
                                              Model model, Authentication authentication){
        User self = getUserFromAuth(authentication);
        Job job = jobService.getJobById(jobId);
        model.addAttribute("user", self);
        model.addAttribute("job", job);
        if(jobService.isJobAppliedByCandidate(self.getPerson().getId(), jobId))
            model.addAttribute("appDetails", jobService.getApplicationDetails(jobId, self.getPerson().getId()));
        model.addAttribute("applyDTO", jobService.getCandidateApplyDTO(jobId, self.getPerson().getId()));
        return APPLICATION_APPLY_PAGE;
    }

    @PreAuthorize("hasAuthority('CANDIDATE')")
    @PutMapping("/job/{jid}/apply")
    public String insertCandidateApplication(@PathVariable(name = "jid") Integer jobId,
                                             @Valid @ModelAttribute(name = "applyDTO") CandidateInsertApplicationDTO applyDTO,
                                             BindingResult result, Authentication authentication, Model model){
        if(!result.hasFieldErrors()){
            User self = getUserFromAuth(authentication);
            jobService.insertCandidateApplication(jobId, self.getPerson().getId(), applyDTO);
            return "redirect:/job/apply/"+jobId;
        }else {
            User self = getUserFromAuth(authentication);
            Job job = jobService.getJobById(jobId);
            model.addAttribute("user", self);
            model.addAttribute("job", job);
            if(jobService.isJobAppliedByCandidate(self.getPerson().getId(), jobId))
                model.addAttribute("appDetails", jobService.getApplicationDetails(jobId, self.getPerson().getId()));
            model.addAttribute("applyDTO", applyDTO);
            return APPLICATION_APPLY_PAGE;
        }
    }

    @PreAuthorize("hasAuthority('PUBLISHER')")
    @GetMapping("/job/{jid}/application")
    public String showJobApplications(@RequestParam(value = "p", required = false) Integer page,
                                      @PathVariable(name = "jid") Integer jobId,
                                      Model model, Authentication authentication){
        User self = getUserFromAuth(authentication);
        Job job = jobService.getJobById(jobId);
        if(!self.equals(job.getPublisher().getUser()))
            return "redirect:/search";
        model.addAttribute("user", self);
        model.addAttribute("job", job);
        model.addAttribute("applications", jobService.getJobApplications(jobId, page));
        return JOB_CANDIDATES_PAGE;
    }

    @PreAuthorize("hasAuthority('PUBLISHER')")
    @GetMapping("/job/{jid}/application/{cid}")
    public String showJobApplicationDetails(@PathVariable(name = "jid") Integer jobId,
                                            @PathVariable(name = "cid") Integer candidateId,
                                            Model model, Authentication authentication){
        User self = getUserFromAuth(authentication);
        Job job = jobService.getJobById(jobId);
        if(!self.equals(job.getPublisher().getUser()))
            return "redirect:/search";
        model.addAttribute("user", self);
        model.addAttribute("job", job);
        model.addAttribute("appDetails", jobService.getApplicationDetails(jobId, candidateId));
        model.addAttribute("applicationDTO", jobService.getPublisherApplicationDTO(jobId, candidateId));
        return APPLICATION_DETAILS_PAGE;
    }

    @PreAuthorize("hasAuthority('PUBLISHER')")
    @PostMapping("/job/{jid}/application/{cid}")
    public String insertJobApplicationDetails(@PathVariable(name = "jid") Integer jobId,
                                              @PathVariable(name = "cid") Integer candidateId,
                                              @ModelAttribute(name = "filter") @Valid PublisherUpdateApplicationDTO applicationDTO,
                                              Model model, Authentication authentication){
        User self = getUserFromAuth(authentication);
        Job job = jobService.getJobById(jobId);
        if(!self.equals(job.getPublisher().getUser()))
            return "redirect:/search";
        jobService.updatePublisherApplication(jobId, candidateId, applicationDTO);
        return "redirect:/job/"+jobId+"/application";
    }


    @PreAuthorize("hasAuthority('CANDIDATE')")
    @DeleteMapping("/job-application")
    public String deleteJobApplication(@RequestParam(value = "jid") Integer jobId,
                                       @RequestParam(value = "cid") Integer candidateId,
                                       RedirectAttributes redirectAttributes){
        jobService.deleteJobApplication(jobId, candidateId);
        redirectAttributes.addFlashAttribute("flashMessage", "Aplicación eliminada exitosamente!");
        return "redirect:/my-jobs";
    }

    private User getUserFromAuth(Authentication authentication) {
        return userService.findUserByEmail(authentication.getName()).orElseThrow(() -> new EntityNotFoundException("Logged user not found"));
    }
}
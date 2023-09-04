package pra.lue11.empleoexpres.controller;

import jakarta.annotation.Nullable;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import lombok.AllArgsConstructor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pra.lue11.empleoexpres.dto.CandidateStudyDTO;
import pra.lue11.empleoexpres.dto.JobHistoryDTO;
import pra.lue11.empleoexpres.model.*;
import pra.lue11.empleoexpres.model.specifications.JobSpecification;
import pra.lue11.empleoexpres.service.*;

import java.util.List;
import java.util.Objects;

/**
 * @author luE11 on 1/08/23
 */
@Controller
@AllArgsConstructor
public class JobController {
    private static final String SEARCH_JOB_PAGE = "jobs/search.html";
    private final String PROFILE_TEMPLATE = "user/my-profile";
    private final String CONSULT_JOB_TEMPLATE = "jobs/consult.html";
    private final String MY_JOBS_PAGE = "jobs/my-jobs";
    private final String JOB_CANDIDATES_PAGE = "jobs/applications/job-candidates";
    private final String APPLICATION_DETAILS_PAGE = "jobs/applications/details";

    private UserService userService;
    private JobService jobService;
    private StudyService studyService;
    private PlaceService placeService;
    private PublisherService publisherService;

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

    @RequestMapping(value = "/search", method = { RequestMethod.GET, RequestMethod.POST })
    public String showSearchJobPage(@ModelAttribute(name = "filter") @Nullable JobSpecification specification,
                                    @RequestParam(value = "p", required = false) Integer page,
                                    @RequestParam(value = "clearFilter", required = false) Boolean clearFilter,
                                    Authentication authentication, Model model, HttpSession session, BindingResult result){
        if(authentication==null)
            return "redirect:/home";
        User self = getUserFromAuth(authentication);
        JobSpecification jobSpec = null;
        if(specification!=null && !specification.isSalaryValid()){
            result.rejectValue("salaryMax", "", "El salario máximo no puede ser menor al mínimo especificado.");
            jobSpec = specification;
            jobSpec.resetSalary();
        }else {
            if(clearFilter!=null && clearFilter)
                session.removeAttribute("lastJobFilter");
            if(specification!=null && specification.isEmpty()
                    && session.getAttribute("lastJobFilter")!=null){
                jobSpec = (JobSpecification) session.getAttribute("lastJobFilter");
            }else {
                jobSpec = !specification.isEmpty() ? specification : new JobSpecification();
                session.setAttribute("lastJobFilter", jobSpec);
            }
        }
        model.addAttribute("jobList", jobService.getAllJobs(page, jobSpec));
        model.addAttribute("user", self);
        model.addAttribute("filter", jobSpec);
        model.addAttribute("places", getAllPlaces());
        model.addAttribute("professions", getAllProfessions());
        model.addAttribute("publishers", getAllPublishers());
        return SEARCH_JOB_PAGE;
    }

    @GetMapping("/job/{id}")
    public String consultJobOffer(@PathVariable(value = "id") Integer id,
                                  Authentication authentication, Model model){
        User self = getUserFromAuth(authentication);
        Job consultedJob = jobService.getById(id);
        model.addAttribute("user", self);
        model.addAttribute("job", consultedJob);
        if(self.isCandidate())
            model.addAttribute("applied", jobService.isJobAppliedByCandidate(self.getPerson().getId(), id));
        return CONSULT_JOB_TEMPLATE;
    }

    @GetMapping("/my-jobs")
    public String showMyJobs(@RequestParam(value = "p", required = false) Integer page,
                             Authentication authentication, Model model){
        User self = getUserFromAuth(authentication);
        if(self.isPublisher())
            model.addAttribute("myJobs", jobService.getPublisherJobs(self.getPublisher().getId(), page));
        else
            model.addAttribute("myJobs", jobService.getCandidateJobs(self.getPerson().getId(), page));
        model.addAttribute("user", self);
        return MY_JOBS_PAGE;
    }

    @PreAuthorize("hasAuthority('PUBLISHER')")
    @GetMapping("/job/{jid}/application")
    public String showJobApplications(@RequestParam(value = "p", required = false) Integer page,
                                      @PathVariable(name = "jid") Integer jobId,
                                      Model model, Authentication authentication){
        User self = getUserFromAuth(authentication);
        Job job = jobService.getById(jobId);
        if(!self.equals(job.getPublisher().getUser()))
            return "redirect:/search";
        model.addAttribute("user", self);
        model.addAttribute("job", job);
        model.addAttribute("applications", jobService.getJobApplications(jobId, page));
        return JOB_CANDIDATES_PAGE;
    }

    @PreAuthorize("hasAuthority('PUBLISHER')")
    @GetMapping("/job/{jid}/application/{cid}")
    public String showJobApplicationDetails(@RequestParam(value = "p", required = false) Integer page,
                                      @PathVariable(name = "jid") Integer jobId,
                                        @PathVariable(name = "cid") Integer candidateId,
                                      Model model, Authentication authentication){
        User self = getUserFromAuth(authentication);
        Job job = jobService.getById(jobId);
        if(!self.equals(job.getPublisher().getUser()))
            return "redirect:/search";
        model.addAttribute("user", self);
        model.addAttribute("job", job);
        model.addAttribute("appDetails", jobService.getApplicationDetails(jobId, page));
        return APPLICATION_DETAILS_PAGE;
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

    @PreAuthorize("hasAnyAuthority('PUBLISHER', 'ADMIN')")
    @DeleteMapping("/job")
    public String deleteJob(@RequestParam(value = "id") Integer jobId,
                                       RedirectAttributes redirectAttributes){
        jobService.deleteJob(jobId);
        redirectAttributes.addFlashAttribute("flashMessage", "Oferta eliminada exitosamente!");
        return "redirect:/my-jobs";
    }

    // TODO: candidates list for publishers

    // GET "/job/apply/{id}"

    private Person getPersonFromAuth(Authentication authentication){
        User user = userService.findUserByEmail(authentication.getName()).orElseThrow(() -> new EntityNotFoundException("Logged user not found"));
        return user.getPerson();
    }

    private User getUserFromAuth(Authentication authentication) {
        return userService.findUserByEmail(authentication.getName()).orElseThrow(() -> new EntityNotFoundException("Logged user not found"));
    }

    private List<Place> getAllPlaces(){
        return placeService.getAllPlaces();
    }

    private List<Study> getAllProfessions(){
        return studyService.getAllStudies();
    }

    private List<Publisher> getAllPublishers(){
        return publisherService.getAllPublishers();
    }
}
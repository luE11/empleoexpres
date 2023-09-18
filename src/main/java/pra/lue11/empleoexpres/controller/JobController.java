package pra.lue11.empleoexpres.controller;

import jakarta.annotation.Nullable;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
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
import pra.lue11.empleoexpres.dto.*;
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
    private final String CONSULT_JOB_TEMPLATE = "jobs/consult.html";
    private final String MY_JOBS_PAGE = "jobs/my-jobs";

    private final String CREATE_JOB_TEMPLATE = "jobs/create.html";
    private final String UPDATE_JOB_TEMPLATE = "jobs/update.html";

    private UserService userService;
    private JobService jobService;
    private StudyService studyService;
    private PlaceService placeService;
    private PublisherService publisherService;

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

    @PreAuthorize("hasAnyAuthority('PUBLISHER', 'ADMIN')")
    @DeleteMapping("/job")
    public String deleteJob(@RequestParam(value = "id") Integer jobId,
                                       RedirectAttributes redirectAttributes){
        jobService.deleteJob(jobId);
        redirectAttributes.addFlashAttribute("flashMessage", "Oferta eliminada exitosamente!");
        return "redirect:/my-jobs";
    }

    @PreAuthorize("hasAuthority('PUBLISHER')")
    @GetMapping("/job")
    public String showCreateJobPage(Authentication authentication, Model model){
        User self = getUserFromAuth(authentication);
        model.addAttribute("user", self);
        model.addAttribute("jobDTO", new JobDTO());
        model.addAttribute("studies", getAllProfessions());
        model.addAttribute("places", getAllPlaces());
        return CREATE_JOB_TEMPLATE;
    }

    @PreAuthorize("hasAuthority('PUBLISHER')")
    @PostMapping("/job")
    public String insertJob(@ModelAttribute(name = "jobDTO") @Valid JobDTO jobDTO,
                            BindingResult result, Model model, Authentication authentication, RedirectAttributes redirectAttributes){
        User self = getUserFromAuth(authentication);
        if(result.hasErrors()){
            model.addAttribute("user", self);
            model.addAttribute("jobDTO", jobDTO);
            model.addAttribute("studies", getAllProfessions());
            model.addAttribute("places", getAllPlaces());
            return CREATE_JOB_TEMPLATE;
        }else {
            jobService.insertJob(jobDTO, self.getPublisher());
            redirectAttributes.addFlashAttribute("successMessage", "Empleo insertado exitosamente");
            return "redirect:/my-jobs";
        }
    }

    @PreAuthorize("hasAuthority('PUBLISHER')")
    @GetMapping("/job/{id}/update")
    public String showUpdateJobPage(@PathVariable(value = "id") Integer id,
                                    Authentication authentication, Model model){
        User self = getUserFromAuth(authentication);
        Job job = jobService.getJobById(id);
        if(!self.getPublisher().equals(job.getPublisher()))
            return "redirect:/my-jobs";
        model.addAttribute("user", self);
        model.addAttribute("jobId", id);
        model.addAttribute("jobDTO", job.getAsDto());
        model.addAttribute("studies", getAllProfessions());
        model.addAttribute("places", getAllPlaces());
        return UPDATE_JOB_TEMPLATE;
    }

    @PreAuthorize("hasAuthority('PUBLISHER')")
    @PutMapping("/job/{id}")
    public String updateJob(@ModelAttribute(name = "jobDTO") @Valid JobDTO jobDTO,
                            @PathVariable(value = "id") Integer id,
                            BindingResult result, Model model, Authentication authentication, RedirectAttributes redirectAttributes){
        User self = getUserFromAuth(authentication);
        if(result.hasErrors()){
            model.addAttribute("user", self);
            model.addAttribute("jobDTO", jobDTO);
            model.addAttribute("studies", getAllProfessions());
            model.addAttribute("places", getAllPlaces());
            return UPDATE_JOB_TEMPLATE;
        }else {
            jobService.updateJob(id, jobDTO, self.getPublisher());
            redirectAttributes.addFlashAttribute("successMessage", "Empleo actualizado exitosamente");
            return "redirect:/my-jobs";
        }
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

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }
}
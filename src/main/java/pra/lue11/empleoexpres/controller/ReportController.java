package pra.lue11.empleoexpres.controller;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import net.sf.jasperreports.engine.JRException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pra.lue11.empleoexpres.model.Job;
import pra.lue11.empleoexpres.model.User;
import pra.lue11.empleoexpres.service.JobService;
import pra.lue11.empleoexpres.service.ReportService;
import pra.lue11.empleoexpres.service.UserService;

import java.io.FileNotFoundException;
import java.sql.SQLException;

/**
 * @author luE11 on 19/09/23
 */
@AllArgsConstructor
@Controller
public class ReportController {

    private UserService userService;
    private JobService jobService;
    private ReportService reportService;

    @PreAuthorize("hasAuthority('PUBLISHER')")
    @GetMapping("/reports/job/{id}")
    public ResponseEntity<byte[]> getJobCandidatesReport(@PathVariable("id") Integer jobId,
                                                         Authentication authentication){
        User self = getUserFromAuth(authentication);
        Job job = jobService.getJobById(jobId);
        if(!self.getPublisher().equals(job.getPublisher()))
            return new ResponseEntity<byte[]>(HttpStatus.UNAUTHORIZED);
        try {
            String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
            byte[] report = reportService.generateJobReport(job, baseUrl);
            HttpHeaders headers = new HttpHeaders();
            //set the PDF format
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("filename", "job_candidates_report.pdf");
            return new ResponseEntity<byte[]>(report, headers, HttpStatus.OK);
        } catch (JRException e) {
            System.err.println(e.getMessage());
            return new ResponseEntity<byte[]>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/reports/publishers")
    public ResponseEntity<byte[]> getAllPublishersReport(){
        try {
            String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
            byte[] report = reportService.generateAllPublishersReport(baseUrl);
            HttpHeaders headers = new HttpHeaders();
            //set the PDF format
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("filename", "all_publishers_report.pdf");
            return new ResponseEntity<byte[]>(report, headers, HttpStatus.OK);
        } catch (JRException | SQLException e) {
            System.err.println(e.getMessage());
            return new ResponseEntity<byte[]>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private User getUserFromAuth(Authentication authentication) {
        return userService.findUserByEmail(authentication.getName()).orElseThrow(() -> new EntityNotFoundException("Logged user not found"));
    }
}

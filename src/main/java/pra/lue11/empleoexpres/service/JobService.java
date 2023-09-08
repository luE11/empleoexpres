package pra.lue11.empleoexpres.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import pra.lue11.empleoexpres.dto.CandidateInsertApplicationDTO;
import pra.lue11.empleoexpres.dto.JobHistoryDTO;
import pra.lue11.empleoexpres.dto.PublisherUpdateApplicationDTO;
import pra.lue11.empleoexpres.model.*;
import pra.lue11.empleoexpres.model.enums.JobApplicationState;
import pra.lue11.empleoexpres.model.enums.JobState;
import pra.lue11.empleoexpres.model.inmutable.ApplicationView;
import pra.lue11.empleoexpres.model.inmutable.CandidateAppliedJobView;
import pra.lue11.empleoexpres.model.specifications.JobSpecification;
import pra.lue11.empleoexpres.repository.JobHasCandidateRepository;
import pra.lue11.empleoexpres.repository.JobHistoryRepository;
import pra.lue11.empleoexpres.repository.JobRepository;
import pra.lue11.empleoexpres.repository.view.ApplicationRepository;
import pra.lue11.empleoexpres.repository.view.CandidateAppliedJobRepository;

import java.util.List;

/**
 * @author luE11 on 1/08/23
 */
@Service
@AllArgsConstructor
public class JobService {

    private static final int PAGE_SIZE = 10;
    private static final String JOB_PUB_DATE_NAME = "pubDate";

    private JobHistoryRepository jobHistoryRepository;
    private JobRepository jobRepository;
    private CandidateAppliedJobRepository candidateAppliedJobRepository;
    private JobHasCandidateRepository jobHasCandidateRepository;
    private ApplicationRepository applicationRepository;
    private PersonService personService;

    public void insertJobHistory(JobHistoryDTO jobHistoryDTO, Person candidate){
        JobHistory jobHistory = jobHistoryDTO.generateJobHistory();
        jobHistory.setCandidate(candidate);
        jobHistoryRepository.save(jobHistory);
    }

    public List<JobHistory> getJobHistoriesByCandidate(Person person){
        return jobHistoryRepository.findAllByCandidate(person);
    }

    public void deleteJobHistory(Integer id, Person person){
        JobHistory jh = jobHistoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Job history of id " + id + " was not found"));
        if(!jh.getCandidate().equals(person))
            throw new AccessDeniedException("User can just delete its own history jobs");
        jobHistoryRepository.deleteById(id);
    }

    public Page<Job> getAllJobs(Integer page, JobSpecification spec){
        int currPage = page != null ? page : 0;
        return jobRepository.findAll(spec,
                PageRequest.ofSize(PAGE_SIZE).withPage(currPage)
                        .withSort(Sort.by(JOB_PUB_DATE_NAME).descending()));
    }

    public Job getById(int id){
        return jobRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Job with id " + id + " was not found"));
    }

    public Page<Job> getPublisherJobs(Integer publisherId, Integer page){
        int currPage = page != null ? page : 0;
        return jobRepository.findAllByPublisherId(publisherId,
                PageRequest.ofSize(PAGE_SIZE).withPage(currPage));
    }

    public Page<CandidateAppliedJobView> getCandidateJobs(Integer personId, Integer page) {
        int currPage = page != null ? page : 0;
        return candidateAppliedJobRepository.findAllByPersonId(personId,
                PageRequest.ofSize(PAGE_SIZE).withPage(currPage));
    }

    public Page<ApplicationView> getJobApplications(Integer jobId, Integer page){
        int currPage = page != null ? page : 0;
        return applicationRepository.findAllByJobId(jobId,
                PageRequest.ofSize(PAGE_SIZE).withPage(currPage).withSort(Sort.by(Sort.Order.desc("updatedAt"))));
    }

    public void deleteJobApplication(int jobId, int candidateId){
        jobHasCandidateRepository.deleteById(new JobCandidateId(candidateId, jobId));
    }

    public void deleteJob(int jobId){
        jobRepository.findById(jobId)
                .map(job -> {
                    job.setSoftDeleted(true);
                    job.setState(JobState.CLOSED);
                    return jobRepository.save(job);
                });
    }

    public JobHasCandidate getApplicationDetails(int jobId, int personId){
        return jobHasCandidateRepository.findById(new JobCandidateId(personId, jobId))
                .orElseThrow(() -> new EntityNotFoundException("Application with job id " + jobId +
                        " and person id "+personId+" was not found"));
    }

    public void updatePublisherApplication(int jobId, int personId, PublisherUpdateApplicationDTO applicationDTO){
        JobHasCandidate jobApplication = getApplicationDetails(jobId, personId);
        jobApplication.setState(JobApplicationState.valueOf(applicationDTO.getState()));
        jobApplication.setCompanyObservations(applicationDTO.getCompanyObservations());
        jobHasCandidateRepository.save(jobApplication);
    }

    public void insertCandidateApplication(int jobId, int personId, CandidateInsertApplicationDTO applicationDTO){
        if(jobHasCandidateRepository.existsById(new JobCandidateId(personId, jobId))){
            jobHasCandidateRepository.findById(new JobCandidateId(personId, jobId))
                    .map(jobHasCandidate -> {
                        jobHasCandidate.setCandidateComment(applicationDTO.getCandidateComment());
                        jobHasCandidate.setCvUrl(applicationDTO.getCvUrl());
                        return jobHasCandidateRepository.save(jobHasCandidate);
                    });
        }else {
            Job job = getJobById(jobId);
            Person person = getPersonById(personId);
            JobHasCandidate jobApplication = applicationDTO.toApplication(job, person);
            jobHasCandidateRepository.save(jobApplication);
        }
    }

    public boolean isJobAppliedByCandidate(int candidateId, int jobId){
        return jobHasCandidateRepository.existsById(new JobCandidateId(candidateId, jobId));
    }

    public Job getJobById(int id){
        return jobRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Job with id " +id+" was not found"));
    }

    public Person getPersonById(int id){
        return personService.getPersonById(id);
    }

    public PublisherUpdateApplicationDTO getPublisherApplicationDTO(Integer jobId, Integer candidateId) {
        JobHasCandidate jobHasCandidate = getApplicationDetails(jobId, candidateId);
        return new PublisherUpdateApplicationDTO(jobHasCandidate.getState().toString(), jobHasCandidate.getCompanyObservations());
    }

    public CandidateInsertApplicationDTO getCandidateApplyDTO(int jobId, int candidateId){
        if(isJobAppliedByCandidate(candidateId, jobId)){
            JobHasCandidate jobHasCandidate = getApplicationDetails(jobId, candidateId);
            return new CandidateInsertApplicationDTO(jobHasCandidate.getCandidateComment(), jobHasCandidate.getCvUrl());
        }
        return new CandidateInsertApplicationDTO();
    }
}

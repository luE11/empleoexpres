package pra.lue11.empleoexpres.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import pra.lue11.empleoexpres.dto.JobHistoryDTO;
import pra.lue11.empleoexpres.model.Job;
import pra.lue11.empleoexpres.model.JobCandidateId;
import pra.lue11.empleoexpres.model.JobHistory;
import pra.lue11.empleoexpres.model.Person;
import pra.lue11.empleoexpres.model.inmutable.CandidateAppliedJobView;
import pra.lue11.empleoexpres.model.specifications.JobSpecification;
import pra.lue11.empleoexpres.repository.JobHasCandidateRepository;
import pra.lue11.empleoexpres.repository.JobHistoryRepository;
import pra.lue11.empleoexpres.repository.JobRepository;
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
                .orElseThrow(() -> new EntityNotFoundException("Job history of id " + id + " has not found"));
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
                .orElseThrow(() -> new EntityNotFoundException("Job with id " + id + " has not found"));
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

    public boolean isJobAppliedByCandidate(int candidateId, int jobId){
        return jobHasCandidateRepository.existsById(new JobCandidateId(candidateId, jobId));
    }

    public void deleteJobApplication(int jobId, int candidateId){
        jobHasCandidateRepository.deleteById(new JobCandidateId(candidateId, jobId));
    }

    public void deleteJob(int jobId){
        jobRepository.findById(jobId)
                .map(job -> {
                    job.setSoftDeleted(true);
                    return jobRepository.save(job);
                });
    }
}

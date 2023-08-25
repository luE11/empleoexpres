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
import pra.lue11.empleoexpres.model.JobHistory;
import pra.lue11.empleoexpres.model.Person;
import pra.lue11.empleoexpres.model.specifications.JobSpecification;
import pra.lue11.empleoexpres.repository.JobHistoryRepository;
import pra.lue11.empleoexpres.repository.JobRepository;

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

}

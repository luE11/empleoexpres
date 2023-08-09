package pra.lue11.empleoexpres.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import pra.lue11.empleoexpres.dto.JobHistoryDTO;
import pra.lue11.empleoexpres.model.JobHistory;
import pra.lue11.empleoexpres.model.Person;
import pra.lue11.empleoexpres.repository.JobHistoryRepository;

import java.util.List;

/**
 * @author luE11 on 1/08/23
 */
@Service
@AllArgsConstructor
public class JobService {
    private JobHistoryRepository jobHistoryRepository;

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

}

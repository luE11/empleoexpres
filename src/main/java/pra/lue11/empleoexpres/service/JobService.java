package pra.lue11.empleoexpres.service;

import lombok.AllArgsConstructor;
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

}

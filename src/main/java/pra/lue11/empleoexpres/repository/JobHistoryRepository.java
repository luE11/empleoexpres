package pra.lue11.empleoexpres.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import pra.lue11.empleoexpres.model.JobHistory;
import pra.lue11.empleoexpres.model.Person;

import java.util.List;

/**
 * @author luE11 on 1/08/23
 */
public interface JobHistoryRepository extends JpaRepository<JobHistory, Integer> {
    List<JobHistory> findAllByCandidate(@Param("candidate") Person candidate);
}

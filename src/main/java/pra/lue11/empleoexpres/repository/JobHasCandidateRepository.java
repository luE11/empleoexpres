package pra.lue11.empleoexpres.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pra.lue11.empleoexpres.model.JobCandidateId;
import pra.lue11.empleoexpres.model.JobHasCandidate;

/**
 * @author luE11 on 17/08/23
 */
public interface JobHasCandidateRepository extends JpaRepository<JobHasCandidate, JobCandidateId> {

}

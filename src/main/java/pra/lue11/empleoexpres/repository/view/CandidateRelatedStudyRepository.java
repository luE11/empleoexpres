package pra.lue11.empleoexpres.repository.view;

import org.springframework.data.jpa.repository.JpaRepository;
import pra.lue11.empleoexpres.model.inmutable.CandidateRelatedStudyView;

/**
 * @author luE11 on 11/08/23
 */
public interface CandidateRelatedStudyRepository extends JpaRepository<CandidateRelatedStudyView, Integer> {
}

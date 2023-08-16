package pra.lue11.empleoexpres.repository.view;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import pra.lue11.empleoexpres.model.inmutable.CandidateRelatedStudyView;

import java.util.List;

/**
 * @author luE11 on 11/08/23
 */
public interface CandidateRelatedStudyRepository extends JpaRepository<CandidateRelatedStudyView, Integer> {

    List<CandidateRelatedStudyView> findAllByPersonId(@Param("personId") Integer personId);

}

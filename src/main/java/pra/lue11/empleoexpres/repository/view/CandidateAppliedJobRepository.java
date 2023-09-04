package pra.lue11.empleoexpres.repository.view;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import pra.lue11.empleoexpres.model.inmutable.CandidateAppliedJobView;

import java.util.List;

/**
 * @author luE11 on 30/08/23
 */
public interface CandidateAppliedJobRepository extends JpaRepository<CandidateAppliedJobView, Integer> {
    Page<CandidateAppliedJobView> findAllByPersonId(@Param("personId") Integer personId, Pageable page);
}

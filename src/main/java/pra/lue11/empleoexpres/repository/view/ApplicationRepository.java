package pra.lue11.empleoexpres.repository.view;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import pra.lue11.empleoexpres.model.inmutable.ApplicationView;

/**
 * @author luE11 on 30/08/23
 */
public interface ApplicationRepository extends JpaRepository<ApplicationView, Integer> {
    Page<ApplicationView> findAllByJobId(@Param("jobId") Integer jobId, Pageable page);

}

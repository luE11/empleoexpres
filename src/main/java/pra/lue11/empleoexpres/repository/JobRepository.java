package pra.lue11.empleoexpres.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;
import pra.lue11.empleoexpres.model.Job;

/**
 * @author luE11 on 17/08/23
 */
public interface JobRepository extends JpaRepository<Job, Integer>, JpaSpecificationExecutor<Job> {

    Page<Job> findAllByPublisherId(@Param("id") Integer id, Pageable pageable);
}

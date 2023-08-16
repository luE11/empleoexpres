package pra.lue11.empleoexpres.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import pra.lue11.empleoexpres.model.Study;

import java.util.List;

/**
 * @author luE11 on 11/08/23
 */
public interface StudyRepository extends JpaRepository<Study, Integer> {

    List<Study> findAllByCertificateName(@Param("certificateName") String certificateName);
}

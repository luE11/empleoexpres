package pra.lue11.empleoexpres.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pra.lue11.empleoexpres.model.PersonHasStudy;
import pra.lue11.empleoexpres.model.PersonStudyId;

/**
 * @author luE11 on 11/08/23
 */
public interface PersonHasStudyRepository extends JpaRepository<PersonHasStudy, PersonStudyId> {

}

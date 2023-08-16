package pra.lue11.empleoexpres.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import pra.lue11.empleoexpres.model.Person;
import pra.lue11.empleoexpres.model.PersonHasStudy;
import pra.lue11.empleoexpres.model.PersonStudyId;
import pra.lue11.empleoexpres.model.Study;

/**
 * @author luE11 on 11/08/23
 */
public interface PersonHasStudyRepository extends JpaRepository<PersonHasStudy, PersonStudyId> {

}

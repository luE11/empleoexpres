package pra.lue11.empleoexpres.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pra.lue11.empleoexpres.model.Person;

/**
 * @author luE11 on 21/07/23
 */
public interface PersonRepository extends JpaRepository<Person, Integer> {
}

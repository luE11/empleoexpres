package pra.lue11.empleoexpres.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import pra.lue11.empleoexpres.model.Place;

import java.util.Optional;

/**
 * @author luE11 on 21/07/23
 */
public interface PlaceRepository extends JpaRepository<Place, Integer> {

    Optional<Place> findByName(@Param("name") String name);
}

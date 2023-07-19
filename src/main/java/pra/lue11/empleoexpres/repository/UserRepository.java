package pra.lue11.empleoexpres.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import pra.lue11.empleoexpres.model.User;

import java.util.Optional;

/**
 * @author luE11 on 17/07/23
 */
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(@Param("email") String email);

    boolean existsByEmail(@Param("email") String email);
}

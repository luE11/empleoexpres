package pra.lue11.empleoexpres.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pra.lue11.empleoexpres.model.Company;

/**
 * @author luE11 on 21/07/23
 */
public interface CompanyRepository extends JpaRepository<Company, Integer> {
}

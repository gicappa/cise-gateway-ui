package eu.cise.console.repository;

import eu.cise.console.domain.CiseService;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CiseService entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CiseServiceRepository extends JpaRepository<CiseService, Long> {

}

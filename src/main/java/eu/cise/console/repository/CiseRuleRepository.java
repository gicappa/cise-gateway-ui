package eu.cise.console.repository;

import eu.cise.console.domain.CiseRule;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CiseRule entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CiseRuleRepository extends JpaRepository<CiseRule, Long> {

}

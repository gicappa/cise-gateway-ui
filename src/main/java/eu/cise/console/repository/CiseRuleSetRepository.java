package eu.cise.console.repository;

import eu.cise.console.domain.CiseRuleSet;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CiseRuleSet entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CiseRuleSetRepository extends JpaRepository<CiseRuleSet, Long> {

}

package eu.cise.console.repository;

import eu.cise.console.domain.CiseServiceProfile;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CiseServiceProfile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CiseServiceProfileRepository extends JpaRepository<CiseServiceProfile, Long> {

}

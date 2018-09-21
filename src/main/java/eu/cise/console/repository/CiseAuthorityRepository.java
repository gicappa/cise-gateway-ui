package eu.cise.console.repository;

import eu.cise.console.domain.CiseAuthority;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the CiseAuthority entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CiseAuthorityRepository extends JpaRepository<CiseAuthority, Long> {

    @Query("select cise_authority from CiseAuthority cise_authority where cise_authority.user.login = ?#{principal.username}")
    List<CiseAuthority> findByUserIsCurrentUser();

}

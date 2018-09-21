package eu.cise.console.service;

import eu.cise.console.service.dto.CiseAuthorityDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing CiseAuthority.
 */
public interface CiseAuthorityService {

    /**
     * Save a ciseAuthority.
     *
     * @param ciseAuthorityDTO the entity to save
     * @return the persisted entity
     */
    CiseAuthorityDTO save(CiseAuthorityDTO ciseAuthorityDTO);

    /**
     * Get all the ciseAuthorities.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<CiseAuthorityDTO> findAll(Pageable pageable);


    /**
     * Get the "id" ciseAuthority.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<CiseAuthorityDTO> findOne(Long id);

    /**
     * Delete the "id" ciseAuthority.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the ciseAuthority corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<CiseAuthorityDTO> search(String query, Pageable pageable);
}

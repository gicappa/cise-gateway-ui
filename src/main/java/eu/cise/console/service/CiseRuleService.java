package eu.cise.console.service;

import eu.cise.console.service.dto.CiseRuleDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing CiseRule.
 */
public interface CiseRuleService {

    /**
     * Save a ciseRule.
     *
     * @param ciseRuleDTO the entity to save
     * @return the persisted entity
     */
    CiseRuleDTO save(CiseRuleDTO ciseRuleDTO);

    /**
     * Get all the ciseRules.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<CiseRuleDTO> findAll(Pageable pageable);


    /**
     * Get the "id" ciseRule.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<CiseRuleDTO> findOne(Long id);

    /**
     * Delete the "id" ciseRule.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the ciseRule corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<CiseRuleDTO> search(String query, Pageable pageable);
}

package eu.cise.console.service;

import eu.cise.console.service.dto.CiseRuleSetDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing CiseRuleSet.
 */
public interface CiseRuleSetService {

    /**
     * Save a ciseRuleSet.
     *
     * @param ciseRuleSetDTO the entity to save
     * @return the persisted entity
     */
    CiseRuleSetDTO save(CiseRuleSetDTO ciseRuleSetDTO);

    /**
     * Get all the ciseRuleSets.
     *
     * @return the list of entities
     */
    List<CiseRuleSetDTO> findAll();
    /**
     * Get all the CiseRuleSetDTO where CiseService is null.
     *
     * @return the list of entities
     */
    List<CiseRuleSetDTO> findAllWhereCiseServiceIsNull();


    /**
     * Get the "id" ciseRuleSet.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<CiseRuleSetDTO> findOne(Long id);

    /**
     * Delete the "id" ciseRuleSet.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the ciseRuleSet corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<CiseRuleSetDTO> search(String query);
}

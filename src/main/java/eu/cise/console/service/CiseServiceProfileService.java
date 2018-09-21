package eu.cise.console.service;

import eu.cise.console.service.dto.CiseServiceProfileDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing CiseServiceProfile.
 */
public interface CiseServiceProfileService {

    /**
     * Save a ciseServiceProfile.
     *
     * @param ciseServiceProfileDTO the entity to save
     * @return the persisted entity
     */
    CiseServiceProfileDTO save(CiseServiceProfileDTO ciseServiceProfileDTO);

    /**
     * Get all the ciseServiceProfiles.
     *
     * @return the list of entities
     */
    List<CiseServiceProfileDTO> findAll();
    /**
     * Get all the CiseServiceProfileDTO where CiseRule is null.
     *
     * @return the list of entities
     */
    List<CiseServiceProfileDTO> findAllWhereCiseRuleIsNull();


    /**
     * Get the "id" ciseServiceProfile.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<CiseServiceProfileDTO> findOne(Long id);

    /**
     * Delete the "id" ciseServiceProfile.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the ciseServiceProfile corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<CiseServiceProfileDTO> search(String query);
}

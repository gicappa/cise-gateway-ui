package eu.cise.console.service;

import eu.cise.console.service.dto.CiseServiceDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing CiseService.
 */
public interface CiseServiceService {

    /**
     * Save a ciseService.
     *
     * @param ciseServiceDTO the entity to save
     * @return the persisted entity
     */
    CiseServiceDTO save(CiseServiceDTO ciseServiceDTO);

    /**
     * Get all the ciseServices.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<CiseServiceDTO> findAll(Pageable pageable);


    /**
     * Get the "id" ciseService.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<CiseServiceDTO> findOne(Long id);

    /**
     * Delete the "id" ciseService.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the ciseService corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<CiseServiceDTO> search(String query, Pageable pageable);
}

package eu.cise.console.service.impl;

import eu.cise.console.service.CiseServiceService;
import eu.cise.console.domain.CiseService;
import eu.cise.console.repository.CiseServiceRepository;
import eu.cise.console.repository.search.CiseServiceSearchRepository;
import eu.cise.console.service.dto.CiseServiceDTO;
import eu.cise.console.service.mapper.CiseServiceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing CiseService.
 */
@Service
@Transactional
public class CiseServiceServiceImpl implements CiseServiceService {

    private final Logger log = LoggerFactory.getLogger(CiseServiceServiceImpl.class);

    private final CiseServiceRepository ciseServiceRepository;

    private final CiseServiceMapper ciseServiceMapper;

    private final CiseServiceSearchRepository ciseServiceSearchRepository;

    public CiseServiceServiceImpl(CiseServiceRepository ciseServiceRepository, CiseServiceMapper ciseServiceMapper, CiseServiceSearchRepository ciseServiceSearchRepository) {
        this.ciseServiceRepository = ciseServiceRepository;
        this.ciseServiceMapper = ciseServiceMapper;
        this.ciseServiceSearchRepository = ciseServiceSearchRepository;
    }

    /**
     * Save a ciseService.
     *
     * @param ciseServiceDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CiseServiceDTO save(CiseServiceDTO ciseServiceDTO) {
        log.debug("Request to save CiseService : {}", ciseServiceDTO);
        CiseService ciseService = ciseServiceMapper.toEntity(ciseServiceDTO);
        ciseService = ciseServiceRepository.save(ciseService);
        CiseServiceDTO result = ciseServiceMapper.toDto(ciseService);
        ciseServiceSearchRepository.save(ciseService);
        return result;
    }

    /**
     * Get all the ciseServices.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CiseServiceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CiseServices");
        return ciseServiceRepository.findAll(pageable)
            .map(ciseServiceMapper::toDto);
    }


    /**
     * Get one ciseService by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CiseServiceDTO> findOne(Long id) {
        log.debug("Request to get CiseService : {}", id);
        return ciseServiceRepository.findById(id)
            .map(ciseServiceMapper::toDto);
    }

    /**
     * Delete the ciseService by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CiseService : {}", id);
        ciseServiceRepository.deleteById(id);
        ciseServiceSearchRepository.deleteById(id);
    }

    /**
     * Search for the ciseService corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CiseServiceDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of CiseServices for query {}", query);
        return ciseServiceSearchRepository.search(queryStringQuery(query), pageable)
            .map(ciseServiceMapper::toDto);
    }
}

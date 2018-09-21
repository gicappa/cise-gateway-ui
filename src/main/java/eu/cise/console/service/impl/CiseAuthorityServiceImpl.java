package eu.cise.console.service.impl;

import eu.cise.console.service.CiseAuthorityService;
import eu.cise.console.domain.CiseAuthority;
import eu.cise.console.repository.CiseAuthorityRepository;
import eu.cise.console.repository.search.CiseAuthoritySearchRepository;
import eu.cise.console.service.dto.CiseAuthorityDTO;
import eu.cise.console.service.mapper.CiseAuthorityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing CiseAuthority.
 */
@Service
@Transactional
public class CiseAuthorityServiceImpl implements CiseAuthorityService {

    private final Logger log = LoggerFactory.getLogger(CiseAuthorityServiceImpl.class);

    private final CiseAuthorityRepository ciseAuthorityRepository;

    private final CiseAuthorityMapper ciseAuthorityMapper;

    private final CiseAuthoritySearchRepository ciseAuthoritySearchRepository;

    public CiseAuthorityServiceImpl(CiseAuthorityRepository ciseAuthorityRepository, CiseAuthorityMapper ciseAuthorityMapper, CiseAuthoritySearchRepository ciseAuthoritySearchRepository) {
        this.ciseAuthorityRepository = ciseAuthorityRepository;
        this.ciseAuthorityMapper = ciseAuthorityMapper;
        this.ciseAuthoritySearchRepository = ciseAuthoritySearchRepository;
    }

    /**
     * Save a ciseAuthority.
     *
     * @param ciseAuthorityDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CiseAuthorityDTO save(CiseAuthorityDTO ciseAuthorityDTO) {
        log.debug("Request to save CiseAuthority : {}", ciseAuthorityDTO);
        CiseAuthority ciseAuthority = ciseAuthorityMapper.toEntity(ciseAuthorityDTO);
        ciseAuthority = ciseAuthorityRepository.save(ciseAuthority);
        CiseAuthorityDTO result = ciseAuthorityMapper.toDto(ciseAuthority);
        ciseAuthoritySearchRepository.save(ciseAuthority);
        return result;
    }

    /**
     * Get all the ciseAuthorities.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CiseAuthorityDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CiseAuthorities");
        return ciseAuthorityRepository.findAll(pageable)
            .map(ciseAuthorityMapper::toDto);
    }


    /**
     * Get one ciseAuthority by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CiseAuthorityDTO> findOne(Long id) {
        log.debug("Request to get CiseAuthority : {}", id);
        return ciseAuthorityRepository.findById(id)
            .map(ciseAuthorityMapper::toDto);
    }

    /**
     * Delete the ciseAuthority by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CiseAuthority : {}", id);
        ciseAuthorityRepository.deleteById(id);
        ciseAuthoritySearchRepository.deleteById(id);
    }

    /**
     * Search for the ciseAuthority corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CiseAuthorityDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of CiseAuthorities for query {}", query);
        return ciseAuthoritySearchRepository.search(queryStringQuery(query), pageable)
            .map(ciseAuthorityMapper::toDto);
    }
}

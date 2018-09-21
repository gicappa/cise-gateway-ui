package eu.cise.console.service.impl;

import eu.cise.console.service.CiseRuleService;
import eu.cise.console.domain.CiseRule;
import eu.cise.console.repository.CiseRuleRepository;
import eu.cise.console.repository.search.CiseRuleSearchRepository;
import eu.cise.console.service.dto.CiseRuleDTO;
import eu.cise.console.service.mapper.CiseRuleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing CiseRule.
 */
@Service
@Transactional
public class CiseRuleServiceImpl implements CiseRuleService {

    private final Logger log = LoggerFactory.getLogger(CiseRuleServiceImpl.class);

    private final CiseRuleRepository ciseRuleRepository;

    private final CiseRuleMapper ciseRuleMapper;

    private final CiseRuleSearchRepository ciseRuleSearchRepository;

    public CiseRuleServiceImpl(CiseRuleRepository ciseRuleRepository, CiseRuleMapper ciseRuleMapper, CiseRuleSearchRepository ciseRuleSearchRepository) {
        this.ciseRuleRepository = ciseRuleRepository;
        this.ciseRuleMapper = ciseRuleMapper;
        this.ciseRuleSearchRepository = ciseRuleSearchRepository;
    }

    /**
     * Save a ciseRule.
     *
     * @param ciseRuleDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CiseRuleDTO save(CiseRuleDTO ciseRuleDTO) {
        log.debug("Request to save CiseRule : {}", ciseRuleDTO);
        CiseRule ciseRule = ciseRuleMapper.toEntity(ciseRuleDTO);
        ciseRule = ciseRuleRepository.save(ciseRule);
        CiseRuleDTO result = ciseRuleMapper.toDto(ciseRule);
        ciseRuleSearchRepository.save(ciseRule);
        return result;
    }

    /**
     * Get all the ciseRules.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CiseRuleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CiseRules");
        return ciseRuleRepository.findAll(pageable)
            .map(ciseRuleMapper::toDto);
    }


    /**
     * Get one ciseRule by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CiseRuleDTO> findOne(Long id) {
        log.debug("Request to get CiseRule : {}", id);
        return ciseRuleRepository.findById(id)
            .map(ciseRuleMapper::toDto);
    }

    /**
     * Delete the ciseRule by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CiseRule : {}", id);
        ciseRuleRepository.deleteById(id);
        ciseRuleSearchRepository.deleteById(id);
    }

    /**
     * Search for the ciseRule corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CiseRuleDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of CiseRules for query {}", query);
        return ciseRuleSearchRepository.search(queryStringQuery(query), pageable)
            .map(ciseRuleMapper::toDto);
    }
}

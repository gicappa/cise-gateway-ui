package eu.cise.console.service.impl;

import eu.cise.console.service.CiseRuleSetService;
import eu.cise.console.domain.CiseRuleSet;
import eu.cise.console.repository.CiseRuleSetRepository;
import eu.cise.console.repository.search.CiseRuleSetSearchRepository;
import eu.cise.console.service.dto.CiseRuleSetDTO;
import eu.cise.console.service.mapper.CiseRuleSetMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing CiseRuleSet.
 */
@Service
@Transactional
public class CiseRuleSetServiceImpl implements CiseRuleSetService {

    private final Logger log = LoggerFactory.getLogger(CiseRuleSetServiceImpl.class);

    private final CiseRuleSetRepository ciseRuleSetRepository;

    private final CiseRuleSetMapper ciseRuleSetMapper;

    private final CiseRuleSetSearchRepository ciseRuleSetSearchRepository;

    public CiseRuleSetServiceImpl(CiseRuleSetRepository ciseRuleSetRepository, CiseRuleSetMapper ciseRuleSetMapper, CiseRuleSetSearchRepository ciseRuleSetSearchRepository) {
        this.ciseRuleSetRepository = ciseRuleSetRepository;
        this.ciseRuleSetMapper = ciseRuleSetMapper;
        this.ciseRuleSetSearchRepository = ciseRuleSetSearchRepository;
    }

    /**
     * Save a ciseRuleSet.
     *
     * @param ciseRuleSetDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CiseRuleSetDTO save(CiseRuleSetDTO ciseRuleSetDTO) {
        log.debug("Request to save CiseRuleSet : {}", ciseRuleSetDTO);
        CiseRuleSet ciseRuleSet = ciseRuleSetMapper.toEntity(ciseRuleSetDTO);
        ciseRuleSet = ciseRuleSetRepository.save(ciseRuleSet);
        CiseRuleSetDTO result = ciseRuleSetMapper.toDto(ciseRuleSet);
        ciseRuleSetSearchRepository.save(ciseRuleSet);
        return result;
    }

    /**
     * Get all the ciseRuleSets.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<CiseRuleSetDTO> findAll() {
        log.debug("Request to get all CiseRuleSets");
        return ciseRuleSetRepository.findAll().stream()
            .map(ciseRuleSetMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }



    /**
     *  get all the ciseRuleSets where CiseService is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<CiseRuleSetDTO> findAllWhereCiseServiceIsNull() {
        log.debug("Request to get all ciseRuleSets where CiseService is null");
        return StreamSupport
            .stream(ciseRuleSetRepository.findAll().spliterator(), false)
            .filter(ciseRuleSet -> ciseRuleSet.getCiseService() == null)
            .map(ciseRuleSetMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one ciseRuleSet by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CiseRuleSetDTO> findOne(Long id) {
        log.debug("Request to get CiseRuleSet : {}", id);
        return ciseRuleSetRepository.findById(id)
            .map(ciseRuleSetMapper::toDto);
    }

    /**
     * Delete the ciseRuleSet by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CiseRuleSet : {}", id);
        ciseRuleSetRepository.deleteById(id);
        ciseRuleSetSearchRepository.deleteById(id);
    }

    /**
     * Search for the ciseRuleSet corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<CiseRuleSetDTO> search(String query) {
        log.debug("Request to search CiseRuleSets for query {}", query);
        return StreamSupport
            .stream(ciseRuleSetSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(ciseRuleSetMapper::toDto)
            .collect(Collectors.toList());
    }
}

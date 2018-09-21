package eu.cise.console.service.impl;

import eu.cise.console.service.CiseServiceProfileService;
import eu.cise.console.domain.CiseServiceProfile;
import eu.cise.console.repository.CiseServiceProfileRepository;
import eu.cise.console.repository.search.CiseServiceProfileSearchRepository;
import eu.cise.console.service.dto.CiseServiceProfileDTO;
import eu.cise.console.service.mapper.CiseServiceProfileMapper;
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
 * Service Implementation for managing CiseServiceProfile.
 */
@Service
@Transactional
public class CiseServiceProfileServiceImpl implements CiseServiceProfileService {

    private final Logger log = LoggerFactory.getLogger(CiseServiceProfileServiceImpl.class);

    private final CiseServiceProfileRepository ciseServiceProfileRepository;

    private final CiseServiceProfileMapper ciseServiceProfileMapper;

    private final CiseServiceProfileSearchRepository ciseServiceProfileSearchRepository;

    public CiseServiceProfileServiceImpl(CiseServiceProfileRepository ciseServiceProfileRepository, CiseServiceProfileMapper ciseServiceProfileMapper, CiseServiceProfileSearchRepository ciseServiceProfileSearchRepository) {
        this.ciseServiceProfileRepository = ciseServiceProfileRepository;
        this.ciseServiceProfileMapper = ciseServiceProfileMapper;
        this.ciseServiceProfileSearchRepository = ciseServiceProfileSearchRepository;
    }

    /**
     * Save a ciseServiceProfile.
     *
     * @param ciseServiceProfileDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CiseServiceProfileDTO save(CiseServiceProfileDTO ciseServiceProfileDTO) {
        log.debug("Request to save CiseServiceProfile : {}", ciseServiceProfileDTO);
        CiseServiceProfile ciseServiceProfile = ciseServiceProfileMapper.toEntity(ciseServiceProfileDTO);
        ciseServiceProfile = ciseServiceProfileRepository.save(ciseServiceProfile);
        CiseServiceProfileDTO result = ciseServiceProfileMapper.toDto(ciseServiceProfile);
        ciseServiceProfileSearchRepository.save(ciseServiceProfile);
        return result;
    }

    /**
     * Get all the ciseServiceProfiles.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<CiseServiceProfileDTO> findAll() {
        log.debug("Request to get all CiseServiceProfiles");
        return ciseServiceProfileRepository.findAll().stream()
            .map(ciseServiceProfileMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }



    /**
     *  get all the ciseServiceProfiles where CiseRule is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<CiseServiceProfileDTO> findAllWhereCiseRuleIsNull() {
        log.debug("Request to get all ciseServiceProfiles where CiseRule is null");
        return StreamSupport
            .stream(ciseServiceProfileRepository.findAll().spliterator(), false)
            .filter(ciseServiceProfile -> ciseServiceProfile.getCiseRule() == null)
            .map(ciseServiceProfileMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one ciseServiceProfile by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CiseServiceProfileDTO> findOne(Long id) {
        log.debug("Request to get CiseServiceProfile : {}", id);
        return ciseServiceProfileRepository.findById(id)
            .map(ciseServiceProfileMapper::toDto);
    }

    /**
     * Delete the ciseServiceProfile by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CiseServiceProfile : {}", id);
        ciseServiceProfileRepository.deleteById(id);
        ciseServiceProfileSearchRepository.deleteById(id);
    }

    /**
     * Search for the ciseServiceProfile corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<CiseServiceProfileDTO> search(String query) {
        log.debug("Request to search CiseServiceProfiles for query {}", query);
        return StreamSupport
            .stream(ciseServiceProfileSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(ciseServiceProfileMapper::toDto)
            .collect(Collectors.toList());
    }
}

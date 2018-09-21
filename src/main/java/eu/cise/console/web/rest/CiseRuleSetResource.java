package eu.cise.console.web.rest;

import com.codahale.metrics.annotation.Timed;
import eu.cise.console.service.CiseRuleSetService;
import eu.cise.console.web.rest.errors.BadRequestAlertException;
import eu.cise.console.web.rest.util.HeaderUtil;
import eu.cise.console.service.dto.CiseRuleSetDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing CiseRuleSet.
 */
@RestController
@RequestMapping("/api")
public class CiseRuleSetResource {

    private final Logger log = LoggerFactory.getLogger(CiseRuleSetResource.class);

    private static final String ENTITY_NAME = "ciseRuleSet";

    private final CiseRuleSetService ciseRuleSetService;

    public CiseRuleSetResource(CiseRuleSetService ciseRuleSetService) {
        this.ciseRuleSetService = ciseRuleSetService;
    }

    /**
     * POST  /cise-rule-sets : Create a new ciseRuleSet.
     *
     * @param ciseRuleSetDTO the ciseRuleSetDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ciseRuleSetDTO, or with status 400 (Bad Request) if the ciseRuleSet has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/cise-rule-sets")
    @Timed
    public ResponseEntity<CiseRuleSetDTO> createCiseRuleSet(@Valid @RequestBody CiseRuleSetDTO ciseRuleSetDTO) throws URISyntaxException {
        log.debug("REST request to save CiseRuleSet : {}", ciseRuleSetDTO);
        if (ciseRuleSetDTO.getId() != null) {
            throw new BadRequestAlertException("A new ciseRuleSet cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CiseRuleSetDTO result = ciseRuleSetService.save(ciseRuleSetDTO);
        return ResponseEntity.created(new URI("/api/cise-rule-sets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cise-rule-sets : Updates an existing ciseRuleSet.
     *
     * @param ciseRuleSetDTO the ciseRuleSetDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ciseRuleSetDTO,
     * or with status 400 (Bad Request) if the ciseRuleSetDTO is not valid,
     * or with status 500 (Internal Server Error) if the ciseRuleSetDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/cise-rule-sets")
    @Timed
    public ResponseEntity<CiseRuleSetDTO> updateCiseRuleSet(@Valid @RequestBody CiseRuleSetDTO ciseRuleSetDTO) throws URISyntaxException {
        log.debug("REST request to update CiseRuleSet : {}", ciseRuleSetDTO);
        if (ciseRuleSetDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CiseRuleSetDTO result = ciseRuleSetService.save(ciseRuleSetDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ciseRuleSetDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cise-rule-sets : get all the ciseRuleSets.
     *
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of ciseRuleSets in body
     */
    @GetMapping("/cise-rule-sets")
    @Timed
    public List<CiseRuleSetDTO> getAllCiseRuleSets(@RequestParam(required = false) String filter) {
        if ("ciseservice-is-null".equals(filter)) {
            log.debug("REST request to get all CiseRuleSets where ciseService is null");
            return ciseRuleSetService.findAllWhereCiseServiceIsNull();
        }
        log.debug("REST request to get all CiseRuleSets");
        return ciseRuleSetService.findAll();
    }

    /**
     * GET  /cise-rule-sets/:id : get the "id" ciseRuleSet.
     *
     * @param id the id of the ciseRuleSetDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ciseRuleSetDTO, or with status 404 (Not Found)
     */
    @GetMapping("/cise-rule-sets/{id}")
    @Timed
    public ResponseEntity<CiseRuleSetDTO> getCiseRuleSet(@PathVariable Long id) {
        log.debug("REST request to get CiseRuleSet : {}", id);
        Optional<CiseRuleSetDTO> ciseRuleSetDTO = ciseRuleSetService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ciseRuleSetDTO);
    }

    /**
     * DELETE  /cise-rule-sets/:id : delete the "id" ciseRuleSet.
     *
     * @param id the id of the ciseRuleSetDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/cise-rule-sets/{id}")
    @Timed
    public ResponseEntity<Void> deleteCiseRuleSet(@PathVariable Long id) {
        log.debug("REST request to delete CiseRuleSet : {}", id);
        ciseRuleSetService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/cise-rule-sets?query=:query : search for the ciseRuleSet corresponding
     * to the query.
     *
     * @param query the query of the ciseRuleSet search
     * @return the result of the search
     */
    @GetMapping("/_search/cise-rule-sets")
    @Timed
    public List<CiseRuleSetDTO> searchCiseRuleSets(@RequestParam String query) {
        log.debug("REST request to search CiseRuleSets for query {}", query);
        return ciseRuleSetService.search(query);
    }

}

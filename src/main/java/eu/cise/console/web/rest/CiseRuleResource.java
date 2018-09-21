package eu.cise.console.web.rest;

import com.codahale.metrics.annotation.Timed;
import eu.cise.console.service.CiseRuleService;
import eu.cise.console.web.rest.errors.BadRequestAlertException;
import eu.cise.console.web.rest.util.HeaderUtil;
import eu.cise.console.web.rest.util.PaginationUtil;
import eu.cise.console.service.dto.CiseRuleDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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
 * REST controller for managing CiseRule.
 */
@RestController
@RequestMapping("/api")
public class CiseRuleResource {

    private final Logger log = LoggerFactory.getLogger(CiseRuleResource.class);

    private static final String ENTITY_NAME = "ciseRule";

    private final CiseRuleService ciseRuleService;

    public CiseRuleResource(CiseRuleService ciseRuleService) {
        this.ciseRuleService = ciseRuleService;
    }

    /**
     * POST  /cise-rules : Create a new ciseRule.
     *
     * @param ciseRuleDTO the ciseRuleDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ciseRuleDTO, or with status 400 (Bad Request) if the ciseRule has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/cise-rules")
    @Timed
    public ResponseEntity<CiseRuleDTO> createCiseRule(@Valid @RequestBody CiseRuleDTO ciseRuleDTO) throws URISyntaxException {
        log.debug("REST request to save CiseRule : {}", ciseRuleDTO);
        if (ciseRuleDTO.getId() != null) {
            throw new BadRequestAlertException("A new ciseRule cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CiseRuleDTO result = ciseRuleService.save(ciseRuleDTO);
        return ResponseEntity.created(new URI("/api/cise-rules/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cise-rules : Updates an existing ciseRule.
     *
     * @param ciseRuleDTO the ciseRuleDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ciseRuleDTO,
     * or with status 400 (Bad Request) if the ciseRuleDTO is not valid,
     * or with status 500 (Internal Server Error) if the ciseRuleDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/cise-rules")
    @Timed
    public ResponseEntity<CiseRuleDTO> updateCiseRule(@Valid @RequestBody CiseRuleDTO ciseRuleDTO) throws URISyntaxException {
        log.debug("REST request to update CiseRule : {}", ciseRuleDTO);
        if (ciseRuleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CiseRuleDTO result = ciseRuleService.save(ciseRuleDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ciseRuleDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cise-rules : get all the ciseRules.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of ciseRules in body
     */
    @GetMapping("/cise-rules")
    @Timed
    public ResponseEntity<List<CiseRuleDTO>> getAllCiseRules(Pageable pageable) {
        log.debug("REST request to get a page of CiseRules");
        Page<CiseRuleDTO> page = ciseRuleService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/cise-rules");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /cise-rules/:id : get the "id" ciseRule.
     *
     * @param id the id of the ciseRuleDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ciseRuleDTO, or with status 404 (Not Found)
     */
    @GetMapping("/cise-rules/{id}")
    @Timed
    public ResponseEntity<CiseRuleDTO> getCiseRule(@PathVariable Long id) {
        log.debug("REST request to get CiseRule : {}", id);
        Optional<CiseRuleDTO> ciseRuleDTO = ciseRuleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ciseRuleDTO);
    }

    /**
     * DELETE  /cise-rules/:id : delete the "id" ciseRule.
     *
     * @param id the id of the ciseRuleDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/cise-rules/{id}")
    @Timed
    public ResponseEntity<Void> deleteCiseRule(@PathVariable Long id) {
        log.debug("REST request to delete CiseRule : {}", id);
        ciseRuleService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/cise-rules?query=:query : search for the ciseRule corresponding
     * to the query.
     *
     * @param query the query of the ciseRule search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/cise-rules")
    @Timed
    public ResponseEntity<List<CiseRuleDTO>> searchCiseRules(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of CiseRules for query {}", query);
        Page<CiseRuleDTO> page = ciseRuleService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/cise-rules");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}

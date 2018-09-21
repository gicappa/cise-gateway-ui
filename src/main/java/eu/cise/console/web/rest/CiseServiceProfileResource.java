package eu.cise.console.web.rest;

import com.codahale.metrics.annotation.Timed;
import eu.cise.console.service.CiseServiceProfileService;
import eu.cise.console.web.rest.errors.BadRequestAlertException;
import eu.cise.console.web.rest.util.HeaderUtil;
import eu.cise.console.service.dto.CiseServiceProfileDTO;
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
 * REST controller for managing CiseServiceProfile.
 */
@RestController
@RequestMapping("/api")
public class CiseServiceProfileResource {

    private final Logger log = LoggerFactory.getLogger(CiseServiceProfileResource.class);

    private static final String ENTITY_NAME = "ciseServiceProfile";

    private final CiseServiceProfileService ciseServiceProfileService;

    public CiseServiceProfileResource(CiseServiceProfileService ciseServiceProfileService) {
        this.ciseServiceProfileService = ciseServiceProfileService;
    }

    /**
     * POST  /cise-service-profiles : Create a new ciseServiceProfile.
     *
     * @param ciseServiceProfileDTO the ciseServiceProfileDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ciseServiceProfileDTO, or with status 400 (Bad Request) if the ciseServiceProfile has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/cise-service-profiles")
    @Timed
    public ResponseEntity<CiseServiceProfileDTO> createCiseServiceProfile(@Valid @RequestBody CiseServiceProfileDTO ciseServiceProfileDTO) throws URISyntaxException {
        log.debug("REST request to save CiseServiceProfile : {}", ciseServiceProfileDTO);
        if (ciseServiceProfileDTO.getId() != null) {
            throw new BadRequestAlertException("A new ciseServiceProfile cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CiseServiceProfileDTO result = ciseServiceProfileService.save(ciseServiceProfileDTO);
        return ResponseEntity.created(new URI("/api/cise-service-profiles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cise-service-profiles : Updates an existing ciseServiceProfile.
     *
     * @param ciseServiceProfileDTO the ciseServiceProfileDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ciseServiceProfileDTO,
     * or with status 400 (Bad Request) if the ciseServiceProfileDTO is not valid,
     * or with status 500 (Internal Server Error) if the ciseServiceProfileDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/cise-service-profiles")
    @Timed
    public ResponseEntity<CiseServiceProfileDTO> updateCiseServiceProfile(@Valid @RequestBody CiseServiceProfileDTO ciseServiceProfileDTO) throws URISyntaxException {
        log.debug("REST request to update CiseServiceProfile : {}", ciseServiceProfileDTO);
        if (ciseServiceProfileDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CiseServiceProfileDTO result = ciseServiceProfileService.save(ciseServiceProfileDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ciseServiceProfileDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cise-service-profiles : get all the ciseServiceProfiles.
     *
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of ciseServiceProfiles in body
     */
    @GetMapping("/cise-service-profiles")
    @Timed
    public List<CiseServiceProfileDTO> getAllCiseServiceProfiles(@RequestParam(required = false) String filter) {
        if ("ciserule-is-null".equals(filter)) {
            log.debug("REST request to get all CiseServiceProfiles where ciseRule is null");
            return ciseServiceProfileService.findAllWhereCiseRuleIsNull();
        }
        log.debug("REST request to get all CiseServiceProfiles");
        return ciseServiceProfileService.findAll();
    }

    /**
     * GET  /cise-service-profiles/:id : get the "id" ciseServiceProfile.
     *
     * @param id the id of the ciseServiceProfileDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ciseServiceProfileDTO, or with status 404 (Not Found)
     */
    @GetMapping("/cise-service-profiles/{id}")
    @Timed
    public ResponseEntity<CiseServiceProfileDTO> getCiseServiceProfile(@PathVariable Long id) {
        log.debug("REST request to get CiseServiceProfile : {}", id);
        Optional<CiseServiceProfileDTO> ciseServiceProfileDTO = ciseServiceProfileService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ciseServiceProfileDTO);
    }

    /**
     * DELETE  /cise-service-profiles/:id : delete the "id" ciseServiceProfile.
     *
     * @param id the id of the ciseServiceProfileDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/cise-service-profiles/{id}")
    @Timed
    public ResponseEntity<Void> deleteCiseServiceProfile(@PathVariable Long id) {
        log.debug("REST request to delete CiseServiceProfile : {}", id);
        ciseServiceProfileService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/cise-service-profiles?query=:query : search for the ciseServiceProfile corresponding
     * to the query.
     *
     * @param query the query of the ciseServiceProfile search
     * @return the result of the search
     */
    @GetMapping("/_search/cise-service-profiles")
    @Timed
    public List<CiseServiceProfileDTO> searchCiseServiceProfiles(@RequestParam String query) {
        log.debug("REST request to search CiseServiceProfiles for query {}", query);
        return ciseServiceProfileService.search(query);
    }

}

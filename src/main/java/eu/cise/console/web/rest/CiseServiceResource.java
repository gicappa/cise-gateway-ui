package eu.cise.console.web.rest;

import com.codahale.metrics.annotation.Timed;
import eu.cise.console.service.CiseServiceService;
import eu.cise.console.web.rest.errors.BadRequestAlertException;
import eu.cise.console.web.rest.util.HeaderUtil;
import eu.cise.console.web.rest.util.PaginationUtil;
import eu.cise.console.service.dto.CiseServiceDTO;
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
 * REST controller for managing CiseService.
 */
@RestController
@RequestMapping("/api")
public class CiseServiceResource {

    private final Logger log = LoggerFactory.getLogger(CiseServiceResource.class);

    private static final String ENTITY_NAME = "ciseService";

    private final CiseServiceService ciseServiceService;

    public CiseServiceResource(CiseServiceService ciseServiceService) {
        this.ciseServiceService = ciseServiceService;
    }

    /**
     * POST  /cise-services : Create a new ciseService.
     *
     * @param ciseServiceDTO the ciseServiceDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ciseServiceDTO, or with status 400 (Bad Request) if the ciseService has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/cise-services")
    @Timed
    public ResponseEntity<CiseServiceDTO> createCiseService(@Valid @RequestBody CiseServiceDTO ciseServiceDTO) throws URISyntaxException {
        log.debug("REST request to save CiseService : {}", ciseServiceDTO);
        if (ciseServiceDTO.getId() != null) {
            throw new BadRequestAlertException("A new ciseService cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CiseServiceDTO result = ciseServiceService.save(ciseServiceDTO);
        return ResponseEntity.created(new URI("/api/cise-services/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cise-services : Updates an existing ciseService.
     *
     * @param ciseServiceDTO the ciseServiceDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ciseServiceDTO,
     * or with status 400 (Bad Request) if the ciseServiceDTO is not valid,
     * or with status 500 (Internal Server Error) if the ciseServiceDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/cise-services")
    @Timed
    public ResponseEntity<CiseServiceDTO> updateCiseService(@Valid @RequestBody CiseServiceDTO ciseServiceDTO) throws URISyntaxException {
        log.debug("REST request to update CiseService : {}", ciseServiceDTO);
        if (ciseServiceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CiseServiceDTO result = ciseServiceService.save(ciseServiceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ciseServiceDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cise-services : get all the ciseServices.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of ciseServices in body
     */
    @GetMapping("/cise-services")
    @Timed
    public ResponseEntity<List<CiseServiceDTO>> getAllCiseServices(Pageable pageable) {
        log.debug("REST request to get a page of CiseServices");
        Page<CiseServiceDTO> page = ciseServiceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/cise-services");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /cise-services/:id : get the "id" ciseService.
     *
     * @param id the id of the ciseServiceDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ciseServiceDTO, or with status 404 (Not Found)
     */
    @GetMapping("/cise-services/{id}")
    @Timed
    public ResponseEntity<CiseServiceDTO> getCiseService(@PathVariable Long id) {
        log.debug("REST request to get CiseService : {}", id);
        Optional<CiseServiceDTO> ciseServiceDTO = ciseServiceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ciseServiceDTO);
    }

    /**
     * DELETE  /cise-services/:id : delete the "id" ciseService.
     *
     * @param id the id of the ciseServiceDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/cise-services/{id}")
    @Timed
    public ResponseEntity<Void> deleteCiseService(@PathVariable Long id) {
        log.debug("REST request to delete CiseService : {}", id);
        ciseServiceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/cise-services?query=:query : search for the ciseService corresponding
     * to the query.
     *
     * @param query the query of the ciseService search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/cise-services")
    @Timed
    public ResponseEntity<List<CiseServiceDTO>> searchCiseServices(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of CiseServices for query {}", query);
        Page<CiseServiceDTO> page = ciseServiceService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/cise-services");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}

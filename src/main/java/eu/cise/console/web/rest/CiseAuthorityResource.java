package eu.cise.console.web.rest;

import com.codahale.metrics.annotation.Timed;
import eu.cise.console.service.CiseAuthorityService;
import eu.cise.console.web.rest.errors.BadRequestAlertException;
import eu.cise.console.web.rest.util.HeaderUtil;
import eu.cise.console.web.rest.util.PaginationUtil;
import eu.cise.console.service.dto.CiseAuthorityDTO;
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
 * REST controller for managing CiseAuthority.
 */
@RestController
@RequestMapping("/api")
public class CiseAuthorityResource {

    private final Logger log = LoggerFactory.getLogger(CiseAuthorityResource.class);

    private static final String ENTITY_NAME = "ciseAuthority";

    private final CiseAuthorityService ciseAuthorityService;

    public CiseAuthorityResource(CiseAuthorityService ciseAuthorityService) {
        this.ciseAuthorityService = ciseAuthorityService;
    }

    /**
     * POST  /cise-authorities : Create a new ciseAuthority.
     *
     * @param ciseAuthorityDTO the ciseAuthorityDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ciseAuthorityDTO, or with status 400 (Bad Request) if the ciseAuthority has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/cise-authorities")
    @Timed
    public ResponseEntity<CiseAuthorityDTO> createCiseAuthority(@Valid @RequestBody CiseAuthorityDTO ciseAuthorityDTO) throws URISyntaxException {
        log.debug("REST request to save CiseAuthority : {}", ciseAuthorityDTO);
        if (ciseAuthorityDTO.getId() != null) {
            throw new BadRequestAlertException("A new ciseAuthority cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CiseAuthorityDTO result = ciseAuthorityService.save(ciseAuthorityDTO);
        return ResponseEntity.created(new URI("/api/cise-authorities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cise-authorities : Updates an existing ciseAuthority.
     *
     * @param ciseAuthorityDTO the ciseAuthorityDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ciseAuthorityDTO,
     * or with status 400 (Bad Request) if the ciseAuthorityDTO is not valid,
     * or with status 500 (Internal Server Error) if the ciseAuthorityDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/cise-authorities")
    @Timed
    public ResponseEntity<CiseAuthorityDTO> updateCiseAuthority(@Valid @RequestBody CiseAuthorityDTO ciseAuthorityDTO) throws URISyntaxException {
        log.debug("REST request to update CiseAuthority : {}", ciseAuthorityDTO);
        if (ciseAuthorityDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CiseAuthorityDTO result = ciseAuthorityService.save(ciseAuthorityDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ciseAuthorityDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cise-authorities : get all the ciseAuthorities.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of ciseAuthorities in body
     */
    @GetMapping("/cise-authorities")
    @Timed
    public ResponseEntity<List<CiseAuthorityDTO>> getAllCiseAuthorities(Pageable pageable) {
        log.debug("REST request to get a page of CiseAuthorities");
        Page<CiseAuthorityDTO> page = ciseAuthorityService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/cise-authorities");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /cise-authorities/:id : get the "id" ciseAuthority.
     *
     * @param id the id of the ciseAuthorityDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ciseAuthorityDTO, or with status 404 (Not Found)
     */
    @GetMapping("/cise-authorities/{id}")
    @Timed
    public ResponseEntity<CiseAuthorityDTO> getCiseAuthority(@PathVariable Long id) {
        log.debug("REST request to get CiseAuthority : {}", id);
        Optional<CiseAuthorityDTO> ciseAuthorityDTO = ciseAuthorityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ciseAuthorityDTO);
    }

    /**
     * DELETE  /cise-authorities/:id : delete the "id" ciseAuthority.
     *
     * @param id the id of the ciseAuthorityDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/cise-authorities/{id}")
    @Timed
    public ResponseEntity<Void> deleteCiseAuthority(@PathVariable Long id) {
        log.debug("REST request to delete CiseAuthority : {}", id);
        ciseAuthorityService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/cise-authorities?query=:query : search for the ciseAuthority corresponding
     * to the query.
     *
     * @param query the query of the ciseAuthority search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/cise-authorities")
    @Timed
    public ResponseEntity<List<CiseAuthorityDTO>> searchCiseAuthorities(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of CiseAuthorities for query {}", query);
        Page<CiseAuthorityDTO> page = ciseAuthorityService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/cise-authorities");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}

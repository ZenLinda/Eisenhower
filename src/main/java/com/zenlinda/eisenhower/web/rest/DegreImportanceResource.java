package com.zenlinda.eisenhower.web.rest;

import com.zenlinda.eisenhower.domain.DegreImportance;
import com.zenlinda.eisenhower.service.DegreImportanceService;
import com.zenlinda.eisenhower.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.zenlinda.eisenhower.domain.DegreImportance}.
 */
@RestController
@RequestMapping("/api")
public class DegreImportanceResource {

    private final Logger log = LoggerFactory.getLogger(DegreImportanceResource.class);

    private static final String ENTITY_NAME = "degreImportance";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DegreImportanceService degreImportanceService;

    public DegreImportanceResource(DegreImportanceService degreImportanceService) {
        this.degreImportanceService = degreImportanceService;
    }

    /**
     * {@code POST  /degre-importances} : Create a new degreImportance.
     *
     * @param degreImportance the degreImportance to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new degreImportance, or with status {@code 400 (Bad Request)} if the degreImportance has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/degre-importances")
    public ResponseEntity<DegreImportance> createDegreImportance(@Valid @RequestBody DegreImportance degreImportance) throws URISyntaxException {
        log.debug("REST request to save DegreImportance : {}", degreImportance);
        if (degreImportance.getId() != null) {
            throw new BadRequestAlertException("A new degreImportance cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DegreImportance result = degreImportanceService.save(degreImportance);
        return ResponseEntity.created(new URI("/api/degre-importances/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /degre-importances} : Updates an existing degreImportance.
     *
     * @param degreImportance the degreImportance to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated degreImportance,
     * or with status {@code 400 (Bad Request)} if the degreImportance is not valid,
     * or with status {@code 500 (Internal Server Error)} if the degreImportance couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/degre-importances")
    public ResponseEntity<DegreImportance> updateDegreImportance(@Valid @RequestBody DegreImportance degreImportance) throws URISyntaxException {
        log.debug("REST request to update DegreImportance : {}", degreImportance);
        if (degreImportance.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DegreImportance result = degreImportanceService.save(degreImportance);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, degreImportance.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /degre-importances} : get all the degreImportances.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of degreImportances in body.
     */
    @GetMapping("/degre-importances")
    public ResponseEntity<List<DegreImportance>> getAllDegreImportances(Pageable pageable) {
        log.debug("REST request to get a page of DegreImportances");
        Page<DegreImportance> page = degreImportanceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /degre-importances/:id} : get the "id" degreImportance.
     *
     * @param id the id of the degreImportance to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the degreImportance, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/degre-importances/{id}")
    public ResponseEntity<DegreImportance> getDegreImportance(@PathVariable Long id) {
        log.debug("REST request to get DegreImportance : {}", id);
        Optional<DegreImportance> degreImportance = degreImportanceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(degreImportance);
    }

    /**
     * {@code DELETE  /degre-importances/:id} : delete the "id" degreImportance.
     *
     * @param id the id of the degreImportance to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/degre-importances/{id}")
    public ResponseEntity<Void> deleteDegreImportance(@PathVariable Long id) {
        log.debug("REST request to delete DegreImportance : {}", id);
        degreImportanceService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}

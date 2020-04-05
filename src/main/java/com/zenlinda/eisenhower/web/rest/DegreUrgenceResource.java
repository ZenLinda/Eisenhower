package com.zenlinda.eisenhower.web.rest;

import com.zenlinda.eisenhower.domain.DegreUrgence;
import com.zenlinda.eisenhower.service.DegreUrgenceService;
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
 * REST controller for managing {@link com.zenlinda.eisenhower.domain.DegreUrgence}.
 */
@RestController
@RequestMapping("/api")
public class DegreUrgenceResource {

    private final Logger log = LoggerFactory.getLogger(DegreUrgenceResource.class);

    private static final String ENTITY_NAME = "degreUrgence";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DegreUrgenceService degreUrgenceService;

    public DegreUrgenceResource(DegreUrgenceService degreUrgenceService) {
        this.degreUrgenceService = degreUrgenceService;
    }

    /**
     * {@code POST  /degre-urgences} : Create a new degreUrgence.
     *
     * @param degreUrgence the degreUrgence to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new degreUrgence, or with status {@code 400 (Bad Request)} if the degreUrgence has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/degre-urgences")
    public ResponseEntity<DegreUrgence> createDegreUrgence(@Valid @RequestBody DegreUrgence degreUrgence) throws URISyntaxException {
        log.debug("REST request to save DegreUrgence : {}", degreUrgence);
        if (degreUrgence.getId() != null) {
            throw new BadRequestAlertException("A new degreUrgence cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DegreUrgence result = degreUrgenceService.save(degreUrgence);
        return ResponseEntity.created(new URI("/api/degre-urgences/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /degre-urgences} : Updates an existing degreUrgence.
     *
     * @param degreUrgence the degreUrgence to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated degreUrgence,
     * or with status {@code 400 (Bad Request)} if the degreUrgence is not valid,
     * or with status {@code 500 (Internal Server Error)} if the degreUrgence couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/degre-urgences")
    public ResponseEntity<DegreUrgence> updateDegreUrgence(@Valid @RequestBody DegreUrgence degreUrgence) throws URISyntaxException {
        log.debug("REST request to update DegreUrgence : {}", degreUrgence);
        if (degreUrgence.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DegreUrgence result = degreUrgenceService.save(degreUrgence);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, degreUrgence.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /degre-urgences} : get all the degreUrgences.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of degreUrgences in body.
     */
    @GetMapping("/degre-urgences")
    public ResponseEntity<List<DegreUrgence>> getAllDegreUrgences(Pageable pageable) {
        log.debug("REST request to get a page of DegreUrgences");
        Page<DegreUrgence> page = degreUrgenceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /degre-urgences/:id} : get the "id" degreUrgence.
     *
     * @param id the id of the degreUrgence to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the degreUrgence, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/degre-urgences/{id}")
    public ResponseEntity<DegreUrgence> getDegreUrgence(@PathVariable Long id) {
        log.debug("REST request to get DegreUrgence : {}", id);
        Optional<DegreUrgence> degreUrgence = degreUrgenceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(degreUrgence);
    }

    /**
     * {@code DELETE  /degre-urgences/:id} : delete the "id" degreUrgence.
     *
     * @param id the id of the degreUrgence to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/degre-urgences/{id}")
    public ResponseEntity<Void> deleteDegreUrgence(@PathVariable Long id) {
        log.debug("REST request to delete DegreUrgence : {}", id);
        degreUrgenceService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}

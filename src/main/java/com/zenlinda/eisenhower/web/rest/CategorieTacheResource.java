package com.zenlinda.eisenhower.web.rest;

import com.zenlinda.eisenhower.domain.CategorieTache;
import com.zenlinda.eisenhower.service.CategorieTacheService;
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
 * REST controller for managing {@link com.zenlinda.eisenhower.domain.CategorieTache}.
 */
@RestController
@RequestMapping("/api")
public class CategorieTacheResource {

    private final Logger log = LoggerFactory.getLogger(CategorieTacheResource.class);

    private static final String ENTITY_NAME = "categorieTache";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CategorieTacheService categorieTacheService;

    public CategorieTacheResource(CategorieTacheService categorieTacheService) {
        this.categorieTacheService = categorieTacheService;
    }

    /**
     * {@code POST  /categorie-taches} : Create a new categorieTache.
     *
     * @param categorieTache the categorieTache to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new categorieTache, or with status {@code 400 (Bad Request)} if the categorieTache has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/categorie-taches")
    public ResponseEntity<CategorieTache> createCategorieTache(@Valid @RequestBody CategorieTache categorieTache) throws URISyntaxException {
        log.debug("REST request to save CategorieTache : {}", categorieTache);
        if (categorieTache.getId() != null) {
            throw new BadRequestAlertException("A new categorieTache cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CategorieTache result = categorieTacheService.save(categorieTache);
        return ResponseEntity.created(new URI("/api/categorie-taches/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /categorie-taches} : Updates an existing categorieTache.
     *
     * @param categorieTache the categorieTache to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated categorieTache,
     * or with status {@code 400 (Bad Request)} if the categorieTache is not valid,
     * or with status {@code 500 (Internal Server Error)} if the categorieTache couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/categorie-taches")
    public ResponseEntity<CategorieTache> updateCategorieTache(@Valid @RequestBody CategorieTache categorieTache) throws URISyntaxException {
        log.debug("REST request to update CategorieTache : {}", categorieTache);
        if (categorieTache.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CategorieTache result = categorieTacheService.save(categorieTache);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, categorieTache.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /categorie-taches} : get all the categorieTaches.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of categorieTaches in body.
     */
    @GetMapping("/categorie-taches")
    public ResponseEntity<List<CategorieTache>> getAllCategorieTaches(Pageable pageable) {
        log.debug("REST request to get a page of CategorieTaches");
        Page<CategorieTache> page = categorieTacheService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /categorie-taches/:id} : get the "id" categorieTache.
     *
     * @param id the id of the categorieTache to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the categorieTache, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/categorie-taches/{id}")
    public ResponseEntity<CategorieTache> getCategorieTache(@PathVariable Long id) {
        log.debug("REST request to get CategorieTache : {}", id);
        Optional<CategorieTache> categorieTache = categorieTacheService.findOne(id);
        return ResponseUtil.wrapOrNotFound(categorieTache);
    }

    /**
     * {@code DELETE  /categorie-taches/:id} : delete the "id" categorieTache.
     *
     * @param id the id of the categorieTache to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/categorie-taches/{id}")
    public ResponseEntity<Void> deleteCategorieTache(@PathVariable Long id) {
        log.debug("REST request to delete CategorieTache : {}", id);
        categorieTacheService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}

package com.zenlinda.eisenhower.web.rest;

import com.zenlinda.eisenhower.domain.Matrice;
import com.zenlinda.eisenhower.service.MatriceService;
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
 * REST controller for managing {@link com.zenlinda.eisenhower.domain.Matrice}.
 */
@RestController
@RequestMapping("/api")
public class MatriceResource {

    private final Logger log = LoggerFactory.getLogger(MatriceResource.class);

    private static final String ENTITY_NAME = "matrice";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MatriceService matriceService;

    public MatriceResource(MatriceService matriceService) {
        this.matriceService = matriceService;
    }

    /**
     * {@code POST  /matrices} : Create a new matrice.
     *
     * @param matrice the matrice to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new matrice, or with status {@code 400 (Bad Request)} if the matrice has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/matrices")
    public ResponseEntity<Matrice> createMatrice(@Valid @RequestBody Matrice matrice) throws URISyntaxException {
        log.debug("REST request to save Matrice : {}", matrice);
        if (matrice.getId() != null) {
            throw new BadRequestAlertException("A new matrice cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Matrice result = matriceService.save(matrice);
        return ResponseEntity.created(new URI("/api/matrices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /matrices} : Updates an existing matrice.
     *
     * @param matrice the matrice to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated matrice,
     * or with status {@code 400 (Bad Request)} if the matrice is not valid,
     * or with status {@code 500 (Internal Server Error)} if the matrice couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/matrices")
    public ResponseEntity<Matrice> updateMatrice(@Valid @RequestBody Matrice matrice) throws URISyntaxException {
        log.debug("REST request to update Matrice : {}", matrice);
        if (matrice.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Matrice result = matriceService.save(matrice);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, matrice.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /matrices} : get all the matrices.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of matrices in body.
     */
    @GetMapping("/matrices")
    public ResponseEntity<List<Matrice>> getAllMatrices(Pageable pageable) {
        log.debug("REST request to get a page of Matrices");
        Page<Matrice> page = matriceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /matrices/:id} : get the "id" matrice.
     *
     * @param id the id of the matrice to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the matrice, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/matrices/{id}")
    public ResponseEntity<Matrice> getMatrice(@PathVariable Long id) {
        log.debug("REST request to get Matrice : {}", id);
        Optional<Matrice> matrice = matriceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(matrice);
    }

    /**
     * {@code DELETE  /matrices/:id} : delete the "id" matrice.
     *
     * @param id the id of the matrice to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/matrices/{id}")
    public ResponseEntity<Void> deleteMatrice(@PathVariable Long id) {
        log.debug("REST request to delete Matrice : {}", id);
        matriceService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}

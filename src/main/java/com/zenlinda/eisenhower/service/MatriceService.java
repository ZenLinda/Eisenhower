package com.zenlinda.eisenhower.service;

import com.zenlinda.eisenhower.domain.Matrice;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Matrice}.
 */
public interface MatriceService {

    /**
     * Save a matrice.
     *
     * @param matrice the entity to save.
     * @return the persisted entity.
     */
    Matrice save(Matrice matrice);

    /**
     * Get all the matrices.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Matrice> findAll(Pageable pageable);

    /**
     * Get the "id" matrice.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Matrice> findOne(Long id);

    /**
     * Delete the "id" matrice.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

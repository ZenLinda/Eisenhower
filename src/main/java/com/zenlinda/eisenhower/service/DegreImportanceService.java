package com.zenlinda.eisenhower.service;

import com.zenlinda.eisenhower.domain.DegreImportance;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link DegreImportance}.
 */
public interface DegreImportanceService {

    /**
     * Save a degreImportance.
     *
     * @param degreImportance the entity to save.
     * @return the persisted entity.
     */
    DegreImportance save(DegreImportance degreImportance);

    /**
     * Get all the degreImportances.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DegreImportance> findAll(Pageable pageable);

    /**
     * Get the "id" degreImportance.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DegreImportance> findOne(Long id);

    /**
     * Delete the "id" degreImportance.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

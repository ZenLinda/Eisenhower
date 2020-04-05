package com.zenlinda.eisenhower.service;

import com.zenlinda.eisenhower.domain.DegreUrgence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link DegreUrgence}.
 */
public interface DegreUrgenceService {

    /**
     * Save a degreUrgence.
     *
     * @param degreUrgence the entity to save.
     * @return the persisted entity.
     */
    DegreUrgence save(DegreUrgence degreUrgence);

    /**
     * Get all the degreUrgences.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DegreUrgence> findAll(Pageable pageable);

    /**
     * Get the "id" degreUrgence.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DegreUrgence> findOne(Long id);

    /**
     * Delete the "id" degreUrgence.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

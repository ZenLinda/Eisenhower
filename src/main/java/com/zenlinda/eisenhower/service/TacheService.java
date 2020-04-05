package com.zenlinda.eisenhower.service;

import com.zenlinda.eisenhower.domain.Tache;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Tache}.
 */
public interface TacheService {

    /**
     * Save a tache.
     *
     * @param tache the entity to save.
     * @return the persisted entity.
     */
    Tache save(Tache tache);

    /**
     * Get all the taches.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Tache> findAll(Pageable pageable);

    /**
     * Get the "id" tache.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Tache> findOne(Long id);

    /**
     * Delete the "id" tache.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

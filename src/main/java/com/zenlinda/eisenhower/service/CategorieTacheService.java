package com.zenlinda.eisenhower.service;

import com.zenlinda.eisenhower.domain.CategorieTache;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link CategorieTache}.
 */
public interface CategorieTacheService {

    /**
     * Save a categorieTache.
     *
     * @param categorieTache the entity to save.
     * @return the persisted entity.
     */
    CategorieTache save(CategorieTache categorieTache);

    /**
     * Get all the categorieTaches.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CategorieTache> findAll(Pageable pageable);

    /**
     * Get the "id" categorieTache.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CategorieTache> findOne(Long id);

    /**
     * Delete the "id" categorieTache.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

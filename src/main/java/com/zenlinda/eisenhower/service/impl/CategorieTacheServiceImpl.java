package com.zenlinda.eisenhower.service.impl;

import com.zenlinda.eisenhower.service.CategorieTacheService;
import com.zenlinda.eisenhower.domain.CategorieTache;
import com.zenlinda.eisenhower.repository.CategorieTacheRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CategorieTache}.
 */
@Service
@Transactional
public class CategorieTacheServiceImpl implements CategorieTacheService {

    private final Logger log = LoggerFactory.getLogger(CategorieTacheServiceImpl.class);

    private final CategorieTacheRepository categorieTacheRepository;

    public CategorieTacheServiceImpl(CategorieTacheRepository categorieTacheRepository) {
        this.categorieTacheRepository = categorieTacheRepository;
    }

    /**
     * Save a categorieTache.
     *
     * @param categorieTache the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CategorieTache save(CategorieTache categorieTache) {
        log.debug("Request to save CategorieTache : {}", categorieTache);
        return categorieTacheRepository.save(categorieTache);
    }

    /**
     * Get all the categorieTaches.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CategorieTache> findAll(Pageable pageable) {
        log.debug("Request to get all CategorieTaches");
        return categorieTacheRepository.findAll(pageable);
    }

    /**
     * Get one categorieTache by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CategorieTache> findOne(Long id) {
        log.debug("Request to get CategorieTache : {}", id);
        return categorieTacheRepository.findById(id);
    }

    /**
     * Delete the categorieTache by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CategorieTache : {}", id);
        categorieTacheRepository.deleteById(id);
    }
}

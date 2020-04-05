package com.zenlinda.eisenhower.service.impl;

import com.zenlinda.eisenhower.service.DegreUrgenceService;
import com.zenlinda.eisenhower.domain.DegreUrgence;
import com.zenlinda.eisenhower.repository.DegreUrgenceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link DegreUrgence}.
 */
@Service
@Transactional
public class DegreUrgenceServiceImpl implements DegreUrgenceService {

    private final Logger log = LoggerFactory.getLogger(DegreUrgenceServiceImpl.class);

    private final DegreUrgenceRepository degreUrgenceRepository;

    public DegreUrgenceServiceImpl(DegreUrgenceRepository degreUrgenceRepository) {
        this.degreUrgenceRepository = degreUrgenceRepository;
    }

    /**
     * Save a degreUrgence.
     *
     * @param degreUrgence the entity to save.
     * @return the persisted entity.
     */
    @Override
    public DegreUrgence save(DegreUrgence degreUrgence) {
        log.debug("Request to save DegreUrgence : {}", degreUrgence);
        return degreUrgenceRepository.save(degreUrgence);
    }

    /**
     * Get all the degreUrgences.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DegreUrgence> findAll(Pageable pageable) {
        log.debug("Request to get all DegreUrgences");
        return degreUrgenceRepository.findAll(pageable);
    }

    /**
     * Get one degreUrgence by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DegreUrgence> findOne(Long id) {
        log.debug("Request to get DegreUrgence : {}", id);
        return degreUrgenceRepository.findById(id);
    }

    /**
     * Delete the degreUrgence by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete DegreUrgence : {}", id);
        degreUrgenceRepository.deleteById(id);
    }
}

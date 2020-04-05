package com.zenlinda.eisenhower.service.impl;

import com.zenlinda.eisenhower.service.DegreImportanceService;
import com.zenlinda.eisenhower.domain.DegreImportance;
import com.zenlinda.eisenhower.repository.DegreImportanceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link DegreImportance}.
 */
@Service
@Transactional
public class DegreImportanceServiceImpl implements DegreImportanceService {

    private final Logger log = LoggerFactory.getLogger(DegreImportanceServiceImpl.class);

    private final DegreImportanceRepository degreImportanceRepository;

    public DegreImportanceServiceImpl(DegreImportanceRepository degreImportanceRepository) {
        this.degreImportanceRepository = degreImportanceRepository;
    }

    /**
     * Save a degreImportance.
     *
     * @param degreImportance the entity to save.
     * @return the persisted entity.
     */
    @Override
    public DegreImportance save(DegreImportance degreImportance) {
        log.debug("Request to save DegreImportance : {}", degreImportance);
        return degreImportanceRepository.save(degreImportance);
    }

    /**
     * Get all the degreImportances.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DegreImportance> findAll(Pageable pageable) {
        log.debug("Request to get all DegreImportances");
        return degreImportanceRepository.findAll(pageable);
    }

    /**
     * Get one degreImportance by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DegreImportance> findOne(Long id) {
        log.debug("Request to get DegreImportance : {}", id);
        return degreImportanceRepository.findById(id);
    }

    /**
     * Delete the degreImportance by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete DegreImportance : {}", id);
        degreImportanceRepository.deleteById(id);
    }
}

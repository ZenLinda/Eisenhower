package com.zenlinda.eisenhower.service.impl;

import com.zenlinda.eisenhower.service.MatriceService;
import com.zenlinda.eisenhower.domain.Matrice;
import com.zenlinda.eisenhower.repository.MatriceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Matrice}.
 */
@Service
@Transactional
public class MatriceServiceImpl implements MatriceService {

    private final Logger log = LoggerFactory.getLogger(MatriceServiceImpl.class);

    private final MatriceRepository matriceRepository;

    public MatriceServiceImpl(MatriceRepository matriceRepository) {
        this.matriceRepository = matriceRepository;
    }

    /**
     * Save a matrice.
     *
     * @param matrice the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Matrice save(Matrice matrice) {
        log.debug("Request to save Matrice : {}", matrice);
        return matriceRepository.save(matrice);
    }

    /**
     * Get all the matrices.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Matrice> findAll(Pageable pageable) {
        log.debug("Request to get all Matrices");
        return matriceRepository.findAll(pageable);
    }

    /**
     * Get one matrice by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Matrice> findOne(Long id) {
        log.debug("Request to get Matrice : {}", id);
        return matriceRepository.findById(id);
    }

    /**
     * Delete the matrice by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Matrice : {}", id);
        matriceRepository.deleteById(id);
    }
}

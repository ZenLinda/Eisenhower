package com.zenlinda.eisenhower.repository;

import com.zenlinda.eisenhower.domain.Matrice;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Matrice entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MatriceRepository extends JpaRepository<Matrice, Long> {
}

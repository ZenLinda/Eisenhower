package com.zenlinda.eisenhower.repository;

import com.zenlinda.eisenhower.domain.DegreImportance;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the DegreImportance entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DegreImportanceRepository extends JpaRepository<DegreImportance, Long> {
}

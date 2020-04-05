package com.zenlinda.eisenhower.repository;

import com.zenlinda.eisenhower.domain.DegreUrgence;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the DegreUrgence entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DegreUrgenceRepository extends JpaRepository<DegreUrgence, Long> {
}

package com.zenlinda.eisenhower.repository;

import com.zenlinda.eisenhower.domain.CategorieTache;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CategorieTache entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CategorieTacheRepository extends JpaRepository<CategorieTache, Long> {
}

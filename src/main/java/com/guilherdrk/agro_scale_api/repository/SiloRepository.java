package com.guilherdrk.agro_scale_api.repository;

import com.guilherdrk.agro_scale_api.domain.entity.Silo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SiloRepository extends JpaRepository<Silo, Integer> {
}

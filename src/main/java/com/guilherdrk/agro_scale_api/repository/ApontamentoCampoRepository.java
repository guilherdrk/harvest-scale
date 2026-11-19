package com.guilherdrk.agro_scale_api.repository;

import com.guilherdrk.agro_scale_api.domain.entity.ApontamentoCampo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ApontamentoCampoRepository extends JpaRepository<ApontamentoCampo, UUID> {
}

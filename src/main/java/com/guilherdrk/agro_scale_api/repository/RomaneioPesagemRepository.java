package com.guilherdrk.agro_scale_api.repository;

import com.guilherdrk.agro_scale_api.domain.entity.RomaneioPesagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RomaneioPesagemRepository extends JpaRepository<RomaneioPesagem, Integer> {
    Optional<RomaneioPesagem> findByUuidOrigemCampo(UUID uuidOrigemCampo);
}

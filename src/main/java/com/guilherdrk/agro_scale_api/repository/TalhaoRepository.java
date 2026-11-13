package com.guilherdrk.agro_scale_api.repository;

import com.guilherdrk.agro_scale_api.domain.entity.Talhao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TalhaoRepository extends JpaRepository<Talhao, Integer> {
}

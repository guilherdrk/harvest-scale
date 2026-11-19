package com.guilherdrk.agro_scale_api.service;

import com.guilherdrk.agro_scale_api.domain.entity.Silo;
import com.guilherdrk.agro_scale_api.dto.SiloRequest;
import com.guilherdrk.agro_scale_api.dto.SiloResponse;
import com.guilherdrk.agro_scale_api.repository.SiloRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SiloService {

    private final SiloRepository siloRepository;

    @Transactional
    public SiloResponse criar(SiloRequest request) {
        Silo silo = Silo.builder()
                .nome(request.nome())
                .capacidadeMaximaKg(request.capacidadeMaximaKg())
                .saldoAtualKg(new BigDecimal("0.00"))
                .build();

        return SiloResponse.fromEntity(siloRepository.save(silo));
    }

    @Transactional(readOnly = true)
    public List<SiloResponse> listarTodos() {
        return siloRepository.findAll()
                .stream()
                .map(SiloResponse::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public SiloResponse buscarPorId(Integer id) {
        Silo silo = siloRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Silo não encontrado com ID: " + id));
        return SiloResponse.fromEntity(silo);
    }
}

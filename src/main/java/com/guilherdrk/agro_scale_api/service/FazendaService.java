package com.guilherdrk.agro_scale_api.service;

import com.guilherdrk.agro_scale_api.domain.entity.Fazenda;
import com.guilherdrk.agro_scale_api.dto.FazendaRequest;
import com.guilherdrk.agro_scale_api.dto.FazendaResponse;
import com.guilherdrk.agro_scale_api.repository.FazendaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FazendaService {

    private final FazendaRepository fazendaRepository;

    @Transactional
    public FazendaResponse criar(FazendaRequest request) {
        String municipio = (request.municipio() != null && !request.municipio().isBlank())
                ? request.municipio()
                : "Uruçuí - PI";

        Fazenda fazenda = Fazenda.builder()
                .nome(request.nome())
                .municipio(municipio)
                .areaTotalHa(request.areaTotalHa())
                .build();

        return FazendaResponse.fromEntity(fazendaRepository.save(fazenda));
    }

    @Transactional(readOnly = true)
    public List<FazendaResponse> listarTodas() {
        return fazendaRepository.findAll()
                .stream()
                .map(FazendaResponse::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public FazendaResponse buscarPorId(Integer id) {
        Fazenda fazenda = fazendaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Fazenda não encontrada com ID: " + id));
        return FazendaResponse.fromEntity(fazenda);
    }
}

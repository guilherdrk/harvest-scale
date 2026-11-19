package com.guilherdrk.agro_scale_api.service;

import com.guilherdrk.agro_scale_api.domain.entity.Fazenda;
import com.guilherdrk.agro_scale_api.domain.entity.Talhao;
import com.guilherdrk.agro_scale_api.dto.TalhaoRequest;
import com.guilherdrk.agro_scale_api.dto.TalhaoResponse;
import com.guilherdrk.agro_scale_api.repository.FazendaRepository;
import com.guilherdrk.agro_scale_api.repository.TalhaoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TalhaoService {

    private final TalhaoRepository talhaoRepository;
    private final FazendaRepository fazendaRepository;

    @Transactional
    public TalhaoResponse criar(TalhaoRequest request) {
        Fazenda fazenda = fazendaRepository.findById(request.fazendaId())
                .orElseThrow(() -> new EntityNotFoundException("Fazenda não encontrada com ID: " + request.fazendaId()));

        Talhao talhao = Talhao.builder()
                .fazenda(fazenda)
                .codigo(request.codigo().toUpperCase())
                .areaHa(request.areaHa())
                .variedadeSemente(request.variedadeSemente())
                .build();

        return TalhaoResponse.fromEntity(talhaoRepository.save(talhao));
    }

    @Transactional(readOnly = true)
    public List<TalhaoResponse> listarTodos() {
        return talhaoRepository.findAll()
                .stream()
                .map(TalhaoResponse::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public TalhaoResponse buscarPorId(Integer id) {
        Talhao talhao = talhaoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Talhão não encontrado com ID: " + id));
        return TalhaoResponse.fromEntity(talhao);
    }
}

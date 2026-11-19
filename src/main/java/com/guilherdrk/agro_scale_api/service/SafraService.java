package com.guilherdrk.agro_scale_api.service;

import com.guilherdrk.agro_scale_api.domain.entity.Safra;
import com.guilherdrk.agro_scale_api.dto.SafraRequest;
import com.guilherdrk.agro_scale_api.dto.SafraResponse;
import com.guilherdrk.agro_scale_api.repository.SafraRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SafraService {

    private final SafraRepository safraRepository;

    @Transactional
    public SafraResponse criar(SafraRequest request) {
        Safra safra = Safra.builder()
                .nome(request.nome())
                .cultura(request.cultura().toUpperCase())
                .umidadePadrao(request.umidadePadrao() != null ? request.umidadePadrao() : new BigDecimal("14.00"))
                .impurezaPadrao(request.impurezaPadrao() != null ? request.impurezaPadrao() : new BigDecimal("1.00"))
                .dataInicio(request.dataInicio())
                .dataFim(request.dataFim())
                .ativa(true)
                .build();

        return SafraResponse.fromEntity(safraRepository.save(safra));
    }

    @Transactional(readOnly = true)
    public List<SafraResponse> listarTodas() {
        return safraRepository.findAll()
                .stream()
                .map(SafraResponse::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public SafraResponse buscarPorId(Integer id) {
        Safra safra = safraRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Safra não encontrada com ID: " + id));
        return SafraResponse.fromEntity(safra);
    }
}

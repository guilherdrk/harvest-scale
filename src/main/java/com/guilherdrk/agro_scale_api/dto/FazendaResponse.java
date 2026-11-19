package com.guilherdrk.agro_scale_api.dto;

import com.guilherdrk.agro_scale_api.domain.entity.Fazenda;

import java.math.BigDecimal;

public record FazendaResponse(
        Integer id,
        String nome,
        String municipio,
        BigDecimal areaTotalHa
) {
    public static FazendaResponse fromEntity(Fazenda fazenda) {
        return new FazendaResponse(
                fazenda.getId(),
                fazenda.getNome(),
                fazenda.getMunicipio(),
                fazenda.getAreaTotalHa()
        );
    }
}

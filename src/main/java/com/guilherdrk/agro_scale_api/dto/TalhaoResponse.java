package com.guilherdrk.agro_scale_api.dto;

import com.guilherdrk.agro_scale_api.domain.entity.Talhao;

import java.math.BigDecimal;

public record TalhaoResponse(
        Integer id,
        Integer fazendaId,
        String fazendaNome,
        String codigo,
        BigDecimal areaHa,
        String variedadeSemente
) {
    public static TalhaoResponse fromEntity(Talhao talhao) {
        return new TalhaoResponse(
                talhao.getId(),
                talhao.getFazenda().getId(),
                talhao.getFazenda().getNome(),
                talhao.getCodigo(),
                talhao.getAreaHa(),
                talhao.getVariedadeSemente()
        );
    }
}

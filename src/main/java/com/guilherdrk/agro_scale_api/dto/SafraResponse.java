package com.guilherdrk.agro_scale_api.dto;

import com.guilherdrk.agro_scale_api.domain.entity.Safra;

import java.math.BigDecimal;
import java.time.LocalDate;

public record SafraResponse(
        Integer id,
        String nome,
        String cultura,
        BigDecimal umidadePadrao,
        BigDecimal impurezaPadrao,
        LocalDate dataInicio,
        LocalDate dataFim,
        Boolean ativa
) {
    public static SafraResponse fromEntity(Safra safra) {
        return new SafraResponse(
                safra.getId(),
                safra.getNome(),
                safra.getCultura(),
                safra.getUmidadePadrao(),
                safra.getImpurezaPadrao(),
                safra.getDataInicio(),
                safra.getDataFim(),
                safra.getAtiva()
        );
    }
}

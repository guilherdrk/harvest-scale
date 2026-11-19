package com.guilherdrk.agro_scale_api.dto;

import java.math.BigDecimal;

public record ProdutividadeTalhaoResponse(
        Integer talhaoId,
        String talhaoCodigo,
        String fazendaNome,
        BigDecimal areaHa,
        Long totalRomaneios,
        BigDecimal totalPesoLiquidoSecoKg,
        BigDecimal totalSacas,
        BigDecimal produtividadeSacasPorHa
) {
}

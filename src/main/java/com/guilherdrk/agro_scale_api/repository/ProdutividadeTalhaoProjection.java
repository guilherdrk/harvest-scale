package com.guilherdrk.agro_scale_api.repository;

import java.math.BigDecimal;

public interface ProdutividadeTalhaoProjection {
    Integer getTalhaoId();
    String getTalhaoCodigo();
    String getFazendaNome();
    BigDecimal getAreaHa();
    Long getTotalRomaneios();
    BigDecimal getTotalPesoLiquidoSecoKg();
}

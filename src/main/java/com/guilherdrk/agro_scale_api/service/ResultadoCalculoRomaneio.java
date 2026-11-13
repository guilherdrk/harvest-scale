package com.guilherdrk.agro_scale_api.service;

import java.math.BigDecimal;

public record ResultadoCalculoRomaneio(
                BigDecimal pesoLiquidoInicialKg,
                BigDecimal descontoImpurezaKg,
                BigDecimal descontoUmidadeKg,
                BigDecimal pesoLiquidoSecoKg,
                BigDecimal quantidadeSacas) {
}

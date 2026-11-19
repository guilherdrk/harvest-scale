package com.guilherdrk.agro_scale_api.dto;

import com.guilherdrk.agro_scale_api.domain.entity.Silo;

import java.math.BigDecimal;
import java.math.RoundingMode;

public record SiloResponse(
        Integer id,
        String nome,
        BigDecimal capacidadeMaximaKg,
        BigDecimal saldoAtualKg,
        BigDecimal capacidadeDisponivelKg,
        BigDecimal percentualOcupacao
) {
    private static final BigDecimal ZERO = new BigDecimal("0.00");
    private static final BigDecimal CEM = new BigDecimal("100.00");

    public static SiloResponse fromEntity(Silo silo) {
        BigDecimal saldoAtual = silo.getSaldoAtualKg() != null ? silo.getSaldoAtualKg() : ZERO;
        BigDecimal capacidadeMaxima = silo.getCapacidadeMaximaKg();
        BigDecimal capacidadeDisponivel = capacidadeMaxima.subtract(saldoAtual).setScale(2, RoundingMode.HALF_UP);

        BigDecimal percentualOcupacao = ZERO;
        if (capacidadeMaxima.compareTo(BigDecimal.ZERO) > 0) {
            percentualOcupacao = saldoAtual.multiply(CEM)
                    .divide(capacidadeMaxima, 2, RoundingMode.HALF_UP);
        }

        return new SiloResponse(
                silo.getId(),
                silo.getNome(),
                capacidadeMaxima,
                saldoAtual,
                capacidadeDisponivel,
                percentualOcupacao
        );
    }
}

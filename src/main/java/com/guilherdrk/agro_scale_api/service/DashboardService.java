package com.guilherdrk.agro_scale_api.service;

import com.guilherdrk.agro_scale_api.dto.ProdutividadeTalhaoResponse;
import com.guilherdrk.agro_scale_api.repository.ProdutividadeTalhaoProjection;
import com.guilherdrk.agro_scale_api.repository.TalhaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private static final BigDecimal PESO_SACA_KG = new BigDecimal("60.00");
    private static final BigDecimal ZERO = new BigDecimal("0.00");

    private final TalhaoRepository talhaoRepository;

    @Transactional(readOnly = true)
    public List<ProdutividadeTalhaoResponse> obterProdutividadePorTalhao(Integer safraId) {
        return talhaoRepository.calcularProdutividadePorTalhao(safraId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private ProdutividadeTalhaoResponse toResponse(ProdutividadeTalhaoProjection projection) {
        BigDecimal totalPlslKg = projection.getTotalPesoLiquidoSecoKg() != null
                ? projection.getTotalPesoLiquidoSecoKg().setScale(2, RoundingMode.HALF_UP)
                : ZERO;

        BigDecimal totalSacas = totalPlslKg.divide(PESO_SACA_KG, 2, RoundingMode.HALF_UP);

        BigDecimal produtividadeSacasPorHa = ZERO;
        if (projection.getAreaHa() != null && projection.getAreaHa().compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal divisor = PESO_SACA_KG.multiply(projection.getAreaHa());
            produtividadeSacasPorHa = totalPlslKg.divide(divisor, 2, RoundingMode.HALF_UP);
        }

        return new ProdutividadeTalhaoResponse(
                projection.getTalhaoId(),
                projection.getTalhaoCodigo(),
                projection.getFazendaNome(),
                projection.getAreaHa(),
                projection.getTotalRomaneios(),
                totalPlslKg,
                totalSacas,
                produtividadeSacasPorHa
        );
    }
}

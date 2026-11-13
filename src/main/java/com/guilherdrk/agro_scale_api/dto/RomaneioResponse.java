package com.guilherdrk.agro_scale_api.dto;

import com.guilherdrk.agro_scale_api.domain.entity.RomaneioPesagem;
import com.guilherdrk.agro_scale_api.domain.entity.StatusRomaneio;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.UUID;

public record RomaneioResponse(
        Integer id,
        UUID uuidOrigemCampo,
        Integer safraId,
        String safraNome,
        Integer talhaoId,
        String talhaoCodigo,
        Integer siloDestinoId,
        String siloDestinoNome,
        String placaVeiculo,
        String motorista,
        BigDecimal pesoBrutoKg,
        BigDecimal pesoTaraKg,
        BigDecimal pesoLiquidoInicialKg,
        BigDecimal umidadePercentual,
        BigDecimal impurezaPercentual,
        BigDecimal descontoImpurezaKg,
        BigDecimal descontoUmidadeKg,
        BigDecimal pesoLiquidoSecoKg,
        BigDecimal quantidadeSacas,
        StatusRomaneio status,
        LocalDateTime dataEntrada,
        LocalDateTime dataSaida
) {
    private static final BigDecimal PESO_SACA_KG = new BigDecimal("60.00");

    public static RomaneioResponse fromEntity(RomaneioPesagem romaneio) {
        BigDecimal sacas = null;
        if (romaneio.getPesoLiquidoSecoKg() != null) {
            sacas = romaneio.getPesoLiquidoSecoKg().divide(PESO_SACA_KG, 2, RoundingMode.HALF_UP);
        }

        return new RomaneioResponse(
                romaneio.getId(),
                romaneio.getUuidOrigemCampo(),
                romaneio.getSafra().getId(),
                romaneio.getSafra().getNome(),
                romaneio.getTalhao().getId(),
                romaneio.getTalhao().getCodigo(),
                romaneio.getSiloDestino().getId(),
                romaneio.getSiloDestino().getNome(),
                romaneio.getPlacaVeiculo(),
                romaneio.getMotorista(),
                romaneio.getPesoBrutoKg(),
                romaneio.getPesoTaraKg(),
                romaneio.getPesoLiquidoInicialKg(),
                romaneio.getUmidadePercentual(),
                romaneio.getImpurezaPercentual(),
                romaneio.getDescontoImpurezaKg(),
                romaneio.getDescontoUmidadeKg(),
                romaneio.getPesoLiquidoSecoKg(),
                sacas,
                romaneio.getStatus(),
                romaneio.getDataEntrada(),
                romaneio.getDataSaida()
        );
    }
}

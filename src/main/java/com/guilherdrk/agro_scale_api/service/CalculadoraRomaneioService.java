package com.guilherdrk.agro_scale_api.service;

import com.guilherdrk.agro_scale_api.domain.entity.RomaneioPesagem;
import com.guilherdrk.agro_scale_api.domain.entity.Safra;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class CalculadoraRomaneioService {

    private static final int ESCALA = 2;
    private static final RoundingMode MODO_ARREDONDAMENTO = RoundingMode.HALF_UP;
    private static final BigDecimal CEM = new BigDecimal("100.00");
    private static final BigDecimal PESO_SACA_KG = new BigDecimal("60.00");
    private static final BigDecimal ZERO = new BigDecimal("0.00");

    public ResultadoCalculoRomaneio calcular(
            BigDecimal pesoBrutoKg,
            BigDecimal pesoTaraKg,
            BigDecimal umidadeMedida,
            BigDecimal umidadePadrao,
            BigDecimal impurezaMedida,
            BigDecimal impurezaPadrao) {
        if (pesoBrutoKg == null || pesoTaraKg == null) {
            throw new IllegalArgumentException("Peso bruto e peso tara são obrigatórios.");
        }
        if (pesoBrutoKg.compareTo(pesoTaraKg) < 0) {
            throw new IllegalArgumentException("O peso bruto não pode ser menor que a tara.");
        }

        BigDecimal pli = pesoBrutoKg.subtract(pesoTaraKg)
                .setScale(ESCALA, MODO_ARREDONDAMENTO);

        BigDecimal di = ZERO;
        if (impurezaMedida != null && impurezaPadrao != null && impurezaMedida.compareTo(impurezaPadrao) > 0) {
            BigDecimal excedenteImpureza = impurezaMedida.subtract(impurezaPadrao);
            di = pli.multiply(excedenteImpureza)
                    .divide(CEM, ESCALA, MODO_ARREDONDAMENTO);
        }

        BigDecimal du = ZERO;
        if (umidadeMedida != null && umidadePadrao != null && umidadeMedida.compareTo(umidadePadrao) > 0) {
            BigDecimal baseLimpa = pli.subtract(di);
            BigDecimal excedenteUmidade = umidadeMedida.subtract(umidadePadrao);
            BigDecimal divisorUmidade = CEM.subtract(umidadePadrao);

            du = baseLimpa.multiply(excedenteUmidade)
                    .divide(divisorUmidade, ESCALA, MODO_ARREDONDAMENTO);
        }

        BigDecimal plsl = pli.subtract(di).subtract(du)
                .setScale(ESCALA, MODO_ARREDONDAMENTO);

        BigDecimal sacas = plsl.divide(PESO_SACA_KG, ESCALA, MODO_ARREDONDAMENTO);

        return new ResultadoCalculoRomaneio(pli, di, du, plsl, sacas);
    }

    public ResultadoCalculoRomaneio aplicarCalculo(RomaneioPesagem romaneio) {
        Safra safra = romaneio.getSafra();
        if (safra == null) {
            throw new IllegalArgumentException("A safra do romaneio é obrigatória para o cálculo de classificação.");
        }

        ResultadoCalculoRomaneio resultado = calcular(
                romaneio.getPesoBrutoKg(),
                romaneio.getPesoTaraKg(),
                romaneio.getUmidadePercentual(),
                safra.getUmidadePadrao(),
                romaneio.getImpurezaPercentual(),
                safra.getImpurezaPadrao());

        romaneio.setPesoLiquidoInicialKg(resultado.pesoLiquidoInicialKg());
        romaneio.setDescontoImpurezaKg(resultado.descontoImpurezaKg());
        romaneio.setDescontoUmidadeKg(resultado.descontoUmidadeKg());
        romaneio.setPesoLiquidoSecoKg(resultado.pesoLiquidoSecoKg());

        return resultado;
    }
}

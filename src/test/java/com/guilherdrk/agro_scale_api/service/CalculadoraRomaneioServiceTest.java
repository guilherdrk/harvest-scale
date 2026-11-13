package com.guilherdrk.agro_scale_api.service;

import com.guilherdrk.agro_scale_api.domain.entity.RomaneioPesagem;
import com.guilherdrk.agro_scale_api.domain.entity.Safra;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CalculadoraRomaneioServiceTest {

    private CalculadoraRomaneioService calculadoraService;

    @BeforeEach
    void setUp() {
        calculadoraService = new CalculadoraRomaneioService();
    }

    @Test
    @DisplayName("Deve calcular corretamente PLI, DI, DU, PLSL e sacas para o cenário oficial do CONTEXT.md")
    void deveCalcularRomaneioComDescontosDeImpurezaEUmidade() {
        BigDecimal pesoBruto = new BigDecimal("52000.00");
        BigDecimal pesoTara = new BigDecimal("15000.00");
        BigDecimal umidadeMedida = new BigDecimal("17.00");
        BigDecimal umidadePadrao = new BigDecimal("14.00");
        BigDecimal impurezaMedida = new BigDecimal("2.00");
        BigDecimal impurezaPadrao = new BigDecimal("1.00");

        ResultadoCalculoRomaneio resultado = calculadoraService.calcular(
                pesoBruto,
                pesoTara,
                umidadeMedida,
                umidadePadrao,
                impurezaMedida,
                impurezaPadrao);

        assertEquals(new BigDecimal("37000.00"), resultado.pesoLiquidoInicialKg());
        assertEquals(new BigDecimal("370.00"), resultado.descontoImpurezaKg());
        assertEquals(new BigDecimal("1277.79"), resultado.descontoUmidadeKg());
        assertEquals(new BigDecimal("35352.21"), resultado.pesoLiquidoSecoKg());
        assertEquals(new BigDecimal("589.20"), resultado.quantidadeSacas());
    }

    @Test
    @DisplayName("Não deve aplicar descontos quando umidade e impureza estiverem dentro do padrão")
    void naoDeveAplicarDescontosQuandoDentroDoPadrao() {
        ResultadoCalculoRomaneio resultado = calculadoraService.calcular(
                new BigDecimal("50000.00"),
                new BigDecimal("14000.00"),
                new BigDecimal("13.50"),
                new BigDecimal("14.00"),
                new BigDecimal("0.80"),
                new BigDecimal("1.00"));

        assertEquals(new BigDecimal("36000.00"), resultado.pesoLiquidoInicialKg());
        assertEquals(new BigDecimal("0.00"), resultado.descontoImpurezaKg());
        assertEquals(new BigDecimal("0.00"), resultado.descontoUmidadeKg());
        assertEquals(new BigDecimal("36000.00"), resultado.pesoLiquidoSecoKg());
        assertEquals(new BigDecimal("600.00"), resultado.quantidadeSacas());
    }

    @Test
    @DisplayName("Deve preencher corretamente a entidade RomaneioPesagem ao aplicar o cálculo")
    void deveAplicarCalculoNaEntidadeRomaneioPesagem() {
        Safra safra = Safra.builder()
                .nome("Safra Soja 2026/2027")
                .cultura("SOJA")
                .umidadePadrao(new BigDecimal("14.00"))
                .impurezaPadrao(new BigDecimal("1.00"))
                .build();

        RomaneioPesagem romaneio = RomaneioPesagem.builder()
                .safra(safra)
                .pesoBrutoKg(new BigDecimal("52000.00"))
                .pesoTaraKg(new BigDecimal("15000.00"))
                .umidadePercentual(new BigDecimal("17.00"))
                .impurezaPercentual(new BigDecimal("2.00"))
                .build();

        ResultadoCalculoRomaneio resultado = calculadoraService.aplicarCalculo(romaneio);

        assertEquals(new BigDecimal("37000.00"), romaneio.getPesoLiquidoInicialKg());
        assertEquals(new BigDecimal("370.00"), romaneio.getDescontoImpurezaKg());
        assertEquals(new BigDecimal("1277.79"), romaneio.getDescontoUmidadeKg());
        assertEquals(new BigDecimal("35352.21"), romaneio.getPesoLiquidoSecoKg());
        assertEquals(new BigDecimal("589.20"), resultado.quantidadeSacas());
    }

    @Test
    @DisplayName("Deve lançar exceção quando o peso bruto for menor que a tara")
    void deveLancarExcecaoQuandoPesoBrutoMenorQueTara() {
        assertThrows(IllegalArgumentException.class, () -> calculadoraService.calcular(
                new BigDecimal("10000.00"),
                new BigDecimal("15000.00"),
                new BigDecimal("14.00"),
                new BigDecimal("14.00"),
                new BigDecimal("1.00"),
                new BigDecimal("1.00")));
    }
}

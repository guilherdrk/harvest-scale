package com.guilherdrk.agro_scale_api.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record FechamentoRomaneioRequest(
        @NotNull(message = "O peso tara é obrigatório")
        @Positive(message = "O peso tara deve ser maior que zero")
        BigDecimal pesoTaraKg,

        @NotNull(message = "O percentual de umidade é obrigatório")
        @DecimalMin(value = "0.00", message = "A umidade não pode ser negativa")
        @DecimalMax(value = "99.99", message = "A umidade deve ser menor que 100%")
        BigDecimal umidadePercentual,

        @NotNull(message = "O percentual de impureza é obrigatório")
        @DecimalMin(value = "0.00", message = "A impureza não pode ser negativa")
        @DecimalMax(value = "99.99", message = "A impureza deve ser menor que 100%")
        BigDecimal impurezaPercentual
) {
}

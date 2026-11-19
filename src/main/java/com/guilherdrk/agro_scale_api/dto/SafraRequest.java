package com.guilherdrk.agro_scale_api.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public record SafraRequest(
        @NotBlank(message = "O nome da safra é obrigatório")
        @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres")
        String nome,

        @NotBlank(message = "A cultura é obrigatória")
        @Size(max = 50, message = "A cultura deve ter no máximo 50 caracteres")
        String cultura,

        @DecimalMin(value = "0.00", message = "A umidade padrão não pode ser negativa")
        @DecimalMax(value = "99.99", message = "A umidade padrão deve ser menor que 100%")
        BigDecimal umidadePadrao,

        @DecimalMin(value = "0.00", message = "A impureza padrão não pode ser negativa")
        @DecimalMax(value = "99.99", message = "A impureza padrão deve ser menor que 100%")
        BigDecimal impurezaPadrao,

        @NotNull(message = "A data de início é obrigatória")
        LocalDate dataInicio,

        LocalDate dataFim
) {
}

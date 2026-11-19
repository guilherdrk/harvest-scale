package com.guilherdrk.agro_scale_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record FazendaRequest(
        @NotBlank(message = "O nome da fazenda é obrigatório")
        @Size(max = 120, message = "O nome da fazenda deve ter no máximo 120 caracteres")
        String nome,

        @Size(max = 80, message = "O município deve ter no máximo 80 caracteres")
        String municipio,

        @NotNull(message = "A área total em hectares é obrigatória")
        @Positive(message = "A área total deve ser maior que zero")
        BigDecimal areaTotalHa
) {
}

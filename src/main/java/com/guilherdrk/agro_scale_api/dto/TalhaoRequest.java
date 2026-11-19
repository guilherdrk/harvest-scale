package com.guilherdrk.agro_scale_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record TalhaoRequest(
        @NotNull(message = "O ID da fazenda é obrigatório")
        Integer fazendaId,

        @NotBlank(message = "O código do talhão é obrigatório")
        @Size(max = 30, message = "O código do talhão deve ter no máximo 30 caracteres")
        String codigo,

        @NotNull(message = "A área do talhão em hectares é obrigatória")
        @Positive(message = "A área do talhão deve ser maior que zero")
        BigDecimal areaHa,

        @Size(max = 80, message = "A variedade da semente deve ter no máximo 80 caracteres")
        String variedadeSemente
) {
}

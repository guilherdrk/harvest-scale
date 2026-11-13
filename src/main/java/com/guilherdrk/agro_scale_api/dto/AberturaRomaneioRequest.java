package com.guilherdrk.agro_scale_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.UUID;

public record AberturaRomaneioRequest(
        UUID uuidOrigemCampo,

        @NotNull(message = "O ID da safra é obrigatório")
        Integer safraId,

        @NotNull(message = "O ID do talhão é obrigatório")
        Integer talhaoId,

        @NotNull(message = "O ID do silo de destino é obrigatório")
        Integer siloDestinoId,

        @NotBlank(message = "A placa do veículo é obrigatória")
        @Size(max = 10, message = "A placa do veículo deve ter no máximo 10 caracteres")
        String placaVeiculo,

        @Size(max = 100, message = "O nome do motorista deve ter no máximo 100 caracteres")
        String motorista,

        @NotNull(message = "O peso bruto é obrigatório")
        @Positive(message = "O peso bruto deve ser maior que zero")
        BigDecimal pesoBrutoKg
) {
}

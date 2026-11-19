package com.guilherdrk.agro_scale_api.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record SiloRequest(
        @NotBlank @Size(max = 80) String nome,
        @NotNull @Positive BigDecimal capacidadeMaximaKg) {
}

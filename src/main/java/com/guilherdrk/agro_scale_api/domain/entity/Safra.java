package com.guilherdrk.agro_scale_api.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "safras") 
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Safra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, length = 50)
    private String cultura;

    @Builder.Default
    @Column(name = "umidade_padrao", precision = 5, scale = 2)
    private BigDecimal umidadePadrao = new BigDecimal("14.00");

    @Builder.Default
    @Column(name = "impureza_padrao", precision = 5, scale = 2)
    private BigDecimal impurezaPadrao = new BigDecimal("1.00");

    @Column(name = "data_inicio", nullable = false)
    private LocalDate dataInicio;

    @Column(name = "data_fim")
    private LocalDate dataFim;

    @Builder.Default
    @Column(name = "ativa")
    private Boolean ativa = true;
}

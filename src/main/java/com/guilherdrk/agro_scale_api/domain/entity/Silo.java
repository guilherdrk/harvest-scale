package com.guilherdrk.agro_scale_api.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "silos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Silo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 80)
    private String nome;

    @Column(name = "capacidade_maxima_kg", nullable = false, precision = 14, scale = 2)
    private BigDecimal capacidadeMaximaKg;

    @Builder.Default
    @Column(name = "saldo_atual_kg", precision = 14, scale = 2)
    private BigDecimal saldoAtualKg = new BigDecimal("0.00");
}

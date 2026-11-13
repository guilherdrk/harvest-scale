package com.guilherdrk.agro_scale_api.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "fazendas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Fazenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 120)
    private String nome;

    @Builder.Default
    @Column(length = 80)
    private String municipio = "Uruçuí - PI";

    @Column(name = "area_total_ha", nullable = false, precision = 10, scale = 2)
    private BigDecimal areaTotalHa;
}

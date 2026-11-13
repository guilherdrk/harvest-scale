package com.guilherdrk.agro_scale_api.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "talhoes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Talhao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fazenda_id", nullable = false)
    private Fazenda fazenda;

    @Column(nullable = false, length = 30)
    private String codigo;

    @Column(name = "area_ha", nullable = false, precision = 10, scale = 2)
    private BigDecimal areaHa;

    @Column(name = "variedade_semente", length = 80)
    private String variedadeSemente;
}

package com.guilherdrk.agro_scale_api.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "apontamentos_campo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApontamentoCampo {

    // UUID gerado offline no dispositivo Android (chave idempotente)
    @Id
    @Column(name = "uuid", nullable = false, updatable = false)
    private UUID uuid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "talhao_id", nullable = false)
    private Talhao talhao;

    @Column(name = "tipo_ocorrencia", nullable = false, length = 50)
    private String tipoOcorrencia;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @Column(precision = 10, scale = 7)
    private BigDecimal latitude;

    @Column(precision = 10, scale = 7)
    private BigDecimal longitude;

    @Column(name = "criado_em_dispositivo", nullable = false)
    private LocalDateTime criadoEmDispositivo;

    @Builder.Default
    @Column(name = "sincronizado_em")
    private LocalDateTime sincronizadoEm = LocalDateTime.now();
}

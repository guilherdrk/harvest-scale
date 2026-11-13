package com.guilherdrk.agro_scale_api.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "romaneios_pesagem")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RomaneioPesagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "uuid_origem_campo", unique = true)
    private UUID uuidOrigemCampo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "safra_id", nullable = false)
    private Safra safra;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "talhao_id", nullable = false)
    private Talhao talhao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "silo_destino_id", nullable = false)
    private Silo siloDestino;

    @Column(name = "placa_veiculo", nullable = false, length = 10)
    private String placaVeiculo;

    @Column(length = 100)
    private String motorista;

    @Column(name = "peso_bruto_kg", nullable = false, precision = 12, scale = 2)
    private BigDecimal pesoBrutoKg;

    @Column(name = "peso_tara_kg", precision = 12, scale = 2)
    private BigDecimal pesoTaraKg;

    @Column(name = "peso_liquido_inicial_kg", precision = 12, scale = 2)
    private BigDecimal pesoLiquidoInicialKg;

    @Column(name = "umidade_percentual", precision = 5, scale = 2)
    private BigDecimal umidadePercentual;

    @Column(name = "impureza_percentual", precision = 5, scale = 2)
    private BigDecimal impurezaPercentual;

    @Builder.Default
    @Column(name = "desconto_impureza_kg", precision = 12, scale = 2)
    private BigDecimal descontoImpurezaKg = new BigDecimal("0.00");

    @Builder.Default
    @Column(name = "desconto_umidade_kg", precision = 12, scale = 2)
    private BigDecimal descontoUmidadeKg = new BigDecimal("0.00");

    @Column(name = "peso_liquido_seco_kg", precision = 12, scale = 2)
    private BigDecimal pesoLiquidoSecoKg;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private StatusRomaneio status = StatusRomaneio.AGUARDANDO_TARA;

    @Builder.Default
    @Column(name = "data_entrada")
    private LocalDateTime dataEntrada = LocalDateTime.now();

    @Column(name = "data_saida")
    private LocalDateTime dataSaida;
}

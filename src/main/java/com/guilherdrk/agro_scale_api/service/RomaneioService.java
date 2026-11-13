package com.guilherdrk.agro_scale_api.service;

import com.guilherdrk.agro_scale_api.domain.entity.*;
import com.guilherdrk.agro_scale_api.dto.AberturaRomaneioRequest;
import com.guilherdrk.agro_scale_api.dto.FechamentoRomaneioRequest;
import com.guilherdrk.agro_scale_api.dto.RomaneioResponse;
import com.guilherdrk.agro_scale_api.repository.RomaneioPesagemRepository;
import com.guilherdrk.agro_scale_api.repository.SafraRepository;
import com.guilherdrk.agro_scale_api.repository.SiloRepository;
import com.guilherdrk.agro_scale_api.repository.TalhaoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RomaneioService {

    private final RomaneioPesagemRepository romaneioRepository;
    private final SafraRepository safraRepository;
    private final TalhaoRepository talhaoRepository;
    private final SiloRepository siloRepository;
    private final CalculadoraRomaneioService calculadoraService;

    @Transactional
    public RomaneioResponse abrirRomaneio(AberturaRomaneioRequest request) {
        Safra safra = safraRepository.findById(request.safraId())
                .orElseThrow(() -> new EntityNotFoundException("Safra não encontrada com ID: " + request.safraId()));

        if (Boolean.FALSE.equals(safra.getAtiva())) {
            throw new IllegalStateException("Não é possível abrir romaneio para uma safra inativa.");
        }

        Talhao talhao = talhaoRepository.findById(request.talhaoId())
                .orElseThrow(() -> new EntityNotFoundException("Talhão não encontrado com ID: " + request.talhaoId()));

        Silo silo = siloRepository.findById(request.siloDestinoId())
                .orElseThrow(() -> new EntityNotFoundException("Silo não encontrado com ID: " + request.siloDestinoId()));

        RomaneioPesagem romaneio = RomaneioPesagem.builder()
                .uuidOrigemCampo(request.uuidOrigemCampo())
                .safra(safra)
                .talhao(talhao)
                .siloDestino(silo)
                .placaVeiculo(request.placaVeiculo().toUpperCase())
                .motorista(request.motorista())
                .pesoBrutoKg(request.pesoBrutoKg())
                .status(StatusRomaneio.AGUARDANDO_TARA)
                .dataEntrada(LocalDateTime.now())
                .build();

        RomaneioPesagem salvo = romaneioRepository.save(romaneio);
        return RomaneioResponse.fromEntity(salvo);
    }

    @Transactional
    public RomaneioResponse fecharRomaneio(Integer id, FechamentoRomaneioRequest request) {
        RomaneioPesagem romaneio = romaneioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Romaneio não encontrado com ID: " + id));

        if (romaneio.getStatus() != StatusRomaneio.AGUARDANDO_TARA) {
            throw new IllegalStateException("Apenas romaneios com status AGUARDANDO_TARA podem ser finalizados. Status atual: " + romaneio.getStatus());
        }

        romaneio.setPesoTaraKg(request.pesoTaraKg());
        romaneio.setUmidadePercentual(request.umidadePercentual());
        romaneio.setImpurezaPercentual(request.impurezaPercentual());

        // Calcula PLI, DI, DU e PLSL e preenche o romaneio
        ResultadoCalculoRomaneio resultado = calculadoraService.aplicarCalculo(romaneio);

        // Atualiza o saldo do Silo de destino com o Peso Líquido Seco e Limpo (PLSL)
        Silo silo = romaneio.getSiloDestino();
        BigDecimal saldoAtual = silo.getSaldoAtualKg() != null ? silo.getSaldoAtualKg() : new BigDecimal("0.00");
        BigDecimal novoSaldo = saldoAtual.add(resultado.pesoLiquidoSecoKg());

        if (novoSaldo.compareTo(silo.getCapacidadeMaximaKg()) > 0) {
            throw new IllegalStateException(String.format(
                    "Capacidade máxima do silo '%s' excedida. Capacidade: %s kg, Saldo após descarga: %s kg.",
                    silo.getNome(), silo.getCapacidadeMaximaKg(), novoSaldo
            ));
        }

        silo.setSaldoAtualKg(novoSaldo);
        siloRepository.save(silo);

        romaneio.setStatus(StatusRomaneio.FINALIZADO);
        romaneio.setDataSaida(LocalDateTime.now());

        RomaneioPesagem salvo = romaneioRepository.save(romaneio);
        return RomaneioResponse.fromEntity(salvo);
    }

    @Transactional(readOnly = true)
    public List<RomaneioResponse> listarTodos() {
        return romaneioRepository.findAll()
                .stream()
                .map(RomaneioResponse::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public RomaneioResponse buscarPorId(Integer id) {
        RomaneioPesagem romaneio = romaneioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Romaneio não encontrado com ID: " + id));
        return RomaneioResponse.fromEntity(romaneio);
    }
}

package com.guilherdrk.agro_scale_api.controller;

import com.guilherdrk.agro_scale_api.dto.AberturaRomaneioRequest;
import com.guilherdrk.agro_scale_api.dto.FechamentoRomaneioRequest;
import com.guilherdrk.agro_scale_api.dto.RomaneioResponse;
import com.guilherdrk.agro_scale_api.service.RomaneioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/romaneios")
@RequiredArgsConstructor
public class RomaneioController {

    private final RomaneioService romaneioService;

    @PostMapping
    public ResponseEntity<RomaneioResponse> abrirRomaneio(@Valid @RequestBody AberturaRomaneioRequest request) {
        RomaneioResponse response = romaneioService.abrirRomaneio(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("/{id}/fechamento")
    public ResponseEntity<RomaneioResponse> fecharRomaneio(
            @PathVariable Integer id,
            @Valid @RequestBody FechamentoRomaneioRequest request
    ) {
        RomaneioResponse response = romaneioService.fecharRomaneio(id, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<RomaneioResponse>> listarTodos() {
        return ResponseEntity.ok(romaneioService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RomaneioResponse> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(romaneioService.buscarPorId(id));
    }
}

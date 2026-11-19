package com.guilherdrk.agro_scale_api.controller;

import com.guilherdrk.agro_scale_api.dto.FazendaRequest;
import com.guilherdrk.agro_scale_api.dto.FazendaResponse;
import com.guilherdrk.agro_scale_api.service.FazendaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/fazendas")
@RequiredArgsConstructor
public class FazendaController {

    private final FazendaService fazendaService;

    @PostMapping
    public ResponseEntity<FazendaResponse> criar(@Valid @RequestBody FazendaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(fazendaService.criar(request));
    }

    @GetMapping
    public ResponseEntity<List<FazendaResponse>> listarTodas() {
        return ResponseEntity.ok(fazendaService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FazendaResponse> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(fazendaService.buscarPorId(id));
    }
}

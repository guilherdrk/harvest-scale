package com.guilherdrk.agro_scale_api.controller;

import com.guilherdrk.agro_scale_api.dto.SiloRequest;
import com.guilherdrk.agro_scale_api.dto.SiloResponse;
import com.guilherdrk.agro_scale_api.service.SiloService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/silos")
@RequiredArgsConstructor
public class SiloController {

    private final SiloService siloService;

    @PostMapping
    public ResponseEntity<SiloResponse> criar(@Valid @RequestBody SiloRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(siloService.criar(request));
    }

    @GetMapping
    public ResponseEntity<List<SiloResponse>> listarTodos() {
        return ResponseEntity.ok(siloService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SiloResponse> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(siloService.buscarPorId(id));
    }
}

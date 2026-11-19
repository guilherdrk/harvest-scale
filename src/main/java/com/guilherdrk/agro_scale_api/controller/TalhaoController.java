package com.guilherdrk.agro_scale_api.controller;

import com.guilherdrk.agro_scale_api.dto.TalhaoRequest;
import com.guilherdrk.agro_scale_api.dto.TalhaoResponse;
import com.guilherdrk.agro_scale_api.service.TalhaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/talhoes")
@RequiredArgsConstructor
public class TalhaoController {

    private final TalhaoService talhaoService;

    @PostMapping
    public ResponseEntity<TalhaoResponse> criar(@Valid @RequestBody TalhaoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(talhaoService.criar(request));
    }

    @GetMapping
    public ResponseEntity<List<TalhaoResponse>> listarTodos() {
        return ResponseEntity.ok(talhaoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TalhaoResponse> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(talhaoService.buscarPorId(id));
    }
}

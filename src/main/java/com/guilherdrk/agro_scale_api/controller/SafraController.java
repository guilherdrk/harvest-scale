package com.guilherdrk.agro_scale_api.controller;

import com.guilherdrk.agro_scale_api.dto.SafraRequest;
import com.guilherdrk.agro_scale_api.dto.SafraResponse;
import com.guilherdrk.agro_scale_api.service.SafraService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/safras")
@RequiredArgsConstructor
public class SafraController {

    private final SafraService safraService;

    @PostMapping
    public ResponseEntity<SafraResponse> criar(@Valid @RequestBody SafraRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(safraService.criar(request));
    }

    @GetMapping
    public ResponseEntity<List<SafraResponse>> listarTodas() {
        return ResponseEntity.ok(safraService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SafraResponse> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(safraService.buscarPorId(id));
    }
}

package com.guilherdrk.agro_scale_api.controller;

import com.guilherdrk.agro_scale_api.dto.ProdutividadeTalhaoResponse;
import com.guilherdrk.agro_scale_api.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/produtividade-talhoes")
    public ResponseEntity<List<ProdutividadeTalhaoResponse>> produtividadePorTalhao(
            @RequestParam(required = false) Integer safraId
    ) {
        return ResponseEntity.ok(dashboardService.obterProdutividadePorTalhao(safraId));
    }
}

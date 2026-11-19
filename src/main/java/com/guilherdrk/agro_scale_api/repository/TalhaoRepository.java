package com.guilherdrk.agro_scale_api.repository;

import com.guilherdrk.agro_scale_api.domain.entity.Talhao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TalhaoRepository extends JpaRepository<Talhao, Integer> {

    @Query("""
            SELECT t.id AS talhaoId,
                   t.codigo AS talhaoCodigo,
                   f.nome AS fazendaNome,
                   t.areaHa AS areaHa,
                   COUNT(r.id) AS totalRomaneios,
                   COALESCE(SUM(r.pesoLiquidoSecoKg), 0) AS totalPesoLiquidoSecoKg
            FROM Talhao t
            JOIN t.fazenda f
            LEFT JOIN RomaneioPesagem r
                   ON r.talhao = t
                  AND r.status = com.guilherdrk.agro_scale_api.domain.entity.StatusRomaneio.FINALIZADO
                  AND (:safraId IS NULL OR r.safra.id = :safraId)
            GROUP BY t.id, t.codigo, f.nome, t.areaHa
            ORDER BY f.nome, t.codigo
            """)
    List<ProdutividadeTalhaoProjection> calcularProdutividadePorTalhao(@Param("safraId") Integer safraId);
}

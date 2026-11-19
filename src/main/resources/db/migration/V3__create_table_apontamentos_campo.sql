-- 6. Tabela de Apontamentos de Campo (Origem: App Android Offline-First na Fase 2)
CREATE TABLE apontamentos_campo (
    uuid UUID PRIMARY KEY,
    talhao_id INT NOT NULL REFERENCES talhoes(id),
    tipo_ocorrencia VARCHAR(50) NOT NULL, -- PRAGA, CHUVA, MANUTENCAO_MAQUINA, REMESSA_COLHEITA
    descricao TEXT,
    latitude NUMERIC(10,7),
    longitude NUMERIC(10,7),
    criado_em_dispositivo TIMESTAMP NOT NULL,
    sincronizado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

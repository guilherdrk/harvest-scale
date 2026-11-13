-- 1. Tabela de Safras (Ex: Safra Soja 2026/2027)
CREATE TABLE safras (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    cultura VARCHAR(50) NOT NULL, -- SOJA, MILHO, ALGODAO
    umidade_padrao NUMERIC(5,2) DEFAULT 14.00,
    impureza_padrao NUMERIC(5,2) DEFAULT 1.00,
    data_inicio DATE NOT NULL,
    data_fim DATE,
    ativa BOOLEAN DEFAULT TRUE
);

-- 2. Tabela de Fazendas (Unidades Produtivas no PI)
CREATE TABLE fazendas (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(120) NOT NULL,
    municipio VARCHAR(80) DEFAULT 'Uruçuí - PI',
    area_total_ha NUMERIC(10,2) NOT NULL
);

-- 3. Tabela de Talhões (Subdivisões de plantio da fazenda)
CREATE TABLE talhoes (
    id SERIAL PRIMARY KEY,
    fazenda_id INT NOT NULL REFERENCES fazendas(id),
    codigo VARCHAR(30) NOT NULL, -- Ex: TL-01, PIVOT-04
    area_ha NUMERIC(10,2) NOT NULL,
    variedade_semente VARCHAR(80)
);

-- 4. Tabela de Silos / Armazéns
CREATE TABLE silos (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(80) NOT NULL, -- Ex: Silo Metálico 01
    capacidade_maxima_kg NUMERIC(14,2) NOT NULL,
    saldo_atual_kg NUMERIC(14,2) DEFAULT 0.00
);

-- 5. Tabela Central: Romaneio de Pesagem (Balança Rodoviária)
CREATE TABLE romaneios_pesagem (
    id SERIAL PRIMARY KEY,
    uuid_origem_campo UUID UNIQUE, -- Virá do App Android Offline no Mês 3!
    safra_id INT NOT NULL REFERENCES safras(id),
    talhao_id INT NOT NULL REFERENCES talhoes(id),
    silo_destino_id INT NOT NULL REFERENCES silos(id),
    placa_veiculo VARCHAR(10) NOT NULL,
    motorista VARCHAR(100),
    
    -- Pesos em Kg
    peso_bruto_kg NUMERIC(12,2) NOT NULL,
    peso_tara_kg NUMERIC(12,2),
    peso_liquido_inicial_kg NUMERIC(12,2),
    
    -- Classificação de Grãos
    umidade_percentual NUMERIC(5,2),
    impureza_percentual NUMERIC(5,2),
    desconto_impureza_kg NUMERIC(12,2) DEFAULT 0.00,
    desconto_umidade_kg NUMERIC(12,2) DEFAULT 0.00,
    
    -- Peso final que entra no Silo
    peso_liquido_seco_kg NUMERIC(12,2),
    
    status VARCHAR(30) DEFAULT 'AGUARDANDO_TARA', -- AGUARDANDO_TARA, FINALIZADO, CANCELADO
    data_entrada TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_saida TIMESTAMP
);
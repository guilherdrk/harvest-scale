INSERT INTO safras (nome, cultura, umidade_padrao, impureza_padrao, data_inicio) 
VALUES ('Safra Soja 2026/2027', 'SOJA', 14.00, 1.00, '2026-10-15');

INSERT INTO fazendas (nome, municipio, area_total_ha) 
VALUES ('Fazenda Serra Branca', 'Uruçuí - PI', 12500.00);

INSERT INTO talhoes (fazenda_id, codigo, area_ha, variedade_semente) 
VALUES (1, 'TL-01A', 450.00, 'TMG 2381 IPRO');

INSERT INTO silos (nome, capacidade_maxima_kg, saldo_atual_kg) 
VALUES ('Silo Central 01', 6000000.00, 0.00);
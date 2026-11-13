# Contexto do Projeto: AgroBalance & FieldSync (Portfólio TI no Agronegócio - Uruçuí/PI)

## 1. Objetivo do Projeto
Sistema corporativo voltado para grandes produtoras agrícolas do Matopiba (como Insolo, SLC Agrícola e Grupo Progresso em Uruçuí-PI).
- **Fase 1 (Meses 1 e 2):** Sistema Web Completo (Back-end Java/Spring Boot + Front-end Web) de Gestão de Safra, Recebimento de Grãos (Romaneio de Balança) e Controle de Silos.
- **Fase 2 (Mês 3):** Aplicativo Android Nativo (Java/Kotlin + Room Database + WorkManager) com arquitetura *Offline-First* para apontamento de campo e remessa de caminhões sem internet, sincronizando via endpoint Batch Sync (`POST /api/v1/sync/batch`).
- **Fase 3 (Mês 4):** Inteligência de Dados (Views SQL analíticas no PostgreSQL para produtividade por hectare, quebras de estoque e BI) e portfólio completo.

## 2. Stack Tecnológica Atual
- **Back-end:** Java 17 + Spring Boot, Spring Web, Spring Data JPA, Validation, Lombok, Flyway Migration, JUnit 5.
- **Banco de Dados:** PostgreSQL 16 rodando via Docker Compose (`agro_balance_db`).
- **Front-end Web (Mês 2):** Angular ou React.
- **Mobile (Mês 3):** Android Nativo (Java/Kotlin), Room Database, Retrofit e WorkManager.

## 3. O que já está pronto

### Concluído no Dia 1 (Infraestrutura e Banco de Dados)
- Projeto `agro-scale-api` criado e conectado ao container PostgreSQL no Docker (`localhost:5432/agro_balance_db`).
- Flyway configurado e validado com 2 migrations aplicadas no schema `public`:
  - **`V1__create_initial_schema.sql`**: Criadas as tabelas `safras`, `fazendas`, `talhoes`, `silos` e `romaneios_pesagem` (já contendo a coluna `uuid_origem_campo` para integração idempotente com o app Android offline).
  - **`V2__seed_initial_data.sql`**: Dados iniciais inseridos (`Safra Soja 2026/2027`, `Fazenda Serra Branca` em Uruçuí-PI com 12.500 ha, talhão `TL-01A` e `Silo Central 01`).

### Concluído no Dia 2 (Domínio, Cálculo de Romaneio e API da Balança)
- Entidades JPA (`@Entity`) mapeadas e validadas com `ddl-auto=validate`: `Safra`, `Fazenda`, `Talhao`, `Silo`, `RomaneioPesagem` e enum `StatusRomaneio`.
- Interfaces `Spring Data JPA` (`SafraRepository`, `FazendaRepository`, `TalhaoRepository`, `SiloRepository`, `RomaneioPesagemRepository` com busca por `uuidOrigemCampo`).
- `CalculadoraRomaneioService` e `ResultadoCalculoRomaneio` implementados com `BigDecimal` (`scale = 2`, `RoundingMode.HALF_UP`) e validados com 4 testes unitários no JUnit 5 (`CalculadoraRomaneioServiceTest`).
- DTOs (`AberturaRomaneioRequest`, `FechamentoRomaneioRequest`, `RomaneioResponse`), `RomaneioService` (`@Transactional`) e `RomaneioController` (`/api/v1/romaneios`) implementados com abertura de pesagem, fechamento com laudo de classificação e atualização automática com validação de capacidade máxima no saldo do `Silo`.

## 4. Regras de Negócio do Romaneio de Grãos (Cálculo Oficial)
Todo cálculo de pesagem na API usa `BigDecimal` com escala de 2 casas decimais (`RoundingMode.HALF_UP`):
1. **Peso Líquido Inicial (PLI):** `Peso Bruto - Tara`.
2. **Desconto de Impureza (DI):** Se `impureza_percentual > impureza_padrao` (ex: 1.00%), desconta o percentual excedente sobre o PLI: `PLI * ((impureza_medida - impureza_padrao) / 100)`.
3. **Desconto de Umidade (DU):** Se `umidade_percentual > umidade_padrao` (ex: 14.00%), aplica a fórmula oficial sobre a base limpa (`PLI - DI`): `(PLI - DI) * ((umidade_medida - umidade_padrao) / (100 - umidade_padrao))`.
4. **Peso Líquido Seco e Limpo (PLSL):** `PLI - DI - DU` (este é o peso real que soma no `saldo_atual_kg` da tabela `silos` e compõe a produtividade do `talhao`).
5. **Conversão em Sacas:** `PLSL / 60`.

## 5. Próximos Passos (Meta do Dia 3 — Fechamento do Back-end Core / Mês 1)
1. **Tratamento Global de Erros (`@RestControllerAdvice`):** Padronizar respostas HTTP (`404 Not Found`, `422 Unprocessable Entity`, `400 Bad Request`) para validações de negócio (ex: capacidade do silo excedida, safra inativa, romaneio já finalizado).
2. **Endpoints de Cadastros Operacionais e Silos:** Criar Controllers e DTOs para `Safra`, `Fazenda`, `Talhao` e `Silo` (incluindo cálculo de percentual de ocupação do silo para o painel gerencial).
3. **Indicador de Produtividade por Talhão (Sacas/ha):** Criar consulta/endpoint de dashboard que retorna o total colhido (PLSL e sacas de 60kg) e a produtividade média parcial (`sacas / area_ha`) por talhão e fazenda.
4. **Migration `V3` — Apontamentos de Campo (`apontamentos_campo`):** Criar a tabela e entidade `ApontamentoCampo` (`uuid`, `talhao_id`, `tipo_ocorrencia`, `descricao`, `latitude`, `longitude`, `criado_em_dispositivo`, `sincronizado_em`) preparando a base para o endpoint `/api/v1/sync/batch`.

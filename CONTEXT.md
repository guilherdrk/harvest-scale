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
- Flyway configurado e validado com 2 migrations iniciais (`V1` e `V2`):
  - **`V1__create_initial_schema.sql`**: Tabelas `safras`, `fazendas`, `talhoes`, `silos` e `romaneios_pesagem`.
  - **`V2__seed_initial_data.sql`**: Dados iniciais (`Safra Soja 2026/2027`, `Fazenda Serra Branca`, talhão `TL-01A` e `Silo Central 01`).

### Concluído no Dia 2 (Domínio, Cálculo de Romaneio e API da Balança)
- Entidades JPA (`Safra`, `Fazenda`, `Talhao`, `Silo`, `RomaneioPesagem`, `StatusRomaneio`) e Repositories Spring Data JPA.
- `CalculadoraRomaneioService` e `ResultadoCalculoRomaneio` com `BigDecimal` (`scale = 2`, `RoundingMode.HALF_UP`) validados com JUnit 5 (`CalculadoraRomaneioServiceTest`).
- Fluxo REST da balança rodoviária (`/api/v1/romaneios`): abertura de romaneio, fechamento com laudo de classificação e atualização transacional do saldo do `Silo`.

### Concluído no Dia 3 (Fechamento do Back-end Core / Mês 1)
- **Tratamento Global de Erros:** `ApiErrorResponse` e `GlobalExceptionHandler` (`@RestControllerAdvice`) mapeando `404`, `422`, `400` (incluindo `fieldErrors` do `@Valid`) e `409`.
- **Cadastros Operacionais e Silos:** DTOs, Services e Controllers REST para `/api/v1/silos` (com `capacidadeDisponivelKg` e `percentualOcupacao`), `/api/v1/safras`, `/api/v1/fazendas` e `/api/v1/talhoes`.
- **Dashboard de Produtividade por Talhão:** `GET /api/v1/dashboard/produtividade-talhoes` (com filtro opcional `?safraId=`), agregando via JPQL o `PLSL` finalizado por talhão e calculando `totalSacas` e `produtividadeSacasPorHa` (`Sacas de 60kg / ha`).
- **Migration `V3` (`V3__create_table_apontamentos_campo.sql`):** Tabela `apontamentos_campo`, entidade `ApontamentoCampo` (chave `UUID` gerada offline no dispositivo) e `ApontamentoCampoRepository`.

## 4. Regras de Negócio do Romaneio de Grãos (Cálculo Oficial)
Todo cálculo de pesagem na API usa `BigDecimal` com escala de 2 casas decimais (`RoundingMode.HALF_UP`):
1. **Peso Líquido Inicial (PLI):** `Peso Bruto - Tara`.
2. **Desconto de Impureza (DI):** Se `impureza_percentual > impureza_padrao` (ex: 1.00%), desconta o percentual excedente sobre o PLI: `PLI * ((impureza_medida - impureza_padrao) / 100)`.
3. **Desconto de Umidade (DU):** Se `umidade_percentual > umidade_padrao` (ex: 14.00%), aplica a fórmula oficial sobre a base limpa (`PLI - DI`): `(PLI - DI) * ((umidade_medida - umidade_padrao) / (100 - umidade_padrao))`.
4. **Peso Líquido Seco e Limpo (PLSL):** `PLI - DI - DU` (soma no `saldo_atual_kg` da tabela `silos` e compõe a produtividade do `talhao`).
5. **Conversão em Sacas:** `PLSL / 60`.

## 5. Próximos Passos (Meta do Dia 4 — Preparação para o Front-end Web / Mês 2)
1. **Configuração de CORS:** Liberar a origem do Front-end Web (`localhost:4200` / `localhost:5173`) na API Spring Boot.
2. **Comprovante de Romaneio em PDF:** Criar endpoint `GET /api/v1/romaneios/{id}/comprovante-pdf` para geração do ticket da balança rodoviária.
3. **Inicialização do Front-end Web (Mês 2):** Criar o projeto Web (Angular ou React) com telas de Cadastros (Safra, Fazenda, Talhão), Operação de Balança (Abertura/Fechamento de Romaneio) e Dashboard Gerencial (Ocupação de Silos + Produtividade por Talhão).

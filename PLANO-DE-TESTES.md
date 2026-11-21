# Plano de Testes — Fase 1 (Caixa-preta)

Mapeamento das classes de equivalência (válidas e inválidas), valores-limite e testes JUnit 5 derivados exclusivamente da especificação (**Anexo A**).

> **Convenção:** use `V1, V2...` para classes de equivalência válidas, `I1, I2...` para classes inválidas e `VL1, VL2...` para valores-limite.

---

## 1. `prazoDias(TipoUsuario tipo)` — *Vinicius Rodrigues*
**Arquivo de teste:** `PrazoLimiteDevolucaoTest.java` (`PrazoDiasTest`)

| ID | Tipo | Descrição da Classe / Limite | Entrada de Exemplo | Resultado Esperado | Teste que Cobre |
| :--- | :--- | :--- | :--- | :--- | :--- |
| V1 | Válida | `TipoUsuario.ALUNO` | `ALUNO` | `14` | `PrazoDiasTest.devolvePrazoConformeTipoUsuario` |
| V2 | Válida | `TipoUsuario.PROFESSOR` | `PROFESSOR` | `30` | `PrazoDiasTest.devolvePrazoConformeTipoUsuario` |
| V3 | Válida | `TipoUsuario.VISITANTE` | `VISITANTE` | `7` | `PrazoDiasTest.devolvePrazoConformeTipoUsuario` |
| I1 | Inválida | `tipo` nulo | `null` | `IllegalArgumentException` | `PrazoDiasTest.lancaExcecaoQuandoTipoNulo` |

---

## 2. `limiteEmprestimos(TipoUsuario tipo)` — *Vinicius Rodrigues*
**Arquivo de teste:** `PrazoLimiteDevolucaoTest.java` (`LimiteEmprestimosTest`)

| ID | Tipo | Descrição da Classe / Limite | Entrada de Exemplo | Resultado Esperado | Teste que Cobre |
| :--- | :--- | :--- | :--- | :--- | :--- |
| V1 | Válida | `TipoUsuario.ALUNO` | `ALUNO` | `3` | `LimiteEmprestimosTest.devolveLimiteConformeTipoUsuario` |
| V2 | Válida | `TipoUsuario.PROFESSOR` | `PROFESSOR` | `8` | `LimiteEmprestimosTest.devolveLimiteConformeTipoUsuario` |
| V3 | Válida | `TipoUsuario.VISITANTE` | `VISITANTE` | `1` | `LimiteEmprestimosTest.devolveLimiteConformeTipoUsuario` |
| I1 | Inválida | `tipo` nulo | `null` | `IllegalArgumentException` | `LimiteEmprestimosTest.lancaExcecaoQuandoTipoNulo` |

---

## 3. `dataDevolucao(LocalDate retirada, TipoUsuario tipo)` — *Vinicius Rodrigues*
**Arquivo de teste:** `PrazoLimiteDevolucaoTest.java` (`DataDevolucaoTest`)

| ID | Tipo | Descrição da Classe / Limite | Entrada de Exemplo | Resultado Esperado | Teste que Cobre |
| :--- | :--- | :--- | :--- | :--- | :--- |
| V1 | Válida | Resultado em dia útil (segunda a sexta) para cada tipo | `2026-11-20, ALUNO` (sexta) | `2026-12-04` | `DataDevolucaoTest.devolucaoEmDiaUtilNaoAlteraData` |
| VL1 | Valor-limite | Resultado na segunda-feira (fronteira imediatamente após domingo) | `2026-11-23, ALUNO`; `2026-11-21, PROFESSOR`; `2026-11-23, VISITANTE` | `2026-12-07`; `2026-12-21`; `2026-11-30` | `DataDevolucaoTest.devolucaoEmDiaUtilNaoAlteraData` |
| V2 / VL2 | Válida / Limite | Resultado no sábado (dia normal — fronteira imediatamente antes do domingo, não adia) | `2026-11-21, ALUNO`; `2026-11-19, PROFESSOR`; `2026-11-21, VISITANTE` | `2026-12-05`; `2026-12-19`; `2026-11-28` | `DataDevolucaoTest.devolucaoNoSabadoEDiaNormal` |
| V3 / VL3 | Válida / Limite | Resultado no domingo (fronteira exata que adia para segunda-feira) | `2026-11-22, ALUNO`; `2026-11-20, PROFESSOR`; `2026-11-22, VISITANTE` | `2026-12-07`; `2026-12-21`; `2026-11-30` | `DataDevolucaoTest.devolucaoNoDomingoPassaParaSegunda` |
| V4 | Válida | Virada de ano caindo em domingo, ano bissexto (2024) e não bissexto (2026) | `2026-12-20, ALUNO`; `2024-02-20, ALUNO`; `2026-02-20, ALUNO` | `2027-01-04`; `2024-03-05`; `2026-03-06` | `DataDevolucaoTest.devolucaoEmViradaDeCalendario` |
| I1 | Inválida | `retirada` nula com `tipo` válido (`ALUNO`, `PROFESSOR`, `VISITANTE`) | `null, ALUNO` (e demais tipos) | `IllegalArgumentException` | `DataDevolucaoTest.lancaExcecaoQuandoRetiradaNula` |
| I2 | Inválida | `tipo` nulo com `retirada` válida | `2026-11-20, null` | `IllegalArgumentException` | `DataDevolucaoTest.lancaExcecaoQuandoTipoNulo` |
| I3 | Inválida | `retirada` nula e `tipo` nulo | `null, null` | `IllegalArgumentException` | `DataDevolucaoTest.lancaExcecaoQuandoAmbosNulos` |

---

## 4. `multaCentavos(TipoUsuario tipo, int diasAtraso)` — *Paulo Diego*
**Arquivo de teste:** `MultasTest.java`

| ID | Tipo | Descrição da Classe / Limite | Entrada de Exemplo | Resultado Esperado | Teste que Cobre |
| :--- | :--- | :--- | :--- | :--- | :--- |
| *(preencher)* | | | | | |

---

## 5. `multaTotalCentavos(TipoUsuario tipo, int[] diasAtrasoPorEmprestimo)` — *Paulo Diego*
**Arquivo de teste:** `MultasTest.java`

| ID | Tipo | Descrição da Classe / Limite | Entrada de Exemplo | Resultado Esperado | Teste que Cobre |
| :--- | :--- | :--- | :--- | :--- | :--- |
| *(preencher)* | | | | | |

---

## 6. `podeEmprestar(TipoUsuario tipo, int emprestimosAtivos, int multaPendenteCentavos, boolean possuiAtraso)` — *Claudemir Vieira*
**Arquivo de teste:** `EmprestimoERenovacaoTest.java`

| ID | Tipo | Descrição da Classe / Limite | Entrada de Exemplo | Resultado Esperado | Teste que Cobre |
| :--- | :--- | :--- | :--- | :--- | :--- |
| *(preencher)* | | | | | |

---

## 7. `podeRenovar(TipoUsuario tipo, int renovacoesFeitas, boolean possuiReserva, boolean emAtraso)` — *Claudemir Vieira*
**Arquivo de teste:** `EmprestimoERenovacaoTest.java`

| ID | Tipo | Descrição da Classe / Limite | Entrada de Exemplo | Resultado Esperado | Teste que Cobre |
| :--- | :--- | :--- | :--- | :--- | :--- |
| *(preencher)* | | | | | |

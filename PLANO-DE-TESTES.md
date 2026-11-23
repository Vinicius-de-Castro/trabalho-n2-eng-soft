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
**Arquivo de teste:** `MultasTest.java` (`MultaCentavosTest`)

**Regra (Anexo A):** dias cobrados = `diasAtraso` (professor: `diasAtraso − 3`, mínimo 0). Do 1º ao 10º dia cobrado: 100/dia; do 11º em diante: 200/dia; teto de 5000.
Daí saem as fronteiras: `0 | 1` (início da cobrança), `10 | 11` dias cobrados (troca de faixa: 1000 -> 1200) e `29 | 30 | 31` dias cobrados (teto: 4800 -> 5000 -> 5000). Para professor, as mesmas fronteiras deslocadas em +3 dias de atraso (`3 | 4`, `13 | 14`, `32 | 33 | 34`).

| ID | Tipo | Descrição da Classe / Limite | Entrada de Exemplo | Resultado Esperado | Teste que Cobre |
| :--- | :--- | :--- | :--- | :--- | :--- |
| I1 | Inválida | `tipo` nulo | `null, 5` | `IllegalArgumentException` | `MultaCentavosTest.lancaExcecaoQuandoTipoNulo` |
| I2 | Inválida | `diasAtraso` negativo | `ALUNO, -10` | `IllegalArgumentException` | `MultaCentavosTest.lancaExcecaoQuandoAtrasoNegativo` |
| VL1 | Valor-limite | Logo abaixo de 0 (fronteira inválido/válido), cada tipo | `ALUNO, -1`; `PROFESSOR, -1`; `VISITANTE, -1` | `IllegalArgumentException` | `MultaCentavosTest.lancaExcecaoQuandoAtrasoNegativo` |
| VL2 | Valor-limite | 0 dias de atraso (menor valor válido), cada tipo | `ALUNO, 0`; `PROFESSOR, 0`; `VISITANTE, 0` | `0` | `MultaCentavosTest.semAtrasoNaoHaMulta` |
| VL3 | Valor-limite | 1º dia cobrado (aluno/visitante, sem carência) | `ALUNO, 1`; `VISITANTE, 1` | `100` | `MultaCentavosTest.alunoEVisitanteSemCarencia` |
| V1 | Válida | Aluno/visitante na faixa de R$ 1,00 (1 a 10 dias) | `ALUNO, 5` | `500` | `MultaCentavosTest.alunoEVisitanteSemCarencia` |
| VL4 | Valor-limite | Troca de faixa: 9, 10 e 11 dias cobrados | `ALUNO, 9 / 10 / 11`; `VISITANTE, 10 / 11` | `900 / 1000 / 1200`; `1000 / 1200` | `MultaCentavosTest.alunoEVisitanteSemCarencia` |
| V2 | Válida | Aluno/visitante na faixa de R$ 2,00 (11 a 29 dias) — inclui o exemplo do Anexo A | `ALUNO, 12`; `ALUNO, 20` | `1400`; `3000` | `MultaCentavosTest.alunoEVisitanteSemCarencia` |
| VL5 | Valor-limite | Teto de R$ 50,00: 29, 30 e 31 dias cobrados | `ALUNO, 29 / 30 / 31`; `VISITANTE, 30 / 31` | `4800 / 5000 / 5000`; `5000 / 5000` | `MultaCentavosTest.alunoEVisitanteSemCarencia` |
| V3 | Válida | Aluno muito acima do teto | `ALUNO, 100` | `5000` | `MultaCentavosTest.alunoEVisitanteSemCarencia` |
| V4 | Válida | Professor dentro da carência (0 a 3 dias) | `PROFESSOR, 1` | `0` | `MultaCentavosTest.professorComCarenciaDe3Dias` |
| VL6 | Valor-limite | Fim da carência: 2, 3 e 4 dias | `PROFESSOR, 2 / 3 / 4` | `0 / 0 / 100` | `MultaCentavosTest.professorComCarenciaDe3Dias` |
| V5 | Válida | Professor na faixa de R$ 1,00 (4 a 13 dias) | `PROFESSOR, 8` | `500` | `MultaCentavosTest.professorComCarenciaDe3Dias` |
| VL7 | Valor-limite | Troca de faixa do professor: 13 e 14 dias (10º e 11º cobrados) | `PROFESSOR, 13 / 14` | `1000 / 1200` | `MultaCentavosTest.professorComCarenciaDe3Dias` |
| V6 | Válida | Professor na faixa de R$ 2,00 (14 a 32 dias) | `PROFESSOR, 20` | `2400` | `MultaCentavosTest.professorComCarenciaDe3Dias` |
| VL8 | Valor-limite | Teto do professor: 32, 33 e 34 dias (29, 30 e 31 cobrados) | `PROFESSOR, 32 / 33 / 34` | `4800 / 5000 / 5000` | `MultaCentavosTest.professorComCarenciaDe3Dias` |
| V7 | Válida | Professor muito acima do teto | `PROFESSOR, 100` | `5000` | `MultaCentavosTest.professorComCarenciaDe3Dias` |
| VL9 | Valor-limite | Maior valor do tipo `int` (`Integer.MAX_VALUE`) — o cálculo não pode estourar | `ALUNO, 2147483647`; `PROFESSOR, 2147483647` | `5000` | `MultaCentavosTest.alunoEVisitanteSemCarencia`; `MultaCentavosTest.professorComCarenciaDe3Dias` |

---

## 5. `multaTotalCentavos(TipoUsuario tipo, int[] diasAtrasoPorEmprestimo)` — *Paulo Diego*
**Arquivo de teste:** `MultasTest.java` (`MultaTotalCentavosTest`)

**Regra (Anexo A):** soma `multaCentavos` de cada elemento (cada uma já limitada a 5000) e limita o total a 10000. Lista vazia -> 0. `tipo` nulo, lista nula ou elemento negativo -> `IllegalArgumentException`.
Para o array, além do valor de cada elemento, particionamos o **tamanho** (0, 1, vários) e a **posição** do elemento relevante (início, meio, fim). Fronteira do teto total: `9900 | 10000 | 10100`.

| ID | Tipo | Descrição da Classe / Limite | Entrada de Exemplo | Resultado Esperado | Teste que Cobre |
| :--- | :--- | :--- | :--- | :--- | :--- |
| I1 | Inválida | `tipo` nulo (com lista com elementos e com lista vazia) | `null, {5}`; `null, {}` | `IllegalArgumentException` | `MultaTotalCentavosTest.lancaExcecaoQuandoTipoNulo` |
| I2 | Inválida | Lista nula, cada tipo | `ALUNO, null` (e demais tipos) | `IllegalArgumentException` | `MultaTotalCentavosTest.lancaExcecaoQuandoListaNula` |
| I3 | Inválida | Elemento negativo no início, no meio e no fim; `-1` (limite) e `-10` (típico) | `ALUNO {-1,5}`; `ALUNO {5,-1,5}`; `ALUNO {5,-1}`; `PROFESSOR {-1}`; `VISITANTE {0,-10}` | `IllegalArgumentException` | `MultaTotalCentavosTest.lancaExcecaoQuandoElementoNegativo` |
| V1 / VL1 | Válida / Limite | Lista vazia (tamanho 0), cada tipo | `ALUNO, {}` (e demais tipos) | `0` | `MultaTotalCentavosTest.listaVaziaDaTotalZero` |
| V2 | Válida | Lista com 1 elemento (= `multaCentavos`) | `ALUNO {0}`; `ALUNO {12}` | `0`; `1400` | `MultaTotalCentavosTest.somaMultasComTetoTotal` |
| V3 | Válida | Lista com vários elementos, abaixo do teto | `ALUNO {5,12}`; `VISITANTE {1,1,1}` | `1900`; `300` | `MultaTotalCentavosTest.somaMultasComTetoTotal` |
| V4 | Válida | Professor: carência aplicada a cada empréstimo, não à soma dos dias | `PROFESSOR {3,4}`; `PROFESSOR {3,3,3}` | `100`; `0` | `MultaTotalCentavosTest.somaMultasComTetoTotal` |
| V5 | Válida | Teto de 5000 por empréstimo aplicado antes de somar | `ALUNO {40}`; `ALUNO {40,1}` | `5000`; `5100` | `MultaTotalCentavosTest.somaMultasComTetoTotal` |
| VL2 | Valor-limite | Teto total: soma 9900, 10000 e 10100 | `ALUNO {30,29,1}/{30,29,2}/{30,29,3}`; `PROFESSOR {33,32,4}/{33,32,5}/{33,32,6}` | `9900 / 10000 / 10000` | `MultaTotalCentavosTest.somaMultasComTetoTotal` |
| V6 | Válida | Soma muito acima do teto total | `ALUNO {30,30,30}`; `ALUNO {0,30,30,30}` | `10000` | `MultaTotalCentavosTest.somaMultasComTetoTotal` |
| V7 | Válida | Posição do empréstimo com atraso: início, meio, fim | `ALUNO {12,0,0}`; `{0,12,0}`; `{0,0,12}` | `1400` | `MultaTotalCentavosTest.somaMultasComTetoTotal` |
| VL3 | Valor-limite | Teto total (VL2) com um empréstimo sem atraso na 1ª posição (combina V7 e VL2) | `ALUNO {0,30,29,1}/{0,30,29,2}/{0,30,29,3}` | `9900 / 10000 / 10000` | `MultaTotalCentavosTest.somaMultasComTetoTotal` |

---

## 6. `podeEmprestar(TipoUsuario tipo, int emprestimosAtivos, int multaPendenteCentavos, boolean possuiAtraso)` — *Claudemir Vieira*
**Arquivo de teste:** `EmprestimoERenovacaoTest.java` (`PodeEmprestarTest`)

**Regra (Anexo A):** devolve `true` somente se as três condições valerem juntas: `emprestimosAtivos < limiteEmprestimos(tipo)` (`ALUNO`: 3, `PROFESSOR`: 8, `VISITANTE`: 1), `multaPendenteCentavos <= 1000` (R$ 10,00) e `possuiAtraso == false`. `tipo` nulo, `emprestimosAtivos < 0` ou `multaPendenteCentavos < 0` lançam `IllegalArgumentException`.
Fronteiras: `emprestimosAtivos` em `-1 | 0` e `limite - 1 | limite | limite + 1` (`2 | 3 | 4` para aluno, `7 | 8 | 9` para professor, `0 | 1 | 2` para visitante); `multaPendenteCentavos` em `-1 | 0 | 1` e `999 | 1000 | 1001`; extremos de `int` (`Integer.MIN_VALUE`, `Integer.MAX_VALUE`); e cada condição booleana/impeditiva isolada e combinada.

| ID | Tipo | Descrição da Classe / Limite | Entrada de Exemplo | Resultado Esperado | Teste que Cobre |
| :--- | :--- | :--- | :--- | :--- | :--- |
| I1 | Inválida | `tipo` nulo (com demais entradas válidas e também com condições impeditivas) | `null, 0, 0, false`; `null, 5, 2000, true` | `IllegalArgumentException` | `PodeEmprestarTest.lancaExcecaoQuandoTipoNulo` |
| VL1 | Valor-limite | `emprestimosAtivos` logo abaixo de 0 (`-1`) para cada tipo, inclusive com atraso/multa acima do limite, e `Integer.MIN_VALUE` | `ALUNO, -1, 0, false`; `PROFESSOR, -1, 0, false`; `VISITANTE, -1, 0, false`; `ALUNO, -1, 0, true`; `ALUNO, -1, 2000, false`; `ALUNO, -2147483648, 0, false` | `IllegalArgumentException` | `PodeEmprestarTest.lancaExcecaoQuandoEmprestimosAtivosNegativo` |
| I2 | Inválida | `emprestimosAtivos` negativo típico | `ALUNO, -5, 0, false` | `IllegalArgumentException` | `PodeEmprestarTest.lancaExcecaoQuandoEmprestimosAtivosNegativo` |
| VL2 | Valor-limite | `multaPendenteCentavos` logo abaixo de 0 (`-1`) para cada tipo, inclusive com atraso/limite de empréstimos, e `Integer.MIN_VALUE` | `ALUNO, 0, -1, false`; `PROFESSOR, 0, -1, false`; `VISITANTE, 0, -1, false`; `ALUNO, 0, -1, true`; `ALUNO, 3, -1, false`; `ALUNO, 0, -2147483648, false` | `IllegalArgumentException` | `PodeEmprestarTest.lancaExcecaoQuandoMultaPendenteNegativa` |
| I3 | Inválida | `multaPendenteCentavos` negativa típica | `ALUNO, 0, -500, false` | `IllegalArgumentException` | `PodeEmprestarTest.lancaExcecaoQuandoMultaPendenteNegativa` |
| I4 | Inválida | Múltiplas entradas inválidas simultâneas (`tipo` nulo e/ou ambos negativos) | `ALUNO, -1, -1, false`; `null, -1, -1, false` | `IllegalArgumentException` | `PodeEmprestarTest.lancaExcecaoQuandoMultiplasEntradasInvalidas` |
| V1 / VL3 | Válida / Limite | Menor valor válido em todas as entradas (`0` empréstimos, `0` multa, sem atraso), cada tipo (`0` é também `limite - 1` do visitante) | `ALUNO, 0, 0, false`; `PROFESSOR, 0, 0, false`; `VISITANTE, 0, 0, false` | `true` | `PodeEmprestarTest.permiteEmprestimoQuandoTodasCondicoesSaoAtendidas` |
| V2 | Válida | Valores intermediários dentro de todos os limites | `ALUNO, 1, 500, false`; `PROFESSOR, 4, 500, false` | `true` | `PodeEmprestarTest.permiteEmprestimoQuandoTodasCondicoesSaoAtendidas` |
| VL4 | Valor-limite | `emprestimosAtivos` em `limite - 1` (máximo permitido), sem multa e sem atraso | `ALUNO, 2, 0, false`; `PROFESSOR, 7, 0, false` | `true` | `PodeEmprestarTest.permiteEmprestimoQuandoTodasCondicoesSaoAtendidas` |
| VL5 | Valor-limite | Fronteira da multa permitida: `1`, `999` e `1000` (teto exato de R$ 10,00), para cada tipo e combinada com `limite - 1` | `ALUNO, 0, 1 / 999 / 1000, false`; `PROFESSOR, 0, 1000, false`; `VISITANTE, 0, 1000, false`; `ALUNO, 2, 1000, false`; `PROFESSOR, 7, 1000, false` | `true` | `PodeEmprestarTest.permiteEmprestimoQuandoTodasCondicoesSaoAtendidas` |
| VL6 | Valor-limite | `emprestimosAtivos` exatamente no limite (`limite`) e logo acima (`limite + 1`), sem multa e sem atraso | `ALUNO, 3 / 4, 0, false`; `PROFESSOR, 8 / 9, 0, false`; `VISITANTE, 1 / 2, 0, false` | `false` | `PodeEmprestarTest.bloqueiaQuandoAtingeOuExcedeLimiteDeEmprestimos` |
| V3 | Válida | `emprestimosAtivos` muito acima do limite, sem multa e sem atraso | `ALUNO, 10, 0, false`; `PROFESSOR, 20, 0, false`; `VISITANTE, 5, 0, false` | `false` | `PodeEmprestarTest.bloqueiaQuandoAtingeOuExcedeLimiteDeEmprestimos` |
| VL7 | Valor-limite | `multaPendenteCentavos` logo acima do teto (`1001`), para cada tipo, com 0 empréstimos e sem atraso | `ALUNO, 0, 1001, false`; `PROFESSOR, 0, 1001, false`; `VISITANTE, 0, 1001, false` | `false` | `PodeEmprestarTest.bloqueiaQuandoMultaPendenteExcedeLimite` |
| V4 | Válida | `multaPendenteCentavos` muito acima do teto, com 0 empréstimos e sem atraso | `ALUNO, 0, 2500, false`; `PROFESSOR, 0, 5000, false` | `false` | `PodeEmprestarTest.bloqueiaQuandoMultaPendenteExcedeLimite` |
| VL8 | Valor-limite | Maior valor do tipo `int` (`Integer.MAX_VALUE`) para empréstimos ativos ou multa | `ALUNO, 2147483647, 0, false`; `ALUNO, 0, 2147483647, false` | `false` | `PodeEmprestarTest.bloqueiaQuandoAtingeOuExcedeLimiteDeEmprestimos`; `PodeEmprestarTest.bloqueiaQuandoMultaPendenteExcedeLimite` |
| V5 | Válida | `possuiAtraso = true` como único impeditivo (para cada tipo e nas fronteiras de multa válida `0`, `1`, `999`, `1000` e `limite - 1`) | `ALUNO / PROFESSOR / VISITANTE, 0, 0, true`; `ALUNO, 0, 1 / 999 / 1000, true`; `ALUNO, 2, 1000, true` | `false` | `PodeEmprestarTest.bloqueiaQuandoPossuiAtraso` |
| V6 | Válida | Múltiplas condições impeditivas simultâneas (pares e todas as três) | `ALUNO, 3, 1001, false`; `ALUNO, 3, 0, true`; `ALUNO, 0, 1001, true`; `ALUNO, 3, 1001, true`; `PROFESSOR, 8, 1500, true`; `VISITANTE, 1, 1500, true` | `false` | `PodeEmprestarTest.bloqueiaQuandoMultiplasCondicoesFalham` |

---

## 7. `podeRenovar(TipoUsuario tipo, int renovacoesFeitas, boolean possuiReserva, boolean emAtraso)` — *Claudemir Vieira*
**Arquivo de teste:** `EmprestimoERenovacaoTest.java` (`PodeRenovarTest`)

**Regra (Anexo A):** `VISITANTE` nunca renova (`false`). `ALUNO` renova até 2 vezes (`renovacoesFeitas < 2`) e `PROFESSOR` até 3 vezes (`renovacoesFeitas < 3`). Em qualquer caso, só é possível renovar se `possuiReserva == false` e `emAtraso == false`. `tipo` nulo ou `renovacoesFeitas < 0` lançam `IllegalArgumentException`, para qualquer tipo.
Fronteiras: `renovacoesFeitas` em `-1 | 0` (para todos os tipos, inclusive visitante e com reserva/atraso), `1 | 2 | 3` para aluno, `2 | 3 | 4` para professor, extremos de `int` (`Integer.MIN_VALUE`, `Integer.MAX_VALUE`), além de `possuiReserva` e `emAtraso` isolados e combinados.

| ID | Tipo | Descrição da Classe / Limite | Entrada de Exemplo | Resultado Esperado | Teste que Cobre |
| :--- | :--- | :--- | :--- | :--- | :--- |
| I1 | Inválida | `tipo` nulo (com demais entradas válidas e também com reserva, atraso ou renovações negativas) | `null, 0, false, false`; `null, 0, true, false`; `null, 0, false, true`; `null, -1, false, false` | `IllegalArgumentException` | `PodeRenovarTest.lancaExcecaoQuandoTipoNulo` |
| VL1 | Valor-limite | `renovacoesFeitas` logo abaixo de 0 (`-1`) para **todos** os tipos (inclusive `VISITANTE` e combinado com reserva/atraso), e `Integer.MIN_VALUE` | `ALUNO / PROFESSOR / VISITANTE, -1, false, false`; `ALUNO, -1, true, false`; `PROFESSOR, -1, false, true`; `VISITANTE, -1, true, true`; `ALUNO, -2147483648, false, false` | `IllegalArgumentException` | `PodeRenovarTest.lancaExcecaoQuandoRenovacoesNegativas` |
| I2 | Inválida | `renovacoesFeitas` negativo típico (`-5`), para cada tipo | `ALUNO, -5, false, false`; `PROFESSOR, -5, false, false`; `VISITANTE, -5, false, false` | `IllegalArgumentException` | `PodeRenovarTest.lancaExcecaoQuandoRenovacoesNegativas` |
| V1 / VL2 | Válida / Limite | `VISITANTE` nunca renova (`0` = menor valor válido, `1`, `2`, `3`, `5`), sem reserva e sem atraso | `VISITANTE, 0 / 1 / 2 / 3 / 5, false, false` | `false` | `PodeRenovarTest.visitanteNuncaRenova` |
| V2 / VL3 | Válida / Limite | `ALUNO` abaixo do limite de renovações (`0` = 1ª renovação / menor valor válido; `1` = 2ª e última renovação permitida), sem reserva e sem atraso | `ALUNO, 0, false, false`; `ALUNO, 1, false, false` | `true` | `PodeRenovarTest.alunoRenovaAteDuasVezes` |
| VL4 | Valor-limite | `ALUNO` no limite exato (`2`) e logo acima (`3`), sem reserva e sem atraso | `ALUNO, 2, false, false`; `ALUNO, 3, false, false` | `false` | `PodeRenovarTest.alunoRenovaAteDuasVezes` |
| V3 | Válida | `ALUNO` muito acima do limite (`10`), sem reserva e sem atraso | `ALUNO, 10, false, false` | `false` | `PodeRenovarTest.alunoRenovaAteDuasVezes` |
| V4 / VL5 | Válida / Limite | `PROFESSOR` abaixo do limite de renovações (`0` = menor valor válido, `1` = intermediário, `2` = 3ª e última renovação permitida), sem reserva e sem atraso | `PROFESSOR, 0, false, false`; `PROFESSOR, 1, false, false`; `PROFESSOR, 2, false, false` | `true` | `PodeRenovarTest.professorRenovaAteTresVezes` |
| VL6 | Valor-limite | `PROFESSOR` no limite exato (`3`) e logo acima (`4`), sem reserva e sem atraso | `PROFESSOR, 3, false, false`; `PROFESSOR, 4, false, false` | `false` | `PodeRenovarTest.professorRenovaAteTresVezes` |
| V5 / VL7 | Válida / Limite | `PROFESSOR` muito acima do limite (`10`) e maior valor do tipo `int` (`Integer.MAX_VALUE`), sem reserva e sem atraso | `PROFESSOR, 10, false, false`; `PROFESSOR, 2147483647, false, false` | `false` | `PodeRenovarTest.professorRenovaAteTresVezes` |
| V6 | Válida | Obra com reserva (`possuiReserva = true`) como único impeditivo, para cada tipo (inclusive na última renovação permitida) | `ALUNO, 0 / 1, true, false`; `PROFESSOR, 0 / 2, true, false`; `VISITANTE, 0, true, false` | `false` | `PodeRenovarTest.bloqueiaQuandoPossuiReserva` |
| V7 | Válida | Empréstimo em atraso (`emAtraso = true`) como único impeditivo, para cada tipo (inclusive na última renovação permitida) | `ALUNO, 0 / 1, false, true`; `PROFESSOR, 0 / 2, false, true`; `VISITANTE, 0, false, true` | `false` | `PodeRenovarTest.bloqueiaQuandoEmAtraso` |
| V8 | Válida | Múltiplas condições impeditivas simultâneas (reserva + atraso, limite + reserva, limite + atraso e todas juntas) | `ALUNO / PROFESSOR / VISITANTE, 0, true, true`; `ALUNO, 2, true, false`; `ALUNO, 2, false, true`; `ALUNO, 2, true, true`; `PROFESSOR, 3, true, false`; `PROFESSOR, 3, false, true`; `PROFESSOR, 3, true, true` | `false` | `PodeRenovarTest.bloqueiaQuandoMultiplasCondicoesFalham` |

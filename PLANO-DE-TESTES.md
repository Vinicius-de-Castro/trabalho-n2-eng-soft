# Plano de Testes — Fase 1 (Caixa-preta)

Mapeamento das classes de equivalência (válidas e inválidas), valores-limite e testes JUnit 5 derivados exclusivamente da especificação (**Anexo A**).

> **Convenção:** use `V1, V2...` para classes de equivalência válidas, `I1, I2...` para classes inválidas e `VL1, VL2...` para valores-limite.

---

## 1. `prazoDias(TipoUsuario tipo)` — *Vinicius*
**Arquivo de teste:** `PrazoLimiteDevolucaoTest.java`

| ID | Tipo | Descrição da Classe / Limite | Entrada de Exemplo | Resultado Esperado | Teste que Cobre |
| :--- | :--- | :--- | :--- | :--- | :--- |
| V1 | Válida | `TipoUsuario.ALUNO` | `ALUNO` | `14` | *(preencher)* |
| V2 | Válida | `TipoUsuario.PROFESSOR` | `PROFESSOR` | `30` | *(preencher)* |
| V3 | Válida | `TipoUsuario.VISITANTE` | `VISITANTE` | `7` | *(preencher)* |
| I1 | Inválida | `tipo` nulo | `null` | `IllegalArgumentException` | *(preencher)* |

---

## 2. `limiteEmprestimos(TipoUsuario tipo)` — *Vinicius*
**Arquivo de teste:** `PrazoLimiteDevolucaoTest.java`

| ID | Tipo | Descrição da Classe / Limite | Entrada de Exemplo | Resultado Esperado | Teste que Cobre |
| :--- | :--- | :--- | :--- | :--- | :--- |
| V1 | Válida | `TipoUsuario.ALUNO` | `ALUNO` | `3` | *(preencher)* |
| V2 | Válida | `TipoUsuario.PROFESSOR` | `PROFESSOR` | `8` | *(preencher)* |
| V3 | Válida | `TipoUsuario.VISITANTE` | `VISITANTE` | `1` | *(preencher)* |
| I1 | Inválida | `tipo` nulo | `null` | `IllegalArgumentException` | *(preencher)* |

---

## 3. `dataDevolucao(LocalDate retirada, TipoUsuario tipo)` — *Vinicius*
**Arquivo de teste:** `PrazoLimiteDevolucaoTest.java`

| ID | Tipo | Descrição da Classe / Limite | Entrada de Exemplo | Resultado Esperado | Teste que Cobre |
| :--- | :--- | :--- | :--- | :--- | :--- |
| *(preencher)* | | | | | |

---

## 4. `multaCentavos(TipoUsuario tipo, int diasAtraso)` — *Paulo*
**Arquivo de teste:** `MultasTest.java`

| ID | Tipo | Descrição da Classe / Limite | Entrada de Exemplo | Resultado Esperado | Teste que Cobre |
| :--- | :--- | :--- | :--- | :--- | :--- |
| *(preencher)* | | | | | |

---

## 5. `multaTotalCentavos(TipoUsuario tipo, int[] diasAtrasoPorEmprestimo)` — *Paulo*
**Arquivo de teste:** `MultasTest.java`

| ID | Tipo | Descrição da Classe / Limite | Entrada de Exemplo | Resultado Esperado | Teste que Cobre |
| :--- | :--- | :--- | :--- | :--- | :--- |
| *(preencher)* | | | | | |

---

## 6. `podeEmprestar(TipoUsuario tipo, int emprestimosAtivos, int multaPendenteCentavos, boolean possuiAtraso)` — *Claudemir*
**Arquivo de teste:** `EmprestimoERenovacaoTest.java`

| ID | Tipo | Descrição da Classe / Limite | Entrada de Exemplo | Resultado Esperado | Teste que Cobre |
| :--- | :--- | :--- | :--- | :--- | :--- |
| *(preencher)* | | | | | |

---

## 7. `podeRenovar(TipoUsuario tipo, int renovacoesFeitas, boolean possuiReserva, boolean emAtraso)` — *Claudemir*
**Arquivo de teste:** `EmprestimoERenovacaoTest.java`

| ID | Tipo | Descrição da Classe / Limite | Entrada de Exemplo | Resultado Esperado | Teste que Cobre |
| :--- | :--- | :--- | :--- | :--- | :--- |
| *(preencher)* | | | | | |

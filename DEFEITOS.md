# Registro de Defeitos Encontrados

Uma linha por falha observada ao executar a suíte de testes contra `RegrasBiblioteca`.
O resultado esperado vem sempre da especificação (**Anexo A**).

| Fase | Método | Entrada | Resultado Esperado | Resultado Obtido | Classe ou Limite que Levou ao Teste |
| :--- | :--- | :--- | :--- | :--- | :--- |
| Fase 1 | `prazoDias` | `VISITANTE` | `7` | `10` | V3 — `TipoUsuario.VISITANTE` |
| Fase 1 | `dataDevolucao` | `2026-11-23, VISITANTE` | `2026-11-30` | `2026-12-03` | VL1 — resultado na segunda-feira |
| Fase 1 | `dataDevolucao` | `2026-11-21, VISITANTE` | `2026-11-28` | `2026-12-01` | V2 / VL2 — resultado no sábado |
| Fase 1 | `dataDevolucao` | `2026-11-20, PROFESSOR` | `2026-12-21` | `2026-12-20` | V3 / VL3 — resultado no domingo |
| Fase 1 | `dataDevolucao` | `2026-11-22, VISITANTE` | `2026-11-30` | `2026-12-03` | V3 / VL3 — resultado no domingo |
| Fase 1 | `multaCentavos` | `ALUNO, -1` | `IllegalArgumentException` | nenhuma exceção | VL1 — logo abaixo de 0 |
| Fase 1 | `multaCentavos` | `PROFESSOR, -1` | `IllegalArgumentException` | nenhuma exceção | VL1 — logo abaixo de 0 |
| Fase 1 | `multaCentavos` | `VISITANTE, -1` | `IllegalArgumentException` | nenhuma exceção | VL1 — logo abaixo de 0 |
| Fase 1 | `multaCentavos` | `ALUNO, -10` | `IllegalArgumentException` | nenhuma exceção | I2 — atraso negativo |
| Fase 1 | `multaCentavos` | `ALUNO, 11` | `1200` | `1100` | VL4 — 11º dia cobrado (1º a R$ 2,00) |
| Fase 1 | `multaCentavos` | `VISITANTE, 11` | `1200` | `1100` | VL4 — 11º dia cobrado (1º a R$ 2,00) |
| Fase 1 | `multaCentavos` | `ALUNO, 31` | `5000` | `5200` | VL5 — logo acima do teto |
| Fase 1 | `multaCentavos` | `VISITANTE, 31` | `5000` | `5200` | VL5 — logo acima do teto |
| Fase 1 | `multaCentavos` | `ALUNO, 100` | `5000` | `19000` | V3 — muito acima do teto |
| Fase 1 | `multaCentavos` | `PROFESSOR, 3` | `0` | `100` | VL6 — último dia de carência |
| Fase 1 | `multaCentavos` | `PROFESSOR, 4` | `100` | `200` | VL6 — 1º dia cobrado após a carência |
| Fase 1 | `multaCentavos` | `PROFESSOR, 8` | `500` | `600` | V5 — faixa de R$ 1,00 do professor |
| Fase 1 | `multaCentavos` | `PROFESSOR, 13` | `1000` | `1100` | VL7 — 10º dia cobrado |
| Fase 1 | `multaCentavos` | `PROFESSOR, 14` | `1200` | `1400` | VL7 — 11º dia cobrado |
| Fase 1 | `multaCentavos` | `PROFESSOR, 20` | `2400` | `2600` | V6 — faixa de R$ 2,00 do professor |
| Fase 1 | `multaCentavos` | `PROFESSOR, 32` | `4800` | `5000` | VL8 — logo abaixo do teto |
| Fase 1 | `multaCentavos` | `PROFESSOR, 33` | `5000` | `5200` | VL8 — teto exato |
| Fase 1 | `multaCentavos` | `PROFESSOR, 34` | `5000` | `5400` | VL8 — logo acima do teto |
| Fase 1 | `multaCentavos` | `PROFESSOR, 100` | `5000` | `18600` | V7 — muito acima do teto |
| Fase 1 | `multaCentavos` | `ALUNO, 2147483647` | `5000` | `-1200` | VL9 — maior `int` (`Integer.MAX_VALUE`) |
| Fase 1 | `multaCentavos` | `PROFESSOR, 2147483647` | `5000` | `-1600` | VL9 — maior `int` (`Integer.MAX_VALUE`) |
| Fase 1 | `multaTotalCentavos` | `ALUNO, {-1, 5}` | `IllegalArgumentException` | nenhuma exceção | I3 — negativo no início |
| Fase 1 | `multaTotalCentavos` | `ALUNO, {5, -1, 5}` | `IllegalArgumentException` | nenhuma exceção | I3 — negativo no meio |
| Fase 1 | `multaTotalCentavos` | `ALUNO, {5, -1}` | `IllegalArgumentException` | nenhuma exceção | I3 — negativo no fim |
| Fase 1 | `multaTotalCentavos` | `PROFESSOR, {-1}` | `IllegalArgumentException` | nenhuma exceção | I3 — negativo (limite -1) |
| Fase 1 | `multaTotalCentavos` | `VISITANTE, {0, -10}` | `IllegalArgumentException` | nenhuma exceção | I3 — negativo típico |
| Fase 1 | `multaTotalCentavos` | `ALUNO, {12}` | `1400` | `0` | V2 — lista com 1 elemento |
| Fase 1 | `multaTotalCentavos` | `ALUNO, {5, 12}` | `1900` | `1400` | V3 — vários elementos |
| Fase 1 | `multaTotalCentavos` | `VISITANTE, {1, 1, 1}` | `300` | `200` | V3 — vários elementos |
| Fase 1 | `multaTotalCentavos` | `PROFESSOR, {3, 4}` | `100` | `200` | V4 — carência por empréstimo |
| Fase 1 | `multaTotalCentavos` | `PROFESSOR, {3, 3, 3}` | `0` | `200` | V4 — carência por empréstimo |
| Fase 1 | `multaTotalCentavos` | `ALUNO, {40}` | `5000` | `0` | V5 — teto por empréstimo |
| Fase 1 | `multaTotalCentavos` | `ALUNO, {40, 1}` | `5100` | `100` | V5 — teto por empréstimo antes da soma |
| Fase 1 | `multaTotalCentavos` | `ALUNO, {30, 29, 1}` | `9900` | `4900` | VL2 — logo abaixo do teto total |
| Fase 1 | `multaTotalCentavos` | `ALUNO, {30, 29, 2}` | `10000` | `5000` | VL2 — teto total exato |
| Fase 1 | `multaTotalCentavos` | `ALUNO, {30, 29, 3}` | `10000` | `5100` | VL2 — logo acima do teto total |
| Fase 1 | `multaTotalCentavos` | `PROFESSOR, {33, 32, 4}` | `9900` | `5200` | VL2 — logo abaixo do teto total |
| Fase 1 | `multaTotalCentavos` | `PROFESSOR, {33, 32, 5}` | `10000` | `5300` | VL2 — teto total exato |
| Fase 1 | `multaTotalCentavos` | `PROFESSOR, {33, 32, 6}` | `10000` | `5400` | VL2 — logo acima do teto total |
| Fase 1 | `multaTotalCentavos` | `ALUNO, {12, 0, 0}` | `1400` | `0` | V7 — atraso no início da lista |
| Fase 1 | `multaTotalCentavos` | `ALUNO, {0, 30, 29, 3}` | `10000` | `5000` | VL3 — logo acima do teto total |
| Fase 1 | `multaTotalCentavos` | `ALUNO, {0, 30, 30, 30}` | `10000` | `5000` | V6 — muito acima do teto total |

### Hipóteses de defeito — multas (Paulo Diego)
Inferidas só pelas entradas e saídas acima (caixa-preta, sem abrir o `.jar`):

1. **`multaCentavos` não valida atraso negativo** — nenhuma exceção para `-1` ou `-10`.
2. **Troca de faixa no lugar errado** — o 11º dia cobrado ainda sai a R$ 1,00 (`ALUNO, 11` -> 1100), mas `ALUNO, 12` -> 1400 está certo.
3. **Sem teto de R$ 50,00 por empréstimo** — `ALUNO, 31` -> 5200 e `ALUNO, 100` -> 19000. Com `Integer.MAX_VALUE` a conta estoura e a multa sai **negativa** (`-1200`).
4. **Carência do professor é de 2 dias, não 3** — todos os resultados do professor batem com `diasAtraso − 2`.
5. **`multaTotalCentavos` ignora o primeiro elemento da lista** — `{12}` -> 0, `{5, 12}` -> 1400, `{12, 0, 0}` -> 0, mas `{0, 12, 0}` -> 1400.
6. **Teto total errado** — quando a soma passa de 10000 o método devolve 5000 (`{0, 30, 29, 3}` -> 5000), enquanto 9900 e 10000 exatos saem certos.
7. **`multaTotalCentavos` não valida elemento negativo** — pode ser consequência do defeito 1.

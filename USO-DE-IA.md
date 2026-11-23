# Uso de IA

Registro transparente do uso de assistentes de IA pela equipe (Vinicius Rodrigues, Paulo Diego e Claudemir Vieira), conforme exigido no enunciado.

---

## Fase 1 — Caixa-preta

### 1. Organização inicial do projeto (Vinicius Rodrigues)
- **O que pedimos à IA:** Resumir o enunciado do trabalho, propor uma divisão equilibrada das tarefas para o trio (Vinicius Rodrigues, Paulo Diego e Claudemir Vieira) e criar a estrutura inicial do repositório (templates de `README.md`, `PLANO-DE-TESTES.md`, `DEFEITOS.md`, `USO-DE-IA.md` e classes de teste esqueleto).
- **O que aproveitamos:** A divisão dos 7 métodos em 3 blocos por afinidade de regras, o checklist de Git e os templates dos arquivos Markdown e das 3 classes de teste.
- **O que tivemos de corrigir / validar:** Corrigimos os nomes completos dos integrantes, conferimos se o primeiro commit foi feito apenas com o kit original antes da adição dos templates e validamos as assinaturas contra o Anexo A.

### 2. Testes de `prazoDias`, `limiteEmprestimos` e `dataDevolucao` (Vinicius Rodrigues)
- **O que pedimos à IA:** Derivar as classes de equivalência e valores-limite a partir do Anexo A e escrever os testes JUnit 5 em `PrazoLimiteDevolucaoTest.java` e a documentação correspondente em `PLANO-DE-TESTES.md`.
- **O que aproveitamos:** Os testes parametrizados (`@ParameterizedTest`, `@CsvSource`, `@EnumSource`) cobrindo todos os tipos de usuário, fronteira sábado/domingo/segunda, virada de ano, ano bissexto e entradas nulas.
- **O que tivemos de corrigir / validar:** Validamos cada data e dia da semana no calendário para garantir que os valores esperados refletem exatamente a especificação do Anexo A (sem consultar o `.jar`).

### 3. Testes de `multaCentavos` e `multaTotalCentavos` (Paulo Diego)
- **O que pedimos à IA:** Resumir o que faltava da minha parte no repositório, derivar do Anexo A as classes de equivalência e os valores-limite dos dois métodos, escrever `MultasTest.java` e ajuda para preencher as seções 4 e 5 do `PLANO-DE-TESTES.md` e as falhas em `DEFEITOS.md`, e ajuda para preencher esse doc aqui.
- **O que aproveitamos:** Os testes parametrizados (`@CsvSource`, `@EnumSource`, `@MethodSource`) com as fronteiras de carência (3|4), troca de faixa (10|11 dias cobrados), teto por empréstimo (29|30|31) e teto total (9900|10000|10100), e as partições de tamanho e posição no array.
- **O que tivemos de corrigir / validar:** Após a 1ª execução, a IA apontou que `ALUNO {30,30,30}` passava por coincidência, refiz a conta na mão para confirmar quanto dava, esperado era 5000 + 5000 + 5000 = 15000, limitado a 10000, o sistema ignorando o 1º elemento, devolve 5000 + 5000 = 10000, ou seja, mesmo valor, o esperado 10000 está correto, mas o teste passaria mesmo com o teto total quebrado, então ele sozinho não testava esse teto. Por isso foram acrescentadas as classes V7 (posição do atraso na lista) e VL3 (teto total com 0 na 1ª posição). Conferimos todos os valores esperados recalculando-os a partir do Anexo A (nenhum divergiu). Na revisão, acrescentamos o limite superior do tipo `int` (`Integer.MAX_VALUE`) em `multaCentavos`, que a primeira versão não cobria.

### 4. Testes de `podeEmprestar` e `podeRenovar` (Claudemir Vieira)
- **O que pedimos à IA:** A partir do `README.md` e da especificação do Anexo A no enunciado, derivar as classes de equivalência (válidas e inválidas) e os valores-limite de `podeEmprestar` e `podeRenovar`, implementar os testes JUnit 5 em `EmprestimoERenovacaoTest.java`, preencher as seções 6 e 7 de `PLANO-DE-TESTES.md`, executar a suíte com Maven, registrar todas as falhas encontradas em `DEFEITOS.md` e documentar o uso de IA aqui.
- **O que aproveitamos:** A organização em classes `@Nested` com `@ParameterizedTest`/`@CsvSource`, cobrindo todas as fronteiras de limite de empréstimos (`limite - 1 | limite | limite + 1`), teto de multa (`999 | 1000 | 1001`), limites de renovação por perfil (`VISITANTE`, `ALUNO` 2, `PROFESSOR` 3), extremos de `int` (`Integer.MIN_VALUE`, `Integer.MAX_VALUE`), validações de entradas inválidas inclusive combinadas com condições impeditivas, e o preenchimento estruturado do plano de testes e da tabela de defeitos.
- **O que tivemos de corrigir / validar:** Validamos cada valor esperado diretamente contra o texto do Anexo A (sem descompilar `lib/biblioteca-1.0.jar`). Após a primeira execução dos testes, refinamos os cenários de fronteira em dois pontos: (1) em `podeEmprestar`, ao combinar `possuiAtraso = true` com as fronteiras de multa válida (`0`, `1`, `999`, `1000`), percebemos que `999` retornava `true` enquanto `1000` retornava `false`, evidenciando um defeito adicional na fronteira `< 1000` que ficava mascarado sem atraso pelo defeito do operador lógico `||`; (2) em `podeRenovar`, expandimos os casos de `VISITANTE` para incluir `2` e `3` (além de `0`, `1` e `5`), permitindo observar por caixa-preta que `VISITANTE` seguia exatamente o limite de `ALUNO` (2 renovações). Também conferimos a remoção de imports não utilizados e que arquivos temporários/enunciado não fossem incluídos nos commits.

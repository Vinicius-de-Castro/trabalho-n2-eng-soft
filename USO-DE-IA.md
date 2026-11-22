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

### 4. *(Adicionar novos registros conforme a equipe utilizar IA)*
- **O que pedimos à IA:**
- **O que aproveitamos:**
- **O que tivemos de corrigir:**

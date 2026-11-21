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

### 3. *(Adicionar novos registros conforme a equipe utilizar IA)*
- **O que pedimos à IA:**
- **O que aproveitamos:**
- **O que tivemos de corrigir:**

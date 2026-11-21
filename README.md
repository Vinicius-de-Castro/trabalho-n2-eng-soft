# Projeto de Teste de Software — Biblioteca

Repositório da equipe para o trabalho de Engenharia de Software.

## Equipe
- **Vinicius Rodrigues**
- **Paulo Diego**
- **Claudemir Vieira**

## Prazos
- **24/11/2026 (23h59):** Entrega da **Fase 1** (Caixa-preta) — vale o último commit na `main`.
- **25/11/2026:** Arguição 1 (presencial e individual) + liberação do código-fonte.
- **30/11/2026 (23h59):** Entrega das **Fases 2 e 3** (Caixa-branca e TDD).
- **01 e 02/12/2026:** Arguição 2 (presencial e individual).

---

## Divisão da Fase 1 (Caixa-preta)

Cada integrante é responsável por levantar as classes de equivalência (válidas e inválidas) e os valores-limite dos seus métodos, escrever os testes JUnit 5 no seu arquivo, preencher a sua seção em `PLANO-DE-TESTES.md` e registrar todas as falhas encontradas em `DEFEITOS.md`.

| Integrante | Métodos | Arquivo de teste |
| :--- | :--- | :--- |
| **Vinicius Rodrigues** | `prazoDias`, `limiteEmprestimos`, `dataDevolucao` | `src/test/java/br/edu/biblioteca/PrazoLimiteDevolucaoTest.java` |
| **Paulo Diego** | `multaCentavos`, `multaTotalCentavos` | `src/test/java/br/edu/biblioteca/MultasTest.java` |
| **Claudemir Vieira** | `podeEmprestar`, `podeRenovar` | `src/test/java/br/edu/biblioteca/EmprestimoERenovacaoTest.java` |

---

## Como rodar os testes
Requisitos: **JDK 17+** e Maven (ou abrir a pasta `kit-fase1` diretamente no IntelliJ / VS Code / Eclipse).

```bash
mvn test
```

Os relatórios ficam em `target/surefire-reports/`. O `pom.xml` está configurado com `<testFailureIgnore>true</testFailureIgnore>`, então o build não falha quando um teste encontra um defeito.

---

## Fluxo de Git combinado
1. Crie uma branch a partir da `main` para a sua parte:
   ```bash
   git checkout -b test/vinicius-prazo-devolucao
   git checkout -b test/paulo-multas
   git checkout -b test/claudemir-emprestimo-renovacao
   ```
2. Faça commits claros conforme avança.
3. Abra Pull Request (ou faça merge) na `main` até **23/11** para revisarmos juntos antes do prazo final (**24/11 às 23h59**).

---

## Regras importantes (não quebrar!)
- **Não descompilar `lib/biblioteca-1.0.jar`** na Fase 1.
- **O resultado esperado vem sempre da especificação (Anexo A).** Nunca altere o `assert` para bater com o retorno do sistema — teste falhando significa defeito encontrado.
- **Não alterar nomes de classes ou métodos do Anexo A.**
- **Registrar uso de IA** em `USO-DE-IA.md` (o que pediu, o que aproveitou e o que corrigiu).
- **Preparação para a Arguição 1 (25/11):** 60% da nota é individual e o professor sorteia quem responde sobre qualquer método. Antes da aula, faremos uma revisão rápida onde cada um explica seus testes aos colegas.

---

## Prévia das Fases 2 e 3 (após 25/11)
- **Fase 2 (Caixa-branca):** 1 grafo de fluxo de controle + cálculo de `V(G)` + conjunto-base para cada integrante (`multaCentavos`, `podeRenovar`, `multaTotalCentavos`) + completar cobertura de ramos no JaCoCo.
- **Fase 3 (TDD — `FilaReservas`):** commits sequenciais obrigatórios com prefixos `[red]`, `[green]` e `[refactor]`.

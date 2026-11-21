package br.edu.biblioteca;

import org.junit.jupiter.api.DisplayName;

/**
 * Testes de caixa-preta (Fase 1) — Responsável: Claudemir.
 *
 * Métodos cobertos:
 * - podeEmprestar(TipoUsuario tipo, int emprestimosAtivos, int multaPendenteCentavos, boolean possuiAtraso)
 * - podeRenovar(TipoUsuario tipo, int renovacoesFeitas, boolean possuiReserva, boolean emAtraso)
 */
@DisplayName("Regras de Empréstimo e Renovação")
class EmprestimoERenovacaoTest {

    private final RegrasBiblioteca regras = new RegrasBiblioteca();

    // TODO: implementar testes de partição de equivalência e valor-limite
}

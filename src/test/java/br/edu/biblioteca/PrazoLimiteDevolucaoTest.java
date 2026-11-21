package br.edu.biblioteca;

import org.junit.jupiter.api.DisplayName;

/**
 * Testes de caixa-preta (Fase 1) — Responsável: Vinicius.
 *
 * Métodos cobertos:
 * - prazoDias(TipoUsuario tipo)
 * - limiteEmprestimos(TipoUsuario tipo)
 * - dataDevolucao(LocalDate retirada, TipoUsuario tipo)
 */
@DisplayName("Prazo, Limite de Empréstimos e Data de Devolução")
class PrazoLimiteDevolucaoTest {

    private final RegrasBiblioteca regras = new RegrasBiblioteca();

    // TODO: implementar testes de partição de equivalência e valor-limite
}

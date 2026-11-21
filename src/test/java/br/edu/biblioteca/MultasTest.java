package br.edu.biblioteca;

import org.junit.jupiter.api.DisplayName;

/**
 * Testes de caixa-preta (Fase 1) — Responsável: Paulo.
 *
 * Métodos cobertos:
 * - multaCentavos(TipoUsuario tipo, int diasAtraso)
 * - multaTotalCentavos(TipoUsuario tipo, int[] diasAtrasoPorEmprestimo)
 */
@DisplayName("Cálculo de Multa Unitária e Multa Total")
class MultasTest {

    private final RegrasBiblioteca regras = new RegrasBiblioteca();

    // TODO: implementar testes de partição de equivalência e valor-limite
}

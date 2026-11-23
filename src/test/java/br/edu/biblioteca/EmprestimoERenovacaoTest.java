package br.edu.biblioteca;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * Testes de caixa-preta (Fase 1) — Responsável: Claudemir Vieira.
 *
 * Métodos cobertos:
 * - podeEmprestar(TipoUsuario tipo, int emprestimosAtivos, int multaPendenteCentavos, boolean possuiAtraso)
 * - podeRenovar(TipoUsuario tipo, int renovacoesFeitas, boolean possuiReserva, boolean emAtraso)
 *
 * Regras (Anexo A):
 * - podeEmprestar: true somente se emprestimosAtivos < limite do tipo (ALUNO: 3, PROFESSOR: 8,
 *   VISITANTE: 1), multaPendenteCentavos <= 1000 (R$ 10,00) e possuiAtraso == false.
 *   tipo nulo, emprestimosAtivos < 0 ou multaPendenteCentavos < 0 lançam IllegalArgumentException.
 * - podeRenovar: VISITANTE nunca renova; ALUNO renova até 2 vezes (renovacoesFeitas < 2);
 *   PROFESSOR até 3 vezes (renovacoesFeitas < 3); sempre exige possuiReserva == false e
 *   emAtraso == false. tipo nulo ou renovacoesFeitas < 0 (para qualquer tipo) lançam
 *   IllegalArgumentException.
 * Os IDs nos comentários correspondem à tabela em PLANO-DE-TESTES.md.
 */
@DisplayName("Regras de Empréstimo e Renovação")
class EmprestimoERenovacaoTest {

    private final RegrasBiblioteca regras = new RegrasBiblioteca();

    @Nested
    @DisplayName("podeEmprestar(TipoUsuario, int, int, boolean)")
    class PodeEmprestarTest {

        @ParameterizedTest(name = "tipo nulo com ativos={0}, multa={1}, atraso={2} deve lançar IllegalArgumentException")
        @CsvSource({
            "0, 0, false",     // I1: tipo nulo com demais entradas válidas
            "5, 2000, true"    // I1: tipo nulo mesmo com condições impeditivas
        })
        void lancaExcecaoQuandoTipoNulo(int emprestimosAtivos, int multaCentavos, boolean possuiAtraso) {
            assertThrows(IllegalArgumentException.class,
                    () -> regras.podeEmprestar(null, emprestimosAtivos, multaCentavos, possuiAtraso));
        }

        @ParameterizedTest(name = "{0} com ativos={1}, multa={2}, atraso={3} deve lançar IllegalArgumentException")
        @CsvSource({
            "ALUNO, -1, 0, false",            // VL1: logo abaixo de 0
            "PROFESSOR, -1, 0, false",        // VL1
            "VISITANTE, -1, 0, false",        // VL1
            "ALUNO, -1, 0, true",             // VL1: negativo mesmo com atraso
            "ALUNO, -1, 2000, false",         // VL1: negativo mesmo com multa acima do limite
            "ALUNO, -5, 0, false",            // I2: negativo típico
            "ALUNO, -2147483648, 0, false"    // VL1: menor int (Integer.MIN_VALUE)
        })
        void lancaExcecaoQuandoEmprestimosAtivosNegativo(
                TipoUsuario tipo, int emprestimosAtivos, int multaCentavos, boolean possuiAtraso) {
            assertThrows(IllegalArgumentException.class,
                    () -> regras.podeEmprestar(tipo, emprestimosAtivos, multaCentavos, possuiAtraso));
        }

        @ParameterizedTest(name = "{0} com ativos={1}, multa={2}, atraso={3} deve lançar IllegalArgumentException")
        @CsvSource({
            "ALUNO, 0, -1, false",            // VL2: logo abaixo de 0
            "PROFESSOR, 0, -1, false",        // VL2
            "VISITANTE, 0, -1, false",        // VL2
            "ALUNO, 0, -1, true",             // VL2: multa negativa mesmo com atraso
            "ALUNO, 3, -1, false",            // VL2: multa negativa mesmo no limite de empréstimos
            "ALUNO, 0, -500, false",          // I3: negativo típico
            "ALUNO, 0, -2147483648, false"    // VL2: menor int (Integer.MIN_VALUE)
        })
        void lancaExcecaoQuandoMultaPendenteNegativa(
                TipoUsuario tipo, int emprestimosAtivos, int multaCentavos, boolean possuiAtraso) {
            assertThrows(IllegalArgumentException.class,
                    () -> regras.podeEmprestar(tipo, emprestimosAtivos, multaCentavos, possuiAtraso));
        }

        @ParameterizedTest(name = "tipo={0}, ativos={1}, multa={2} (múltiplos inválidos) deve lançar IllegalArgumentException")
        @CsvSource(value = {
            "ALUNO, -1, -1, false",      // I4: ambos numéricos negativos
            "null, -1, -1, false"        // I4: tipo nulo e ambos negativos
        }, nullValues = "null")
        void lancaExcecaoQuandoMultiplasEntradasInvalidas(
                TipoUsuario tipo, int emprestimosAtivos, int multaCentavos, boolean possuiAtraso) {
            assertThrows(IllegalArgumentException.class,
                    () -> regras.podeEmprestar(tipo, emprestimosAtivos, multaCentavos, possuiAtraso));
        }

        @ParameterizedTest(name = "{0} com ativos={1} e multa={2} sem atraso deve poder emprestar")
        @CsvSource({
            "ALUNO, 0, 0",         // V1 / VL3: menor valor válido
            "PROFESSOR, 0, 0",     // V1 / VL3
            "VISITANTE, 0, 0",     // V1 / VL3 (também limite - 1 do visitante)
            "ALUNO, 1, 500",       // V2: valores intermediários válidos
            "PROFESSOR, 4, 500",   // V2
            "ALUNO, 2, 0",         // VL4: limite - 1 do aluno (3 - 1)
            "PROFESSOR, 7, 0",     // VL4: limite - 1 do professor (8 - 1)
            "ALUNO, 0, 1",         // VL5: menor multa positiva válida
            "ALUNO, 0, 999",       // VL5: logo abaixo do teto de R$ 10,00
            "ALUNO, 0, 1000",      // VL5: teto exato de R$ 10,00
            "PROFESSOR, 0, 1000",  // VL5
            "VISITANTE, 0, 1000",  // VL5: teto de multa + limite - 1 do visitante
            "ALUNO, 2, 1000",      // VL5: combinação limite - 1 e teto de multa
            "PROFESSOR, 7, 1000"   // VL5: combinação limite - 1 e teto de multa
        })
        void permiteEmprestimoQuandoTodasCondicoesSaoAtendidas(
                TipoUsuario tipo, int emprestimosAtivos, int multaCentavos) {
            assertTrue(regras.podeEmprestar(tipo, emprestimosAtivos, multaCentavos, false));
        }

        @ParameterizedTest(name = "{0} com {1} empréstimos ativos (no limite ou acima) não pode emprestar")
        @CsvSource({
            "ALUNO, 3",            // VL6: exatamente no limite do aluno (3)
            "ALUNO, 4",            // VL6: logo acima do limite do aluno
            "PROFESSOR, 8",        // VL6: exatamente no limite do professor (8)
            "PROFESSOR, 9",        // VL6: logo acima do limite do professor
            "VISITANTE, 1",        // VL6: exatamente no limite do visitante (1)
            "VISITANTE, 2",        // VL6: logo acima do limite do visitante
            "ALUNO, 10",           // V3: muito acima do limite
            "PROFESSOR, 20",       // V3
            "VISITANTE, 5",        // V3
            "ALUNO, 2147483647"    // V3 / VL8: maior int (Integer.MAX_VALUE)
        })
        void bloqueiaQuandoAtingeOuExcedeLimiteDeEmprestimos(TipoUsuario tipo, int emprestimosAtivos) {
            assertFalse(regras.podeEmprestar(tipo, emprestimosAtivos, 0, false));
        }

        @ParameterizedTest(name = "{0} com multa de {1} centavos (> R$ 10,00) não pode emprestar")
        @CsvSource({
            "ALUNO, 1001",         // VL7: logo acima do limite de R$ 10,00
            "PROFESSOR, 1001",     // VL7
            "VISITANTE, 1001",     // VL7
            "ALUNO, 2500",         // V4: muito acima do limite
            "PROFESSOR, 5000",     // V4
            "ALUNO, 2147483647"    // V4 / VL8: maior int (Integer.MAX_VALUE)
        })
        void bloqueiaQuandoMultaPendenteExcedeLimite(TipoUsuario tipo, int multaCentavos) {
            assertFalse(regras.podeEmprestar(tipo, 0, multaCentavos, false));
        }

        @ParameterizedTest(name = "{0} com ativos={1}, multa={2} e empréstimo em atraso não pode emprestar")
        @CsvSource({
            "ALUNO, 0, 0",         // V5: atraso como único impeditivo
            "PROFESSOR, 0, 0",     // V5
            "VISITANTE, 0, 0",     // V5
            "ALUNO, 0, 1",         // V5: atraso com menor multa positiva válida
            "ALUNO, 0, 999",       // V5: atraso logo abaixo do teto de multa
            "ALUNO, 0, 1000",      // V5: atraso no teto exato de multa
            "ALUNO, 2, 1000"       // V5: atraso no limite válido de empréstimos e multa
        })
        void bloqueiaQuandoPossuiAtraso(TipoUsuario tipo, int emprestimosAtivos, int multaCentavos) {
            assertFalse(regras.podeEmprestar(tipo, emprestimosAtivos, multaCentavos, true));
        }

        @ParameterizedTest(name = "{0} com ativos={1}, multa={2}, atraso={3} (múltiplos bloqueios) não pode emprestar")
        @CsvSource({
            "ALUNO, 3, 1001, false",     // V6: limite + multa acima
            "ALUNO, 3, 0, true",         // V6: limite + atraso
            "ALUNO, 0, 1001, true",      // V6: multa acima + atraso
            "ALUNO, 3, 1001, true",      // V6: todas as 3 condições falham
            "PROFESSOR, 8, 1500, true",  // V6
            "VISITANTE, 1, 1500, true"   // V6
        })
        void bloqueiaQuandoMultiplasCondicoesFalham(
                TipoUsuario tipo, int emprestimosAtivos, int multaCentavos, boolean possuiAtraso) {
            assertFalse(regras.podeEmprestar(tipo, emprestimosAtivos, multaCentavos, possuiAtraso));
        }
    }

    @Nested
    @DisplayName("podeRenovar(TipoUsuario, int, boolean, boolean)")
    class PodeRenovarTest {

        @ParameterizedTest(name = "tipo nulo com renovacoes={0}, reserva={1}, atraso={2} deve lançar IllegalArgumentException")
        @CsvSource({
            "0, false, false",   // I1: tipo nulo com demais entradas válidas
            "0, true, false",    // I1: tipo nulo com reserva
            "0, false, true",    // I1: tipo nulo com atraso
            "-1, false, false"   // I1: tipo nulo e renovações negativas
        })
        void lancaExcecaoQuandoTipoNulo(int renovacoesFeitas, boolean possuiReserva, boolean emAtraso) {
            assertThrows(IllegalArgumentException.class,
                    () -> regras.podeRenovar(null, renovacoesFeitas, possuiReserva, emAtraso));
        }

        @ParameterizedTest(name = "{0} com renovacoes={1}, reserva={2}, atraso={3} deve lançar IllegalArgumentException")
        @CsvSource({
            "ALUNO, -1, false, false",            // VL1: logo abaixo de 0
            "PROFESSOR, -1, false, false",        // VL1
            "VISITANTE, -1, false, false",        // VL1: visitante também deve lançar exceção
            "ALUNO, -1, true, false",             // VL1: negativo mesmo com reserva
            "PROFESSOR, -1, false, true",         // VL1: negativo mesmo em atraso
            "VISITANTE, -1, true, true",          // VL1: visitante negativo com reserva e atraso
            "ALUNO, -5, false, false",            // I2: negativo típico
            "PROFESSOR, -5, false, false",        // I2
            "VISITANTE, -5, false, false",        // I2
            "ALUNO, -2147483648, false, false"    // VL1: menor int (Integer.MIN_VALUE)
        })
        void lancaExcecaoQuandoRenovacoesNegativas(
                TipoUsuario tipo, int renovacoesFeitas, boolean possuiReserva, boolean emAtraso) {
            assertThrows(IllegalArgumentException.class,
                    () -> regras.podeRenovar(tipo, renovacoesFeitas, possuiReserva, emAtraso));
        }

        @ParameterizedTest(name = "VISITANTE com {0} renovações feitas nunca pode renovar")
        @CsvSource({
            "0",  // V1 / VL2: menor valor válido
            "1",  // V1 / VL2: logo acima de 0
            "2",  // V1: limite do aluno (ainda inválido para visitante)
            "3",  // V1: limite do professor (ainda inválido para visitante)
            "5"   // V1: típico
        })
        void visitanteNuncaRenova(int renovacoesFeitas) {
            assertFalse(regras.podeRenovar(TipoUsuario.VISITANTE, renovacoesFeitas, false, false));
        }

        @ParameterizedTest(name = "ALUNO com {0} renovações feitas (sem reserva nem atraso) -> {1}")
        @CsvSource({
            "0, true",   // V2 / VL3: 1ª renovação (menor valor válido)
            "1, true",   // V2 / VL3: 2ª e última renovação permitida (limite - 1)
            "2, false",  // VL4: já renovou 2 vezes (limite exato)
            "3, false",  // VL4: logo acima do limite
            "10, false"  // V3: muito acima do limite
        })
        void alunoRenovaAteDuasVezes(int renovacoesFeitas, boolean esperado) {
            assertEquals(esperado, regras.podeRenovar(TipoUsuario.ALUNO, renovacoesFeitas, false, false));
        }

        @ParameterizedTest(name = "PROFESSOR com {0} renovações feitas (sem reserva nem atraso) -> {1}")
        @CsvSource({
            "0, true",           // V4 / VL5: 1ª renovação (menor valor válido)
            "1, true",           // V4: 2ª renovação (intermediário)
            "2, true",           // V4 / VL5: 3ª e última renovação permitida (limite - 1)
            "3, false",          // VL6: já renovou 3 vezes (limite exato)
            "4, false",          // VL6: logo acima do limite
            "10, false",         // V5: muito acima do limite
            "2147483647, false"  // V5 / VL7: maior int (Integer.MAX_VALUE)
        })
        void professorRenovaAteTresVezes(int renovacoesFeitas, boolean esperado) {
            assertEquals(esperado, regras.podeRenovar(TipoUsuario.PROFESSOR, renovacoesFeitas, false, false));
        }

        @ParameterizedTest(name = "{0} com {1} renovações feitas e obra reservada não pode renovar")
        @CsvSource({
            "ALUNO, 0",      // V6: reserva como único impeditivo
            "ALUNO, 1",      // V6: reserva na última renovação permitida
            "PROFESSOR, 0",  // V6
            "PROFESSOR, 2",  // V6: reserva na última renovação permitida
            "VISITANTE, 0"   // V6
        })
        void bloqueiaQuandoPossuiReserva(TipoUsuario tipo, int renovacoesFeitas) {
            assertFalse(regras.podeRenovar(tipo, renovacoesFeitas, true, false));
        }

        @ParameterizedTest(name = "{0} com {1} renovações feitas e empréstimo em atraso não pode renovar")
        @CsvSource({
            "ALUNO, 0",      // V7: atraso como único impeditivo
            "ALUNO, 1",      // V7: atraso na última renovação permitida
            "PROFESSOR, 0",  // V7
            "PROFESSOR, 2",  // V7: atraso na última renovação permitida
            "VISITANTE, 0"   // V7
        })
        void bloqueiaQuandoEmAtraso(TipoUsuario tipo, int renovacoesFeitas) {
            assertFalse(regras.podeRenovar(tipo, renovacoesFeitas, false, true));
        }

        @ParameterizedTest(name = "{0} com renovacoes={1}, reserva={2}, atraso={3} (múltiplos bloqueios) não pode renovar")
        @CsvSource({
            "ALUNO, 0, true, true",      // V8: reserva + atraso
            "PROFESSOR, 0, true, true",  // V8: reserva + atraso
            "VISITANTE, 0, true, true",  // V8: visitante + reserva + atraso
            "ALUNO, 2, true, false",     // V8: limite + reserva
            "ALUNO, 2, false, true",     // V8: limite + atraso
            "ALUNO, 2, true, true",      // V8: limite + reserva + atraso
            "PROFESSOR, 3, true, false", // V8: limite + reserva
            "PROFESSOR, 3, false, true", // V8: limite + atraso
            "PROFESSOR, 3, true, true"   // V8: limite + reserva + atraso
        })
        void bloqueiaQuandoMultiplasCondicoesFalham(
                TipoUsuario tipo, int renovacoesFeitas, boolean possuiReserva, boolean emAtraso) {
            assertFalse(regras.podeRenovar(tipo, renovacoesFeitas, possuiReserva, emAtraso));
        }
    }
}

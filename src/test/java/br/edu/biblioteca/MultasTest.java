package br.edu.biblioteca;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Testes de caixa-preta (Fase 1) — Responsável: Paulo Diego.
 *
 * Métodos cobertos:
 * - multaCentavos(TipoUsuario tipo, int diasAtraso)
 * - multaTotalCentavos(TipoUsuario tipo, int[] diasAtrasoPorEmprestimo)
 *
 * Regra (Anexo A): dias cobrados = diasAtraso (professor: diasAtraso - 3, mínimo 0);
 * 1º ao 10º dia cobrado = 100/dia; 11º em diante = 200/dia; teto de 5000 por empréstimo
 * e teto de 10000 no total. Os IDs nos comentários são os de PLANO-DE-TESTES.md.
 */
@DisplayName("Cálculo de Multa Unitária e Multa Total")
class MultasTest {

    private final RegrasBiblioteca regras = new RegrasBiblioteca();

    @Nested
    @DisplayName("multaCentavos(TipoUsuario, int)")
    class MultaCentavosTest {

        @Test
        @DisplayName("Tipo nulo deve lançar IllegalArgumentException")
        void lancaExcecaoQuandoTipoNulo() { // I1
            assertThrows(IllegalArgumentException.class, () -> regras.multaCentavos(null, 5));
        }

        @ParameterizedTest(name = "{0} com {1} dias de atraso deve lançar IllegalArgumentException")
        @CsvSource({
            "ALUNO, -1",      // VL1: logo abaixo de 0
            "PROFESSOR, -1",  // VL1
            "VISITANTE, -1",  // VL1
            "ALUNO, -10"      // I2: negativo típico
        })
        void lancaExcecaoQuandoAtrasoNegativo(TipoUsuario tipo, int dias) {
            assertThrows(IllegalArgumentException.class, () -> regras.multaCentavos(tipo, dias));
        }

        @ParameterizedTest(name = "{0} sem atraso (0 dias) não paga multa")
        @EnumSource(TipoUsuario.class)
        void semAtrasoNaoHaMulta(TipoUsuario tipo) { // VL2
            assertEquals(0, regras.multaCentavos(tipo, 0));
        }

        @ParameterizedTest(name = "{0} com {1} dias de atraso paga {2} centavos")
        @CsvSource({
            "ALUNO, 1, 100",       // VL3: 1º dia cobrado
            "VISITANTE, 1, 100",   // VL3
            "ALUNO, 5, 500",       // V1: faixa de R$ 1,00
            "ALUNO, 9, 900",       // VL4: logo abaixo da troca de faixa
            "ALUNO, 10, 1000",     // VL4: último dia a R$ 1,00
            "VISITANTE, 10, 1000", // VL4
            "ALUNO, 11, 1200",     // VL4: 1º dia a R$ 2,00
            "VISITANTE, 11, 1200", // VL4
            "ALUNO, 12, 1400",     // V2: exemplo do Anexo A
            "ALUNO, 20, 3000",     // V2: faixa de R$ 2,00
            "ALUNO, 29, 4800",     // VL5: logo abaixo do teto
            "ALUNO, 30, 5000",     // VL5: atinge o teto exato
            "VISITANTE, 30, 5000", // VL5
            "ALUNO, 31, 5000",     // VL5: acima do teto, continua 5000
            "VISITANTE, 31, 5000", // VL5
            "ALUNO, 100, 5000",    // V3: muito acima do teto
            "ALUNO, 2147483647, 5000" // VL9: maior int (Integer.MAX_VALUE)
        })
        void alunoEVisitanteSemCarencia(TipoUsuario tipo, int dias, int esperado) {
            assertEquals(esperado, regras.multaCentavos(tipo, dias));
        }

        @ParameterizedTest(name = "PROFESSOR com {0} dias de atraso paga {1} centavos")
        @CsvSource({
            "1, 0",     // V4: dentro da carência
            "2, 0",     // VL6: logo abaixo do fim da carência
            "3, 0",     // VL6: último dia de carência
            "4, 100",   // VL6: 1º dia cobrado
            "8, 500",   // V5: faixa de R$ 1,00 após a carência
            "13, 1000", // VL7: 10º dia cobrado
            "14, 1200", // VL7: 11º dia cobrado
            "20, 2400", // V6: faixa de R$ 2,00 após a carência
            "32, 4800", // VL8: logo abaixo do teto
            "33, 5000", // VL8: atinge o teto exato
            "34, 5000", // VL8: acima do teto
            "100, 5000", // V7: muito acima do teto
            "2147483647, 5000" // VL9: maior int (Integer.MAX_VALUE)
        })
        void professorComCarenciaDe3Dias(int dias, int esperado) {
            assertEquals(esperado, regras.multaCentavos(TipoUsuario.PROFESSOR, dias));
        }
    }

    @Nested
    @DisplayName("multaTotalCentavos(TipoUsuario, int[])")
    class MultaTotalCentavosTest {

        @Test
        @DisplayName("Tipo nulo deve lançar IllegalArgumentException (lista com elementos ou vazia)")
        void lancaExcecaoQuandoTipoNulo() { // I1
            assertThrows(IllegalArgumentException.class, () -> regras.multaTotalCentavos(null, new int[] {5}));
            assertThrows(IllegalArgumentException.class, () -> regras.multaTotalCentavos(null, new int[] {}));
        }

        @ParameterizedTest(name = "{0} com lista nula deve lançar IllegalArgumentException")
        @EnumSource(TipoUsuario.class)
        void lancaExcecaoQuandoListaNula(TipoUsuario tipo) { // I2
            assertThrows(IllegalArgumentException.class, () -> regras.multaTotalCentavos(tipo, null));
        }

        static Stream<Arguments> listasComNegativo() {
            return Stream.of(
                arguments(TipoUsuario.ALUNO, new int[] {-1, 5}),      // I3: negativo no início
                arguments(TipoUsuario.ALUNO, new int[] {5, -1, 5}),   // I3: negativo no meio
                arguments(TipoUsuario.ALUNO, new int[] {5, -1}),      // I3: negativo no fim
                arguments(TipoUsuario.PROFESSOR, new int[] {-1}),     // I3: professor (carência não "absorve" negativo)
                arguments(TipoUsuario.VISITANTE, new int[] {0, -10})  // I3: negativo típico após um 0 válido
            );
        }

        @ParameterizedTest(name = "{0} com lista {1} deve lançar IllegalArgumentException")
        @MethodSource("listasComNegativo")
        void lancaExcecaoQuandoElementoNegativo(TipoUsuario tipo, int[] dias) {
            assertThrows(IllegalArgumentException.class, () -> regras.multaTotalCentavos(tipo, dias));
        }

        @ParameterizedTest(name = "{0} com lista vazia tem total 0")
        @EnumSource(TipoUsuario.class)
        void listaVaziaDaTotalZero(TipoUsuario tipo) { // V1 / VL1
            assertEquals(0, regras.multaTotalCentavos(tipo, new int[] {}));
        }

        static Stream<Arguments> listasValidas() {
            return Stream.of(
                arguments(TipoUsuario.ALUNO, new int[] {0}, 0),                   // V2: um elemento sem atraso
                arguments(TipoUsuario.ALUNO, new int[] {12}, 1400),               // V2: um elemento = multaCentavos
                arguments(TipoUsuario.ALUNO, new int[] {5, 12}, 1900),            // V3: soma de vários
                arguments(TipoUsuario.VISITANTE, new int[] {1, 1, 1}, 300),       // V3
                arguments(TipoUsuario.PROFESSOR, new int[] {3, 4}, 100),          // V4: carência por empréstimo
                arguments(TipoUsuario.PROFESSOR, new int[] {3, 3, 3}, 0),         // V4: carência não soma os dias
                arguments(TipoUsuario.ALUNO, new int[] {40}, 5000),               // V5: teto de 5000 por empréstimo
                arguments(TipoUsuario.ALUNO, new int[] {40, 1}, 5100),            // V5: teto aplicado antes de somar
                arguments(TipoUsuario.ALUNO, new int[] {30, 29, 1}, 9900),        // VL2: logo abaixo do teto total
                arguments(TipoUsuario.ALUNO, new int[] {30, 29, 2}, 10000),       // VL2: atinge o teto total
                arguments(TipoUsuario.ALUNO, new int[] {30, 29, 3}, 10000),       // VL2: acima do teto total
                arguments(TipoUsuario.PROFESSOR, new int[] {33, 32, 4}, 9900),    // VL2: com carência
                arguments(TipoUsuario.PROFESSOR, new int[] {33, 32, 5}, 10000),   // VL2
                arguments(TipoUsuario.PROFESSOR, new int[] {33, 32, 6}, 10000),   // VL2
                arguments(TipoUsuario.ALUNO, new int[] {30, 30, 30}, 10000),      // V6: muito acima do teto total
                arguments(TipoUsuario.ALUNO, new int[] {12, 0, 0}, 1400),         // V7: atraso no início da lista
                arguments(TipoUsuario.ALUNO, new int[] {0, 12, 0}, 1400),         // V7: atraso no meio da lista
                arguments(TipoUsuario.ALUNO, new int[] {0, 0, 12}, 1400),         // V7: atraso no fim da lista
                arguments(TipoUsuario.ALUNO, new int[] {0, 30, 29, 1}, 9900),     // VL3: teto total com 0 no início
                arguments(TipoUsuario.ALUNO, new int[] {0, 30, 29, 2}, 10000),    // VL3
                arguments(TipoUsuario.ALUNO, new int[] {0, 30, 29, 3}, 10000),    // VL3
                arguments(TipoUsuario.ALUNO, new int[] {0, 30, 30, 30}, 10000)    // V6: com 0 no início
            );
        }

        @ParameterizedTest(name = "{0} com lista {1} tem total {2} centavos")
        @MethodSource("listasValidas")
        void somaMultasComTetoTotal(TipoUsuario tipo, int[] dias, int esperado) {
            assertEquals(esperado, regras.multaTotalCentavos(tipo, dias));
        }
    }
}

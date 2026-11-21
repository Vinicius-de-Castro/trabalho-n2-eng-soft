package br.edu.biblioteca;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;

/**
 * Testes de caixa-preta (Fase 1) — Responsável: Vinicius Rodrigues.
 *
 * Métodos cobertos:
 * - prazoDias(TipoUsuario tipo)
 * - limiteEmprestimos(TipoUsuario tipo)
 * - dataDevolucao(LocalDate retirada, TipoUsuario tipo)
 */
@DisplayName("Prazo, Limite de Empréstimos e Data de Devolução")
class PrazoLimiteDevolucaoTest {

    private final RegrasBiblioteca regras = new RegrasBiblioteca();

    @Nested
    @DisplayName("prazoDias(TipoUsuario)")
    class PrazoDiasTest {

        @ParameterizedTest(name = "{0} deve ter prazo de {1} dias")
        @CsvSource({
            "ALUNO, 14",
            "PROFESSOR, 30",
            "VISITANTE, 7"
        })
        void devolvePrazoConformeTipoUsuario(TipoUsuario tipo, int prazoEsperado) {
            assertEquals(prazoEsperado, regras.prazoDias(tipo));
        }

        @Test
        @DisplayName("Tipo nulo deve lançar IllegalArgumentException")
        void lancaExcecaoQuandoTipoNulo() {
            assertThrows(IllegalArgumentException.class, () -> regras.prazoDias(null));
        }
    }

    @Nested
    @DisplayName("limiteEmprestimos(TipoUsuario)")
    class LimiteEmprestimosTest {

        @ParameterizedTest(name = "{0} deve ter limite de {1} empréstimos simultâneos")
        @CsvSource({
            "ALUNO, 3",
            "PROFESSOR, 8",
            "VISITANTE, 1"
        })
        void devolveLimiteConformeTipoUsuario(TipoUsuario tipo, int limiteEsperado) {
            assertEquals(limiteEsperado, regras.limiteEmprestimos(tipo));
        }

        @Test
        @DisplayName("Tipo nulo deve lançar IllegalArgumentException")
        void lancaExcecaoQuandoTipoNulo() {
            assertThrows(IllegalArgumentException.class, () -> regras.limiteEmprestimos(null));
        }
    }

    @Nested
    @DisplayName("dataDevolucao(LocalDate, TipoUsuario)")
    class DataDevolucaoTest {

        @ParameterizedTest(name = "{1} retirando em {0} (dia útil) deve devolver em {2}")
        @CsvSource({
            // Sexta + 14d (ALUNO) = Sexta
            "2026-11-20, ALUNO, 2026-12-04",
            // Segunda + 14d (ALUNO) = Segunda (fronteira logo após domingo)
            "2026-11-23, ALUNO, 2026-12-07",
            // Sábado + 30d (PROFESSOR) = Segunda (fronteira logo após domingo)
            "2026-11-21, PROFESSOR, 2026-12-21",
            // Segunda + 7d (VISITANTE) = Segunda (fronteira logo após domingo)
            "2026-11-23, VISITANTE, 2026-11-30"
        })
        void devolucaoEmDiaUtilNaoAlteraData(LocalDate retirada, TipoUsuario tipo, LocalDate esperado) {
            assertEquals(esperado, regras.dataDevolucao(retirada, tipo));
        }

        @ParameterizedTest(name = "{1} retirando em {0} cai no sábado ({2}) e não deve ser adiado")
        @CsvSource({
            // Sábado + 14d (ALUNO) = Sábado
            "2026-11-21, ALUNO, 2026-12-05",
            // Quinta + 30d (PROFESSOR) = Sábado
            "2026-11-19, PROFESSOR, 2026-12-19",
            // Sábado + 7d (VISITANTE) = Sábado
            "2026-11-21, VISITANTE, 2026-11-28"
        })
        void devolucaoNoSabadoEDiaNormal(LocalDate retirada, TipoUsuario tipo, LocalDate esperado) {
            assertEquals(esperado, regras.dataDevolucao(retirada, tipo));
        }

        @ParameterizedTest(name = "{1} retirando em {0} cai no domingo e passa para segunda ({2})")
        @CsvSource({
            // Domingo + 14d (ALUNO) = Domingo (2026-12-06) -> Segunda (2026-12-07)
            "2026-11-22, ALUNO, 2026-12-07",
            // Sexta + 30d (PROFESSOR) = Domingo (2026-12-20) -> Segunda (2026-12-21)
            "2026-11-20, PROFESSOR, 2026-12-21",
            // Domingo + 7d (VISITANTE) = Domingo (2026-11-29) -> Segunda (2026-11-30)
            "2026-11-22, VISITANTE, 2026-11-30"
        })
        void devolucaoNoDomingoPassaParaSegunda(LocalDate retirada, TipoUsuario tipo, LocalDate esperado) {
            assertEquals(esperado, regras.dataDevolucao(retirada, tipo));
        }

        @ParameterizedTest(name = "Virada de mês/ano ou bissexto: {1} em {0} -> {2}")
        @CsvSource({
            // Virada de ano caindo no domingo: 2026-12-20 + 14d = 2027-01-03 (Dom) -> 2027-01-04 (Seg)
            "2026-12-20, ALUNO, 2027-01-04",
            // Ano bissexto (fevereiro com 29 dias): 2024-02-20 + 14d = 2024-03-05 (Ter)
            "2024-02-20, ALUNO, 2024-03-05",
            // Ano não bissexto (fevereiro com 28 dias): 2026-02-20 + 14d = 2026-03-06 (Sex)
            "2026-02-20, ALUNO, 2026-03-06"
        })
        void devolucaoEmViradaDeCalendario(LocalDate retirada, TipoUsuario tipo, LocalDate esperado) {
            assertEquals(esperado, regras.dataDevolucao(retirada, tipo));
        }

        @ParameterizedTest(name = "Retirada nula com tipo {0} deve lançar IllegalArgumentException")
        @EnumSource(TipoUsuario.class)
        void lancaExcecaoQuandoRetiradaNula(TipoUsuario tipo) {
            assertThrows(IllegalArgumentException.class, () -> regras.dataDevolucao(null, tipo));
        }

        @Test
        @DisplayName("Tipo nulo com retirada válida deve lançar IllegalArgumentException")
        void lancaExcecaoQuandoTipoNulo() {
            assertThrows(IllegalArgumentException.class,
                    () -> regras.dataDevolucao(LocalDate.of(2026, 11, 20), null));
        }

        @Test
        @DisplayName("Retirada nula e tipo nulo devem lançar IllegalArgumentException")
        void lancaExcecaoQuandoAmbosNulos() {
            assertThrows(IllegalArgumentException.class, () -> regras.dataDevolucao(null, null));
        }
    }
}

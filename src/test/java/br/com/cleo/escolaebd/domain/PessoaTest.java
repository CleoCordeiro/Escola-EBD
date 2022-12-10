package br.com.cleo.escolaebd.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;

import br.com.cleo.escolaebd.domain.enumeration.FaixaEtaria;
import br.com.cleo.escolaebd.domain.enumeration.TipoPessoa;
import br.com.cleo.escolaebd.web.rest.TestUtil;
import java.time.LocalDate;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class PessoaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pessoa.class);
        Pessoa pessoa1 = new Pessoa();
        pessoa1.setId(UUID.randomUUID());
        Pessoa pessoa2 = new Pessoa();
        pessoa2.setId(pessoa1.getId());
        assertThat(pessoa1).isEqualTo(pessoa2);
        pessoa2.setId(UUID.randomUUID());
        assertThat(pessoa1).isNotEqualTo(pessoa2);
        pessoa1.setId(null);
        assertThat(pessoa1).isNotEqualTo(pessoa2);
    }

    // Matricular um aluno da faixa etária Criança em uma turma da faixa etária
    // Criança
    @Test
    void matricularAlunoCriancaEmTurmaCrianca() {
        Pessoa aluno = new Pessoa();
        aluno.setTipoPessoa(TipoPessoa.ALUNO);
        aluno.setDataNascimento(LocalDate.of(2017, 1, 1));
        Turma turma = new Turma();
        turma.setFaixaEtaria(FaixaEtaria.CRIANCA);
        aluno.setTurma(turma);

        assertThat(aluno.getTurma()).isEqualTo(turma);
    }

    //Tentar Matricular um aluno da faixa etária Adolescente em uma turma da faixa etária Criança
    @Test
    void matricularAlunoAdolescenteEmTurmaCrianca() {
        Pessoa aluno = new Pessoa();
        aluno.setTipoPessoa(TipoPessoa.ALUNO);
        aluno.setDataNascimento(LocalDate.of(2005, 3, 4));
        Turma turma = new Turma();
        assertThrows(RuntimeException.class, () -> aluno.setTurma(turma));
    }

    // Um professor pode ser capaz de dar aula para alunos de qualquer faixa etária
    @Test
    void setarTurmaParaProfessor() {
        Pessoa professor = new Pessoa();
        professor.setTipoPessoa(TipoPessoa.PROFESSOR);
        professor.setDataNascimento(LocalDate.of(1985, 9, 15));
        Turma turma = new Turma();
        professor.setTurma(turma);
        assertThat(professor.getTurma()).isEqualTo(turma);
    }
}

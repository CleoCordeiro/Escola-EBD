package br.com.cleo.escolaebd.service.dto;

import br.com.cleo.escolaebd.domain.enumeration.FaixaEtaria;
import br.com.cleo.escolaebd.domain.enumeration.SexoTurma;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.UUID;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link br.com.cleo.escolaebd.domain.Turma} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TurmaDTO implements Serializable {

    private UUID id;

    @NotNull
    private String nome;

    @NotNull
    private SexoTurma sexoTurma;

    @NotNull
    private FaixaEtaria faixaEtaria;

    @NotNull
    private LocalDate dataInicio;

    private LocalDate dataFim;

    private ZonedDateTime dataCadastro;

    private PessoaDTO professor;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public SexoTurma getSexoTurma() {
        return sexoTurma;
    }

    public void setSexoTurma(SexoTurma sexoTurma) {
        this.sexoTurma = sexoTurma;
    }

    public FaixaEtaria getFaixaEtaria() {
        return faixaEtaria;
    }

    public void setFaixaEtaria(FaixaEtaria faixaEtaria) {
        this.faixaEtaria = faixaEtaria;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    public ZonedDateTime getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(ZonedDateTime dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public PessoaDTO getProfessor() {
        return professor;
    }

    public void setProfessor(PessoaDTO professor) {
        this.professor = professor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TurmaDTO)) {
            return false;
        }

        TurmaDTO turmaDTO = (TurmaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, turmaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TurmaDTO{" +
            "id='" + getId() + "'" +
            ", nome='" + getNome() + "'" +
            ", sexoTurma='" + getSexoTurma() + "'" +
            ", faixaEtaria='" + getFaixaEtaria() + "'" +
            ", dataInicio='" + getDataInicio() + "'" +
            ", dataFim='" + getDataFim() + "'" +
            ", dataCadastro='" + getDataCadastro() + "'" +
            ", professor=" + getProfessor() +
            "}";
    }
}

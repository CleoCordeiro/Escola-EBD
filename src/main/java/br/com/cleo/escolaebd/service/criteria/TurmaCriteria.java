package br.com.cleo.escolaebd.service.criteria;

import br.com.cleo.escolaebd.domain.enumeration.FaixaEtaria;
import br.com.cleo.escolaebd.domain.enumeration.SexoTurma;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link br.com.cleo.escolaebd.domain.Turma} entity. This class is used
 * in {@link br.com.cleo.escolaebd.web.rest.TurmaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /turmas?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TurmaCriteria implements Serializable, Criteria {

    /**
     * Class for filtering SexoTurma
     */
    public static class SexoTurmaFilter extends Filter<SexoTurma> {

        public SexoTurmaFilter() {}

        public SexoTurmaFilter(SexoTurmaFilter filter) {
            super(filter);
        }

        @Override
        public SexoTurmaFilter copy() {
            return new SexoTurmaFilter(this);
        }
    }

    /**
     * Class for filtering FaixaEtaria
     */
    public static class FaixaEtariaFilter extends Filter<FaixaEtaria> {

        public FaixaEtariaFilter() {}

        public FaixaEtariaFilter(FaixaEtariaFilter filter) {
            super(filter);
        }

        @Override
        public FaixaEtariaFilter copy() {
            return new FaixaEtariaFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private UUIDFilter id;

    private StringFilter nome;

    private SexoTurmaFilter sexoTurma;

    private FaixaEtariaFilter faixaEtaria;

    private LocalDateFilter dataInicio;

    private LocalDateFilter dataFim;

    private ZonedDateTimeFilter dataCadastro;

    private UUIDFilter professorId;

    private UUIDFilter alunosId;

    private Boolean distinct;

    public TurmaCriteria() {}

    public TurmaCriteria(TurmaCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nome = other.nome == null ? null : other.nome.copy();
        this.sexoTurma = other.sexoTurma == null ? null : other.sexoTurma.copy();
        this.faixaEtaria = other.faixaEtaria == null ? null : other.faixaEtaria.copy();
        this.dataInicio = other.dataInicio == null ? null : other.dataInicio.copy();
        this.dataFim = other.dataFim == null ? null : other.dataFim.copy();
        this.dataCadastro = other.dataCadastro == null ? null : other.dataCadastro.copy();
        this.professorId = other.professorId == null ? null : other.professorId.copy();
        this.alunosId = other.alunosId == null ? null : other.alunosId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public TurmaCriteria copy() {
        return new TurmaCriteria(this);
    }

    public UUIDFilter getId() {
        return id;
    }

    public UUIDFilter id() {
        if (id == null) {
            id = new UUIDFilter();
        }
        return id;
    }

    public void setId(UUIDFilter id) {
        this.id = id;
    }

    public StringFilter getNome() {
        return nome;
    }

    public StringFilter nome() {
        if (nome == null) {
            nome = new StringFilter();
        }
        return nome;
    }

    public void setNome(StringFilter nome) {
        this.nome = nome;
    }

    public SexoTurmaFilter getSexoTurma() {
        return sexoTurma;
    }

    public SexoTurmaFilter sexoTurma() {
        if (sexoTurma == null) {
            sexoTurma = new SexoTurmaFilter();
        }
        return sexoTurma;
    }

    public void setSexoTurma(SexoTurmaFilter sexoTurma) {
        this.sexoTurma = sexoTurma;
    }

    public FaixaEtariaFilter getFaixaEtaria() {
        return faixaEtaria;
    }

    public FaixaEtariaFilter faixaEtaria() {
        if (faixaEtaria == null) {
            faixaEtaria = new FaixaEtariaFilter();
        }
        return faixaEtaria;
    }

    public void setFaixaEtaria(FaixaEtariaFilter faixaEtaria) {
        this.faixaEtaria = faixaEtaria;
    }

    public LocalDateFilter getDataInicio() {
        return dataInicio;
    }

    public LocalDateFilter dataInicio() {
        if (dataInicio == null) {
            dataInicio = new LocalDateFilter();
        }
        return dataInicio;
    }

    public void setDataInicio(LocalDateFilter dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDateFilter getDataFim() {
        return dataFim;
    }

    public LocalDateFilter dataFim() {
        if (dataFim == null) {
            dataFim = new LocalDateFilter();
        }
        return dataFim;
    }

    public void setDataFim(LocalDateFilter dataFim) {
        this.dataFim = dataFim;
    }

    public ZonedDateTimeFilter getDataCadastro() {
        return dataCadastro;
    }

    public ZonedDateTimeFilter dataCadastro() {
        if (dataCadastro == null) {
            dataCadastro = new ZonedDateTimeFilter();
        }
        return dataCadastro;
    }

    public void setDataCadastro(ZonedDateTimeFilter dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public UUIDFilter getProfessorId() {
        return professorId;
    }

    public UUIDFilter professorId() {
        if (professorId == null) {
            professorId = new UUIDFilter();
        }
        return professorId;
    }

    public void setProfessorId(UUIDFilter professorId) {
        this.professorId = professorId;
    }

    public UUIDFilter getAlunosId() {
        return alunosId;
    }

    public UUIDFilter alunosId() {
        if (alunosId == null) {
            alunosId = new UUIDFilter();
        }
        return alunosId;
    }

    public void setAlunosId(UUIDFilter alunosId) {
        this.alunosId = alunosId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final TurmaCriteria that = (TurmaCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(nome, that.nome) &&
            Objects.equals(sexoTurma, that.sexoTurma) &&
            Objects.equals(faixaEtaria, that.faixaEtaria) &&
            Objects.equals(dataInicio, that.dataInicio) &&
            Objects.equals(dataFim, that.dataFim) &&
            Objects.equals(dataCadastro, that.dataCadastro) &&
            Objects.equals(professorId, that.professorId) &&
            Objects.equals(alunosId, that.alunosId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, sexoTurma, faixaEtaria, dataInicio, dataFim, dataCadastro, professorId, alunosId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TurmaCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (nome != null ? "nome=" + nome + ", " : "") +
            (sexoTurma != null ? "sexoTurma=" + sexoTurma + ", " : "") +
            (faixaEtaria != null ? "faixaEtaria=" + faixaEtaria + ", " : "") +
            (dataInicio != null ? "dataInicio=" + dataInicio + ", " : "") +
            (dataFim != null ? "dataFim=" + dataFim + ", " : "") +
            (dataCadastro != null ? "dataCadastro=" + dataCadastro + ", " : "") +
            (professorId != null ? "professorId=" + professorId + ", " : "") +
            (alunosId != null ? "alunosId=" + alunosId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}

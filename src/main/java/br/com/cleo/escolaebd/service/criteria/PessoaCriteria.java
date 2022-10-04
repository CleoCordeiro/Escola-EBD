package br.com.cleo.escolaebd.service.criteria;

import br.com.cleo.escolaebd.domain.enumeration.Sexo;
import br.com.cleo.escolaebd.domain.enumeration.TipoPessoa;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link br.com.cleo.escolaebd.domain.Pessoa} entity. This class is used
 * in {@link br.com.cleo.escolaebd.web.rest.PessoaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /pessoas?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PessoaCriteria implements Serializable, Criteria {

    /**
     * Class for filtering Sexo
     */
    public static class SexoFilter extends Filter<Sexo> {

        public SexoFilter() {}

        public SexoFilter(SexoFilter filter) {
            super(filter);
        }

        @Override
        public SexoFilter copy() {
            return new SexoFilter(this);
        }
    }

    /**
     * Class for filtering TipoPessoa
     */
    public static class TipoPessoaFilter extends Filter<TipoPessoa> {

        public TipoPessoaFilter() {}

        public TipoPessoaFilter(TipoPessoaFilter filter) {
            super(filter);
        }

        @Override
        public TipoPessoaFilter copy() {
            return new TipoPessoaFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private UUIDFilter id;

    private StringFilter nome;

    private LocalDateFilter dataNascimento;

    private StringFilter cpf;

    private SexoFilter sexo;

    private TipoPessoaFilter tipoPessoa;

    private ZonedDateTimeFilter dataCadastro;

    private ZonedDateTimeFilter dataAtualizacao;

    private UUIDFilter telefonesId;

    private UUIDFilter enderecosId;

    private UUIDFilter turmaId;

    private UUIDFilter responsaveisId;

    private Boolean distinct;

    public PessoaCriteria() {}

    public PessoaCriteria(PessoaCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nome = other.nome == null ? null : other.nome.copy();
        this.dataNascimento = other.dataNascimento == null ? null : other.dataNascimento.copy();
        this.cpf = other.cpf == null ? null : other.cpf.copy();
        this.sexo = other.sexo == null ? null : other.sexo.copy();
        this.tipoPessoa = other.tipoPessoa == null ? null : other.tipoPessoa.copy();
        this.dataCadastro = other.dataCadastro == null ? null : other.dataCadastro.copy();
        this.dataAtualizacao = other.dataAtualizacao == null ? null : other.dataAtualizacao.copy();
        this.telefonesId = other.telefonesId == null ? null : other.telefonesId.copy();
        this.enderecosId = other.enderecosId == null ? null : other.enderecosId.copy();
        this.turmaId = other.turmaId == null ? null : other.turmaId.copy();
        this.responsaveisId = other.responsaveisId == null ? null : other.responsaveisId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public PessoaCriteria copy() {
        return new PessoaCriteria(this);
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

    public LocalDateFilter getDataNascimento() {
        return dataNascimento;
    }

    public LocalDateFilter dataNascimento() {
        if (dataNascimento == null) {
            dataNascimento = new LocalDateFilter();
        }
        return dataNascimento;
    }

    public void setDataNascimento(LocalDateFilter dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public StringFilter getCpf() {
        return cpf;
    }

    public StringFilter cpf() {
        if (cpf == null) {
            cpf = new StringFilter();
        }
        return cpf;
    }

    public void setCpf(StringFilter cpf) {
        this.cpf = cpf;
    }

    public SexoFilter getSexo() {
        return sexo;
    }

    public SexoFilter sexo() {
        if (sexo == null) {
            sexo = new SexoFilter();
        }
        return sexo;
    }

    public void setSexo(SexoFilter sexo) {
        this.sexo = sexo;
    }

    public TipoPessoaFilter getTipoPessoa() {
        return tipoPessoa;
    }

    public TipoPessoaFilter tipoPessoa() {
        if (tipoPessoa == null) {
            tipoPessoa = new TipoPessoaFilter();
        }
        return tipoPessoa;
    }

    public void setTipoPessoa(TipoPessoaFilter tipoPessoa) {
        this.tipoPessoa = tipoPessoa;
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

    public ZonedDateTimeFilter getDataAtualizacao() {
        return dataAtualizacao;
    }

    public ZonedDateTimeFilter dataAtualizacao() {
        if (dataAtualizacao == null) {
            dataAtualizacao = new ZonedDateTimeFilter();
        }
        return dataAtualizacao;
    }

    public void setDataAtualizacao(ZonedDateTimeFilter dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    public UUIDFilter getTelefonesId() {
        return telefonesId;
    }

    public UUIDFilter telefonesId() {
        if (telefonesId == null) {
            telefonesId = new UUIDFilter();
        }
        return telefonesId;
    }

    public void setTelefonesId(UUIDFilter telefonesId) {
        this.telefonesId = telefonesId;
    }

    public UUIDFilter getEnderecosId() {
        return enderecosId;
    }

    public UUIDFilter enderecosId() {
        if (enderecosId == null) {
            enderecosId = new UUIDFilter();
        }
        return enderecosId;
    }

    public void setEnderecosId(UUIDFilter enderecosId) {
        this.enderecosId = enderecosId;
    }

    public UUIDFilter getTurmaId() {
        return turmaId;
    }

    public UUIDFilter turmaId() {
        if (turmaId == null) {
            turmaId = new UUIDFilter();
        }
        return turmaId;
    }

    public void setTurmaId(UUIDFilter turmaId) {
        this.turmaId = turmaId;
    }

    public UUIDFilter getResponsaveisId() {
        return responsaveisId;
    }

    public UUIDFilter responsaveisId() {
        if (responsaveisId == null) {
            responsaveisId = new UUIDFilter();
        }
        return responsaveisId;
    }

    public void setResponsaveisId(UUIDFilter responsaveisId) {
        this.responsaveisId = responsaveisId;
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
        final PessoaCriteria that = (PessoaCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(nome, that.nome) &&
            Objects.equals(dataNascimento, that.dataNascimento) &&
            Objects.equals(cpf, that.cpf) &&
            Objects.equals(sexo, that.sexo) &&
            Objects.equals(tipoPessoa, that.tipoPessoa) &&
            Objects.equals(dataCadastro, that.dataCadastro) &&
            Objects.equals(dataAtualizacao, that.dataAtualizacao) &&
            Objects.equals(telefonesId, that.telefonesId) &&
            Objects.equals(enderecosId, that.enderecosId) &&
            Objects.equals(turmaId, that.turmaId) &&
            Objects.equals(responsaveisId, that.responsaveisId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            nome,
            dataNascimento,
            cpf,
            sexo,
            tipoPessoa,
            dataCadastro,
            dataAtualizacao,
            telefonesId,
            enderecosId,
            turmaId,
            responsaveisId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PessoaCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (nome != null ? "nome=" + nome + ", " : "") +
            (dataNascimento != null ? "dataNascimento=" + dataNascimento + ", " : "") +
            (cpf != null ? "cpf=" + cpf + ", " : "") +
            (sexo != null ? "sexo=" + sexo + ", " : "") +
            (tipoPessoa != null ? "tipoPessoa=" + tipoPessoa + ", " : "") +
            (dataCadastro != null ? "dataCadastro=" + dataCadastro + ", " : "") +
            (dataAtualizacao != null ? "dataAtualizacao=" + dataAtualizacao + ", " : "") +
            (telefonesId != null ? "telefonesId=" + telefonesId + ", " : "") +
            (enderecosId != null ? "enderecosId=" + enderecosId + ", " : "") +
            (turmaId != null ? "turmaId=" + turmaId + ", " : "") +
            (responsaveisId != null ? "responsaveisId=" + responsaveisId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}

package br.com.cleo.escolaebd.service.criteria;

import br.com.cleo.escolaebd.domain.enumeration.Sexo;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link br.com.cleo.escolaebd.domain.Responsavel} entity. This class is used
 * in {@link br.com.cleo.escolaebd.web.rest.ResponsavelResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /responsavels?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ResponsavelCriteria implements Serializable, Criteria {

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

    private static final long serialVersionUID = 1L;

    private UUIDFilter id;

    private StringFilter nome;

    private StringFilter cpf;

    private SexoFilter sexo;

    private StringFilter parentesco;

    private ZonedDateTimeFilter dataCadastro;

    private UUIDFilter telefonesId;

    private UUIDFilter enderecosId;

    private UUIDFilter alunosId;

    private Boolean distinct;

    public ResponsavelCriteria() {}

    public ResponsavelCriteria(ResponsavelCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nome = other.nome == null ? null : other.nome.copy();
        this.cpf = other.cpf == null ? null : other.cpf.copy();
        this.sexo = other.sexo == null ? null : other.sexo.copy();
        this.parentesco = other.parentesco == null ? null : other.parentesco.copy();
        this.dataCadastro = other.dataCadastro == null ? null : other.dataCadastro.copy();
        this.telefonesId = other.telefonesId == null ? null : other.telefonesId.copy();
        this.enderecosId = other.enderecosId == null ? null : other.enderecosId.copy();
        this.alunosId = other.alunosId == null ? null : other.alunosId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ResponsavelCriteria copy() {
        return new ResponsavelCriteria(this);
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

    public StringFilter getParentesco() {
        return parentesco;
    }

    public StringFilter parentesco() {
        if (parentesco == null) {
            parentesco = new StringFilter();
        }
        return parentesco;
    }

    public void setParentesco(StringFilter parentesco) {
        this.parentesco = parentesco;
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
        final ResponsavelCriteria that = (ResponsavelCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(nome, that.nome) &&
            Objects.equals(cpf, that.cpf) &&
            Objects.equals(sexo, that.sexo) &&
            Objects.equals(parentesco, that.parentesco) &&
            Objects.equals(dataCadastro, that.dataCadastro) &&
            Objects.equals(telefonesId, that.telefonesId) &&
            Objects.equals(enderecosId, that.enderecosId) &&
            Objects.equals(alunosId, that.alunosId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, cpf, sexo, parentesco, dataCadastro, telefonesId, enderecosId, alunosId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ResponsavelCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (nome != null ? "nome=" + nome + ", " : "") +
            (cpf != null ? "cpf=" + cpf + ", " : "") +
            (sexo != null ? "sexo=" + sexo + ", " : "") +
            (parentesco != null ? "parentesco=" + parentesco + ", " : "") +
            (dataCadastro != null ? "dataCadastro=" + dataCadastro + ", " : "") +
            (telefonesId != null ? "telefonesId=" + telefonesId + ", " : "") +
            (enderecosId != null ? "enderecosId=" + enderecosId + ", " : "") +
            (alunosId != null ? "alunosId=" + alunosId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}

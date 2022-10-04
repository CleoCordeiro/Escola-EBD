package br.com.cleo.escolaebd.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link br.com.cleo.escolaebd.domain.Endereco} entity. This class is used
 * in {@link br.com.cleo.escolaebd.web.rest.EnderecoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /enderecos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EnderecoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private UUIDFilter id;

    private StringFilter logradouro;

    private StringFilter numero;

    private StringFilter complemento;

    private StringFilter bairro;

    private StringFilter cidade;

    private StringFilter estado;

    private StringFilter cep;

    private UUIDFilter responsaveisId;

    private UUIDFilter pessoasId;

    private Boolean distinct;

    public EnderecoCriteria() {}

    public EnderecoCriteria(EnderecoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.logradouro = other.logradouro == null ? null : other.logradouro.copy();
        this.numero = other.numero == null ? null : other.numero.copy();
        this.complemento = other.complemento == null ? null : other.complemento.copy();
        this.bairro = other.bairro == null ? null : other.bairro.copy();
        this.cidade = other.cidade == null ? null : other.cidade.copy();
        this.estado = other.estado == null ? null : other.estado.copy();
        this.cep = other.cep == null ? null : other.cep.copy();
        this.responsaveisId = other.responsaveisId == null ? null : other.responsaveisId.copy();
        this.pessoasId = other.pessoasId == null ? null : other.pessoasId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public EnderecoCriteria copy() {
        return new EnderecoCriteria(this);
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

    public StringFilter getLogradouro() {
        return logradouro;
    }

    public StringFilter logradouro() {
        if (logradouro == null) {
            logradouro = new StringFilter();
        }
        return logradouro;
    }

    public void setLogradouro(StringFilter logradouro) {
        this.logradouro = logradouro;
    }

    public StringFilter getNumero() {
        return numero;
    }

    public StringFilter numero() {
        if (numero == null) {
            numero = new StringFilter();
        }
        return numero;
    }

    public void setNumero(StringFilter numero) {
        this.numero = numero;
    }

    public StringFilter getComplemento() {
        return complemento;
    }

    public StringFilter complemento() {
        if (complemento == null) {
            complemento = new StringFilter();
        }
        return complemento;
    }

    public void setComplemento(StringFilter complemento) {
        this.complemento = complemento;
    }

    public StringFilter getBairro() {
        return bairro;
    }

    public StringFilter bairro() {
        if (bairro == null) {
            bairro = new StringFilter();
        }
        return bairro;
    }

    public void setBairro(StringFilter bairro) {
        this.bairro = bairro;
    }

    public StringFilter getCidade() {
        return cidade;
    }

    public StringFilter cidade() {
        if (cidade == null) {
            cidade = new StringFilter();
        }
        return cidade;
    }

    public void setCidade(StringFilter cidade) {
        this.cidade = cidade;
    }

    public StringFilter getEstado() {
        return estado;
    }

    public StringFilter estado() {
        if (estado == null) {
            estado = new StringFilter();
        }
        return estado;
    }

    public void setEstado(StringFilter estado) {
        this.estado = estado;
    }

    public StringFilter getCep() {
        return cep;
    }

    public StringFilter cep() {
        if (cep == null) {
            cep = new StringFilter();
        }
        return cep;
    }

    public void setCep(StringFilter cep) {
        this.cep = cep;
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

    public UUIDFilter getPessoasId() {
        return pessoasId;
    }

    public UUIDFilter pessoasId() {
        if (pessoasId == null) {
            pessoasId = new UUIDFilter();
        }
        return pessoasId;
    }

    public void setPessoasId(UUIDFilter pessoasId) {
        this.pessoasId = pessoasId;
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
        final EnderecoCriteria that = (EnderecoCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(logradouro, that.logradouro) &&
            Objects.equals(numero, that.numero) &&
            Objects.equals(complemento, that.complemento) &&
            Objects.equals(bairro, that.bairro) &&
            Objects.equals(cidade, that.cidade) &&
            Objects.equals(estado, that.estado) &&
            Objects.equals(cep, that.cep) &&
            Objects.equals(responsaveisId, that.responsaveisId) &&
            Objects.equals(pessoasId, that.pessoasId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, logradouro, numero, complemento, bairro, cidade, estado, cep, responsaveisId, pessoasId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EnderecoCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (logradouro != null ? "logradouro=" + logradouro + ", " : "") +
            (numero != null ? "numero=" + numero + ", " : "") +
            (complemento != null ? "complemento=" + complemento + ", " : "") +
            (bairro != null ? "bairro=" + bairro + ", " : "") +
            (cidade != null ? "cidade=" + cidade + ", " : "") +
            (estado != null ? "estado=" + estado + ", " : "") +
            (cep != null ? "cep=" + cep + ", " : "") +
            (responsaveisId != null ? "responsaveisId=" + responsaveisId + ", " : "") +
            (pessoasId != null ? "pessoasId=" + pessoasId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}

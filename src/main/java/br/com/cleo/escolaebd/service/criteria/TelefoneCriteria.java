package br.com.cleo.escolaebd.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link br.com.cleo.escolaebd.domain.Telefone} entity. This class is used
 * in {@link br.com.cleo.escolaebd.web.rest.TelefoneResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /telefones?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TelefoneCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private UUIDFilter id;

    private StringFilter numero;

    private BooleanFilter whatsapp;

    private UUIDFilter pessoaId;

    private UUIDFilter responsavelId;

    private Boolean distinct;

    public TelefoneCriteria() {}

    public TelefoneCriteria(TelefoneCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.numero = other.numero == null ? null : other.numero.copy();
        this.whatsapp = other.whatsapp == null ? null : other.whatsapp.copy();
        this.pessoaId = other.pessoaId == null ? null : other.pessoaId.copy();
        this.responsavelId = other.responsavelId == null ? null : other.responsavelId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public TelefoneCriteria copy() {
        return new TelefoneCriteria(this);
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

    public BooleanFilter getWhatsapp() {
        return whatsapp;
    }

    public BooleanFilter whatsapp() {
        if (whatsapp == null) {
            whatsapp = new BooleanFilter();
        }
        return whatsapp;
    }

    public void setWhatsapp(BooleanFilter whatsapp) {
        this.whatsapp = whatsapp;
    }

    public UUIDFilter getPessoaId() {
        return pessoaId;
    }

    public UUIDFilter pessoaId() {
        if (pessoaId == null) {
            pessoaId = new UUIDFilter();
        }
        return pessoaId;
    }

    public void setPessoaId(UUIDFilter pessoaId) {
        this.pessoaId = pessoaId;
    }

    public UUIDFilter getResponsavelId() {
        return responsavelId;
    }

    public UUIDFilter responsavelId() {
        if (responsavelId == null) {
            responsavelId = new UUIDFilter();
        }
        return responsavelId;
    }

    public void setResponsavelId(UUIDFilter responsavelId) {
        this.responsavelId = responsavelId;
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
        final TelefoneCriteria that = (TelefoneCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(numero, that.numero) &&
            Objects.equals(whatsapp, that.whatsapp) &&
            Objects.equals(pessoaId, that.pessoaId) &&
            Objects.equals(responsavelId, that.responsavelId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, numero, whatsapp, pessoaId, responsavelId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TelefoneCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (numero != null ? "numero=" + numero + ", " : "") +
            (whatsapp != null ? "whatsapp=" + whatsapp + ", " : "") +
            (pessoaId != null ? "pessoaId=" + pessoaId + ", " : "") +
            (responsavelId != null ? "responsavelId=" + responsavelId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}

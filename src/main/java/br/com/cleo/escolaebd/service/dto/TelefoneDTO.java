package br.com.cleo.escolaebd.service.dto;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link br.com.cleo.escolaebd.domain.Telefone} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TelefoneDTO implements Serializable {

    private UUID id;

    @NotNull
    private String numero;

    @NotNull
    private Boolean whatsapp;

    private PessoaDTO pessoa;

    private ResponsavelDTO responsavel;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Boolean getWhatsapp() {
        return whatsapp;
    }

    public void setWhatsapp(Boolean whatsapp) {
        this.whatsapp = whatsapp;
    }

    public PessoaDTO getPessoa() {
        return pessoa;
    }

    public void setPessoa(PessoaDTO pessoa) {
        this.pessoa = pessoa;
    }

    public ResponsavelDTO getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(ResponsavelDTO responsavel) {
        this.responsavel = responsavel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TelefoneDTO)) {
            return false;
        }

        TelefoneDTO telefoneDTO = (TelefoneDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, telefoneDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TelefoneDTO{" +
            "id='" + getId() + "'" +
            ", numero='" + getNumero() + "'" +
            ", whatsapp='" + getWhatsapp() + "'" +
            ", pessoa=" + getPessoa() +
            ", responsavel=" + getResponsavel() +
            "}";
    }
}

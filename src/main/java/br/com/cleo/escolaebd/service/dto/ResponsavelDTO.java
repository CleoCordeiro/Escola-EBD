package br.com.cleo.escolaebd.service.dto;

import br.com.cleo.escolaebd.domain.enumeration.Sexo;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link br.com.cleo.escolaebd.domain.Responsavel} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ResponsavelDTO implements Serializable {

    private UUID id;

    @NotNull
    private String nome;

    @NotNull
    private String cpf;

    @NotNull
    private Sexo sexo;

    @NotNull
    private String parentesco;

    private ZonedDateTime dataCadastro;

    private Set<EnderecoDTO> enderecos = new HashSet<>();

    private Set<PessoaDTO> alunos = new HashSet<>();

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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public String getParentesco() {
        return parentesco;
    }

    public void setParentesco(String parentesco) {
        this.parentesco = parentesco;
    }

    public ZonedDateTime getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(ZonedDateTime dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public Set<EnderecoDTO> getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(Set<EnderecoDTO> enderecos) {
        this.enderecos = enderecos;
    }

    public Set<PessoaDTO> getAlunos() {
        return alunos;
    }

    public void setAlunos(Set<PessoaDTO> alunos) {
        this.alunos = alunos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ResponsavelDTO)) {
            return false;
        }

        ResponsavelDTO responsavelDTO = (ResponsavelDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, responsavelDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ResponsavelDTO{" +
            "id='" + getId() + "'" +
            ", nome='" + getNome() + "'" +
            ", cpf='" + getCpf() + "'" +
            ", sexo='" + getSexo() + "'" +
            ", parentesco='" + getParentesco() + "'" +
            ", dataCadastro='" + getDataCadastro() + "'" +
            ", enderecos=" + getEnderecos() +
            ", alunos=" + getAlunos() +
            "}";
    }
}

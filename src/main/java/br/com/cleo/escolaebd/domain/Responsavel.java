package br.com.cleo.escolaebd.domain;

import br.com.cleo.escolaebd.domain.enumeration.Sexo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Responsavel.
 */
@Entity
@Table(name = "responsavel")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Responsavel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @NotNull
    @Column(name = "cpf", nullable = false)
    private String cpf;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "sexo", nullable = false)
    private Sexo sexo;

    @NotNull
    @Column(name = "parentesco", nullable = false)
    private String parentesco;

    @Column(name = "data_cadastro")
    private ZonedDateTime dataCadastro;

    @OneToMany(mappedBy = "responsavel")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "pessoa", "responsavel" }, allowSetters = true)
    private Set<Telefone> telefones = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "rel_responsavel__enderecos",
        joinColumns = @JoinColumn(name = "responsavel_id"),
        inverseJoinColumns = @JoinColumn(name = "enderecos_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "responsaveis", "pessoas" }, allowSetters = true)
    private Set<Endereco> enderecos = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "rel_responsavel__alunos",
        joinColumns = @JoinColumn(name = "responsavel_id"),
        inverseJoinColumns = @JoinColumn(name = "alunos_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "telefones", "enderecos", "turma", "responsaveis" }, allowSetters = true)
    private Set<Pessoa> alunos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public Responsavel id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public Responsavel nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return this.cpf;
    }

    public Responsavel cpf(String cpf) {
        this.setCpf(cpf);
        return this;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Sexo getSexo() {
        return this.sexo;
    }

    public Responsavel sexo(Sexo sexo) {
        this.setSexo(sexo);
        return this;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public String getParentesco() {
        return this.parentesco;
    }

    public Responsavel parentesco(String parentesco) {
        this.setParentesco(parentesco);
        return this;
    }

    public void setParentesco(String parentesco) {
        this.parentesco = parentesco;
    }

    public ZonedDateTime getDataCadastro() {
        return this.dataCadastro;
    }

    public Responsavel dataCadastro(ZonedDateTime dataCadastro) {
        this.setDataCadastro(dataCadastro);
        return this;
    }

    public void setDataCadastro(ZonedDateTime dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public Set<Telefone> getTelefones() {
        return this.telefones;
    }

    public void setTelefones(Set<Telefone> telefones) {
        if (this.telefones != null) {
            this.telefones.forEach(i -> i.setResponsavel(null));
        }
        if (telefones != null) {
            telefones.forEach(i -> i.setResponsavel(this));
        }
        this.telefones = telefones;
    }

    public Responsavel telefones(Set<Telefone> telefones) {
        this.setTelefones(telefones);
        return this;
    }

    public Responsavel addTelefones(Telefone telefone) {
        this.telefones.add(telefone);
        telefone.setResponsavel(this);
        return this;
    }

    public Responsavel removeTelefones(Telefone telefone) {
        this.telefones.remove(telefone);
        telefone.setResponsavel(null);
        return this;
    }

    public Set<Endereco> getEnderecos() {
        return this.enderecos;
    }

    public void setEnderecos(Set<Endereco> enderecos) {
        this.enderecos = enderecos;
    }

    public Responsavel enderecos(Set<Endereco> enderecos) {
        this.setEnderecos(enderecos);
        return this;
    }

    public Responsavel addEnderecos(Endereco endereco) {
        this.enderecos.add(endereco);
        endereco.getResponsaveis().add(this);
        return this;
    }

    public Responsavel removeEnderecos(Endereco endereco) {
        this.enderecos.remove(endereco);
        endereco.getResponsaveis().remove(this);
        return this;
    }

    public Set<Pessoa> getAlunos() {
        return this.alunos;
    }

    public void setAlunos(Set<Pessoa> pessoas) {
        this.alunos = pessoas;
    }

    public Responsavel alunos(Set<Pessoa> pessoas) {
        this.setAlunos(pessoas);
        return this;
    }

    public Responsavel addAlunos(Pessoa pessoa) {
        this.alunos.add(pessoa);
        pessoa.getResponsaveis().add(this);
        return this;
    }

    public Responsavel removeAlunos(Pessoa pessoa) {
        this.alunos.remove(pessoa);
        pessoa.getResponsaveis().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Responsavel)) {
            return false;
        }
        return id != null && id.equals(((Responsavel) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Responsavel{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", cpf='" + getCpf() + "'" +
            ", sexo='" + getSexo() + "'" +
            ", parentesco='" + getParentesco() + "'" +
            ", dataCadastro='" + getDataCadastro() + "'" +
            "}";
    }
}

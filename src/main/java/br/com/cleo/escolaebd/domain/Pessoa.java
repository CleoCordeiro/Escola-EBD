package br.com.cleo.escolaebd.domain;

import br.com.cleo.escolaebd.domain.enumeration.Sexo;
import br.com.cleo.escolaebd.domain.enumeration.TipoPessoa;
import br.com.cleo.escolaebd.web.rest.errors.BadRequestAlertException;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Pessoa.
 */
@Entity
@Table(name = "pessoa")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Pessoa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @NotNull
    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;

    @NotNull
    @Column(name = "cpf", nullable = false)
    private String cpf;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "sexo", nullable = false)
    private Sexo sexo;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_pessoa", nullable = false)
    private TipoPessoa tipoPessoa;

    @Column(name = "data_cadastro")
    private ZonedDateTime dataCadastro;

    @Column(name = "data_atualizacao")
    private ZonedDateTime dataAtualizacao;

    @OneToMany(mappedBy = "pessoa")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "pessoa", "responsavel" }, allowSetters = true)
    private Set<Telefone> telefones = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "rel_pessoa__enderecos",
        joinColumns = @JoinColumn(name = "pessoa_id"),
        inverseJoinColumns = @JoinColumn(name = "enderecos_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "responsaveis", "pessoas" }, allowSetters = true)
    private Set<Endereco> enderecos = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "professor", "alunos" }, allowSetters = true)
    private Turma turma;

    @ManyToMany(mappedBy = "alunos")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "telefones", "enderecos", "alunos" }, allowSetters = true)
    private Set<Responsavel> responsaveis = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public Pessoa id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public Pessoa nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataNascimento() {
        return this.dataNascimento;
    }

    public Pessoa dataNascimento(LocalDate dataNascimento) {
        this.setDataNascimento(dataNascimento);
        return this;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getCpf() {
        return this.cpf;
    }

    public Pessoa cpf(String cpf) {
        this.setCpf(cpf);
        return this;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Sexo getSexo() {
        return this.sexo;
    }

    public Pessoa sexo(Sexo sexo) {
        this.setSexo(sexo);
        return this;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public TipoPessoa getTipoPessoa() {
        return this.tipoPessoa;
    }

    public Pessoa tipoPessoa(TipoPessoa tipoPessoa) {
        this.setTipoPessoa(tipoPessoa);
        return this;
    }

    public void setTipoPessoa(TipoPessoa tipoPessoa) {
        this.tipoPessoa = tipoPessoa;
    }

    public ZonedDateTime getDataCadastro() {
        return this.dataCadastro;
    }

    public Pessoa dataCadastro(ZonedDateTime dataCadastro) {
        this.setDataCadastro(dataCadastro);
        return this;
    }

    public void setDataCadastro(ZonedDateTime dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public ZonedDateTime getDataAtualizacao() {
        return this.dataAtualizacao;
    }

    public Pessoa dataAtualizacao(ZonedDateTime dataAtualizacao) {
        this.setDataAtualizacao(dataAtualizacao);
        return this;
    }

    public void setDataAtualizacao(ZonedDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    public Set<Telefone> getTelefones() {
        return this.telefones;
    }

    public void setTelefones(Set<Telefone> telefones) {
        if (this.telefones != null) {
            this.telefones.forEach(i -> i.setPessoa(null));
        }
        if (telefones != null) {
            telefones.forEach(i -> i.setPessoa(this));
        }
        this.telefones = telefones;
    }

    public Pessoa telefones(Set<Telefone> telefones) {
        this.setTelefones(telefones);
        return this;
    }

    public Pessoa addTelefones(Telefone telefone) {
        this.telefones.add(telefone);
        telefone.setPessoa(this);
        return this;
    }

    public Pessoa removeTelefones(Telefone telefone) {
        this.telefones.remove(telefone);
        telefone.setPessoa(null);
        return this;
    }

    public Set<Endereco> getEnderecos() {
        return this.enderecos;
    }

    public void setEnderecos(Set<Endereco> enderecos) {
        this.enderecos = enderecos;
    }

    public Pessoa enderecos(Set<Endereco> enderecos) {
        this.setEnderecos(enderecos);
        return this;
    }

    public Pessoa addEnderecos(Endereco endereco) {
        this.enderecos.add(endereco);
        endereco.getPessoas().add(this);
        return this;
    }

    public Pessoa removeEnderecos(Endereco endereco) {
        this.enderecos.remove(endereco);
        endereco.getPessoas().remove(this);
        return this;
    }

    public Turma getTurma() {
        return this.turma;
    }

    // Como um aluno
    // Quero ser matriculado em uma turma
    // Para que eu possa participar das aulas
    // Pessoas do tipo aluno só podem ser matriculadas em turmas que estejam dentro
    // da sua faixa etária
    public void setTurma(Turma turma) {
        if (turma != null) {
            if (TipoPessoa.ALUNO.equals(this.getTipoPessoa())) {
                int Age = Period.between(this.getDataNascimento(), LocalDate.now()).getYears();
                if (Age >= turma.getFaixaEtaria().getIdadeMinima() && Age <= turma.getFaixaEtaria().getIdadeMaxima()) {
                    this.turma = turma;
                } else {
                    throw new RuntimeException("A idade do aluno não está dentro da faixa etária da turma");
                }
            }
        } else {
            this.turma = turma;
        }
    }

    public Pessoa turma(Turma turma) {
        this.setTurma(turma);
        return this;
    }

    public Set<Responsavel> getResponsaveis() {
        return this.responsaveis;
    }

    public void setResponsaveis(Set<Responsavel> responsavels) {
        if (this.responsaveis != null) {
            this.responsaveis.forEach(i -> i.removeAlunos(this));
        }
        if (responsavels != null) {
            responsavels.forEach(i -> i.addAlunos(this));
        }
        this.responsaveis = responsavels;
    }

    public Pessoa responsaveis(Set<Responsavel> responsavels) {
        this.setResponsaveis(responsavels);
        return this;
    }

    public Pessoa addResponsaveis(Responsavel responsavel) {
        this.responsaveis.add(responsavel);
        responsavel.getAlunos().add(this);
        return this;
    }

    public Pessoa removeResponsaveis(Responsavel responsavel) {
        this.responsaveis.remove(responsavel);
        responsavel.getAlunos().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and
    // setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Pessoa)) {
            return false;
        }
        return id != null && id.equals(((Pessoa) o).id);
    }

    @Override
    public int hashCode() {
        // see
        // https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Pessoa{" +
                "id=" + getId() +
                ", nome='" + getNome() + "'" +
                ", dataNascimento='" + getDataNascimento() + "'" +
                ", cpf='" + getCpf() + "'" +
                ", sexo='" + getSexo() + "'" +
                ", tipoPessoa='" + getTipoPessoa() + "'" +
                ", dataCadastro='" + getDataCadastro() + "'" +
                ", dataAtualizacao='" + getDataAtualizacao() + "'" +
                "}";
    }
}

package br.com.cleo.escolaebd.domain;

import br.com.cleo.escolaebd.domain.enumeration.FaixaEtaria;
import br.com.cleo.escolaebd.domain.enumeration.SexoTurma;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Turma.
 */
@Entity
@Table(name = "turma")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Turma implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "sexo_turma", nullable = false)
    private SexoTurma sexoTurma;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "faixa_etaria", nullable = false)
    private FaixaEtaria faixaEtaria;

    @NotNull
    @Column(name = "data_inicio", nullable = false)
    private LocalDate dataInicio;

    @Column(name = "data_fim")
    private LocalDate dataFim;

    @Column(name = "data_cadastro")
    private ZonedDateTime dataCadastro;

    @JsonIgnoreProperties(value = { "telefones", "enderecos", "turma", "responsaveis" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Pessoa professor;

    @OneToMany(mappedBy = "turma")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "telefones", "enderecos", "turma", "responsaveis" }, allowSetters = true)
    private Set<Pessoa> alunos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public Turma id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public Turma nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public SexoTurma getSexoTurma() {
        return this.sexoTurma;
    }

    public Turma sexoTurma(SexoTurma sexoTurma) {
        this.setSexoTurma(sexoTurma);
        return this;
    }

    public void setSexoTurma(SexoTurma sexoTurma) {
        this.sexoTurma = sexoTurma;
    }

    public FaixaEtaria getFaixaEtaria() {
        return this.faixaEtaria;
    }

    public Turma faixaEtaria(FaixaEtaria faixaEtaria) {
        this.setFaixaEtaria(faixaEtaria);
        return this;
    }

    public void setFaixaEtaria(FaixaEtaria faixaEtaria) {
        this.faixaEtaria = faixaEtaria;
    }

    public LocalDate getDataInicio() {
        return this.dataInicio;
    }

    public Turma dataInicio(LocalDate dataInicio) {
        this.setDataInicio(dataInicio);
        return this;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFim() {
        return this.dataFim;
    }

    public Turma dataFim(LocalDate dataFim) {
        this.setDataFim(dataFim);
        return this;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    public ZonedDateTime getDataCadastro() {
        return this.dataCadastro;
    }

    public Turma dataCadastro(ZonedDateTime dataCadastro) {
        this.setDataCadastro(dataCadastro);
        return this;
    }

    public void setDataCadastro(ZonedDateTime dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public Pessoa getProfessor() {
        return this.professor;
    }

    public void setProfessor(Pessoa pessoa) {
        this.professor = pessoa;
    }

    public Turma professor(Pessoa pessoa) {
        this.setProfessor(pessoa);
        return this;
    }

    public Set<Pessoa> getAlunos() {
        return this.alunos;
    }

    public void setAlunos(Set<Pessoa> pessoas) {
        if (this.alunos != null) {
            this.alunos.forEach(i -> i.setTurma(null));
        }
        if (pessoas != null) {
            pessoas.forEach(i -> i.setTurma(this));
        }
        this.alunos = pessoas;
    }

    public Turma alunos(Set<Pessoa> pessoas) {
        this.setAlunos(pessoas);
        return this;
    }

    public Turma addAlunos(Pessoa pessoa) {
        this.alunos.add(pessoa);
        pessoa.setTurma(this);
        return this;
    }

    public Turma removeAlunos(Pessoa pessoa) {
        this.alunos.remove(pessoa);
        pessoa.setTurma(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Turma)) {
            return false;
        }
        return id != null && id.equals(((Turma) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Turma{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", sexoTurma='" + getSexoTurma() + "'" +
            ", faixaEtaria='" + getFaixaEtaria() + "'" +
            ", dataInicio='" + getDataInicio() + "'" +
            ", dataFim='" + getDataFim() + "'" +
            ", dataCadastro='" + getDataCadastro() + "'" +
            "}";
    }
}

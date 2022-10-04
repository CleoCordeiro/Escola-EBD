package br.com.cleo.escolaebd.web.rest;

import static br.com.cleo.escolaebd.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.cleo.escolaebd.IntegrationTest;
import br.com.cleo.escolaebd.domain.Endereco;
import br.com.cleo.escolaebd.domain.Pessoa;
import br.com.cleo.escolaebd.domain.Responsavel;
import br.com.cleo.escolaebd.domain.Telefone;
import br.com.cleo.escolaebd.domain.Turma;
import br.com.cleo.escolaebd.domain.enumeration.Sexo;
import br.com.cleo.escolaebd.domain.enumeration.TipoPessoa;
import br.com.cleo.escolaebd.repository.PessoaRepository;
import br.com.cleo.escolaebd.service.PessoaService;
import br.com.cleo.escolaebd.service.criteria.PessoaCriteria;
import br.com.cleo.escolaebd.service.dto.PessoaDTO;
import br.com.cleo.escolaebd.service.mapper.PessoaMapper;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PessoaResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class PessoaResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATA_NASCIMENTO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_NASCIMENTO = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATA_NASCIMENTO = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_CPF = "AAAAAAAAAA";
    private static final String UPDATED_CPF = "BBBBBBBBBB";

    private static final Sexo DEFAULT_SEXO = Sexo.MASCULINO;
    private static final Sexo UPDATED_SEXO = Sexo.FEMININO;

    private static final TipoPessoa DEFAULT_TIPO_PESSOA = TipoPessoa.ALUNO;
    private static final TipoPessoa UPDATED_TIPO_PESSOA = TipoPessoa.PROFESSOR;

    private static final ZonedDateTime DEFAULT_DATA_CADASTRO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATA_CADASTRO = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_DATA_CADASTRO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final ZonedDateTime DEFAULT_DATA_ATUALIZACAO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATA_ATUALIZACAO = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_DATA_ATUALIZACAO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final String ENTITY_API_URL = "/api/pessoas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private PessoaRepository pessoaRepository;

    @Mock
    private PessoaRepository pessoaRepositoryMock;

    @Autowired
    private PessoaMapper pessoaMapper;

    @Mock
    private PessoaService pessoaServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPessoaMockMvc;

    private Pessoa pessoa;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pessoa createEntity(EntityManager em) {
        Pessoa pessoa = new Pessoa()
            .nome(DEFAULT_NOME)
            .dataNascimento(DEFAULT_DATA_NASCIMENTO)
            .cpf(DEFAULT_CPF)
            .sexo(DEFAULT_SEXO)
            .tipoPessoa(DEFAULT_TIPO_PESSOA)
            .dataCadastro(DEFAULT_DATA_CADASTRO)
            .dataAtualizacao(DEFAULT_DATA_ATUALIZACAO);
        return pessoa;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pessoa createUpdatedEntity(EntityManager em) {
        Pessoa pessoa = new Pessoa()
            .nome(UPDATED_NOME)
            .dataNascimento(UPDATED_DATA_NASCIMENTO)
            .cpf(UPDATED_CPF)
            .sexo(UPDATED_SEXO)
            .tipoPessoa(UPDATED_TIPO_PESSOA)
            .dataCadastro(UPDATED_DATA_CADASTRO)
            .dataAtualizacao(UPDATED_DATA_ATUALIZACAO);
        return pessoa;
    }

    @BeforeEach
    public void initTest() {
        pessoa = createEntity(em);
    }

    @Test
    @Transactional
    void createPessoa() throws Exception {
        int databaseSizeBeforeCreate = pessoaRepository.findAll().size();
        // Create the Pessoa
        PessoaDTO pessoaDTO = pessoaMapper.toDto(pessoa);
        restPessoaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pessoaDTO)))
            .andExpect(status().isCreated());

        // Validate the Pessoa in the database
        List<Pessoa> pessoaList = pessoaRepository.findAll();
        assertThat(pessoaList).hasSize(databaseSizeBeforeCreate + 1);
        Pessoa testPessoa = pessoaList.get(pessoaList.size() - 1);
        assertThat(testPessoa.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testPessoa.getDataNascimento()).isEqualTo(DEFAULT_DATA_NASCIMENTO);
        assertThat(testPessoa.getCpf()).isEqualTo(DEFAULT_CPF);
        assertThat(testPessoa.getSexo()).isEqualTo(DEFAULT_SEXO);
        assertThat(testPessoa.getTipoPessoa()).isEqualTo(DEFAULT_TIPO_PESSOA);
        assertThat(testPessoa.getDataCadastro()).isEqualTo(DEFAULT_DATA_CADASTRO);
        assertThat(testPessoa.getDataAtualizacao()).isEqualTo(DEFAULT_DATA_ATUALIZACAO);
    }

    @Test
    @Transactional
    void createPessoaWithExistingId() throws Exception {
        // Create the Pessoa with an existing ID
        pessoaRepository.saveAndFlush(pessoa);
        PessoaDTO pessoaDTO = pessoaMapper.toDto(pessoa);

        int databaseSizeBeforeCreate = pessoaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPessoaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pessoaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Pessoa in the database
        List<Pessoa> pessoaList = pessoaRepository.findAll();
        assertThat(pessoaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = pessoaRepository.findAll().size();
        // set the field null
        pessoa.setNome(null);

        // Create the Pessoa, which fails.
        PessoaDTO pessoaDTO = pessoaMapper.toDto(pessoa);

        restPessoaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pessoaDTO)))
            .andExpect(status().isBadRequest());

        List<Pessoa> pessoaList = pessoaRepository.findAll();
        assertThat(pessoaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDataNascimentoIsRequired() throws Exception {
        int databaseSizeBeforeTest = pessoaRepository.findAll().size();
        // set the field null
        pessoa.setDataNascimento(null);

        // Create the Pessoa, which fails.
        PessoaDTO pessoaDTO = pessoaMapper.toDto(pessoa);

        restPessoaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pessoaDTO)))
            .andExpect(status().isBadRequest());

        List<Pessoa> pessoaList = pessoaRepository.findAll();
        assertThat(pessoaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCpfIsRequired() throws Exception {
        int databaseSizeBeforeTest = pessoaRepository.findAll().size();
        // set the field null
        pessoa.setCpf(null);

        // Create the Pessoa, which fails.
        PessoaDTO pessoaDTO = pessoaMapper.toDto(pessoa);

        restPessoaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pessoaDTO)))
            .andExpect(status().isBadRequest());

        List<Pessoa> pessoaList = pessoaRepository.findAll();
        assertThat(pessoaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSexoIsRequired() throws Exception {
        int databaseSizeBeforeTest = pessoaRepository.findAll().size();
        // set the field null
        pessoa.setSexo(null);

        // Create the Pessoa, which fails.
        PessoaDTO pessoaDTO = pessoaMapper.toDto(pessoa);

        restPessoaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pessoaDTO)))
            .andExpect(status().isBadRequest());

        List<Pessoa> pessoaList = pessoaRepository.findAll();
        assertThat(pessoaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTipoPessoaIsRequired() throws Exception {
        int databaseSizeBeforeTest = pessoaRepository.findAll().size();
        // set the field null
        pessoa.setTipoPessoa(null);

        // Create the Pessoa, which fails.
        PessoaDTO pessoaDTO = pessoaMapper.toDto(pessoa);

        restPessoaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pessoaDTO)))
            .andExpect(status().isBadRequest());

        List<Pessoa> pessoaList = pessoaRepository.findAll();
        assertThat(pessoaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPessoas() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList
        restPessoaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pessoa.getId().toString())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].dataNascimento").value(hasItem(DEFAULT_DATA_NASCIMENTO.toString())))
            .andExpect(jsonPath("$.[*].cpf").value(hasItem(DEFAULT_CPF)))
            .andExpect(jsonPath("$.[*].sexo").value(hasItem(DEFAULT_SEXO.toString())))
            .andExpect(jsonPath("$.[*].tipoPessoa").value(hasItem(DEFAULT_TIPO_PESSOA.toString())))
            .andExpect(jsonPath("$.[*].dataCadastro").value(hasItem(sameInstant(DEFAULT_DATA_CADASTRO))))
            .andExpect(jsonPath("$.[*].dataAtualizacao").value(hasItem(sameInstant(DEFAULT_DATA_ATUALIZACAO))));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPessoasWithEagerRelationshipsIsEnabled() throws Exception {
        when(pessoaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPessoaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(pessoaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPessoasWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(pessoaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPessoaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(pessoaRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getPessoa() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        // Get the pessoa
        restPessoaMockMvc
            .perform(get(ENTITY_API_URL_ID, pessoa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pessoa.getId().toString()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.dataNascimento").value(DEFAULT_DATA_NASCIMENTO.toString()))
            .andExpect(jsonPath("$.cpf").value(DEFAULT_CPF))
            .andExpect(jsonPath("$.sexo").value(DEFAULT_SEXO.toString()))
            .andExpect(jsonPath("$.tipoPessoa").value(DEFAULT_TIPO_PESSOA.toString()))
            .andExpect(jsonPath("$.dataCadastro").value(sameInstant(DEFAULT_DATA_CADASTRO)))
            .andExpect(jsonPath("$.dataAtualizacao").value(sameInstant(DEFAULT_DATA_ATUALIZACAO)));
    }

    @Test
    @Transactional
    void getPessoasByIdFiltering() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        UUID id = pessoa.getId();

        defaultPessoaShouldBeFound("id.equals=" + id);
        defaultPessoaShouldNotBeFound("id.notEquals=" + id);
    }

    @Test
    @Transactional
    void getAllPessoasByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where nome equals to DEFAULT_NOME
        defaultPessoaShouldBeFound("nome.equals=" + DEFAULT_NOME);

        // Get all the pessoaList where nome equals to UPDATED_NOME
        defaultPessoaShouldNotBeFound("nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllPessoasByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where nome in DEFAULT_NOME or UPDATED_NOME
        defaultPessoaShouldBeFound("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME);

        // Get all the pessoaList where nome equals to UPDATED_NOME
        defaultPessoaShouldNotBeFound("nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllPessoasByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where nome is not null
        defaultPessoaShouldBeFound("nome.specified=true");

        // Get all the pessoaList where nome is null
        defaultPessoaShouldNotBeFound("nome.specified=false");
    }

    @Test
    @Transactional
    void getAllPessoasByNomeContainsSomething() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where nome contains DEFAULT_NOME
        defaultPessoaShouldBeFound("nome.contains=" + DEFAULT_NOME);

        // Get all the pessoaList where nome contains UPDATED_NOME
        defaultPessoaShouldNotBeFound("nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllPessoasByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where nome does not contain DEFAULT_NOME
        defaultPessoaShouldNotBeFound("nome.doesNotContain=" + DEFAULT_NOME);

        // Get all the pessoaList where nome does not contain UPDATED_NOME
        defaultPessoaShouldBeFound("nome.doesNotContain=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllPessoasByDataNascimentoIsEqualToSomething() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where dataNascimento equals to DEFAULT_DATA_NASCIMENTO
        defaultPessoaShouldBeFound("dataNascimento.equals=" + DEFAULT_DATA_NASCIMENTO);

        // Get all the pessoaList where dataNascimento equals to UPDATED_DATA_NASCIMENTO
        defaultPessoaShouldNotBeFound("dataNascimento.equals=" + UPDATED_DATA_NASCIMENTO);
    }

    @Test
    @Transactional
    void getAllPessoasByDataNascimentoIsInShouldWork() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where dataNascimento in DEFAULT_DATA_NASCIMENTO or UPDATED_DATA_NASCIMENTO
        defaultPessoaShouldBeFound("dataNascimento.in=" + DEFAULT_DATA_NASCIMENTO + "," + UPDATED_DATA_NASCIMENTO);

        // Get all the pessoaList where dataNascimento equals to UPDATED_DATA_NASCIMENTO
        defaultPessoaShouldNotBeFound("dataNascimento.in=" + UPDATED_DATA_NASCIMENTO);
    }

    @Test
    @Transactional
    void getAllPessoasByDataNascimentoIsNullOrNotNull() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where dataNascimento is not null
        defaultPessoaShouldBeFound("dataNascimento.specified=true");

        // Get all the pessoaList where dataNascimento is null
        defaultPessoaShouldNotBeFound("dataNascimento.specified=false");
    }

    @Test
    @Transactional
    void getAllPessoasByDataNascimentoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where dataNascimento is greater than or equal to DEFAULT_DATA_NASCIMENTO
        defaultPessoaShouldBeFound("dataNascimento.greaterThanOrEqual=" + DEFAULT_DATA_NASCIMENTO);

        // Get all the pessoaList where dataNascimento is greater than or equal to UPDATED_DATA_NASCIMENTO
        defaultPessoaShouldNotBeFound("dataNascimento.greaterThanOrEqual=" + UPDATED_DATA_NASCIMENTO);
    }

    @Test
    @Transactional
    void getAllPessoasByDataNascimentoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where dataNascimento is less than or equal to DEFAULT_DATA_NASCIMENTO
        defaultPessoaShouldBeFound("dataNascimento.lessThanOrEqual=" + DEFAULT_DATA_NASCIMENTO);

        // Get all the pessoaList where dataNascimento is less than or equal to SMALLER_DATA_NASCIMENTO
        defaultPessoaShouldNotBeFound("dataNascimento.lessThanOrEqual=" + SMALLER_DATA_NASCIMENTO);
    }

    @Test
    @Transactional
    void getAllPessoasByDataNascimentoIsLessThanSomething() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where dataNascimento is less than DEFAULT_DATA_NASCIMENTO
        defaultPessoaShouldNotBeFound("dataNascimento.lessThan=" + DEFAULT_DATA_NASCIMENTO);

        // Get all the pessoaList where dataNascimento is less than UPDATED_DATA_NASCIMENTO
        defaultPessoaShouldBeFound("dataNascimento.lessThan=" + UPDATED_DATA_NASCIMENTO);
    }

    @Test
    @Transactional
    void getAllPessoasByDataNascimentoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where dataNascimento is greater than DEFAULT_DATA_NASCIMENTO
        defaultPessoaShouldNotBeFound("dataNascimento.greaterThan=" + DEFAULT_DATA_NASCIMENTO);

        // Get all the pessoaList where dataNascimento is greater than SMALLER_DATA_NASCIMENTO
        defaultPessoaShouldBeFound("dataNascimento.greaterThan=" + SMALLER_DATA_NASCIMENTO);
    }

    @Test
    @Transactional
    void getAllPessoasByCpfIsEqualToSomething() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where cpf equals to DEFAULT_CPF
        defaultPessoaShouldBeFound("cpf.equals=" + DEFAULT_CPF);

        // Get all the pessoaList where cpf equals to UPDATED_CPF
        defaultPessoaShouldNotBeFound("cpf.equals=" + UPDATED_CPF);
    }

    @Test
    @Transactional
    void getAllPessoasByCpfIsInShouldWork() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where cpf in DEFAULT_CPF or UPDATED_CPF
        defaultPessoaShouldBeFound("cpf.in=" + DEFAULT_CPF + "," + UPDATED_CPF);

        // Get all the pessoaList where cpf equals to UPDATED_CPF
        defaultPessoaShouldNotBeFound("cpf.in=" + UPDATED_CPF);
    }

    @Test
    @Transactional
    void getAllPessoasByCpfIsNullOrNotNull() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where cpf is not null
        defaultPessoaShouldBeFound("cpf.specified=true");

        // Get all the pessoaList where cpf is null
        defaultPessoaShouldNotBeFound("cpf.specified=false");
    }

    @Test
    @Transactional
    void getAllPessoasByCpfContainsSomething() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where cpf contains DEFAULT_CPF
        defaultPessoaShouldBeFound("cpf.contains=" + DEFAULT_CPF);

        // Get all the pessoaList where cpf contains UPDATED_CPF
        defaultPessoaShouldNotBeFound("cpf.contains=" + UPDATED_CPF);
    }

    @Test
    @Transactional
    void getAllPessoasByCpfNotContainsSomething() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where cpf does not contain DEFAULT_CPF
        defaultPessoaShouldNotBeFound("cpf.doesNotContain=" + DEFAULT_CPF);

        // Get all the pessoaList where cpf does not contain UPDATED_CPF
        defaultPessoaShouldBeFound("cpf.doesNotContain=" + UPDATED_CPF);
    }

    @Test
    @Transactional
    void getAllPessoasBySexoIsEqualToSomething() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where sexo equals to DEFAULT_SEXO
        defaultPessoaShouldBeFound("sexo.equals=" + DEFAULT_SEXO);

        // Get all the pessoaList where sexo equals to UPDATED_SEXO
        defaultPessoaShouldNotBeFound("sexo.equals=" + UPDATED_SEXO);
    }

    @Test
    @Transactional
    void getAllPessoasBySexoIsInShouldWork() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where sexo in DEFAULT_SEXO or UPDATED_SEXO
        defaultPessoaShouldBeFound("sexo.in=" + DEFAULT_SEXO + "," + UPDATED_SEXO);

        // Get all the pessoaList where sexo equals to UPDATED_SEXO
        defaultPessoaShouldNotBeFound("sexo.in=" + UPDATED_SEXO);
    }

    @Test
    @Transactional
    void getAllPessoasBySexoIsNullOrNotNull() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where sexo is not null
        defaultPessoaShouldBeFound("sexo.specified=true");

        // Get all the pessoaList where sexo is null
        defaultPessoaShouldNotBeFound("sexo.specified=false");
    }

    @Test
    @Transactional
    void getAllPessoasByTipoPessoaIsEqualToSomething() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where tipoPessoa equals to DEFAULT_TIPO_PESSOA
        defaultPessoaShouldBeFound("tipoPessoa.equals=" + DEFAULT_TIPO_PESSOA);

        // Get all the pessoaList where tipoPessoa equals to UPDATED_TIPO_PESSOA
        defaultPessoaShouldNotBeFound("tipoPessoa.equals=" + UPDATED_TIPO_PESSOA);
    }

    @Test
    @Transactional
    void getAllPessoasByTipoPessoaIsInShouldWork() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where tipoPessoa in DEFAULT_TIPO_PESSOA or UPDATED_TIPO_PESSOA
        defaultPessoaShouldBeFound("tipoPessoa.in=" + DEFAULT_TIPO_PESSOA + "," + UPDATED_TIPO_PESSOA);

        // Get all the pessoaList where tipoPessoa equals to UPDATED_TIPO_PESSOA
        defaultPessoaShouldNotBeFound("tipoPessoa.in=" + UPDATED_TIPO_PESSOA);
    }

    @Test
    @Transactional
    void getAllPessoasByTipoPessoaIsNullOrNotNull() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where tipoPessoa is not null
        defaultPessoaShouldBeFound("tipoPessoa.specified=true");

        // Get all the pessoaList where tipoPessoa is null
        defaultPessoaShouldNotBeFound("tipoPessoa.specified=false");
    }

    @Test
    @Transactional
    void getAllPessoasByDataCadastroIsEqualToSomething() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where dataCadastro equals to DEFAULT_DATA_CADASTRO
        defaultPessoaShouldBeFound("dataCadastro.equals=" + DEFAULT_DATA_CADASTRO);

        // Get all the pessoaList where dataCadastro equals to UPDATED_DATA_CADASTRO
        defaultPessoaShouldNotBeFound("dataCadastro.equals=" + UPDATED_DATA_CADASTRO);
    }

    @Test
    @Transactional
    void getAllPessoasByDataCadastroIsInShouldWork() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where dataCadastro in DEFAULT_DATA_CADASTRO or UPDATED_DATA_CADASTRO
        defaultPessoaShouldBeFound("dataCadastro.in=" + DEFAULT_DATA_CADASTRO + "," + UPDATED_DATA_CADASTRO);

        // Get all the pessoaList where dataCadastro equals to UPDATED_DATA_CADASTRO
        defaultPessoaShouldNotBeFound("dataCadastro.in=" + UPDATED_DATA_CADASTRO);
    }

    @Test
    @Transactional
    void getAllPessoasByDataCadastroIsNullOrNotNull() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where dataCadastro is not null
        defaultPessoaShouldBeFound("dataCadastro.specified=true");

        // Get all the pessoaList where dataCadastro is null
        defaultPessoaShouldNotBeFound("dataCadastro.specified=false");
    }

    @Test
    @Transactional
    void getAllPessoasByDataCadastroIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where dataCadastro is greater than or equal to DEFAULT_DATA_CADASTRO
        defaultPessoaShouldBeFound("dataCadastro.greaterThanOrEqual=" + DEFAULT_DATA_CADASTRO);

        // Get all the pessoaList where dataCadastro is greater than or equal to UPDATED_DATA_CADASTRO
        defaultPessoaShouldNotBeFound("dataCadastro.greaterThanOrEqual=" + UPDATED_DATA_CADASTRO);
    }

    @Test
    @Transactional
    void getAllPessoasByDataCadastroIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where dataCadastro is less than or equal to DEFAULT_DATA_CADASTRO
        defaultPessoaShouldBeFound("dataCadastro.lessThanOrEqual=" + DEFAULT_DATA_CADASTRO);

        // Get all the pessoaList where dataCadastro is less than or equal to SMALLER_DATA_CADASTRO
        defaultPessoaShouldNotBeFound("dataCadastro.lessThanOrEqual=" + SMALLER_DATA_CADASTRO);
    }

    @Test
    @Transactional
    void getAllPessoasByDataCadastroIsLessThanSomething() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where dataCadastro is less than DEFAULT_DATA_CADASTRO
        defaultPessoaShouldNotBeFound("dataCadastro.lessThan=" + DEFAULT_DATA_CADASTRO);

        // Get all the pessoaList where dataCadastro is less than UPDATED_DATA_CADASTRO
        defaultPessoaShouldBeFound("dataCadastro.lessThan=" + UPDATED_DATA_CADASTRO);
    }

    @Test
    @Transactional
    void getAllPessoasByDataCadastroIsGreaterThanSomething() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where dataCadastro is greater than DEFAULT_DATA_CADASTRO
        defaultPessoaShouldNotBeFound("dataCadastro.greaterThan=" + DEFAULT_DATA_CADASTRO);

        // Get all the pessoaList where dataCadastro is greater than SMALLER_DATA_CADASTRO
        defaultPessoaShouldBeFound("dataCadastro.greaterThan=" + SMALLER_DATA_CADASTRO);
    }

    @Test
    @Transactional
    void getAllPessoasByDataAtualizacaoIsEqualToSomething() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where dataAtualizacao equals to DEFAULT_DATA_ATUALIZACAO
        defaultPessoaShouldBeFound("dataAtualizacao.equals=" + DEFAULT_DATA_ATUALIZACAO);

        // Get all the pessoaList where dataAtualizacao equals to UPDATED_DATA_ATUALIZACAO
        defaultPessoaShouldNotBeFound("dataAtualizacao.equals=" + UPDATED_DATA_ATUALIZACAO);
    }

    @Test
    @Transactional
    void getAllPessoasByDataAtualizacaoIsInShouldWork() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where dataAtualizacao in DEFAULT_DATA_ATUALIZACAO or UPDATED_DATA_ATUALIZACAO
        defaultPessoaShouldBeFound("dataAtualizacao.in=" + DEFAULT_DATA_ATUALIZACAO + "," + UPDATED_DATA_ATUALIZACAO);

        // Get all the pessoaList where dataAtualizacao equals to UPDATED_DATA_ATUALIZACAO
        defaultPessoaShouldNotBeFound("dataAtualizacao.in=" + UPDATED_DATA_ATUALIZACAO);
    }

    @Test
    @Transactional
    void getAllPessoasByDataAtualizacaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where dataAtualizacao is not null
        defaultPessoaShouldBeFound("dataAtualizacao.specified=true");

        // Get all the pessoaList where dataAtualizacao is null
        defaultPessoaShouldNotBeFound("dataAtualizacao.specified=false");
    }

    @Test
    @Transactional
    void getAllPessoasByDataAtualizacaoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where dataAtualizacao is greater than or equal to DEFAULT_DATA_ATUALIZACAO
        defaultPessoaShouldBeFound("dataAtualizacao.greaterThanOrEqual=" + DEFAULT_DATA_ATUALIZACAO);

        // Get all the pessoaList where dataAtualizacao is greater than or equal to UPDATED_DATA_ATUALIZACAO
        defaultPessoaShouldNotBeFound("dataAtualizacao.greaterThanOrEqual=" + UPDATED_DATA_ATUALIZACAO);
    }

    @Test
    @Transactional
    void getAllPessoasByDataAtualizacaoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where dataAtualizacao is less than or equal to DEFAULT_DATA_ATUALIZACAO
        defaultPessoaShouldBeFound("dataAtualizacao.lessThanOrEqual=" + DEFAULT_DATA_ATUALIZACAO);

        // Get all the pessoaList where dataAtualizacao is less than or equal to SMALLER_DATA_ATUALIZACAO
        defaultPessoaShouldNotBeFound("dataAtualizacao.lessThanOrEqual=" + SMALLER_DATA_ATUALIZACAO);
    }

    @Test
    @Transactional
    void getAllPessoasByDataAtualizacaoIsLessThanSomething() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where dataAtualizacao is less than DEFAULT_DATA_ATUALIZACAO
        defaultPessoaShouldNotBeFound("dataAtualizacao.lessThan=" + DEFAULT_DATA_ATUALIZACAO);

        // Get all the pessoaList where dataAtualizacao is less than UPDATED_DATA_ATUALIZACAO
        defaultPessoaShouldBeFound("dataAtualizacao.lessThan=" + UPDATED_DATA_ATUALIZACAO);
    }

    @Test
    @Transactional
    void getAllPessoasByDataAtualizacaoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where dataAtualizacao is greater than DEFAULT_DATA_ATUALIZACAO
        defaultPessoaShouldNotBeFound("dataAtualizacao.greaterThan=" + DEFAULT_DATA_ATUALIZACAO);

        // Get all the pessoaList where dataAtualizacao is greater than SMALLER_DATA_ATUALIZACAO
        defaultPessoaShouldBeFound("dataAtualizacao.greaterThan=" + SMALLER_DATA_ATUALIZACAO);
    }

    @Test
    @Transactional
    void getAllPessoasByTelefonesIsEqualToSomething() throws Exception {
        Telefone telefones;
        if (TestUtil.findAll(em, Telefone.class).isEmpty()) {
            pessoaRepository.saveAndFlush(pessoa);
            telefones = TelefoneResourceIT.createEntity(em);
        } else {
            telefones = TestUtil.findAll(em, Telefone.class).get(0);
        }
        em.persist(telefones);
        em.flush();
        pessoa.addTelefones(telefones);
        pessoaRepository.saveAndFlush(pessoa);
        UUID telefonesId = telefones.getId();

        // Get all the pessoaList where telefones equals to telefonesId
        defaultPessoaShouldBeFound("telefonesId.equals=" + telefonesId);

        // Get all the pessoaList where telefones equals to UUID.randomUUID()
        defaultPessoaShouldNotBeFound("telefonesId.equals=" + UUID.randomUUID());
    }

    @Test
    @Transactional
    void getAllPessoasByEnderecosIsEqualToSomething() throws Exception {
        Endereco enderecos;
        if (TestUtil.findAll(em, Endereco.class).isEmpty()) {
            pessoaRepository.saveAndFlush(pessoa);
            enderecos = EnderecoResourceIT.createEntity(em);
        } else {
            enderecos = TestUtil.findAll(em, Endereco.class).get(0);
        }
        em.persist(enderecos);
        em.flush();
        pessoa.addEnderecos(enderecos);
        pessoaRepository.saveAndFlush(pessoa);
        UUID enderecosId = enderecos.getId();

        // Get all the pessoaList where enderecos equals to enderecosId
        defaultPessoaShouldBeFound("enderecosId.equals=" + enderecosId);

        // Get all the pessoaList where enderecos equals to UUID.randomUUID()
        defaultPessoaShouldNotBeFound("enderecosId.equals=" + UUID.randomUUID());
    }

    @Test
    @Transactional
    void getAllPessoasByTurmaIsEqualToSomething() throws Exception {
        Turma turma;
        if (TestUtil.findAll(em, Turma.class).isEmpty()) {
            pessoaRepository.saveAndFlush(pessoa);
            turma = TurmaResourceIT.createEntity(em);
        } else {
            turma = TestUtil.findAll(em, Turma.class).get(0);
        }
        em.persist(turma);
        em.flush();
        pessoa.setTurma(turma);
        pessoaRepository.saveAndFlush(pessoa);
        UUID turmaId = turma.getId();

        // Get all the pessoaList where turma equals to turmaId
        defaultPessoaShouldBeFound("turmaId.equals=" + turmaId);

        // Get all the pessoaList where turma equals to UUID.randomUUID()
        defaultPessoaShouldNotBeFound("turmaId.equals=" + UUID.randomUUID());
    }

    @Test
    @Transactional
    void getAllPessoasByResponsaveisIsEqualToSomething() throws Exception {
        Responsavel responsaveis;
        if (TestUtil.findAll(em, Responsavel.class).isEmpty()) {
            pessoaRepository.saveAndFlush(pessoa);
            responsaveis = ResponsavelResourceIT.createEntity(em);
        } else {
            responsaveis = TestUtil.findAll(em, Responsavel.class).get(0);
        }
        em.persist(responsaveis);
        em.flush();
        pessoa.addResponsaveis(responsaveis);
        pessoaRepository.saveAndFlush(pessoa);
        UUID responsaveisId = responsaveis.getId();

        // Get all the pessoaList where responsaveis equals to responsaveisId
        defaultPessoaShouldBeFound("responsaveisId.equals=" + responsaveisId);

        // Get all the pessoaList where responsaveis equals to UUID.randomUUID()
        defaultPessoaShouldNotBeFound("responsaveisId.equals=" + UUID.randomUUID());
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPessoaShouldBeFound(String filter) throws Exception {
        restPessoaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pessoa.getId().toString())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].dataNascimento").value(hasItem(DEFAULT_DATA_NASCIMENTO.toString())))
            .andExpect(jsonPath("$.[*].cpf").value(hasItem(DEFAULT_CPF)))
            .andExpect(jsonPath("$.[*].sexo").value(hasItem(DEFAULT_SEXO.toString())))
            .andExpect(jsonPath("$.[*].tipoPessoa").value(hasItem(DEFAULT_TIPO_PESSOA.toString())))
            .andExpect(jsonPath("$.[*].dataCadastro").value(hasItem(sameInstant(DEFAULT_DATA_CADASTRO))))
            .andExpect(jsonPath("$.[*].dataAtualizacao").value(hasItem(sameInstant(DEFAULT_DATA_ATUALIZACAO))));

        // Check, that the count call also returns 1
        restPessoaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPessoaShouldNotBeFound(String filter) throws Exception {
        restPessoaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPessoaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingPessoa() throws Exception {
        // Get the pessoa
        restPessoaMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPessoa() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        int databaseSizeBeforeUpdate = pessoaRepository.findAll().size();

        // Update the pessoa
        Pessoa updatedPessoa = pessoaRepository.findById(pessoa.getId()).get();
        // Disconnect from session so that the updates on updatedPessoa are not directly saved in db
        em.detach(updatedPessoa);
        updatedPessoa
            .nome(UPDATED_NOME)
            .dataNascimento(UPDATED_DATA_NASCIMENTO)
            .cpf(UPDATED_CPF)
            .sexo(UPDATED_SEXO)
            .tipoPessoa(UPDATED_TIPO_PESSOA)
            .dataCadastro(UPDATED_DATA_CADASTRO)
            .dataAtualizacao(UPDATED_DATA_ATUALIZACAO);
        PessoaDTO pessoaDTO = pessoaMapper.toDto(updatedPessoa);

        restPessoaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pessoaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pessoaDTO))
            )
            .andExpect(status().isOk());

        // Validate the Pessoa in the database
        List<Pessoa> pessoaList = pessoaRepository.findAll();
        assertThat(pessoaList).hasSize(databaseSizeBeforeUpdate);
        Pessoa testPessoa = pessoaList.get(pessoaList.size() - 1);
        assertThat(testPessoa.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testPessoa.getDataNascimento()).isEqualTo(UPDATED_DATA_NASCIMENTO);
        assertThat(testPessoa.getCpf()).isEqualTo(UPDATED_CPF);
        assertThat(testPessoa.getSexo()).isEqualTo(UPDATED_SEXO);
        assertThat(testPessoa.getTipoPessoa()).isEqualTo(UPDATED_TIPO_PESSOA);
        assertThat(testPessoa.getDataCadastro()).isEqualTo(UPDATED_DATA_CADASTRO);
        assertThat(testPessoa.getDataAtualizacao()).isEqualTo(UPDATED_DATA_ATUALIZACAO);
    }

    @Test
    @Transactional
    void putNonExistingPessoa() throws Exception {
        int databaseSizeBeforeUpdate = pessoaRepository.findAll().size();
        pessoa.setId(UUID.randomUUID());

        // Create the Pessoa
        PessoaDTO pessoaDTO = pessoaMapper.toDto(pessoa);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPessoaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pessoaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pessoaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pessoa in the database
        List<Pessoa> pessoaList = pessoaRepository.findAll();
        assertThat(pessoaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPessoa() throws Exception {
        int databaseSizeBeforeUpdate = pessoaRepository.findAll().size();
        pessoa.setId(UUID.randomUUID());

        // Create the Pessoa
        PessoaDTO pessoaDTO = pessoaMapper.toDto(pessoa);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPessoaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pessoaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pessoa in the database
        List<Pessoa> pessoaList = pessoaRepository.findAll();
        assertThat(pessoaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPessoa() throws Exception {
        int databaseSizeBeforeUpdate = pessoaRepository.findAll().size();
        pessoa.setId(UUID.randomUUID());

        // Create the Pessoa
        PessoaDTO pessoaDTO = pessoaMapper.toDto(pessoa);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPessoaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pessoaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Pessoa in the database
        List<Pessoa> pessoaList = pessoaRepository.findAll();
        assertThat(pessoaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePessoaWithPatch() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        int databaseSizeBeforeUpdate = pessoaRepository.findAll().size();

        // Update the pessoa using partial update
        Pessoa partialUpdatedPessoa = new Pessoa();
        partialUpdatedPessoa.setId(pessoa.getId());

        partialUpdatedPessoa
            .nome(UPDATED_NOME)
            .dataNascimento(UPDATED_DATA_NASCIMENTO)
            .sexo(UPDATED_SEXO)
            .tipoPessoa(UPDATED_TIPO_PESSOA)
            .dataCadastro(UPDATED_DATA_CADASTRO)
            .dataAtualizacao(UPDATED_DATA_ATUALIZACAO);

        restPessoaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPessoa.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPessoa))
            )
            .andExpect(status().isOk());

        // Validate the Pessoa in the database
        List<Pessoa> pessoaList = pessoaRepository.findAll();
        assertThat(pessoaList).hasSize(databaseSizeBeforeUpdate);
        Pessoa testPessoa = pessoaList.get(pessoaList.size() - 1);
        assertThat(testPessoa.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testPessoa.getDataNascimento()).isEqualTo(UPDATED_DATA_NASCIMENTO);
        assertThat(testPessoa.getCpf()).isEqualTo(DEFAULT_CPF);
        assertThat(testPessoa.getSexo()).isEqualTo(UPDATED_SEXO);
        assertThat(testPessoa.getTipoPessoa()).isEqualTo(UPDATED_TIPO_PESSOA);
        assertThat(testPessoa.getDataCadastro()).isEqualTo(UPDATED_DATA_CADASTRO);
        assertThat(testPessoa.getDataAtualizacao()).isEqualTo(UPDATED_DATA_ATUALIZACAO);
    }

    @Test
    @Transactional
    void fullUpdatePessoaWithPatch() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        int databaseSizeBeforeUpdate = pessoaRepository.findAll().size();

        // Update the pessoa using partial update
        Pessoa partialUpdatedPessoa = new Pessoa();
        partialUpdatedPessoa.setId(pessoa.getId());

        partialUpdatedPessoa
            .nome(UPDATED_NOME)
            .dataNascimento(UPDATED_DATA_NASCIMENTO)
            .cpf(UPDATED_CPF)
            .sexo(UPDATED_SEXO)
            .tipoPessoa(UPDATED_TIPO_PESSOA)
            .dataCadastro(UPDATED_DATA_CADASTRO)
            .dataAtualizacao(UPDATED_DATA_ATUALIZACAO);

        restPessoaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPessoa.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPessoa))
            )
            .andExpect(status().isOk());

        // Validate the Pessoa in the database
        List<Pessoa> pessoaList = pessoaRepository.findAll();
        assertThat(pessoaList).hasSize(databaseSizeBeforeUpdate);
        Pessoa testPessoa = pessoaList.get(pessoaList.size() - 1);
        assertThat(testPessoa.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testPessoa.getDataNascimento()).isEqualTo(UPDATED_DATA_NASCIMENTO);
        assertThat(testPessoa.getCpf()).isEqualTo(UPDATED_CPF);
        assertThat(testPessoa.getSexo()).isEqualTo(UPDATED_SEXO);
        assertThat(testPessoa.getTipoPessoa()).isEqualTo(UPDATED_TIPO_PESSOA);
        assertThat(testPessoa.getDataCadastro()).isEqualTo(UPDATED_DATA_CADASTRO);
        assertThat(testPessoa.getDataAtualizacao()).isEqualTo(UPDATED_DATA_ATUALIZACAO);
    }

    @Test
    @Transactional
    void patchNonExistingPessoa() throws Exception {
        int databaseSizeBeforeUpdate = pessoaRepository.findAll().size();
        pessoa.setId(UUID.randomUUID());

        // Create the Pessoa
        PessoaDTO pessoaDTO = pessoaMapper.toDto(pessoa);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPessoaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, pessoaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pessoaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pessoa in the database
        List<Pessoa> pessoaList = pessoaRepository.findAll();
        assertThat(pessoaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPessoa() throws Exception {
        int databaseSizeBeforeUpdate = pessoaRepository.findAll().size();
        pessoa.setId(UUID.randomUUID());

        // Create the Pessoa
        PessoaDTO pessoaDTO = pessoaMapper.toDto(pessoa);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPessoaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pessoaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pessoa in the database
        List<Pessoa> pessoaList = pessoaRepository.findAll();
        assertThat(pessoaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPessoa() throws Exception {
        int databaseSizeBeforeUpdate = pessoaRepository.findAll().size();
        pessoa.setId(UUID.randomUUID());

        // Create the Pessoa
        PessoaDTO pessoaDTO = pessoaMapper.toDto(pessoa);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPessoaMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(pessoaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Pessoa in the database
        List<Pessoa> pessoaList = pessoaRepository.findAll();
        assertThat(pessoaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePessoa() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        int databaseSizeBeforeDelete = pessoaRepository.findAll().size();

        // Delete the pessoa
        restPessoaMockMvc
            .perform(delete(ENTITY_API_URL_ID, pessoa.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Pessoa> pessoaList = pessoaRepository.findAll();
        assertThat(pessoaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

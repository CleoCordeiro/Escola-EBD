package br.com.cleo.escolaebd.web.rest;

import static br.com.cleo.escolaebd.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.cleo.escolaebd.IntegrationTest;
import br.com.cleo.escolaebd.domain.Pessoa;
import br.com.cleo.escolaebd.domain.Turma;
import br.com.cleo.escolaebd.domain.enumeration.FaixaEtaria;
import br.com.cleo.escolaebd.domain.enumeration.SexoTurma;
import br.com.cleo.escolaebd.repository.TurmaRepository;
import br.com.cleo.escolaebd.service.criteria.TurmaCriteria;
import br.com.cleo.escolaebd.service.dto.TurmaDTO;
import br.com.cleo.escolaebd.service.mapper.TurmaMapper;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link TurmaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TurmaResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final SexoTurma DEFAULT_SEXO_TURMA = SexoTurma.MASCULINO;
    private static final SexoTurma UPDATED_SEXO_TURMA = SexoTurma.FEMININO;

    private static final FaixaEtaria DEFAULT_FAIXA_ETARIA = FaixaEtaria.CRIANCA;
    private static final FaixaEtaria UPDATED_FAIXA_ETARIA = FaixaEtaria.JOVEM;

    private static final LocalDate DEFAULT_DATA_INICIO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_INICIO = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATA_INICIO = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_DATA_FIM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_FIM = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATA_FIM = LocalDate.ofEpochDay(-1L);

    private static final ZonedDateTime DEFAULT_DATA_CADASTRO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATA_CADASTRO = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_DATA_CADASTRO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final String ENTITY_API_URL = "/api/turmas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private TurmaRepository turmaRepository;

    @Autowired
    private TurmaMapper turmaMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTurmaMockMvc;

    private Turma turma;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Turma createEntity(EntityManager em) {
        Turma turma = new Turma()
            .nome(DEFAULT_NOME)
            .sexoTurma(DEFAULT_SEXO_TURMA)
            .faixaEtaria(DEFAULT_FAIXA_ETARIA)
            .dataInicio(DEFAULT_DATA_INICIO)
            .dataFim(DEFAULT_DATA_FIM)
            .dataCadastro(DEFAULT_DATA_CADASTRO);
        return turma;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Turma createUpdatedEntity(EntityManager em) {
        Turma turma = new Turma()
            .nome(UPDATED_NOME)
            .sexoTurma(UPDATED_SEXO_TURMA)
            .faixaEtaria(UPDATED_FAIXA_ETARIA)
            .dataInicio(UPDATED_DATA_INICIO)
            .dataFim(UPDATED_DATA_FIM)
            .dataCadastro(UPDATED_DATA_CADASTRO);
        return turma;
    }

    @BeforeEach
    public void initTest() {
        turma = createEntity(em);
    }

    @Test
    @Transactional
    void createTurma() throws Exception {
        int databaseSizeBeforeCreate = turmaRepository.findAll().size();
        // Create the Turma
        TurmaDTO turmaDTO = turmaMapper.toDto(turma);
        restTurmaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(turmaDTO)))
            .andExpect(status().isCreated());

        // Validate the Turma in the database
        List<Turma> turmaList = turmaRepository.findAll();
        assertThat(turmaList).hasSize(databaseSizeBeforeCreate + 1);
        Turma testTurma = turmaList.get(turmaList.size() - 1);
        assertThat(testTurma.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testTurma.getSexoTurma()).isEqualTo(DEFAULT_SEXO_TURMA);
        assertThat(testTurma.getFaixaEtaria()).isEqualTo(DEFAULT_FAIXA_ETARIA);
        assertThat(testTurma.getDataInicio()).isEqualTo(DEFAULT_DATA_INICIO);
        assertThat(testTurma.getDataFim()).isEqualTo(DEFAULT_DATA_FIM);
        assertThat(testTurma.getDataCadastro()).isEqualTo(DEFAULT_DATA_CADASTRO);
    }

    @Test
    @Transactional
    void createTurmaWithExistingId() throws Exception {
        // Create the Turma with an existing ID
        turmaRepository.saveAndFlush(turma);
        TurmaDTO turmaDTO = turmaMapper.toDto(turma);

        int databaseSizeBeforeCreate = turmaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTurmaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(turmaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Turma in the database
        List<Turma> turmaList = turmaRepository.findAll();
        assertThat(turmaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = turmaRepository.findAll().size();
        // set the field null
        turma.setNome(null);

        // Create the Turma, which fails.
        TurmaDTO turmaDTO = turmaMapper.toDto(turma);

        restTurmaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(turmaDTO)))
            .andExpect(status().isBadRequest());

        List<Turma> turmaList = turmaRepository.findAll();
        assertThat(turmaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSexoTurmaIsRequired() throws Exception {
        int databaseSizeBeforeTest = turmaRepository.findAll().size();
        // set the field null
        turma.setSexoTurma(null);

        // Create the Turma, which fails.
        TurmaDTO turmaDTO = turmaMapper.toDto(turma);

        restTurmaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(turmaDTO)))
            .andExpect(status().isBadRequest());

        List<Turma> turmaList = turmaRepository.findAll();
        assertThat(turmaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFaixaEtariaIsRequired() throws Exception {
        int databaseSizeBeforeTest = turmaRepository.findAll().size();
        // set the field null
        turma.setFaixaEtaria(null);

        // Create the Turma, which fails.
        TurmaDTO turmaDTO = turmaMapper.toDto(turma);

        restTurmaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(turmaDTO)))
            .andExpect(status().isBadRequest());

        List<Turma> turmaList = turmaRepository.findAll();
        assertThat(turmaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDataInicioIsRequired() throws Exception {
        int databaseSizeBeforeTest = turmaRepository.findAll().size();
        // set the field null
        turma.setDataInicio(null);

        // Create the Turma, which fails.
        TurmaDTO turmaDTO = turmaMapper.toDto(turma);

        restTurmaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(turmaDTO)))
            .andExpect(status().isBadRequest());

        List<Turma> turmaList = turmaRepository.findAll();
        assertThat(turmaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTurmas() throws Exception {
        // Initialize the database
        turmaRepository.saveAndFlush(turma);

        // Get all the turmaList
        restTurmaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(turma.getId().toString())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].sexoTurma").value(hasItem(DEFAULT_SEXO_TURMA.toString())))
            .andExpect(jsonPath("$.[*].faixaEtaria").value(hasItem(DEFAULT_FAIXA_ETARIA.toString())))
            .andExpect(jsonPath("$.[*].dataInicio").value(hasItem(DEFAULT_DATA_INICIO.toString())))
            .andExpect(jsonPath("$.[*].dataFim").value(hasItem(DEFAULT_DATA_FIM.toString())))
            .andExpect(jsonPath("$.[*].dataCadastro").value(hasItem(sameInstant(DEFAULT_DATA_CADASTRO))));
    }

    @Test
    @Transactional
    void getTurma() throws Exception {
        // Initialize the database
        turmaRepository.saveAndFlush(turma);

        // Get the turma
        restTurmaMockMvc
            .perform(get(ENTITY_API_URL_ID, turma.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(turma.getId().toString()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.sexoTurma").value(DEFAULT_SEXO_TURMA.toString()))
            .andExpect(jsonPath("$.faixaEtaria").value(DEFAULT_FAIXA_ETARIA.toString()))
            .andExpect(jsonPath("$.dataInicio").value(DEFAULT_DATA_INICIO.toString()))
            .andExpect(jsonPath("$.dataFim").value(DEFAULT_DATA_FIM.toString()))
            .andExpect(jsonPath("$.dataCadastro").value(sameInstant(DEFAULT_DATA_CADASTRO)));
    }

    @Test
    @Transactional
    void getTurmasByIdFiltering() throws Exception {
        // Initialize the database
        turmaRepository.saveAndFlush(turma);

        UUID id = turma.getId();

        defaultTurmaShouldBeFound("id.equals=" + id);
        defaultTurmaShouldNotBeFound("id.notEquals=" + id);
    }

    @Test
    @Transactional
    void getAllTurmasByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        turmaRepository.saveAndFlush(turma);

        // Get all the turmaList where nome equals to DEFAULT_NOME
        defaultTurmaShouldBeFound("nome.equals=" + DEFAULT_NOME);

        // Get all the turmaList where nome equals to UPDATED_NOME
        defaultTurmaShouldNotBeFound("nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllTurmasByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        turmaRepository.saveAndFlush(turma);

        // Get all the turmaList where nome in DEFAULT_NOME or UPDATED_NOME
        defaultTurmaShouldBeFound("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME);

        // Get all the turmaList where nome equals to UPDATED_NOME
        defaultTurmaShouldNotBeFound("nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllTurmasByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        turmaRepository.saveAndFlush(turma);

        // Get all the turmaList where nome is not null
        defaultTurmaShouldBeFound("nome.specified=true");

        // Get all the turmaList where nome is null
        defaultTurmaShouldNotBeFound("nome.specified=false");
    }

    @Test
    @Transactional
    void getAllTurmasByNomeContainsSomething() throws Exception {
        // Initialize the database
        turmaRepository.saveAndFlush(turma);

        // Get all the turmaList where nome contains DEFAULT_NOME
        defaultTurmaShouldBeFound("nome.contains=" + DEFAULT_NOME);

        // Get all the turmaList where nome contains UPDATED_NOME
        defaultTurmaShouldNotBeFound("nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllTurmasByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        turmaRepository.saveAndFlush(turma);

        // Get all the turmaList where nome does not contain DEFAULT_NOME
        defaultTurmaShouldNotBeFound("nome.doesNotContain=" + DEFAULT_NOME);

        // Get all the turmaList where nome does not contain UPDATED_NOME
        defaultTurmaShouldBeFound("nome.doesNotContain=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllTurmasBySexoTurmaIsEqualToSomething() throws Exception {
        // Initialize the database
        turmaRepository.saveAndFlush(turma);

        // Get all the turmaList where sexoTurma equals to DEFAULT_SEXO_TURMA
        defaultTurmaShouldBeFound("sexoTurma.equals=" + DEFAULT_SEXO_TURMA);

        // Get all the turmaList where sexoTurma equals to UPDATED_SEXO_TURMA
        defaultTurmaShouldNotBeFound("sexoTurma.equals=" + UPDATED_SEXO_TURMA);
    }

    @Test
    @Transactional
    void getAllTurmasBySexoTurmaIsInShouldWork() throws Exception {
        // Initialize the database
        turmaRepository.saveAndFlush(turma);

        // Get all the turmaList where sexoTurma in DEFAULT_SEXO_TURMA or UPDATED_SEXO_TURMA
        defaultTurmaShouldBeFound("sexoTurma.in=" + DEFAULT_SEXO_TURMA + "," + UPDATED_SEXO_TURMA);

        // Get all the turmaList where sexoTurma equals to UPDATED_SEXO_TURMA
        defaultTurmaShouldNotBeFound("sexoTurma.in=" + UPDATED_SEXO_TURMA);
    }

    @Test
    @Transactional
    void getAllTurmasBySexoTurmaIsNullOrNotNull() throws Exception {
        // Initialize the database
        turmaRepository.saveAndFlush(turma);

        // Get all the turmaList where sexoTurma is not null
        defaultTurmaShouldBeFound("sexoTurma.specified=true");

        // Get all the turmaList where sexoTurma is null
        defaultTurmaShouldNotBeFound("sexoTurma.specified=false");
    }

    @Test
    @Transactional
    void getAllTurmasByFaixaEtariaIsEqualToSomething() throws Exception {
        // Initialize the database
        turmaRepository.saveAndFlush(turma);

        // Get all the turmaList where faixaEtaria equals to DEFAULT_FAIXA_ETARIA
        defaultTurmaShouldBeFound("faixaEtaria.equals=" + DEFAULT_FAIXA_ETARIA);

        // Get all the turmaList where faixaEtaria equals to UPDATED_FAIXA_ETARIA
        defaultTurmaShouldNotBeFound("faixaEtaria.equals=" + UPDATED_FAIXA_ETARIA);
    }

    @Test
    @Transactional
    void getAllTurmasByFaixaEtariaIsInShouldWork() throws Exception {
        // Initialize the database
        turmaRepository.saveAndFlush(turma);

        // Get all the turmaList where faixaEtaria in DEFAULT_FAIXA_ETARIA or UPDATED_FAIXA_ETARIA
        defaultTurmaShouldBeFound("faixaEtaria.in=" + DEFAULT_FAIXA_ETARIA + "," + UPDATED_FAIXA_ETARIA);

        // Get all the turmaList where faixaEtaria equals to UPDATED_FAIXA_ETARIA
        defaultTurmaShouldNotBeFound("faixaEtaria.in=" + UPDATED_FAIXA_ETARIA);
    }

    @Test
    @Transactional
    void getAllTurmasByFaixaEtariaIsNullOrNotNull() throws Exception {
        // Initialize the database
        turmaRepository.saveAndFlush(turma);

        // Get all the turmaList where faixaEtaria is not null
        defaultTurmaShouldBeFound("faixaEtaria.specified=true");

        // Get all the turmaList where faixaEtaria is null
        defaultTurmaShouldNotBeFound("faixaEtaria.specified=false");
    }

    @Test
    @Transactional
    void getAllTurmasByDataInicioIsEqualToSomething() throws Exception {
        // Initialize the database
        turmaRepository.saveAndFlush(turma);

        // Get all the turmaList where dataInicio equals to DEFAULT_DATA_INICIO
        defaultTurmaShouldBeFound("dataInicio.equals=" + DEFAULT_DATA_INICIO);

        // Get all the turmaList where dataInicio equals to UPDATED_DATA_INICIO
        defaultTurmaShouldNotBeFound("dataInicio.equals=" + UPDATED_DATA_INICIO);
    }

    @Test
    @Transactional
    void getAllTurmasByDataInicioIsInShouldWork() throws Exception {
        // Initialize the database
        turmaRepository.saveAndFlush(turma);

        // Get all the turmaList where dataInicio in DEFAULT_DATA_INICIO or UPDATED_DATA_INICIO
        defaultTurmaShouldBeFound("dataInicio.in=" + DEFAULT_DATA_INICIO + "," + UPDATED_DATA_INICIO);

        // Get all the turmaList where dataInicio equals to UPDATED_DATA_INICIO
        defaultTurmaShouldNotBeFound("dataInicio.in=" + UPDATED_DATA_INICIO);
    }

    @Test
    @Transactional
    void getAllTurmasByDataInicioIsNullOrNotNull() throws Exception {
        // Initialize the database
        turmaRepository.saveAndFlush(turma);

        // Get all the turmaList where dataInicio is not null
        defaultTurmaShouldBeFound("dataInicio.specified=true");

        // Get all the turmaList where dataInicio is null
        defaultTurmaShouldNotBeFound("dataInicio.specified=false");
    }

    @Test
    @Transactional
    void getAllTurmasByDataInicioIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        turmaRepository.saveAndFlush(turma);

        // Get all the turmaList where dataInicio is greater than or equal to DEFAULT_DATA_INICIO
        defaultTurmaShouldBeFound("dataInicio.greaterThanOrEqual=" + DEFAULT_DATA_INICIO);

        // Get all the turmaList where dataInicio is greater than or equal to UPDATED_DATA_INICIO
        defaultTurmaShouldNotBeFound("dataInicio.greaterThanOrEqual=" + UPDATED_DATA_INICIO);
    }

    @Test
    @Transactional
    void getAllTurmasByDataInicioIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        turmaRepository.saveAndFlush(turma);

        // Get all the turmaList where dataInicio is less than or equal to DEFAULT_DATA_INICIO
        defaultTurmaShouldBeFound("dataInicio.lessThanOrEqual=" + DEFAULT_DATA_INICIO);

        // Get all the turmaList where dataInicio is less than or equal to SMALLER_DATA_INICIO
        defaultTurmaShouldNotBeFound("dataInicio.lessThanOrEqual=" + SMALLER_DATA_INICIO);
    }

    @Test
    @Transactional
    void getAllTurmasByDataInicioIsLessThanSomething() throws Exception {
        // Initialize the database
        turmaRepository.saveAndFlush(turma);

        // Get all the turmaList where dataInicio is less than DEFAULT_DATA_INICIO
        defaultTurmaShouldNotBeFound("dataInicio.lessThan=" + DEFAULT_DATA_INICIO);

        // Get all the turmaList where dataInicio is less than UPDATED_DATA_INICIO
        defaultTurmaShouldBeFound("dataInicio.lessThan=" + UPDATED_DATA_INICIO);
    }

    @Test
    @Transactional
    void getAllTurmasByDataInicioIsGreaterThanSomething() throws Exception {
        // Initialize the database
        turmaRepository.saveAndFlush(turma);

        // Get all the turmaList where dataInicio is greater than DEFAULT_DATA_INICIO
        defaultTurmaShouldNotBeFound("dataInicio.greaterThan=" + DEFAULT_DATA_INICIO);

        // Get all the turmaList where dataInicio is greater than SMALLER_DATA_INICIO
        defaultTurmaShouldBeFound("dataInicio.greaterThan=" + SMALLER_DATA_INICIO);
    }

    @Test
    @Transactional
    void getAllTurmasByDataFimIsEqualToSomething() throws Exception {
        // Initialize the database
        turmaRepository.saveAndFlush(turma);

        // Get all the turmaList where dataFim equals to DEFAULT_DATA_FIM
        defaultTurmaShouldBeFound("dataFim.equals=" + DEFAULT_DATA_FIM);

        // Get all the turmaList where dataFim equals to UPDATED_DATA_FIM
        defaultTurmaShouldNotBeFound("dataFim.equals=" + UPDATED_DATA_FIM);
    }

    @Test
    @Transactional
    void getAllTurmasByDataFimIsInShouldWork() throws Exception {
        // Initialize the database
        turmaRepository.saveAndFlush(turma);

        // Get all the turmaList where dataFim in DEFAULT_DATA_FIM or UPDATED_DATA_FIM
        defaultTurmaShouldBeFound("dataFim.in=" + DEFAULT_DATA_FIM + "," + UPDATED_DATA_FIM);

        // Get all the turmaList where dataFim equals to UPDATED_DATA_FIM
        defaultTurmaShouldNotBeFound("dataFim.in=" + UPDATED_DATA_FIM);
    }

    @Test
    @Transactional
    void getAllTurmasByDataFimIsNullOrNotNull() throws Exception {
        // Initialize the database
        turmaRepository.saveAndFlush(turma);

        // Get all the turmaList where dataFim is not null
        defaultTurmaShouldBeFound("dataFim.specified=true");

        // Get all the turmaList where dataFim is null
        defaultTurmaShouldNotBeFound("dataFim.specified=false");
    }

    @Test
    @Transactional
    void getAllTurmasByDataFimIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        turmaRepository.saveAndFlush(turma);

        // Get all the turmaList where dataFim is greater than or equal to DEFAULT_DATA_FIM
        defaultTurmaShouldBeFound("dataFim.greaterThanOrEqual=" + DEFAULT_DATA_FIM);

        // Get all the turmaList where dataFim is greater than or equal to UPDATED_DATA_FIM
        defaultTurmaShouldNotBeFound("dataFim.greaterThanOrEqual=" + UPDATED_DATA_FIM);
    }

    @Test
    @Transactional
    void getAllTurmasByDataFimIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        turmaRepository.saveAndFlush(turma);

        // Get all the turmaList where dataFim is less than or equal to DEFAULT_DATA_FIM
        defaultTurmaShouldBeFound("dataFim.lessThanOrEqual=" + DEFAULT_DATA_FIM);

        // Get all the turmaList where dataFim is less than or equal to SMALLER_DATA_FIM
        defaultTurmaShouldNotBeFound("dataFim.lessThanOrEqual=" + SMALLER_DATA_FIM);
    }

    @Test
    @Transactional
    void getAllTurmasByDataFimIsLessThanSomething() throws Exception {
        // Initialize the database
        turmaRepository.saveAndFlush(turma);

        // Get all the turmaList where dataFim is less than DEFAULT_DATA_FIM
        defaultTurmaShouldNotBeFound("dataFim.lessThan=" + DEFAULT_DATA_FIM);

        // Get all the turmaList where dataFim is less than UPDATED_DATA_FIM
        defaultTurmaShouldBeFound("dataFim.lessThan=" + UPDATED_DATA_FIM);
    }

    @Test
    @Transactional
    void getAllTurmasByDataFimIsGreaterThanSomething() throws Exception {
        // Initialize the database
        turmaRepository.saveAndFlush(turma);

        // Get all the turmaList where dataFim is greater than DEFAULT_DATA_FIM
        defaultTurmaShouldNotBeFound("dataFim.greaterThan=" + DEFAULT_DATA_FIM);

        // Get all the turmaList where dataFim is greater than SMALLER_DATA_FIM
        defaultTurmaShouldBeFound("dataFim.greaterThan=" + SMALLER_DATA_FIM);
    }

    @Test
    @Transactional
    void getAllTurmasByDataCadastroIsEqualToSomething() throws Exception {
        // Initialize the database
        turmaRepository.saveAndFlush(turma);

        // Get all the turmaList where dataCadastro equals to DEFAULT_DATA_CADASTRO
        defaultTurmaShouldBeFound("dataCadastro.equals=" + DEFAULT_DATA_CADASTRO);

        // Get all the turmaList where dataCadastro equals to UPDATED_DATA_CADASTRO
        defaultTurmaShouldNotBeFound("dataCadastro.equals=" + UPDATED_DATA_CADASTRO);
    }

    @Test
    @Transactional
    void getAllTurmasByDataCadastroIsInShouldWork() throws Exception {
        // Initialize the database
        turmaRepository.saveAndFlush(turma);

        // Get all the turmaList where dataCadastro in DEFAULT_DATA_CADASTRO or UPDATED_DATA_CADASTRO
        defaultTurmaShouldBeFound("dataCadastro.in=" + DEFAULT_DATA_CADASTRO + "," + UPDATED_DATA_CADASTRO);

        // Get all the turmaList where dataCadastro equals to UPDATED_DATA_CADASTRO
        defaultTurmaShouldNotBeFound("dataCadastro.in=" + UPDATED_DATA_CADASTRO);
    }

    @Test
    @Transactional
    void getAllTurmasByDataCadastroIsNullOrNotNull() throws Exception {
        // Initialize the database
        turmaRepository.saveAndFlush(turma);

        // Get all the turmaList where dataCadastro is not null
        defaultTurmaShouldBeFound("dataCadastro.specified=true");

        // Get all the turmaList where dataCadastro is null
        defaultTurmaShouldNotBeFound("dataCadastro.specified=false");
    }

    @Test
    @Transactional
    void getAllTurmasByDataCadastroIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        turmaRepository.saveAndFlush(turma);

        // Get all the turmaList where dataCadastro is greater than or equal to DEFAULT_DATA_CADASTRO
        defaultTurmaShouldBeFound("dataCadastro.greaterThanOrEqual=" + DEFAULT_DATA_CADASTRO);

        // Get all the turmaList where dataCadastro is greater than or equal to UPDATED_DATA_CADASTRO
        defaultTurmaShouldNotBeFound("dataCadastro.greaterThanOrEqual=" + UPDATED_DATA_CADASTRO);
    }

    @Test
    @Transactional
    void getAllTurmasByDataCadastroIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        turmaRepository.saveAndFlush(turma);

        // Get all the turmaList where dataCadastro is less than or equal to DEFAULT_DATA_CADASTRO
        defaultTurmaShouldBeFound("dataCadastro.lessThanOrEqual=" + DEFAULT_DATA_CADASTRO);

        // Get all the turmaList where dataCadastro is less than or equal to SMALLER_DATA_CADASTRO
        defaultTurmaShouldNotBeFound("dataCadastro.lessThanOrEqual=" + SMALLER_DATA_CADASTRO);
    }

    @Test
    @Transactional
    void getAllTurmasByDataCadastroIsLessThanSomething() throws Exception {
        // Initialize the database
        turmaRepository.saveAndFlush(turma);

        // Get all the turmaList where dataCadastro is less than DEFAULT_DATA_CADASTRO
        defaultTurmaShouldNotBeFound("dataCadastro.lessThan=" + DEFAULT_DATA_CADASTRO);

        // Get all the turmaList where dataCadastro is less than UPDATED_DATA_CADASTRO
        defaultTurmaShouldBeFound("dataCadastro.lessThan=" + UPDATED_DATA_CADASTRO);
    }

    @Test
    @Transactional
    void getAllTurmasByDataCadastroIsGreaterThanSomething() throws Exception {
        // Initialize the database
        turmaRepository.saveAndFlush(turma);

        // Get all the turmaList where dataCadastro is greater than DEFAULT_DATA_CADASTRO
        defaultTurmaShouldNotBeFound("dataCadastro.greaterThan=" + DEFAULT_DATA_CADASTRO);

        // Get all the turmaList where dataCadastro is greater than SMALLER_DATA_CADASTRO
        defaultTurmaShouldBeFound("dataCadastro.greaterThan=" + SMALLER_DATA_CADASTRO);
    }

    @Test
    @Transactional
    void getAllTurmasByProfessorIsEqualToSomething() throws Exception {
        Pessoa professor;
        if (TestUtil.findAll(em, Pessoa.class).isEmpty()) {
            turmaRepository.saveAndFlush(turma);
            professor = PessoaResourceIT.createEntity(em);
        } else {
            professor = TestUtil.findAll(em, Pessoa.class).get(0);
        }
        em.persist(professor);
        em.flush();
        turma.setProfessor(professor);
        turmaRepository.saveAndFlush(turma);
        UUID professorId = professor.getId();

        // Get all the turmaList where professor equals to professorId
        defaultTurmaShouldBeFound("professorId.equals=" + professorId);

        // Get all the turmaList where professor equals to UUID.randomUUID()
        defaultTurmaShouldNotBeFound("professorId.equals=" + UUID.randomUUID());
    }

    @Test
    @Transactional
    void getAllTurmasByAlunosIsEqualToSomething() throws Exception {
        Pessoa alunos;
        if (TestUtil.findAll(em, Pessoa.class).isEmpty()) {
            turmaRepository.saveAndFlush(turma);
            alunos = PessoaResourceIT.createEntity(em);
        } else {
            alunos = TestUtil.findAll(em, Pessoa.class).get(0);
        }
        em.persist(alunos);
        em.flush();
        turma.addAlunos(alunos);
        turmaRepository.saveAndFlush(turma);
        UUID alunosId = alunos.getId();

        // Get all the turmaList where alunos equals to alunosId
        defaultTurmaShouldBeFound("alunosId.equals=" + alunosId);

        // Get all the turmaList where alunos equals to UUID.randomUUID()
        defaultTurmaShouldNotBeFound("alunosId.equals=" + UUID.randomUUID());
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTurmaShouldBeFound(String filter) throws Exception {
        restTurmaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(turma.getId().toString())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].sexoTurma").value(hasItem(DEFAULT_SEXO_TURMA.toString())))
            .andExpect(jsonPath("$.[*].faixaEtaria").value(hasItem(DEFAULT_FAIXA_ETARIA.toString())))
            .andExpect(jsonPath("$.[*].dataInicio").value(hasItem(DEFAULT_DATA_INICIO.toString())))
            .andExpect(jsonPath("$.[*].dataFim").value(hasItem(DEFAULT_DATA_FIM.toString())))
            .andExpect(jsonPath("$.[*].dataCadastro").value(hasItem(sameInstant(DEFAULT_DATA_CADASTRO))));

        // Check, that the count call also returns 1
        restTurmaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTurmaShouldNotBeFound(String filter) throws Exception {
        restTurmaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTurmaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingTurma() throws Exception {
        // Get the turma
        restTurmaMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTurma() throws Exception {
        // Initialize the database
        turmaRepository.saveAndFlush(turma);

        int databaseSizeBeforeUpdate = turmaRepository.findAll().size();

        // Update the turma
        Turma updatedTurma = turmaRepository.findById(turma.getId()).get();
        // Disconnect from session so that the updates on updatedTurma are not directly saved in db
        em.detach(updatedTurma);
        updatedTurma
            .nome(UPDATED_NOME)
            .sexoTurma(UPDATED_SEXO_TURMA)
            .faixaEtaria(UPDATED_FAIXA_ETARIA)
            .dataInicio(UPDATED_DATA_INICIO)
            .dataFim(UPDATED_DATA_FIM)
            .dataCadastro(UPDATED_DATA_CADASTRO);
        TurmaDTO turmaDTO = turmaMapper.toDto(updatedTurma);

        restTurmaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, turmaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(turmaDTO))
            )
            .andExpect(status().isOk());

        // Validate the Turma in the database
        List<Turma> turmaList = turmaRepository.findAll();
        assertThat(turmaList).hasSize(databaseSizeBeforeUpdate);
        Turma testTurma = turmaList.get(turmaList.size() - 1);
        assertThat(testTurma.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testTurma.getSexoTurma()).isEqualTo(UPDATED_SEXO_TURMA);
        assertThat(testTurma.getFaixaEtaria()).isEqualTo(UPDATED_FAIXA_ETARIA);
        assertThat(testTurma.getDataInicio()).isEqualTo(UPDATED_DATA_INICIO);
        assertThat(testTurma.getDataFim()).isEqualTo(UPDATED_DATA_FIM);
        assertThat(testTurma.getDataCadastro()).isEqualTo(UPDATED_DATA_CADASTRO);
    }

    @Test
    @Transactional
    void putNonExistingTurma() throws Exception {
        int databaseSizeBeforeUpdate = turmaRepository.findAll().size();
        turma.setId(UUID.randomUUID());

        // Create the Turma
        TurmaDTO turmaDTO = turmaMapper.toDto(turma);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTurmaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, turmaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(turmaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Turma in the database
        List<Turma> turmaList = turmaRepository.findAll();
        assertThat(turmaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTurma() throws Exception {
        int databaseSizeBeforeUpdate = turmaRepository.findAll().size();
        turma.setId(UUID.randomUUID());

        // Create the Turma
        TurmaDTO turmaDTO = turmaMapper.toDto(turma);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTurmaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(turmaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Turma in the database
        List<Turma> turmaList = turmaRepository.findAll();
        assertThat(turmaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTurma() throws Exception {
        int databaseSizeBeforeUpdate = turmaRepository.findAll().size();
        turma.setId(UUID.randomUUID());

        // Create the Turma
        TurmaDTO turmaDTO = turmaMapper.toDto(turma);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTurmaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(turmaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Turma in the database
        List<Turma> turmaList = turmaRepository.findAll();
        assertThat(turmaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTurmaWithPatch() throws Exception {
        // Initialize the database
        turmaRepository.saveAndFlush(turma);

        int databaseSizeBeforeUpdate = turmaRepository.findAll().size();

        // Update the turma using partial update
        Turma partialUpdatedTurma = new Turma();
        partialUpdatedTurma.setId(turma.getId());

        partialUpdatedTurma.dataCadastro(UPDATED_DATA_CADASTRO);

        restTurmaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTurma.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTurma))
            )
            .andExpect(status().isOk());

        // Validate the Turma in the database
        List<Turma> turmaList = turmaRepository.findAll();
        assertThat(turmaList).hasSize(databaseSizeBeforeUpdate);
        Turma testTurma = turmaList.get(turmaList.size() - 1);
        assertThat(testTurma.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testTurma.getSexoTurma()).isEqualTo(DEFAULT_SEXO_TURMA);
        assertThat(testTurma.getFaixaEtaria()).isEqualTo(DEFAULT_FAIXA_ETARIA);
        assertThat(testTurma.getDataInicio()).isEqualTo(DEFAULT_DATA_INICIO);
        assertThat(testTurma.getDataFim()).isEqualTo(DEFAULT_DATA_FIM);
        assertThat(testTurma.getDataCadastro()).isEqualTo(UPDATED_DATA_CADASTRO);
    }

    @Test
    @Transactional
    void fullUpdateTurmaWithPatch() throws Exception {
        // Initialize the database
        turmaRepository.saveAndFlush(turma);

        int databaseSizeBeforeUpdate = turmaRepository.findAll().size();

        // Update the turma using partial update
        Turma partialUpdatedTurma = new Turma();
        partialUpdatedTurma.setId(turma.getId());

        partialUpdatedTurma
            .nome(UPDATED_NOME)
            .sexoTurma(UPDATED_SEXO_TURMA)
            .faixaEtaria(UPDATED_FAIXA_ETARIA)
            .dataInicio(UPDATED_DATA_INICIO)
            .dataFim(UPDATED_DATA_FIM)
            .dataCadastro(UPDATED_DATA_CADASTRO);

        restTurmaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTurma.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTurma))
            )
            .andExpect(status().isOk());

        // Validate the Turma in the database
        List<Turma> turmaList = turmaRepository.findAll();
        assertThat(turmaList).hasSize(databaseSizeBeforeUpdate);
        Turma testTurma = turmaList.get(turmaList.size() - 1);
        assertThat(testTurma.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testTurma.getSexoTurma()).isEqualTo(UPDATED_SEXO_TURMA);
        assertThat(testTurma.getFaixaEtaria()).isEqualTo(UPDATED_FAIXA_ETARIA);
        assertThat(testTurma.getDataInicio()).isEqualTo(UPDATED_DATA_INICIO);
        assertThat(testTurma.getDataFim()).isEqualTo(UPDATED_DATA_FIM);
        assertThat(testTurma.getDataCadastro()).isEqualTo(UPDATED_DATA_CADASTRO);
    }

    @Test
    @Transactional
    void patchNonExistingTurma() throws Exception {
        int databaseSizeBeforeUpdate = turmaRepository.findAll().size();
        turma.setId(UUID.randomUUID());

        // Create the Turma
        TurmaDTO turmaDTO = turmaMapper.toDto(turma);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTurmaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, turmaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(turmaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Turma in the database
        List<Turma> turmaList = turmaRepository.findAll();
        assertThat(turmaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTurma() throws Exception {
        int databaseSizeBeforeUpdate = turmaRepository.findAll().size();
        turma.setId(UUID.randomUUID());

        // Create the Turma
        TurmaDTO turmaDTO = turmaMapper.toDto(turma);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTurmaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(turmaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Turma in the database
        List<Turma> turmaList = turmaRepository.findAll();
        assertThat(turmaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTurma() throws Exception {
        int databaseSizeBeforeUpdate = turmaRepository.findAll().size();
        turma.setId(UUID.randomUUID());

        // Create the Turma
        TurmaDTO turmaDTO = turmaMapper.toDto(turma);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTurmaMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(turmaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Turma in the database
        List<Turma> turmaList = turmaRepository.findAll();
        assertThat(turmaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTurma() throws Exception {
        // Initialize the database
        turmaRepository.saveAndFlush(turma);

        int databaseSizeBeforeDelete = turmaRepository.findAll().size();

        // Delete the turma
        restTurmaMockMvc
            .perform(delete(ENTITY_API_URL_ID, turma.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Turma> turmaList = turmaRepository.findAll();
        assertThat(turmaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

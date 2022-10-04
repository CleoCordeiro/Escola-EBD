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
import br.com.cleo.escolaebd.domain.enumeration.Sexo;
import br.com.cleo.escolaebd.repository.ResponsavelRepository;
import br.com.cleo.escolaebd.service.ResponsavelService;
import br.com.cleo.escolaebd.service.criteria.ResponsavelCriteria;
import br.com.cleo.escolaebd.service.dto.ResponsavelDTO;
import br.com.cleo.escolaebd.service.mapper.ResponsavelMapper;
import java.time.Instant;
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
 * Integration tests for the {@link ResponsavelResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ResponsavelResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_CPF = "AAAAAAAAAA";
    private static final String UPDATED_CPF = "BBBBBBBBBB";

    private static final Sexo DEFAULT_SEXO = Sexo.MASCULINO;
    private static final Sexo UPDATED_SEXO = Sexo.FEMININO;

    private static final String DEFAULT_PARENTESCO = "AAAAAAAAAA";
    private static final String UPDATED_PARENTESCO = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATA_CADASTRO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATA_CADASTRO = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_DATA_CADASTRO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final String ENTITY_API_URL = "/api/responsavels";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ResponsavelRepository responsavelRepository;

    @Mock
    private ResponsavelRepository responsavelRepositoryMock;

    @Autowired
    private ResponsavelMapper responsavelMapper;

    @Mock
    private ResponsavelService responsavelServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restResponsavelMockMvc;

    private Responsavel responsavel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Responsavel createEntity(EntityManager em) {
        Responsavel responsavel = new Responsavel()
            .nome(DEFAULT_NOME)
            .cpf(DEFAULT_CPF)
            .sexo(DEFAULT_SEXO)
            .parentesco(DEFAULT_PARENTESCO)
            .dataCadastro(DEFAULT_DATA_CADASTRO);
        return responsavel;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Responsavel createUpdatedEntity(EntityManager em) {
        Responsavel responsavel = new Responsavel()
            .nome(UPDATED_NOME)
            .cpf(UPDATED_CPF)
            .sexo(UPDATED_SEXO)
            .parentesco(UPDATED_PARENTESCO)
            .dataCadastro(UPDATED_DATA_CADASTRO);
        return responsavel;
    }

    @BeforeEach
    public void initTest() {
        responsavel = createEntity(em);
    }

    @Test
    @Transactional
    void createResponsavel() throws Exception {
        int databaseSizeBeforeCreate = responsavelRepository.findAll().size();
        // Create the Responsavel
        ResponsavelDTO responsavelDTO = responsavelMapper.toDto(responsavel);
        restResponsavelMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(responsavelDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Responsavel in the database
        List<Responsavel> responsavelList = responsavelRepository.findAll();
        assertThat(responsavelList).hasSize(databaseSizeBeforeCreate + 1);
        Responsavel testResponsavel = responsavelList.get(responsavelList.size() - 1);
        assertThat(testResponsavel.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testResponsavel.getCpf()).isEqualTo(DEFAULT_CPF);
        assertThat(testResponsavel.getSexo()).isEqualTo(DEFAULT_SEXO);
        assertThat(testResponsavel.getParentesco()).isEqualTo(DEFAULT_PARENTESCO);
        assertThat(testResponsavel.getDataCadastro()).isEqualTo(DEFAULT_DATA_CADASTRO);
    }

    @Test
    @Transactional
    void createResponsavelWithExistingId() throws Exception {
        // Create the Responsavel with an existing ID
        responsavelRepository.saveAndFlush(responsavel);
        ResponsavelDTO responsavelDTO = responsavelMapper.toDto(responsavel);

        int databaseSizeBeforeCreate = responsavelRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restResponsavelMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(responsavelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Responsavel in the database
        List<Responsavel> responsavelList = responsavelRepository.findAll();
        assertThat(responsavelList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = responsavelRepository.findAll().size();
        // set the field null
        responsavel.setNome(null);

        // Create the Responsavel, which fails.
        ResponsavelDTO responsavelDTO = responsavelMapper.toDto(responsavel);

        restResponsavelMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(responsavelDTO))
            )
            .andExpect(status().isBadRequest());

        List<Responsavel> responsavelList = responsavelRepository.findAll();
        assertThat(responsavelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCpfIsRequired() throws Exception {
        int databaseSizeBeforeTest = responsavelRepository.findAll().size();
        // set the field null
        responsavel.setCpf(null);

        // Create the Responsavel, which fails.
        ResponsavelDTO responsavelDTO = responsavelMapper.toDto(responsavel);

        restResponsavelMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(responsavelDTO))
            )
            .andExpect(status().isBadRequest());

        List<Responsavel> responsavelList = responsavelRepository.findAll();
        assertThat(responsavelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSexoIsRequired() throws Exception {
        int databaseSizeBeforeTest = responsavelRepository.findAll().size();
        // set the field null
        responsavel.setSexo(null);

        // Create the Responsavel, which fails.
        ResponsavelDTO responsavelDTO = responsavelMapper.toDto(responsavel);

        restResponsavelMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(responsavelDTO))
            )
            .andExpect(status().isBadRequest());

        List<Responsavel> responsavelList = responsavelRepository.findAll();
        assertThat(responsavelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkParentescoIsRequired() throws Exception {
        int databaseSizeBeforeTest = responsavelRepository.findAll().size();
        // set the field null
        responsavel.setParentesco(null);

        // Create the Responsavel, which fails.
        ResponsavelDTO responsavelDTO = responsavelMapper.toDto(responsavel);

        restResponsavelMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(responsavelDTO))
            )
            .andExpect(status().isBadRequest());

        List<Responsavel> responsavelList = responsavelRepository.findAll();
        assertThat(responsavelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllResponsavels() throws Exception {
        // Initialize the database
        responsavelRepository.saveAndFlush(responsavel);

        // Get all the responsavelList
        restResponsavelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(responsavel.getId().toString())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].cpf").value(hasItem(DEFAULT_CPF)))
            .andExpect(jsonPath("$.[*].sexo").value(hasItem(DEFAULT_SEXO.toString())))
            .andExpect(jsonPath("$.[*].parentesco").value(hasItem(DEFAULT_PARENTESCO)))
            .andExpect(jsonPath("$.[*].dataCadastro").value(hasItem(sameInstant(DEFAULT_DATA_CADASTRO))));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllResponsavelsWithEagerRelationshipsIsEnabled() throws Exception {
        when(responsavelServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restResponsavelMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(responsavelServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllResponsavelsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(responsavelServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restResponsavelMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(responsavelRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getResponsavel() throws Exception {
        // Initialize the database
        responsavelRepository.saveAndFlush(responsavel);

        // Get the responsavel
        restResponsavelMockMvc
            .perform(get(ENTITY_API_URL_ID, responsavel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(responsavel.getId().toString()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.cpf").value(DEFAULT_CPF))
            .andExpect(jsonPath("$.sexo").value(DEFAULT_SEXO.toString()))
            .andExpect(jsonPath("$.parentesco").value(DEFAULT_PARENTESCO))
            .andExpect(jsonPath("$.dataCadastro").value(sameInstant(DEFAULT_DATA_CADASTRO)));
    }

    @Test
    @Transactional
    void getResponsavelsByIdFiltering() throws Exception {
        // Initialize the database
        responsavelRepository.saveAndFlush(responsavel);

        UUID id = responsavel.getId();

        defaultResponsavelShouldBeFound("id.equals=" + id);
        defaultResponsavelShouldNotBeFound("id.notEquals=" + id);
    }

    @Test
    @Transactional
    void getAllResponsavelsByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        responsavelRepository.saveAndFlush(responsavel);

        // Get all the responsavelList where nome equals to DEFAULT_NOME
        defaultResponsavelShouldBeFound("nome.equals=" + DEFAULT_NOME);

        // Get all the responsavelList where nome equals to UPDATED_NOME
        defaultResponsavelShouldNotBeFound("nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllResponsavelsByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        responsavelRepository.saveAndFlush(responsavel);

        // Get all the responsavelList where nome in DEFAULT_NOME or UPDATED_NOME
        defaultResponsavelShouldBeFound("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME);

        // Get all the responsavelList where nome equals to UPDATED_NOME
        defaultResponsavelShouldNotBeFound("nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllResponsavelsByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        responsavelRepository.saveAndFlush(responsavel);

        // Get all the responsavelList where nome is not null
        defaultResponsavelShouldBeFound("nome.specified=true");

        // Get all the responsavelList where nome is null
        defaultResponsavelShouldNotBeFound("nome.specified=false");
    }

    @Test
    @Transactional
    void getAllResponsavelsByNomeContainsSomething() throws Exception {
        // Initialize the database
        responsavelRepository.saveAndFlush(responsavel);

        // Get all the responsavelList where nome contains DEFAULT_NOME
        defaultResponsavelShouldBeFound("nome.contains=" + DEFAULT_NOME);

        // Get all the responsavelList where nome contains UPDATED_NOME
        defaultResponsavelShouldNotBeFound("nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllResponsavelsByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        responsavelRepository.saveAndFlush(responsavel);

        // Get all the responsavelList where nome does not contain DEFAULT_NOME
        defaultResponsavelShouldNotBeFound("nome.doesNotContain=" + DEFAULT_NOME);

        // Get all the responsavelList where nome does not contain UPDATED_NOME
        defaultResponsavelShouldBeFound("nome.doesNotContain=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllResponsavelsByCpfIsEqualToSomething() throws Exception {
        // Initialize the database
        responsavelRepository.saveAndFlush(responsavel);

        // Get all the responsavelList where cpf equals to DEFAULT_CPF
        defaultResponsavelShouldBeFound("cpf.equals=" + DEFAULT_CPF);

        // Get all the responsavelList where cpf equals to UPDATED_CPF
        defaultResponsavelShouldNotBeFound("cpf.equals=" + UPDATED_CPF);
    }

    @Test
    @Transactional
    void getAllResponsavelsByCpfIsInShouldWork() throws Exception {
        // Initialize the database
        responsavelRepository.saveAndFlush(responsavel);

        // Get all the responsavelList where cpf in DEFAULT_CPF or UPDATED_CPF
        defaultResponsavelShouldBeFound("cpf.in=" + DEFAULT_CPF + "," + UPDATED_CPF);

        // Get all the responsavelList where cpf equals to UPDATED_CPF
        defaultResponsavelShouldNotBeFound("cpf.in=" + UPDATED_CPF);
    }

    @Test
    @Transactional
    void getAllResponsavelsByCpfIsNullOrNotNull() throws Exception {
        // Initialize the database
        responsavelRepository.saveAndFlush(responsavel);

        // Get all the responsavelList where cpf is not null
        defaultResponsavelShouldBeFound("cpf.specified=true");

        // Get all the responsavelList where cpf is null
        defaultResponsavelShouldNotBeFound("cpf.specified=false");
    }

    @Test
    @Transactional
    void getAllResponsavelsByCpfContainsSomething() throws Exception {
        // Initialize the database
        responsavelRepository.saveAndFlush(responsavel);

        // Get all the responsavelList where cpf contains DEFAULT_CPF
        defaultResponsavelShouldBeFound("cpf.contains=" + DEFAULT_CPF);

        // Get all the responsavelList where cpf contains UPDATED_CPF
        defaultResponsavelShouldNotBeFound("cpf.contains=" + UPDATED_CPF);
    }

    @Test
    @Transactional
    void getAllResponsavelsByCpfNotContainsSomething() throws Exception {
        // Initialize the database
        responsavelRepository.saveAndFlush(responsavel);

        // Get all the responsavelList where cpf does not contain DEFAULT_CPF
        defaultResponsavelShouldNotBeFound("cpf.doesNotContain=" + DEFAULT_CPF);

        // Get all the responsavelList where cpf does not contain UPDATED_CPF
        defaultResponsavelShouldBeFound("cpf.doesNotContain=" + UPDATED_CPF);
    }

    @Test
    @Transactional
    void getAllResponsavelsBySexoIsEqualToSomething() throws Exception {
        // Initialize the database
        responsavelRepository.saveAndFlush(responsavel);

        // Get all the responsavelList where sexo equals to DEFAULT_SEXO
        defaultResponsavelShouldBeFound("sexo.equals=" + DEFAULT_SEXO);

        // Get all the responsavelList where sexo equals to UPDATED_SEXO
        defaultResponsavelShouldNotBeFound("sexo.equals=" + UPDATED_SEXO);
    }

    @Test
    @Transactional
    void getAllResponsavelsBySexoIsInShouldWork() throws Exception {
        // Initialize the database
        responsavelRepository.saveAndFlush(responsavel);

        // Get all the responsavelList where sexo in DEFAULT_SEXO or UPDATED_SEXO
        defaultResponsavelShouldBeFound("sexo.in=" + DEFAULT_SEXO + "," + UPDATED_SEXO);

        // Get all the responsavelList where sexo equals to UPDATED_SEXO
        defaultResponsavelShouldNotBeFound("sexo.in=" + UPDATED_SEXO);
    }

    @Test
    @Transactional
    void getAllResponsavelsBySexoIsNullOrNotNull() throws Exception {
        // Initialize the database
        responsavelRepository.saveAndFlush(responsavel);

        // Get all the responsavelList where sexo is not null
        defaultResponsavelShouldBeFound("sexo.specified=true");

        // Get all the responsavelList where sexo is null
        defaultResponsavelShouldNotBeFound("sexo.specified=false");
    }

    @Test
    @Transactional
    void getAllResponsavelsByParentescoIsEqualToSomething() throws Exception {
        // Initialize the database
        responsavelRepository.saveAndFlush(responsavel);

        // Get all the responsavelList where parentesco equals to DEFAULT_PARENTESCO
        defaultResponsavelShouldBeFound("parentesco.equals=" + DEFAULT_PARENTESCO);

        // Get all the responsavelList where parentesco equals to UPDATED_PARENTESCO
        defaultResponsavelShouldNotBeFound("parentesco.equals=" + UPDATED_PARENTESCO);
    }

    @Test
    @Transactional
    void getAllResponsavelsByParentescoIsInShouldWork() throws Exception {
        // Initialize the database
        responsavelRepository.saveAndFlush(responsavel);

        // Get all the responsavelList where parentesco in DEFAULT_PARENTESCO or UPDATED_PARENTESCO
        defaultResponsavelShouldBeFound("parentesco.in=" + DEFAULT_PARENTESCO + "," + UPDATED_PARENTESCO);

        // Get all the responsavelList where parentesco equals to UPDATED_PARENTESCO
        defaultResponsavelShouldNotBeFound("parentesco.in=" + UPDATED_PARENTESCO);
    }

    @Test
    @Transactional
    void getAllResponsavelsByParentescoIsNullOrNotNull() throws Exception {
        // Initialize the database
        responsavelRepository.saveAndFlush(responsavel);

        // Get all the responsavelList where parentesco is not null
        defaultResponsavelShouldBeFound("parentesco.specified=true");

        // Get all the responsavelList where parentesco is null
        defaultResponsavelShouldNotBeFound("parentesco.specified=false");
    }

    @Test
    @Transactional
    void getAllResponsavelsByParentescoContainsSomething() throws Exception {
        // Initialize the database
        responsavelRepository.saveAndFlush(responsavel);

        // Get all the responsavelList where parentesco contains DEFAULT_PARENTESCO
        defaultResponsavelShouldBeFound("parentesco.contains=" + DEFAULT_PARENTESCO);

        // Get all the responsavelList where parentesco contains UPDATED_PARENTESCO
        defaultResponsavelShouldNotBeFound("parentesco.contains=" + UPDATED_PARENTESCO);
    }

    @Test
    @Transactional
    void getAllResponsavelsByParentescoNotContainsSomething() throws Exception {
        // Initialize the database
        responsavelRepository.saveAndFlush(responsavel);

        // Get all the responsavelList where parentesco does not contain DEFAULT_PARENTESCO
        defaultResponsavelShouldNotBeFound("parentesco.doesNotContain=" + DEFAULT_PARENTESCO);

        // Get all the responsavelList where parentesco does not contain UPDATED_PARENTESCO
        defaultResponsavelShouldBeFound("parentesco.doesNotContain=" + UPDATED_PARENTESCO);
    }

    @Test
    @Transactional
    void getAllResponsavelsByDataCadastroIsEqualToSomething() throws Exception {
        // Initialize the database
        responsavelRepository.saveAndFlush(responsavel);

        // Get all the responsavelList where dataCadastro equals to DEFAULT_DATA_CADASTRO
        defaultResponsavelShouldBeFound("dataCadastro.equals=" + DEFAULT_DATA_CADASTRO);

        // Get all the responsavelList where dataCadastro equals to UPDATED_DATA_CADASTRO
        defaultResponsavelShouldNotBeFound("dataCadastro.equals=" + UPDATED_DATA_CADASTRO);
    }

    @Test
    @Transactional
    void getAllResponsavelsByDataCadastroIsInShouldWork() throws Exception {
        // Initialize the database
        responsavelRepository.saveAndFlush(responsavel);

        // Get all the responsavelList where dataCadastro in DEFAULT_DATA_CADASTRO or UPDATED_DATA_CADASTRO
        defaultResponsavelShouldBeFound("dataCadastro.in=" + DEFAULT_DATA_CADASTRO + "," + UPDATED_DATA_CADASTRO);

        // Get all the responsavelList where dataCadastro equals to UPDATED_DATA_CADASTRO
        defaultResponsavelShouldNotBeFound("dataCadastro.in=" + UPDATED_DATA_CADASTRO);
    }

    @Test
    @Transactional
    void getAllResponsavelsByDataCadastroIsNullOrNotNull() throws Exception {
        // Initialize the database
        responsavelRepository.saveAndFlush(responsavel);

        // Get all the responsavelList where dataCadastro is not null
        defaultResponsavelShouldBeFound("dataCadastro.specified=true");

        // Get all the responsavelList where dataCadastro is null
        defaultResponsavelShouldNotBeFound("dataCadastro.specified=false");
    }

    @Test
    @Transactional
    void getAllResponsavelsByDataCadastroIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        responsavelRepository.saveAndFlush(responsavel);

        // Get all the responsavelList where dataCadastro is greater than or equal to DEFAULT_DATA_CADASTRO
        defaultResponsavelShouldBeFound("dataCadastro.greaterThanOrEqual=" + DEFAULT_DATA_CADASTRO);

        // Get all the responsavelList where dataCadastro is greater than or equal to UPDATED_DATA_CADASTRO
        defaultResponsavelShouldNotBeFound("dataCadastro.greaterThanOrEqual=" + UPDATED_DATA_CADASTRO);
    }

    @Test
    @Transactional
    void getAllResponsavelsByDataCadastroIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        responsavelRepository.saveAndFlush(responsavel);

        // Get all the responsavelList where dataCadastro is less than or equal to DEFAULT_DATA_CADASTRO
        defaultResponsavelShouldBeFound("dataCadastro.lessThanOrEqual=" + DEFAULT_DATA_CADASTRO);

        // Get all the responsavelList where dataCadastro is less than or equal to SMALLER_DATA_CADASTRO
        defaultResponsavelShouldNotBeFound("dataCadastro.lessThanOrEqual=" + SMALLER_DATA_CADASTRO);
    }

    @Test
    @Transactional
    void getAllResponsavelsByDataCadastroIsLessThanSomething() throws Exception {
        // Initialize the database
        responsavelRepository.saveAndFlush(responsavel);

        // Get all the responsavelList where dataCadastro is less than DEFAULT_DATA_CADASTRO
        defaultResponsavelShouldNotBeFound("dataCadastro.lessThan=" + DEFAULT_DATA_CADASTRO);

        // Get all the responsavelList where dataCadastro is less than UPDATED_DATA_CADASTRO
        defaultResponsavelShouldBeFound("dataCadastro.lessThan=" + UPDATED_DATA_CADASTRO);
    }

    @Test
    @Transactional
    void getAllResponsavelsByDataCadastroIsGreaterThanSomething() throws Exception {
        // Initialize the database
        responsavelRepository.saveAndFlush(responsavel);

        // Get all the responsavelList where dataCadastro is greater than DEFAULT_DATA_CADASTRO
        defaultResponsavelShouldNotBeFound("dataCadastro.greaterThan=" + DEFAULT_DATA_CADASTRO);

        // Get all the responsavelList where dataCadastro is greater than SMALLER_DATA_CADASTRO
        defaultResponsavelShouldBeFound("dataCadastro.greaterThan=" + SMALLER_DATA_CADASTRO);
    }

    @Test
    @Transactional
    void getAllResponsavelsByTelefonesIsEqualToSomething() throws Exception {
        Telefone telefones;
        if (TestUtil.findAll(em, Telefone.class).isEmpty()) {
            responsavelRepository.saveAndFlush(responsavel);
            telefones = TelefoneResourceIT.createEntity(em);
        } else {
            telefones = TestUtil.findAll(em, Telefone.class).get(0);
        }
        em.persist(telefones);
        em.flush();
        responsavel.addTelefones(telefones);
        responsavelRepository.saveAndFlush(responsavel);
        UUID telefonesId = telefones.getId();

        // Get all the responsavelList where telefones equals to telefonesId
        defaultResponsavelShouldBeFound("telefonesId.equals=" + telefonesId);

        // Get all the responsavelList where telefones equals to UUID.randomUUID()
        defaultResponsavelShouldNotBeFound("telefonesId.equals=" + UUID.randomUUID());
    }

    @Test
    @Transactional
    void getAllResponsavelsByEnderecosIsEqualToSomething() throws Exception {
        Endereco enderecos;
        if (TestUtil.findAll(em, Endereco.class).isEmpty()) {
            responsavelRepository.saveAndFlush(responsavel);
            enderecos = EnderecoResourceIT.createEntity(em);
        } else {
            enderecos = TestUtil.findAll(em, Endereco.class).get(0);
        }
        em.persist(enderecos);
        em.flush();
        responsavel.addEnderecos(enderecos);
        responsavelRepository.saveAndFlush(responsavel);
        UUID enderecosId = enderecos.getId();

        // Get all the responsavelList where enderecos equals to enderecosId
        defaultResponsavelShouldBeFound("enderecosId.equals=" + enderecosId);

        // Get all the responsavelList where enderecos equals to UUID.randomUUID()
        defaultResponsavelShouldNotBeFound("enderecosId.equals=" + UUID.randomUUID());
    }

    @Test
    @Transactional
    void getAllResponsavelsByAlunosIsEqualToSomething() throws Exception {
        Pessoa alunos;
        if (TestUtil.findAll(em, Pessoa.class).isEmpty()) {
            responsavelRepository.saveAndFlush(responsavel);
            alunos = PessoaResourceIT.createEntity(em);
        } else {
            alunos = TestUtil.findAll(em, Pessoa.class).get(0);
        }
        em.persist(alunos);
        em.flush();
        responsavel.addAlunos(alunos);
        responsavelRepository.saveAndFlush(responsavel);
        UUID alunosId = alunos.getId();

        // Get all the responsavelList where alunos equals to alunosId
        defaultResponsavelShouldBeFound("alunosId.equals=" + alunosId);

        // Get all the responsavelList where alunos equals to UUID.randomUUID()
        defaultResponsavelShouldNotBeFound("alunosId.equals=" + UUID.randomUUID());
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultResponsavelShouldBeFound(String filter) throws Exception {
        restResponsavelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(responsavel.getId().toString())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].cpf").value(hasItem(DEFAULT_CPF)))
            .andExpect(jsonPath("$.[*].sexo").value(hasItem(DEFAULT_SEXO.toString())))
            .andExpect(jsonPath("$.[*].parentesco").value(hasItem(DEFAULT_PARENTESCO)))
            .andExpect(jsonPath("$.[*].dataCadastro").value(hasItem(sameInstant(DEFAULT_DATA_CADASTRO))));

        // Check, that the count call also returns 1
        restResponsavelMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultResponsavelShouldNotBeFound(String filter) throws Exception {
        restResponsavelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restResponsavelMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingResponsavel() throws Exception {
        // Get the responsavel
        restResponsavelMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingResponsavel() throws Exception {
        // Initialize the database
        responsavelRepository.saveAndFlush(responsavel);

        int databaseSizeBeforeUpdate = responsavelRepository.findAll().size();

        // Update the responsavel
        Responsavel updatedResponsavel = responsavelRepository.findById(responsavel.getId()).get();
        // Disconnect from session so that the updates on updatedResponsavel are not directly saved in db
        em.detach(updatedResponsavel);
        updatedResponsavel
            .nome(UPDATED_NOME)
            .cpf(UPDATED_CPF)
            .sexo(UPDATED_SEXO)
            .parentesco(UPDATED_PARENTESCO)
            .dataCadastro(UPDATED_DATA_CADASTRO);
        ResponsavelDTO responsavelDTO = responsavelMapper.toDto(updatedResponsavel);

        restResponsavelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, responsavelDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(responsavelDTO))
            )
            .andExpect(status().isOk());

        // Validate the Responsavel in the database
        List<Responsavel> responsavelList = responsavelRepository.findAll();
        assertThat(responsavelList).hasSize(databaseSizeBeforeUpdate);
        Responsavel testResponsavel = responsavelList.get(responsavelList.size() - 1);
        assertThat(testResponsavel.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testResponsavel.getCpf()).isEqualTo(UPDATED_CPF);
        assertThat(testResponsavel.getSexo()).isEqualTo(UPDATED_SEXO);
        assertThat(testResponsavel.getParentesco()).isEqualTo(UPDATED_PARENTESCO);
        assertThat(testResponsavel.getDataCadastro()).isEqualTo(UPDATED_DATA_CADASTRO);
    }

    @Test
    @Transactional
    void putNonExistingResponsavel() throws Exception {
        int databaseSizeBeforeUpdate = responsavelRepository.findAll().size();
        responsavel.setId(UUID.randomUUID());

        // Create the Responsavel
        ResponsavelDTO responsavelDTO = responsavelMapper.toDto(responsavel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResponsavelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, responsavelDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(responsavelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Responsavel in the database
        List<Responsavel> responsavelList = responsavelRepository.findAll();
        assertThat(responsavelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchResponsavel() throws Exception {
        int databaseSizeBeforeUpdate = responsavelRepository.findAll().size();
        responsavel.setId(UUID.randomUUID());

        // Create the Responsavel
        ResponsavelDTO responsavelDTO = responsavelMapper.toDto(responsavel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResponsavelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(responsavelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Responsavel in the database
        List<Responsavel> responsavelList = responsavelRepository.findAll();
        assertThat(responsavelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamResponsavel() throws Exception {
        int databaseSizeBeforeUpdate = responsavelRepository.findAll().size();
        responsavel.setId(UUID.randomUUID());

        // Create the Responsavel
        ResponsavelDTO responsavelDTO = responsavelMapper.toDto(responsavel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResponsavelMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(responsavelDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Responsavel in the database
        List<Responsavel> responsavelList = responsavelRepository.findAll();
        assertThat(responsavelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateResponsavelWithPatch() throws Exception {
        // Initialize the database
        responsavelRepository.saveAndFlush(responsavel);

        int databaseSizeBeforeUpdate = responsavelRepository.findAll().size();

        // Update the responsavel using partial update
        Responsavel partialUpdatedResponsavel = new Responsavel();
        partialUpdatedResponsavel.setId(responsavel.getId());

        partialUpdatedResponsavel.sexo(UPDATED_SEXO).parentesco(UPDATED_PARENTESCO).dataCadastro(UPDATED_DATA_CADASTRO);

        restResponsavelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedResponsavel.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedResponsavel))
            )
            .andExpect(status().isOk());

        // Validate the Responsavel in the database
        List<Responsavel> responsavelList = responsavelRepository.findAll();
        assertThat(responsavelList).hasSize(databaseSizeBeforeUpdate);
        Responsavel testResponsavel = responsavelList.get(responsavelList.size() - 1);
        assertThat(testResponsavel.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testResponsavel.getCpf()).isEqualTo(DEFAULT_CPF);
        assertThat(testResponsavel.getSexo()).isEqualTo(UPDATED_SEXO);
        assertThat(testResponsavel.getParentesco()).isEqualTo(UPDATED_PARENTESCO);
        assertThat(testResponsavel.getDataCadastro()).isEqualTo(UPDATED_DATA_CADASTRO);
    }

    @Test
    @Transactional
    void fullUpdateResponsavelWithPatch() throws Exception {
        // Initialize the database
        responsavelRepository.saveAndFlush(responsavel);

        int databaseSizeBeforeUpdate = responsavelRepository.findAll().size();

        // Update the responsavel using partial update
        Responsavel partialUpdatedResponsavel = new Responsavel();
        partialUpdatedResponsavel.setId(responsavel.getId());

        partialUpdatedResponsavel
            .nome(UPDATED_NOME)
            .cpf(UPDATED_CPF)
            .sexo(UPDATED_SEXO)
            .parentesco(UPDATED_PARENTESCO)
            .dataCadastro(UPDATED_DATA_CADASTRO);

        restResponsavelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedResponsavel.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedResponsavel))
            )
            .andExpect(status().isOk());

        // Validate the Responsavel in the database
        List<Responsavel> responsavelList = responsavelRepository.findAll();
        assertThat(responsavelList).hasSize(databaseSizeBeforeUpdate);
        Responsavel testResponsavel = responsavelList.get(responsavelList.size() - 1);
        assertThat(testResponsavel.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testResponsavel.getCpf()).isEqualTo(UPDATED_CPF);
        assertThat(testResponsavel.getSexo()).isEqualTo(UPDATED_SEXO);
        assertThat(testResponsavel.getParentesco()).isEqualTo(UPDATED_PARENTESCO);
        assertThat(testResponsavel.getDataCadastro()).isEqualTo(UPDATED_DATA_CADASTRO);
    }

    @Test
    @Transactional
    void patchNonExistingResponsavel() throws Exception {
        int databaseSizeBeforeUpdate = responsavelRepository.findAll().size();
        responsavel.setId(UUID.randomUUID());

        // Create the Responsavel
        ResponsavelDTO responsavelDTO = responsavelMapper.toDto(responsavel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResponsavelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, responsavelDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(responsavelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Responsavel in the database
        List<Responsavel> responsavelList = responsavelRepository.findAll();
        assertThat(responsavelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchResponsavel() throws Exception {
        int databaseSizeBeforeUpdate = responsavelRepository.findAll().size();
        responsavel.setId(UUID.randomUUID());

        // Create the Responsavel
        ResponsavelDTO responsavelDTO = responsavelMapper.toDto(responsavel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResponsavelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(responsavelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Responsavel in the database
        List<Responsavel> responsavelList = responsavelRepository.findAll();
        assertThat(responsavelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamResponsavel() throws Exception {
        int databaseSizeBeforeUpdate = responsavelRepository.findAll().size();
        responsavel.setId(UUID.randomUUID());

        // Create the Responsavel
        ResponsavelDTO responsavelDTO = responsavelMapper.toDto(responsavel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResponsavelMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(responsavelDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Responsavel in the database
        List<Responsavel> responsavelList = responsavelRepository.findAll();
        assertThat(responsavelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteResponsavel() throws Exception {
        // Initialize the database
        responsavelRepository.saveAndFlush(responsavel);

        int databaseSizeBeforeDelete = responsavelRepository.findAll().size();

        // Delete the responsavel
        restResponsavelMockMvc
            .perform(delete(ENTITY_API_URL_ID, responsavel.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Responsavel> responsavelList = responsavelRepository.findAll();
        assertThat(responsavelList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

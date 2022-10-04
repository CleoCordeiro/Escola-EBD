package br.com.cleo.escolaebd.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.cleo.escolaebd.IntegrationTest;
import br.com.cleo.escolaebd.domain.Pessoa;
import br.com.cleo.escolaebd.domain.Responsavel;
import br.com.cleo.escolaebd.domain.Telefone;
import br.com.cleo.escolaebd.repository.TelefoneRepository;
import br.com.cleo.escolaebd.service.criteria.TelefoneCriteria;
import br.com.cleo.escolaebd.service.dto.TelefoneDTO;
import br.com.cleo.escolaebd.service.mapper.TelefoneMapper;
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
 * Integration tests for the {@link TelefoneResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TelefoneResourceIT {

    private static final String DEFAULT_NUMERO = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_WHATSAPP = false;
    private static final Boolean UPDATED_WHATSAPP = true;

    private static final String ENTITY_API_URL = "/api/telefones";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private TelefoneRepository telefoneRepository;

    @Autowired
    private TelefoneMapper telefoneMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTelefoneMockMvc;

    private Telefone telefone;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Telefone createEntity(EntityManager em) {
        Telefone telefone = new Telefone().numero(DEFAULT_NUMERO).whatsapp(DEFAULT_WHATSAPP);
        return telefone;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Telefone createUpdatedEntity(EntityManager em) {
        Telefone telefone = new Telefone().numero(UPDATED_NUMERO).whatsapp(UPDATED_WHATSAPP);
        return telefone;
    }

    @BeforeEach
    public void initTest() {
        telefone = createEntity(em);
    }

    @Test
    @Transactional
    void createTelefone() throws Exception {
        int databaseSizeBeforeCreate = telefoneRepository.findAll().size();
        // Create the Telefone
        TelefoneDTO telefoneDTO = telefoneMapper.toDto(telefone);
        restTelefoneMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(telefoneDTO)))
            .andExpect(status().isCreated());

        // Validate the Telefone in the database
        List<Telefone> telefoneList = telefoneRepository.findAll();
        assertThat(telefoneList).hasSize(databaseSizeBeforeCreate + 1);
        Telefone testTelefone = telefoneList.get(telefoneList.size() - 1);
        assertThat(testTelefone.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testTelefone.getWhatsapp()).isEqualTo(DEFAULT_WHATSAPP);
    }

    @Test
    @Transactional
    void createTelefoneWithExistingId() throws Exception {
        // Create the Telefone with an existing ID
        telefoneRepository.saveAndFlush(telefone);
        TelefoneDTO telefoneDTO = telefoneMapper.toDto(telefone);

        int databaseSizeBeforeCreate = telefoneRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTelefoneMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(telefoneDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Telefone in the database
        List<Telefone> telefoneList = telefoneRepository.findAll();
        assertThat(telefoneList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNumeroIsRequired() throws Exception {
        int databaseSizeBeforeTest = telefoneRepository.findAll().size();
        // set the field null
        telefone.setNumero(null);

        // Create the Telefone, which fails.
        TelefoneDTO telefoneDTO = telefoneMapper.toDto(telefone);

        restTelefoneMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(telefoneDTO)))
            .andExpect(status().isBadRequest());

        List<Telefone> telefoneList = telefoneRepository.findAll();
        assertThat(telefoneList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkWhatsappIsRequired() throws Exception {
        int databaseSizeBeforeTest = telefoneRepository.findAll().size();
        // set the field null
        telefone.setWhatsapp(null);

        // Create the Telefone, which fails.
        TelefoneDTO telefoneDTO = telefoneMapper.toDto(telefone);

        restTelefoneMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(telefoneDTO)))
            .andExpect(status().isBadRequest());

        List<Telefone> telefoneList = telefoneRepository.findAll();
        assertThat(telefoneList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTelefones() throws Exception {
        // Initialize the database
        telefoneRepository.saveAndFlush(telefone);

        // Get all the telefoneList
        restTelefoneMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(telefone.getId().toString())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].whatsapp").value(hasItem(DEFAULT_WHATSAPP.booleanValue())));
    }

    @Test
    @Transactional
    void getTelefone() throws Exception {
        // Initialize the database
        telefoneRepository.saveAndFlush(telefone);

        // Get the telefone
        restTelefoneMockMvc
            .perform(get(ENTITY_API_URL_ID, telefone.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(telefone.getId().toString()))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO))
            .andExpect(jsonPath("$.whatsapp").value(DEFAULT_WHATSAPP.booleanValue()));
    }

    @Test
    @Transactional
    void getTelefonesByIdFiltering() throws Exception {
        // Initialize the database
        telefoneRepository.saveAndFlush(telefone);

        UUID id = telefone.getId();

        defaultTelefoneShouldBeFound("id.equals=" + id);
        defaultTelefoneShouldNotBeFound("id.notEquals=" + id);
    }

    @Test
    @Transactional
    void getAllTelefonesByNumeroIsEqualToSomething() throws Exception {
        // Initialize the database
        telefoneRepository.saveAndFlush(telefone);

        // Get all the telefoneList where numero equals to DEFAULT_NUMERO
        defaultTelefoneShouldBeFound("numero.equals=" + DEFAULT_NUMERO);

        // Get all the telefoneList where numero equals to UPDATED_NUMERO
        defaultTelefoneShouldNotBeFound("numero.equals=" + UPDATED_NUMERO);
    }

    @Test
    @Transactional
    void getAllTelefonesByNumeroIsInShouldWork() throws Exception {
        // Initialize the database
        telefoneRepository.saveAndFlush(telefone);

        // Get all the telefoneList where numero in DEFAULT_NUMERO or UPDATED_NUMERO
        defaultTelefoneShouldBeFound("numero.in=" + DEFAULT_NUMERO + "," + UPDATED_NUMERO);

        // Get all the telefoneList where numero equals to UPDATED_NUMERO
        defaultTelefoneShouldNotBeFound("numero.in=" + UPDATED_NUMERO);
    }

    @Test
    @Transactional
    void getAllTelefonesByNumeroIsNullOrNotNull() throws Exception {
        // Initialize the database
        telefoneRepository.saveAndFlush(telefone);

        // Get all the telefoneList where numero is not null
        defaultTelefoneShouldBeFound("numero.specified=true");

        // Get all the telefoneList where numero is null
        defaultTelefoneShouldNotBeFound("numero.specified=false");
    }

    @Test
    @Transactional
    void getAllTelefonesByNumeroContainsSomething() throws Exception {
        // Initialize the database
        telefoneRepository.saveAndFlush(telefone);

        // Get all the telefoneList where numero contains DEFAULT_NUMERO
        defaultTelefoneShouldBeFound("numero.contains=" + DEFAULT_NUMERO);

        // Get all the telefoneList where numero contains UPDATED_NUMERO
        defaultTelefoneShouldNotBeFound("numero.contains=" + UPDATED_NUMERO);
    }

    @Test
    @Transactional
    void getAllTelefonesByNumeroNotContainsSomething() throws Exception {
        // Initialize the database
        telefoneRepository.saveAndFlush(telefone);

        // Get all the telefoneList where numero does not contain DEFAULT_NUMERO
        defaultTelefoneShouldNotBeFound("numero.doesNotContain=" + DEFAULT_NUMERO);

        // Get all the telefoneList where numero does not contain UPDATED_NUMERO
        defaultTelefoneShouldBeFound("numero.doesNotContain=" + UPDATED_NUMERO);
    }

    @Test
    @Transactional
    void getAllTelefonesByWhatsappIsEqualToSomething() throws Exception {
        // Initialize the database
        telefoneRepository.saveAndFlush(telefone);

        // Get all the telefoneList where whatsapp equals to DEFAULT_WHATSAPP
        defaultTelefoneShouldBeFound("whatsapp.equals=" + DEFAULT_WHATSAPP);

        // Get all the telefoneList where whatsapp equals to UPDATED_WHATSAPP
        defaultTelefoneShouldNotBeFound("whatsapp.equals=" + UPDATED_WHATSAPP);
    }

    @Test
    @Transactional
    void getAllTelefonesByWhatsappIsInShouldWork() throws Exception {
        // Initialize the database
        telefoneRepository.saveAndFlush(telefone);

        // Get all the telefoneList where whatsapp in DEFAULT_WHATSAPP or UPDATED_WHATSAPP
        defaultTelefoneShouldBeFound("whatsapp.in=" + DEFAULT_WHATSAPP + "," + UPDATED_WHATSAPP);

        // Get all the telefoneList where whatsapp equals to UPDATED_WHATSAPP
        defaultTelefoneShouldNotBeFound("whatsapp.in=" + UPDATED_WHATSAPP);
    }

    @Test
    @Transactional
    void getAllTelefonesByWhatsappIsNullOrNotNull() throws Exception {
        // Initialize the database
        telefoneRepository.saveAndFlush(telefone);

        // Get all the telefoneList where whatsapp is not null
        defaultTelefoneShouldBeFound("whatsapp.specified=true");

        // Get all the telefoneList where whatsapp is null
        defaultTelefoneShouldNotBeFound("whatsapp.specified=false");
    }

    @Test
    @Transactional
    void getAllTelefonesByPessoaIsEqualToSomething() throws Exception {
        Pessoa pessoa;
        if (TestUtil.findAll(em, Pessoa.class).isEmpty()) {
            telefoneRepository.saveAndFlush(telefone);
            pessoa = PessoaResourceIT.createEntity(em);
        } else {
            pessoa = TestUtil.findAll(em, Pessoa.class).get(0);
        }
        em.persist(pessoa);
        em.flush();
        telefone.setPessoa(pessoa);
        telefoneRepository.saveAndFlush(telefone);
        UUID pessoaId = pessoa.getId();

        // Get all the telefoneList where pessoa equals to pessoaId
        defaultTelefoneShouldBeFound("pessoaId.equals=" + pessoaId);

        // Get all the telefoneList where pessoa equals to UUID.randomUUID()
        defaultTelefoneShouldNotBeFound("pessoaId.equals=" + UUID.randomUUID());
    }

    @Test
    @Transactional
    void getAllTelefonesByResponsavelIsEqualToSomething() throws Exception {
        Responsavel responsavel;
        if (TestUtil.findAll(em, Responsavel.class).isEmpty()) {
            telefoneRepository.saveAndFlush(telefone);
            responsavel = ResponsavelResourceIT.createEntity(em);
        } else {
            responsavel = TestUtil.findAll(em, Responsavel.class).get(0);
        }
        em.persist(responsavel);
        em.flush();
        telefone.setResponsavel(responsavel);
        telefoneRepository.saveAndFlush(telefone);
        UUID responsavelId = responsavel.getId();

        // Get all the telefoneList where responsavel equals to responsavelId
        defaultTelefoneShouldBeFound("responsavelId.equals=" + responsavelId);

        // Get all the telefoneList where responsavel equals to UUID.randomUUID()
        defaultTelefoneShouldNotBeFound("responsavelId.equals=" + UUID.randomUUID());
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTelefoneShouldBeFound(String filter) throws Exception {
        restTelefoneMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(telefone.getId().toString())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].whatsapp").value(hasItem(DEFAULT_WHATSAPP.booleanValue())));

        // Check, that the count call also returns 1
        restTelefoneMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTelefoneShouldNotBeFound(String filter) throws Exception {
        restTelefoneMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTelefoneMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingTelefone() throws Exception {
        // Get the telefone
        restTelefoneMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTelefone() throws Exception {
        // Initialize the database
        telefoneRepository.saveAndFlush(telefone);

        int databaseSizeBeforeUpdate = telefoneRepository.findAll().size();

        // Update the telefone
        Telefone updatedTelefone = telefoneRepository.findById(telefone.getId()).get();
        // Disconnect from session so that the updates on updatedTelefone are not directly saved in db
        em.detach(updatedTelefone);
        updatedTelefone.numero(UPDATED_NUMERO).whatsapp(UPDATED_WHATSAPP);
        TelefoneDTO telefoneDTO = telefoneMapper.toDto(updatedTelefone);

        restTelefoneMockMvc
            .perform(
                put(ENTITY_API_URL_ID, telefoneDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(telefoneDTO))
            )
            .andExpect(status().isOk());

        // Validate the Telefone in the database
        List<Telefone> telefoneList = telefoneRepository.findAll();
        assertThat(telefoneList).hasSize(databaseSizeBeforeUpdate);
        Telefone testTelefone = telefoneList.get(telefoneList.size() - 1);
        assertThat(testTelefone.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testTelefone.getWhatsapp()).isEqualTo(UPDATED_WHATSAPP);
    }

    @Test
    @Transactional
    void putNonExistingTelefone() throws Exception {
        int databaseSizeBeforeUpdate = telefoneRepository.findAll().size();
        telefone.setId(UUID.randomUUID());

        // Create the Telefone
        TelefoneDTO telefoneDTO = telefoneMapper.toDto(telefone);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTelefoneMockMvc
            .perform(
                put(ENTITY_API_URL_ID, telefoneDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(telefoneDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Telefone in the database
        List<Telefone> telefoneList = telefoneRepository.findAll();
        assertThat(telefoneList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTelefone() throws Exception {
        int databaseSizeBeforeUpdate = telefoneRepository.findAll().size();
        telefone.setId(UUID.randomUUID());

        // Create the Telefone
        TelefoneDTO telefoneDTO = telefoneMapper.toDto(telefone);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTelefoneMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(telefoneDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Telefone in the database
        List<Telefone> telefoneList = telefoneRepository.findAll();
        assertThat(telefoneList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTelefone() throws Exception {
        int databaseSizeBeforeUpdate = telefoneRepository.findAll().size();
        telefone.setId(UUID.randomUUID());

        // Create the Telefone
        TelefoneDTO telefoneDTO = telefoneMapper.toDto(telefone);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTelefoneMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(telefoneDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Telefone in the database
        List<Telefone> telefoneList = telefoneRepository.findAll();
        assertThat(telefoneList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTelefoneWithPatch() throws Exception {
        // Initialize the database
        telefoneRepository.saveAndFlush(telefone);

        int databaseSizeBeforeUpdate = telefoneRepository.findAll().size();

        // Update the telefone using partial update
        Telefone partialUpdatedTelefone = new Telefone();
        partialUpdatedTelefone.setId(telefone.getId());

        partialUpdatedTelefone.numero(UPDATED_NUMERO).whatsapp(UPDATED_WHATSAPP);

        restTelefoneMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTelefone.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTelefone))
            )
            .andExpect(status().isOk());

        // Validate the Telefone in the database
        List<Telefone> telefoneList = telefoneRepository.findAll();
        assertThat(telefoneList).hasSize(databaseSizeBeforeUpdate);
        Telefone testTelefone = telefoneList.get(telefoneList.size() - 1);
        assertThat(testTelefone.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testTelefone.getWhatsapp()).isEqualTo(UPDATED_WHATSAPP);
    }

    @Test
    @Transactional
    void fullUpdateTelefoneWithPatch() throws Exception {
        // Initialize the database
        telefoneRepository.saveAndFlush(telefone);

        int databaseSizeBeforeUpdate = telefoneRepository.findAll().size();

        // Update the telefone using partial update
        Telefone partialUpdatedTelefone = new Telefone();
        partialUpdatedTelefone.setId(telefone.getId());

        partialUpdatedTelefone.numero(UPDATED_NUMERO).whatsapp(UPDATED_WHATSAPP);

        restTelefoneMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTelefone.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTelefone))
            )
            .andExpect(status().isOk());

        // Validate the Telefone in the database
        List<Telefone> telefoneList = telefoneRepository.findAll();
        assertThat(telefoneList).hasSize(databaseSizeBeforeUpdate);
        Telefone testTelefone = telefoneList.get(telefoneList.size() - 1);
        assertThat(testTelefone.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testTelefone.getWhatsapp()).isEqualTo(UPDATED_WHATSAPP);
    }

    @Test
    @Transactional
    void patchNonExistingTelefone() throws Exception {
        int databaseSizeBeforeUpdate = telefoneRepository.findAll().size();
        telefone.setId(UUID.randomUUID());

        // Create the Telefone
        TelefoneDTO telefoneDTO = telefoneMapper.toDto(telefone);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTelefoneMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, telefoneDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(telefoneDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Telefone in the database
        List<Telefone> telefoneList = telefoneRepository.findAll();
        assertThat(telefoneList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTelefone() throws Exception {
        int databaseSizeBeforeUpdate = telefoneRepository.findAll().size();
        telefone.setId(UUID.randomUUID());

        // Create the Telefone
        TelefoneDTO telefoneDTO = telefoneMapper.toDto(telefone);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTelefoneMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(telefoneDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Telefone in the database
        List<Telefone> telefoneList = telefoneRepository.findAll();
        assertThat(telefoneList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTelefone() throws Exception {
        int databaseSizeBeforeUpdate = telefoneRepository.findAll().size();
        telefone.setId(UUID.randomUUID());

        // Create the Telefone
        TelefoneDTO telefoneDTO = telefoneMapper.toDto(telefone);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTelefoneMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(telefoneDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Telefone in the database
        List<Telefone> telefoneList = telefoneRepository.findAll();
        assertThat(telefoneList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTelefone() throws Exception {
        // Initialize the database
        telefoneRepository.saveAndFlush(telefone);

        int databaseSizeBeforeDelete = telefoneRepository.findAll().size();

        // Delete the telefone
        restTelefoneMockMvc
            .perform(delete(ENTITY_API_URL_ID, telefone.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Telefone> telefoneList = telefoneRepository.findAll();
        assertThat(telefoneList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

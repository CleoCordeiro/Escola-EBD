package br.com.cleo.escolaebd.web.rest;

import br.com.cleo.escolaebd.repository.ResponsavelRepository;
import br.com.cleo.escolaebd.service.ResponsavelQueryService;
import br.com.cleo.escolaebd.service.ResponsavelService;
import br.com.cleo.escolaebd.service.criteria.ResponsavelCriteria;
import br.com.cleo.escolaebd.service.dto.ResponsavelDTO;
import br.com.cleo.escolaebd.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link br.com.cleo.escolaebd.domain.Responsavel}.
 */
@RestController
@RequestMapping("/api")
public class ResponsavelResource {

    private final Logger log = LoggerFactory.getLogger(ResponsavelResource.class);

    private static final String ENTITY_NAME = "responsavel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ResponsavelService responsavelService;

    private final ResponsavelRepository responsavelRepository;

    private final ResponsavelQueryService responsavelQueryService;

    public ResponsavelResource(
        ResponsavelService responsavelService,
        ResponsavelRepository responsavelRepository,
        ResponsavelQueryService responsavelQueryService
    ) {
        this.responsavelService = responsavelService;
        this.responsavelRepository = responsavelRepository;
        this.responsavelQueryService = responsavelQueryService;
    }

    /**
     * {@code POST  /responsavels} : Create a new responsavel.
     *
     * @param responsavelDTO the responsavelDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new responsavelDTO, or with status {@code 400 (Bad Request)} if the responsavel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/responsavels")
    public ResponseEntity<ResponsavelDTO> createResponsavel(@Valid @RequestBody ResponsavelDTO responsavelDTO) throws URISyntaxException {
        log.debug("REST request to save Responsavel : {}", responsavelDTO);
        if (responsavelDTO.getId() != null) {
            throw new BadRequestAlertException("A new responsavel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ResponsavelDTO result = responsavelService.save(responsavelDTO);
        return ResponseEntity
            .created(new URI("/api/responsavels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /responsavels/:id} : Updates an existing responsavel.
     *
     * @param id the id of the responsavelDTO to save.
     * @param responsavelDTO the responsavelDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated responsavelDTO,
     * or with status {@code 400 (Bad Request)} if the responsavelDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the responsavelDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/responsavels/{id}")
    public ResponseEntity<ResponsavelDTO> updateResponsavel(
        @PathVariable(value = "id", required = false) final UUID id,
        @Valid @RequestBody ResponsavelDTO responsavelDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Responsavel : {}, {}", id, responsavelDTO);
        if (responsavelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, responsavelDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!responsavelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ResponsavelDTO result = responsavelService.update(responsavelDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, responsavelDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /responsavels/:id} : Partial updates given fields of an existing responsavel, field will ignore if it is null
     *
     * @param id the id of the responsavelDTO to save.
     * @param responsavelDTO the responsavelDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated responsavelDTO,
     * or with status {@code 400 (Bad Request)} if the responsavelDTO is not valid,
     * or with status {@code 404 (Not Found)} if the responsavelDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the responsavelDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/responsavels/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ResponsavelDTO> partialUpdateResponsavel(
        @PathVariable(value = "id", required = false) final UUID id,
        @NotNull @RequestBody ResponsavelDTO responsavelDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Responsavel partially : {}, {}", id, responsavelDTO);
        if (responsavelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, responsavelDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!responsavelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ResponsavelDTO> result = responsavelService.partialUpdate(responsavelDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, responsavelDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /responsavels} : get all the responsavels.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of responsavels in body.
     */
    @GetMapping("/responsavels")
    public ResponseEntity<List<ResponsavelDTO>> getAllResponsavels(
        ResponsavelCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Responsavels by criteria: {}", criteria);
        Page<ResponsavelDTO> page = responsavelQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /responsavels/count} : count all the responsavels.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/responsavels/count")
    public ResponseEntity<Long> countResponsavels(ResponsavelCriteria criteria) {
        log.debug("REST request to count Responsavels by criteria: {}", criteria);
        return ResponseEntity.ok().body(responsavelQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /responsavels/:id} : get the "id" responsavel.
     *
     * @param id the id of the responsavelDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the responsavelDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/responsavels/{id}")
    public ResponseEntity<ResponsavelDTO> getResponsavel(@PathVariable UUID id) {
        log.debug("REST request to get Responsavel : {}", id);
        Optional<ResponsavelDTO> responsavelDTO = responsavelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(responsavelDTO);
    }

    /**
     * {@code DELETE  /responsavels/:id} : delete the "id" responsavel.
     *
     * @param id the id of the responsavelDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/responsavels/{id}")
    public ResponseEntity<Void> deleteResponsavel(@PathVariable UUID id) {
        log.debug("REST request to delete Responsavel : {}", id);
        responsavelService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

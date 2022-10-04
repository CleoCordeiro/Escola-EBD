package br.com.cleo.escolaebd.service;

import br.com.cleo.escolaebd.service.dto.ResponsavelDTO;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link br.com.cleo.escolaebd.domain.Responsavel}.
 */
public interface ResponsavelService {
    /**
     * Save a responsavel.
     *
     * @param responsavelDTO the entity to save.
     * @return the persisted entity.
     */
    ResponsavelDTO save(ResponsavelDTO responsavelDTO);

    /**
     * Updates a responsavel.
     *
     * @param responsavelDTO the entity to update.
     * @return the persisted entity.
     */
    ResponsavelDTO update(ResponsavelDTO responsavelDTO);

    /**
     * Partially updates a responsavel.
     *
     * @param responsavelDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ResponsavelDTO> partialUpdate(ResponsavelDTO responsavelDTO);

    /**
     * Get all the responsavels.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ResponsavelDTO> findAll(Pageable pageable);

    /**
     * Get all the responsavels with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ResponsavelDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" responsavel.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ResponsavelDTO> findOne(UUID id);

    /**
     * Delete the "id" responsavel.
     *
     * @param id the id of the entity.
     */
    void delete(UUID id);
}

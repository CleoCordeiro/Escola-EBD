package br.com.cleo.escolaebd.service;

import br.com.cleo.escolaebd.service.dto.TelefoneDTO;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link br.com.cleo.escolaebd.domain.Telefone}.
 */
public interface TelefoneService {
    /**
     * Save a telefone.
     *
     * @param telefoneDTO the entity to save.
     * @return the persisted entity.
     */
    TelefoneDTO save(TelefoneDTO telefoneDTO);

    /**
     * Updates a telefone.
     *
     * @param telefoneDTO the entity to update.
     * @return the persisted entity.
     */
    TelefoneDTO update(TelefoneDTO telefoneDTO);

    /**
     * Partially updates a telefone.
     *
     * @param telefoneDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TelefoneDTO> partialUpdate(TelefoneDTO telefoneDTO);

    /**
     * Get all the telefones.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TelefoneDTO> findAll(Pageable pageable);

    /**
     * Get the "id" telefone.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TelefoneDTO> findOne(UUID id);

    /**
     * Delete the "id" telefone.
     *
     * @param id the id of the entity.
     */
    void delete(UUID id);
}

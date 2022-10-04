package br.com.cleo.escolaebd.service.impl;

import br.com.cleo.escolaebd.domain.Telefone;
import br.com.cleo.escolaebd.repository.TelefoneRepository;
import br.com.cleo.escolaebd.service.TelefoneService;
import br.com.cleo.escolaebd.service.dto.TelefoneDTO;
import br.com.cleo.escolaebd.service.mapper.TelefoneMapper;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Telefone}.
 */
@Service
@Transactional
public class TelefoneServiceImpl implements TelefoneService {

    private final Logger log = LoggerFactory.getLogger(TelefoneServiceImpl.class);

    private final TelefoneRepository telefoneRepository;

    private final TelefoneMapper telefoneMapper;

    public TelefoneServiceImpl(TelefoneRepository telefoneRepository, TelefoneMapper telefoneMapper) {
        this.telefoneRepository = telefoneRepository;
        this.telefoneMapper = telefoneMapper;
    }

    @Override
    public TelefoneDTO save(TelefoneDTO telefoneDTO) {
        log.debug("Request to save Telefone : {}", telefoneDTO);
        Telefone telefone = telefoneMapper.toEntity(telefoneDTO);
        telefone = telefoneRepository.save(telefone);
        return telefoneMapper.toDto(telefone);
    }

    @Override
    public TelefoneDTO update(TelefoneDTO telefoneDTO) {
        log.debug("Request to update Telefone : {}", telefoneDTO);
        Telefone telefone = telefoneMapper.toEntity(telefoneDTO);
        telefone = telefoneRepository.save(telefone);
        return telefoneMapper.toDto(telefone);
    }

    @Override
    public Optional<TelefoneDTO> partialUpdate(TelefoneDTO telefoneDTO) {
        log.debug("Request to partially update Telefone : {}", telefoneDTO);

        return telefoneRepository
            .findById(telefoneDTO.getId())
            .map(existingTelefone -> {
                telefoneMapper.partialUpdate(existingTelefone, telefoneDTO);

                return existingTelefone;
            })
            .map(telefoneRepository::save)
            .map(telefoneMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TelefoneDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Telefones");
        return telefoneRepository.findAll(pageable).map(telefoneMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TelefoneDTO> findOne(UUID id) {
        log.debug("Request to get Telefone : {}", id);
        return telefoneRepository.findById(id).map(telefoneMapper::toDto);
    }

    @Override
    public void delete(UUID id) {
        log.debug("Request to delete Telefone : {}", id);
        telefoneRepository.deleteById(id);
    }
}

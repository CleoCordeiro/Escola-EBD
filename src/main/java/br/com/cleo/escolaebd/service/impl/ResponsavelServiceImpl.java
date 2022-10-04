package br.com.cleo.escolaebd.service.impl;

import br.com.cleo.escolaebd.domain.Responsavel;
import br.com.cleo.escolaebd.repository.ResponsavelRepository;
import br.com.cleo.escolaebd.service.ResponsavelService;
import br.com.cleo.escolaebd.service.dto.ResponsavelDTO;
import br.com.cleo.escolaebd.service.mapper.ResponsavelMapper;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Responsavel}.
 */
@Service
@Transactional
public class ResponsavelServiceImpl implements ResponsavelService {

    private final Logger log = LoggerFactory.getLogger(ResponsavelServiceImpl.class);

    private final ResponsavelRepository responsavelRepository;

    private final ResponsavelMapper responsavelMapper;

    public ResponsavelServiceImpl(ResponsavelRepository responsavelRepository, ResponsavelMapper responsavelMapper) {
        this.responsavelRepository = responsavelRepository;
        this.responsavelMapper = responsavelMapper;
    }

    @Override
    public ResponsavelDTO save(ResponsavelDTO responsavelDTO) {
        log.debug("Request to save Responsavel : {}", responsavelDTO);
        Responsavel responsavel = responsavelMapper.toEntity(responsavelDTO);
        responsavel = responsavelRepository.save(responsavel);
        return responsavelMapper.toDto(responsavel);
    }

    @Override
    public ResponsavelDTO update(ResponsavelDTO responsavelDTO) {
        log.debug("Request to update Responsavel : {}", responsavelDTO);
        Responsavel responsavel = responsavelMapper.toEntity(responsavelDTO);
        responsavel = responsavelRepository.save(responsavel);
        return responsavelMapper.toDto(responsavel);
    }

    @Override
    public Optional<ResponsavelDTO> partialUpdate(ResponsavelDTO responsavelDTO) {
        log.debug("Request to partially update Responsavel : {}", responsavelDTO);

        return responsavelRepository
            .findById(responsavelDTO.getId())
            .map(existingResponsavel -> {
                responsavelMapper.partialUpdate(existingResponsavel, responsavelDTO);

                return existingResponsavel;
            })
            .map(responsavelRepository::save)
            .map(responsavelMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ResponsavelDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Responsavels");
        return responsavelRepository.findAll(pageable).map(responsavelMapper::toDto);
    }

    public Page<ResponsavelDTO> findAllWithEagerRelationships(Pageable pageable) {
        return responsavelRepository.findAllWithEagerRelationships(pageable).map(responsavelMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ResponsavelDTO> findOne(UUID id) {
        log.debug("Request to get Responsavel : {}", id);
        return responsavelRepository.findOneWithEagerRelationships(id).map(responsavelMapper::toDto);
    }

    @Override
    public void delete(UUID id) {
        log.debug("Request to delete Responsavel : {}", id);
        responsavelRepository.deleteById(id);
    }
}

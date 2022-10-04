package br.com.cleo.escolaebd.service.impl;

import br.com.cleo.escolaebd.domain.Turma;
import br.com.cleo.escolaebd.repository.TurmaRepository;
import br.com.cleo.escolaebd.service.TurmaService;
import br.com.cleo.escolaebd.service.dto.TurmaDTO;
import br.com.cleo.escolaebd.service.mapper.TurmaMapper;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Turma}.
 */
@Service
@Transactional
public class TurmaServiceImpl implements TurmaService {

    private final Logger log = LoggerFactory.getLogger(TurmaServiceImpl.class);

    private final TurmaRepository turmaRepository;

    private final TurmaMapper turmaMapper;

    public TurmaServiceImpl(TurmaRepository turmaRepository, TurmaMapper turmaMapper) {
        this.turmaRepository = turmaRepository;
        this.turmaMapper = turmaMapper;
    }

    @Override
    public TurmaDTO save(TurmaDTO turmaDTO) {
        log.debug("Request to save Turma : {}", turmaDTO);
        Turma turma = turmaMapper.toEntity(turmaDTO);
        turma = turmaRepository.save(turma);
        return turmaMapper.toDto(turma);
    }

    @Override
    public TurmaDTO update(TurmaDTO turmaDTO) {
        log.debug("Request to update Turma : {}", turmaDTO);
        Turma turma = turmaMapper.toEntity(turmaDTO);
        turma = turmaRepository.save(turma);
        return turmaMapper.toDto(turma);
    }

    @Override
    public Optional<TurmaDTO> partialUpdate(TurmaDTO turmaDTO) {
        log.debug("Request to partially update Turma : {}", turmaDTO);

        return turmaRepository
            .findById(turmaDTO.getId())
            .map(existingTurma -> {
                turmaMapper.partialUpdate(existingTurma, turmaDTO);

                return existingTurma;
            })
            .map(turmaRepository::save)
            .map(turmaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TurmaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Turmas");
        return turmaRepository.findAll(pageable).map(turmaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TurmaDTO> findOne(UUID id) {
        log.debug("Request to get Turma : {}", id);
        return turmaRepository.findById(id).map(turmaMapper::toDto);
    }

    @Override
    public void delete(UUID id) {
        log.debug("Request to delete Turma : {}", id);
        turmaRepository.deleteById(id);
    }
}

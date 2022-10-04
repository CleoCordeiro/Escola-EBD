package br.com.cleo.escolaebd.service;

import br.com.cleo.escolaebd.domain.*; // for static metamodels
import br.com.cleo.escolaebd.domain.Turma;
import br.com.cleo.escolaebd.repository.TurmaRepository;
import br.com.cleo.escolaebd.service.criteria.TurmaCriteria;
import br.com.cleo.escolaebd.service.dto.TurmaDTO;
import br.com.cleo.escolaebd.service.mapper.TurmaMapper;
import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Turma} entities in the database.
 * The main input is a {@link TurmaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TurmaDTO} or a {@link Page} of {@link TurmaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TurmaQueryService extends QueryService<Turma> {

    private final Logger log = LoggerFactory.getLogger(TurmaQueryService.class);

    private final TurmaRepository turmaRepository;

    private final TurmaMapper turmaMapper;

    public TurmaQueryService(TurmaRepository turmaRepository, TurmaMapper turmaMapper) {
        this.turmaRepository = turmaRepository;
        this.turmaMapper = turmaMapper;
    }

    /**
     * Return a {@link List} of {@link TurmaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TurmaDTO> findByCriteria(TurmaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Turma> specification = createSpecification(criteria);
        return turmaMapper.toDto(turmaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TurmaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TurmaDTO> findByCriteria(TurmaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Turma> specification = createSpecification(criteria);
        return turmaRepository.findAll(specification, page).map(turmaMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TurmaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Turma> specification = createSpecification(criteria);
        return turmaRepository.count(specification);
    }

    /**
     * Function to convert {@link TurmaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Turma> createSpecification(TurmaCriteria criteria) {
        Specification<Turma> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Turma_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), Turma_.nome));
            }
            if (criteria.getSexoTurma() != null) {
                specification = specification.and(buildSpecification(criteria.getSexoTurma(), Turma_.sexoTurma));
            }
            if (criteria.getFaixaEtaria() != null) {
                specification = specification.and(buildSpecification(criteria.getFaixaEtaria(), Turma_.faixaEtaria));
            }
            if (criteria.getDataInicio() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataInicio(), Turma_.dataInicio));
            }
            if (criteria.getDataFim() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataFim(), Turma_.dataFim));
            }
            if (criteria.getDataCadastro() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataCadastro(), Turma_.dataCadastro));
            }
            if (criteria.getProfessorId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getProfessorId(), root -> root.join(Turma_.professor, JoinType.LEFT).get(Pessoa_.id))
                    );
            }
            if (criteria.getAlunosId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getAlunosId(), root -> root.join(Turma_.alunos, JoinType.LEFT).get(Pessoa_.id))
                    );
            }
        }
        return specification;
    }
}

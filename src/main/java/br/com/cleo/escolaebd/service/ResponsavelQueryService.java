package br.com.cleo.escolaebd.service;

import br.com.cleo.escolaebd.domain.*; // for static metamodels
import br.com.cleo.escolaebd.domain.Responsavel;
import br.com.cleo.escolaebd.repository.ResponsavelRepository;
import br.com.cleo.escolaebd.service.criteria.ResponsavelCriteria;
import br.com.cleo.escolaebd.service.dto.ResponsavelDTO;
import br.com.cleo.escolaebd.service.mapper.ResponsavelMapper;
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
 * Service for executing complex queries for {@link Responsavel} entities in the database.
 * The main input is a {@link ResponsavelCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ResponsavelDTO} or a {@link Page} of {@link ResponsavelDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ResponsavelQueryService extends QueryService<Responsavel> {

    private final Logger log = LoggerFactory.getLogger(ResponsavelQueryService.class);

    private final ResponsavelRepository responsavelRepository;

    private final ResponsavelMapper responsavelMapper;

    public ResponsavelQueryService(ResponsavelRepository responsavelRepository, ResponsavelMapper responsavelMapper) {
        this.responsavelRepository = responsavelRepository;
        this.responsavelMapper = responsavelMapper;
    }

    /**
     * Return a {@link List} of {@link ResponsavelDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ResponsavelDTO> findByCriteria(ResponsavelCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Responsavel> specification = createSpecification(criteria);
        return responsavelMapper.toDto(responsavelRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ResponsavelDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ResponsavelDTO> findByCriteria(ResponsavelCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Responsavel> specification = createSpecification(criteria);
        return responsavelRepository.findAll(specification, page).map(responsavelMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ResponsavelCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Responsavel> specification = createSpecification(criteria);
        return responsavelRepository.count(specification);
    }

    /**
     * Function to convert {@link ResponsavelCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Responsavel> createSpecification(ResponsavelCriteria criteria) {
        Specification<Responsavel> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Responsavel_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), Responsavel_.nome));
            }
            if (criteria.getCpf() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCpf(), Responsavel_.cpf));
            }
            if (criteria.getSexo() != null) {
                specification = specification.and(buildSpecification(criteria.getSexo(), Responsavel_.sexo));
            }
            if (criteria.getParentesco() != null) {
                specification = specification.and(buildStringSpecification(criteria.getParentesco(), Responsavel_.parentesco));
            }
            if (criteria.getDataCadastro() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataCadastro(), Responsavel_.dataCadastro));
            }
            if (criteria.getTelefonesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getTelefonesId(),
                            root -> root.join(Responsavel_.telefones, JoinType.LEFT).get(Telefone_.id)
                        )
                    );
            }
            if (criteria.getEnderecosId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEnderecosId(),
                            root -> root.join(Responsavel_.enderecos, JoinType.LEFT).get(Endereco_.id)
                        )
                    );
            }
            if (criteria.getAlunosId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getAlunosId(), root -> root.join(Responsavel_.alunos, JoinType.LEFT).get(Pessoa_.id))
                    );
            }
        }
        return specification;
    }
}

package br.com.cleo.escolaebd.service;

import br.com.cleo.escolaebd.domain.*; // for static metamodels
import br.com.cleo.escolaebd.domain.Telefone;
import br.com.cleo.escolaebd.repository.TelefoneRepository;
import br.com.cleo.escolaebd.service.criteria.TelefoneCriteria;
import br.com.cleo.escolaebd.service.dto.TelefoneDTO;
import br.com.cleo.escolaebd.service.mapper.TelefoneMapper;
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
 * Service for executing complex queries for {@link Telefone} entities in the database.
 * The main input is a {@link TelefoneCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TelefoneDTO} or a {@link Page} of {@link TelefoneDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TelefoneQueryService extends QueryService<Telefone> {

    private final Logger log = LoggerFactory.getLogger(TelefoneQueryService.class);

    private final TelefoneRepository telefoneRepository;

    private final TelefoneMapper telefoneMapper;

    public TelefoneQueryService(TelefoneRepository telefoneRepository, TelefoneMapper telefoneMapper) {
        this.telefoneRepository = telefoneRepository;
        this.telefoneMapper = telefoneMapper;
    }

    /**
     * Return a {@link List} of {@link TelefoneDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TelefoneDTO> findByCriteria(TelefoneCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Telefone> specification = createSpecification(criteria);
        return telefoneMapper.toDto(telefoneRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TelefoneDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TelefoneDTO> findByCriteria(TelefoneCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Telefone> specification = createSpecification(criteria);
        return telefoneRepository.findAll(specification, page).map(telefoneMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TelefoneCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Telefone> specification = createSpecification(criteria);
        return telefoneRepository.count(specification);
    }

    /**
     * Function to convert {@link TelefoneCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Telefone> createSpecification(TelefoneCriteria criteria) {
        Specification<Telefone> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Telefone_.id));
            }
            if (criteria.getNumero() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNumero(), Telefone_.numero));
            }
            if (criteria.getWhatsapp() != null) {
                specification = specification.and(buildSpecification(criteria.getWhatsapp(), Telefone_.whatsapp));
            }
            if (criteria.getPessoaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getPessoaId(), root -> root.join(Telefone_.pessoa, JoinType.LEFT).get(Pessoa_.id))
                    );
            }
            if (criteria.getResponsavelId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getResponsavelId(),
                            root -> root.join(Telefone_.responsavel, JoinType.LEFT).get(Responsavel_.id)
                        )
                    );
            }
        }
        return specification;
    }
}

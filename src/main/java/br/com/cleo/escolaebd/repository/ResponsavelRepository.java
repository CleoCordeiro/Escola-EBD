package br.com.cleo.escolaebd.repository;

import br.com.cleo.escolaebd.domain.Responsavel;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Responsavel entity.
 *
 * When extending this class, extend ResponsavelRepositoryWithBagRelationships too.
 * For more information refer to https://github.com/jhipster/generator-jhipster/issues/17990.
 */
@Repository
public interface ResponsavelRepository
    extends ResponsavelRepositoryWithBagRelationships, JpaRepository<Responsavel, UUID>, JpaSpecificationExecutor<Responsavel> {
    default Optional<Responsavel> findOneWithEagerRelationships(UUID id) {
        return this.fetchBagRelationships(this.findById(id));
    }

    default List<Responsavel> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAll());
    }

    default Page<Responsavel> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAll(pageable));
    }
}

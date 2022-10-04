package br.com.cleo.escolaebd.repository;

import br.com.cleo.escolaebd.domain.Turma;
import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Turma entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TurmaRepository extends JpaRepository<Turma, UUID>, JpaSpecificationExecutor<Turma> {}

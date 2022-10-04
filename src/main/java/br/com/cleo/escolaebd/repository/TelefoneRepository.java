package br.com.cleo.escolaebd.repository;

import br.com.cleo.escolaebd.domain.Telefone;
import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Telefone entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TelefoneRepository extends JpaRepository<Telefone, UUID>, JpaSpecificationExecutor<Telefone> {}

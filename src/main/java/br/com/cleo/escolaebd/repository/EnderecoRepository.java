package br.com.cleo.escolaebd.repository;

import br.com.cleo.escolaebd.domain.Endereco;
import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Endereco entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, UUID>, JpaSpecificationExecutor<Endereco> {}

package br.com.cleo.escolaebd.repository;

import br.com.cleo.escolaebd.domain.Pessoa;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;

public interface PessoaRepositoryWithBagRelationships {
    Optional<Pessoa> fetchBagRelationships(Optional<Pessoa> pessoa);

    List<Pessoa> fetchBagRelationships(List<Pessoa> pessoas);

    Page<Pessoa> fetchBagRelationships(Page<Pessoa> pessoas);
}

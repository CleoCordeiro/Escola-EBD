package br.com.cleo.escolaebd.repository;

import br.com.cleo.escolaebd.domain.Responsavel;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;

public interface ResponsavelRepositoryWithBagRelationships {
    Optional<Responsavel> fetchBagRelationships(Optional<Responsavel> responsavel);

    List<Responsavel> fetchBagRelationships(List<Responsavel> responsavels);

    Page<Responsavel> fetchBagRelationships(Page<Responsavel> responsavels);
}

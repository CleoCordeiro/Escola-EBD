package br.com.cleo.escolaebd.repository;

import br.com.cleo.escolaebd.domain.Responsavel;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.IntStream;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.annotations.QueryHints;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class ResponsavelRepositoryWithBagRelationshipsImpl implements ResponsavelRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Responsavel> fetchBagRelationships(Optional<Responsavel> responsavel) {
        return responsavel.map(this::fetchEnderecos).map(this::fetchAlunos);
    }

    @Override
    public Page<Responsavel> fetchBagRelationships(Page<Responsavel> responsavels) {
        return new PageImpl<>(
            fetchBagRelationships(responsavels.getContent()),
            responsavels.getPageable(),
            responsavels.getTotalElements()
        );
    }

    @Override
    public List<Responsavel> fetchBagRelationships(List<Responsavel> responsavels) {
        return Optional.of(responsavels).map(this::fetchEnderecos).map(this::fetchAlunos).orElse(Collections.emptyList());
    }

    Responsavel fetchEnderecos(Responsavel result) {
        return entityManager
            .createQuery(
                "select responsavel from Responsavel responsavel left join fetch responsavel.enderecos where responsavel is :responsavel",
                Responsavel.class
            )
            .setParameter("responsavel", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<Responsavel> fetchEnderecos(List<Responsavel> responsavels) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, responsavels.size()).forEach(index -> order.put(responsavels.get(index).getId(), index));
        List<Responsavel> result = entityManager
            .createQuery(
                "select distinct responsavel from Responsavel responsavel left join fetch responsavel.enderecos where responsavel in :responsavels",
                Responsavel.class
            )
            .setParameter("responsavels", responsavels)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }

    Responsavel fetchAlunos(Responsavel result) {
        return entityManager
            .createQuery(
                "select responsavel from Responsavel responsavel left join fetch responsavel.alunos where responsavel is :responsavel",
                Responsavel.class
            )
            .setParameter("responsavel", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<Responsavel> fetchAlunos(List<Responsavel> responsavels) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, responsavels.size()).forEach(index -> order.put(responsavels.get(index).getId(), index));
        List<Responsavel> result = entityManager
            .createQuery(
                "select distinct responsavel from Responsavel responsavel left join fetch responsavel.alunos where responsavel in :responsavels",
                Responsavel.class
            )
            .setParameter("responsavels", responsavels)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}

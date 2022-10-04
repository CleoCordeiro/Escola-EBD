package br.com.cleo.escolaebd.service.mapper;

import br.com.cleo.escolaebd.domain.Pessoa;
import br.com.cleo.escolaebd.domain.Turma;
import br.com.cleo.escolaebd.service.dto.PessoaDTO;
import br.com.cleo.escolaebd.service.dto.TurmaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Turma} and its DTO {@link TurmaDTO}.
 */
@Mapper(componentModel = "spring")
public interface TurmaMapper extends EntityMapper<TurmaDTO, Turma> {
    @Mapping(target = "professor", source = "professor", qualifiedByName = "pessoaId")
    TurmaDTO toDto(Turma s);

    @Named("pessoaId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PessoaDTO toDtoPessoaId(Pessoa pessoa);
}

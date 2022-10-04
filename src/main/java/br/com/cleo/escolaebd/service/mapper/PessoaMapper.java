package br.com.cleo.escolaebd.service.mapper;

import br.com.cleo.escolaebd.domain.Endereco;
import br.com.cleo.escolaebd.domain.Pessoa;
import br.com.cleo.escolaebd.domain.Turma;
import br.com.cleo.escolaebd.service.dto.EnderecoDTO;
import br.com.cleo.escolaebd.service.dto.PessoaDTO;
import br.com.cleo.escolaebd.service.dto.TurmaDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Pessoa} and its DTO {@link PessoaDTO}.
 */
@Mapper(componentModel = "spring")
public interface PessoaMapper extends EntityMapper<PessoaDTO, Pessoa> {
    @Mapping(target = "enderecos", source = "enderecos", qualifiedByName = "enderecoIdSet")
    @Mapping(target = "turma", source = "turma", qualifiedByName = "turmaId")
    PessoaDTO toDto(Pessoa s);

    @Mapping(target = "removeEnderecos", ignore = true)
    Pessoa toEntity(PessoaDTO pessoaDTO);

    @Named("enderecoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EnderecoDTO toDtoEnderecoId(Endereco endereco);

    @Named("enderecoIdSet")
    default Set<EnderecoDTO> toDtoEnderecoIdSet(Set<Endereco> endereco) {
        return endereco.stream().map(this::toDtoEnderecoId).collect(Collectors.toSet());
    }

    @Named("turmaId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TurmaDTO toDtoTurmaId(Turma turma);
}

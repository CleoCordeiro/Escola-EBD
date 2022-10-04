package br.com.cleo.escolaebd.service.mapper;

import br.com.cleo.escolaebd.domain.Endereco;
import br.com.cleo.escolaebd.domain.Pessoa;
import br.com.cleo.escolaebd.domain.Responsavel;
import br.com.cleo.escolaebd.service.dto.EnderecoDTO;
import br.com.cleo.escolaebd.service.dto.PessoaDTO;
import br.com.cleo.escolaebd.service.dto.ResponsavelDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Responsavel} and its DTO {@link ResponsavelDTO}.
 */
@Mapper(componentModel = "spring")
public interface ResponsavelMapper extends EntityMapper<ResponsavelDTO, Responsavel> {
    @Mapping(target = "enderecos", source = "enderecos", qualifiedByName = "enderecoIdSet")
    @Mapping(target = "alunos", source = "alunos", qualifiedByName = "pessoaIdSet")
    ResponsavelDTO toDto(Responsavel s);

    @Mapping(target = "removeEnderecos", ignore = true)
    @Mapping(target = "removeAlunos", ignore = true)
    Responsavel toEntity(ResponsavelDTO responsavelDTO);

    @Named("enderecoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EnderecoDTO toDtoEnderecoId(Endereco endereco);

    @Named("enderecoIdSet")
    default Set<EnderecoDTO> toDtoEnderecoIdSet(Set<Endereco> endereco) {
        return endereco.stream().map(this::toDtoEnderecoId).collect(Collectors.toSet());
    }

    @Named("pessoaId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PessoaDTO toDtoPessoaId(Pessoa pessoa);

    @Named("pessoaIdSet")
    default Set<PessoaDTO> toDtoPessoaIdSet(Set<Pessoa> pessoa) {
        return pessoa.stream().map(this::toDtoPessoaId).collect(Collectors.toSet());
    }
}

package br.com.cleo.escolaebd.service.mapper;

import br.com.cleo.escolaebd.domain.Pessoa;
import br.com.cleo.escolaebd.domain.Responsavel;
import br.com.cleo.escolaebd.domain.Telefone;
import br.com.cleo.escolaebd.service.dto.PessoaDTO;
import br.com.cleo.escolaebd.service.dto.ResponsavelDTO;
import br.com.cleo.escolaebd.service.dto.TelefoneDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Telefone} and its DTO {@link TelefoneDTO}.
 */
@Mapper(componentModel = "spring")
public interface TelefoneMapper extends EntityMapper<TelefoneDTO, Telefone> {
    @Mapping(target = "pessoa", source = "pessoa", qualifiedByName = "pessoaId")
    @Mapping(target = "responsavel", source = "responsavel", qualifiedByName = "responsavelId")
    TelefoneDTO toDto(Telefone s);

    @Named("pessoaId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PessoaDTO toDtoPessoaId(Pessoa pessoa);

    @Named("responsavelId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ResponsavelDTO toDtoResponsavelId(Responsavel responsavel);
}

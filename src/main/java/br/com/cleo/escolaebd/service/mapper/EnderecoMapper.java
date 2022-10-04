package br.com.cleo.escolaebd.service.mapper;

import br.com.cleo.escolaebd.domain.Endereco;
import br.com.cleo.escolaebd.service.dto.EnderecoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Endereco} and its DTO {@link EnderecoDTO}.
 */
@Mapper(componentModel = "spring")
public interface EnderecoMapper extends EntityMapper<EnderecoDTO, Endereco> {}

package br.com.cleo.escolaebd.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.cleo.escolaebd.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class EnderecoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EnderecoDTO.class);
        EnderecoDTO enderecoDTO1 = new EnderecoDTO();
        enderecoDTO1.setId(UUID.randomUUID());
        EnderecoDTO enderecoDTO2 = new EnderecoDTO();
        assertThat(enderecoDTO1).isNotEqualTo(enderecoDTO2);
        enderecoDTO2.setId(enderecoDTO1.getId());
        assertThat(enderecoDTO1).isEqualTo(enderecoDTO2);
        enderecoDTO2.setId(UUID.randomUUID());
        assertThat(enderecoDTO1).isNotEqualTo(enderecoDTO2);
        enderecoDTO1.setId(null);
        assertThat(enderecoDTO1).isNotEqualTo(enderecoDTO2);
    }
}

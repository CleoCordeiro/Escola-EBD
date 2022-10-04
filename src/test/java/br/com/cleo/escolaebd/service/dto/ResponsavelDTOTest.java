package br.com.cleo.escolaebd.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.cleo.escolaebd.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class ResponsavelDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ResponsavelDTO.class);
        ResponsavelDTO responsavelDTO1 = new ResponsavelDTO();
        responsavelDTO1.setId(UUID.randomUUID());
        ResponsavelDTO responsavelDTO2 = new ResponsavelDTO();
        assertThat(responsavelDTO1).isNotEqualTo(responsavelDTO2);
        responsavelDTO2.setId(responsavelDTO1.getId());
        assertThat(responsavelDTO1).isEqualTo(responsavelDTO2);
        responsavelDTO2.setId(UUID.randomUUID());
        assertThat(responsavelDTO1).isNotEqualTo(responsavelDTO2);
        responsavelDTO1.setId(null);
        assertThat(responsavelDTO1).isNotEqualTo(responsavelDTO2);
    }
}

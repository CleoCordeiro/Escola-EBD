package br.com.cleo.escolaebd.domain;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.cleo.escolaebd.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class ResponsavelTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Responsavel.class);
        Responsavel responsavel1 = new Responsavel();
        responsavel1.setId(UUID.randomUUID());
        Responsavel responsavel2 = new Responsavel();
        responsavel2.setId(responsavel1.getId());
        assertThat(responsavel1).isEqualTo(responsavel2);
        responsavel2.setId(UUID.randomUUID());
        assertThat(responsavel1).isNotEqualTo(responsavel2);
        responsavel1.setId(null);
        assertThat(responsavel1).isNotEqualTo(responsavel2);
    }
}

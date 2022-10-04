package br.com.cleo.escolaebd.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.cleo.escolaebd.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class TurmaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TurmaDTO.class);
        TurmaDTO turmaDTO1 = new TurmaDTO();
        turmaDTO1.setId(UUID.randomUUID());
        TurmaDTO turmaDTO2 = new TurmaDTO();
        assertThat(turmaDTO1).isNotEqualTo(turmaDTO2);
        turmaDTO2.setId(turmaDTO1.getId());
        assertThat(turmaDTO1).isEqualTo(turmaDTO2);
        turmaDTO2.setId(UUID.randomUUID());
        assertThat(turmaDTO1).isNotEqualTo(turmaDTO2);
        turmaDTO1.setId(null);
        assertThat(turmaDTO1).isNotEqualTo(turmaDTO2);
    }
}

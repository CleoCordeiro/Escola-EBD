package br.com.cleo.escolaebd.domain;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.cleo.escolaebd.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class TurmaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Turma.class);
        Turma turma1 = new Turma();
        turma1.setId(UUID.randomUUID());
        Turma turma2 = new Turma();
        turma2.setId(turma1.getId());
        assertThat(turma1).isEqualTo(turma2);
        turma2.setId(UUID.randomUUID());
        assertThat(turma1).isNotEqualTo(turma2);
        turma1.setId(null);
        assertThat(turma1).isNotEqualTo(turma2);
    }
}

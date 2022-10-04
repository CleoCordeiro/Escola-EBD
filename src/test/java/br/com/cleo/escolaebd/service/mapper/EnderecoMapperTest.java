package br.com.cleo.escolaebd.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EnderecoMapperTest {

    private EnderecoMapper enderecoMapper;

    @BeforeEach
    public void setUp() {
        enderecoMapper = new EnderecoMapperImpl();
    }
}

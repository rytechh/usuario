package com.rytech.usuario.business.converter;

import com.rytech.usuario.business.dto.in.EnderecoDTORequest;
import com.rytech.usuario.business.dto.out.EnderecoDTOResponse;
import com.rytech.usuario.infrastructure.entity.Endereco;
import com.rytech.usuario.business.dto.in.EnderecoDTORequestFixture;
import com.rytech.usuario.business.dto.out.EnderecoDTOResponseFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class EnderecoConverterTest {

    @InjectMocks
    UsuarioConverter usuarioConverter;

    Endereco enderecoEntity;

    EnderecoDTORequest enderecoDTORequest;

    EnderecoDTOResponse enderecoDTOResponse;


    @BeforeEach
    public void setUp() {

        enderecoEntity = Endereco.builder()
                .rua("Teste Unitário")
                .numero("12345")
                .cep("44340000")
                .cidade("São Paulo")
                .complemento("Complemento")
                .estado("SP")
                .build();

        enderecoDTORequest = EnderecoDTORequestFixture.build("Teste Unitário", "12345",
                "Complemento", "São Paulo", "SP", "44340000");

        enderecoDTOResponse = EnderecoDTOResponseFixture.build(1234567L, "Teste Unitário", "12345",
                "Complemento", "São Paulo", "SP", "44340000");
    }

    @Test
    void deveConverterEnderecoDTOparaEntity() {

        Endereco entity = usuarioConverter.paraEndereco(enderecoDTORequest);

        assertEquals(enderecoEntity, entity);
    }

    @Test
    void deveConverterEnderecoEntityParaDTO() {

        EnderecoDTOResponse dto = usuarioConverter.paraEnderecoDTO(enderecoEntity);

        assertEquals(enderecoDTOResponse, dto);
    }
}

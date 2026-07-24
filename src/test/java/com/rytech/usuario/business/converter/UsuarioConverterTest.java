package com.rytech.usuario.business.converter;

import com.rytech.usuario.business.dto.in.EnderecoDTORequest;
import com.rytech.usuario.business.dto.in.TelefoneDTORequest;
import com.rytech.usuario.business.dto.in.UsuarioDTORequest;
import com.rytech.usuario.business.dto.out.EnderecoDTOResponse;
import com.rytech.usuario.business.dto.out.TelefoneDTOResponse;
import com.rytech.usuario.business.dto.out.UsuarioDTOResponse;
import com.rytech.usuario.infrastructure.entity.Endereco;
import com.rytech.usuario.infrastructure.entity.Telefone;
import com.rytech.usuario.infrastructure.entity.Usuario;
import com.rytech.usuario.business.dto.in.EnderecoDTORequestFixture;
import com.rytech.usuario.business.dto.in.TelefoneDTORequestFixture;
import com.rytech.usuario.business.dto.in.UsuarioDTORequestFixture;
import com.rytech.usuario.business.dto.out.EnderecoDTOResponseFixture;
import com.rytech.usuario.business.dto.out.TelefoneDTOResponseFixture;
import com.rytech.usuario.business.dto.out.UsuarioDTOResponseFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class UsuarioConverterTest {

    @InjectMocks
    UsuarioConverter usuarioConverter;

    Usuario usuarioEntity;

    Endereco enderecoEntity;

    Telefone telefoneEntity;

    UsuarioDTORequest usuarioDTORequest;

    TelefoneDTORequest telefoneDTORequest;

    EnderecoDTORequest enderecoDTORequest;

    UsuarioDTOResponse usuarioDTOResponse;

    EnderecoDTOResponse enderecoDTOResponse;

    TelefoneDTOResponse telefoneDTOResponse;


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
        telefoneEntity = Telefone.builder()
                .numero("34243526")
                .ddd("71")
                .build();
        usuarioEntity = Usuario.builder()
                .nome("rytechh")
                .email("rytechh21@gmail.com")
                .senha("1234")
                .enderecos(List.of(enderecoEntity))
                .telefones(List.of(telefoneEntity))
                .build();

        telefoneDTORequest = TelefoneDTORequestFixture.build("34243526", "71");
        enderecoDTORequest = EnderecoDTORequestFixture.build("Teste Unitário", "12345",
                "Complemento", "São Paulo", "SP", "44340000");
        usuarioDTORequest = UsuarioDTORequestFixture.build("rytechh", "rytechh21@gmail.com", "1234",
                List.of(enderecoDTORequest), List.of(telefoneDTORequest));


        telefoneDTOResponse = TelefoneDTOResponseFixture.build(123456L, "34243526", "71");
        enderecoDTOResponse = EnderecoDTOResponseFixture.build(1234567L, "Teste Unitário", "12345",
                "Complemento", "São Paulo", "SP", "44340000");
        usuarioDTOResponse = UsuarioDTOResponseFixture.build("rytechh", "rytechh21@gmail.com", "1234",
                List.of(enderecoDTOResponse), List.of(telefoneDTOResponse));
    }

    @Test
    void deveConverterParaUsuarioEntity() {

        Usuario entity = usuarioConverter.paraUsuario(usuarioDTORequest);

        assertEquals(usuarioEntity, entity);
    }

    @Test
    void deveConverterParaUsuarioDTOResponse() {

        UsuarioDTOResponse dtoResponse = usuarioConverter.paraUsuarioDTO(usuarioEntity);

        assertEquals(usuarioDTOResponse, dtoResponse);
    }

}

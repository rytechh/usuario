package com.rytech.usuario.business.converter;

import com.rytech.usuario.business.dto.in.EnderecoDTORequest;
import com.rytech.usuario.business.dto.in.TelefoneDTORequest;
import com.rytech.usuario.business.dto.in.UsuarioDTORequest;
import com.rytech.usuario.infrastructure.entity.Endereco;
import com.rytech.usuario.infrastructure.entity.Telefone;
import com.rytech.usuario.infrastructure.entity.Usuario;
import com.rytech.usuario.business.dto.in.EnderecoDTORequestFixture;
import com.rytech.usuario.business.dto.in.TelefoneDTORequestFixture;
import com.rytech.usuario.business.dto.in.UsuarioDTORequestFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class UsuarioUpdateConverterTest {

    @InjectMocks
    UsuarioConverter usuarioConverter;

    Usuario usuarioEntity;

    Usuario usuarioEntityEsperado;

    Endereco enderecoEntityEsperado;

    Telefone telefoneEntityEsperado;

    UsuarioDTORequest usuarioRequestCompletoDTO;

    UsuarioDTORequest usuarioDTORequestNulo;

    Endereco enderecoEntity;

    Telefone telefoneEntity;

    TelefoneDTORequest telefoneRequestDTONullo;

    TelefoneDTORequest telefoneRequestCompletoDTO;

    EnderecoDTORequest enderecoRequestDTONullo;

    EnderecoDTORequest enderecoRequestCompletoDTO;


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
                .nome("Lore")
                .email("leuraloura@gmail.com")
                .senha("14102001")
                .enderecos(List.of())
                .telefones(List.of())
                .build();
        enderecoRequestDTONullo = EnderecoDTORequestFixture.buildNulo();
        enderecoRequestCompletoDTO = EnderecoDTORequestFixture.buildCompleto();
        telefoneRequestDTONullo = TelefoneDTORequestFixture.buildNulo();
        telefoneRequestCompletoDTO = TelefoneDTORequestFixture.buildCompleto();
        usuarioDTORequestNulo = UsuarioDTORequestFixture.buildNulo();
        usuarioRequestCompletoDTO = UsuarioDTORequestFixture.buildCompleto();

        telefoneEntityEsperado = Telefone.builder()
                .numero("75981202878")
                .ddd("21")
                .build();
        enderecoEntityEsperado = Endereco.builder()
                .rua("Rua Teste")
                .numero("999")
                .complemento("Apto 10")
                .cidade("Salvador")
                .estado("BA")
                .cep("40000000")
                .build();
        usuarioEntityEsperado = Usuario.builder()
                .nome("rytechh")
                .email("rytechh21@gmail.com")
                .senha("1234567")
                .enderecos(List.of())
                .telefones(List.of())
                .build();
    }

    @Test
    void deveManterDadosDaEntityUsuarioQuandoDtoForTotalmenteNullo() {

        Usuario resultado = usuarioConverter.updateUsuario(usuarioDTORequestNulo, usuarioEntityEsperado);

        assertEquals(usuarioEntityEsperado, resultado);
    }

    @Test
    void deveSobrescreverDadosQuandoDtoUsuarioEstiverCompleto() {

        Usuario resultado = usuarioConverter.updateUsuario(usuarioRequestCompletoDTO, usuarioEntity);

        assertEquals(usuarioEntityEsperado, resultado);

    }

    @Test
    void deveManterDadosDaEntityEnderecoQuandoDtoForTotalmenteNullo() {

        Endereco resultado = usuarioConverter.updateEndereco(enderecoRequestDTONullo, enderecoEntityEsperado);

        assertEquals(enderecoEntityEsperado, resultado);
    }

    @Test
    void deveSobrescreverDadosQuandoDtoEnderecoEstiverCompleto() {

        Endereco resultado = usuarioConverter.updateEndereco(enderecoRequestCompletoDTO, enderecoEntity);

        assertEquals(enderecoEntityEsperado, resultado);
    }

    @Test
    void deveManterDadosDaEntityTelefoneQuandoDtoForTotalmenteNullo() {

        Telefone resultado = usuarioConverter.updateTelefone(telefoneRequestDTONullo, telefoneEntityEsperado);

        assertEquals(telefoneEntityEsperado, resultado);
    }

    @Test
    void deveSobreescreverDadosQuandoDtoTelefoneEstiverCompleto() {

        Telefone resultado = usuarioConverter.updateTelefone(telefoneRequestCompletoDTO, telefoneEntity);

        assertEquals(telefoneEntityEsperado, resultado);
    }

}

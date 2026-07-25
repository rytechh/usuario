package com.rytech.usuario.business.converter;

import com.rytech.usuario.business.dto.in.TelefoneDTORequest;
import com.rytech.usuario.business.dto.out.TelefoneDTOResponse;
import com.rytech.usuario.infrastructure.entity.Telefone;
import com.rytech.usuario.business.dto.in.TelefoneDTORequestFixture;
import com.rytech.usuario.business.dto.out.TelefoneDTOResponseFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class TelefoneConverterTest {

    @InjectMocks
    UsuarioConverter usuarioConverter;

    Telefone telefoneEntity;

    TelefoneDTORequest telefoneDTORequest;

    TelefoneDTOResponse telefoneDTOResponse;


    @BeforeEach
    public void setUp() {

        telefoneEntity = Telefone.builder()
                .numero("34243526")
                .ddd("71")
                .build();

        telefoneDTORequest = TelefoneDTORequestFixture.build("34243526", "71");

        telefoneDTOResponse = TelefoneDTOResponseFixture.build(123456L, "34243526", "71");
    }

    @Test
    void deveConverterTelefoneDTOParaEntity() {

        Telefone entity = usuarioConverter.paraTelefone(telefoneDTORequest);

        assertEquals(telefoneEntity, entity);
    }

    @Test
    void deveConverterTelefoneEntityParaDTO() {

        TelefoneDTOResponse dto = usuarioConverter.paraTelefoneDTO(telefoneEntity);

        assertEquals(telefoneDTOResponse, dto);
    }

}

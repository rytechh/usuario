package com.rytech.usuario.business.dto.in;

import java.util.List;

public class UsuarioDTORequestFixture {

    public static UsuarioDTORequest build(String nome, String email, String senha,
                                          List<EnderecoDTORequest> enderecos,
                                          List<TelefoneDTORequest> telefones
    ) {
        return UsuarioDTORequest.builder()
                .nome("rytechh")
                .email("rytechh21@gmail.com")
                .senha("1234")
                .enderecos(enderecos)
                .telefones(telefones)
                .build();
    }

    public static UsuarioDTORequest buildNulo() {
        return UsuarioDTORequest.builder()
                .nome(null)
                .email(null)
                .senha(null)
                .enderecos(List.of())
                .telefones(List.of())
                .build();
    }

    public static UsuarioDTORequest buildCompleto() {
        return UsuarioDTORequest.builder()
                .nome("rytechh")
                .email("rytechh21@gmail.com")
                .senha("1234567")
                .enderecos(List.of())
                .telefones(List.of())
                .build();
    }

}

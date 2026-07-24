package com.rytech.usuario.business.dto.out;

import java.util.List;

public class UsuarioDTOResponseFixture {

    public static UsuarioDTOResponse build(String nome,
                                           String email,
                                           String senha,
                                           List<EnderecoDTOResponse> enderecos,
                                           List<TelefoneDTOResponse> telefones
    ) {
        return new UsuarioDTOResponse(nome, email, senha, enderecos, telefones);
    }
}

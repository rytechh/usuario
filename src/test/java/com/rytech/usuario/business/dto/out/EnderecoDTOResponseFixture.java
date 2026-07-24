package com.rytech.usuario.business.dto.out;

public class EnderecoDTOResponseFixture {

    public static EnderecoDTOResponse build(Long id,
                                            String rua,
                                            String numero,
                                            String complemento,
                                            String cidade,
                                            String estado,
                                            String cep
    ) {
        return new EnderecoDTOResponse(id, rua, numero, complemento, cidade, estado, cep);
    }
}

package com.rytech.usuario.business.dto.in;

public class EnderecoDTORequestFixture {

    public static EnderecoDTORequest build(String rua, String numero, String complemento,
                                           String cidade,
                                           String estado,
                                           String cep) {
        return EnderecoDTORequest.builder()
                .rua(rua)
                .numero(numero)
                .complemento(complemento)
                .cidade(cidade)
                .estado(estado)
                .cep(cep)
                .build();
    }

    public static EnderecoDTORequest buildNulo() {
        return EnderecoDTORequest.builder()
                .rua(null)
                .numero(null)
                .complemento(null)
                .cidade(null)
                .estado(null)
                .cep(null)
                .build();
    }

    public static EnderecoDTORequest buildCompleto() {
        return EnderecoDTORequest.builder()
                .rua("Rua Teste")
                .numero("999")
                .complemento("Apto 10")
                .cidade("Salvador")
                .estado("BA")
                .cep("40000000")
                .build();
    }

}

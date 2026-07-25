package com.rytech.usuario.infrastructure.clients;

public class ViaCepDTOFixture {

    public static ViaCepDTO build(String cep,

                                  String logradouro,

                                  String complemento,

                                  String unidade,

                                  String bairro,

                                  String localidade,

                                  String uf,

                                  String estado,

                                  String regiao,

                                  String ibge,

                                  String gia,

                                  String ddd,

                                  String siafi
    ) {
        return new ViaCepDTO(cep, logradouro, complemento, unidade, bairro, localidade,
                uf, estado, regiao, ibge, gia, ddd, siafi);
    }

}

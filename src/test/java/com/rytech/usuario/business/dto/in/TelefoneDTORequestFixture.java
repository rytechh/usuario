package com.rytech.usuario.business.dto.in;

public class TelefoneDTORequestFixture {

    public static TelefoneDTORequest build(String numero, String ddd) {

        return TelefoneDTORequest.builder()
                .numero(numero)
                .ddd(ddd)
                .build();
    }

    public static TelefoneDTORequest buildNulo() {
        return TelefoneDTORequest.builder()
                .numero(null)
                .ddd(null)
                .build();
    }

    public static TelefoneDTORequest buildCompleto() {
        return TelefoneDTORequest.builder()
                .numero("75981202878")
                .ddd("21")
                .build();
    }
}

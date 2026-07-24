package com.rytech.usuario.business.dto.out;

public class TelefoneDTOResponseFixture {

    public static TelefoneDTOResponse build(Long id,
                                            String numero,
                                            String ddd) {
        return new TelefoneDTOResponse(id, numero, ddd);
    }
}

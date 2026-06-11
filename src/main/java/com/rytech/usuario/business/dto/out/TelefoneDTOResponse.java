package com.rytech.usuario.business.dto.out;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(exclude = "id")
public class TelefoneDTOResponse {

    private Long id;
    private String numero;
    private String ddd;
}

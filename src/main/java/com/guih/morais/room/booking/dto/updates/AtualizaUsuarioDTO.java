package com.guih.morais.room.booking.dto.updates;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AtualizaUsuarioDTO(
        @NotNull
        Long id,
        @NotBlank
        String nome
) {
}

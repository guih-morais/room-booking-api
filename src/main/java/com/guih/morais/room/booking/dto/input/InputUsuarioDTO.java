package com.guih.morais.room.booking.dto.input;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record InputUsuarioDTO(
        @NotBlank
        String nome,
        @Email
        @NotBlank
        String email
) {
}

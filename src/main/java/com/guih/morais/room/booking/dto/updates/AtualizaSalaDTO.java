package com.guih.morais.room.booking.dto.updates;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record AtualizaSalaDTO(
        @NotNull
        Long id,
        @Positive
        Integer capacidade,
        @Positive
        Integer numeroSala
) {
}

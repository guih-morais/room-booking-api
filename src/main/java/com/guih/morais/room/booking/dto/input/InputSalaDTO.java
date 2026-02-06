package com.guih.morais.room.booking.dto.input;

import com.guih.morais.room.booking.models.Sala;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record InputSalaDTO(
        @Positive
        @NotNull
        Integer capacidade,
        @Positive
        @NotNull
        Integer numeroSala
) {
    public InputSalaDTO(Sala sala) {
        this(sala.getCapacidade(), sala.getNumeroSala());
    }
}

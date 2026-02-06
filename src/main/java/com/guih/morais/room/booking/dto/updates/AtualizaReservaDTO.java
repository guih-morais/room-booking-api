package com.guih.morais.room.booking.dto.updates;

import com.guih.morais.room.booking.models.Reserva;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record AtualizaReservaDTO(
        @NotNull
        Long id,
        @Future
        @NotNull
        @JsonFormat(pattern = "dd/MM/yy - HH:mm")
        LocalDateTime inicioReserva,
        @Future
        @NotNull
        @JsonFormat(pattern = "dd/MM/yy - HH:mm")
        LocalDateTime fimReserva
) {
    public AtualizaReservaDTO(Reserva reserva) {
        this(   reserva.getId(),
                reserva.getInicioReserva(),
                reserva.getFimReserva());
    }


}

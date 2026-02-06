package com.guih.morais.room.booking.dto.input;

import com.guih.morais.room.booking.models.Reserva;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record InputReservaDTO(
        @NotNull
        @Future
        @JsonFormat(pattern = "dd/MM/yy - HH:mm")
        LocalDateTime inicioReserva,
        @NotNull
        @Future
        @JsonFormat(pattern = "dd/MM/yy - HH:mm")
        LocalDateTime fimReserva,
        @NotNull
        Long sala_id,
        @NotNull
        Long usuario_id) {

    public InputReservaDTO(Reserva reserva) {
        this(reserva.getInicioReserva(),
                reserva.getFimReserva(),
                reserva.getSala().getId(),
                reserva.getUsuario().getId());
    }
}

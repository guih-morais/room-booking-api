package com.guih.morais.room.booking.dto;

import com.guih.morais.room.booking.dto.resume.ResumeSalaDTO;
import com.guih.morais.room.booking.dto.resume.ResumeUsuarioDTO;
import com.guih.morais.room.booking.models.Reserva;
import com.guih.morais.room.booking.models.StatusReserva;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record ReservaDTO(
        Long id,
        @JsonFormat(pattern = "dd/MM/yy - HH:mm")
        LocalDateTime inicioReserva,
        @JsonFormat(pattern = "dd/MM/yy - HH:mm")
        LocalDateTime fimReserva,
        ResumeSalaDTO sala,
        ResumeUsuarioDTO usuario,
        StatusReserva statusReserva) {

    public ReservaDTO(Reserva reserva) {
        this(reserva.getId(),
                reserva.getInicioReserva(),
                reserva.getFimReserva(),
                new ResumeSalaDTO(reserva.getSala()),
                new ResumeUsuarioDTO(reserva.getUsuario()),
                reserva.getStatusReserva());
    }
}

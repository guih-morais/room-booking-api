package classroom.scheduler.dto;

import classroom.scheduler.dto.resume.ResumeSalaDTO;
import classroom.scheduler.dto.resume.ResumeUsuarioDTO;
import classroom.scheduler.models.Reserva;
import classroom.scheduler.models.Sala;
import classroom.scheduler.models.StatusReserva;
import classroom.scheduler.models.Usuario;
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

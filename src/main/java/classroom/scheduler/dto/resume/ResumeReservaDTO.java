package classroom.scheduler.dto.resume;

import classroom.scheduler.models.Reserva;
import classroom.scheduler.models.StatusReserva;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record ResumeReservaDTO(
        Long id,
        @JsonFormat(pattern = "dd/MM/yy - HH:mm")
        LocalDateTime inicioReserva,
        @JsonFormat(pattern = "dd/MM/yy - HH:mm")
        LocalDateTime fimReserva,
        ResumeUsuarioDTO usuario,
        ResumeSalaDTO sala,
        StatusReserva statusReserva


) {
    public ResumeReservaDTO(Reserva reserva){
        this(reserva.getId(),
                reserva.getInicioReserva(),
                reserva.getFimReserva(),
                new ResumeUsuarioDTO(reserva.getUsuario()),
                new ResumeSalaDTO(reserva.getSala()),
                reserva.getStatusReserva());
    }


}

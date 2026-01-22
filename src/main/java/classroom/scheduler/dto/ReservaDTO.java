package classroom.scheduler.dto;

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
        Sala sala,
        Usuario usuario,
        StatusReserva statusReserva) {

    public ReservaDTO(Reserva reserva) {
        this(reserva.getId(),
                reserva.getInicioReserva(),
                reserva.getFimReserva(),
                reserva.getSala(),
                reserva.getUsuario(),
                reserva.getStatusReserva());
    }
}
